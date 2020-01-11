package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.devices.EncodersOld;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.logic.KillOpModeException;

@Autonomous(name = "TankAutonomousTester", group="Skystone")
public class TankAutonomousTester extends LinearOpMode {
    LinearOpMode linearOpMode;
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);
    private final EncodersOld encoders = new EncodersOld(ChassisName.TANK);
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;

    //Use this class to test new methods and anything else for auto

    public void runOpMode() {
        wheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);
        imu.initializeIMU(wheels,this);
        encoders.initialize(wheels, this);

        wheels.setZeroPowerBrakeBehavior();
        waitForStart();

        robot.startTime();
        try {
            executeAutonomousLogic();
        } catch (KillOpModeException e) {}
    }

    protected void executeAutonomousLogic() {
        //double ticksToInches=288/(Math.PI*6.125);

        //encoders.moveInchesEncoders(telemetry, .15, 0.05,-1500);

        //sleep(5000);

        /*
        wheels.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         */

        //wheels.crabDrive("right", .6, 1000);

        //wheels.changedCrabDriveRight(.6, 1000);


        //encoders.crabInchesEncoderEdited(telemetry, 1, .3, 10);

        //wheels.frontRight.setPower(-1);
        //wheels.backLeft.setPower(-1);
        //wheels.sleepAndCheckActive(3000);

        //encoders.fixCrabdrive(telemetry, .3, .5, 20);

        /*
        double ratio = (.5/.4);


        wheels.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        wheels.frontLeft.setPower(.5);
        wheels.backRight.setPower(.5);
        wheels.frontRight.setPower(-.7);
        wheels.backLeft.setPower(-.7);

        wheels.sleepAndCheckActive(10000);

        //encoders.testFix(encoders.finalPower, .3);
        */

    }
}
