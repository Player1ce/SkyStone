package org.firstinspires.ftc.GoBilda;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.devices.Camera;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.logic.KillOpModeException;
import org.firstinspires.ftc.teamcode.TeleOpMethods;





@Autonomous(name = "GoBildaChassisAutonomous", group="Skystone")
public class GoBildaChassisAutonomous extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.GOBILDA);
    private final MecanumWheels mecanumWheels=new MecanumWheels(ChassisName.GOBILDA);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.GOBILDA);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.GOBILDA);
    private final Camera camera = new Camera();

    public void runOpMode() throws InterruptedException {
        mecanumWheels.initializeWheels(this);
        camera.initializeCamera(this);

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).

        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        try {
            executeAutonomousLogic();
        }
        catch (KillOpModeException e) {
            //do nothing (the program will end gracefully)
        }

    }

    protected void executeAutonomousLogic () {
        //put all code in this while loop so the bot will stop when we tell it to

            mecanumWheels.sleepAndCheckActive(30000);

    }


}
