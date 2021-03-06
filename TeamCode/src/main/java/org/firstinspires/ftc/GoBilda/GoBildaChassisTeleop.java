package org.firstinspires.ftc.GoBilda;

//imports all different classes needed such as TeleOP, Servo, and DcMotor
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.MecanumWheels;

@TeleOp(name="GoBildaChassisTeleop", group="Skystone")
//@Disabled
public class GoBildaChassisTeleop extends OpMode {

    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.GOBILDA);
    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();

    private boolean reverse = false;
    private boolean highPower = true;


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
        double HIGH_POWER = 1.0;
        double NORMAL_POWER = 0.5;
        double power = highPower ? HIGH_POWER : NORMAL_POWER;

        mecanumWheels.setPowerFromGamepad(reverse, power, gamepad1.left_stick_x,
                gamepad1.right_stick_x, gamepad1.left_stick_y);


        //telemetry is used to show on the driver controller phone what the code sees
        telemetry.addData("Power:", power);
        //reverse telemetry

    }

}
