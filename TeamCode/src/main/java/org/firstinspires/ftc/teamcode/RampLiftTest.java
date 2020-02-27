package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.devices.BlockClaw;
import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ButtonOneShot;

@TeleOp(name="Ramp Lift Test", group = "Skystone")
public class RampLiftTest extends OpMode {


    int position;

    public DcMotor spoolMotor;

    public void initialize (OpMode opMode){
        spoolMotor = opMode.hardwareMap.dcMotor.get("spoolMotor");
    }

    public void init() {
        spoolMotor = hardwareMap.dcMotor.get("spoolMotor");
        spoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void loop() {
        if (gamepad1.left_bumper) {
           position=500;
        }
       else  if (gamepad1.right_bumper) {
            position=0;
        }

       spoolMotor.setPower(0.4);
       spoolMotor.setTargetPosition(position);
        spoolMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("target pos:", position);
        telemetry.addData("current pos:", spoolMotor.getCurrentPosition());
        telemetry.update();

    }

}
