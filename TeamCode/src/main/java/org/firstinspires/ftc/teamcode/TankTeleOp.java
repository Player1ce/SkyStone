package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.devices.BlockClaw;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.ScissorLift;
import org.firstinspires.ftc.devices.SkystoneLever;
import org.firstinspires.ftc.devices.Swivel;
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
    private final SkystoneLever skystoneLever = new SkystoneLever();
    private final ScissorLift scissorLift = new ScissorLift(mecanumWheels);
    private final Swivel swivel = new Swivel();
    private final BlockClaw blockClaw = new BlockClaw();

    private ButtonOneShot reverseButtonLogic = new ButtonOneShot();
    private ButtonOneShot powerChangeButtonLogic = new ButtonOneShot();
    private ButtonOneShot hookServoButtonLogic = new ButtonOneShot();
    private ButtonOneShot skystoneLeverButtonLogic = new ButtonOneShot();
    private ButtonOneShot swivel180ButtonLogic = new ButtonOneShot();
    private ButtonOneShot swivel90ButtonLogic = new ButtonOneShot();
    private ButtonOneShot scissorLiftCalibrateButtonLogic = new ButtonOneShot();
    private ButtonOneShot scissorLiftSetPositionsButtonLogic = new ButtonOneShot();
    private ButtonOneShot clawOpenButtonLogic = new ButtonOneShot();
    private ButtonOneShot clawClosedButtonLogic = new ButtonOneShot();


    private ButtonOneShot liftRampButtonLogic = new ButtonOneShot();
    private ButtonOneShot lowerRampButtonLogic = new ButtonOneShot();
    private Encoders encoders = new Encoders(ChassisName.TANK);
    private ButtonOneShot heightUpButtonLogic = new ButtonOneShot();
    private ButtonOneShot heightDownButtonLogic = new ButtonOneShot();
    private ButtonOneShot swivelRotateLeftButtonLogic = new ButtonOneShot();
    private ButtonOneShot swivelRotateRightButtonLogic = new ButtonOneShot();

    //TODO correct starting cars for drive
    private boolean reverse = true;
    private boolean highPower = true;
    private boolean hookServoEnable = false;
    private boolean leverUP = false;
    private boolean scissorLiftDirectControl;
    private boolean clawOpen;
    private String clawState;
    private double clawPosition = .8;
    private int count180 = 0;
    private int count90 = 0;
    //private int setPosition = 0;



    public void init() {
        /* attaching configuration names to each motor; each one of these names must match the name
        of the motor in the configuration profile on the phone (spaces and capitalization matter)
        or else an error will occur
        */
        mecanumWheels.initializeWheels(this);
        hookServo.initializeHook(this);
        intake.initializeIntake(this);
        encoders.initialize(mecanumWheels, this);
        skystoneLever.initialize(this);
        scissorLift.initialize(this);
        swivel.scissorLift=scissorLift;
        swivel.initialize(this);
        blockClaw.initialize(this);

        intake.setIntakeBrakes();

    }

    boolean raisingLiftForSwivel;
    boolean aButtonPressed;

    public void loop() {
        /** Drive Controller (gamepad1) ---------------------------
         * Controller for the driver:
         * a.	B: Reverse
         * b.	A: Power Change
         * c.	Y: Skystone Lever
         * d.	X: Hook
         * e.	Lt: Intake In
         * f.	Lr: Intake Out
         * g.	Lb: Ramp Up
         * h.	Rb: Ramp Down
         */

        //drive train --------------------------------
        if (powerChangeButtonLogic.isPressed(gamepad1.a)) {
            highPower = !highPower;
        }
        if (reverseButtonLogic.isPressed(gamepad1.b)) {
            reverse = !reverse;
        }
        //if high power, use the high power constant, else use the normal power constant
        double HIGH_POWER = 1.0;
        double NORMAL_POWER = 0.5;
        double power = highPower ? HIGH_POWER : NORMAL_POWER;


        mecanumWheels.setPowerFromGamepad(reverse, power, gamepad1.left_stick_x,
                gamepad1.right_stick_x, gamepad1.left_stick_y);


        //foundation hook -------------------------------
        if (hookServoButtonLogic.isPressed(gamepad1.x)) {
            hookServoEnable = !hookServoEnable;
        }
        if (hookServoEnable) {
            hookServo.hookServo.setPosition(0.00);
        } else {
            hookServo.hookServo.setPosition(.2);
            //hookServo.setPosition(.47);
        }

        //Skystone Lever -------------------------------
        if (skystoneLeverButtonLogic.isPressed(gamepad1.y)) {
            leverUP = !leverUP;
        }

        if (leverUP) {
            skystoneLever.setPosition(BasicPositions.UP);
        } else if (!leverUP) {
            skystoneLever.setPosition(BasicPositions.DOWN);
        }

        //intake control ----------------------------
        if (gamepad1.left_trigger > 0) {
            intake.leftIntake.setPower(-gamepad1.left_trigger * .7);
            intake.rightIntake.setPower(gamepad1.left_trigger * .7);
        } else if (gamepad1.right_trigger > 0) {
            intake.leftIntake.setPower(gamepad1.right_trigger * .7);
            intake.rightIntake.setPower(-gamepad1.right_trigger * .7);
        } else {
            intake.leftIntake.setPower(0);
            intake.rightIntake.setPower(0);
        }

        if (lowerRampButtonLogic.isPressed(gamepad1.right_bumper)) {
            intake.lowerRamp();
        }
        else if (liftRampButtonLogic.isPressed(gamepad1.left_bumper)) {
            intake.raiseRamp();
        }

        intake.checkState();


       /* if (gamepad1.left_bumper) {
            position = 500;
        } else if (gamepad1.right_bumper) {
            position = 0;*/


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

            if (swivel180ButtonLogic.isPressed(gamepad2.a)) {
                switch (count180) {
                    case (0):
                        scissorLiftDirectControl = false;
                        swivel.setPositionEnum(BasicPositions.CLOSED);
                        count180 = 1;
                        count90 = 1;
                        break;
                    case (1):
                        scissorLiftDirectControl = false;
                        swivel.setPositionEnum(BasicPositions.OPEN);
                        count180 = 0;
                        count90 = 1;
                        break;
                    default:
                        count180 = 0;
                        break;
                }
            }

            swivel.checkState();
/*
            if (swivel90ButtonLogic.isPressed(gamepad2.b)) {
                switch (count90) {
                    case (0):
                        swivel.setPositionEnum(BasicPositions.CLOSED);
                        count90 = 1;
                        count180 = 1;
                        break;
                    case (1):
                        swivel.setPosition(0.6);
                        count90 = 0;
                        count180 = 1;
                        break;
                    default:
                        count90 = 0;
                        break;
                }
            }
*/
            if (heightUpButtonLogic.isPressed(gamepad2.dpad_up)) {
                scissorLift.increasePresetHeight();
                scissorLiftDirectControl = false;
            }
            else if (heightDownButtonLogic.isPressed(gamepad2.dpad_down)) {
                scissorLift.decreasePresetHeight();
                scissorLiftDirectControl = false;
            }

            if (swivelRotateLeftButtonLogic.isPressed(gamepad2.dpad_left)) {
                swivel.rotate(-0.01);
            }
            else if (swivelRotateRightButtonLogic.isPressed(gamepad2.dpad_right)) {
                swivel.rotate(0.01);
            }


            if (scissorLiftSetPositionsButtonLogic.isPressed(gamepad2.x)) {
                scissorLiftDirectControl = false;
            }

            if (scissorLiftSetPositionsButtonLogic.isPressed(gamepad2.y)) {
                scissorLiftDirectControl = false;
                scissorLift.resetHeight();
            }

            if (gamepad2.left_trigger > 0 && scissorLift.limitSwitch.getState()) {
                scissorLiftDirectControl = scissorLift.switchMode(scissorLiftDirectControl);
                scissorLift.liftMotor.setPower(-gamepad2.left_trigger);
            } else if (gamepad2.right_trigger > 0 ) {
                scissorLiftDirectControl = scissorLift.switchMode(scissorLiftDirectControl);
                scissorLift.liftMotor.setPower(gamepad2.right_trigger);
            } else if (scissorLift.liftMotor.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
                scissorLiftDirectControl = scissorLift.switchMode(scissorLiftDirectControl);
                scissorLift.liftMotor.setPower(0);
            }

            if (gamepad2.left_bumper /*clawOpenButtonLogic.isPressed(gamepad2.left_bumper)*/) {
                clawPosition += 0.01;
            } else if (gamepad2.right_bumper /*clawClosedButtonLogic.isPressed(gamepad2.right_bumper)*/) {
                clawPosition -= 0.01;
            }
            clawPosition=Math.max(0.55,Math.min(0.8,clawPosition));
            blockClaw.clawServo.setPosition(clawPosition);

            //telemetry ------------------------------
            //telemetry is used to show on the driver controller phone what the code sees
            telemetry.addData("SL height:", scissorLift.presetHeight);
            telemetry.addData("Power:", power);
            telemetry.addData("F/R:", robot.reverseSense(reverse));
            telemetry.addData("claw State:", clawPosition);
            telemetry.addData("swivel state:", swivel.targetMode+" "+swivel.targetState);
            telemetry.addData("scissor lift DC:", scissorLiftDirectControl);
            telemetry.addData("hookServo Position", hookServo.hookServo.getPosition());
            telemetry.addData("SL position:", scissorLift.getPosition());
            telemetry.addData("count:", scissorLift.count);
            telemetry.addData("Skystone lever up:", leverUP);

        /*telemetry.addData("x_left:", mecanumWheels.xLeft);
        telemetry.addData("x_right:", mecanumWheels.xRight);
        telemetry.addData("y_left:", mecanumWheels.yLeft);
        telemetry.addData("y:", encoders.getY());

         */

            //telemetry.addData("encoder x vlaue:", mecanumWheels.frontLeft.getCurrentPosition());
            //telemetry.addData("encoder y vlaue:", mecanumWheels.frontRight.getCurrentPosition());

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

