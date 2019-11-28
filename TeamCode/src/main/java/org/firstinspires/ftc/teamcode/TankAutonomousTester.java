package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.IMUOnBot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.logic.ServoPosition;
import org.firstinspires.ftc.devices.SkystoneIntake;

@Autonomous(name = "TankAutonomousTester", group="Skystone")
public class TankAutonomousTester extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);
    private final IMUOnBot imu = new IMUOnBot(ChassisName.TANK);
    private final Encoders encoders = new Encoders(0, 0, ChassisName.TANK);
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;

    //Use this class to test new methods and anything else for auto

    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);
        imu.initializeIMU(this);

        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();

        robot.startTime();
        executeAutonomousLogic();

    }

    protected void executeAutonomousLogic() {
        double ticksToInches=288/(Math.PI*6.125);

        encoders.moveInchesEncoders(telemetry, .4, 0, 2, ticksToInches );

        imu.rotate(90, 1);


    }

    }


