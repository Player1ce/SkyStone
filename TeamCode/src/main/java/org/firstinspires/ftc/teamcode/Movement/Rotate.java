package org.firstinspires.ftc.teamcode.Movement;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutonomousMethods;

public abstract class Rotate extends AutonomousMethods {


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
