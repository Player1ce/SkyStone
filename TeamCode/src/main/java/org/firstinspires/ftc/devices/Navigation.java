package org.firstinspires.ftc.devices;

import android.provider.ContactsContract;
import android.view.KeyCharacterMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.devices.Odometry.Encoders;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.TeleOpMethods;

public class Navigation {

    ChassisName chassis;

    Orientation startOrientation;

    double angle;

    long time = 0;

    double maxCorrectionPower;

    public Navigation(ChassisName chassisName) {
        this.chassis = chassisName;
    }

    private MecanumWheels wheels;
    private PIDController pidController;
    private IMURevHub imu;
    private EncodersOld encoders;


    public void initialize(MecanumWheels wheels, PIDController PIDController, IMURevHub imu, EncodersOld encoders, OpMode opMode) {
        this.wheels = wheels;
        this.pidController = PIDController;
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

            correctionPower = Math.abs(getOutput());

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

    public void NavigateStraightInches (Telemetry telemetry,
        double MotorPower, double MinMotorPower, double Inches) {
        encoders.resetPosition();
        long curTime;
        long diff;
        double correctionPower;
        startOrientation = imu.getOrientation();

        //encoders.setyTarget((Inches * 4)/.0699);
        encoders.setyTarget(Inches);
        double currentX = encoders.getX();

        while (Math.abs(encoders.getY()) < Math.abs(encoders.yTarget)) {
            wheels.checkIsActive();

            angle = imu.getAngleWithStart(startOrientation);
            pidController.input(angle);

            correctionPower = Math.abs(getOutput());
            correctionPower = Math.max(-maxCorrectionPower, Math.min(maxCorrectionPower, correctionPower));

            double rightCorrect = 0;
            double leftCorrect = 0;

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

            double distanceX = Math.abs(encoders.getX() - encoders.xTarget);
            double distanceY = Math.abs(encoders.getY() - encoders.yTarget);
            double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, Math.max(MinMotorPower, .3));
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            double yDirection = encoders.getYDirection();
            double xDirection = encoders.getXDirection();



            //wheels.setPowerFromGamepad(false, 1, 0, powerX * 0.4 * xDirection, powerY * yDirection);

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
