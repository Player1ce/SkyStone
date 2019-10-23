package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoMethods {
    LinearOpMode linearOpMode;
    OpMode opMode;
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

    final double WHEEL_DIAMETER = 6;
    final double DRIVE_WHEEL_GEAR_RATIO = 1;

    AutoMethods(String chassisName) {
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

    public void setZeroPowerBrakeBehavior() {
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    public void executeAutonomousLogic() {
        double ticksToInches=288/(Math.PI*6.125);
        this.ForwardMoveInches(telemetry, NORMAL_POWER, 20, ticksToInches);

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

    public void ForwardMoveInches(Telemetry telemetry, double MotorPower, double Inches, double ticksToInches) {

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
            //TODO comment or remove specific wheel telemetry?
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


    public void moveHook(String position){
        String fixedPosition = position.toLowerCase();
        if (fixedPosition.equals("up"))  {
            hookServo.setPosition(.47);
        }
        else if (fixedPosition.equals("down"))  {
            hookServo.setPosition(1);
        }
    }


}
