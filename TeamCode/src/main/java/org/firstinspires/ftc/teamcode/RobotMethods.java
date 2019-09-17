package org.firstinspires.ftc.teamcode;


import android.app.Activity;
import android.graphics.Path;
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
    void ResetEncoders(LinearOpMode linearOpMode);
    void StopMotors();
    double InchesToPulses(double Inches);
    double PulsesToInches(double Pulses);
    void ForwardMove(double MotorPower);
    void ForwardMove(double MotorPower, int EncoderTarget, LinearOpMode linearOpMode);
    void ForwardMove(double MotorPower, int EncoderTarget, boolean stop, LinearOpMode linearOpMode);
    void BackwardMove(double MotorPower, int EncoderTarget, boolean stop, LinearOpMode linearOpMode);
    void ForwardMoveInches(double MotorPower, double Inches, LinearOpMode linearOpMode);
    void BackwardMove(double MotorPower);
    void BackwardMove(double MotorPower, int EncoderTarget, LinearOpMode linearOpMode);
    void BackwardMoveInches(double MotorPower, double Inches, LinearOpMode linearOpMode);
    void LeftMove(double MotorPower);
    void LeftMove(double MotorPower, int EncoderTarget, LinearOpMode linearOpMode);
    void RightMove(double MotorPower);
    void RightMove(double MotorPower, int EncoderTarget, LinearOpMode linearOpMode);
    void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2, LinearOpMode linearOpMode);
    void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);
    void RotateLeftAngle(double MotorPower, double angleValue, LinearOpMode linearOpMode);
    void RotateRightSpecialAngle(double MotorPower, double angleValue, LinearOpMode linearOpMode);
    void RotateLeftSpecialAngle(double MotorPower, double angleValue, LinearOpMode linearOpMode);
    void moveElevatorDown(double speed, LinearOpMode linearOpMode);
    void moveElevatorUp(double speed, LinearOpMode linearOpMode);
}
