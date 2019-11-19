package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.devices.ChassisName;

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

    ChassisName chassis;

    public TeleOpMethods (ChassisName name) { chassis = name; }

    public void startTime() { startTime = System.currentTimeMillis(); }


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

    public String reverseSense(boolean reverse) {
        if (reverse) {
            return("F/R: REVERSE");
        }else {
            return("F/R: FORWARD");
        }
    }

}