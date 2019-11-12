package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class SkystoneIntake {
    DcMotor leftIntake;
    DcMotor rightIntake;
    Servo rampServo;

    String chassis;

    SkystoneIntake(String chassisName) {
        chassis = chassisName.toLowerCase();
    }

    public void initializeIntake (OpMode opMode) {
        leftIntake = opMode.hardwareMap.dcMotor.get("leftIntake");
        rightIntake = opMode.hardwareMap.dcMotor.get("rightIntake");
        rampServo = opMode.hardwareMap.servo.get("hookServo");
    }

    public void setIntakeBrakes() {
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}

