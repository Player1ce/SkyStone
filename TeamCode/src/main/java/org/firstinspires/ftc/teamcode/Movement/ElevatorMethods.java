package org.firstinspires.ftc.teamcode.Movement;

import org.firstinspires.ftc.teamcode.AutonomousMethods;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class ElevatorMethods extends AutonomousMethods {

    //elevator methods --------------------------------------------------------------------------------------------------------
    //moves elevator down. in turn moves robot up if hanging
    public void moveElevatorDown(double speed) {
        while (bottomLimitSwitch.getState() == true && opModeIsActive()) {
            elevator.setPower(-speed);
        }
        elevator.setPower(0);
    }

    //moves elevator up. in turn moves robot down if hanging
    public void moveElevatorUp(double speed) {
        while (topLimitSwitch.getState() == true && opModeIsActive()) {
            elevator.setPower(speed);
        }
        elevator.setPower(0);
    }

    //moves elevator down based on time. in turn moves robot up if hanging
    public void timeElevatorDown(double speed, double time) {
        double startTime = System.currentTimeMillis();
        while (bottomLimitSwitch.getState() == true && opModeIsActive() && Math.abs(System.currentTimeMillis() - startTime) < time) {
            elevator.setPower(-speed);
        }
        elevator.setPower(0);
    }

    //moves elevator up based on time. in turn moves robot down if hanging
    public void timeElevatorDUp(double speed, double time) {
        double startTime = System.currentTimeMillis();
        while (topLimitSwitch.getState() == true && opModeIsActive() && Math.abs(System.currentTimeMillis() - startTime) < time) {
            elevator.setPower(speed);
        }
        elevator.setPower(0);
    }


}
