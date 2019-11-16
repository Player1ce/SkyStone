package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotDevices.ChassisName;
import org.firstinspires.ftc.robotDevices.FoundationHook;
import org.firstinspires.ftc.robotDevices.MecanumWheels;
import org.firstinspires.ftc.controlunits.ServoPosition;
import org.firstinspires.ftc.robotDevices.SkystoneIntake;
import org.firstinspires.ftc.teamcode.TeleOpMethods;

@Autonomous(name = "TankAutonomousTester", group="Skystone")
public class TankAutonomousTester extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;

    //Use this class to test new methods and anything else for auto

    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);

        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        hookServo.moveHookEnum(ServoPosition.UP);

        executeAutonomousLogic();

    }

    protected void executeAutonomousLogic() {
        double ticksToInches=288/(Math.PI*6.125);

    }

    }


