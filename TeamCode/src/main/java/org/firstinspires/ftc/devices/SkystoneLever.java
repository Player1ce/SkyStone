package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ChassisName;

public class SkystoneLever {
    public Servo leverServo;

    public void initialize(OpMode opMode) {
        leverServo = opMode.hardwareMap.servo.get("skystoneServo");
    }
    public void setPosition (BasicPositions position) {
        if (position == BasicPositions.OPEN) {
            leverServo.setPosition(0.27);
        }
        if (position == BasicPositions.CLOSED) {
            leverServo.setPosition(1);
        }
    }
}
