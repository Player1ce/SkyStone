package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumWheels {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;


    double frontRightPower; //-right
    double frontLeftPower; //-right
    double backRightPower; //-right
    double backLeftPower;

    double xLeft;
    double yLeft;
    double xRight;

    String chassis;

    MecanumWheels (String chassisName) {
        chassis = chassisName.toLowerCase();
    }

    public void initialize(DcMotor frontLeft, DcMotor frontRight,DcMotor backLeft, DcMotor backRight ) {
      //assign the passed in Motors to the class fields for later use
      this.frontLeft = frontLeft;
      this.frontRight = frontRight;
      this.backLeft = backLeft;
      this.backRight = backRight;

    }


    public void setPower (double frontRightPower, double frontLeftPower, double backRightPower, double backLeftPower) {
        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
    }

    public void setPowerFromGamepad(boolean reverse, double power, double left_stick_x,
                                        double right_stick_x,double left_stick_y) {
        xLeft=left_stick_x;
        if (!reverse) {
            xRight = right_stick_x;
            yLeft = -left_stick_y;
        } else {
            xRight = -right_stick_x;
            yLeft = left_stick_y;
        }

        if (chassis == "tank") {
            frontRightPower = (yLeft - xRight + xLeft) * power; //-right
            frontLeftPower = (yLeft + xRight - xLeft) * power; //-right
            backRightPower = (yLeft + xRight + xLeft) * power; //-right
            backLeftPower = (yLeft - xRight - xLeft) * power;

        }
        else if (chassis == "gobilda") {
            frontRightPower = (-yLeft - xRight - xLeft) * power; //-right
            frontLeftPower = (yLeft - xRight - xLeft) * power; //-right
            backRightPower = (-yLeft - xRight + xLeft) * power; //-right
            backLeftPower = (yLeft - xRight + xLeft) * power;

        }

        setPower(frontRightPower, frontLeftPower, backRightPower, backLeftPower);

    }

    public void setZeroPowerBrakeBehavior() {
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e){}
    }

    public void ResetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getAverageEncoderPos() {
        double averagePos= (Math.abs(frontLeft.getCurrentPosition())+Math.abs(frontRight.getCurrentPosition())+
                Math.abs(backLeft.getCurrentPosition())+Math.abs(backRight.getCurrentPosition()))/4;

        return averagePos;
    }

    public void ForwardMoveInches(Telemetry telemetry,double MotorPower, double Inches,double ticksToInches) {

        ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        double averagePos=getAverageEncoderPos();

        double dest=ticksToInches*Inches;

        while (averagePos < dest){
           telemetry.addData("Moving Forward","Moving Forward");
            telemetry.addData("avg encoder value:", averagePos*ticksToInches);
            telemetry.addData("F/L encoder value:", frontLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("F/R encoder value:", frontRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/L encoder value:", backLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/R encoder value:", backRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("encoder target:", Inches);
            telemetry.update();

            averagePos= getAverageEncoderPos();
        }

        StopMotors();

        sleep(5000);
    }


    public void StopMotors(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

}
