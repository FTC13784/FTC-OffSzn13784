package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.geometry.Vector2d;
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

    //Start positions are in DriveConstants

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

        //Drive type!
        switch (driveType) {
            case MECANUM:
                driveGoal((SampleMecanumDrive) drive, side, goalType);
                break;
            case TANK:
                driveGoal((SampleTankDrive) drive, side, goalType);
                break;
        }
    }


    /**
     *
     * @param drive
     * @param side
     * @param type
     */
    public void driveGoal(SampleMecanumDrive drive, Side side, GoalType type) {
        switch (side) {
            case BLUE:
                //TODO: Add an ActionType variable
                switch (type) {
                    case A:
                        drive.trajectoryBuilder(drive.getPoseEstimate())
                                .splineTo(new Vector2d(10, 60), 0).build();
                        break;
                    case B:
                        drive.trajectoryBuilder(drive.getPoseEstimate())
                                .splineTo(new Vector2d(35, 37), 0).build();
                        break;
                    case C:
                        drive.trajectoryBuilder(drive.getPoseEstimate())
                                .splineTo(new Vector2d(60, 60), 0).build();
                        break;
                }
                break;
            case RED:
                switch (type) {
                    case A:
                        drive.trajectoryBuilder(drive.getPoseEstimate()).splineTo(new Vector2d(10, -60), 0)
                                .build();
                        break;
                    case B:
                        break;
                    case C:
                        break;
                }
                break;
        }
    }

    public void driveGoal(SampleTankDrive drive, Side side, GoalType type) {

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
