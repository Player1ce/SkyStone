package org.firstinspires.ftc.robotDevices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class DeadWheels {
    private MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    public DcMotor forwardEncoder = mecanumWheels.frontLeft;
    public DcMotor horizontalEncoder = mecanumWheels.frontRight;


    //position vars
    double xPosition = horizontalEncoder.getCurrentPosition();
    double yPosition = forwardEncoder.getCurrentPosition();

    double xTarget;
    double yTarget;

    double xVariance;
    double yVariance;

    double distancex = Math.abs(xPosition - xTarget);
    double distancey = Math.abs(yPosition - yTarget);
    double distance = Math.sqrt(((distancex * distancex) + (distancey * distancey)));

    double worldPosition[] = new double[] {xPosition, yPosition};


    double destination[] = new double[] {xTarget, yTarget};


    public void initializeEncoders () {
        forwardEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e){}
    }

    public void resetEncoders() {
        forwardEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);

        forwardEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        horizontalEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setTarget (double x, double y) {
        xTarget = x;
        yTarget = y;
    }

    public void setVarince (double x, double y) {
        this.xVariance = Math.abs(xTarget-x);
        this.yVariance= Math.abs(yTarget-y);
    }

    protected void MoveInches(Telemetry telemetry, double MotorPower, double MinMotorPower, double Inches, double ticksToInches) {

        resetEncoders();

        //double averagePos=getAverageEncoderPos();

        double dest=ticksToInches*Inches;

        while (xPosition < xTarget){

            //double distance=Math.abs(averagePos-dest);
            distancey = Math.abs(worldPosition[1] - destination[1]);

            double power=mecanumWheels.calculateProportionalMotorPower(0.0015,distancey,MotorPower,MinMotorPower);

            mecanumWheels.setPower(power, power, power, power);

            /*
            mecanumWheels.backRight.setPower(power);
            frontLeft.setPower(power);
            mecanumWheels.backLeft.setPower(power);
            frontRight.setPower(power);
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


            xPosition = horizontalEncoder.getCurrentPosition();
            yPosition = forwardEncoder.getCurrentPosition();
        }

        mecanumWheels.StopMotors();

    }


    /*
    public void initializeOdometry (OpMode opMode) {
        //compress using this.frontRight = ...;
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }
     */

}


