package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import org.firstinspires.ftc.logic.ColorSensorLogic;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.logic.KillOpModeException;
import org.firstinspires.ftc.logic.ServoPosition;
import org.firstinspires.ftc.devices.BlockIntake;

@Autonomous (name = "TankAutonomousBlueWall", group="Skystone")
public class TankAutonomousBlueWall extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels=new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);

    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    private double i;
    long startTime;



    ColorSensor colorSensor;

    ColorSensorLogic frontColorSensor;

    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);

        colorSensor = hardwareMap.get(ColorSensor.class, "frontColorSensor");

        frontColorSensor=new ColorSensorLogic(colorSensor);

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        moveHook(ServoPosition.UP);

        try {
            executeAutonomousLogic();
        }
        catch (KillOpModeException e) {
            //do nothing (the program will end gracefully)
        }

    }

    public void moveHook(ServoPosition position){
        if (position==ServoPosition.UP)  {
            hookServo.hookServo.setPosition(.4);
        }
        else  {
            hookServo.hookServo.setPosition(0);
        }
    }

    protected void executeAutonomousLogic() {

            double ticksToInches = 288 / (Math.PI * 6.125);
            mecanumWheels.ForwardMoveInches(telemetry, 0.5, 23, ticksToInches);
            mecanumWheels.crabDrive("left", 0.7, 1050);

            //now drive until we see the red or blue plate
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

            mecanumWheels.ForwardMoveInches(telemetry, 0.4, 1, ticksToInches);

            //mecanumWheels.ForwardMoveInches(telemetry, 0.5, 5, ticksToInches);

            moveHook(ServoPosition.DOWN);

            mecanumWheels.sleepAndCheckActive(1000);
            mecanumWheels.BackwardMoveInches(telemetry, -0.5, 33, ticksToInches);



            mecanumWheels.sleepAndCheckActive(1000);

            moveHook(ServoPosition.UP);

            mecanumWheels.crabDrive("right", 0.7, 2500);
            mecanumWheels.ForwardMoveInches(telemetry, 0.5, 13, ticksToInches);
            mecanumWheels.crabDrive("left", 0.7, 2000);

            mecanumWheels.sleepAndCheckActive(500);

            mecanumWheels.crabDrive("right", 0.7, 1000);

            mecanumWheels.sleepAndCheckActive(500);

            mecanumWheels.setPowerFromGamepad(false, .7, 10, 0, 0);

            mecanumWheels.sleepAndCheckActive(1100);

            mecanumWheels.StopMotors();

            mecanumWheels.sleepAndCheckActive(500);

            mecanumWheels.crabDrive("right", 0.7, 1500);

            mecanumWheels.sleepAndCheckActive(500);



            mecanumWheels.setPowerFromGamepad(true, .7, 0, 0, 10);

            //added by Leefe for timeout
           //startTime = System.currentTimeMillis();

            while (colorSensor.red() < redTarget && colorSensor.blue() < blueTarget ) {//&& System.currentTimeMillis() - startTime < 1400) {
                telemetry.addData("Colors", "-> " + colors[0] + "  " + colors[1] + "   " + colors[2]);
                telemetry.update();
                mecanumWheels.sleepAndCheckActive(10);
                i += 1;
                if (i == 18) {
                    //intake.rampServo.setPosition(.2);
                }
            }

            mecanumWheels.StopMotors();
            //rampServo.setPosition(.2);

    }

}




