package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name= "RightMechanumAutonomousFINAL", group="FINAL Rover Ruckus")
public class RightMechanumAutonomousFINAL extends AutonomousMethods {

    public void runOpMode() throws InterruptedException {
        InitializeHardware();
        InitializeIMU();
        //TODO: InitializeDetector();

        //starts timer
        waitForStart();
        startTime();

        //gold block being to left
        boolean left = false;

        midServo.setPosition(.91);

            sleep(200);

            //gets original position of gold block
            //TODO: goldX = detector.getXPosition();
            telemetry.addData("Position: ", goldX);
            telemetry.update();
            sleep(200);

            //bot moves down
            moveElevatorUp(.7);
            sleep(200);

            //takes position of gold block when lowered
            //TODO: .goldX = detector.getXPosition();
            telemetry.addData("Position: ", goldX);
            telemetry.update();
            sleep(500);

            //establishes whether gold block is to the  or not
        left = goldX > 530 || goldX == 0;

            //if gold block is seen, it is either right or middle and moves an x coordinate change of 40 based on position of gold block
            //also, checks time taking, should not take more than 5 seconds so stops there
/*            double startingTime = System.currentTimeMillis();
            if (goldX > 0) {
                while (Math.abs(goldX - detector.getXPosition()) < 25 && opModeIsActive() && System.currentTimeMillis() - startingTime < 1200) {
                    ForwardMove(.2);
                }
                //moves until sees the left gold block
            } else {
                while (goldX == 0 && opModeIsActive() && System.currentTimeMillis() - startingTime < 1200) {
                    ForwardMove(.2);
                }
            }*/
            ForwardMove(.2,290);
            StopMotors();

            //elevator stoppers clear the lander
            moveElevatorDown(.7);

            LeftMove(.4, 130);
            sleep(200);
            //BackwardMoveInches(.4, 1);
            BackwardMove(0.3,145);
            sleep(200);
            RotateRightAngle(.5, 68, false, 0, 0);
            sleep(200);


            //moves to the depot area
            //ALGORITHM FOR SAMPLING
            if (System.currentTimeMillis() - startTime < 25000 && opModeIsActive()) {
                if (goldX > 0 && goldX < 250) {
                    rightServo.setPosition(1);
                    sleep(500);
                } else if (goldX >= 250 && goldX < 500) {
                    midServo.setPosition(.48);
                    sleep(500);
                } else {
                    leftServo.setPosition(.03);
                    sleep(500);
                }
                BackwardMoveInches(.4, 37);
                sleep(200);

                //repositions servo extensions
                rightServo.setPosition(0);
                midServo.setPosition(.91);
                leftServo.setPosition(.85);

                //positions for crater
                BackwardMoveInches(.4, 20);
                sleep(200);


                RotateLeftAngle(.5, 58);
                sleep(100);

                LeftMove(.4, 90);
                sleep(100);

                pivot.setPosition(.2);
                sleep(1500);

                if (goldX > 500) {
                    rightServo.setPosition(1);
                }
                //moves into crater
                ForwardMoveInches(.3, 45);
                rightServo.setPosition(0);
                if (goldX < 250) {
                    rightServo.setPosition(1);
                }
                ForwardMoveInches(.8, 46);
                sleep(1000);
                rightServo.setPosition(0);
                sleep(500);
            } else {
                //if too late, just moves back
                BackwardMove(.7, 500);
            }


        }

}