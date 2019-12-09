package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Encoders {
    //TODO do we need to run using encoder. Run using encoder is a PID method so it will interfere with our power setup?
    // also, xEncoder will be sideways so it won't really relate to movement.  Finally, i think we can get position without using encoders.
    //TODO we need to move the encoders to separate ports on the rev hub so the wheels cna run with encoders.


    ChassisName chassis;

    public Encoders (ChassisName chassisName) {
        this.chassis = chassisName;
    }

    private MecanumWheels wheels;

    private DcMotor xEncoder;
    private DcMotor yEncoder;

    private double xTarget;
    private double yTarget;


    //actual diameter: 2.7812 in.
    //final double encoderWheelsInchesToTicks = 125/(Math.PI*2.7812);
    //final double inchesToTicks = (1/.0681);

    public void initialize(MecanumWheels wheels, OpMode opMode) {
        this.wheels=wheels;
        //xEncoder = wheels.frontLeft;
        //yEncoder = wheels.frontRight;
        //pass opmode into class
        xEncoder = opMode.hardwareMap.dcMotor.get("port 2");
        yEncoder = opMode.hardwareMap.dcMotor.get("port 3");
    }

    public double getX () { return xEncoder.getCurrentPosition(); }

    public double getY () { return -yEncoder.getCurrentPosition(); }

    private void setxTarget (double target) { xTarget = target; }

    private void setyTarget (double target) { yTarget = target; }


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

        while (Math.abs(getY()) < Math.abs(yTarget)){
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

        while (Math.abs(getX()) < Math.abs(xTarget)){
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
    private double getXDirection () {
        if (getX() > xTarget) return -1;
        else if (getX() < xTarget) return 1;
        else return 0;
    }

    private double getYDirection () {
        if (getY() > yTarget) return 1;
        else if (getY() < yTarget) return -1;
        else return 0;
    }

    public void moveInchesEncoderEdited (Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches) {
        resetPosition();

        //setyTarget((Inches * 4)/.0699);
        setyTarget(Inches);
        setxTarget(0);

        while (Math.abs(getY()) < Math.abs(yTarget)) {
            wheels.checkIsActive();
            //|| Math.abs(getX()) > Math.abs(xTarget +10)
            double distanceX=Math.abs(getX() - xTarget);
            double distanceY=Math.abs(getY() - yTarget);
            double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, MinMotorPower);
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            double yDirection = getYDirection();
            double xDirection = getXDirection();

            if (distanceX < 5) {
                wheels.frontLeft.setPower(powerY * yDirection);
                wheels.backRight.setPower(powerY * yDirection);
                wheels.frontRight.setPower(powerY * yDirection);
                wheels.backLeft.setPower(powerY * yDirection);
            } else if (distanceX > 5) {
                wheels.frontLeft.setPower((powerY * yDirection * .7) + (-powerX * xDirection * .7));
                wheels.backRight.setPower((powerY * yDirection * .7) + (-powerX * xDirection * .7));
                wheels.frontRight.setPower((powerY * yDirection * .7) + (powerX * xDirection * 2));
                wheels.backLeft.setPower((powerY * yDirection * .7) + (powerX * xDirection * 2));
            }

            telemetry.addData("y", getY());
            telemetry.addData("x", getX());
            telemetry.update();
        }
        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);

        telemetry.addData("final Y:", getY());
        telemetry.addData("Y target", yTarget);
        telemetry.update();
    }



    public void crabInchesEncoderEdited(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches) {

        resetPosition();

        setyTarget(0);
        setxTarget((Inches *4)/.0699);
        //setxTarget(Inches);

        while (Math.abs(getX()) < Math.abs(xTarget) || Math.abs(getY()) > yTarget +10){

            wheels.checkIsActive();

            double distanceX=Math.abs(getX() - xTarget);
            double distanceY=Math.abs(getY() - yTarget);
            double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, .3);
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceY, MotorPower, MinMotorPower);
            double yDirection = getYDirection();
            double xDirection = getXDirection();

            if (distanceY < 5) {
                wheels.frontLeft.setPower(-powerX * xDirection);
                wheels.backRight.setPower(-powerX * xDirection);
                wheels.frontRight.setPower(powerX * xDirection);
                wheels.backLeft.setPower(powerX * xDirection);
            } else if (distanceX > 5) {
                wheels.frontLeft.setPower((powerY * yDirection * .7) + (-powerX * xDirection * .7));
                wheels.backRight.setPower((powerY * yDirection * .7) + (-powerX * xDirection * .7));
                wheels.frontRight.setPower((powerY * yDirection * .7) + (powerX * xDirection * 2));
                wheels.backLeft.setPower((powerY * yDirection * .7) + (powerX * xDirection * 2));
            }

            telemetry.addData("powerx:", powerX);
            telemetry.addData("y:", getY());
            telemetry.addData("x", getX());
            telemetry.addData("ypower:", powerY);
            telemetry.update();
        }
        telemetry.addData("y:", getY());
        telemetry.addData("x", getX());
        telemetry.update();

        wheels.StopMotors();

    }



}
