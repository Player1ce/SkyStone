package org.firstinspires.ftc.logic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.EncodersFix;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class NavigationFix {
    ChassisName chassis;
    public NavigationFix(ChassisName chassisName) {
        this.chassis = chassisName;
    }


        /*setPIDValues(rotationPidController, 0.00325, 0.001,0.001);
        setPIDValues(yPidController, .00225, 0.001, 0.001);
        setPIDValues(xPidController, .15, .001, .001);*/


    private PIDController rotationPidController = new PIDController("rot_pid",true,0.05, 0.0015,0.0015);

    private PIDController yPidController = new PIDController("y_pid",true,0.00225, 0.001, 0.001);
    private PIDController xPidController = new PIDController("x_pid",true,0.00015, .0001, .0001);

    EncodersFix encoders = new EncodersFix(chassis);
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


    public void setMaxRotationCorrectionPower (double power) { maxRotationCorrectionPower = power; }


    public double calculateCorrectionPower (PIDController pid, double position, double maxMotorPower, double minMotorPower) {

        double outputPower;
        pid.input(position);
        outputPower = Math.abs(pid.output());
        outputPower = Math.max(minMotorPower, outputPower);
        outputPower = Math.min(maxMotorPower, outputPower);

        return outputPower * (pid.output() / Math.abs(pid.output()));
    }


    public void NavigateStraightTicks (Telemetry telemetry, double MotorPower,
                                       double MinMotorPower, double ticks) {
        //imu----------------------------------------------------------------------------------------------
        rotationPidController.setMaxErrorForIntegral(0.002);
        long curTime;
        long diff;
        startOrientation = imu.getOrientation();
        setMaxRotationCorrectionPower(MotorPower - 0.1);
        double correctionPower;
        double rightCorrect = 0;
        double leftCorrect = 0;

        //encoders---------------------------------------------------------------------------------------------
        encoders.resetPosition();

        //need to set this for getting direction later
        encoders.setXTarget(0);
        encoders.setYTarget(ticks);

        double distanceX;
        double distanceY;
        double currentY;
        double powerX;
        double powerY;
        double yDirection;
        double xDirection;

        //sets target for pid
        xPidController.setTarget(0);
        yPidController.setTarget(ticks);
        Orientation startAngle = new Orientation();
        rotationPidController.setTarget(startAngle.firstAngle);

        while (Math.abs(ticks - encoders.getY()) < 10) {
            wheels.checkIsActive();

            /*
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
            */

            //encoders---------------------------------------------------------------------------------------------
            distanceX = encoders.getX();
            currentY = encoders.getY();
            distanceY = encoders.yTarget - currentY;

            //TODO: Check this ---------------------------------------------------------------------------------------------
            //need to fix these numbers
            //divide by ten to adjust for the huge encoder tick values. might need to divide by 100.
            powerX = calculateCorrectionPower(xPidController, distanceX, Math.max( .2, MotorPower - .4)  , 0.03) / 10;
            powerY = calculateCorrectionPower(yPidController, currentY, MotorPower, MinMotorPower) / 10;

            yDirection = encoders.getYDirection(distanceX);
            //xDirection = encoders.getXDirection(currentY);


            //set power---------------------------------------------------------------------------------------------
            xDirection = 1;

            wheels.setPower(
                    (powerY) -(powerX * xDirection) + rightCorrect,
                    (powerY) + (powerX *xDirection) + leftCorrect,
                    (powerY) + (powerX * xDirection),
                    (powerY) - (powerX * xDirection)
            );

            telemetry.addData("X position:", distanceX);
            telemetry.addData("distance x:", distanceX);
            telemetry.addData("Y position:", currentY);
            telemetry.addData("distance y:", distanceY);
            telemetry.addData("x Power:", (powerX * 0.4 * xDirection));
            telemetry.addData("y power", (powerY * yDirection));
            //telemetry.addData("Correction power:", correctionPower);
            telemetry.addData("Right Correct:", rightCorrect);
            telemetry.addData("Left Correct:", leftCorrect);
            telemetry.addData("Angle", angle);
            //telemetry.addData("Time(ms)", diff);
            telemetry.update();

        }
        wheels.StopMotors();

        correctRotation(telemetry, rightCorrect, leftCorrect);


    }


    public void rotate(double degrees, double motorPower, double minMotorPower, LinearOpMode linearOpMode)
    {
        //double motorPower = .4;
        //double minMotorPower = .3;
        double distance = Math.abs(degrees - imu.getAngle());
        double calcedPower= MecanumWheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
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
                calcedPower= MecanumWheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, 1, 0, 0);
            }

            while (linearOpMode.opModeIsActive() && imu.getAngle() > degrees) {
                distance = Math.abs(degrees - imu.getAngle());
                calcedPower= MecanumWheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, 1, 0, 0);
            }
        }
        else    // left turn.
            while (linearOpMode.opModeIsActive() && imu.getAngle() < degrees) {
                distance = Math.abs(degrees - imu.getAngle());
                calcedPower= MecanumWheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, -1, 0, 0);
            }


        // turn the motors off.
        wheels.StopMotors();

        // wait for rotation to stop.
        wheels.sleepAndCheckActive(1000);

        // reset angle tracking on new heading.
        imu.resetAngle();
    }

    public void NavigateStraightTicksBackwards (Telemetry telemetry, double MotorPower,
                                       double MinMotorPower, double ticks) {
        //imu----------------------------------------------------------------------------------------------
        rotationPidController.setMaxErrorForIntegral(0.002);
        long curTime;
        long diff;
        startOrientation = imu.getOrientation();
        setMaxRotationCorrectionPower(MotorPower - 0.1);
        double correctionPower;
        double rightCorrect = 0;
        double leftCorrect = 0;

        //encoders---------------------------------------------------------------------------------------------
        encoders.resetPosition();
        //encoders.setyTarget((Inches * 4)/.0699);
        encoders.setXTarget(0);
        encoders.setYTarget(ticks);

        double dest=Math.abs(encoders.yTarget)-5;

        /*setPIDValues(rotationPidController, 0.0125, 0.001, 0.001);
        setPIDValues(yPidController, 0.15, 0.001,0.001);
        setPIDValues(xPidController, .00225, 0.001, 0.001);*/

        while (Math.abs(encoders.getY()) < dest) {
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
            double powerX = calculateCorrectionPower(xPidController, distanceX * .03 ,   Math.max( .2, MotorPower - .4)  , 0.03);
            double powerY = calculateCorrectionPower(yPidController, distanceY, MotorPower, MinMotorPower);
            double yDirection = encoders.getYDirection(1);
            double xDirection = encoders.getXDirection(1);

/*
            if (encoders.getY() == 0) {
                wheels.setPowerFromGamepad(false,1, 0 ,0, 1 * yDirection);
            }
 */

            //set power---------------------------------------------------------------------------------------------
            xDirection = 1;
            //powerY = 0;
            wheels.setPower(
                    (powerY) -(powerX * xDirection) + rightCorrect,
                    (powerY) + (powerX *xDirection) + leftCorrect,
                    (powerY) + (powerX * xDirection),
                    (powerY) - (powerX * xDirection)
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
        //setPIDValues(xPidController, .15, .001, .001);
        imu.resetAngle();
        wheels.sleepAndCheckActive(500);


    }



    public void correctY(Telemetry telemetry) {

        double distanceY = encoders.yTarget - encoders.getY();
        double yDirection = -1*encoders.getYDirection(1);

        if (encoders.getY()==0) {
            return;
        }

        while (Math.abs(encoders.getY()) > 5) {
            wheels.checkIsActive();

            distanceY =Math.abs(encoders.getY());

            double powerY = calculateCorrectionPower(yPidController, distanceY, 0.4, 0.1);

            //powerY = 0;
            wheels.setPower(
                    (powerY*yDirection) ,
                    (powerY*yDirection) ,
                    (powerY*yDirection),
                    (powerY*yDirection)
            );


            telemetry.addData("Y position:", encoders.getY());
            telemetry.addData("distance y:", distanceY);
            telemetry.addData("y power", powerY);
            telemetry.addData("output:", yPidController.output());
            telemetry.addData("correction:", (yPidController.output() / Math.abs(yPidController.output())));
            telemetry.update();
        }

        wheels.StopMotors();
    }

    private void correctRotation(Telemetry telemetry, double rightCorrect, double leftCorrect) {
        double correctionPower;
        angle = imu.getAngleWithStart(startOrientation);

        double kp=rotationPidController.getKP();
        double ki=rotationPidController.getKI();
        double kd=rotationPidController.getKD();

        rotationPidController.setKD(kd*3);
        rotationPidController.setKI(ki*3);
        rotationPidController.setKP(kp*5);

        while (Math.abs(angle) > 0.5) {
            rotationPidController.input(angle);

            correctionPower = Math.abs(rotationPidController.output());
            //correctionPower = Math.max(correctionPower,  .2);
            //correctionPower = Math.min(-maxRotationCorrectionPower, Math.min(maxRotationCorrectionPower, correctionPower));

            if (angle < 0) {
                rightCorrect = -1 * correctionPower;//-.025;
                leftCorrect = correctionPower;//.025;
            } else if (angle > 0) {
                rightCorrect = correctionPower;//.025;
                leftCorrect = -1 * correctionPower;//-.025;
            }

            wheels.setPower(
                    rightCorrect,
                    leftCorrect,
                    rightCorrect,
                    leftCorrect
            );
            angle = imu.getAngleWithStart(startOrientation);

            telemetry.addData("X position:", encoders.getX());
            telemetry.addData("Y position:", encoders.getY());
            telemetry.addData("Right Correct:", rightCorrect);
            telemetry.addData("Left Correct:", leftCorrect);
            telemetry.addData("Angle", angle);
            telemetry.update();
        }
        wheels.StopMotors();
        imu.resetAngle();

        telemetry.addData("Angle", angle);
        telemetry.update();


        rotationPidController.setKD(kd);
        rotationPidController.setKI(ki);
        rotationPidController.setKP(kp);
    }


}
