package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.teamcode.AutonomousMethods;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class Spin extends AutonomousMethods {


    //spin --------------------------------------------------------------------------
    // TODO complete spin?

    public void SpinLeft(double MotorPower, int angle){
        backRight.setPower(MotorPower);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(MotorPower);

        while (Math.abs(gyro.getIntegratedZValue()) < angle && opModeIsActive()) {
            telemetry.addData("Spinning Left","Spinning Left");
            telemetry.addData("angle value:", Math.abs(gyro.getIntegratedZValue()));
            telemetry.addData("angle target:", angle);
            telemetry.update();
        }

        StopMotors();
    }

    public void SpinRight(double MotorPower, int angle){
        //some code here
    }


}
