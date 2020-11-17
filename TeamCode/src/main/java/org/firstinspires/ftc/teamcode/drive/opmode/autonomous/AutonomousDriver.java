package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.SampleRevBlinkinLedDriver;
import org.firstinspires.ftc.teamcode.drive.DriveConstants.*;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

/**
 * Similar to the SampleDrive classes, except designed purely for autonomous.
 * Serves as another layer of abstraction over those classes so autonomous is even easier.
 * Why? Because I'm lazy. Also because this should be usable in future years.
 * Also makes it really easy for newbie coders to learn.
 */
public class AutonomousDriver {

    private Side side;
    private GoalType goalType;
    private ActionType actionType;
    private Drive drive;
    private HardwareMap map;
    private DriveType driveType;

    public AutonomousDriver(Side side, GoalType goalType, ActionType actionType, Drive drive, HardwareMap map, DriveType type) {
        this.side = side;
        this.goalType = goalType;
        this.actionType = actionType;
        this.drive = drive;
        this.map = map;
        this.driveType = type;
    }

    public void start() {
        SampleMecanumDrive mecanumDrive = null;
        SampleTankDrive tankDrive = null;

        switch (driveType) {
            case MECANUM:
                mecanumDrive = (SampleMecanumDrive) drive;
                break;
            case TANK:
                tankDrive = (SampleTankDrive) drive;
                break;
        }

    }

    //Call this to update the autonomous class.
    public void update() {
        if (drive instanceof SampleMecanumDrive)
            ((SampleMecanumDrive) drive).update();
        if (drive instanceof SampleTankDrive)
            ((SampleTankDrive) drive).update();
        drive.updatePoseEstimate();
    }

    public void end() {

    }

    //Useful for identifying which actions can and can't be used
    public enum DriveType {
        MECANUM,
        TANK,
        CUSTOM,
        NONE;
    }
}
