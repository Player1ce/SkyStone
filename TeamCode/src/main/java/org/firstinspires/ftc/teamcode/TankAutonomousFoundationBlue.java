package org.firstinspires.ftc.teamcode;

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

@Autonomous(name = "TankFoundationBlue", group="Skystone")
public class TankAutonomousFoundationBlue extends LinearOpMode {
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
        hookServo.initializeHook(this);
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


        moveHook(ServoPosition.UP);

        try {
            executeAutonomousLogic();
            //executeAutonomousLogicWithCamera();
        } catch (KillOpModeException e) {
            //do nothing (the program will end gracefully)
        }

    }

    //opens up the ramp and scissor lift
    private void openUp() {
        Thread t = new Thread(new Runnable() {
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
        mecanumWheels.sleepAndCheckActive(200);

        intake.raiseRampHalfway();

        scissorLift.setPosition(scissorLift.getPosition()-2000);
        mecanumWheels.sleepAndCheckActive(500);
    }


    public void moveHook(ServoPosition position) {
        if (position == ServoPosition.UP) {
            hookServo.hookServo.setPosition(.2);
        } else {
            hookServo.hookServo.setPosition(0);
        }
    }

    protected void executeAutonomousLogic() {
        navigation.NavigateStraightTicks(telemetry, .5, 0.35, -700);
        mecanumWheels.sleepAndCheckActive(300);
        imu.rotate(89, .7, .35, this);
        mecanumWheels.sleepAndCheckActive(100);
        navigation.NavigateStraightTicks(telemetry, .5, 0.3, -300);
        mecanumWheels.sleepAndCheckActive(300);
        imu.rotate(-88, .7, .35, this);
        mecanumWheels.sleepAndCheckActive(100);

        int[] colors = frontColorSensor.getAverageColor(500);

        int redBaseline = colors[0];
        int blueBaseline = colors[2];

        int redTarget = redBaseline + 7;
        int blueTarget = blueBaseline + 4;

        telemetry.addData("Baseline Colors", colors[0] + "  " + colors[1] + "   " + colors[2]);
        telemetry.update();
        mecanumWheels.setPower(0.3, 0.3, 0.3, 0.3);

        while (colorSensor.red() < redTarget && colorSensor.blue() < blueTarget) {
            telemetry.addData("Colors", "-> " + colors[0] + "  " + colors[1] + "   " + colors[2]);
            telemetry.update();
            mecanumWheels.sleepAndCheckActive(10);
        }
        mecanumWheels.StopMotors();
        telemetry.addData("Colors", "* " + colors[0] + "  " + colors[1] + "   " + colors[2]);
        telemetry.update();

        navigation.NavigateStraightTicks(telemetry, .5, 0.3, -30);
        mecanumWheels.sleepAndCheckActive(300);

        moveHook(ServoPosition.DOWN);
        mecanumWheels.sleepAndCheckActive(300);

        navigation.NavigateStraightTicks(telemetry, .5, 0.3, 1300);
        mecanumWheels.sleepAndCheckActive(300);

        imu.rotate(20, 1, .7, this);
        mecanumWheels.sleepAndCheckActive(300);

        moveHook(ServoPosition.UP);
        mecanumWheels.sleepAndCheckActive(300);

        mecanumWheels.setPowerFromGamepad(false, .5, 0,0,1);
        mecanumWheels.sleepAndCheckActive(100);

        navigation.NavigateCrabTicksLeftNoRotation(telemetry,.7, .4,300);
        mecanumWheels.sleepAndCheckActive(200);

        imu.rotate(60, .7, .35, this);
        mecanumWheels.sleepAndCheckActive(100);

        doOpen();
        
        navigation.NavigateStraightTicks(telemetry, .5, 0.3, 1400);
        mecanumWheels.sleepAndCheckActive(300);

    }
}
