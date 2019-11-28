package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Encoders {
    //TODO do we need to run using encoder. Run using encoder is a PID method so it will interfere with our power setup?
    // also, xEncoder will be sideways so it won't really relate to movement.  Finally, i think we can get position without using encoders.
    //TODO we need to move the encoders to separate ports on the rev hub so the wheels cna run with encoders.


    ChassisName chassis = ChassisName.TANK;
    double ticksToInches;

    public Encoders (double startX, double startY, ChassisName chassisName) {
        x = startX;
        y = startY;
        chassis = chassisName;
        if (chassis == ChassisName.TANK) {
            ticksToInches = 288 / (Math.PI * 6.125);
        }
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

    /*
    double xCorrection;
    double yCorrection;
     */

    public double getX () {
        return xEncoder.getCurrentPosition();
    }

    public double getY () {
        return yEncoder.getCurrentPosition();
    }

    private void setxTarget (double target) { xTarget = target; }

    private void setyTarget (double target) { yTarget = target; }

    private double correctX () {
        double xCorrection = 0;
        if ((xTarget-getX()) > xError) {
            xCorrection = -1;
        } else if ((xTarget - getX()) < -xError) {
            xCorrection = 1;
        }
        return xCorrection;
    }

    private double correctY() {
        double yCorrection = 0;
        if ((yTarget - getY()) > yError) {
            yCorrection = -1;
        } else if ((yTarget - getY()) < -xError) {
            yCorrection = 1;
        }
        return yCorrection;
    }

    private boolean testPosition (double xTarget, double yTarget) {
        if ((getX() == xTarget + xError || getX() == xTarget - xError) && (getY() == yTarget + yError || getY() == yTarget - yError)) {
            return false;
        } else {
            return true;
        }
    }

    public void resetPosition() {
        wheels.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheels.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        wheels.sleepAndCheckActive(50);

        wheels.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheels.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void moveInchesEncoders(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches,double ticksToInches) {

        wheels.ResetEncoders();

        setyTarget(ticksToInches*Inches);
        setxTarget(0);

        while (testPosition(xTarget, yTarget)){
            wheels.checkIsActive();

            double distance=Math.abs(y-yTarget) ;

            //min motor power should be set to zero
            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, power, 0, correctX(), correctY());

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

            double distance=Math.abs(x-xTarget);

            //min motor power should be set to zero
            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, power, 0, correctX(), correctY());

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
        setyTarget(getY());

        while (testPosition(xTarget, getY())){
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
            setyTarget(getY());

        }

        wheels.StopMotors();

    }


}
