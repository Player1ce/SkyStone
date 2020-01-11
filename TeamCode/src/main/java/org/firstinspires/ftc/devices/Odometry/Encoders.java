package org.firstinspires.ftc.devices.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class Encoders {

    ChassisName chassis;

    Orientation startOrientation;

    long time=0;

    public Encoders(ChassisName chassisName) {
        this.chassis = chassisName;
    }

    private MecanumWheels wheels;

    private DcMotor xEncoder, yEncoder;

    private double xTarget, yTarget;

    public double finalPower = 0;


    //actual diameter: 2.7812 in.
    //final double encoderWheelsInchesToTicks = 125/(Math.PI*2.7812);
    //final double inchesToTicks = (1/.0681);

    public void initialize(MecanumWheels wheels, OpMode opMode) {
        this.wheels = wheels;
        //xEncoder = wheels.frontLeft;
        //yEncoder = wheels.frontRight;
        xEncoder = opMode.hardwareMap.dcMotor.get("port 2");
        yEncoder = opMode.hardwareMap.dcMotor.get("port 3");
    }

    public double getX() {
        return xEncoder.getCurrentPosition();
    }

    public double getY() {
        return yEncoder.getCurrentPosition();
    }

    private void setxTarget(double target) {
        xTarget = target;
    }

    private void setyTarget(double target) {
        yTarget = target;
    }

}
