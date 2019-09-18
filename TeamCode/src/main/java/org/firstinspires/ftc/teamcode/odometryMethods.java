package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class odometryMethods extends BasicRobotMethods {
	DcMotor horizontalEncoder;
    DcMotor forwardEncoder;
	
	int forwardLeeway = 15;
    int horizonatalLeeway = 15;
	
	@Override
	public void resetOdometry() throws InterruptedException {
		forwardEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

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
	public void forwardMoveOdometry(double MotorPower, int EncoderTarget){
		 ResetEncoders();
        ResetOdometry();
        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);
        String running = "Normal";
        boolean offPoint = true;

        while ((offPoint) && opModeIsActive()) {
            telemetry.addData("running: ", running);
            telemetry.addData("encoder value:", frontRight.getCurrentPosition());
            telemetry.addData("encoder target:", EncoderTarget);
            telemetry.update();
            int measure = Math.abs(EncoderTarget - horizontalEncoder.getCurrentPosition());


            if (forwardEncoder.getCurrentPosition() < EncoderTarget) {
                //DriveForward(double MotorPower, int EncoderTarget);
            }
            if (forwardEncoder.getCurrentPosition() > EncoderTarget) {
                ForwardCorrection(MotorPower, EncoderTarget);
            }
            if (measure > horizonatalLeeway) {
                HorizontalCorrection(MotorPower, EncoderTarget);
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
