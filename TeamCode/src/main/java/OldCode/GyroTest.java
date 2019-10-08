package OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import OldCode.AutonomousMethods;


@Autonomous(name= "GyroTest", group="Test")
public class GyroTest extends AutonomousMethods {


    //actual linear run
    public void runOpMode() throws InterruptedException {



        InitializeHardware();
        InitializeIMU();

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Angle: ", GetIMUHeading());
            telemetry.addData("Acceleration: ", imu.getAcceleration());
            telemetry.addData("Velocity: ", imu.getVelocity());
            telemetry.addData("Position: ", imu.getPosition());
            telemetry.addData("Gravity: ", imu.getGravity());
            telemetry.update();
        }


}




}
