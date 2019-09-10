package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.teamcode.AutonomousMethods;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class BasicMovement extends AutonomousMethods {

    //basic move forward and backward --------------------------------------------------------------
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


}
