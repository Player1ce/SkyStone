package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.teamcode.AutonomousMethods;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class MoveInches extends AutonomousMethods {

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


}
