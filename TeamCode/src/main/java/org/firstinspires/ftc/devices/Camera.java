package org.firstinspires.ftc.devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class Camera {
    //TODO: Possible Error parameters changed line 51.
    // We can tweak the accuracy threshold on line 70.

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private WebcamName webcamName;

    private static final String VUFORIA_KEY = "AfnD84D/////AAABmW4G5xT8p0BGsOHFi6XGdl0963rtELJowsONUJMZrPugweZ7oWIk3Z2iECWQli6QtEv2xGM27MpU4sQV5SY/Cz/ZAcBGcxG3/iojXVlZ9rG9M5gk/iGnwKNdrwL0QSUt4DQFjd4oFVSJNQIOIZo5UpRbrYsmvuW9fw8HNZvNedLupacWJ2bQ0LF18AIXeI2kWr1w36NawvITHqsqmxHwWsJOaMhfrOfS4XSyHb+aZqro8NcreKWgZdJfAuAd+/R+tSNEGNubv0yFwMXJ1sin+hMwGFfWvhr2k37InDdXafo67NyK+GjLROwTyfPYWPEzBfd2to5tiOjzu0ghhcpyd3jGvVlDgryq+6EFVYABtDik​";


    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    public void initializeCamera (OpMode opMode) {
        webcamName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        initVuforia(opMode);

        initTfod(opMode);

        tfod.activate();
    }

    public List<Recognition> getUpdatedRecognitions() {
        if (tfod != null) {
            return tfod.getUpdatedRecognitions();
        }
        return new ArrayList<Recognition>(0);
    }


    private void initVuforia (OpMode opMode) {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        //switch the comment to disable the previewer
        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        /**
         * We also indicate which camera on the RC we wish to use.
         */
        parameters.cameraDirection = CAMERA_CHOICE;
        parameters.cameraName = webcamName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod(OpMode opMode) {
        int tfodMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        //The minimum accuracy rate to detect a block
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

}
