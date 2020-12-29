package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import android.drm.DrmStore;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "autonomous", name = "master")
public abstract class MasterAutonomous extends OpMode {

    AutonomousDriver driver;

    //TODO: Possibly make action type an array?
    //Nah you park anyway who cares
    @Override
    public void init() {
        driver = new AutonomousDriver(getSide(), getGoal(), getAction(),
                getDrive(), hardwareMap, getDriveType(), getMode());

    }

    @Override
    public void start() {
        super.start();
        driver.start();
    }

    @Override
    public void loop() {
        driver.update();
    }

    public abstract DriveConstants.Side getSide();

    public abstract DriveConstants.GoalType getGoal();

    public abstract DriveConstants.ActionType getAction();

    public abstract Drive getDrive();

    public abstract AutonomousDriver.DriveType getDriveType();

    public abstract OpMode getMode();
}
