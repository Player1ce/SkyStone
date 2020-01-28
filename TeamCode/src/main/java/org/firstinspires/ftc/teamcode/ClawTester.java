package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.BlockClaw;
import org.firstinspires.ftc.logic.ChassisName;

@TeleOp(name="Claw tester", group = "Skystone")
public class ClawTester extends OpMode {
    private final BlockClaw blockClaw = new BlockClaw();
    private double rampPosition;

    public void init() {
        blockClaw.initialize(this);
        rampPosition = blockClaw.clawServo.getPosition();
    }

    public void loop() {
        if (gamepad2.left_bumper) {
            if (rampPosition < 1) {
                rampPosition = rampPosition + 0.003;
            }
            blockClaw.clawServo.setPosition(rampPosition);
        }
        if (gamepad2.right_bumper) {
            if (rampPosition > .0001) {
                rampPosition = rampPosition - 0.003;
            }
            blockClaw.clawServo.setPosition(rampPosition);
        }

        telemetry.addData("claw Position:", blockClaw.clawServo.getPosition());
        telemetry.update();

    }

}
