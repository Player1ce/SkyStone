package org.firstinspires.ftc.devices;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;

public class ScissorLift {
    ChassisName chassis;
    public DcMotor liftMotor;
    public MecanumWheels wheels;
    DistanceSensor distanceSensor;
    public DigitalChannel limitSwitch;
    int count;

    /**
     * limit switch starts
     *
     */

    public ScissorLift (ChassisName name, MecanumWheels Wheels) {
        this.wheels = Wheels;
        chassis = name;
    }

    public boolean getLimitState() {
        return limitSwitch.getState();
    }

    public void initialize(OpMode opMode) {
        limitSwitch= opMode.hardwareMap.digitalChannel.get("limitSwitch");
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DistanceSensor distanceSensor = opMode.hardwareMap.get(DistanceSensor.class, "liftDistanceSensor");
        //this.distanceSensor = (Rev2mDistanceSensor)distanceSensor;
    }

    public double getPosition () {
        return liftMotor.getCurrentPosition();
    }

    public void resetEncoder () {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.sleepAndCheckActive(10);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void setTargetPosition (int position) {
        liftMotor.setTargetPosition(position);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(1);
    }

    public void getDistance () {
        double distance = distanceSensor.getDistance(DistanceUnit.INCH);
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
                target=0;
                count=-1;
                break;
        }
        count++;
        liftMotor.setTargetPosition(target);
        liftMotor.setPower(1);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }


}
