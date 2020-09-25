package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class EncodersFix {
    //TODO do we need to run using encoder. Run using encoder is a PID method so it will interfere with our power setup?
    // also, xEncoder will be sideways so it won't really relate to movement.  Finally, i think we can get position without using encoders.
    //TODO we need to move the encoders to separate ports on the rev hub so the wheels cna run with encoders.


    public EncodersFix(ChassisName chassisName) {
    }

    private MecanumWheels wheels;

    private DcMotor xEncoder, yEncoder;

    public double xTarget, yTarget;


    //actual diameter: 2.7812 in.
    //final double encoderWheelsInchesToTicks = 125/(Math.PI*2.7812);
    //final double inchesToTicks = (1/.0681);

    public void initialize(MecanumWheels wheels, OpMode opMode) {
        this.wheels = wheels;
        //xEncoder = wheels.frontLeft;
        //yEncoder = wheels.frontRight;
        xEncoder = opMode.hardwareMap.dcMotor.get("leftIntake");
        //we are stealing port 3's encoder input
        yEncoder = opMode.hardwareMap.dcMotor.get("rightIntake");
    }

    public double getX() {
        return xEncoder.getCurrentPosition();
    }

    public double getY() {
        return yEncoder.getCurrentPosition();
    }

    public void setXTarget(double target) {
        xTarget = target;
    }

    public void setYTarget(double target) { yTarget = target; }


    public void resetPosition() {
        xEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        wheels.sleepAndCheckActive(50);

        xEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //need to add conversion factor to this
    public void ForwardMoveInches(Telemetry telemetry, double motorPower, double Inches) {
        moveTicksEncoders(telemetry, motorPower, 0.1, Inches);
    }


    public void BackwardMoveInches(Telemetry telemetry, double motorPower, double Inches) {
        moveTicksEncoders(telemetry, motorPower, -0.1, Inches);
    }

    public void moveTicksEncoders(Telemetry telemetry, double MotorPower, double MinMotorPower, double ticks) {

        resetPosition();

        double horizontalDistance;
        double distance;
        double power;
        double xPower;

        setYTarget(ticks);

        //while the distance is greater than half the tolerance (tolerance is doubled b/c of two directions)
        while (Math.abs(yTarget - getY()) > 10) {
            wheels.checkIsActive();
            horizontalDistance = getX();

            distance = yTarget - getY();

            //min motor power should be set to zero
            power= MecanumWheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);
            xPower= MecanumWheels.calculateProportionalMotorPower(0.0015,horizontalDistance,MotorPower,MinMotorPower);

            //make sure these values work (why is left_stick_y set to -1?)
            wheels.setPowerFromGamepad(false, power , 0, horizontalDistance>0?-0.1:0.1 , -1 );


            telemetry.addData("Moving Forward", power);
            // telemetry.addData("avg encoder value:", averagePos);
            telemetry.addData("distance:", distance);
            telemetry.addData("hor dist:", horizontalDistance);
            telemetry.addData("encoder target:", ticks);
            telemetry.addData("power:", power);
            telemetry.update();

        }
        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);

        telemetry.addData("final Y:", getY());
        telemetry.addData("Y target", yTarget);
        telemetry.update();


    }

    public void crabInchesEncoder(Telemetry telemetry, double MotorPower, double MinMotorPower, double ticks) {

        resetPosition();

        //setXTarget(encoderWheelsInchesToTicks*Inches);
        setXTarget(ticks);
        double distance;
        double power;

        while (Math.abs(yTarget - getX()) > 10) {
            wheels.checkIsActive();

            distance = xTarget - getX();

            power = MecanumWheels.calculateProportionalMotorPower(0.0015, distance, MotorPower, MinMotorPower);

            wheels.setPowerFromGamepad(false, power, 0, 1, 0);

            telemetry.addData("Moving Forward", power);
            // telemetry.addData("avg encoder value:", averagePos);
            telemetry.addData("distance:", distance);
            telemetry.addData("encoder target:", ticks);
            telemetry.update();


        }

        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);

        telemetry.addData("final x:", getX());
        telemetry.addData("x target", xTarget);
        telemetry.update();

    }

    public double getXDirection() {
        return Double.compare(xTarget, getX());
    }

    public double getYDirection() {
        return Double.compare(getY(), yTarget);
    }

}
