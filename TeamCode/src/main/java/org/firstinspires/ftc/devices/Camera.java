package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class Camera {

    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    WebcamName webcamName;
    private VuforiaLocalizer vuforia;


    private static final String VUFORIA_KEY = "AfnD84D/////AAABmW4G5xT8p0BGsOHFi6XGdl0963rtELJowsONUJMZrPugweZ7oWIk3Z2iECWQli6QtEv2xGM27MpU4sQV5SY/Cz/ZAcBGcxG3/iojXVlZ9rG9M5gk/iGnwKNdrwL0QSUt4DQFjd4oFVSJNQIOIZo5UpRbrYsmvuW9fw8HNZvNedLupacWJ2bQ0LF18AIXeI2kWr1w36NawvITHqsqmxHwWsJOaMhfrOfS4XSyHb+aZqro8NcreKWgZdJfAuAd+/R+tSNEGNubv0yFwMXJ1sin+hMwGFfWvhr2k37InDdXafo67NyK+GjLROwTyfPYWPEzBfd2to5tiOjzu0ghhcpyd3jGvVlDgryq+6EFVYABtDik​";

    public void Camera () {  }

    public void initializeCamera (OpMode opMode) {
        webcamName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        /**
         * We also indicate which camera on the RC we wish to use.
         */
        parameters.cameraDirection = CAMERA_CHOICE;
        parameters.cameraName = webcamName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);


    }

}
