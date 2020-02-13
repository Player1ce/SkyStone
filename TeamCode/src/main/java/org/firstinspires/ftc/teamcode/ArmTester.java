package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;



@TeleOp(name="Arm Tester", group = "Skystone")
public class ArmTester extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);

    public Servo armServo;

    double position= .2;

    public void init() {
        armServo = hardwareMap.servo.get("armServo");
    }

    public void loop() {

        if (gamepad1.right_bumper) {
            position += .003;
        }
        else if (gamepad1.left_bumper) {
            position += -.003;
        } else if (gamepad1.a) {
            position = .2;
        } else if (gamepad1.b) {
            position = .877;
        }

        armServo.setPosition(position);


        telemetry.addData("Encoder:", armServo.getPosition());
        telemetry.update();
    }

}
