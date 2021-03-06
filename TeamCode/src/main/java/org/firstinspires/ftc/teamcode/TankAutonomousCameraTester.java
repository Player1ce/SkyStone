package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.devices.Camera;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.BlockIntake;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;
import java.util.Locale;

@Autonomous(name = "TankAutonomousCameraTester", group="Skystone")
public class TankAutonomousCameraTester extends LinearOpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook( ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);
    final double HIGH_POWER = 1.0;
    final double NORMAL_POWER = 0.5;
    double i;
    private final Camera camera = new Camera();


    public void runOpMode() {
        mecanumWheels.initializeWheels(this);
        camera.initializeCamera(this);


        mecanumWheels.setZeroPowerBrakeBehavior();
        waitForStart();
        robot.startTime();

        executeAutonomousLogic();

    }


    protected void executeAutonomousLogic() {
        while (opModeIsActive()) {
            //put all code in this while loop so the bot will stop when we tell it to
            List<Recognition> updatedRecognitions = camera.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format(Locale.US,"label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format(Locale.US,"  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format(Locale.US,"  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                }
                telemetry.update();
            }
        }
    }
}
