package org.firstinspires.ftc.GoBilda;

//imports all different classes needed such as TeleOP, Servo, and DcMotor
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.devices.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.teamcode.TeleOpMethods;

@TeleOp(name="GoBildaChassisTeleop", group="Skystone")
//@Disabled
public class GoBildaChassisTeleop extends OpMode {

    private TeleOpMethods robot = new TeleOpMethods(ChassisName.GOBILDA);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.GOBILDA);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.GOBILDA);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.GOBILDA);
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();

    private boolean reverse = false;
    private boolean highPower = true;

    private double HIGH_POWER = 1.0;
    private double NORMAL_POWER = 0.5;


    //DcMotor spool;

    public void init() {
        //attaching configuration names to each motor; each one of these names must match the name
        //of the motor in the configuration profile on the phone (spaces and capitalization matter)
        //or else an error will occur
        mecanumWheels.initializeWheels(this);
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
        telemetry.addData("Power:", power);
        //reverse telemetry
        telemetry.addData("F/R:", robot.reverseSense(reverse));

    }

}
