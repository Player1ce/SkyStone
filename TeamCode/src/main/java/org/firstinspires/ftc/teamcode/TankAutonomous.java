package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "TankAutonomous", group="Skystone")
public class TankAutonomous extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods("tank");
    private final  MecanumWheels mecanumWheels = new MecanumWheels("tank");
    private final ServoMethods servos = new ServoMethods("tank");
    private final IntakeMethods intake = new IntakeMethods("tank");
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;


    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        servos.initializeServos(this);
        servos.initializeServos(this);
        /*
        hookServo=hardwareMap.servo.get("hookServo");
        rampServo=hardwareMap.servo.get("rampServo");

        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");

        DcMotor leftIntake = hardwareMap.dcMotor.get("leftIntake");
        DcMotor rightIntake = hardwareMap.dcMotor.get("rightIntake");
         */

        mecanumWheels.initialize(mecanumWheels.frontLeft, mecanumWheels.frontRight,
                mecanumWheels.backLeft, mecanumWheels.backRight);
        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        moveHook(ServoPosition.UP);

        executeAutonomousLogic();

    }

    public void moveHook(ServoPosition position){
        if (position==ServoPosition.UP)  {
            servos.hookServo.setPosition(.4);
        }
        else  {
            servos.hookServo.setPosition(0);
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


