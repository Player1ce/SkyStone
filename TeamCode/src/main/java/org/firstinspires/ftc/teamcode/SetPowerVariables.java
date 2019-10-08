package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class SetPowerVariables {
    double x_right;
    double x_left;
    double y_left;

    public void setMovementVars (Gamepad gamepad1, boolean reverse) {
        x_left = gamepad1.left_stick_x;
        if (!reverse) {
            x_right = gamepad1.right_stick_x;
            y_left = -gamepad1.left_stick_y;
        } else {
            x_right = -gamepad1.right_stick_x;
            y_left = gamepad1.left_stick_y;
        }
    }
}
