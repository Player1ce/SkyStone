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


public interface RobotMethods extends LinearOpMode{
    void InitializeHardware();
    void InitializeIMU();
    void GetIMUHeading();
    void ResetEncoders();
    void StopMotor();
    void ForwardMove(double MotorPower);
    void ForwardMove(double MotorPower, int EncoerTarget) throws InterruptedException;
    void ForwardMove(double MotorPower, int EncoderTarget, boolean stop) throws InterruptedException;
    void BackwardMove(double MotorPower, int EncoderTarget, boolean stop) throws InterruptedException;
    void ForwardMoveInches(double MotorPower, double Inches) throws InterruptedException;
    void BackwardMoveInches(double MotorPower, double Inches) throws InterruptedException;
    void BackwardMove(double MotorPower);
    void BackwardMove(double MotorPower, int EncoderTarget) throws InterruptedException;
    void LeftMove(double MotorPower);
    void LeftMove(double MotorPower, int EncoderTarget) throws InterruptedException;
    void RightMove(double MotorPower);
    void RightMove(double MotorPower, int EncoderTarget) throws InterruptedException;
    void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);
    void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);
    void RotateLeftAngle(double MotorPower, double angleValue);
    void RotateRightSpecialAngle(double MotorPower, double angleValue);
    void RotateLeftSpecialAngle(double MotorPower, double angleValue);
    void moveElevatorDown(double speed);
    void moveElevatorUp(double speed);
}
