package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
import org.firstinspires.ftc.teamcode.AutonomousMethods;

import java.util.Locale;


//@Disabled
public abstract class ForwardMove extends AutonomousMethods {
    //stores Autonomous movement methods

    //Basic auto movement -----------------------------------------------------------------------------------------------------

    //reset encoder values

    //acceleration ------------------------------------------------------------------------------------

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

    //basic move forward and backward -------------------------------------------------------------------------------------

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

    // move inches ---------------------------------------------------------------------------------


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


    //basic move left and right ---------------------------------------------------------------------


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


    //controlled move left and right ----------------------------------------------------------------------------------------------

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


    //spin --------------------------------------------------------------------------
    // TODO complete spin?

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

    // rotate --------------------------------------------------------------------------------------

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


    //Angle measure turn ---------------------------------------------------------------------------
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



}
