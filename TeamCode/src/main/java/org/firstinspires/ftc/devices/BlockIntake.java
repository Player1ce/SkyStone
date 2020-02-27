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

    public DcMotor spoolMotor;

    public BlockIntake(ChassisName name) {
    }

    public void initializeIntake (OpMode opMode) {
        leftIntake = opMode.hardwareMap.dcMotor.get("leftIntake");
        rightIntake = opMode.hardwareMap.dcMotor.get("rightIntake");
        rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        spoolMotor = opMode.hardwareMap.dcMotor.get("spoolMotor");
        spoolMotor.setPower(0.4);
        spoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void lowerRamp() {
        spoolMotor.setTargetPosition(0);
        spoolMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void raiseRamp() {
        spoolMotor.setTargetPosition(500);
        spoolMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void setIntakeBrakes() {
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}

