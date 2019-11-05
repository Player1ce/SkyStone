package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoMethods {
    String chassis;
    Servo hookServo;
    Servo rampServo;

    ServoMethods (String chassisName) {
        chassis=chassisName.toLowerCase();
    }

    public void assignServoVariables (Servo hookServo, Servo rampServo) {
        this.hookServo = hookServo;
        this.rampServo = rampServo;
    }
}
