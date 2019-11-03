package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;



@SuppressWarnings("StatementWithEmptyBody")
@TeleOp(name="Tank TeleOp", group="Skystone")
//@Disabled
public class TankTeleOp extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods("tank");
    final  MecanumWheels mecanumWheels=new MecanumWheels("tank");
    final ServoMethods servos = new ServoMethods("tank");
    final IntakeMethods intake = new IntakeMethods("tank");
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampServoButtonLogic = new ButtonOneShot();

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor leftIntake;
    DcMotor rightIntake;

    Servo hookServo;
    Servo rampServo;

    boolean reverse = false;
    boolean highPower = true;
    boolean hookServoEnable = false;
    boolean rampServoUp = true;
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    final double spoolConstant = 1.0;

    double rampPosition;

    //DcMotor spool;


    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        robot.InitializeHardware(this);
        servos.initializeServos(this);
        intake.initializeIntake(this);

        //TODO we might want to use the variables initialized in the top instead of initializing them here.
        //TODO the change tests using the already initialized variables.
        //TODO to remove this we need to refference the class where the servo motor etc... is initialized
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

        //TODO I changed servos and intake to null for a full functionality test.
        //set up variables in respective classes.
        mecanumWheels.initialize(frontLeft, frontRight, backLeft, backRight,
                null, null, null, null);
        servos.setServoVars(rampServo, hookServo);
        intake.setIntakeVars(leftIntake, rightIntake);

        intake.setIntakeBrakes();

        rampPosition = 0.4;
        rampServo.setPosition(rampPosition);
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
        if (rampServoButtonLogic.isPressed(gamepad1.left_bumper)) {
            rampServoUp = !rampServoUp;
        }
        if (gamepad1.left_bumper) {
            if (rampPosition < .5) {
                rampPosition = rampPosition + 0.003;
            }
            rampServo.setPosition(rampPosition);
        }
        if (gamepad1.right_bumper) {
            if (rampPosition > 0) {
                rampPosition = rampPosition - 0.003;
            }
            rampServo.setPosition(rampPosition);
        }
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

        telemetry.addData("hookServo Position", hookServo.getPosition());
        telemetry.addData("rampServo Position:", rampServo.getPosition());
        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);


        //if high power, use the high power constant, else use the normal power constant
        double power = highPower?HIGH_POWER:NORMAL_POWER;

        telemetry.addData("Power:", power);

        mecanumWheels.setPowerFromGamepad(reverse,power,gamepad1.left_stick_x,
                gamepad1.right_stick_x,gamepad1.left_stick_y);

        //telemetry is used to show on the driver controller phone what the code sees
        //use method instead? telemetry.addString(robot.reverseSense(reverse));
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        }else {
            telemetry.addData("F/R:", "FORWARD");
        }
        if (rampServoUp) {
            telemetry.addData("RampServo:", "UP");
        }
        else {
            telemetry.addData("RampServo Position:", "DOWN");
        }

        telemetry.update();


        /* spool code. Uncomment when we add it or to test a motor.
        if (gamepad1.right_trigger > 0) {
            spool.setPower(gamepad1.right_trigger * spoolConstant);
        } else if (gamepad1.left_trigger > 0) {
            spool.setPower(gamepad1.left_trigger * spoolConstant);
        }
         */
    }

}
