package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.ChassisName;

public class RingShooter {

    public DcMotor shooterMotor;

    public RingShooter(ChassisName name) {
    }

    public void initializeShooter (OpMode opMode) {
        shooterMotor = opMode.hardwareMap.dcMotor.get("shooterMotor");
    }

    public void spinShooter() {
        shooterMotor.setPower(1);
    }

    public void stopShooter() {
        shooterMotor.setPower(0);
    }
}
