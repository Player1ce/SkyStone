package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ChassisName;

public class Swivel {
    ChassisName chassis;
    public Servo swivelServo;

    public void initialize (OpMode opMode) {
        swivelServo = opMode.hardwareMap.servo.get("swivelServo");
    }

    public void setPosition (double position) {
        swivelServo.setPosition(position);
        /**important positions
         *
         */
    }


}
