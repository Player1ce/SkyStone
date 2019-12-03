package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Encoders {
    //TODO do we need to run using encoder. Run using encoder is a PID method so it will interfere with our power setup?
    // also, xEncoder will be sideways so it won't really relate to movement.  Finally, i think we can get position without using encoders.
    //TODO we need to move the encoders to separate ports on the rev hub so the wheels cna run with encoders.


    ChassisName chassis = ChassisName.TANK;

    public Encoders (double startX, double startY, ChassisName chassisName) {
        x = startX;
        y = startY;
        chassis = chassisName;

    }

    private MecanumWheels wheels;

    DcMotor xEncoder;
    DcMotor yEncoder;

    //TODO set up ticks to inches for get x and get y.
    double x;
    double y;

    double xTarget;
    double yTarget;

    double xError;
    double yError;

    final double encoderWheelsInchesToTicks = 125/(Math.PI*2.7812);

    final double inchesToTicks = (1/.0699);

    /*
    double xCorrection;
    double yCorrection;
     */

    public double getX () { return xEncoder.getCurrentPosition();}

    public double getY () { return yEncoder.getCurrentPosition(); }

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

        wheels.StopMotors();

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

    public void moveInchesEncoders(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches) {

        resetPosition();

        //setyTarget(encoderWheelsInchesToTicks*Inches);
        setyTarget(Inches/.0699);
        //setyTarget(ticks);

        setxTarget(0);

        while (getY()<yTarget){
            wheels.checkIsActive();

            double distance=Math.abs(getY()-yTarget) ;

            //min motor power should be set to zero
            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, power , 0, 0 , -1 );

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
            telemetry.addData("power:", power);
            telemetry.update();

        }
        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);

        telemetry.addData("final Y:", getY());
        telemetry.addData("Y target", yTarget);
        telemetry.update();


    }

    protected void crabInchesEncoder(Telemetry telemetry,double MotorPower, double MinMotorPower,double ticks) {

        resetPosition();

        //setxTarget(encoderWheelsInchesToTicks*Inches);
        setyTarget(ticks);
        setyTarget(0);

        while (testPosition(xTarget, yTarget)){
            wheels.checkIsActive();

            double distance=Math.abs(getX()-xTarget);

            //min motor power should be set to zero
            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, 1, 0, correctX() * power, correctY());

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
            telemetry.addData("encoder target:", ticks);
            telemetry.update();

            x = getX();
            y = getY();

        }

        wheels.StopMotors();

    }


    public void initialize(MecanumWheels wheels) {
        this.wheels=wheels;
        xEncoder = wheels.frontLeft;
        yEncoder = wheels.frontRight;
    }


}
