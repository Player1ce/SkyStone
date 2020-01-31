package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ChassisName;

public class ScissorLift {
    ChassisName chassis;
    public DcMotor liftMotor;
    public MecanumWheels wheels;

    public ScissorLift (ChassisName name, MecanumWheels Wheels) {
        this.wheels = Wheels;
        chassis = name;
    }

    public void initialize(OpMode opMode) {
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public double getPosition () {
        return liftMotor.getCurrentPosition();
    }

    public void resetEncoder () {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.sleepAndCheckActive(10);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void setPosition (BasicPositions position) {
        if (position == BasicPositions.OPEN) {
            liftMotor.setTargetPosition(0);
        }
        if (position == BasicPositions.CLOSED) {
            liftMotor.setTargetPosition(0);
        }
    }


}
