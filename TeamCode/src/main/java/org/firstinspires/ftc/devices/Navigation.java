package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.nio.channels.Pipe;

public class Navigation {
    ChassisName chassis;
    public Navigation(ChassisName chassisName) {
        this.chassis = chassisName;
    }
    private PIDController rotationPidController = new PIDController(0.0125, 0.001,0.001);
    private PIDController yPidController = new PIDController(0.15, 10,10);
    private PIDController xPidController = new PIDController(0.15, 0.001,0.01);

    Encoders encoders = new Encoders(chassis);
    IMURevHub imu;
    private MecanumWheels wheels;

    double angle;
    long time = 0;
    double maxRotationCorrectionPower = 0.1;

    Orientation startOrientation;



    public void initialize(MecanumWheels wheels, IMURevHub imu, OpMode opMode) {
        this.wheels = wheels;
        this.imu = imu;
        encoders.initialize(wheels, opMode);
    }


    public void setPIDValues( PIDController pid, double KP, double KI, double KD) {
        pid.setKP(KP);
        pid.setKI(KI);
        pid.setKD(KD);
    }


    public void setRotationMaxCorrectionPower (double power) { maxRotationCorrectionPower = power; }


    public double calculateCorrectionPower (PIDController pid, double error, double maxMotorPower, double minMotorPower) {
        double outputPower;
        pid.input(error);
        outputPower = Math.abs(pid.output());
        outputPower = Math.max(minMotorPower, outputPower);
        outputPower = Math.min(maxMotorPower, outputPower);
        return outputPower * (pid.output() / Math.abs(pid.output()));
    }

    public void rotate(double degrees, double motorPower, double minMotorPower, LinearOpMode linearOpMode)
    {
        //double motorPower = .4;
        //double minMotorPower = .3;
        double distance = Math.abs(degrees - imu.getAngle());
        double calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
        double  leftPower, rightPower;

        // restart imu movement tracking.
        imu.resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (linearOpMode.opModeIsActive() && imu.getAngle() == 0) {
                distance = Math.abs(degrees - imu.getAngle());
                calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, 1, 0, 0);
            }

            while (linearOpMode.opModeIsActive() && imu.getAngle() > degrees) {
                distance = Math.abs(degrees - imu.getAngle());
                calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, 1, 0, 0);
            }
        }
        else    // left turn.
            while (linearOpMode.opModeIsActive() && imu.getAngle() < degrees) {
                distance = Math.abs(degrees - imu.getAngle());
                calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, -1, 0, 0);
            }


        // turn the motors off.
        wheels.StopMotors();

        // wait for rotation to stop.
        wheels.sleepAndCheckActive(1000);

        // reset angle tracking on new heading.
        imu.resetAngle();
    }

    public void NavigateStraightTicks (Telemetry telemetry, double MotorPower,
                                        double MinMotorPower, double ticks) {
        //imu----------------------------------------------------------------------------------------------
        rotationPidController.setMaxErrorForIntegral(0.002);
        long curTime;
        long diff;
        startOrientation = imu.getOrientation();
        setRotationMaxCorrectionPower(MotorPower - 0.1);
        double correctionPower;
        double rightCorrect = 0;
        double leftCorrect = 0;


        //encoders---------------------------------------------------------------------------------------------
        encoders.resetPosition();
        //encoders.setyTarget((Inches * 4)/.0699);
        encoders.setxTarget(0);
        encoders.setyTarget(ticks);

        double dest=Math.abs(encoders.yTarget)-5;

        //setPIDValues(rotationPidController, 0.0015, 0.001, 0.001);
        //setPIDValues(yPidController, 0.15, .001,.001);
        //setPIDValues(xPidController, .15, .001, .001);

        while (Math.abs(encoders.getY()) < dest ) {
            wheels.checkIsActive();

            //imu---------------------------------------------------------------------------------------------
            angle = imu.getAngleWithStart(startOrientation);
            rotationPidController.input(angle);

            correctionPower = Math.abs(rotationPidController.output());
            correctionPower = Math.max(-maxRotationCorrectionPower, Math.min(maxRotationCorrectionPower, correctionPower));

            if (angle < 0) {
                rightCorrect = -1 * correctionPower;//-.025;
                leftCorrect = correctionPower;//.025;
            } else if (angle > 0) {
                rightCorrect = correctionPower;//.025;
                leftCorrect = -1 * correctionPower;//-.025;
            }

            curTime = System.currentTimeMillis();
            diff = curTime - time;
            time = curTime;

            //encoders---------------------------------------------------------------------------------------------
            double distanceX = encoders.xTarget - encoders.getX();
            double distanceY = encoders.yTarget - encoders.getY();
            //double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, Math.max(MinMotorPower, .3));
            //double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            //TODO: Check this ---------------------------------------------------------------------------------------------
            double powerX = calculateCorrectionPower(xPidController, distanceX, MotorPower, MinMotorPower);
            double powerY = calculateCorrectionPower(yPidController, distanceY, MotorPower, MinMotorPower);

            double yDirection = encoders.getYDirection();
            double xDirection = encoders.getXDirection();

/*
            if (encoders.getY() == 0) {
                wheels.setPowerFromGamepad(false,1, 0 ,0, 1 * yDirection);
            }
 */

            //set power---------------------------------------------------------------------------------------------
            wheels.setPower(
                    (powerY * yDirection) -(powerX * 0.4 * xDirection) + rightCorrect,
                    (powerY * yDirection) + (powerX * 0.4 * xDirection) + leftCorrect,
                    (powerY * yDirection) + (powerX * 0.4 * xDirection),
                    (powerY * yDirection) - (powerX * 0.4 * xDirection)
            );

            telemetry.addData("X position:", encoders.getX());
            telemetry.addData("Y position:", encoders.getY());
            telemetry.addData("distance y:", distanceY);
            telemetry.addData("distance x:", distanceX);
            telemetry.addData("x Power:", (powerX * 0.4 * xDirection));
            telemetry.addData("y power", (powerY * yDirection));
            telemetry.addData("Correction power:", correctionPower);
            telemetry.addData("Right Correct:", rightCorrect);
            telemetry.addData("Left Correct:", leftCorrect);
            telemetry.addData("Angle", angle);
            telemetry.addData("Time(ms)", diff);
            telemetry.update();

        }
        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);


    }


    public void NavigateCrabTicks (Telemetry telemetry, double MotorPower,
                                       double MinMotorPower, double ticks) {
        //imu----------------------------------------------------------------------------------------------
        long curTime;
        long diff;
        startOrientation = imu.getOrientation();
        setRotationMaxCorrectionPower(MotorPower - 0.1);
        double correctionPower;
        double rightCorrect = 0;
        double leftCorrect = 0;

        //encoders---------------------------------------------------------------------------------------------
        encoders.resetPosition();
        //encoders.setyTarget((Inches * 4)/.0699);
        encoders.setyTarget(0);
        encoders.setxTarget(ticks);

        setPIDValues(rotationPidController, .015, 0.001, .001);
        setPIDValues(yPidController, .01, 0.001, 0.001);
        setPIDValues(xPidController, .15, .001, .001);


        while (Math.abs(encoders.getX()) < Math.abs(encoders.xTarget)) {
            wheels.checkIsActive();

            //imu---------------------------------------------------------------------------------------------
            angle = imu.getAngleWithStart(startOrientation);
            rotationPidController.input(angle);

            correctionPower = Math.abs(rotationPidController.output());
            correctionPower = Math.max(-maxRotationCorrectionPower, Math.min(maxRotationCorrectionPower, correctionPower));

            if (angle < 0) {
                rightCorrect = -1 * correctionPower;//-.025;
                leftCorrect = correctionPower;//.025;
            } else if (angle > 0) {
                rightCorrect = correctionPower;//.025;
                leftCorrect = -1 * correctionPower;//-.025;
            }

            curTime = System.currentTimeMillis();
            diff = curTime - time;
            time = curTime;

            //encoders---------------------------------------------------------------------------------------------
            //double distanceX = Math.abs(encoders.getX() - encoders.xTarget);
            //double distanceY = Math.abs(encoders.getY() - encoders.yTarget);
            double distanceX = encoders.xTarget - encoders.getX();
            double distanceY = encoders.yTarget - encoders.getY();

            //double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, Math.max(MinMotorPower, .3));
            //double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            //TODO: Check this ---------------------------------------------------------------------------------------------
            double powerX = calculateCorrectionPower(xPidController, distanceX, MotorPower, MinMotorPower);
            double powerY = calculateCorrectionPower(yPidController, distanceY, MotorPower, MinMotorPower);


            double yDirection = encoders.getYDirection();
            double xDirection = encoders.getXDirection();

/*
            if (encoders.getY() == 0) {
                wheels.setPowerFromGamepad(false,1, 0,1 * xDirection ,0);
            }
*/
            //set power---------------------------------------------------------------------------------------------
            //wheels.setPower(-powerX, powerX, powerX, -powerX);
            wheels.setPower((-powerX) + (rightCorrect),
                    (powerX) + (leftCorrect),
                    (powerX) + (leftCorrect),
                    (-powerX) + (rightCorrect));

            /*wheels.setPower(
                    ((powerY * 0.4 * 0) - - + rightCorrect *0),
                    ((powerY * 0.4 *0) + -.5 + leftCorrect *0),
                    ((powerY * 0.4 *0) + -.5 + rightCorrect *0),
                    ((powerY * 0.4 *0) - -.5 + leftCorrect *0)
            );

             */

            telemetry.addData("X position:", encoders.getX());
            telemetry.addData("Y position:", encoders.getY());
            telemetry.addData("distance x:", distanceX);
            telemetry.addData("distance y:", distanceY);
            telemetry.addData("x Power:", (powerX));
            telemetry.addData("y power", (powerY * .4));
            telemetry.addData("Correction power:", correctionPower);
            telemetry.addData("Right Correct:", rightCorrect);
            telemetry.addData("Left Correct:", leftCorrect);
            telemetry.addData("output:", yPidController.output());
            telemetry.addData("correction:", (yPidController.output() / Math.abs(yPidController.output())));
            telemetry.addData("Angle", angle);
            telemetry.addData("Time(ms)", diff);
            telemetry.update();

        }

        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);
        imu.resetAngle();
    }


}
