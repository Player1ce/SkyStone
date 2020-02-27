package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
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

@Autonomous(name = "TankBlueSkystoneBridge", group="Skystone")
public class TankAutonomousBlueSkystoneBridge extends LinearOpMode {
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
        camera.initializeCamera(this);
        skystoneLever.initialize(this);

        camera.setClipping(100,100,0,0);

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
        }
        catch (KillOpModeException e) {
            //do nothing (the program will end gracefully)
        }

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
        navigation.NavigateCrabTicksRight(telemetry,.6,0.35,1020);

        camera.activate();
        mecanumWheels.sleepAndCheckActive(2000);

        int blockIndex=0;
        outerLoop:
        while(opModeIsActive()) {
            Recognition stone=detectSkystone(3);
            if (stone!=null) {
                //we have a skystone

                telemetry.addData("Block Index", blockIndex);

                telemetry.update();
                break outerLoop;
            }
            else {
                blockIndex++;
                camera.deactivate();

                navigation.NavigateStraightTicks(telemetry,0.3,0.15,-360);

                camera.activate();
                mecanumWheels.sleepAndCheckActive(2000);
            }
        }


        navigation.NavigateStraightTicks(telemetry,0.3,0.15,-50);

        navigation.NavigateCrabTicksRight(telemetry,.4,0.35,100);
        skystoneLever.setPosition(BasicPositions.DOWN);

        mecanumWheels.sleepAndCheckActive(1000);
        navigation.NavigateCrabTicksLeft(telemetry,.4,0.35,100);

        int ticksFromFirst=1000;

        mecanumWheels.sleepAndCheckActive(2000);

     //   navigation.NavigateCrabTicksRight(telemetry,.6,0.35,100);


        LogUtils.closeLoggers();

        mecanumWheels.sleepAndCheckActive(10000);


    }

    protected Recognition detectSkystone(int maxAttempts) {

        int attempt=0;
        while (opModeIsActive()) {
            attempt++;
            if (attempt>maxAttempts) {
                return null;
            }
            //put all code in this while loop so the bot will stop when we tell it to
            List<Recognition> updatedRecognitions = camera.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                if (updatedRecognitions.size()>0) {
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format(Locale.US, "label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format(Locale.US, "  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format(Locale.US, "  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        telemetry.addData(String.format(Locale.US, "  w,h (%d)", i), "%d , %d",
                                recognition.getImageWidth(), recognition.getImageHeight());
                        if ("Skystone".equals(recognition.getLabel())) {
                            return recognition;
                        }
                    }
                    telemetry.update();
                    return null;
                }
            }
        }
        return null;
    }

}
