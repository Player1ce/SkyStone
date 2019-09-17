package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.hardware.adafruit.BNO055IMU;
//import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;


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
