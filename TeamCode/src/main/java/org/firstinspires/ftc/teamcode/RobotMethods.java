package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;




public interface RobotMethods {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor horizontalEncoder;
    DcMotor forwardEncoder;
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


    public void InitializeHardware() {

        //initalize motors and set direction and mode
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        //frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backLeft = hardwareMap.dcMotor.get("backLeft");
        //backLeft.setDirection(DcMotor.Direction.REVERSE);
        //backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        //frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRight = hardwareMap.dcMotor.get("backRight");
        //backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //create encoder motor objects
        horizontalEncoder = hardwareMap.dcMotor.get("horizontalEncoder");
        horizontalEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        forwardEncoder = hardwareMap.dcMotor.get("forwardEncoder");
        forwardEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //brake system
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        bottomLimitSwitch = hardwareMap.digitalChannel.get("bottomLimitSwitch");
        topLimitSwitch = hardwareMap.digitalChannel.get("topLimitSwitch");

        //initialize IMU in the REV module
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        pivot = hardwareMap.servo.get("pivot");

        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
        midServo = hardwareMap.servo.get("midServo");

        elevator = hardwareMap.dcMotor.get("elevator");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //intake = hardwareMap.dcMotor.get("intake");

        //  random variables
        bad = false;

        goldX = 0;
    }
}
