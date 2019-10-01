package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "TankAutonomous", group="Final Rover Ruckus")
public class TankAutonomous extends LinearOpMode {
    AutoMethodsTank robot = new AutoMethodsTank();

    public void runOpMode() throws InterruptedException {
        robot.InitializeHardware();
        robot.InitializeIMU();

        waitForStart();
        //startTime();

    }
}
