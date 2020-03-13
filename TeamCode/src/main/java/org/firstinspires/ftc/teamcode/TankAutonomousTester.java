package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.ScissorLift;
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
    private final ScissorLift scissorLift = new ScissorLift(wheels);

    //Use this class to test new methods and anything else for auto

    public void runOpMode() {
        LogUtils.reset();

        wheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);
        imu.initializeIMU(wheels, this);
        navigation.initialize(wheels, imu, this);
        scissorLift.initialize(this);

        wheels.setZeroPowerBrakeBehavior();
        waitForStart();

        robot.startTime();
        try {
            executeAutonomousLogic();
        } catch (KillOpModeException e) {}
    }

    private void doOpen() {

        scissorLift.setPosition(scissorLift.getPosition()+1100);
        wheels.sleepAndCheckActive(1000);

        intake.spoolMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.spoolMotor.setPower(-0.205);
        wheels.sleepAndCheckActive(1000);

        intake.spoolMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.spoolMotor.setPower(0.1);
        wheels.sleepAndCheckActive(800);

        intake.spoolMotor.setPower(0.4);
        intake.spoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.sleepAndCheckActive(200);

        intake.raiseRampHalfway();

        scissorLift.setPosition(scissorLift.getPosition()-2000);
        wheels.sleepAndCheckActive(500);
    }

    protected void executeAutonomousLogic() {
        /*navigation.NavigateCrabTicksRight(telemetry,.7,0.3,300);
        navigation.NavigateCrabTicksLeft(telemetry,.7,0.3,300);
        navigation.NavigateCrabTicksRight(telemetry,.7,0.3,300);
        navigation.NavigateCrabTicksLeft(telemetry,.7,0.3,300);
        navigation.NavigateCrabTicksRight(telemetry,.7,0.3,300);
        navigation.NavigateCrabTicksLeft(telemetry,.7,0.3,300);
        LogUtils.closeLoggers();
        //navigation.NavigateStraightTicks(telemetry, .5, .3, 2000);
        wheels.sleepAndCheckActive(10000);
         */

        doOpen();

    }
}
