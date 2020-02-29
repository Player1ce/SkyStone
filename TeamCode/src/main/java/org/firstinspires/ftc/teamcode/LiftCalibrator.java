package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.ScissorLift;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;


@TeleOp(name="lift calibrator", group="Skystone")
//@Disabled
public class LiftCalibrator extends OpMode {
    private final MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
    private final ScissorLift lift = new ScissorLift(wheels);
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

    boolean calibrating;

    int count=0;

    public void loop() {
        if (calibrating) {
            if (lift.limitSwitch.getState()) {
                lift.liftMotor.setPower(-0.5);
            }
            else {
                lift.liftMotor.setPower(0);
                lift.resetEncoder();
                calibrating=false;
            }

        }
        else if (xButton.isPressed(gamepad1.x)) {
            calibrating=true;
            lift.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        telemetry.addData("limit:", lift.getLimitState());
        //  telemetry.addData("liftState:", liftState);
        //telemetry.addData("set position:", setPositions);
        telemetry.addData("position:", lift.getPosition());
        telemetry.addData("Power:", lift.liftMotor.getPower());
        telemetry.addData("calibrating:", calibrating);
        telemetry.update();

    }
}
