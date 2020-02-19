package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.BasicPositions;

public class Swivel {
    public Servo swivelServo;

    public void initialize (OpMode opMode) {
        swivelServo = opMode.hardwareMap.servo.get("swivelServo");
    }


    public void setPosition (double position) {
        swivelServo.setPosition(position);
        /*important positions
         * out(180): 0.93
         * in(0): 0.27
         * TODO: 90:?
         */
    }


    public void setPositionEnum (BasicPositions positionEnum) {
        if (positionEnum == BasicPositions.OPEN) {
            setPosition( .93);
        }
        else if (positionEnum == BasicPositions.CLOSED) {
            setPosition(.27);
        }
    }


}
