package org.firstinspires.ftc.useless;

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

//TODO: make inches to pulses conversions accurate
public abstract class BasicRobotMethods implements RobotMethods{
    LinearOpMode linearOpMode;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor horizontalEncoder;
    DcMotor forwardEncoder;
    Servo hookServo;
    BNO055IMU imu;
    ModernRoboticsI2cGyro gyro;
    ModernRoboticsI2cRangeSensor rangeSensor;
    ColorSensor colorSensor;

    Servo pivot;

    Servo leftServo;
    Servo rightServo;
    Servo midServo;

    Orientation lastAngle;
    double globalAngle;

    DigitalChannel bottomLimitSwitch;
    DigitalChannel topLimitSwitch;

    final double WHEEL_DIAMETER = 6;
    final int NR40_PPR = 1120;
    final double DRIVE_WHEEL_GEAR_RATIO = 1;

    long startTime;

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

        //initialize hook system
        hookServo = hardwareMap.servo.get("hookServo");

        //brake system
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        bottomLimitSwitch = hardwareMap.digitalChannel.get("bottomLimitSwitch");
        topLimitSwitch = hardwareMap.digitalChannel.get("topLimitSwitch");

        //initialize IMUOnBot in the REV module
        imu = hardwareMap.get(BNO055IMU.class, "imu");
    }


    @Override
    public void InitializeIMU () {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMUOnBot";
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
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        linearOpMode.sleep(50);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void StopMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

    }

    @Override
    public double InchesToPulses(double Inches){

        double PulsesPerInch = NR40_PPR * DRIVE_WHEEL_GEAR_RATIO / (WHEEL_DIAMETER * Math.PI);

        return Inches * PulsesPerInch;

    } // TODO THIS NEEDS TO BE VERIFIED!!!

    @Override
    public double PulsesToInches(double Pulses){

        double PulsesPerInch = NR40_PPR * DRIVE_WHEEL_GEAR_RATIO / (WHEEL_DIAMETER * Math.PI);

        return Pulses / PulsesPerInch;

    } // TODO THIS NEEDS TO BE VERIFIED!!!

    @Override
    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void ForwardMove(double MotorPower) {
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);
    }


    @Override
    public void ForwardMove(double MotorPower, int EncoderTarget) {
        ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(backRight.getCurrentPosition()) < EncoderTarget && linearOpMode.opModeIsActive()){
            linearOpMode.telemetry.addData("Moving Forward","Moving Forward");
            linearOpMode.telemetry.addData("encoder value:", backRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
        }
        StopMotors();

    }


    @Override
    public void ForwardMove(double MotorPower, int EncoderTarget, boolean stop) {
        ResetEncoders();
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && linearOpMode.opModeIsActive()){
            linearOpMode.telemetry.addData("Moving Forward","Moving Forward");
            linearOpMode.telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
        }
        if (stop) StopMotors();
    }


    @Override
    public void BackwardMove(double MotorPower, int EncoderTarget, boolean stop) {
        ResetEncoders();
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && linearOpMode.opModeIsActive()) {
            linearOpMode.telemetry.addData("Moving Backward", "Moving Backward");
            linearOpMode.telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
        }
        if (stop) StopMotors();
    }


    @Override
    public void ForwardMoveInches(double MotorPower, double Inches) {
        ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < InchesToPulses(Inches) && linearOpMode.opModeIsActive()){
            linearOpMode.telemetry.addData("Moving Forward","Moving Forward");
            linearOpMode.telemetry.addData("encoder value:", PulsesToInches(frontRight.getCurrentPosition()));
            linearOpMode.telemetry.addData("encoder target:", Inches);
            linearOpMode.telemetry.update();
        }

        StopMotors();
    }


    @Override
    public void BackwardMove(double MotorPower) {
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);
    }


    @Override
    public void BackwardMove(double MotorPower, int EncoderTarget) {
        ResetEncoders();

        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);

        while (Math.abs(backRight.getCurrentPosition()) < EncoderTarget && linearOpMode.opModeIsActive()){
            linearOpMode.telemetry.addData("Moving Backward","Moving Backward");
            linearOpMode.telemetry.addData("encoder value:", backRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
        }

        StopMotors();

    }


    @Override
    public void BackwardMoveInches(double MotorPower, double Inches) {
        ResetEncoders();

        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < InchesToPulses(Inches) && linearOpMode.opModeIsActive()){
            linearOpMode.telemetry.addData("Moving Forward","Moving Forward");
            linearOpMode.telemetry.addData("encoder value:", PulsesToInches(frontRight.getCurrentPosition()));
            linearOpMode.telemetry.addData("encoder target:", Inches);
            linearOpMode.telemetry.update();
        }

        StopMotors();


    }


    @Override
    public void LeftMove(double MotorPower) {
        frontRight.setPower(MotorPower);
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(MotorPower);
    }


    @Override
    public void LeftMove(double MotorPower, int EncoderTarget) {
        ResetEncoders();
        frontRight.setPower(MotorPower);
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(MotorPower);


        while (Math.abs(backLeft.getCurrentPosition()) < EncoderTarget && linearOpMode.opModeIsActive()){
            linearOpMode.telemetry.addData("Moving Left","Moving Left");
            linearOpMode.telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
        }

        StopMotors();

    }


    @Override
    public void RightMove(double MotorPower) {
        frontRight.setPower(-MotorPower);
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(-MotorPower);
    }


    @Override
    public void RightMove(double MotorPower, int EncoderTarget) {
        ResetEncoders();
        frontRight.setPower(-MotorPower);
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(-MotorPower);


        while (Math.abs(backRight.getCurrentPosition()) < EncoderTarget && linearOpMode.opModeIsActive()){
            linearOpMode.telemetry.addData("Moving Right","Moving Right");
            linearOpMode.telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
        }

        StopMotors();
    }


    @Override
    public void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while (Math.abs(GetIMUHeading()) < angleValue && linearOpMode.opModeIsActive()) {
            if (strong) {
                backRight.setPower(-MotorPower * k1);
                frontRight.setPower(-MotorPower * k1);
                frontLeft.setPower(MotorPower * k2);
                backLeft.setPower(MotorPower * k2);
            } else {
                backRight.setPower(-MotorPower);
                frontRight.setPower(-MotorPower);
                frontLeft.setPower(MotorPower);
                backLeft.setPower(MotorPower);
            }
            linearOpMode.telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            linearOpMode.telemetry.update();
            //time check to see if moving, if has not changed more than .9 degrees in 1.5 seconds, move a bit to readjust
            if (System.currentTimeMillis() - startTime >= 1500) {
                startTime = System.currentTimeMillis();
                if (!(Math.abs(GetIMUHeading()) > lastAngle + .9)) {
                    BackwardMove(.5);
                    linearOpMode.sleep(350);
                    StopMotors();
                }
                lastAngle = Math.abs(GetIMUHeading());
            }

        }
        StopMotors();
    }


    @Override
    public void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2) {

    }


    @Override
    public void RotateLeftAngle(double MotorPower, double angleValue) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while ((Math.abs(GetIMUHeading())) > angleValue && linearOpMode.opModeIsActive()) {
            backRight.setPower(MotorPower);
            frontLeft.setPower(-MotorPower);
            backLeft.setPower(-MotorPower);
            frontRight.setPower(MotorPower);
            linearOpMode.telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            linearOpMode.telemetry.update();
            if (System.currentTimeMillis() - startTime >= 1500) {
                startTime = System.currentTimeMillis();
                if (!(Math.abs(GetIMUHeading()) < lastAngle - .9)) {
                    ForwardMove(.5);
                    linearOpMode.sleep(350);
                    StopMotors();
                }
                lastAngle = Math.abs(GetIMUHeading());
            }
        }
        StopMotors();
    }


    @Override
    public void RotateRightSpecialAngle(double MotorPower, double angleValue) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while (GetIMUHeading() > angleValue && linearOpMode.opModeIsActive()) {
            backRight.setPower(-MotorPower);
            frontRight.setPower(-MotorPower);
            frontLeft.setPower(MotorPower);
            backLeft.setPower(MotorPower);
            linearOpMode.telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            linearOpMode.telemetry.update();
            if (System.currentTimeMillis() - startTime >= 1500) {
                startTime = System.currentTimeMillis();
                if (!(Math.abs(GetIMUHeading()) > lastAngle + .9)) {
                    BackwardMove(.5);
                    linearOpMode.sleep(300);
                    StopMotors();
                }
                lastAngle = Math.abs(GetIMUHeading());
            }

        }
        StopMotors();
    }


    @Override
    public void RotateLeftSpecialAngle(double MotorPower, double angleValue) {
// while ((Math.abs(GetIMUHeading())) > angleValue) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while (GetIMUHeading() < angleValue && linearOpMode.opModeIsActive()) {
            backRight.setPower(MotorPower);
            frontLeft.setPower(-MotorPower);
            backLeft.setPower(-MotorPower);
            frontRight.setPower(MotorPower);
            linearOpMode.telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            linearOpMode.telemetry.update();
            //time check to see if moving, if has not changed more than .9 degrees in 1.5 seconds, move a bit to readjust
            if (System.currentTimeMillis() - startTime >= 1500) {
                startTime = System.currentTimeMillis();
                if (!(Math.abs(GetIMUHeading()) < lastAngle - .9)) {
                    ForwardMove(.6);
                    linearOpMode.sleep(100);
                    StopMotors();
                }
                lastAngle = Math.abs(GetIMUHeading());
            }
        }
        StopMotors();
    }

	@Override
	public void resetOdometry() {

	}

	@Override
	public void horizontalCorrection(double MotorPower, int EncoderTarget){
		
	}
	
	@Override
	public void forwardCorrection(double MotorPower, int EncoderTarget){
		
	}

	@Override
	public void forwardMoveOdometry(double MotorPower, int EncoderTarget){
		
	}

	@Override
    public void backwardMoveOdometry(double MotorPower, int EncoderTarget) {

    }

    @Override
    public void moveHook(String position) {

    }

    @Override
    public void setPower (double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
    }

    @Override
    public void setPowerVars(Gamepad gamepad1, boolean reverse) {

    }

    @Override
    public String reverseSense(boolean reverse) {
        return "false";
    }


}
