package org.firstinspires.ftc.logic;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Helper class for color sensors.
 */
public class ColorSensorLogic {
    private final ColorSensor colorSensor;

    public ColorSensorLogic(ColorSensor colorSensor) {
        this.colorSensor=colorSensor;
    }

    /**
     * Returns the average colors by sampling the color sensor
     * as many times as possible in the specified time period.
     *
     * @param timeMillis
     * @return
     */
    public int[] getAverageColor(long timeMillis) {
        long currentTime=System.currentTimeMillis();
        long endTime=currentTime+timeMillis;

        int red=0;
        int green=0;
        int blue=0;

        int count=0;

        while (System.currentTimeMillis()<endTime) {
            red+=colorSensor.red();
            green+=colorSensor.green();
            blue+=colorSensor.blue();

            count++;
        }

        //return an array with the average values for red,green, and blue
        return new int[]{red/count,green/count,blue/count};
    }

}
