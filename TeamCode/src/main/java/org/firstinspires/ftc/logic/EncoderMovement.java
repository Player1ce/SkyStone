package org.firstinspires.ftc.logic;


import org.firstinspires.ftc.devices.ChassisName;
import org.firstinspires.ftc.devices.Encoders;
import org.firstinspires.ftc.devices.MecanumWheels;

public class EncoderMovement {
    ChassisName chassis;

    public EncoderMovement (ChassisName chassisName) { chassis = chassisName; }

    private Encoders encoders = new Encoders(0, 0, chassis);
    private MecanumWheels wheels = new MecanumWheels(chassis);


}
