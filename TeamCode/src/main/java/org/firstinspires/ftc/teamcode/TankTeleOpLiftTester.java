package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name="Tank TeleOp Lift Test", group="Skystone")
//@Disabled
public class TankTeleOpLiftTester extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);

    DistanceSensor distanceSensor;
    public DcMotor liftMotor;

    public void init() {
        distanceSensor = hardwareMap.get(DistanceSensor.class, "liftDistanceSensor");
        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {

        if (gamepad1.right_bumper) {
            liftMotor.setPower(1);
        }
        else if (gamepad1.left_bumper) {
            liftMotor.setPower(-1);
        }
        else {
            liftMotor.setPower(0);
        }

        double distance=distanceSensor.getDistance(DistanceUnit.MM);

        int pos=liftMotor.getCurrentPosition();

        telemetry.addData("Encoder:", pos);
        telemetry.addData("Distance:", distance);
        telemetry.update();
    }

}
