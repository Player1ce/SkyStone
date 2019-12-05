package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.time.chrono.MinguoEra;

public class Encoders {
    //TODO do we need to run using encoder. Run using encoder is a PID method so it will interfere with our power setup?
    // also, xEncoder will be sideways so it won't really relate to movement.  Finally, i think we can get position without using encoders.
    //TODO we need to move the encoders to separate ports on the rev hub so the wheels cna run with encoders.


    ChassisName chassis;

    public Encoders (ChassisName chassisName) {
        chassis = chassisName;
    }

    private MecanumWheels wheels;

    DcMotor xEncoder;
    DcMotor yEncoder;

    double xTarget;
    double yTarget;


    //actual diameter: 2.7812 in.
    final double encoderWheelsInchesToTicks = 125/(Math.PI*2.7812);
    final double inchesToTicks = (1/.0681);

    public void initialize(MecanumWheels wheels) {
        this.wheels=wheels;
        xEncoder = wheels.frontLeft;
        yEncoder = wheels.frontRight;
    }

    public double getX () { return xEncoder.getCurrentPosition();}

    public double getY () { return yEncoder.getCurrentPosition(); }

    public void setxTarget (double target) { xTarget = target; }

    public void setyTarget (double target) { yTarget = target; }


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

    public void moveInchesEncoders(Telemetry telemetry,double MotorPower, double MinMotorPower,double ticks) {

        resetPosition();

        //setyTarget(encoderWheelsInchesToTicks*Inches);
        //setyTarget((Inches+.133)/.0713);
        setyTarget(ticks);
        setxTarget(0);

        while (getY() < yTarget){
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

    public void crabInchesEncoder(Telemetry telemetry,double MotorPower, double MinMotorPower,double ticks) {

        resetPosition();

        //setxTarget(encoderWheelsInchesToTicks*Inches);
        setxTarget(ticks);
        setyTarget(0);

        while (getX() < xTarget){
            wheels.checkIsActive();

            double distance=Math.abs(getX() - xTarget);

            double power=wheels.calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            wheels.setPowerFromGamepad(false, power, 0, 1, 0);

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


        }

        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);

        telemetry.addData("final x:", getX());
        telemetry.addData("x target", xTarget);
        telemetry.update();

    }
    public double getXDirection () {
        if (getX() > xTarget) {
            return -1;
        } else if (getX() < xTarget) {
            return 1;
        } else {
            return 0;
        }
    }

    public double getYDirection () {
        if (getY() > yTarget) {
            return 1;
        } else if (getY() < yTarget) {
            return -1;
        } else {
            return 0;
        }
    }

    public void moveInchesEncodersEdited (Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches) {
        resetPosition();

        setyTarget(Inches/.0699);
        //setyTarget(Inches);
        setxTarget(0);

        while (getY() < yTarget || Math.abs(getX()) > xTarget +10) {
            wheels.checkIsActive();

            double distanceX=Math.abs(getX() - xTarget);
            double distanceY=Math.abs(getX() - yTarget);
            double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, MinMotorPower);
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            double yDirection = getYDirection();
            double xDirection = getXDirection();

            wheels.setPowerFromGamepad(false, 1 , 0, powerX * 0.4 * xDirection, powerY * yDirection);

        }
        wheels.StopMotors();
    }



    public void crabInchesEncoderEdited(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches) {

        resetPosition();

        setyTarget(0);
        setxTarget((Inches *4)/.0699);
        //setxTarget(Inches);

        while (getX() < xTarget || Math.abs(getY()) > yTarget +10){

            wheels.checkIsActive();


            double distanceX=Math.abs(getX() - xTarget);
            double distanceY=Math.abs(getX() - yTarget);
            double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, MinMotorPower);
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            double yDirection = getYDirection();
            double xDirection = getXDirection();

            wheels.setPowerFromGamepad(false, 1, 0, powerX * xDirection , 0.4 * powerY * yDirection);

            telemetry.addData("powerx:", powerX);
            telemetry.addData("y:", getY());
            telemetry.addData("ypower:", powerY);
            telemetry.update();
        }
        wheels.StopMotors();

    }


}
