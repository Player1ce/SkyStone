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
    private TeleOpMethods robot = new TeleOpMethods();
    final  MecanumWheels mecanumWheels=new MecanumWheels("tank");
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();

    Servo hookServo;

    boolean reverse = false;
    boolean highPower = true;
    boolean hookServoEnable = false;
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    final double spoolConstant = 1.0;

    //DcMotor spool;


    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        robot.InitializeHardware(this);

        hookServo=hardwareMap.servo.get("hookServo");

        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");

        mecanumWheels.initialize(frontLeft, frontRight, backLeft, backRight);

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
            //hookServo.setPosition(.47);
        }
        else   {
            hookServo.setPosition(1);
        }

        telemetry.addData("hookServo Position", hookServo.getPosition());

        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);

        //if high power, use the high power constant, else use the normal power constant
        double power = highPower?HIGH_POWER:NORMAL_POWER;

        telemetry.addData("Power:", power);

        mecanumWheels.setPowerFromGamepad(reverse,power,gamepad1.left_stick_x,
                gamepad1.right_stick_x,gamepad1.left_stick_y);

        //telemetry is used to show on the driver controller phone what the code sees
        //use method instead? telemetry.addString(robot.reverseSensor(reverse));
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
