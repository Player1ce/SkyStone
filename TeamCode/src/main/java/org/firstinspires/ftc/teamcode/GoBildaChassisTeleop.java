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

    private TeleOpMethods robot = new TeleOpMethods();
    final MecanumWheels mecanumWheels = new MecanumWheels("gobilda");
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();

    boolean reverse = false;
    boolean highPower = true;
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    final double spoolConstant = 1.0;

    double x_left;
    double x_right;
    double y_left;

    double frontRightPower; //-right
    double frontLeftPower; //-right
    double backRightPower; //-right
    double backLeftPower;

    //DcMotor spool;

    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        robot.InitializeHardware(this);

        //spool = hardwareMap.dcMotor.get("spool");

    }

    public void loop() {
        if (reverseButtonLogic.isPressed(gamepad1.b)) {
            reverse = !reverse;
        }
        if (powerChangeButtonLogic.isPressed(gamepad1.a)) {
            highPower = !highPower;
        }

        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);

        //if high power, use the high power constant, else use the normal power constant
        double power = highPower?HIGH_POWER:NORMAL_POWER;

        telemetry.addData("Power:", power);

        mecanumWheels.setPowerFromGamepad(reverse, power, gamepad1.left_stick_x,
                gamepad1.left_stick_x, gamepad1.left_stick_y);


        //telemetry is used to show on the driver controller phone what the code sees
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        } else {
            telemetry.addData("F/R:", "FORWARD");
        }
        telemetry.update();

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
