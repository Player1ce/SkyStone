package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.teamcode.AutonomousMethods;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class Odometry extends AutonomousMethods {

    // beginning odometry -------------------------------------------------------------------------------------------------

    // odometry methods:
    // TODO variables initiated locally for now will be moved to top later.
    //make private?
    int forwardLeeway = 15;
    int horizonatalLeeway = 15;


    public void ResetOdometry() throws InterruptedException {
        forwardEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

        forwardEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void HorizontalCorrection(double MotorPower, int EncoderTarget) {
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


    public void ForwardCorrection(double MotorPower, int EncoderTarget) {
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


    public void ForwardMoveOdometry(double MotorPower, int EncoderTarget) throws InterruptedException {
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
