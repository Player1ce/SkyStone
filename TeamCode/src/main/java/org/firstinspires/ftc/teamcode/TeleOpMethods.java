package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TeleOpMethods extends BasicRobotMethods {
    LinearOpMode linearOpMode;
    OpMode opMode;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor horizontalEncoder;
    DcMotor forwardEncoder;
    Servo leftHook;
    Servo rightHook;
    BNO055IMU imu;
    ModernRoboticsI2cGyro gyro;
    ModernRoboticsI2cRangeSensor rangeSensor;
    ColorSensor colorSensor;

    Servo pivot;

    Servo leftServo;
    Servo rightServo;
    Servo midServo;

    DcMotor elevator;
    DcMotor intake;

    Orientation lastAngle;
    double globalAngle;

    DigitalChannel bottomLimitSwitch;
    DigitalChannel topLimitSwitch;

    final double WHEEL_DIAMETER = 6;
    final int NR40_PPR = 1120;
    final double DRIVE_WHEEL_GEAR_RATIO = 1;

    Position position;
    Velocity velocity;

    long startTime;
    boolean bad;



    //method to control hooks in TeleOp (still needs testing)
    /*@Override
    public void hookController(Gamepad gamepad) {
        if (gamepad.left_bumper) {
            leftHook.setPosition(1);
            rightHook.setPosition(1);
        } else if (gamepad.left_trigger == 1) {
            leftHook.setPosition(0);
            rightHook.setPosition(0);
        }
    }*/

}
