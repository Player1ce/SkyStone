package org.firstinspires.ftc.GoBilda;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.devices.Camera;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.SkystoneIntake;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.TeleOpMethods;
import org.firstinspires.ftc.utils.LogUtils;

import java.util.List;


@TeleOp(name="GoBildaTestTeleop", group="Skystone")
//@Disabled
public class GoBildaTestTeleop extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.GOBILDA);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.GOBILDA);
    private final SkystoneIntake intake = new SkystoneIntake(ChassisName.GOBILDA);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.GOBILDA);
    private final IMURevHub imu = new IMURevHub(ChassisName.GOBILDA);
    private final Camera camera = new Camera();

    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampDirectControl = new ButtonOneShot();
    private Encoders encoders = new Encoders(ChassisName.GOBILDA);

    //TODO correct starting cars for drive
    private boolean reverse = true;
    private boolean highPower = true;
    private boolean hookServoEnable = false;
    private boolean rampServoUp = false;
    private boolean directRampControl = false;
    private final double HIGH_POWER = 1.0;
    private final double NORMAL_POWER = 0.5;
    private double rampPosition;

    private double startAngle = 0;

    DistanceSensor distanceSensor;

    public void init() {

        // distanceSensor = hardwareMap.get(DistanceSensor.class, "liftDistanceSensor");
       //  camera.initializeCamera(this);

    }

    public void loop() {

        LogUtils.log(LogUtils.LogType.normal,"Test","x_pid");

            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions =camera.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                }
                telemetry.update();
            }


   /* double distance=distanceSensor.getDistance(DistanceUnit.MM);

        telemetry.addData("Distance:", distance);
        telemetry.update();*/
    }



    /*
    Crab Drive setting
    KP = 0.125 KI = 0.01 KD= 0.01

    mecanumWheels.setPower(.6 + rightCorrect, -0.6 + leftCorrect,
                -0.6 + rightCorrect, -0.6 - leftCorrect);

    double maxCorrectionPower = .1;

    double correctionPower=Math.abs(pidController.output());

    correctionPower=Math.max(-maxCorrectionPower,Math.min(maxCorrectionPower,correctionPower));

     */

}
