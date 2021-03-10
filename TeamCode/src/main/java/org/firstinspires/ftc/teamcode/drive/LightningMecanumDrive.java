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

    //Degrees per tick
    private double turnDegrees;
    //How fast everything goes
    private double powerLevel;
    private UltrasonicSensor distanceSensor;

    public LightningMecanumDrive(HardwareMap hardwareMap) {
        super(hardwareMap);

        this.powerLevel = 1.0;
        this.turnDegrees = 10;
        distanceSensor = hardwareMap.get(UltrasonicSensor.class, "ds");
    }

    public double getTurnDegrees() {
        return this.turnDegrees;
    }

    public double getPowerLevel() {
        return this.powerLevel;
    }

    public double getDistanceCm(UltrasonicSensor sensor) {
        return sensor.getUltrasonicLevel();
    }
}
