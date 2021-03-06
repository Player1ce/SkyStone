package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.BasicPositions;

public class SkystoneLever {
    public Servo leverServo;


    public void initialize(OpMode opMode) {
        leverServo = opMode.hardwareMap.servo.get("skystoneServo");
    }


    public void setPosition (BasicPositions position) {
        if (position == BasicPositions.UP || position == BasicPositions.OPEN) {
            leverServo.setPosition(0.45);
        }
        if (position == BasicPositions.DOWN || position == BasicPositions.CLOSED) {
            leverServo.setPosition(0.97);
        }
    }
}
