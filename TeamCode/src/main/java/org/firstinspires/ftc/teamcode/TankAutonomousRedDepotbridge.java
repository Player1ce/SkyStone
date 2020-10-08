package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.BlockIntake;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.logic.ColorSensorLogic;
import org.firstinspires.ftc.logic.KillOpModeException;
import org.firstinspires.ftc.logic.ServoPosition;

@Disabled
@Autonomous(name = "TankRedDepotBridge", group="Skystone OLD")
public class TankAutonomousRedDepotbridge extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook( ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);

    ColorSensor colorSensor;

    ColorSensorLogic frontColorSensor;

    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        hookServo.initializeHook(this);
        intake.initializeIntake(this);
        intake.setIntakeBrakes();

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
        }
        catch (KillOpModeException e) {}

    }

    public void moveHook(ServoPosition position) {
        if (position == ServoPosition.UP) {
            hookServo.hookServo.setPosition(.4);
        } else {
            hookServo.hookServo.setPosition(0);
        }
    }

    protected void executeAutonomousLogic() {
        double ticksToInches = 288 / (Math.PI * 6.125);

        int[] colors = frontColorSensor.getAverageColor(500);

        int redBaseline = colors[0];
        int blueBaseline = colors[2];

        int redTarget = redBaseline + 7;
        int blueTarget = blueBaseline + 4;


        mecanumWheels.setPowerFromGamepad(false, .6, 0, 0, 10);

            while (colorSensor.red() < redTarget && colorSensor.blue() < blueTarget) {
                telemetry.addData("Colors", "-> " + colors[0] + "  " + colors[1] + "   " + colors[2]);
                telemetry.update();
                sleep(10);
                mecanumWheels.StopMotors();
            }

            mecanumWheels.crabDrive("left", .6, 2000 );

    }
}