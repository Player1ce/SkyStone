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

    //DcMotor spool;


    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        robot.InitializeHardware(this);



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

        mecanumWheels.initialize(frontLeft, frontRight, backLeft, backRight, hookServo, rampServo, leftIntake, rightIntake);
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
        if (rampServoButtonLogic.isPressed(gamepad1.y)) {
            rampServoUp = !rampServoUp;
        }
        if (rampServoUp) {
            rampServo.setPosition(.4);
        }
        else {
            rampServo.setPosition(.16);
        }

        if (gamepad1.left_trigger > .5 && gamepad1.right_trigger == 0) {
            mecanumWheels.leftIntake.setPower(-1);
            mecanumWheels.rightIntake.setPower(1);
        } else if (gamepad1.right_trigger > .5 && gamepad1.left_trigger == 0) {
            mecanumWheels.leftIntake.setPower(1);
            mecanumWheels.rightIntake.setPower(-1);
        } else {
            mecanumWheels.leftIntake.setPower(0);
            mecanumWheels.rightIntake.setPower(0);
        }


        //rampServo tests
        //rampServo.setPosition(.16); //down
        //rampServo.setPosition(0.4); /up (mid)

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
