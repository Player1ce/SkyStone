package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Encoders {


    ChassisName chassis = ChassisName.TANK;
    double ticksToInches;

    public void setTicksToInches (ChassisName chassis) {
        if (chassis == ChassisName.TANK) {
            ticksToInches = 288 / (Math.PI * 6.125);
        }
    }

    public Encoders (double startX, double startY, ChassisName chassisName) {
        x = startX;
        y = startY;
        chassis = chassisName;
        setTicksToInches(chassis);
    }


    private MecanumWheels wheels = new MecanumWheels(chassis);

    DcMotor xEncoder = wheels.frontLeft;
    DcMotor yEncoder = wheels.frontRight;

    //TODO set up ticks to inches for get x and get y.
    double x;
    double y;

    double xTarget;
    double yTarget;

    double xError;
    double yError;

    double xCorrection;
    double yCorrection;



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

    public double correctX () {
        if (getX() > xError) {
            xCorrection = -1;
        } else if (getX() < -xError) {
            xCorrection = 1;
        }
        return xCorrection;
    }

    public double correctY() {
        if (getY() > yError) {
            yCorrection = -1;
        } else if (getX() < -xError) {
            yCorrection = 1;
        }
        return xCorrection;
    }

    public boolean testPosition (double xTarget, double yTarget) {
        if (getX() == xTarget && getY() == yTarget) {
            return true;
        } else {
            return false;
        }
    }

    protected void moveInchesEncoders(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches,double ticksToInches) {

        wheels.ResetEncoders();

        setyTarget(ticksToInches*Inches);
        setxTarget(0);

        while (testPosition(xTarget, yTarget)){
            wheels.checkIsActive();

            double distance=Math.abs(x-xTarget);

            //min motor power should be set to zero
            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, 1, 0, correctX(), power);

            /*
            wheels.backRight.setPower(power);
            wheels.frontLeft.setPower(power);
            wheels.backLeft.setPower(power);
            wheels.frontRight.setPower(power);
             */

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

    protected void crabInchesEncoder(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches,double ticksToInches) {

        wheels.ResetEncoders();

        setxTarget(ticksToInches*Inches);
        setyTarget(0);

        while (testPosition(xTarget, yTarget)){
            wheels.checkIsActive();

            double distance=Math.abs(x-xTarget) * ticksToInches;

            //min motor power should be set to zero
            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, 1, 0, power, correctY());

            /*
            wheels.backRight.setPower(power);
            wheels.frontLeft.setPower(power);
            wheels.backLeft.setPower(power);
            wheels.frontRight.setPower(power);
             */

            telemetry.addData("Moving Forward","Moving Forward "+power);
            // telemetry.addData("avg encoder value:", averagePos);
            telemetry.addData("distance:", distance);
           /* telemetry.addData("F/L encoder value:", frontLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("F/R encoder value:", frontRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/L encoder value:", backLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/R encoder value:", backRight.getCurrentPosition()*ticksToInches);*/
            telemetry.addData("encoder target:", Inches);
            telemetry.update();

            x = getX();
            y = getY();

        }

        wheels.StopMotors();

    }


    //TODO this won't work yet
    public void turnInchesEncoder(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches,double ticksToInches) {

        wheels.ResetEncoders();

        setxTarget(ticksToInches*Inches);
        setyTarget(0);

        while (testPosition(xTarget, 0)){
            wheels.checkIsActive();

            double distance=Math.abs(x-xTarget);

            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, 1, 1, 0, 0);

            /*
            wheels.backRight.setPower(power);
            wheels.frontLeft.setPower(power);
            wheels.backLeft.setPower(power);
            wheels.frontRight.setPower(power);
             */

            telemetry.addData("Moving Forward","Moving Forward "+power);
            // telemetry.addData("avg encoder value:", averagePos);
            telemetry.addData("distance:", distance);
           /* telemetry.addData("F/L encoder value:", frontLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("F/R encoder value:", frontRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/L encoder value:", backLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/R encoder value:", backRight.getCurrentPosition()*ticksToInches);*/
            telemetry.addData("encoder target:", Inches);
            telemetry.update();

            x = getX();
            y = getY();

        }

        wheels.StopMotors();


    }


}
