package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.logic.Navigation;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.BlockIntake;
import org.firstinspires.ftc.logic.KillOpModeException;
import org.firstinspires.ftc.utils.LogUtils;

@Autonomous(name = "TankAutonomousTester", group="Skystone")
public class TankAutonomousTester extends LinearOpMode {
    LinearOpMode linearOpMode;
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);
    private final Navigation navigation = new Navigation(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);
    //Use this class to test new methods and anything else for auto

    public void runOpMode() {
        LogUtils.reset();

        wheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);
        imu.initializeIMU(wheels, this);
        navigation.initialize(wheels, imu, this);

        wheels.setZeroPowerBrakeBehavior();
        waitForStart();

        robot.startTime();
        try {
            executeAutonomousLogic();
        } catch (KillOpModeException e) {}
    }

    protected void executeAutonomousLogic() {
        navigation.NavigateCrabTicks(telemetry,.7,0.3,2500);
        LogUtils.closeLoggers();
        //navigation.NavigateStraightTicks(telemetry, .5, .3, 2000);
        wheels.sleepAndCheckActive(10000);

    }
}
