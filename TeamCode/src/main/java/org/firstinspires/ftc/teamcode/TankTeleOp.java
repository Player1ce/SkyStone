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


@SuppressWarnings("StatementWithEmptyBody")
@TeleOp(name="Tank TeleOp", group="Skystone")
//@Disabled
public class TankTeleOp extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods("tank");
    final  MecanumWheels mecanumWheels=new MecanumWheels("tank");
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampDirectControl = new ButtonOneShot();

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor leftIntake;
    DcMotor rightIntake;

    Servo hookServo;
    Servo rampServo;

    boolean directRampControl = false;
    boolean reverse = true;
    boolean highPower = true;
    boolean hookServoEnable = false;
    boolean rampServoUp = false;
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    final double spoolConstant = 1.0;

    double rampPosition;

    //DcMotor spool;

    ColorSensor colorSensor;

    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        robot.InitializeHardware(this);

        colorSensor = hardwareMap.get(ColorSensor.class, "frontColorSensor");

        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        //TODO why is this here. The Initialize hardware method should take care of this.
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");

        DcMotor leftIntake = hardwareMap.dcMotor.get("leftIntake");
        DcMotor rightIntake = hardwareMap.dcMotor.get("rightIntake");



        hookServo=hardwareMap.servo.get("hookServo");
        rampServo=hardwareMap.servo.get("rampServo");

        mecanumWheels.initialize(frontLeft, frontRight, backLeft, backRight,
                hookServo, rampServo, leftIntake, rightIntake);
        mecanumWheels.setIntakeBrakes();

        //spool setup
        //spool = hardwareMap.dcMotor.get("spool");
    }

    public void loop() {


        if (reverseButtonLogic.isPressed(gamepad1.b)) {
            reverse = !reverse;
        }
        if (powerChangeButtonLogic.isPressed(gamepad1.a)) {
            highPower = !highPower;
        }
        if (hookServoButtonLogic.isPressed(gamepad1.x)) {
            hookServoEnable = !hookServoEnable;
        }
        if (hookServoEnable)  {
            hookServo.setPosition(0);
        }
        else   {
            hookServo.setPosition(.6);
            //hookServo.setPosition(.47);
        }
        //gamepad 2 functions
        if (rampServoButtonLogic.isPressed(gamepad2.y)) {
            rampServoUp = !rampServoUp;
        }
        if (rampDirectControl.isPressed(gamepad2.b)) {
            directRampControl = !directRampControl;
        }
        if (!directRampControl) {
            if (rampServoUp) {
                rampServo.setPosition(.38);
            } else {
                rampServo.setPosition(.29);
            }
        }
        if (directRampControl) {
            if (gamepad2.left_bumper) {
                if (rampPosition < .5) {
                    rampPosition = rampPosition + 0.003;
                }
                rampServo.setPosition(rampPosition);
            }
            if (gamepad2.right_bumper) {
                if (rampPosition > .001) {
                    rampPosition = rampPosition - 0.003;
                }
                rampServo.setPosition(rampPosition);
            }
        }


        if (gamepad2.left_trigger > 0) {
            mecanumWheels.leftIntake.setPower(-gamepad2.left_trigger * .7);
            mecanumWheels.rightIntake.setPower(gamepad2.left_trigger * .7);
        }
        else if (gamepad2.right_trigger > 0) {
            mecanumWheels.leftIntake.setPower(gamepad2.right_trigger * .7);
            mecanumWheels.rightIntake.setPower(-gamepad2.right_trigger * .7);
        }
        else {
            mecanumWheels.leftIntake.setPower(0);
            mecanumWheels.rightIntake.setPower(0);
        }
        //up .5 down .05 output .35

        telemetry.addData("hookServo Position", hookServo.getPosition());
        telemetry.addData("rampServo Position:", rampServo.getPosition());
        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);
        telemetry.addData("intakeleft", gamepad2.left_trigger);
        telemetry.addData("intakeright", gamepad2.right_trigger);

        //if high power, use the high power constant, else use the normal power constant
        double power = highPower?HIGH_POWER:NORMAL_POWER;

        telemetry.addData("Power:", power);

        mecanumWheels.setPowerFromGamepad(reverse,power,gamepad1.left_stick_x,
                gamepad1.right_stick_x,gamepad1.left_stick_y);

        //telemetry is used to show on the driver controller phone what the code sees
        //use method instead? telemetry.addString(robot.reverseSense(reverse));
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

        /* spool code. Uncomment when we add it or to test a motor.
        if (gamepad1.right_trigger > 0) {
            spool.setPower(gamepad1.right_trigger * spoolConstant);
        } else if (gamepad1.left_trigger > 0) {
            spool.setPower(gamepad1.left_trigger * spoolConstant);
        }
         */
    }

}
