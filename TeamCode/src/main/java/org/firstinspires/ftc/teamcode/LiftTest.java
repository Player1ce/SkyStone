package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.ScissorLift;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;

@TeleOp(name="lift Test", group="Skystone")
//@Disabled
public class LiftTest extends OpMode {
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final ScissorLift lift = new ScissorLift(ChassisName.TANK, wheels);
    private final ButtonOneShot xButton = new ButtonOneShot();
    //Use this class to test new methods and anything else for auto

    public void init() {
        lift.initialize(this);


    }

    public void loop() {
        if (gamepad1.left_trigger > 0) {
            lift.liftMotor.setPower(1);
        }
        else if (gamepad1.right_trigger > 0) {
            lift.liftMotor.setPower(-1);
        }
        else {
            lift.liftMotor.setPower(0);
        }

        if (xButton.isPressed(gamepad1.x)) {
            lift.resetEncoder();
        }

        telemetry.addData("position:", lift.getPosition());
        telemetry.update();

    }
}
