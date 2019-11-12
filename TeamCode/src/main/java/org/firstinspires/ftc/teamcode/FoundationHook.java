package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class FoundationHook {

    Servo hookServo;

    String chassis;

    FoundationHook(String chassisName) { chassis = chassisName.toLowerCase(); }

    public void  initializeHook (OpMode opmode) {
        hookServo = opmode.hardwareMap.servo.get("hookServo");
    }

}
