package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@TeleOp(name="Tank TeleOp", group="Skystone")
//@Disabled
public class TankTeleOp extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods("tank");
    private final  MecanumWheels mecanumWheels = new MecanumWheels("tank");
    private final ServoMethods servos = new ServoMethods("tank");
    private final IntakeMethods intake = new IntakeMethods("tank");
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampDirectControl = new ButtonOneShot();


    //TODO correct starting cars for drive
    private boolean reverse = true;
    private boolean highPower = true;
    private boolean hookServoEnable = false;
    private boolean rampServoUp = false;
    private boolean directRampControl = false;
    private final double HIGH_POWER = 1.0;
    private final double NORMAL_POWER = 0.5;
    private double rampPosition;

    ColorSensor colorSensor;

    public void init() {
        /* attaching configuration names to each motor; each one of these names must match the name
        of the motor in the configuration profile on the phone (spaces and capitalization matter)
        or else an error will occur
        */
        mecanumWheels.initializeWheels(this);
        servos.initializeServos(this);
        intake.initializeIntake(this);

        colorSensor = hardwareMap.get(ColorSensor.class, "frontColorSensor");

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        /*TODO we might want to use the variables initialized in the top instead of initializing them here.
           the change tests using the already initialized variables.
           to remove this we need to reference the class where the servo motor etc... is initialized
        */

        //TODO I changed servos and intake to null for a full functionality test.
        //set up variables in respective classes.
        mecanumWheels.initialize(mecanumWheels.frontLeft, mecanumWheels.frontRight,
                mecanumWheels.backLeft, mecanumWheels.backRight);
        servos.setServoVars(servos.rampServo, servos.hookServo);
        intake.setIntakeVars(intake.leftIntake, intake.rightIntake);

        intake.setIntakeBrakes();
    }

    public void loop() {

        //drive train --------------------------------
        if (powerChangeButtonLogic.isPressed(gamepad1.a)) {
            highPower = !highPower;
        }
        if (reverseButtonLogic.isPressed(gamepad1.b)) {
            reverse = !reverse;
        }
        //if high power, use the high power constant, else use the normal power constant
        double power = highPower ? HIGH_POWER : NORMAL_POWER;

        mecanumWheels.setPowerFromGamepad(reverse, power,gamepad1.left_stick_x,
                gamepad1.right_stick_x,gamepad1.left_stick_y);


        //hook servo -------------------------------
        if (hookServoButtonLogic.isPressed(gamepad1.x)) {
            hookServoEnable = !hookServoEnable;
        }
        if (hookServoEnable)  {
            servos.hookServo.setPosition(0);
        }
        else   {
            servos.hookServo.setPosition(.6);
            //hookServo.setPosition(.47);
        }

        //gamepad 2 functions-------------------

        //ramp servo control -------------------
        if (rampServoButtonLogic.isPressed(gamepad2.y)) {
            rampServoUp = !rampServoUp;
        }
        if (rampDirectControl.isPressed(gamepad2.b)) {
            directRampControl = !directRampControl;
        }
        if (!directRampControl) {
            if (rampServoUp) {
                servos.rampServo.setPosition(.38);
            } else {
                servos.rampServo.setPosition(.29);
            }
        }
        if (directRampControl) {
            if (gamepad2.left_bumper) {
                if (rampPosition < .5) {
                    rampPosition = rampPosition + 0.003;
                }
                servos.rampServo.setPosition(rampPosition);
            }
            if (gamepad2.right_bumper) {
                if (rampPosition > .001) {
                    rampPosition = rampPosition - 0.003;
                }
                servos.rampServo.setPosition(rampPosition);
            }
        }

        //intake control ----------------------------
        if (gamepad2.left_trigger > 0) {
            intake.leftIntake.setPower(-gamepad2.left_trigger * .7);
            intake.rightIntake.setPower(gamepad2.left_trigger * .7);
        }
        else if (gamepad2.right_trigger > 0) {
            intake.leftIntake.setPower(gamepad2.right_trigger * .7);
            intake.rightIntake.setPower(-gamepad2.right_trigger * .7);
        }
        else {
            intake.leftIntake.setPower(0);
            intake.rightIntake.setPower(0);
        }


        //telemetry ------------------------------
        //telemetry is used to show on the driver controller phone what the code sees
        telemetry.addData("hookServo Position", servos.hookServo.getPosition());
        telemetry.addData("rampServo Position:", servos.rampServo.getPosition());
        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);
        telemetry.addData("intakeleft", gamepad2.left_trigger);
        telemetry.addData("intakeright", gamepad2.right_trigger);
        telemetry.addData("Power:", power);

        //TODO correct this telemetry
        if (reverse) {
            telemetry.addData("F/R:", "FORWARD");
        }else {
            telemetry.addData("F/R:", "REVERSE");
        }
        telemetry.addData("rampServoPosition:", rampPosition);

        /*
        Color sensor diagnostics

        telemetry.addLine()
                .addData("r", colorSensor.red())
                .addData("g",  colorSensor.green())
                .addData("b",  colorSensor.blue());


        telemetry.update();
        */
        telemetry.update();
    }

}
