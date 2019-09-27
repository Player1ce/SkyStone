package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class OdometryMethods extends BasicRobotMethods {
	DcMotor horizontalEncoder;
    DcMotor forwardEncoder;
    LinearOpMode linearOpMode;
	
	int forwardLeeway = 15;
    int horizonatalLeeway = 15;


	
	@Override
	public void resetOdometry() {
		forwardEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        linearOpMode.sleep(50);

        forwardEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
	
	@Override
	public void horizontalCorrection(double MotorPower, int EncoderTarget){
	int error = Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition());

        while (error > horizonatalLeeway) {
            if ((EncoderTarget - horizontalEncoder.getCurrentPosition()) < 0) {
                LeftMove(MotorPower, EncoderTarget);
            }
            if ((EncoderTarget - horizontalEncoder.getCurrentPosition()) > 0) {
                RightMove(MotorPower, EncoderTarget);
            }

        }
        StopMotors();

	}
	
	@Override
	public void forwardCorrection(double MotorPower, int EncoderTarget){
		 int error = Math.abs(EncoderTarget - forwardEncoder.getCurrentPosition());

        while (error > forwardLeeway) {
            if ((EncoderTarget - forwardEncoder.getCurrentPosition()) < 0) {
                BackwardMove(MotorPower, EncoderTarget);
            }
            if ((EncoderTarget - forwardEncoder.getCurrentPosition()) > 0) {
                ForwardMove(MotorPower,EncoderTarget);
            }

        }
        StopMotors();

	}
	
	@Override
	public void forwardMoveOdometry(double MotorPower, int EncoderTarget){
	    ResetEncoders();
        resetOdometry();
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);
        String running = "Normal";
        boolean offPoint = true;

        while ((offPoint) && linearOpMode.opModeIsActive()) {
            linearOpMode.telemetry.addData("running: ", running);
            linearOpMode.telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
            int measure = Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition());


            if (forwardEncoder.getCurrentPosition() < EncoderTarget) {
                ForwardMove(MotorPower, EncoderTarget);
            }
            if (forwardEncoder.getCurrentPosition() > EncoderTarget) {
                forwardCorrection(MotorPower, EncoderTarget);
            }
            if (measure > horizonatalLeeway) {
                horizontalCorrection(MotorPower, EncoderTarget);
            }
            //check position
            if ((Math.abs(EncoderTarget - forwardEncoder.getCurrentPosition()) < forwardLeeway) && (Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition()) < horizonatalLeeway)) {
                // stops loop
                offPoint = false;
            }

        }
        StopMotors();
    }

    public void backwardMoveOdometry(double MotorPower, int EncoderTarget) {
        ResetEncoders();
        resetOdometry();
        backRight.setPower(-MotorPower);
        frontLeft.setPower(-MotorPower);
        backLeft.setPower(-MotorPower);
        frontRight.setPower(-MotorPower);
        String running = "Normal";
        boolean offPoint = true;

        while ((offPoint) && linearOpMode.opModeIsActive()) {
            linearOpMode.telemetry.addData("running: ", running);
            linearOpMode.telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            linearOpMode.telemetry.addData("encoder target:", EncoderTarget);
            linearOpMode.telemetry.update();
            int measure = Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition());

            if (forwardEncoder.getCurrentPosition() < EncoderTarget) {
                ForwardMove(MotorPower, EncoderTarget);
            }
            if (forwardEncoder.getCurrentPosition() > EncoderTarget) {
                forwardCorrection(MotorPower, EncoderTarget);
            }
            if (measure > horizonatalLeeway) {
                horizontalCorrection(MotorPower, EncoderTarget);
            }
            //check position
            if ((Math.abs(EncoderTarget - forwardEncoder.getCurrentPosition()) < forwardLeeway) && (Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition()) < horizonatalLeeway)) {
                // stops loop
                offPoint = false;
            }

        }
        StopMotors();
    }

}
