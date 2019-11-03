package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class IntakeMethods {
    DcMotor leftIntake;
    DcMotor rightIntake;

    String chassis;

    IntakeMethods (String chassisName) {
        chassis = chassisName.toLowerCase();
    }

    public void setIntakeVars (DcMotor leftIntakeSet, DcMotor rightIntakeSet) {
        this.leftIntake = leftIntakeSet;
        this.rightIntake = rightIntakeSet;
    }

    public void initializeIntake (OpMode opMode) {
        leftIntake = opMode.hardwareMap.dcMotor.get("leftIntake");
        rightIntake = opMode.hardwareMap.dcMotor.get("rightIntake");
    }


    public void setIntakeBrakes() {
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}

