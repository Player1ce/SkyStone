package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "GoBildaChassisAutonomous", group="Skystone")
public class GoBildaChassisAutonomous extends LinearOpMode {
    private AutoMethodsGoBildaChassis robot = new AutoMethodsGoBildaChassis();

    public void runOpMode() throws InterruptedException {
        robot.InitializeHardware();
        robot.InitializeIMU();

        waitForStart();
        robot.startTime();

        robot.hookServo.setPosition(.47);

    }


}
