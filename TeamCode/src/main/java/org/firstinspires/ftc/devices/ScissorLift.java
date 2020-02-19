package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class ScissorLift {
    public DcMotor liftMotor;
    public MecanumWheels wheels;
    public DigitalChannel limitSwitch;
    public int count;

    public ScissorLift (MecanumWheels Wheels) {
        this.wheels = Wheels;
    }

    public boolean getLimitState() {
        return limitSwitch.getState();
    }

    public void initialize(OpMode opMode) {
        limitSwitch= opMode.hardwareMap.digitalChannel.get("limitSwitch");
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public double getPosition () {
        return liftMotor.getCurrentPosition();
    }

    private void resetEncoder () {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.sleepAndCheckActive(10);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void zeroEncoder() {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (limitSwitch.getState()) {
            liftMotor.setPower(-1);
            wheels.sleepAndCheckActive(10);
        }
        liftMotor.setPower(0);
        wheels.sleepAndCheckActive(500);
        resetEncoder();

    }

    public void setScissorHeights () {
        int target=0;
        switch (count) {
            case 0:
                target=2500;
                break;
            case 1:
                target=5000;
                break;
            case 2:
                target=7500;
                break;
            case 3:
                target=10000;
                break;
            case 4:
                target=12500;
                break;
            case 5:
                target=15000;
                break;
            case 6:
                target=17500;
                break;
            default:
                count=-1;
                break;
        }
        count++;
        liftMotor.setTargetPosition(target);
        liftMotor.setPower(1);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean switchMode (boolean directControl) {
        if (!directControl) {
            liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
            wheels.sleepAndCheckActive(500);
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setPower(0);
        }
        return true;
    }


}
