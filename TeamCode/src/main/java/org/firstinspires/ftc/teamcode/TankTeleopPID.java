package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.logic.Navigation;
import org.firstinspires.ftc.devices.BlockIntake;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp (name="TankTeleopPID", group = "Skystone")
public class TankTeleopPID extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);
    private final Navigation navigation = new Navigation(ChassisName.TANK);

    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot rampDirectControl = new ButtonOneShot();
    private Encoders encoders = new Encoders(ChassisName.TANK);

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

    public void init() {
        /* attaching configuration names to each motor; each one of these names must match the name
        of the motor in the configuration profile on the phone (spaces and capitalization matter)
        or else an error will occur
        */
        mecanumWheels.initializeWheels(this);
        //   hookServo.initializeHook(this);
        //  intake.initializeIntake(this);
        //  encoders.initialize(mecanumWheels, this);
        //imu.initializeIMU(mecanumWheels,this);
        navigation.initialize(mecanumWheels, imu, this);

        //CRAB: pidController=new PIDController(.0125,0.001,0.001);
        pidController=new PIDController(.0125,0.001,0.001);
        pidController.setMaxErrorForIntegral(0.002);

    }

    PIDController pidController;

    Orientation startOrientation;

    long time=0;

    public void loop() {

        if (startOrientation==null) {
            startOrientation=imu.getOrientation();
        }

        double angle=imu.getAngleWithStart(startOrientation);

        pidController.input(angle);

        //CRAB: double maxCorrectionPower = .1;

        //CRAB: double correctionPower=Math.abs(pidController.output());

        //CRAB: correctionPower=Math.max(-maxCorrectionPower,Math.min(maxCorrectionPower,correctionPower));

        double motorPower = .4;
        double maxCorrectionPower = motorPower - .1;

        double correctionPower=Math.abs(pidController.output());

        correctionPower=Math.max(-maxCorrectionPower,Math.min(maxCorrectionPower,correctionPower));


        double rightCorrect = 0;
        double leftCorrect = 0;

        long curTime=System.currentTimeMillis();

        long diff=curTime-time;

        time=curTime;


        if (angle < 0) {
            rightCorrect = -1*correctionPower;//-.025;
            leftCorrect = correctionPower;//.025;
        }
        else if (angle > 0) {
            rightCorrect = correctionPower;//.025;
            leftCorrect = -1*correctionPower;//-.025;
        }

        //CRAB :mecanumWheels.setPower(.6 + rightCorrect, -0.6 + leftCorrect,
        // -0.6 + rightCorrect, -0.6 - leftCorrect);

        //mecanumWheels.setPower(motorPower + rightCorrect, motorPower + leftCorrect,
        //        motorPower, motorPower);


        telemetry.addData("output:", pidController.output());
        telemetry.addData("Correction power:", correctionPower);
        telemetry.addData("Right Correct:", rightCorrect);
        telemetry.addData("Left Correct:", leftCorrect);
        telemetry.addData("Angle", angle);
        telemetry.addData("Time(ms)", diff);
        telemetry.update();
    }

}
