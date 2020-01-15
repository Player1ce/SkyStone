package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.nio.channels.Pipe;

public class Navigation {
    PIDController pidController = new PIDController(0.125, 0.001,0.001);

    ChassisName chassis;

    Orientation startOrientation;

    double angle;

    long time = 0;

    double maxCorrectionPower;

    public Navigation(ChassisName chassisName) {
        this.chassis = chassisName;
    }

    private MecanumWheels wheels;
    private IMURevHub imu;
    private Encoders encoders;

    public void initialize(MecanumWheels wheels, IMURevHub imu, Encoders encoders, OpMode opMode) {
        this.wheels = wheels;
        this.encoders = encoders;
        this.imu = imu;
        maxCorrectionPower = 0.1;
    }

    public void setPIDValues(double KP, double KI, double KD) {
        pidController.setKP(KP);
        pidController.setKI(KI);
        pidController.setKD(KD);
    }

    public void setMaxCorrectionPower (double power) { maxCorrectionPower = power; }

    public double getOutput () {
        return pidController.getKP()*pidController.getError()+pidController.getKI()*pidController.getIntegral()+pidController.getKD()*pidController.getDerivative();
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

            pidController.input(angle);

            correctionPower = Math.abs(pidController.output());

            correctionPower = Math.max(-maxCorrectionPower, Math.min(maxCorrectionPower, correctionPower));


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

    public void NavigateStraightTicks (Telemetry telemetry, double MotorPower,
                                        double MinMotorPower, double ticks) {
        //imu----------------------------------------------------------------------------------------------
        pidController.setMaxErrorForIntegral(0.002);
        long curTime;
        long diff;
        startOrientation = imu.getOrientation();
        setMaxCorrectionPower(MotorPower - 0.1);
        double correctionPower;
        double rightCorrect = 0;
        double leftCorrect = 0;

        //encoders---------------------------------------------------------------------------------------------
        encoders.resetPosition();
        //encoders.setyTarget((Inches * 4)/.0699);
        encoders.setyTarget(ticks);
        double currentX = encoders.getX();

        while (Math.abs(encoders.getY()) < Math.abs(encoders.yTarget) || Math.abs(encoders.getX()) < (Math.abs(encoders.xTarget) + 10)) {
            wheels.checkIsActive();

            //imu---------------------------------------------------------------------------------------------
            angle = imu.getAngleWithStart(startOrientation);
            pidController.input(angle);

            correctionPower = Math.abs(pidController.output());
            correctionPower = Math.max(-maxCorrectionPower, Math.min(maxCorrectionPower, correctionPower));

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
                    (powerY * yDirection) + (powerX * 0.4 * xDirection),
                    (powerY * yDirection) - (powerX * 0.4 * xDirection)
            );

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
        setMaxCorrectionPower(MotorPower - 0.1);
        double correctionPower;
        double rightCorrect = 0;
        double leftCorrect = 0;

        //encoders---------------------------------------------------------------------------------------------
        encoders.resetPosition();
        //encoders.setyTarget((Inches * 4)/.0699);
        encoders.setyTarget(ticks);
        double currentX = encoders.getX();

        while (Math.abs(encoders.getX()) < Math.abs(encoders.xTarget) || Math.abs(encoders.getY()) > encoders.yTarget + 10) {
            wheels.checkIsActive();

            //imu---------------------------------------------------------------------------------------------
            angle = imu.getAngleWithStart(startOrientation);
            pidController.input(angle);

            correctionPower = Math.abs(pidController.output());
            correctionPower = Math.max(-maxCorrectionPower, Math.min(maxCorrectionPower, correctionPower));

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

}
