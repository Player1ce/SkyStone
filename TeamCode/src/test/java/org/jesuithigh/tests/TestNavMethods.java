package org.jesuithigh.tests;


import org.firstinspires.ftc.Controller.PIDController;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.IMURevHub;
import org.firstinspires.ftc.devices.MecanumWheels;
import org.firstinspires.ftc.logic.ChassisName;
import org.firstinspires.ftc.logic.Navigation;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/*
*tests for navigation units
 */
public class TestNavMethods {

    private final Navigation navigation = new Navigation(ChassisName.TANK);
    ChassisName chassis;
    private PIDController rotationPidController = new PIDController(0.0125, 0.001,0.001);
    private PIDController yPidController = new PIDController(0.15, 10,10);
    private PIDController xPidController = new PIDController(0.15, 0.001,0.01);

    Encoders encoders = new Encoders(chassis);
    IMURevHub imu;
    private MecanumWheels wheels;

    double angle;
    long time = 0;
    double maxRotationCorrectionPower = 0.1;

    Orientation startOrientation;


    public void NavigateCrabTicks (Telemetry telemetry, double MotorPower,
                                   double MinMotorPower, double ticks, double inputx, double inputy, double angle) {
        //imu----------------------------------------------------------------------------------------------
        long curTime;
        long diff;
        startOrientation = imu.getOrientation();
        maxRotationCorrectionPower = (MotorPower - 0.1);
        double correctionPower;
        double rightCorrect = 0;
        double leftCorrect = 0;

        //encoders---------------------------------------------------------------------------------------------
        encoders.resetPosition();
        //encoders.setyTarget((Inches * 4)/.0699);
        encoders.setyTarget(0);
        encoders.setxTarget(ticks);

        navigation.setPIDValues(rotationPidController, .015, 0.001, .001);
        navigation.setPIDValues(yPidController, .15, 0.001, 0.001);
        navigation.setPIDValues(xPidController, .15, .001, .001);
        double distanceX = Math.abs(encoders.xTarget) - Math.abs(encoders.getX());


        while (distanceX > 0) {
            wheels.checkIsActive();

            //imu---------------------------------------------------------------------------------------------
            rotationPidController.input(angle);

            correctionPower = Math.abs(rotationPidController.output());
            correctionPower = Math.max(correctionPower, .2);
            correctionPower = Math.max(-maxRotationCorrectionPower, Math.min(maxRotationCorrectionPower, correctionPower));

            if (angle < 0) {
                rightCorrect = -1 * correctionPower;//-.025;
                leftCorrect = correctionPower;//.025;
            } else if (angle > 0) {
                rightCorrect = correctionPower;//.025;
                leftCorrect = -1 * correctionPower;//-.025;
            }

            curTime = System.currentTimeMillis();
            diff = curTime - time;
            time = curTime;

            //encoders---------------------------------------------------------------------------------------------
            //double distanceX = Math.abs(encoders.getX() - encoders.xTarget);
            double distanceY = Math.abs(encoders.getY() - encoders.yTarget);
            distanceX = encoders.xTarget - encoders.getX();
            //double distanceY = encoders.yTarget - encoders.getY();

            //double powerX = MecanumWheels.calculateProportionalMotorPower(0.0015, distanceX, MotorPower, Math.max(MinMotorPower, .3));
            double powerY = MecanumWheels.calculateProportionalMotorPower(0.0015, inputy, maxRotationCorrectionPower, MinMotorPower);
            //TODO: Check this ---------------------------------------------------------------------------------------------
            double powerX = navigation.calculateCorrectionPower(xPidController, inputx, MotorPower, MinMotorPower);
            //double powerY = calculateCorrectionPower(yPidController, distanceY, MotorPower, MinMotorPower);


            double yDirection = encoders.getYDirection();
            double xDirection = encoders.getXDirection();

/*
            if (encoders.getY() == 0) {
                wheels.setPowerFromGamepad(false,1, 0,1 * xDirection ,0);
            }
*/
            //set power---------------------------------------------------------------------------------------------
            wheels.setPower(
                    (powerY * 0.7 * yDirection) +(powerX) + rightCorrect * 0,
                    (powerY * 0.7 * yDirection) - (powerX) + leftCorrect * 0,
                    (powerY * 0.7 * yDirection) - (powerX) + rightCorrect * 0,
                    (powerY  * 0.7 * yDirection) + (powerX) + leftCorrect * 0
            );
        }

        wheels.StopMotors();
        wheels.sleepAndCheckActive(500);
        imu.resetAngle();
    }

}
