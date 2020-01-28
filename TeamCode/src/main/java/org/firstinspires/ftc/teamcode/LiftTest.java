package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.Navigation;
import org.firstinspires.ftc.devices.ScissorLift;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.logic.KillOpModeException;

@TeleOp(name="lift Test", group="Skystone")
//@Disabled
public class LiftTest extends OpMode {
    private final ScissorLift lift = new ScissorLift();
    private final ButtonOneShot liftControl = new ButtonOneShot();
    //Use this class to test new methods and anything else for auto

    public void init() {
        lift.initialize(this);


    }

    public void loop() {
        if (gamepad2.left_trigger > 0) {
            lift.liftMotor.setPower(1);
        }
        else if (gamepad2.right_trigger > 0) {
            lift.liftMotor.setPower(-1);
        }
        else {
            lift.liftMotor.setPower(0);
        }

    }
}
