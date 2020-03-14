package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.logic.BasicPositions;

public class Swivel {
    public Servo swivelServo;

    public ScissorLift scissorLift;

    public BasicPositions targetMode;

    public int targetState=0;

    public long targetSleepTime;

    public void initialize (OpMode opMode) {
        swivelServo = opMode.hardwareMap.servo.get("swivelServo");
    }


    public void setPosition (double position) {
        swivelServo.setPosition(position);
        /*important positions
         * out(180): 0.93
         * in(0): 0.27
         * TODO: 90:?
         */
    }


    public void setPositionEnum (BasicPositions positionEnum) {
        targetMode = positionEnum;
        targetState=0;
    }

    public void checkState() {
        if (targetMode==null) {
            //we are not in an active behavior right now
            return;
        }
        //CLOSED is over the ramp, OPEN is extended
        switch (targetState) {
            case 0:
                if (scissorLift.getPosition() < 4190) {
                    scissorLift.setPosition(4200);
                } else {
                    targetState = 1;
                }
                break;
            case 1:
                targetSleepTime = System.currentTimeMillis() + SLEEP_TIME;
                targetState = 2;
                if (targetMode == BasicPositions.CLOSED) {
                    swivelServo.setPosition(CLOSED_POSITION);
                }
                else if (targetMode == BasicPositions.MIDWAY) {
                    swivelServo.setPosition(MID_POSITION);
                }
                else {
                    swivelServo.setPosition(OPEN_POSITION);
                }
                break;
            case 2:
                if (System.currentTimeMillis() >= targetSleepTime) {
                    if (targetMode == BasicPositions.CLOSED) {
                        scissorLift.setPosition(2500);
                    } else if (targetMode == BasicPositions.OPEN) {
                        scissorLift.setPosition(ScissorLift.presetHeights[0]);
                    } else if (targetMode == BasicPositions.MIDWAY) {
                        scissorLift.setPosition(ScissorLift.presetHeights[1]);
                    }
                    targetMode = null;
                }
                break;
        }
    }

    public void rotate(double increment) {
        double newTarget=swivelServo.getPosition()+increment;
        if (newTarget<.2) {
            newTarget=.2;
        }
        else if (newTarget>.96) {
            newTarget=0.96;
        }
        swivelServo.setPosition(newTarget);
    }

    public static final double CLOSED_POSITION=.26;
    public static final double OPEN_POSITION=.93;
    public static final double MID_POSITION=0.6;

    public static final long SLEEP_TIME=500;

}
