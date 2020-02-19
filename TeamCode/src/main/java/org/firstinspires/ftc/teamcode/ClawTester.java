package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.BlockClaw;
import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ButtonOneShot;

@TeleOp(name="Claw tester", group = "Skystone")
public class ClawTester extends OpMode {
    private final BlockClaw blockClaw = new BlockClaw();
    private double clawPosition;
    private final ButtonOneShot buttonOneShotY = new ButtonOneShot();
    private final ButtonOneShot buttonOneShotX = new ButtonOneShot();
    private boolean clawOpen = true;
    private boolean setPositions = true;
    private String clawState;

    public void init() {
        blockClaw.initialize(this);
        clawPosition = blockClaw.clawServo.getPosition();

    }

    public void loop() {
        if (gamepad1.left_bumper) {
            if (clawPosition < 1) {
                clawPosition = clawPosition + 0.001;
            }
            blockClaw.clawServo.setPosition(clawPosition);
        }
        if (gamepad1.right_bumper) {
            if (clawPosition > .0001) {
                clawPosition = clawPosition - 0.001;
            }
            blockClaw.clawServo.setPosition(clawPosition);
        }

        if (buttonOneShotY.isPressed(gamepad1.y)) {
            clawOpen = !clawOpen;
        }

        if (buttonOneShotX.isPressed(gamepad1.x)) {
            setPositions = !setPositions;
        }
        if (setPositions) {
            if (clawOpen) {
                blockClaw.setPosition(BasicPositions.OPEN);
                clawState = "open";
            } else if (!clawOpen) {
                blockClaw.setPosition(BasicPositions.CLOSED);
                clawState = "closed";
            }
        }

        telemetry.addData("claw Position:", blockClaw.clawServo.getPosition());
        telemetry.addData("clawState:", clawState);
        telemetry.update();

    }

}
