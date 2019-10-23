package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeleOpMethods {
    //TODO phase out. this class is irrelevent save it for teleop specific code
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    BNO055IMU imu;

    double x_right;
    double x_left;
    double y_left;

    final double WHEEL_DIAMETER = 6;
    final int NR40_PPR = 1120;
    final double DRIVE_WHEEL_GEAR_RATIO = 1;
    String Chassis;

    TeleOpMethods (String chassisName) {
        Chassis = chassisName.toLowerCase();
    }

    public String reverseSense(boolean reverse) {
        if (reverse) {
            return("F/R: REVERSE");
        }else {
            return("F/R: FORWARD");
        }
    }

}