package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name= "BlueRightAutonomous", group="Rover Ruckus")
public class BlueRightAutonomous extends AutonomousMethods {

    public void runOpMode() throws InterruptedException {

        InitializeHardware();
        InitializeIMU();

        // wait for the start button to be pressed.
        waitForStart();

        sleep(500);

        BackwardMoveInches(.4, 54);
        sleep(1000);
        ForwardMoveInches(.4, 6);

        RotateLeftAngle(.3, 25);

        sleep(500);
        pivot.setPosition(0);
        sleep(1000);

        ForwardMoveInches(.75, 82);



    }

}