package org.jesuithigh.tests;

import org.firstinspires.ftc.teamcode.MecanumWheels;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test methods for utility methods used by either teleop or autonomous.
 */
public class TestUtilityMethods {

    @Test
    public void testDriveForwardRampDown() {
        //test that proportional controller max power works
        Assert.assertEquals(1,MecanumWheels.calculateProportionalMotorPower(0.5,10,1,0.3),0.000001);

        //test some proportional values output as expected
        Assert.assertEquals(0.325,MecanumWheels.calculateProportionalMotorPower(0.05,0.5,1,0.3),0.000001);
        Assert.assertEquals(0.4,MecanumWheels.calculateProportionalMotorPower(0.05,2,1,0.3),0.000001);
        Assert.assertEquals(0.45,MecanumWheels.calculateProportionalMotorPower(0.05,3,1,0.3),0.000001);
        Assert.assertEquals(0.5,MecanumWheels.calculateProportionalMotorPower(0.05,4,1,0.3),0.000001);
        Assert.assertEquals(0.7,MecanumWheels.calculateProportionalMotorPower(0.1,4,1,0.3),0.000001);

        //test that min power works
        Assert.assertEquals(0.3,MecanumWheels.calculateProportionalMotorPower(0.05,0.0,1,0.3),0.000001);

    }


}