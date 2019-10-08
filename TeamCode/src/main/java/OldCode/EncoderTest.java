package OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import OldCode.AutonomousMethods;

/**
 * Created by jesuit on 8/27/2018.
 */
@Autonomous(name= "Encoder Test", group="Test")
public class EncoderTest extends AutonomousMethods {

    //actual linear run
    public void runOpMode() throws InterruptedException {

        waitForStart();

        InitializeHardware();

        ResetEncoders();

        while(opModeIsActive()){

            telemetry.addData("Encoder backRight Value: ", backRight.getCurrentPosition());
            telemetry.addData("Encoder backLeft Value: ", backLeft.getCurrentPosition());
            telemetry.addData("Encoder frontRight Value: ", frontRight.getCurrentPosition());
            telemetry.addData("Encoder frontLeft Value: ", frontLeft.getCurrentPosition());
            //telemetry.addData("Encoder horizontalEncoder value: ", horizontalEncoder.getCurrentPosition());
            telemetry.addData("Encoder forwardEncoder value: ", forwardEncoder.getCurrentPosition());
            telemetry.update();

        }


    }


}
