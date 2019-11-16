package org.firstinspires.ftc.controlunits;

public class ButtonOneShot {
    //state variable that tracks whether the button will accept the input
    private boolean waitForPush;

    /**
     * Contains logic to "one shot" a button.  Returns true
     * only if the button is currently pressed on this scan and it was
     * not pressed on the previous scan.
     *
     */
    public boolean isPressed(boolean buttonState) {
        if (buttonState) {
            if (waitForPush) {
                waitForPush = false;
                return true;
            }
        }
        else {
            waitForPush = true;
        }
        return false;
    }
}
