package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class HookMethods {

    Servo hookServo;

    String chassis;

    HookMethods (String chassisName) { chassis = chassisName.toLowerCase(); }

    public void  initializeHook (OpMode opmode) {
        hookServo = opmode.hardwareMap.servo.get("hookServo");
    }

}
