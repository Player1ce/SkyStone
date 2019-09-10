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


        //attaching configuration names to limit switches;
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



        //telemetry is used to show on the driver controller phone what the code sees
        //this particular telemetry shows the state of the top and bottom limit switch which wil/ either be true
        //or false since limit switches are booleans
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        }else {
            telemetry.addData("F/R:", "FORWARD");
            }
        telemetry.update();

        }

}