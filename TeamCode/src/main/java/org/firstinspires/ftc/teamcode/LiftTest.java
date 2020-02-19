package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
    private boolean liftUp = true;
    private boolean setPositions = true;
    String liftState;
    boolean directControl = true;

    //Use this class to test new methods and anything else for auto

    public void init() {
        lift.initialize(this);


    }

    int count=0;

    public void loop() {

        if (xButton.isPressed(gamepad1.x)) {
            if (!directControl) {
                lift.liftMotor.setTargetPosition(lift.liftMotor.getCurrentPosition());
                wheels.sleepAndCheckActive(500);
                lift.liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.liftMotor.setPower(0);
                directControl = !directControl;
            }
            lift.zeroEncoder();
        }

        if (gamepad1.left_trigger > 0) {
            if (!directControl) {
                lift.liftMotor.setTargetPosition(lift.liftMotor.getCurrentPosition());
                wheels.sleepAndCheckActive(500);
                lift.liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.liftMotor.setPower(0);
                directControl = !directControl;
            }
            lift.liftMotor.setPower(gamepad1.left_trigger);

        }
        else if (gamepad1.right_trigger > 0 && lift.limitSwitch.getState()) {
            if (!directControl) {
                lift.liftMotor.setTargetPosition(lift.liftMotor.getCurrentPosition());
                wheels.sleepAndCheckActive(500);
                lift.liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.liftMotor.setPower(0);
                directControl = !directControl;
            }
            lift.liftMotor.setPower(-gamepad1.right_trigger);
        }

        else if (lift.liftMotor.getMode() == DcMotor.RunMode.RUN_USING_ENCODER){
            if (!directControl) {
                lift.liftMotor.setTargetPosition(lift.liftMotor.getCurrentPosition());
                wheels.sleepAndCheckActive(500);
                lift.liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.liftMotor.setPower(0);
                directControl = !directControl;
            }
            lift.liftMotor.setPower(0);
        }

        if (buttonOneShotA.isPressed(gamepad1.a)) {
            directControl = false;
            lift.setScissorHeights();
        }


        /*
        if (buttonOneShotY.isPressed(gamepad1.y)) {
            liftUp = !liftUp;
        }

        if (buttonOneShotA.isPressed(gamepad1.a)) {
            setPositions = !setPositions;
        }
        if (setPositions) {
            if (liftUp) {
                lift.setTargetPosition(BasicPositions.OPEN);
                liftState = "up";
            } else if (!liftUp) {
                lift.setTargetPosition(BasicPositions.CLOSED);
                liftState = "down";
            }
        }

         */

        telemetry.addData("limit:", lift.getLimitState());
        //  telemetry.addData("liftState:", liftState);
        //telemetry.addData("set position:", setPositions);
        telemetry.addData("position:", lift.getPosition());
        telemetry.addData("Power:", lift.liftMotor.getPower());
        telemetry.addData("direct control:", directControl);
        telemetry.addData("runmode:", lift.liftMotor.getMode());
        telemetry.update();

    }
}
