package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.devices.RingShooter;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;

@TeleOp(name="Shooter Tester", group = "UltimateGoal")
public class ShooterTester extends OpMode{
    private final ButtonOneShot buttonOneShotY = new ButtonOneShot();
    private final ButtonOneShot buttonOneShotX = new ButtonOneShot();
    private final RingShooter shooter = new RingShooter(ChassisName.GOBILDA);

    public void init() {
        shooter.initializeShooter(this);
    }

    public void loop() {
        if (gamepad1.left_trigger > 0) {
            shooter.shooterMotor.setPower(-1);
            telemetry.addData("Power: ", "-1");
        }
        else if (gamepad1.right_trigger > 0) {
           shooter.shooterMotor.setPower(1);
            telemetry.addData("Power: ", "1");
        }
        else {
            shooter.shooterMotor.setPower(0);
            telemetry.addData("Power: ", "off");
        }
    }
}
