package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "TankAutonomous", group="Skystone")
public class TankAutonomous extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods();
    final  MecanumWheels mecanumWheels=new MecanumWheels("tank");
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;


    Servo hookServo;

    public void runOpMode() {
        robot.InitializeHardware(this);
        //robot.InitializeIMU();

        hookServo=hardwareMap.servo.get("hookServo");


        //TODO remove lines 27-34 the motors are initialized with a method
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");

        mecanumWheels.initialize(frontLeft, frontRight, backLeft, backRight);
        mecanumWheels.setZeroPowerBrakeBehavior();

        waitForStart();
        robot.startTime();

        //robot.moveHook("up");

        hookServo.setPosition(25);

        executeAutonomousLogic();

    }

    //TODO create this method above the loop?
    protected void executeAutonomousLogic() {
        double ticksToInches=288/(Math.PI*6.125);
        mecanumWheels.ForwardMoveInches(telemetry, NORMAL_POWER, 20, ticksToInches);

    }


}
