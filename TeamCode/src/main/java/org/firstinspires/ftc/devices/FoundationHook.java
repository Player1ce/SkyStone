package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.ServoPosition;

public class FoundationHook {

    public Servo hookServo;

    private ChassisName chassis;

    public FoundationHook(ChassisName name) { chassis = name; }

    public void  initializeHook (OpMode opmode) {
        hookServo = opmode.hardwareMap.servo.get("hookServo");
    }

    public void moveHookBoolean(boolean servoState){
        if (servoState)  {
            hookServo.setPosition(0);
        }
        else   {
            hookServo.setPosition(.6);
        }

    }

    public void moveHookEnum(ServoPosition position) {
        if (position==ServoPosition.UP)  {
            hookServo.setPosition(.4);
        }
        else  {
            hookServo.setPosition(0);
        }
    }


}
