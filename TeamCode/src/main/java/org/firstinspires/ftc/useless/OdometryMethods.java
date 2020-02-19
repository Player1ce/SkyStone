package org.firstinspires.ftc.useless;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.BlockIntake;
import org.firstinspires.ftc.teamcode.TeleOpMethods;


public class OdometryMethods extends BasicRobotMethods {
    //TODO use a varibale from a constructor in instance creation
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);

    DcMotor horizontalEncoder;
    DcMotor forwardEncoder;
    LinearOpMode linearOpMode;
    
    int forwardLeeway = 15;
    int horizonatalLeeway = 15;

    String chassis;
    
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
        mecanumWheels.backRight.setPower(MotorPower);
        mecanumWheels.frontLeft.setPower(MotorPower);
        mecanumWheels.backLeft.setPower(MotorPower);
        mecanumWheels.frontRight.setPower(MotorPower);
        String running = "Normal";
        boolean offPoint = true;

        while ((offPoint) && linearOpMode.opModeIsActive()) {
            linearOpMode.telemetry.addData("running: ", running);
            linearOpMode.telemetry.addData("encoder value:", mecanumWheels.frontRight.getCurrentPosition());
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
        mecanumWheels.backRight.setPower(-MotorPower);
        mecanumWheels.frontLeft.setPower(-MotorPower);
        mecanumWheels.backLeft.setPower(-MotorPower);
        mecanumWheels.frontRight.setPower(-MotorPower);
        String running = "Normal";
        boolean offPoint = true;

        while ((offPoint) && linearOpMode.opModeIsActive()) {
            linearOpMode.telemetry.addData("running: ", running);
            linearOpMode.telemetry.addData("encoder value:", mecanumWheels.frontRight.getCurrentPosition());
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
