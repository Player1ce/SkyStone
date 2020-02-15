package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.ScissorLift;
import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;

@TeleOp(name="lift Test", group="Skystone")
//@Disabled
public class LiftTest extends OpMode {
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final ScissorLift lift = new ScissorLift(ChassisName.TANK, wheels);
    private final ButtonOneShot xButton = new ButtonOneShot();
    private final ButtonOneShot buttonOneShotY = new ButtonOneShot();
    private final ButtonOneShot buttonOneShotA = new ButtonOneShot();
    private boolean liftOpen = true;
    private boolean setPositions = true;
    String liftState;

    //Use this class to test new methods and anything else for auto

    public void init() {
        lift.initialize(this);


    }

    int count=0;

    public void loop() {
        /*if (gamepad1.left_trigger > 0) {
            lift.liftMotor.setPower(1);
        }
        else if (gamepad1.right_trigger > 0 && lift.limitSwitch.getState()) {
            lift.liftMotor.setPower(-1);
        }
        else {
            lift.liftMotor.setPower(0);
        }*/

        if (xButton.isPressed(gamepad1.x)) {
            lift.zeroEncoder();

         //   lift.setPosition(200);
        }


        if (buttonOneShotA.isPressed(gamepad1.a)){
            int target=0;
            switch (count) {
                case 0:
                    target=2500;
                    break;
                case 1:
                    target=5000;
                    break;
                case 2:
                    target=7500;
                    break;
                case 3:
                    target=10000;
                    break;
                case 4:
                    target=12500;
                    break;
                case 5:
                    target=15000;
                    break;
                case 6:
                    target=17500;
                    break;
                default:
                    target=0;
                    count=-1;
                    break;
            }
            count++;
            lift.setPosition(target);

        }

        /*
        if (buttonOneShotY.isPressed(gamepad1.y)) {
            liftOpen = !liftOpen;
        }

        if (buttonOneShotA.isPressed(gamepad1.a)) {
            setPositions = !setPositions;
        }
        if (setPositions) {
            if (liftOpen) {
                lift.setPosition(BasicPositions.OPEN);
                liftState = "open";
            } else if (!liftOpen) {
                lift.setPosition(BasicPositions.CLOSED);
                liftState = "closed";
            }
        }

         */

        telemetry.addData("limit:", lift.getLimitState());
      //  telemetry.addData("liftState:", liftState);
        telemetry.addData("position:", lift.getPosition());
        telemetry.addData("Power:", lift.liftMotor.getPower());
        telemetry.update();

    }
}
