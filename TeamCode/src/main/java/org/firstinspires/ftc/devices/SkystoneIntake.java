package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.ChassisName;

public class SkystoneIntake {
    public DcMotor leftIntake;
    public DcMotor rightIntake;
    public Servo rampServo;

    private ChassisName chassis;

    public SkystoneIntake(ChassisName name) {
        chassis = name;
    }

    public void initializeIntake (OpMode opMode) {
        leftIntake = opMode.hardwareMap.dcMotor.get("leftIntake");
        rightIntake = opMode.hardwareMap.dcMotor.get("rightIntake");
        rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        //rampServo = opMode.hardwareMap.servo.get("rampServo");
    }

    public void setIntakeBrakes() {
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}

