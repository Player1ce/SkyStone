package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.Navigation;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.logic.KillOpModeException;

@Autonomous(name = "TankAutonomousTester", group="Skystone")
public class TankAutonomousTester extends LinearOpMode {
    LinearOpMode linearOpMode;
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);
    private final Navigation navigation = new Navigation(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);
    //Use this class to test new methods and anything else for auto

    public void runOpMode() {
        wheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);
        navigation.initialize(wheels, this);

        wheels.setZeroPowerBrakeBehavior();
        waitForStart();

        robot.startTime();
        try {
            executeAutonomousLogic();
        } catch (KillOpModeException e) {}
    }

    protected void executeAutonomousLogic() {
        navigation.NavigateStraightTicks(telemetry, .5, 0,2000);
        //imu.rotate(-90,.3,0.1,this);
        //navigation.NavigateCrabTicks(telemetry, 0.6,0.3,2000);
        wheels.sleepAndCheckActive(20000);


    }
}
