package OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.BasicRobotMethods;
import org.firstinspires.ftc.teamcode.OdometryMethods;
import org.firstinspires.ftc.teamcode.RobotMethods;
import org.firstinspires.ftc.teamcode.TeleOpMethods;

@Disabled
@Autonomous(name= "LeftAutonomousEVERYTHING", group="FINAL Rover Ruckus")
public class LeftAutonomousEVERYTHING extends AutonomousMethods {
    public BasicRobotMethods robotMethods = new TeleOpMethods();
    public void runOpMode() throws InterruptedException {
        //robotMethods.InitializeHardware(this);

        InitializeHardware();
        robotMethods.InitializeIMU();
        //TODO: robotMethods.InitializeDetector();



        //starts timer
        waitForStart();
        startTime();

        midServo.setPosition(.91);

        //gold block being to left
        boolean right = false;


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
        //TODO: goldX = detector.getXPosition();
        telemetry.addData("Position: ", goldX);
        telemetry.update();
        sleep(500);

        //establishes whether gold block is to the  or not
        right = goldX > 530 || goldX == 0;

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

        BackwardMove(.2, 185);

        //elevator stoppers clear the lander
        moveElevatorDown(.7);

        LeftMove(.6, 950);
        sleep(200);
        ForwardMoveInches(.4, 2);



        //moves to the depot area
        //ALGORITHM FOR SAMPLING
        if (System.currentTimeMillis() - startTime < 25000 && opModeIsActive()) {
            if (goldX >= 0 && goldX < 250) {
                //rightServo.setPosition(1);
                leftServo.setPosition(.03);
                sleep(500);
                BackwardMoveInches(.4, 5);
                leftServo.setPosition(.85);
                sleep(500);
                BackwardMoveInches(.4, 34);
            } else if (goldX >= 250 && goldX < 500) {
                leftServo.setPosition(.03);
                sleep(500);
                ForwardMoveInches(.4, 10);
                leftServo.setPosition(.85);
                sleep(500);
                BackwardMoveInches(.4, 53);
            } else {
                ForwardMoveInches(.4, 15);
                sleep(200);
                leftServo.setPosition(.03);
                sleep(500);
                ForwardMoveInches(.4, 10);
                sleep(200);
                leftServo.setPosition(.85);
                sleep(500);
                BackwardMoveInches(.4, 74);
            }

            //moves into crater
            RotateLeftSpecialAngle(.5, 35);

            if (goldX > 500) {
                rightServo.setPosition(1);
                BackwardMoveInches(.55, 38);
                rightServo.setPosition(0);
                sleep(100);
            } else {
                BackwardMoveInches(.55, 45);       //52
                sleep(200);
            }

            pivot.setPosition(.2);
            sleep(1500);

            LeftMove(.4, 105);

            /*if (goldX >= 0 && goldX < 250) {
                ForwardMoveInches(1, 38);
                sleep(200);
                rightServo.setPosition(1);
                sleep(200);
                ForwardMoveInches(1, 52);
                rightServo.setPosition(0);
            } else {
                ForwardMoveInches(1, 90);
            }*/
            ForwardMoveInches(1, 89);

            //repositions servo extensions
            rightServo.setPosition(0);
            midServo.setPosition(.91);
            leftServo.setPosition(.85);
        } else {
            BackwardMove(.5, 500);
        }


    }

}