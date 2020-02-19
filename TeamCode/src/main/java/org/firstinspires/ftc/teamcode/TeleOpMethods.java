package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.ChassisName;

public class TeleOpMethods {
    Servo hookServo;

    long startTime;

    ChassisName chassis;

    public TeleOpMethods (ChassisName name) { chassis = name; }

    public void startTime() { startTime = System.currentTimeMillis(); }

    public String reverseSense(boolean reverse) {
        if (reverse) {
            return("F/R: REVERSE");
        }
        else {
            return("F/R: FORWARD");
        }
    }

}
