package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;

@Autonomous(name = "TankAutonomousTester", group="Skystone")
public class TankAutonomousTester extends LinearOpMode {
    LinearOpMode linearOpMode;
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);
    private final Encoders encoders = new Encoders(0, 0, ChassisName.TANK);
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;

    //Use this class to test new methods and anything else for auto

    public void runOpMode() {
        wheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);
        imu.initializeIMU(this);
        encoders.initialize(wheels);

        wheels.setZeroPowerBrakeBehavior();
        waitForStart();

        robot.startTime();
        executeAutonomousLogic();

    }

    protected void executeAutonomousLogic() {
        //double ticksToInches=288/(Math.PI*6.125);

        encoders.moveInchesEncoders(telemetry, .6, 0.1,25.5);

        sleep(10000);
        /*

        wheels.sleepAndCheckActive(1500);

        encoders.moveInchesEncoders(telemetry, -.6, 0, 2);
        wheels.sleepAndCheckActive(1000);

        encoders.moveInchesEncoders(telemetry, .6, 0, 2);
        wheels.sleepAndCheckActive(500);

        imu.rotate(90, 1, 0, linearOpMode );

        wheels.sleepAndCheckActive(5000);

        imu.rotate(90, 1, 0, linearOpMode);

        encoders.moveInchesEncoders(telemetry, .6, 0, 1);

        imu.rotate(180, 1, 0, linearOpMode);

        encoders.moveInchesEncoders(telemetry, -.6, 0, 1);

        telemetry.addData("end time:",  time);
        telemetry.update();

        wheels.sleepAndCheckActive(7000);

         */

        encoders.moveInchesEncoders(telemetry, .6, 0, 2, ticksToInches );

        wheels.sleepAndCheckActive(1500);

        encoders.moveInchesEncoders(telemetry, -.6, 0, 2, ticksToInches);
        wheels.sleepAndCheckActive(1000);

        encoders.moveInchesEncoders(telemetry, .6, 0, 2, ticksToInches );
        wheels.sleepAndCheckActive(500);

        imu.rotate(90, 1, 0, linearOpMode );

        wheels.sleepAndCheckActive(5000);

        imu.rotate(90, 1, 0, linearOpMode);

        encoders.moveInchesEncoders(telemetry, .6, 0, 1, ticksToInches);

        imu.rotate(180, 1, 0, linearOpMode);

        encoders.moveInchesEncoders(telemetry, -.6, 0, 1, ticksToInches);

        telemetry.addData("end time:",  time);
        telemetry.update();

        wheels.sleepAndCheckActive(7000);

    }

    }
