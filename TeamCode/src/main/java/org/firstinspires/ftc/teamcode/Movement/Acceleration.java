package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.teamcode.AutonomousMethods;

public abstract class Acceleration extends AutonomousMethods {

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
}
