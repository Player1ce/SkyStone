package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Encoders {
    //TODO set up ticks to enchis for get x and get y.

    ChassisName chassis;

    double xTarget;
    double yTarget;

    double xError;
    double yError;

    double xCorrection;
    double yCorrection;

    public Encoders (double startX, double startY, ChassisName chassisName) {
        x = startX;
        y = startY;
        chassis = chassisName;
    }

    private MecanumWheels wheels = new MecanumWheels(chassis);

    DcMotor xEncoder = wheels.frontLeft;
    DcMotor yEncoder = wheels.frontRight;


    double x;
    double y;


    public void initializeEncoders () {
        wheels.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheels.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getX () {
        return wheels.frontLeft.getCurrentPosition();
    }

    public double getY () {
        return wheels.frontRight.getCurrentPosition();
    }

    public void setxTarget (double target) { xTarget = target; }

    public void setyTarget (double target) { yTarget = target; }

    public double correctX (double xError) {
        if (getX() > xError) {
            xCorrection = -1;
        } else if (getX() < -xError) {
            xCorrection = 1;
        }
        return xCorrection;
    }

    public double corrextY() {
        if (getY() > yError) {
            yCorrection = -1;
        } else if (getX() < -xError) {
            yCorrection = 1;
        }
        return xCorrection;
    }

    public boolean testPosition (double xTarget, double yTarget) {
        if ((getX() == xTarget || getX() == 0) && (getY() == yTarget || getY() == 0)) {
            return true;
        } else {
            return false;
        }
    }

    protected void MoveInchesEncoders(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches,double ticksToInches) {

        wheels.ResetEncoders();

        setxTarget(ticksToInches*Inches);
        setyTarget(0);

        while (testPosition(xTarget, yTarget)){
            wheels.checkIsActive();

            double distance=Math.abs(x-xTarget);

            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.backRight.setPower(power);
            wheels.frontLeft.setPower(power);
            wheels.backLeft.setPower(power);
            wheels.frontRight.setPower(power);

            telemetry.addData("Moving Forward","Moving Forward "+power);
            // telemetry.addData("avg encoder value:", averagePos);
            telemetry.addData("distance:", distance);
           /* telemetry.addData("F/L encoder value:", frontLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("F/R encoder value:", frontRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/L encoder value:", backLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/R encoder value:", backRight.getCurrentPosition()*ticksToInches);*/
            telemetry.addData("encoder target:", Inches);
            telemetry.update();

        }

        wheels.StopMotors();

    }

}
