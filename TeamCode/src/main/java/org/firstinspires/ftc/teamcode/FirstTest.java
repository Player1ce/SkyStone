package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.logic.Navigation;

@Autonomous(name = "FirstTest", group = "Skystone")
public class FirstTest extends LinearOpMode {
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final Navigation navigation = new Navigation(ChassisName.TANK);


    public void runOpMode() {
        wheels.initializeWheels(this);
        wheels.setZeroPowerBrakeBehavior();
        waitForStart();

        navigation.NavigateStraightTicks(telemetry,.7, .3,500);
        wheels.sleepAndCheckActive(2000);
        navigation.NavigateStraightTicks(telemetry,.7,.3,-500);
        wheels.sleepAndCheckActive(2000);

    }


}
