package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name= "LeftMechanumAutonomousFINAL", group="FINAL Rover Ruckus")
public class LeftMechanumAutonomousFINAL extends AutonomousMethods {

    public void runOpMode() throws InterruptedException {
        InitializeHardware();
        InitializeIMU();
        InitializeDetector();

        boolean left = false;
        boolean center = false;
        boolean right = false;

        //starts timer
        waitForStart();
        startTime();

        //gold block being to left

        midServo.setPosition(.91);

        sleep(200);

        //gets original position of gold block
        goldX = detector.getXPosition();
        telemetry.addData("Position: ", goldX);
        telemetry.update();
        sleep(200);

        //bot moves down
        moveElevatorUp(.7);
        sleep(200);

        //takes position of gold block when lowered
        goldX = detector.getXPosition();
        telemetry.addData("Position: ", goldX);
        telemetry.update();
        sleep(500);

        //establishes whether gold block is to the  or not
        if (goldX > 530 || goldX == 0) {
            right = true;
        } else {
            right = false;
        }

        //if gold block is seen, it is either right or middle and moves an x coordinate change of 40 based on position of gold block
        //also, checks time taking, should not take more than 5 seconds so stops there
        /*double startingTime = System.currentTimeMillis();
        if (goldX > 0) {
            while (Math.abs(goldX - detector.getXPosition()) < 30 && opModeIsActive() && System.currentTimeMillis() - startingTime < 3000) {
                BackwardMove(.38);
            }
        } else {
            //moves until sees the left gold block
            while (goldX == 0 && System.currentTimeMillis() - startingTime < 3000) {
                BackwardMove(.38);
            }
        }
        StopMotors();
        */

        BackwardMove(.2, 250);

        //elevator stoppers clear the lander
        moveElevatorDown(.7);

        LeftMove(.4, 130);
        sleep(200);
        ForwardMove(0.3,175);
        sleep(200);

        //begins set of commands to exit the lander area
        RotateRightSpecialAngle(.5, -73);

        //moves to the depot area
        //ALGORITHM FOR SAMPLING
        if (System.currentTimeMillis() - startTime < 25000 && opModeIsActive()) {
            if (goldX >= 0 && goldX < 250) {
                left = true;
                BackwardMoveInches(.4, 8);
                sleep(200);
                RightMove(.4, 700);
                sleep(200);
                midServo.setPosition(.48);
                sleep(500);
            } else if (goldX >= 250 && goldX < 500) {
                center = true;
                midServo.setPosition(.48);
                sleep(500);
            } else {
                right = true;
                BackwardMoveInches(.4, 8);
                sleep(200);
                LeftMove(.4, 750);
                sleep(200);
                midServo.setPosition(.48);
                sleep(500);
            }
            //moves into crater
                BackwardMoveInches(.55, 42);
                sleep(200);

            //repositions servo extensions
            rightServo.setPosition(0);
            midServo.setPosition(.91);
            leftServo.setPosition(.85);
        } else {
            BackwardMove(.5, 500);
        }


    }
}
