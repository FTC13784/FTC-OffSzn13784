package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public class LightningMecanumDrive extends SampleMecanumDrive {

    //Need to initialise:
    //Wobble goal mechanism/claw
    //Distance sensor
    //TODO:
    //Intake & load motor
    //Shoot motor
    //Right & left triggers: intake, shooter on respectively. Hold. 360 servos. Use 0-90.

    //bruh

    private UltrasonicSensor distanceSensor;

    public LightningMecanumDrive(HardwareMap hardwareMap) {
        super(hardwareMap);

        distanceSensor = hardwareMap.get(UltrasonicSensor.class, "ds");
    }

    public double getCmDestance(UltrasonicSensor sensor) {
        return sensor.getUltrasonicLevel();
    }
}
