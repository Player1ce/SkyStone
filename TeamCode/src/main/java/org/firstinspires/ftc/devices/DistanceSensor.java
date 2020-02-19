package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensor {
    com.qualcomm.robotcore.hardware.DistanceSensor distanceSensor;

    public DistanceSensor(ChassisName name, MecanumWheels Wheels) {

    }

    public void initialize(OpMode opMode) {
        com.qualcomm.robotcore.hardware.DistanceSensor distanceSensor = opMode.hardwareMap.get(com.qualcomm.robotcore.hardware.DistanceSensor.class, "liftDistanceSensor");
        //this.distanceSensor = (Rev2mDistanceSensor)distanceSensor;
    }

    public void getDistance () {
        double distance = distanceSensor.getDistance(DistanceUnit.INCH);
    }

}
