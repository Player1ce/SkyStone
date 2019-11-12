package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeleOpMethods {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor leftIntake;
    DcMotor rightIntake;
    Servo hookServo;
    Servo rampServo;

    BNO055IMU imu;

    double x_right;
    double x_left;
    double y_left;

    final double WHEEL_DIAMETER = 6;
    final int NR40_PPR = 1120;
    final double DRIVE_WHEEL_GEAR_RATIO = 1;

    long startTime;

    String chassis;

    public TeleOpMethods (String ChassisName) {
        chassis = ChassisName.toLowerCase();
    }

    /*
    public void InitializeHardware(OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;

        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.dcMotor.get("backRight");

        backLeft = hardwareMap.dcMotor.get("backLeft");

        if (chassis.equals("tank")) {
            hookServo = hardwareMap.servo.get("hookServo");
            rampServo = hardwareMap.servo.get("rampServo");
            leftIntake = hardwareMap.dcMotor.get("leftIntake");
            rightIntake = hardwareMap.dcMotor.get("rightIntake");
        }

        //spool = hardwareMap.dcMotor.get("spool");
    }
     */

    public void setPowerVars(Gamepad gamepad1, boolean reverse) {
        x_left = gamepad1.left_stick_x;
        if (!reverse) {
            x_right = gamepad1.right_stick_x;
            y_left = -gamepad1.left_stick_y;
        } else {
            x_right = -gamepad1.right_stick_x;
            y_left = gamepad1.left_stick_y;
        }
    }



    public void setPower (double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
    }

    public String reverseSense(boolean reverse) {
        if (reverse) {
            return("F/R: REVERSE");
        }else {
            return("F/R: FORWARD");
        }
    }

    public void startTime() {
        startTime = System.currentTimeMillis();
    }

}