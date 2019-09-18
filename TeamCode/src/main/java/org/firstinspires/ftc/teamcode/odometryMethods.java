package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class odometryMethods extends BasicRobotMethods {
	DcMotor horizontalEncoder;
    DcMotor forwardEncoder;
	
	int forwardLeeway = 15;
    int horizonatalLeeway = 15;
	
	@Override
	public void resetOdometry(LinearOpMode linearOpMode) {
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
                //DriveLeft(double MotorPower, int EncoderTarget);
            }
            if ((EncoderTarget - horizontalEncoder.getCurrentPosition()) > 0) {
                //DriveRight(double MotorPower, int EncoderTarget);
            }

        }
        StopMotors();

	}
	
	@Override
	public void forwardCorrection(double MotorPower, int EncoderTarget){
		 int error = Math.abs(EncoderTarget - forwardEncoder.getCurrentPosition());

        while (error > forwardLeeway) {
            if ((EncoderTarget - forwardEncoder.getCurrentPosition()) < 0) {
                //DriveBackward(double MotorPower, int EncoderTarget);
            }
            if ((EncoderTarget - forwardEncoder.getCurrentPosition()) > 0) {
                //DriveForward(double MotorPower, int EncoderTarget);
            }

        }
        StopMotors();

	}
	
	@Override
	public void forwardMoveOdometry(double MotorPower, int EncoderTarget, LinearOpMode linearOpMode){
	    ResetEncoders(linearOpMode);
        resetOdometry(linearOpMode);
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
                //DriveForward(double MotorPower, int EncoderTarget);
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
