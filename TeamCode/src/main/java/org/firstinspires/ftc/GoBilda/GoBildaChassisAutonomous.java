package org.firstinspires.ftc.GoBilda;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.controlunits.ColorSensorLogic;
import org.firstinspires.ftc.controlunits.ServoPosition;
import org.firstinspires.ftc.robotDevices.ChassisName;
import org.firstinspires.ftc.robotDevices.FoundationHook;
import org.firstinspires.ftc.robotDevices.MecanumWheels;
import org.firstinspires.ftc.robotDevices.SkystoneIntake;
import org.firstinspires.ftc.teamcode.TeleOpMethods;

@Autonomous(name = "GoBildaChassisAutonomous", group="Skystone")
    public class GoBildaChassisAutonomous extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.GOBILDA);
    private final MecanumWheels mecanumWheels=new MecanumWheels(ChassisName.GOBILDA);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.GOBILDA);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.GOBILDA);

    public void runOpMode() throws InterruptedException {
        mecanumWheels.initializeWheels(this);

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).

        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        executeAutonomousLogic();

    }

    protected void executeAutonomousLogic () {
        //put all code in this while loop so the bot will stop when we tell it to
        while (opModeIsActive()) {

        }

    }


}
