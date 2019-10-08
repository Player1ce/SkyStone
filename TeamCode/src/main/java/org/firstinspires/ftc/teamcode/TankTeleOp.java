package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;



@SuppressWarnings("StatementWithEmptyBody")
@TeleOp(name="Tank TeleOp", group="Skystone")
//@Disabled
public class TankTeleOp extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods();
    private ButtonOneShot aButtonLogic = new ButtonOneShot();

    Servo hookServo;

    boolean reverse = false;
    final double CONSTANT = 1.0;
    final double spoolConstant = 1.0;

    //DcMotor spool;

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
        robot.InitializeHardware(this);
        hookServo = hardwareMap.servo.get ("hookServo");

        //spool setup
        //spool = hardwareMap.dcMotor.get("spool");
    }

    public void loop() {

        //TODO test this for functionality. Is it worth it?
        robot.setPowerVars(gamepad1, reverse);
        telemetry.addData("x_left:", robot.x_left);
        telemetry.addData("x_right:", robot.x_right);
        telemetry.addData("y_left:", robot.y_left);
        x_left = robot.x_left;
        x_right = robot.x_right;
        y_left = robot.y_left;

        /* old reverse code
        x_left = gamepad1.left_stick_x;
        if (!reverse) {
            x_right = gamepad1.right_stick_x;
            y_left = -gamepad1.left_stick_y;
        } else {
            x_right = -gamepad1.right_stick_x;
            y_left = gamepad1.left_stick_y;
        }
         */

        frontRightPower = (y_left - x_right + x_left) * CONSTANT; //-right
        frontLeftPower = (y_left + x_right - x_left) * CONSTANT; //-right
        backRightPower = (y_left + x_right + x_left) * CONSTANT; //-right
        backLeftPower = (y_left - x_right - x_left) * CONSTANT;

        robot.setPower(frontRightPower, frontLeftPower, backRightPower, backLeftPower);


        //TODO: Test for values and functionality
        telemetry.addData("hookServo Position", hookServo.getPosition());
        if (gamepad1.x)  {
            hookServo.setPosition(0);
            //hookServo.setPosition(.47);
        }
        else if (gamepad1.y)  {
            hookServo.setPosition(1);
        }

        if (aButtonLogic.isPressed(gamepad1.a)) {
            reverse = !reverse;
        }


        //telemetry is used to show on the driver controller phone what the code sees
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        }else {
            telemetry.addData("F/R:", "FORWARD");
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
