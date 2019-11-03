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

    private TeleOpMethods robot = new TeleOpMethods("gobilda");
    final MecanumWheels mecanumWheels = new MecanumWheels("gobilda");
    private final ServoMethods servos = new ServoMethods("gobilda");
    private final IntakeMethods intake = new IntakeMethods("gobilda");
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();

    private boolean reverse = false;
    private boolean highPower = true;

    private final double power = 0.5;
    private double HIGH_POWER = 1.0;
    private double NORMAL_POWER = 0.5;


    //DcMotor spool;

    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        mecanumWheels.initializeWheels(this);
        servos.initializeServos(this);
        intake.initializeIntake(this);


        //TODO why is this here. The Initialize hardware method should take care of this.
        /*
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        */


        mecanumWheels.initialize(mecanumWheels.frontLeft, mecanumWheels.frontRight,
                mecanumWheels.backLeft, mecanumWheels.backRight);

    }

    public void loop() {
        //drive train ------------------------
        if (reverseButtonLogic.isPressed(gamepad1.b)) {
            reverse = !reverse;
        }
        if (powerChangeButtonLogic.isPressed(gamepad1.a)) {
            highPower = !highPower;
        }
        //if high power, use the high power constant, else use the normal power constant
        double power = highPower ? HIGH_POWER : NORMAL_POWER;

        mecanumWheels.setPowerFromGamepad(reverse, power, gamepad1.left_stick_x,
                gamepad1.right_stick_x, gamepad1.left_stick_y);


        //telemetry is used to show on the driver controller phone what the code sees
        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);
        telemetry.addData("Power:", power);
        //reverse telemetry
        if (reverse) {
            telemetry.addData("F/R:", "REVERSE");
        } else {
            telemetry.addData("F/R:", "FORWARD");
        }
        telemetry.update();

    }

}
