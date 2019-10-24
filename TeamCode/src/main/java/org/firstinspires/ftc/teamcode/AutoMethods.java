package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoMethods {
    MecanumWheels wheels = new MecanumWheels(this.chassis);
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    Servo hookServo;

    double frontRightPower; //-right
    double frontLeftPower; //-right
    double backRightPower; //-right
    double backLeftPower;

    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;

    String chassis;

    final double WHEEL_DIAMETER = 6;
    final double DRIVE_WHEEL_GEAR_RATIO = 1;

    long startTime;

    AutoMethods(String chassisName) {
        chassis = chassisName.toLowerCase();
    }

    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    public void setZeroPowerBrakeBehavior() {
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e){}
    }

    public void ForwardMoveInches(Telemetry telemetry, double MotorPower, double Inches, double ticksToInches) {

        wheels.ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        double averagePos=wheels.getAverageEncoderPos();

        double dest=ticksToInches*Inches;

        while (averagePos < dest){
            telemetry.addData("Moving Forward","Moving Forward");
            telemetry.addData("avg encoder value:", averagePos*ticksToInches);
            //TODO comment or remove specific wheel telemetry?
            telemetry.addData("F/L encoder value:", frontLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("F/R encoder value:", frontRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/L encoder value:", backLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/R encoder value:", backRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("encoder target:", Inches);
            telemetry.update();

            averagePos= wheels.getAverageEncoderPos();
        }

        wheels.StopMotors();

        sleep(5000);
    }


    public void moveHook(String position){
        String fixedPosition = position.toLowerCase();
        if (fixedPosition.equals("up"))  {
            hookServo.setPosition(.47);
        }
        else if (fixedPosition.equals("down"))  {
            hookServo.setPosition(1);
        }
    }


}
