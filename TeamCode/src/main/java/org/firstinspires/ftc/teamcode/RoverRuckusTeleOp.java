package org.firstinspires.ftc.teamcode;

//imports all different classes needed such as TeleOP, Servo, and DcMotor
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Rover Ruckus Teleop FINAL", group="FINAL Rover Ruckus")
//@Disabled
public class RoverRuckusTeleOp extends OpMode {

    //all motors are declared; letting the code know that these motors exist
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    DcMotor elevator;

    DcMotor intake;

    //all servos are declared
    Servo pivot;

    Servo rightServo;
    Servo leftServo;
    Servo midServo;

    Servo lid;

    DcMotor arm;

    //limit switches for hanging arm are declared
    DigitalChannel bottomLimitSwitch;
    DigitalChannel topLimitSwitch;

    boolean reverse = false;

    //constants delcared; used to allow us to decrease available motor power which yields greater precision
    final double CONSTANT = 1.0;
    final double ELEVATOR_SPEED = .6;
    final double INTAKE_SPEED = .7;

    int counterLid;

    double x_left;
    double x_right;
    double y_left;

    double frontRightPower; //-right
    double frontLeftPower; //-right
    double backRightPower; //-right
    double backLeftPower;


    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        elevator = hardwareMap.dcMotor.get("elevator");

        intake = hardwareMap.dcMotor.get("intake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lid = hardwareMap.servo.get("lid");

        //attaching configuration names to servos
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
        midServo = hardwareMap.servo.get("midServo");

        pivot = hardwareMap.servo.get("pivot");

        arm = hardwareMap.dcMotor.get("arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //attaching configuration names to limit switches;
        bottomLimitSwitch = hardwareMap.digitalChannel.get("bottomLimitSwitch");
        topLimitSwitch = hardwareMap.digitalChannel.get("topLimitSwitch");

        counterLid = 0;
    }

    public void loop() {

        //allows driver to have all four direction steering by using mechanum wheels


/*        double x_left = gamepad1.left_stick_x;
        double x_right = -gamepad1.right_stick_x;
        double y_left = gamepad1.left_stick_y;

        double frontRightPower = (y_left - x_right + x_left) * CONSTANT; //-right
        double frontLeftPower = (-y_left - x_right + x_left) * CONSTANT; //-right
        double backRightPower = (-y_left - x_right - x_left) * CONSTANT; //-right
        double backLeftPower = (y_left - x_right - x_left) * CONSTANT; //-right*/

        x_left = gamepad1.left_stick_x;

        if (!reverse) {
            x_right = gamepad1.right_stick_x;
            y_left = -gamepad1.left_stick_y;
        } else {
            x_right = -gamepad1.right_stick_x;
            y_left = gamepad1.left_stick_y;
        }


        frontRightPower = (y_left - x_right - x_left) * CONSTANT; //-right
        frontLeftPower = (y_left + x_right + x_left) * CONSTANT; //-right
        backRightPower = (y_left + x_right - x_left) * CONSTANT; //-right
        backLeftPower = (y_left - x_right + x_left) * CONSTANT;


        /*if (!reverse) {
            frontRight.setPower(frontRightPower);
            frontLeft.setPower(frontLeftPower);
            backRight.setPower(backRightPower);
            backLeft.setPower(backLeftPower);
        } else {
            frontRight.setPower(-frontRightPower);
            frontLeft.setPower(-frontLeftPower);
            backRight.setPower(-backRightPower);
            backLeft.setPower(-backLeftPower);
        }*/

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);


        //code for the hanging arm's elevator
        //right and left trigger are float values meaning they can accept variable control
        //this allows us to determine how fast or slow we want to ascend and descend.
        if (gamepad2.right_trigger > 0.1 && topLimitSwitch.getState()) {
            elevator.setPower(gamepad2.right_trigger);   //

        } else if (gamepad2.left_trigger > 0.1 && bottomLimitSwitch.getState()) {
            elevator.setPower(-gamepad2.left_trigger); //
        } else {
            elevator.setPower(0);
        }
        //telemetry is used to show on the driver controller phone what the code sees
        //this particular telemetry shows the state of the top and bottom limit switch which wil/ either be true
        //or false since limit switches are booleans
        telemetry.addData("Top Limit Switch:",topLimitSwitch.getState());
        telemetry.addData("Bottom Limit Switch:", bottomLimitSwitch.getState());
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        }else {
            telemetry.addData("F/R:", "FORWARD");
            }
        telemetry.update();

        //Code to push marker out in TeleOp in case autonomous is unsuccessful
        if (gamepad2.x) {
            pivot.setPosition(0);
        }
        else if (gamepad2.back) {
            pivot.setPosition(1);
        } else {

        }

        //right paddle control
        if (gamepad2.dpad_up) {
            rightServo.setPosition(0);
        } else if (gamepad2.dpad_down) {
            rightServo.setPosition(1);
        } else{

        }
        //left paddle control
        if (gamepad2.dpad_left) {
            leftServo.setPosition(.85);
        } else if (gamepad2.dpad_right) {
            leftServo.setPosition(.03);
        } else {

        }
        //middle paddle control
        if (gamepad2.a) {
            midServo.setPosition(.48);
        } else if (gamepad2.y){
            midServo.setPosition(.91);
        } else {

        }





        //INTAKE
        if (gamepad2.left_stick_y > .05) {
            arm.setPower(gamepad2.left_stick_y);
        } else if (gamepad2.left_stick_y < -.05) {
            //intake.setPosition(Math.abs(gamepad2.left_stick_y) * -.5 + .5);
            arm.setPower(gamepad2.left_stick_y);
        } else {
            arm.setPower(0);
        }

        //arm of intake
        if (gamepad2.right_stick_y > .05) {
            intake.setPower(.9);
        } else if (gamepad2.right_stick_y < -.05) {
            intake.setPower(-.9);
        } else {
            intake.setPower(0);
        }

        //lid of intake
        if (gamepad2.right_bumper) {
            lid.setPosition(0);
        } else if (gamepad2.left_bumper) {
            lid.setPosition(.4);
        } else {

        }

        if (gamepad1.b) {
            reverse = true;
        } else if (gamepad1.x) {
            reverse = false;
        } else {

        }


    }




}













