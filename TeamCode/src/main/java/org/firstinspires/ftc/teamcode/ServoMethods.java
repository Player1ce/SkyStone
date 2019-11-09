package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoMethods {
    Servo rampServo;
    Servo hookServo;

    String chassis;

    ServoMethods (String chassisName) { chassis = chassisName.toLowerCase(); }

    public void setServoVars (Servo rampServoSet, Servo hookServoSet) {
        this.rampServo = rampServoSet;
        this.hookServo = hookServoSet;
    }

    public void  initializeServos (OpMode opmode) {
        hookServo = opmode.hardwareMap.servo.get("hookServo");
        rampServo = opmode.hardwareMap.servo.get("rampServo");
    }
    public void hookControl (boolean enabled) {
        if (enabled)  {
            hookServo.setPosition(0);
        }
        else   {
            hookServo.setPosition(.6);
            //hookServo.setPosition(.47);
        }
    }

}
