package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.Swivel;
import org.firstinspires.ftc.logic.ButtonOneShot;

@TeleOp (name = "Swivel Test", group = "Skystone")
public class SwivelTest extends OpMode {

    private final Swivel swivel = new Swivel();
    private double clawPosition;
    private final ButtonOneShot buttonOneShotY = new ButtonOneShot();
    private final ButtonOneShot buttonOneShotX = new ButtonOneShot();
    private boolean clawOpen = true;
    private boolean setPositions = true;
    private String clawState;

    public void init() {
        swivel.initialize(this);
        clawPosition = swivel.swivelServo.getPosition();

    }

    public void loop() {
        if (gamepad1.left_bumper) {
            if (clawPosition < 1) {
                clawPosition = clawPosition + 0.001;
            }
            swivel.swivelServo.setPosition(clawPosition);
        }
        if (gamepad1.right_bumper) {
            if (clawPosition > .0001) {
                clawPosition = clawPosition - 0.001;
            }
            swivel.swivelServo.setPosition(clawPosition);
        }

        if (buttonOneShotY.isPressed(gamepad1.y)) {
            clawOpen = !clawOpen;
        }

        if (buttonOneShotX.isPressed(gamepad1.x)) {
            setPositions = !setPositions;
        }
        if (setPositions) {
            if (clawOpen) {
                swivel.setPosition(0.93);
                clawState = "open";
            } else if (!clawOpen) {
                swivel.setPosition(0.27);
                clawState = "closed";
            }
        }

        telemetry.addData("claw Position:", swivel.swivelServo.getPosition());
        telemetry.addData("clawState:", clawState);
        telemetry.update();

    }
    
}
