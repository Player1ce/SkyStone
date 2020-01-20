package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.ChassisName;

public class ScissorLift {
    ChassisName chassis;
    DcMotor liftMotor;

    public void ScissorLift(ChassisName name) {
        chassis = name;
    }

    public void initialize(OpMode opMode) {
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



}
