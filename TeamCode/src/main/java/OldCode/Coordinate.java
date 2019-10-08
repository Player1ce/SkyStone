package OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import OldCode.AutonomousMethods;

@Autonomous(name= "Coordinate", group="Test")
public class Coordinate extends AutonomousMethods {



    //actual linear run
    public void runOpMode() throws InterruptedException {

        double x = 15;
        double y = 20;
        double MotorPower = 0.5;

        double distance = Math.sqrt((x * x) + (y * y));

        double angle = Math.toDegrees(Math.atan(x / y));

        //declares motors
        InitializeHardware();

        //declare gyros
        InitializeIMU();

        waitForStart();


        while (angle==0) {
            telemetry.addData("Angle: ", angle);
            telemetry.addData("calc: ",Math.toDegrees(Math.atan(x / y)));
            telemetry.addData("Gyro: ", Math.abs(GetIMUHeading()));
            telemetry.update();
        }

        telemetry.addData("Angle: ", angle);
        telemetry.addData("calc: ",Math.atan(x / y));
        telemetry.addData("Gyro: ", Math.abs(GetIMUHeading()));
        telemetry.update();

        sleep(3000);

        RotateRightAngle(.3, angle, false, 0, 0);

        sleep(1000);

        ResetEncoders();

        backRight.setPower(MotorPower);
        frontLeft.setPower(MotorPower);
        backLeft.setPower(MotorPower);
        frontRight.setPower(MotorPower);

        while (Math.abs(backRight.getCurrentPosition()) < InchesToPulses(distance)){
            telemetry.addData("Inches:",distance);
            telemetry.addData("pulses:", InchesToPulses(distance));
            telemetry.addData("encoder:", backRight.getCurrentPosition());
            telemetry.update();
        }
        StopMotors();

        sleep(5000);

        //MovementMethods(.5, (int) InchesToPulses(distance));








    }
}
