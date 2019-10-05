package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;



@SuppressWarnings("StatementWithEmptyBody")
@TeleOp(name="Tank TeleOp", group="FINAL Rover Ruckus")
//@Disabled


public class TankTeleOp extends OpMode {
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    //DcMotor spool;

    DcMotor elevator;

    DigitalChannel bottomLimitSwitch;
    DigitalChannel topLimitSwitch;

    Servo hookServo;

    boolean reverse = false;
    final double CONSTANT = 1.0;
    final double spoolConstant = 1.0;
    final double elevatorSpeed = .6;



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

        //spool setup
        //spool = hardwareMap.dcMotor.get("spool");

        //elevator = hardwareMap.dcMotor.get("elevator");

        hookServo = hardwareMap.servo.get ("hookServo");

       /* bottomLimitSwitch = hardwareMap.digitalChannel.get("bottomLimitSwitch");
        topLimitSwitch = hardwareMap.digitalChannel.get("topLimitSwitch");
*/

    }

    public void loop() {
        x_left = gamepad1.left_stick_x;


        if (!reverse) {
            x_right = gamepad1.right_stick_x;
            y_left = -gamepad1.left_stick_y;
        } else {
            x_right = -gamepad1.right_stick_x;
            y_left = gamepad1.left_stick_y;
        }


        frontRightPower = (y_left - x_right + x_left) * CONSTANT; //-right
        frontLeftPower = (y_left + x_right - x_left) * CONSTANT; //-right
        backRightPower = (y_left + x_right + x_left) * CONSTANT; //-right
        backLeftPower = (y_left - x_right - x_left) * CONSTANT;


        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);

        if (gamepad1.x)  {
            hookServo.setPosition(0);
            //hookServo.setPosition(.47);
        }
        else if (gamepad1.y)  {
            hookServo.setPosition(1);
        }


        //telemetry is used to show on the driver controller phone what the code sees
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        }else {
            telemetry.addData("F/R:", "FORWARD");
        }
        telemetry.update();

        if (gamepad1.b) {
            reverse = !reverse;
        }

        /* spool code uncomment when we add it or to test a motor.
        if (gamepad1.right_trigger > 0) {
            spool.setPower(gamepad1.right_trigger * spoolConstant);
        } else if (gamepad1.left_trigger > 0) {
            spool.setPower(gamepad1.left_trigger * spoolConstant);
        }
         */
    }

}