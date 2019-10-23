package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumWheels {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    Servo hookServo;


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

    public void InitializeHardware(OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;

        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.dcMotor.get("backRight");

        backLeft = hardwareMap.dcMotor.get("backLeft");

        //spool = hardwareMap.dcMotor.get("spool");

        if (chassis == "tank") {
            hookServo=hardwareMap.servo.get("hookServo");
        }
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

        if (chassis.equals("tank")) {
            frontRightPower = (yLeft - xRight + xLeft) * power; //-right
            frontLeftPower = (yLeft + xRight - xLeft) * power; //-right
            backRightPower = (yLeft + xRight + xLeft) * power; //-right
            backLeftPower = (yLeft - xRight - xLeft) * power;

        }
        else if (chassis.equals("gobilda")) {
            frontRightPower = (-yLeft - xRight - xLeft) * power; //-right
            frontLeftPower = (yLeft - xRight - xLeft) * power; //-right
            backRightPower = (-yLeft - xRight + xLeft) * power; //-right
            backLeftPower = (yLeft - xRight + xLeft) * power;

        }

        setPower(frontRightPower, frontLeftPower, backRightPower, backLeftPower);

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

    //TODO Is this necessary in teleop?
    public void StopMotors(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

}
