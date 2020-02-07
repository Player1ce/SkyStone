package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.BlockClaw;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;

@TeleOp(name="Intake tester", group = "Skystone")
public class IntakeTester extends OpMode {
    private final ButtonOneShot buttonOneShotY = new ButtonOneShot();
    private final ButtonOneShot buttonOneShotX = new ButtonOneShot();
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);

    public void init() {
        intake.initializeIntake(this);
    }

    public void loop() {
        if (gamepad1.left_trigger > 0) {
            intake.leftIntake.setPower(-gamepad1.left_trigger * .7);
            intake.rightIntake.setPower(gamepad1.left_trigger * .7);
        }
        else if (gamepad1.right_trigger > 0) {
            intake.leftIntake.setPower(gamepad1.right_trigger * .7);
            intake.rightIntake.setPower(-gamepad1.right_trigger * .7);
        }
        else {
            intake.leftIntake.setPower(0);
            intake.rightIntake.setPower(0);
        }

    }

}
