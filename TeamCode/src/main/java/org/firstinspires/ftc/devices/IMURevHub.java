package org.firstinspires.ftc.devices;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class IMURevHub {
    //Reference Cite: https://stemrobotics.cs.pdx.edu/node/7265

    ChassisName chassis;

    public IMURevHub(ChassisName name) {
        chassis = name;
    }

    private MecanumWheels wheels;
    Orientation lastAngles = new Orientation();

    BNO055IMU imu;
    DcMotor leftMotor, rightMotor;
    TouchSensor touch;

    double  globalAngle, power = .30, correction;
    boolean aButton, bButton, touched;


    public void initializeIMU (MecanumWheels wheels,OpMode opMode) {
        this.wheels=wheels;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        opMode.telemetry.addData("Mode", "calibrating...");
        opMode.telemetry.update();


        /* make sure the imu gyro is calibrated before continuing.*/
        while (!imu.isGyroCalibrated())
        {
            wheels.sleepAndCheckActive(1);
        }


        opMode.telemetry.addData("Mode", "waiting for start");
        opMode.telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        opMode.telemetry.update();
    }

    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    public double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    public Orientation getOrientation() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    public double getAngleWithStart(Orientation startOrientation)
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - startOrientation.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        return deltaAngle;
    }

    public double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    public void rotate(int degrees, double motorPower, double minMotorPower, LinearOpMode linearOpMode)
    {
        double distance = Math.abs(degrees - getAngle());
        double calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = calcedPower;
            rightPower = -calcedPower;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -calcedPower;
            rightPower = calcedPower;
        }
        else return;

        /*
        // set power to rotate.
        wheels.setPower(rightPower, leftPower, rightPower, leftPower);
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
         */

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (linearOpMode.opModeIsActive() && getAngle() == 0) {
                distance = Math.abs(degrees - getAngle());
                calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, 1, 0, 0);
            }

            while (linearOpMode.opModeIsActive() && getAngle() > degrees) {
                distance = Math.abs(degrees - getAngle());
                calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, 1, 0, 0);
            }
        }
        else    // left turn.
            while (linearOpMode.opModeIsActive() && getAngle() < degrees) {
                distance = Math.abs(degrees - getAngle());
                calcedPower=wheels.calculateProportionalMotorPower(0.0015,distance, motorPower, minMotorPower);
                wheels.setPowerFromGamepad(false, calcedPower, -1, 0, 0);
                leftMotor.setPower(calcedPower);
                rightMotor.setPower(-calcedPower);
            }


        // turn the motors off.
        wheels.StopMotors();

        // wait for rotation to stop.
        wheels.sleepAndCheckActive(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }

}
