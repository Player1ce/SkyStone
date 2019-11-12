package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;


@Autonomous(name = "TankAutonomousRed", group="Skystone")
public class TankAutonomousRed extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods("tank");
    private final  MecanumWheels mecanumWheels=new MecanumWheels("tank");
    private final HookMethods hookServo = new HookMethods("tank");
    private final IntakeMethods intake = new IntakeMethods("tank");

    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;

    private double i;

    ColorSensor colorSensor;

    ColorSensorLogic frontColorSensor;

    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        intake.initializeIntake(this);
        hookServo.initializeHook(this);

        colorSensor = hardwareMap.get(ColorSensor.class, "frontColorSensor");

        frontColorSensor=new ColorSensorLogic(colorSensor);

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

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
        double ticksToInches = 288 / (Math.PI * 6.125);
        mecanumWheels.ForwardMoveInches(telemetry, 0.5, 23, ticksToInches);
        mecanumWheels.crabDrive("right", 0.7, 1250);

        //now drive until we see the red or blue plate
        int colors[] = frontColorSensor.getAverageColor(500);

        int redBaseline = colors[0];
        int blueBaseline = colors[2];

        int redTarget = redBaseline + 10;
        int blueTarget = blueBaseline + 7;

        telemetry.addData("Baseline Colors", colors[0] + "  " + colors[1] + "   " + colors[2]);
        telemetry.update();
        mecanumWheels.setPower(0.3, 0.3, 0.3, 0.3);

        while (colorSensor.red() < redTarget && colorSensor.blue() < blueTarget) {
            telemetry.addData("Colors", "-> " + colors[0] + "  " + colors[1] + "   " + colors[2]);
            telemetry.update();
            sleep(10);
        }
        mecanumWheels.StopMotors();
        telemetry.addData("Colors", "* " + colors[0] + "  " + colors[1] + "   " + colors[2]);
        telemetry.update();

        //mecanumWheels.ForwardMoveInches(telemetry, 0.5, 5, ticksToInches);

        moveHook(ServoPosition.DOWN);

        sleep(1000);
        mecanumWheels.BackwardMoveInches(telemetry, -0.5, 30, ticksToInches);

        sleep(1000);

        moveHook(ServoPosition.UP);

        mecanumWheels.crabDrive("left", 0.7, 2500);
        mecanumWheels.ForwardMoveInches(telemetry, 0.5, 13, ticksToInches);
        mecanumWheels.crabDrive("right", 0.7, 2000);

        sleep(500);

        mecanumWheels.crabDrive("left", 0.7, 1000);

        sleep(500);

        mecanumWheels.setPowerFromGamepad(false, .7, -10, 0, 0);

        sleep(1050);

        mecanumWheels.StopMotors();

        sleep(500);

        mecanumWheels.setPowerFromGamepad(true, .7, 0, 0, 10);

        while (colorSensor.red() < redTarget && colorSensor.blue() < blueTarget) {
            telemetry.addData("Colors", "-> " + colors[0] + "  " + colors[1] + "   " + colors[2]);
            telemetry.update();
            sleep(10);
            i += 1;
            if (i == 18) {
                intake.rampServo.setPosition(.2);
            }

            if (i == 35) {
                return;
            }
        }

        mecanumWheels.StopMotors();



    }

}
