package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.AutonomousMethods;
import org.firstinspires.ftc.teamcode.RobotMethods;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class BasicRobotMethods implements RobotMethods{
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

public void test(){}

    @Override
        public void InitializeHardware (OpMode opMode) {
            HardwareMap hardwareMap=opMode.hardwareMap;

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
            boolean bad = false;

            int goldX = 0;

        }

        public void InitializeIMU () {
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
            parameters.loggingEnabled      = true;
            parameters.loggingTag          = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            imu.initialize(parameters);

            imu.startAccelerationIntegration(new Position(), new Velocity(), 250);

            lastAngle = new Orientation();

        }

    @Override
    public double GetIMUHeading() {
        Orientation angle = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ);
        double deltaAngle = angle.thirdAngle - lastAngle.thirdAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngle = angle;

        return globalAngle;

        //return -angles.thirdAngle;
    }

    @Override
    public void ResetEncoders() {

    }

    @Override
    public void StopMotor() {

    }

    @Override
    public void ForwardMove(double MotorPower) {

    }

    @Override
    public void ForwardMove(double MotorPower, int EncoerTarget) {

    }

    @Override
    public void ForwardMove(double MotorPower, int EncoderTarget, boolean stop) {

    }

    @Override
    public void BackwardMove(double MotorPower, int EncoderTarget, boolean stop) {

    }

    @Override
    public void ForwardMoveInches(double MotorPower, double Inches) {

    }

    @Override
    public void BackwardMoveInches(double MotorPower, double Inches) {

    }

    @Override
    public void BackwardMove(double MotorPower) {

    }

    @Override
    public void BackwardMove(double MotorPower, int EncoderTarget) {

    }

    @Override
    public void LeftMove(double MotorPower) {

    }

    @Override
    public void LeftMove(double MotorPower, int EncoderTarget) {

    }

    @Override
    public void RightMove(double MotorPower) {

    }

    @Override
    public void RightMove(double MotorPower, int EncoderTarget) {

    }

    @Override
    public void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2) {

    }

    @Override
    public void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2) {

    }

    @Override
    public void RotateLeftAngle(double MotorPower, double angleValue) {

    }

    @Override
    public void RotateRightSpecialAngle(double MotorPower, double angleValue) {

    }

    @Override
    public void RotateLeftSpecialAngle(double MotorPower, double angleValue) {

    }

    @Override
    public void moveElevatorDown(double speed) {

    }

    @Override
    public void moveElevatorUp(double speed) {

    }
}