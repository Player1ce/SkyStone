package org.firstinspires.ftc.useless;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutoMethodsTank extends BasicRobotMethods{
    LinearOpMode linearOpMode;
    OpMode opMode;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    Servo hookServo;

    final double WHEEL_DIAMETER = 6;
    final int NR40_PPR = 1120;
    final double DRIVE_WHEEL_GEAR_RATIO = 1;

    @Override
    public void InitializeHardware(OpMode opMode) {
        //initalize motors and set direction and mode
        frontLeft = linearOpMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        //frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backLeft = linearOpMode.hardwareMap.dcMotor.get("backLeft");
        //backLeft.setDirection(DcMotor.Direction.REVERSE);
        //backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRight = linearOpMode.hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        //frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backRight = linearOpMode.hardwareMap.dcMotor.get("backRight");
        //backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        hookServo = linearOpMode.hardwareMap.servo.get("hookServo");
    }

    @Override
    public void moveHook(String position){
        String fixedPosition = position.toLowerCase();
        if (fixedPosition.equals("up"))  {
            hookServo.setPosition(.47);
        }
        else if (fixedPosition.equals("down"))  {
            hookServo.setPosition(1);
        }
    }


}
