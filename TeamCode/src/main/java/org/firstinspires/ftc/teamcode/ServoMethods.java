package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

public class ServoMethods extends MecanumWheels {
    String chassis;

    ServoMethods (String chassisName) {
        chassis=chassisName;
    }

    public void assignServoVariables (Servo hookServo, Servo rampServo) {
        this.hookServo = hookServo;
        this.rampServo = rampServo;
    }
}
