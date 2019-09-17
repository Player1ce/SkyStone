package org.firstinspires.ftc.teamcode;


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


public interface RobotMethods {
    void InitializeHardware(OpMode opMode);
    void InitializeIMU();
    double GetIMUHeading();
    void ResetEncoders();
    void StopMotor();
    void ForwardMove(double MotorPower);
    void ForwardMove(double MotorPower, int EncoerTarget) ;
    void ForwardMove(double MotorPower, int EncoderTarget, boolean stop) ;
    void BackwardMove(double MotorPower, int EncoderTarget, boolean stop) ;
    void ForwardMoveInches(double MotorPower, double Inches) ;
    void BackwardMoveInches(double MotorPower, double Inches) ;
    void BackwardMove(double MotorPower);
    void BackwardMove(double MotorPower, int EncoderTarget) ;
    void LeftMove(double MotorPower);
    void LeftMove(double MotorPower, int EncoderTarget) ;
    void RightMove(double MotorPower);
    void RightMove(double MotorPower, int EncoderTarget) ;
    void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);
    void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);
    void RotateLeftAngle(double MotorPower, double angleValue);
    void RotateRightSpecialAngle(double MotorPower, double angleValue);
    void RotateLeftSpecialAngle(double MotorPower, double angleValue);
    void moveElevatorDown(double speed);
    void moveElevatorUp(double speed);
}
