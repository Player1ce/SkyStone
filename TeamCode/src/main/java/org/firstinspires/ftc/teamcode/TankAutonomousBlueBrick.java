package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@Autonomous(name = "TankAutonomousBlueBrick", group="Skystone")
public class TankAutonomousBlueBrick extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods("tank");
    private final MecanumWheels mecanumWheels = new MecanumWheels("tank");
    private final FoundationHook hookServo = new FoundationHook( "tank");
    private final SkystoneIntake intake = new SkystoneIntake("tnak");
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    double i;

    ColorSensor colorSensor;

    ColorSensorLogic frontColorSensor;

    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        hookServo.initializeHook(this);
        intake.initializeIntake(this);
        intake.setIntakeBrakes();

        colorSensor = hardwareMap.get(ColorSensor.class, "frontColorSensor");

        frontColorSensor = new ColorSensorLogic(colorSensor);

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }

        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        moveHook(ServoPosition.UP);

        executeAutonomousLogic();

    }

    public void moveHook(ServoPosition position) {
        if (position == ServoPosition.UP) {
            hookServo.hookServo.setPosition(.4);
        } else {
            hookServo.hookServo.setPosition(0);
        }
    }

    protected void executeAutonomousLogic() {
        double ticksToInches = 288 / (Math.PI * 6.125);

        int colors[] = frontColorSensor.getAverageColor(500);

        int redBaseline = colors[0];
        int blueBaseline = colors[2];

        int redTarget = redBaseline + 10;
        int blueTarget = blueBaseline + 7;

        mecanumWheels.ForwardMoveInches(telemetry, 0.5, 7, ticksToInches);

        mecanumWheels.setPowerFromGamepad(false, .7, -10, 0, 0);

        sleep(1000);

        mecanumWheels.StopMotors();

        sleep(500);


        mecanumWheels.setPowerFromGamepad(true, .7, 0, 0, 10);

        //added by Leefe for Destrehan qualifier

        while (colorSensor.red() < redTarget && colorSensor.blue() < blueTarget) {
            telemetry.addData("Colors", "-> " + colors[0] + "  " + colors[1] + "   " + colors[2]);
            telemetry.update();
            sleep(10);
            i += 1;
            if (i == 18) {
                intake.rampServo.setPosition(.2);
            }

            mecanumWheels.StopMotors();
        }


    }
}
