package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


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

    /*
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor leftIntake;
    DcMotor rightIntake;

    Servo hookServo;
    Servo rampServo;
     */

    //TODO correct starting cars for drive
    private boolean reverse = true;
    private boolean highPower = false;
    private boolean hookServoEnable = false;
    private boolean rampServoUp = true;
    private final double HIGH_POWER = 1.0;
    private final double NORMAL_POWER = 0.5;
    private double rampPosition;


    public void init() {
        /* attaching configuration names to each motor; each one of these names must match the name
        of the motor in the configuration profile on the phone (spaces and capitalization matter)
        or else an error will occur
        */
        mecanumWheels.initializeWheels(this);
        servos.initializeServos(this);
        intake.initializeIntake(this);

        /*TODO we might want to use the variables initialized in the top instead of initializing them here.
           the change tests using the already initialized variables.
           to remove this we need to reference the class where the servo motor etc... is initialized
        */

        /*
        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        leftIntake = hardwareMap.dcMotor.get("leftIntake");
        rightIntake = hardwareMap.dcMotor.get("rightIntake");

        hookServo=hardwareMap.servo.get("hookServo");
        rampServo=hardwareMap.servo.get("rampServo");
        */

        //TODO I changed servos and intake to null for a full functionality test.
        //set up variables in respective classes.
        mecanumWheels.initialize(mecanumWheels.frontLeft, mecanumWheels.frontRight,
                mecanumWheels.backLeft, mecanumWheels.backRight);
        servos.setServoVars(servos.rampServo, servos.hookServo);
        intake.setIntakeVars(intake.leftIntake, intake.rightIntake);

        intake.setIntakeBrakes();

        //rampServo setup
        rampPosition = 0.4;
        servos.rampServo.setPosition(rampPosition);
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
        //hook movement control this might not be a good thing to make a method.
        servos.hookControl(hookServoEnable);


        //ramp servo -------------------------------
        if (rampServoButtonLogic.isPressed(gamepad1.left_bumper)) {
            rampServoUp = !rampServoUp;
        }
        //ramp movement control
        if (gamepad1.left_bumper) {
            if (rampPosition < .5) {
                rampPosition = rampPosition + 0.003;
            }
            servos.rampServo.setPosition(rampPosition);
        }
        if (gamepad1.right_bumper) {
            if (rampPosition > 0) {
                rampPosition = rampPosition - 0.003;
            }
            servos.rampServo.setPosition(rampPosition);
        }


        //intake ----------------------------------
        //TODO test getting intake power from the gamepad triggers level of depression.
        if (gamepad1.left_trigger > .01 && gamepad1.right_trigger == 0) {
            intake.leftIntake.setPower(-1);
            intake.rightIntake.setPower(1);
        }
        else if (gamepad1.right_trigger > .01 && gamepad1.left_trigger == 0) {
            intake.leftIntake.setPower(1);
            intake.rightIntake.setPower(-1);
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
        telemetry.addData("Power:", power);

        //TODO correct this telemetry
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        }else {
            telemetry.addData("F/R:", "FORWARD");
        }

        /* not important in current system
        if (rampServoUp) {
            telemetry.addData("RampServo:", "UP");
        }
        else {
            telemetry.addData("RampServo Position:", "DOWN");
        }
         */
        telemetry.update();
    }

}
