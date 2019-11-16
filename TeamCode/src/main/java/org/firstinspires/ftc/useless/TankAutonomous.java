package org.firstinspires.ftc.useless;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotDevices.ChassisName;
import org.firstinspires.ftc.robotDevices.FoundationHook;
import org.firstinspires.ftc.robotDevices.MecanumWheels;
import org.firstinspires.ftc.controlunits.ServoPosition;
import org.firstinspires.ftc.robotDevices.SkystoneIntake;
import org.firstinspires.ftc.teamcode.TeleOpMethods;

@Autonomous(name = "TankAutonomous", group="Skystone")
public class TankAutonomous extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;


    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);

        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        moveHook(ServoPosition.UP);

        executeAutonomousLogic();

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
        double ticksToInches=288/(Math.PI*6.125);
        /*mecanumWheels.ForwardMoveInches(telemetry, 0.5, 30, ticksToInches);


        moveHook(ServoPosition.DOWN);

        sleep(1000);
        mecanumWheels.BackwardMoveInches(telemetry,-0.5,29,ticksToInches);

        sleep(10000);
         */

        mecanumWheels.crabDrive("left", 1, 5000);
    }

    }


