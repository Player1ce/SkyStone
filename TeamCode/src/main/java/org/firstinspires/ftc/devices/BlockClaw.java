package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.ChassisName;

public class BlockClaw {
    ChassisName chassis;
    public Servo clawServo;

    public void BlockClaw(ChassisName name) {
        chassis = name;
    }

    public void initialize(OpMode opMode) {
        clawServo = opMode.hardwareMap.servo.get("rampServo");
    }

}
