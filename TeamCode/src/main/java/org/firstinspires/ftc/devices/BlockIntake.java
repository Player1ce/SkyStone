package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ChassisName;

public class BlockIntake {
    public DcMotor leftIntake;
    public DcMotor rightIntake;
    private Servo lockServo;
    public Servo rampServo;

    public BlockIntake(ChassisName name) {
    }

    public void initializeIntake (OpMode opMode) {
        leftIntake = opMode.hardwareMap.dcMotor.get("leftIntake");
        rightIntake = opMode.hardwareMap.dcMotor.get("rightIntake");
        rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        //lockServo = opMode.hardwareMap.servo.get("lockServo");
    }

    public void lockControl (BasicPositions position) {
        if (position == BasicPositions.OPEN) {
            lockServo.setPosition(1);
        }
        else if (position == BasicPositions.CLOSED) {
            lockServo.setPosition(0);
        }
    }


    public void setIntakeBrakes() {
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}

