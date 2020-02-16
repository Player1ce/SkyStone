package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.BlockClaw;
import org.firstinspires.ftc.devices.SkystoneLever;
import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;

import static org.firstinspires.ftc.logic.BasicPositions.*;

@TeleOp(name = "Skystone Lever Tester", group = "Skystone")
public class SkystoneLeverTester extends OpMode {
    private final SkystoneLever lever = new SkystoneLever();
    private final ButtonOneShot buttonOneShotY = new ButtonOneShot();
    private final ButtonOneShot buttonOneShotX = new ButtonOneShot();
    private double leverPosition;
    private boolean leverUp;
    private String leverState;
    private boolean setPositions = false;

    public void init() {
        lever.initialize(this);

    }

    public void loop() {
        if (gamepad1.left_bumper) {
            if (leverPosition < 1) {
                leverPosition = leverPosition + 0.001;
            }
        }
        if (gamepad1.right_bumper) {
            if (leverPosition > .0001) {
                leverPosition = leverPosition - 0.001;
            }
        }

        lever.leverServo.setPosition(leverPosition);

        if (buttonOneShotY.isPressed(gamepad1.y)) {
            leverUp = !leverUp;
        }

        if (buttonOneShotX.isPressed(gamepad1.x)) {
            setPositions = !setPositions;
        }

        if (setPositions) {
            if (leverUp) {
                lever.setPosition(OPEN);
                leverState = "open";
            }
            else if (!leverUp) {
                lever.setPosition(CLOSED);
                leverState = "closed";
            }
        }

        telemetry.addData("claw Position:", lever.leverServo.getPosition());
        telemetry.addData("leverState:", leverState);
        telemetry.addData("setPositions:", setPositions);
        telemetry.update();
        
        
        
    }

}
