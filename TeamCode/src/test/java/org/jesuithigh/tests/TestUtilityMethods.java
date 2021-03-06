package org.jesuithigh.tests;

import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.logic.ButtonOneShot;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.Controller.PIDController;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test methods for utility methods used by either teleop or autonomous.
 */
public class TestUtilityMethods {

    @Test
    public void testDriveForwardRampDown() {
        //test that proportional controller max power works
        Assert.assertEquals(1, MecanumWheels.calculateProportionalMotorPower(0.5,10,1,0.3),0.000001);

        //test some proportional values output as expected
        Assert.assertEquals(0.325,MecanumWheels.calculateProportionalMotorPower(0.05,0.5,1,0.3),0.000001);
        Assert.assertEquals(0.4,MecanumWheels.calculateProportionalMotorPower(0.05,2,1,0.3),0.000001);
        Assert.assertEquals(0.45,MecanumWheels.calculateProportionalMotorPower(0.05,3,1,0.3),0.000001);
        Assert.assertEquals(0.5,MecanumWheels.calculateProportionalMotorPower(0.05,4,1,0.3),0.000001);
        Assert.assertEquals(0.7,MecanumWheels.calculateProportionalMotorPower(0.1,4,1,0.3),0.000001);

       // Assert.assertEquals(0.325,MecanumWheels.calculateProportionalMotorPower(0.005,50,0.5,0.2),0.000001);
      //  Assert.assertEquals(0.325,MecanumWheels.calculateProportionalMotorPower(0.0025,30,0.5,0.2),0.000001);


        //test that min power works
        Assert.assertEquals(0.3,MecanumWheels.calculateProportionalMotorPower(0.05,0.0,1,0.3),0.000001);

    }

    @Test
    public void testButtonOneShot() {
        //tests the logic for one shot buttons
        ButtonOneShot buttonLogic=new ButtonOneShot();

        Assert.assertFalse(buttonLogic.isPressed(false));

        //now hold button down (only initial press should return true)
        Assert.assertTrue(buttonLogic.isPressed(true));
        for (int i=0;i<20;i++) {
            Assert.assertFalse(buttonLogic.isPressed(true));
        }
        //release button
        Assert.assertFalse(buttonLogic.isPressed(false));

        //now hold button down (only initial press should return true)
        Assert.assertTrue(buttonLogic.isPressed(true));
        for (int i=0;i<20;i++) {
            Assert.assertFalse(buttonLogic.isPressed(true));
        }
        //release button
        Assert.assertFalse(buttonLogic.isPressed(false));
    }

    @Test
    public void testPIDvsProportional () {
        MecanumWheels wheels = new MecanumWheels(ChassisName.TANK);
        PIDController pid = new PIDController(0.0015, 0, 0);
        pid.input(30);
        Assert.assertTrue(wheels.calculateProportionalMotorPower(0.0015, 30, .4,.2) > Math.abs(pid.output()));
        //pid.input(30);
        //Assert.assertTrue();
        //pid.input(90);
        //Assert.assertTrue();


    }


    PIDController pid = new PIDController(0.00225, 0.001, 0.001);

    @Test
    public void testPID () {
        pid.setTarget(0);
        pid.input(-25);
        Assert.assertEquals(1, pid.output(),0.000001 );
        pid.input(-20);
        //Assert.assertEquals(1, pid.output(),0.000001 );

        // above a distance of about 25 the output will be over 1. we might want to divide by 10 in our program
        /*
        here the target it set to 0 so that distance is represented exactly by the input.
        Outputs are negative because the correction would be to move backwards.

        input: 3000 output: -136
        input: 200 output: -9
        input: 25 output: -1.2
        input: 20 output: -.89

         */

    }

}