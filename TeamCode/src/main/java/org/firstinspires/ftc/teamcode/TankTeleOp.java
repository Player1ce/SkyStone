package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.ScissorLift;
import org.firstinspires.ftc.devices.SkystoneLever;
import org.firstinspires.ftc.logic.BasicPositions;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.devices.FoundationHook;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.devices.BlockIntake;


@TeleOp(name="Tank TeleOp", group="Skystone")
//@Disabled
public class TankTeleOp extends OpMode {
    private TeleOpMethods robot = new TeleOpMethods(ChassisName.TANK);
    private final MecanumWheels mecanumWheels = new MecanumWheels(ChassisName.TANK);
    private final BlockIntake intake = new BlockIntake(ChassisName.TANK);
    private final FoundationHook hookServo = new FoundationHook(ChassisName.TANK);
    private final IMURevHub imu = new IMURevHub(ChassisName.TANK);
    private final SkystoneLever skystoneLever = new SkystoneLever();
    private final ScissorLift scissorLift = new ScissorLift(ChassisName.TANK, mecanumWheels);

    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot skystoneLeverButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookControlButtonLocgic = new ButtonOneShot();
    private Encoders encoders = new Encoders(ChassisName.TANK);

    //TODO correct starting cars for drive
    private boolean reverse = true;
    private boolean highPower = true;
    private boolean hookServoEnable = false;
    private final double HIGH_POWER = 1.0;
    private final double NORMAL_POWER = 0.5;
    private boolean leverUP = true;


    public void init() {
        /* attaching configuration names to each motor; each one of these names must match the name
        of the motor in the configuration profile on the phone (spaces and capitalization matter)
        or else an error will occur
        */
        mecanumWheels.initializeWheels(this);
        hookServo.initializeHook(this);
        intake.initializeIntake(this);
        encoders.initialize(mecanumWheels, this);
        imu.initializeIMU(mecanumWheels,this);
        skystoneLever.initialize(this);
        //TODO I changed servos and intake to null for a full functionality test.
        //set up variables in respective classes.

        intake.setIntakeBrakes();
    }

    public void loop() {
        /** Drive Controller (gamepad1) ---------------------------
         * Controller for the driver:
         * a.	B: Reverse
         * b.	A: Power Change
         * c.	Y: Skystone Lever
         * d.	X: Hook
         * e.	Lt: Intake In
         * f.	Lr: Intake Out
         * g.	Lb:
         * h.	Rb:
         */

        //drive train --------------------------------
        if (powerChangeButtonLogic.isPressed(gamepad1.a)) {
            highPower = !highPower;
        }
        if (reverseButtonLogic.isPressed(gamepad1.b)) {
            reverse = !reverse;
        }
        //if high power, use the high power constant, else use the normal power constant
        double power = highPower ? HIGH_POWER : NORMAL_POWER;



        mecanumWheels.setPowerFromGamepad(reverse, power,gamepad1.left_stick_x,
                gamepad1.right_stick_x,gamepad1.left_stick_y);


        //foundation hook -------------------------------
        if (hookServoButtonLogic.isPressed(gamepad1.x)) {
            hookServoEnable = !hookServoEnable;
        }
        if (hookServoEnable)  {
            hookServo.hookServo.setPosition(0);
        }
        else   {
            hookServo.hookServo.setPosition(.6);
            //hookServo.setPosition(.47);
        }

        //Skystone Lever -------------------------------
        if (skystoneLeverButtonLogic.isPressed(gamepad1.y)) {
            leverUP = !leverUP;
        }
        if (leverUP) {
            skystoneLever.setPosition(BasicPositions.UP);
        }
        else if (!leverUP) {
            skystoneLever.setPosition(BasicPositions.DOWN);
        }

        //intake control ----------------------------
        if (gamepad1.left_trigger > 0) {
            intake.leftIntake.setPower(-gamepad1.left_trigger * .7);
            intake.rightIntake.setPower(gamepad1.left_trigger * .7);
        }
        else if (gamepad1.right_trigger > 0) {
            intake.leftIntake.setPower(gamepad1.right_trigger * .7);
            intake.rightIntake.setPower(-gamepad1.right_trigger * .7);
        }
        else {
            intake.leftIntake.setPower(0);
            intake.rightIntake.setPower(0);
        }

        /** I/O Controller Controller (gmaepad2)-------------------
         * Controller for intake and output:
         * a.	A: Swivel 180
         * b.	B: Swivel 90
         * c.	Y: Recalibrate SL
         * d.	X: Scissor Lift Set Positions
         * e.	Lt: Down SL
         * f.	Rt: Up SL
         * g.	Lb: Open Claw
         * h.	Rb: Close Claw
         */








        //telemetry ------------------------------
        //telemetry is used to show on the driver controller phone what the code sees
        telemetry.addData("encoder x vlaue:", mecanumWheels.frontLeft.getCurrentPosition());
        telemetry.addData("encoder y vlaue:", mecanumWheels.frontRight.getCurrentPosition());

        telemetry.addData("Power:", power);
        telemetry.addData("F/R:", robot.reverseSense(reverse));
        telemetry.addData("hookServo Position", hookServo.hookServo.getPosition());
        telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);
        telemetry.addData("y:", encoders.getY());

        //telemetry.addData("rampServo Position:", intake.rampServo.getPosition());
        //telemetry.addData("rampServoPosition:", rampPosition);

        /* not important in current system
        if (rampServoUp) {
            telemetry.addData("RampServo:", "UP");
        }
        else {
            telemetry.addData("RampServo Position:", "DOWN");
        }
         */


        /*
        Color sensor diagnostics

        telemetry.addLine()
                .addData("r", colorSensor.red())
                .addData("g",  colorSensor.green())
                .addData("b",  colorSensor.blue());
        */

        telemetry.update();
    }

}
