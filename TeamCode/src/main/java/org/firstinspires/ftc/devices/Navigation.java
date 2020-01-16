package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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
    private PIDController yPidController = new PIDController(0.15, 0.001,0.001);
    private PIDController xPidController = new PIDController(0.15, 0.001,0.001);

    Encoders encoders = new Encoders(chassis);
    IMURevHub imu = new IMURevHub(chassis);
    private MecanumWheels wheels;

    double angle;
    long time = 0;
    double maxRotationCorrectionPower = 0.1;

    Orientation startOrientation;



    public void initialize(MecanumWheels wheels, OpMode opMode) {
        this.wheels = wheels;
        imu.initializeIMU(wheels, opMode);
        encoders.initialize(wheels, opMode);
    }


    public void setPIDValues( PIDController pid, double KP, double KI, double KD) {
        pid.setKP(KP);
        pid.setKI(KI);
        pid.setKD(KD);
    }


    public void setRotationMaxCorrectionPower (double power) { maxRotationCorrectionPower = power; }


    public double calculateCorrectionPower (PIDController pid, double error, double maxMotorPower, double minMotorPower) {
        double power;
        double outputPower;
        pid.input(error);
        outputPower = pid.output();
        power = Math.max(minMotorPower, outputPower);
        outputPower = Math.min(maxMotorPower, power);
        return outputPower;
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
        double currentX = encoders.getX();

        while (Math.abs(encoders.getY()) < Math.abs(encoders.yTarget) || Math.abs(encoders.getX()) < Math.abs(encoders.xTarget + 10)) {
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
            double distanceX = Math.abs(encoders.getX() - encoders.xTarget);
            double distanceY = Math.abs(encoders.getY() - encoders.yTarget);
            double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, Math.max(MinMotorPower, .3));
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            //TODO: Check this -------------------------------------------------
            powerX = calculateCorrectionPower(xPidController, distanceX, MotorPower, MinMotorPower);
            powerY = calculateCorrectionPower(yPidController, distanceY, MotorPower, MinMotorPower);

            double yDirection = encoders.getYDirection();
            double xDirection = encoders.getXDirection();


            //set power---------------------------------------------------------------------------------------------
            wheels.setPower(
                    (powerY * yDirection) -(powerX * 0.4 * xDirection) + rightCorrect,
                    (powerY * yDirection) + (powerX * 0.4 * xDirection) + leftCorrect,
                    (powerY * yDirection) + (powerX * 0.4 * xDirection),
                    (powerY * yDirection) - (powerX * 0.4 * xDirection)
            );

            telemetry.addData("X position:", encoders.getX());
            telemetry.addData("Y position:", encoders.getY());
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
        double currentX = encoders.getX();

        while (Math.abs(encoders.getX()) < Math.abs(encoders.xTarget) || Math.abs(encoders.getY()) < Math.abs(encoders.yTarget + 10)) {
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
            double distanceX = Math.abs(encoders.getX() - encoders.xTarget);
            double distanceY = Math.abs(encoders.getY() - encoders.yTarget);
            double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, Math.max(MinMotorPower, .3));
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            double yDirection = encoders.getYDirection();
            double xDirection = encoders.getXDirection();

            //set power---------------------------------------------------------------------------------------------
            wheels.setPower(
                    (powerY * yDirection) -(powerX * 0.4 * xDirection) + rightCorrect,
                    (powerY * yDirection) + (powerX * 0.4 * xDirection) + leftCorrect,
                    (powerY * yDirection) + (powerX * 0.4 * xDirection) - rightCorrect,
                    (powerY * yDirection) - (powerX * 0.4 * xDirection) - leftCorrect
            );

            telemetry.addData("X position:", encoders.getX());
            telemetry.addData("Y position:", encoders.getY());
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


    public void OrientateStraight(Telemetry telemetry, long ms) {
        long curTime;
        long diff;
        double correctionPower;
        startOrientation = imu.getOrientation();
        double startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < ms)) {
            wheels.checkIsActive();

            angle = imu.getAngleWithStart(startOrientation);

            rotationPidController.input(angle);

            correctionPower = Math.abs(rotationPidController.output());

            correctionPower = Math.max(-maxRotationCorrectionPower, Math.min(maxRotationCorrectionPower, correctionPower));


            double rightCorrect = 0;
            double leftCorrect = 0;

            curTime = System.currentTimeMillis();

            diff = curTime - time;

            time = curTime;

            if (angle < 0) {
                rightCorrect = -1 * correctionPower;//-.025;
                leftCorrect = correctionPower;//.025;
            } else if (angle > 0) {
                rightCorrect = correctionPower;//.025;
                leftCorrect = -1 * correctionPower;//-.025;
            }

            wheels.setPower(0.2 + rightCorrect, 0.2 + leftCorrect, -0.2, 0.2);

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


}
