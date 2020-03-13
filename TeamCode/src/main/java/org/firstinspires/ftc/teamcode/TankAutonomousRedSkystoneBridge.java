package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.devices.BlockClaw;
import org.firstinspires.ftc.devices.BlockIntake;
import org.firstinspires.ftc.devices.Camera;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.ScissorLift;
import org.firstinspires.ftc.devices.SkystoneLever;
import org.firstinspires.ftc.devices.Swivel;
import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.logic.ColorSensorLogic;
import org.firstinspires.ftc.logic.KillOpModeException;
import org.firstinspires.ftc.logic.Navigation;
import org.firstinspires.ftc.logic.ServoPosition;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.utils.LogUtils;

import java.util.List;
import java.util.Locale;

@Autonomous(name = "TankRedSkystoneBridge", group="Skystone")
public class TankAutonomousRedSkystoneBridge extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);
    private final SkystoneLever skystoneLever = new SkystoneLever();
    private final ScissorLift scissorLift = new ScissorLift(mecanumWheels);
    private final Swivel swivel = new Swivel();
    private final BlockClaw blockClaw = new BlockClaw();
    private final Navigation navigation = new Navigation(ChassisName.TANK);
    private final Camera camera = new Camera();

    ColorSensor colorSensor;

    ColorSensorLogic frontColorSensor;

    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        // hookServo.initializeHook(this);
        intake.initializeIntake(this);
        intake.setIntakeBrakes();
        //  camera.initializeCamera(this);
        // camera.setClipping(100,100,0,0);
        skystoneLever.initialize(this);

        scissorLift.initialize(this);

        imu.initializeIMU(mecanumWheels, this);
        navigation.initialize(mecanumWheels, imu, this);

        colorSensor = hardwareMap.get(ColorSensor.class, "frontColorSensor");

        frontColorSensor = new ColorSensorLogic(colorSensor);

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }
        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();


        //     moveHook(ServoPosition.UP);

        try {
            executeAutonomousLogic();
            //executeAutonomousLogicWithCamera();
        }
        catch (KillOpModeException e) {
            //do nothing (the program will end gracefully)
        }

    }
    //opens up the ramp and scissor lift
    private void openUp() {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                doOpen();
            }
        });
        t.setDaemon(true);
        t.start();

    }

    private void doOpen() {

        scissorLift.setPosition(scissorLift.getPosition()+1100);
        mecanumWheels.sleepAndCheckActive(1000);

        intake.spoolMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.spoolMotor.setPower(-0.205);
        mecanumWheels.sleepAndCheckActive(1000);

        intake.spoolMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.spoolMotor.setPower(0.1);
        mecanumWheels.sleepAndCheckActive(800);

        intake.spoolMotor.setPower(0.4);
        intake.spoolMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intake.raiseRampHalfway();

        scissorLift.setPosition(scissorLift.getPosition()-2000);
        mecanumWheels.sleepAndCheckActive(500);
    }

    public void moveHook(ServoPosition position) {
        if (position == ServoPosition.UP) {
            hookServo.hookServo.setPosition(.4);
        } else {
            hookServo.hookServo.setPosition(0);
        }
    }

    protected void executeAutonomousLogic() {
        skystoneLever.setPosition(BasicPositions.UP);
        navigation.NavigateCrabTicksRight(telemetry, .7, 0.35, 1400);
        skystoneLever.setPosition(BasicPositions.DOWN);
        mecanumWheels.sleepAndCheckActive(300);
        navigation.NavigateCrabTicksLeft(telemetry, .6, 0.35, 400);
        mecanumWheels.sleepAndCheckActive(300);
        doOpen();
        navigation.NavigateStraightTicks(telemetry, 0.4, 0.2, -2200);
        mecanumWheels.sleepAndCheckActive(300);
        skystoneLever.setPosition(BasicPositions.UP);
        mecanumWheels.sleepAndCheckActive(300);

        navigation.NavigateStraightTicks(telemetry, 0.4, 0.2, 2360);

        mecanumWheels.sleepAndCheckActive(300);
        navigation.NavigateCrabTicksRight(telemetry, .6, 0.35, 400);
        skystoneLever.setPosition(BasicPositions.DOWN);
        mecanumWheels.sleepAndCheckActive(300);
        navigation.NavigateCrabTicksLeft(telemetry, .6, 0.35, 400);
        mecanumWheels.sleepAndCheckActive(300);
        navigation.NavigateStraightTicks(telemetry, 0.4, 0.2, -2360);
        skystoneLever.setPosition(BasicPositions.UP);
        mecanumWheels.sleepAndCheckActive(300);


        navigation.NavigateStraightTicks(telemetry, 0.4, 0.2, 800);
        mecanumWheels.sleepAndCheckActive(30000);
    }


    }
