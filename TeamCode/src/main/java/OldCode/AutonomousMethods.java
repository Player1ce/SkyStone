package OldCode;

import android.app.Activity;
import android.view.View;

import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Locale;

/* error: caused on port to new version
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
*/


/**
 * MASTER CODE WITH ALL AUTONOMOUS METHODS. ALL AUTONOMOUS CLASSES EXTEND FROM THIS CLASS TO ACCESS ALL OF THESE METHODS< OBJECTS< AND VARIABLES
 *
 */

//@Disabled
public abstract class AutonomousMethods extends LinearOpMode {

    //sets variables
    //sup nerd

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

    public Servo pivot;

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


    /* error: caused on port to new version
    GoldAlignDetector detector;
    */

    double goldX;

    //vuforia
    public void InitializeVuforia(VuforiaLocalizer v) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AaOaI0//////AAAAGc/200WiAU6BmIPzX3Yfy+A2k2NuO+ZAc/nSckX2kd6deUr1UZRfkv81fdpOcksh+ZvmH+PbybnGWkYyw67YwLohi5XkD5eu8ODgQ27bEWdjc39zNRpKbT/GVh+tiyCgYUmMEOcCQCW1cO13bMmlwmWtZKnJc5so+zN3Q0e1Nfa6jpFjjaqlbplrxF2U+Ol2daq6cvIrhO6d+AMByFLHplH4OxP2yV3s+AzoCrt7FdNn3VOAwYe3bUYyHTqmYvpwzJTAj4zCbW3lYSh4KyXWGyNVi6RDoBJaDoK87GgoaRgBibd1q4s8gd9gFZ7zANcJY2XGW2dwVywRVB7ak5APiaZ3bSlakPnjZmrOtKrREoxD";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        v = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = v.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        // can help in debugging; otherwise not necessary
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float[] hsvValues = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float[] values = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
    }



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

    public void startTime() {
        startTime = System.currentTimeMillis();
    }


    /* error: caused on port to new version
    public void InitializeDetector() {
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        //Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA

        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!
    }
    */


    public void InitializeIMU(){


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

    //end of initialization----------------------------------------------------------------------------------------------------------------------

    //get angle value from imu
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


    public void resetAngle() {
        lastAngle   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.XYZ);
        globalAngle = 0;
    }


    public Position GetIMUPosition(){


        return imu.getPosition();
            }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }


    //Basic auto ovement -----------------------------------------------------------------------------------------------------

    //reset encoder values
    public void ResetEncoders() throws InterruptedException {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void StopMotors(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }


        public void AccelerateForward ( double StartPower, double MotorPower, double AccFactor)  throws
        InterruptedException {
        while (StartPower < MotorPower) {
            StartPower += AccFactor;
            ForwardMove(StartPower);
        }

    }
        public void AccelerateBackward ( double StartPower, double MotorPower, double AccFactor)  throws
        InterruptedException {
        while (StartPower < MotorPower) {
            StartPower += AccFactor;
            BackwardMove(StartPower);
        }

    }
        public void AccelerateLeft ( double StartPower, double MotorPower, double AccFactor)  throws
        InterruptedException {
        while (StartPower < MotorPower) {
            StartPower += AccFactor;
            LeftMove(StartPower);
        }

    }
        public void AccelerateRight ( double StartPower, double MotorPower, double AccFactor)  throws
        InterruptedException {
        while (StartPower < MotorPower) {
            StartPower += AccFactor;
            RightMove(StartPower);
        }

    }
        public void DecelerateForward ( double MotorPower, double DecFactor)  throws
        InterruptedException {
        while (MotorPower > 0.0) {
            MotorPower -= DecFactor;
            ForwardMove(MotorPower);
        }
        StopMotors();
    }

        public void DecelerateBackward ( double MotorPower, double DecFactor)  throws
        InterruptedException {
        while (MotorPower > 0.0) {
            MotorPower -= DecFactor;
            BackwardMove(MotorPower);
        }
        StopMotors();
    }
        public void DecelerateLeft ( double MotorPower, double DecFactor)  throws
        InterruptedException {
        while (MotorPower > 0.0) {
            MotorPower -= DecFactor;
            LeftMove(MotorPower);
        }
        StopMotors();
    }
        public void DecelerateRight ( double MotorPower, double DecFactor)  throws
        InterruptedException {
        while (MotorPower > 0.0) {
            MotorPower -= DecFactor;
            RightMove(MotorPower);
        }
        StopMotors();
    }

    public void ForwardMove(double MotorPower){
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);
    }

    public void ForwardMove(double MotorPower, int EncoderTarget) throws InterruptedException {

        ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(backRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            telemetry.addData("Moving Forward","Moving Forward");
            telemetry.addData("encoder value:", backRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }
        StopMotors();

    }

    public void ForwardMoveLeft(double MotorPower, int EncoderTarget) throws InterruptedException {

        ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()) {
            telemetry.addData("Moving Forward", "Moving Forward");
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }
        StopMotors();

    }

    public void ForwardMove(double MotorPower, int EncoderTarget, boolean stop) throws InterruptedException {

        ResetEncoders();
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            telemetry.addData("Moving Forward","Moving Forward");
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }
        if (stop) StopMotors();
    }

    public void BackwardMove(double MotorPower, int EncoderTarget, boolean stop) throws InterruptedException {

        ResetEncoders();
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            telemetry.addData("Moving Backward","Moving Backward");
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }
        if (stop) StopMotors();
    }
    /********
    //convert encoder targets to inches and base movement from inches
     *******/
    public void ForwardMoveInches(double MotorPower, double Inches) throws InterruptedException {

        ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < InchesToPulses(Inches) && opModeIsActive()){
            telemetry.addData("Moving Forward","Moving Forward");
            telemetry.addData("encoder value:", PulsesToInches(frontRight.getCurrentPosition()));
            telemetry.addData("encoder target:", Inches);
            telemetry.update();
        }

        StopMotors();

    }

    //inches cont.
    public void BackwardMoveInches(double MotorPower, double Inches) throws InterruptedException {

        ResetEncoders();

        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < InchesToPulses(Inches) && opModeIsActive()){
            telemetry.addData("Moving Forward","Moving Forward");
            telemetry.addData("encoder value:", PulsesToInches(frontRight.getCurrentPosition()));
            telemetry.addData("encoder target:", Inches);
            telemetry.update();
        }

        StopMotors();

    }

    public void BackwardMove(double MotorPower){
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);
    }

    public void BackwardMove(double MotorPower, int EncoderTarget) throws InterruptedException {

        ResetEncoders();

        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);

        while (Math.abs(backRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            telemetry.addData("Moving Backward","Moving Backward");
            telemetry.addData("encoder value:", backRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }

        StopMotors();

    }

    //Basic controlled move ----------------------------------------------------------------------------------------------

    public void LeftMoveControlled(double basePower, int EncoderTarget) throws InterruptedException {
        double heading;
        double MotorLeft;
        double MotorRight;
        double BasePower = basePower;
        double Kp = 0.05;
        ResetEncoders();
        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
        heading = -GetIMUHeading();
        if (heading > 180) {
            heading = heading - 360;
        }

        MotorLeft = BasePower - (Kp*heading);
        MotorRight = BasePower + (Kp*heading);

        frontRight.setPower(MotorRight * .8);
        backRight.setPower(-MotorRight);
        frontLeft.setPower(-MotorLeft);
        backLeft.setPower(MotorLeft * .8);

            telemetry.addData("Moving Left", "Moving Left");
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }

        StopMotors();
    }

    public void RightMoveControlled(double basePower, int EncoderTarget) throws InterruptedException {
        double heading;
        double MotorLeft;
        double MotorRight;
        double BasePower = basePower;
        double Kp = 0.05;
        ResetEncoders();
        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            heading = -GetIMUHeading();
            if (heading > 180) {
                heading = heading - 360;
            }

            telemetry.addData("x position: ", "%.3f ", GetIMUPosition().x);
            telemetry.addData("y position: ", GetIMUPosition().y);
            telemetry.addData("z position: ", GetIMUPosition().z);
            telemetry.addData("Sensor Distance", rangeSensor.rawUltrasonic());
            telemetry.update();

            //other way around
            MotorRight = BasePower - (Kp*heading);
            MotorLeft = BasePower + (Kp*heading);

            frontRight.setPower(-MotorRight);
            backRight.setPower(MotorRight);
            frontLeft.setPower(MotorLeft);
            backLeft.setPower(-MotorLeft);
        }

        StopMotors();
    }

    public void LeftMove(double MotorPower){
        frontRight.setPower(MotorPower);
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(MotorPower);

    }

    public void LeftMove(double MotorPower, int EncoderTarget) throws InterruptedException {

        ResetEncoders();
        frontRight.setPower(MotorPower);
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(MotorPower);


        while (Math.abs(backLeft.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            telemetry.addData("Moving Left","Moving Left");
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }

        StopMotors();

    }

    public void RightMove(double MotorPower){
        frontRight.setPower(-MotorPower);
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(-MotorPower);

    }

    public void RightMove(double MotorPower, int EncoderTarget) throws InterruptedException {

        ResetEncoders();
        frontRight.setPower(-MotorPower);
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(-MotorPower);


        while (Math.abs(backRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            telemetry.addData("Moving Right","Moving Right");
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }

        StopMotors();

    }

    public void SpinLeft(double MotorPower, int angle){
        backRight.setPower(MotorPower);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(MotorPower);

        while (Math.abs(gyro.getIntegratedZValue()) < angle && opModeIsActive()) {
            telemetry.addData("Spinning Left","Spinning Left");
            telemetry.addData("angle value:", Math.abs(gyro.getIntegratedZValue()));
            telemetry.addData("angle target:", angle);
            telemetry.update();
        }

        StopMotors();
    }

    public void SpinRight(double MotorPower, int angle){
        //some code here
    }

    public void RotateLeft(double MotorPower){
        backRight.setPower(MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(MotorPower);
    }

    public void RotateRight(double MotorPower){

        backRight.setPower(-MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(-MotorPower);
    }

    public void RotateRight(double MotorPower, int EncoderTarget){

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRight.setPower(-MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(-MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < EncoderTarget && opModeIsActive()){
            telemetry.addData("Rotating Right","Moving Right");
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
        }

        StopMotors();
    }

    //use angle measurement to turn
    //k1 and k2 constant parameters are used to give more power to either side of motors
    public void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while (Math.abs(GetIMUHeading()) < angleValue && opModeIsActive()) {
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
                telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
                telemetry.update();
                //time check to see if moving, if has not changed more than .9 degrees in 1.5 seconds, move a bit to readjust
                if (System.currentTimeMillis() - startTime >= 1500) {
                    startTime = System.currentTimeMillis();
                    if (!(Math.abs(GetIMUHeading()) > lastAngle + .9)) {
                        BackwardMove(.5);
                        sleep(350);
                        StopMotors();
                    }
                    lastAngle = Math.abs(GetIMUHeading());
                }

        }
            StopMotors();
    }

    //disregarded
    public void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2) {
        while (Math.abs(GetIMUHeading()) < angleValue && opModeIsActive()) {
            while (Math.abs(GetIMUHeading()) < 32 && opModeIsActive()) {
                double angle = Math.abs(GetIMUHeading());
                while (Math.abs(GetIMUHeading()) < angle + 4 && opModeIsActive()) {
                    backRight.setPower(-MotorPower);
                    frontRight.setPower(-MotorPower);
                    frontLeft.setPower(MotorPower);
                    backLeft.setPower(MotorPower);
                }
                BackwardMove(.5);
                sleep(300);
                StopMotors();
            }

            backRight.setPower(-MotorPower);
            frontRight.setPower(-MotorPower);
            frontLeft.setPower(MotorPower);
            backLeft.setPower(MotorPower);

            telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            telemetry.update();
        }
        StopMotors();
        }

    public void RotateRightToZero(double MotorPower) {
        while (Math.abs(GetIMUHeading()) > 4 && opModeIsActive()) {
            backRight.setPower(-MotorPower);
            frontLeft.setPower(MotorPower);
            backLeft.setPower(MotorPower);
            frontRight.setPower(-MotorPower);
            telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            telemetry.update();

        }
        StopMotors();
    }

    public void RotateLeftToZero(double MotorPower) {
        while (Math.abs(GetIMUHeading()) > 4 && opModeIsActive()) {
            backRight.setPower(MotorPower);
            frontLeft.setPower(-MotorPower);
            backLeft.setPower(-MotorPower);
            frontRight.setPower(MotorPower);
            telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            telemetry.update();

        }
        StopMotors();
    }
    //use angle measurement to turn
    public void RotateLeftAngle(double MotorPower, double angleValue) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while ((Math.abs(GetIMUHeading())) > angleValue && opModeIsActive()) {
                backRight.setPower(MotorPower);
                frontLeft.setPower(-MotorPower);
                backLeft.setPower(-MotorPower);
                frontRight.setPower(MotorPower);
                telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
                telemetry.update();
                if (System.currentTimeMillis() - startTime >= 1500) {
                    startTime = System.currentTimeMillis();
                    if (!(Math.abs(GetIMUHeading()) < lastAngle - .9)) {
                            ForwardMove(.5);
                            sleep(350);
                            StopMotors();
                    }
                    lastAngle = Math.abs(GetIMUHeading());
            }
        }
        StopMotors();
    }

    public void RotateRightSpecialAngle(double MotorPower, double angleValue) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while (GetIMUHeading() > angleValue && opModeIsActive()) {
                backRight.setPower(-MotorPower);
                frontRight.setPower(-MotorPower);
                frontLeft.setPower(MotorPower);
                backLeft.setPower(MotorPower);
            telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            telemetry.update();
            if (System.currentTimeMillis() - startTime >= 1500) {
                startTime = System.currentTimeMillis();
                if (!(Math.abs(GetIMUHeading()) > lastAngle + .9)) {
                    BackwardMove(.5);
                    sleep(300);
                    StopMotors();
                }
                lastAngle = Math.abs(GetIMUHeading());
            }

        }
        StopMotors();
    }

    public void RotateLeftSpecialAngle(double MotorPower, double angleValue) {
        // while ((Math.abs(GetIMUHeading())) > angleValue) {
        double startTime = System.currentTimeMillis();
        double lastAngle = 0;
        while (GetIMUHeading() < angleValue && opModeIsActive()) {
            backRight.setPower(MotorPower);
            frontLeft.setPower(-MotorPower);
            backLeft.setPower(-MotorPower);
            frontRight.setPower(MotorPower);
            telemetry.addData("Heading: ", Math.abs(GetIMUHeading()));
            telemetry.update();
            //time check to see if moving, if has not changed more than .9 degrees in 1.5 seconds, move a bit to readjust
            if (System.currentTimeMillis() - startTime >= 1500) {
                startTime = System.currentTimeMillis();
                if (!(Math.abs(GetIMUHeading()) < lastAngle - .9)) {
                    ForwardMove(.6);
                    sleep(100);
                    StopMotors();
                }
                lastAngle = Math.abs(GetIMUHeading());
            }
        }
        StopMotors();
    }

    //2018 ROVER RUCKUS METHODS

    public void moveForwardCoordinate(int x, int y, double MotorPower) {

        double distance = Math.sqrt((x * x) + (y * y));

        double angle = Math.toDegrees(Math.atan(x / y));

        telemetry.addData("Angle: ", angle);
        telemetry.addData("calc: ",Math.atan(x / y));
        telemetry.addData("Gyro: ", Math.abs(GetIMUHeading()));
        telemetry.update();

        sleep(500);

        RotateRightAngle(.3, angle, false, 0, 0);

        sleep(500);

        //RESET ENCODERS

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(10);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //END RESET ENCODERS

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(frontRight.getCurrentPosition()) < InchesToPulses(distance) && opModeIsActive()){
            telemetry.addData("Inches:",distance);
            telemetry.addData("pulses:", InchesToPulses(distance));
            telemetry.addData("encoder:", frontRight.getCurrentPosition());
            telemetry.update();
        }

        StopMotors();

        sleep(1000);
    }

    // End movement -----------------------------------------------------------------------------------------------------------

    //elevator methods --------------------------------------------------------------------------------------------------------
    //moves elevator down. in turn moves robot up if hanging
    public void moveElevatorDown(double speed) {
        while (bottomLimitSwitch.getState() && opModeIsActive()) {
            elevator.setPower(-speed);
        }
        elevator.setPower(0);
    }

    //moves elevator up. in turn moves robot down if hanging
    public void moveElevatorUp(double speed) {
        while (topLimitSwitch.getState() && opModeIsActive()) {
            elevator.setPower(speed);
        }
        elevator.setPower(0);
    }

    //moves elevator down based on time. in turn moves robot up if hanging
    public void timeElevatorDown(double speed, double time) {
        double startTime = System.currentTimeMillis();
        while (bottomLimitSwitch.getState() && opModeIsActive() && Math.abs(System.currentTimeMillis() - startTime) < time) {
            elevator.setPower(-speed);
        }
        elevator.setPower(0);
    }

    //moves elevator up based on time. in turn moves robot down if hanging
    public void timeElevatorDUp(double speed, double time) {
        double startTime = System.currentTimeMillis();
        while (topLimitSwitch.getState() && opModeIsActive() && Math.abs(System.currentTimeMillis() - startTime) < time) {
            elevator.setPower(speed);
        }
        elevator.setPower(0);
    }
    //elevator methods end

    //deprecated conversions
    public double InchesToPulses(double Inches){

        double PulsesPerInch = NR40_PPR * DRIVE_WHEEL_GEAR_RATIO / (WHEEL_DIAMETER * Math.PI);

        return Inches * PulsesPerInch;

    } // TODO THIS NEEDS TO BE VERIFIED!!!

    public double PulsesToInches(double Pulses){

        double PulsesPerInch = NR40_PPR * DRIVE_WHEEL_GEAR_RATIO / (WHEEL_DIAMETER * Math.PI);

        return Pulses / PulsesPerInch;

    } // TODO THIS NEEDS TO BE VERIFIED!!!


    // beginning odometry -------------------------------------------------------------------------------------------------

    // odometry methods:
    // TODO variables initiated locally for now will be moved to top later.
    //make private?
    int forwardLeeway = 15;
    int horizonatalLeeway = 15;


    public void ResetOdometry() throws InterruptedException {
        forwardEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

        forwardEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void HorizontalCorrection(double MotorPower, int EncoderTarget) {
        int error = Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition());

        while (error > horizonatalLeeway) {
            if ((EncoderTarget - horizontalEncoder.getCurrentPosition()) < 0) {
                //DriveLeft(double MotorPower, int EncoderTarget);
            }
            if ((EncoderTarget - horizontalEncoder.getCurrentPosition()) > 0) {
                //DriveRight(double MotorPower, int EncoderTarget);
            }

        }
        StopMotors();

    }


    public void ForwardCorrection(double MotorPower, int EncoderTarget) {
        int error = Math.abs(EncoderTarget - forwardEncoder.getCurrentPosition());

        while (error > forwardLeeway) {
            if ((EncoderTarget - forwardEncoder.getCurrentPosition()) < 0) {
                //DriveBackward(double MotorPower, int EncoderTarget);
            }
            if ((EncoderTarget - forwardEncoder.getCurrentPosition()) > 0) {
                //DriveForward(double MotorPower, int EncoderTarget);
            }

        }
        StopMotors();

    }


    public void ForwardMoveOdometry(double MotorPower, int EncoderTarget) throws InterruptedException {
        ResetEncoders();
        ResetOdometry();
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);
        String running = "Normal";
        boolean offPoint = true;

        while ((offPoint) && opModeIsActive()) {
            telemetry.addData("running: ", running);
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
            int measure = Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition());


            if (forwardEncoder.getCurrentPosition() < EncoderTarget) {
                //DriveForward(double MotorPower, int EncoderTarget);
            }
            if (forwardEncoder.getCurrentPosition() > EncoderTarget) {
                ForwardCorrection(MotorPower, EncoderTarget);
            }
            if (measure > horizonatalLeeway) {
                HorizontalCorrection(MotorPower, EncoderTarget);
            }
            //check position
            if ((Math.abs(EncoderTarget - forwardEncoder.getCurrentPosition()) < forwardLeeway) && (Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition()) < horizonatalLeeway)) {
                // stops loop
                offPoint = false;
            }

        }
        StopMotors();
    }

}














