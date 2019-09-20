package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public interface RobotMethods {
    void InitializeHardware(OpMode opMode);

    void InitializeIMU();

    double GetIMUHeading();

    void ResetEncoders();

    void StopMotors();

    double InchesToPulses(double Inches);

    double PulsesToInches(double Pulses);

    void ForwardMove(double MotorPower);

    void ForwardMove(double MotorPower, int EncoderTarget);

    void ForwardMove(double MotorPower, int EncoderTarget, boolean stop);

    void BackwardMove(double MotorPower, int EncoderTarget, boolean stop);

    void ForwardMoveInches(double MotorPower, double Inches);

    void BackwardMove(double MotorPower);

    void BackwardMove(double MotorPower, int EncoderTarget);

    void BackwardMoveInches(double MotorPower, double Inches);

    void LeftMove(double MotorPower);

    void LeftMove(double MotorPower, int EncoderTarget);

    void RightMove(double MotorPower);

    void RightMove(double MotorPower, int EncoderTarget);

    void RotateRightAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);

    void RotateRightShimmyAngle(double MotorPower, double angleValue, boolean strong, double k1, double k2);

    void RotateLeftAngle(double MotorPower, double angleValue);

    void RotateRightSpecialAngle(double MotorPower, double angleValue);

    void RotateLeftSpecialAngle(double MotorPower, double angleValue);

    void moveElevatorDown(double speed);

    void moveElevatorUp(double speed);

    void resetOdometry();

    void horizontalCorrection(double MotorPower, int EncoderTarget);

    void forwardCorrection(double MotorPower, int EncoderTarget);

    void forwardMoveOdometry(double MotorPower, int EncoderTarget);
}
	
