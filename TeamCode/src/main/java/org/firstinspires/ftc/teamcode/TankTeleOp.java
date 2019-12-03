package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;


@TeleOp(name="Tank TeleOp", group="Skystone")
//@Disabled
public class TankTeleOp extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
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


    public void init() {
        /* attaching configuration names to each motor; each one of these names must match the name
        of the motor in the configuration profile on the phone (spaces and capitalization matter)
        or else an error will occur
        */
        mecanumWheels.initializeWheels(this);
        hookServo.initializeHook(this);
        intake.initializeIntake(this);

        //TODO I changed servos and intake to null for a full functionality test.
        //set up variables in respective classes.

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


        //foundation hook -------------------------------
        if (hookServoButtonLogic.isPressed(gamepad1.x)) {
            hookServoEnable = !hookServoEnable;
        }
        if (hookServoEnable)  {
            hookServo.hookServo.setPosition(0);
        }
        else   {
            hookServo.hookServo.setPosition(.6);
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
                intake.rampServo.setPosition(.38);
            } else {
                intake.rampServo.setPosition(.29);
            }
        }
        if (directRampControl) {
            if (gamepad2.left_bumper) {
                if (rampPosition < .5) {
                    rampPosition = rampPosition + 0.003;
                }
                intake.rampServo.setPosition(rampPosition);
            }
            if (gamepad2.right_bumper) {
                if (rampPosition > .001) {
                    rampPosition = rampPosition - 0.003;
                }
                intake.rampServo.setPosition(rampPosition);
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
        telemetry.addData("encoder x vlaue:", mecanumWheels.frontLeft.getCurrentPosition());
        telemetry.addData("encoder y vlaue:", mecanumWheels.frontRight.getCurrentPosition());

        telemetry.addData("Power:", power);
        telemetry.addData("F/R:", robot.reverseSense(reverse));
        telemetry.addData("rampServoPosition:", rampPosition);
        telemetry.addData("hookServo Position", hookServo.hookServo.getPosition());
        telemetry.addData("rampServo Position:", intake.rampServo.getPosition());
        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);

        /* not important in current system
        if (rampServoUp) {
            telemetry.addData("RampServo:", "UP");
        }
        else {
            telemetry.addData("RampServo Position:", "DOWN");
        }
         */


        /*
        Color sensor diagnostics

        telemetry.addLine()
                .addData("r", colorSensor.red())
                .addData("g",  colorSensor.green())
                .addData("b",  colorSensor.blue());
        */

        telemetry.update();
    }

}
