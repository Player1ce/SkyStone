package org.firstinspires.ftc.teamcode;


import android.app.Activity;
import android.view.View;

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

import java.util.Locale;


public interface RobotMethods {
    public void InitializeHardware();
    public void InitializeIMU();
    public void GetIMUHeading();
    public void ResetEncoders();
    public void StopMotor();
    public void ForwardMove(double MotorPower);
    public void ForwardMove(double MotorPower, int EncoerTarget) throws InterruptedException;
    public void ForwardMove(double MotorPower, int EncoderTarget, boolean stop) throws InterruptedException;
    public void BackwardMove(double MotorPower, int EncoderTarget, boolean stop) throws InterruptedException;
    public void ForwardMoveInches(double MotorPower, double Inches) throws InterruptedException;
    public void BackwardMoveInches(double MotorPower, double Inches) throws InterruptedException;
    public void BackwardMove(double MotorPower);
    public void BackwardMove(double MotorPower, int EncoderTarget) throws InterruptedException;
    public void LeftMove(double MotorPower);
    public void LeftMove(double MotorPower, int EncoderTarget) throws InterruptedException;
    public void RightMove(double MotorPower);
    public void RightMove(double MotorPower, int EncoderTarget) throws InterruptedException;
    public void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);
    public void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);
    public void RotateLeftAngle(double MotorPower, double angleValue);
    public void RotateRightSpecialAngle(double MotorPower, double angleValue);
    public void RotateLeftSpecialAngle(double MotorPower, double angleValue);
    public void moveElevatorDown(double speed);
    public void moveElevatorUp(double speed);






}
