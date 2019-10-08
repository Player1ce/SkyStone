package org.firstinspires.ftc.teamcode;

//imports all different classes needed such as TeleOP, Servo, and DcMotor
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;

@TeleOp(name="GoBildaChassisTeleop", group="Skystone")
//@Disabled
public class GoBildaChassisTeleop extends OpMode {
    private RobotMethods robot = new TeleOpMethods();

    boolean reverse = false;

    //all motors are declared; letting the code know that these motors exist



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
    double spoolPower;


    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        robot.InitializeHardware(this);

        //spool = hardwareMap.dcMotor.get("spool");

        //making servo configuration names



        //attaching configuration names to limit switches;
        counterLid = 0;
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


        frontRightPower = (-y_left - x_right - x_left) * CONSTANT; //-right
        frontLeftPower = (y_left - x_right - x_left) * CONSTANT; //-right
        backRightPower = (-y_left - x_right + x_left) * CONSTANT; //-right
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

        robot.setPower(frontRightPower, frontLeftPower, backRightPower, backLeftPower);


        //telemetry is used to show on the driver controller phone what the code sees
        //this particular telemetry shows the state of the top and bottom limit switch which wil/ either be true
        //or false since limit switches are booleans
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        } else {
            telemetry.addData("F/R:", "FORWARD");
        }
        telemetry.update();

        /*
        //hook controller
        //TODO should be able to replace with: robot.hookController(gamepad1);
        if (gamepad1.left_bumper) {
            leftHook.setPosition(1);
            rightHook.setPosition(1);
        } else if (gamepad1.left_trigger == 1) {
            leftHook.setPosition(0);
            rightHook.setPosition(0);
        }
        */

        /*
        // Spool control: left for up, right for dowm.
        //WARNING: Be very careful with the speed here. The motor power is set based on how far the trigger is pulled.
        if (gamepad1.right_trigger > 0) {
            spool.setPower(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger > 0) {
            spool.setPower(-gamepad1.left_trigger)
        }
        */

    }

}
