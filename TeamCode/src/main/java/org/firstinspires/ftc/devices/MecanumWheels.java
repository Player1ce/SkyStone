package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.logic.KillOpModeException;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumWheels {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    double frontRightPower; //-right
    double frontLeftPower; //-right
    double backRightPower; //-right
    double backLeftPower;


    public double xLeft;
    public double yLeft;
    public double xRight;

    private ChassisName chassis;

    public MecanumWheels (ChassisName name) {
        chassis = name;
    }

    LinearOpMode linearOpMode;

    public void initializeWheels (OpMode opMode) {
        //compress using this.frontRight = ...;
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = opMode.hardwareMap.dcMotor.get("backRight");

        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");

        if (opMode instanceof LinearOpMode) {
            linearOpMode=(LinearOpMode)opMode;
        }

    }

    protected boolean opModeIsActive() {
        if (linearOpMode==null) {
            return true;
        }
        else {
            return linearOpMode.opModeIsActive();
        }
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

        if (chassis == ChassisName.TANK) {
            frontRightPower = (yLeft - xRight + xLeft) * power; //-right
            frontLeftPower = (yLeft + xRight - xLeft) * power; //-right
            backRightPower = (yLeft + xRight + xLeft) * power; //-right
            backLeftPower = (yLeft - xRight - xLeft) * power;

        }
        else if (chassis == ChassisName.GOBILDA) {
            frontRightPower = (yLeft - xRight - xLeft) * power; //-right
            frontLeftPower = (yLeft + xRight + xLeft) * power; //-right
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

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e){}
    }

    public void RunWithoutEncoders() {

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void ResetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleepAndCheckActive(50);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /*
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */

    }

    public double getAverageEncoderPos() {
        double averagePos= (Math.abs(frontLeft.getCurrentPosition())+Math.abs(frontRight.getCurrentPosition())+
                Math.abs(backLeft.getCurrentPosition())+Math.abs(backRight.getCurrentPosition()))/4;

        return averagePos;
    }


    //Forward backward ------------------------------
    public void ForwardMoveInches(Telemetry telemetry,double motorPower, double Inches,double ticksToInches) {
        MoveInches(telemetry,motorPower,0.1,Inches,ticksToInches);
    }


    public void BackwardMoveInches(Telemetry telemetry,double motorPower, double Inches,double ticksToInches) {
        MoveInches(telemetry,motorPower,-0.1,Inches,ticksToInches);
    }

    protected void MoveInches(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches,double ticksToInches) {

        ResetEncoders();

        double averagePos=getAverageEncoderPos();

        double dest=ticksToInches*Inches;

        while (averagePos < dest){
            checkIsActive();

            double distance=Math.abs(averagePos-dest);

            double power=calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            backRight.setPower(power);
            frontLeft.setPower(power);
            backLeft.setPower(power);
            frontRight.setPower(power);

            telemetry.addData("Moving Forward","Moving Forward "+power);
           // telemetry.addData("avg encoder value:", averagePos);
            telemetry.addData("distance:", distance);
           /* telemetry.addData("F/L encoder value:", frontLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("F/R encoder value:", frontRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/L encoder value:", backLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/R encoder value:", backRight.getCurrentPosition()*ticksToInches);*/
            telemetry.addData("encoder target:", Inches);
            telemetry.update();

            averagePos= getAverageEncoderPos();
        }

        StopMotors();

    }

    protected void MoveInchesChanged(Telemetry telemetry,double MotorPower, double MinMotorPower,double Inches) {

        ResetEncoders();

        double position = frontRight.getCurrentPosition();

        double dest = Inches/.0699;
        //double dest = ticks;

        while (position < dest){
            checkIsActive();

            double distance=Math.abs(position-dest);

            double power=calculateProportionalMotorPower(0.0015,distance,MotorPower,MinMotorPower);

            setPowerFromGamepad(false, power, 0, 0, -1);

            telemetry.addData("Moving Forward","Moving Forward "+power);
            // telemetry.addData("avg encoder value:", averagePos);
            telemetry.addData("distance:", distance);
           /* telemetry.addData("F/L encoder value:", frontLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("F/R encoder value:", frontRight.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/L encoder value:", backLeft.getCurrentPosition()*ticksToInches);
            telemetry.addData("B/R encoder value:", backRight.getCurrentPosition()*ticksToInches);*/
            telemetry.addData("encoder target:", Inches);
            telemetry.update();

            position= frontRight.getCurrentPosition();
        }

        StopMotors();

    }
    public void sleepAndCheckActive(long ms) {
        final long targetTime=System.currentTimeMillis()+ms;
        while (System.currentTimeMillis()<targetTime) {
            sleep(1);
            checkIsActive();
        }
    }

    public void checkIsActive() {
        if (!opModeIsActive()) {
            StopMotors();
            throw new KillOpModeException();
        }
    }


    //Crab Drive --------------------
    public void crabDrive (String direction, double motorpower, long time) {
        double crab;
        String sanitizedDirection = direction.toLowerCase();
        if (sanitizedDirection.equals("left")) {
            crab = -1;
        }
        else {
            crab = 1;
        }
        setPowerFromGamepad(false, motorpower, 0, crab, 0);
        sleepAndCheckActive(time);

        StopMotors();
    }

    public void changedCrabDriveLeft (double motorpower, long time) {
        frontLeft.setPower(-motorpower * .7);
        frontRight.setPower(motorpower * 2);
        backLeft.setPower(motorpower * .7);
        backRight.setPower(-motorpower * 2);
        sleepAndCheckActive(time);
        StopMotors();
    }

    public void changedCrabDriveRight (double motorpower, long time) {
        frontLeft.setPower(-motorpower * .7);
        frontRight.setPower(motorpower * 2);
        backLeft.setPower(motorpower * 2);
        backRight.setPower(-motorpower * .7);
        sleepAndCheckActive(time);
        StopMotors();
    }



    //Turning ----------------------
    //TODO test directions
    public void turnSeconds (String direction, double motorpower, long time) {
        double turn;
        String sanitizedDirection = direction.toLowerCase();
        if (sanitizedDirection.equals("right")) {
            turn = -10;
        }
        else {
            turn = 10;
        }
        setPowerFromGamepad(false, motorpower, turn, 0, 0);
        sleep(time);

        StopMotors();
    }


    /**
     * Calculates the ramped power using this proportional gain formula:
     *
     * outPower=min(maxPower,(gain*error)+minPower)
     *
     * @param gain - the scaling factor
     * @param errorDistance - The distance between current position and target position.
     * @param maxMotorPower - the max power we want to use
     * @param minMotorPower - the min power we want to use at the destination
     */

    public static final double calculateProportionalMotorPower(double gain, double
            errorDistance, double maxMotorPower,double minMotorPower) {
        double suggestedPower=(gain*errorDistance) + minMotorPower;

        return Math.min(maxMotorPower,suggestedPower);
    }


    public void StopMotors(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

}
