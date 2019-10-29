package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "TankAutonomous", group="Skystone")
public class TankAutonomous extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods("tank");
    final  MecanumWheels mecanumWheels=new MecanumWheels("tank");
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    Servo hookServo;
    Servo rampServo;

    public void runOpMode() {
        robot.InitializeHardware(this);

        /*TODO remove if this works
        hookServo=hardwareMap.servo.get("hookServo");
        rampServo=hardwareMap.servo.get("rampServo");

        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
         */

        mecanumWheels.initialize(frontLeft, frontRight, backLeft, backRight, hookServo, rampServo);
        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        moveHook(ServoPosition.UP);

        executeAutonomousLogic();

    }

    public void moveHook(ServoPosition position){
        if (position==ServoPosition.UP)  {
            hookServo.setPosition(.47);
        }
        else  {
            hookServo.setPosition(0);
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


