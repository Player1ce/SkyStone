package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;



@SuppressWarnings("StatementWithEmptyBody")
@TeleOp(name="Tank TeleOp", group="Skystone")
//@Disabled
public class TankTeleOp extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods();
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

    boolean a_waitforpush;

    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        robot.InitializeHardware(this);

        //spool setup
        //spool = hardwareMap.dcMotor.get("spool");

        //elevator = hardwareMap.dcMotor.get("elevator");

        //TODO: hookServo = hardwareMap.servo.get ("hookServo");

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

        robot.setPower(frontRightPower, frontLeftPower, backRightPower, backLeftPower);

        /* TODO
        if (gamepad1.x)  {
            hookServo.setPosition(0);
            //hookServo.setPosition(.47);
        }
        else if (gamepad1.y)  {
            hookServo.setPosition(1);
        }

         */


        //telemetry is used to show on the driver controller phone what the code sees
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
            telemetry.addData("a_waitforpush:", a_waitforpush);
        }else {
            telemetry.addData("F/R:", "FORWARD");
            telemetry.addData("a_waitforpush:", a_waitforpush);
        }
        telemetry.update();



        if (gamepad1.a) {
                if (a_waitforpush) {
                    reverse = !reverse;
                    a_waitforpush = false;
                }
        }
        else {
            a_waitforpush = true;
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
