package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.DriveConstants.ActionType;
import org.firstinspires.ftc.teamcode.drive.DriveConstants.GoalType;
import org.firstinspires.ftc.teamcode.drive.DriveConstants.Side;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

/**
 * Similar to the SampleDrive classes, except designed purely for autonomous.
 * Serves as another layer of abstraction over those classes so autonomous is even easier.
 * Why? Because I'm lazy. Also because this should be usable in future years.
 * Also makes it really easy for newbie coders to learn.
 */
public class AutonomousDriver {

    //TODO: Change trajectoryBuilding to trajectoryFollowing

    private Side side;
    private GoalType goalType;
    private ActionType actionType;
    private Drive drive;
    private HardwareMap map;
    private DriveType driveType;
    private OpMode opMode;

    //Start positions are in DriveConstants

    public AutonomousDriver(Side side, GoalType goalType, ActionType actionType, Drive drive, HardwareMap map, DriveType type, OpMode mode) {
        this.side = side;
        this.goalType = goalType;
        this.actionType = actionType;
        this.drive = drive;
        this.map = map;
        this.driveType = type;
        this.opMode = mode;
    }

    public void start() {
        SampleMecanumDrive mecanumDrive = null;
        SampleTankDrive tankDrive = null;

        //Drive type!
        switch (driveType) {
            case MECANUM:
                driveGoal((SampleMecanumDrive) drive, side, actionType, goalType);
                break;
            case TANK:
                driveGoal((SampleTankDrive) drive, side, actionType, goalType);
                break;
        }
    }


    public void park(SampleMecanumDrive drive, Side side) {
        switch (side) {
            case BLUE:
                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                        .splineTo(DriveConstants.BLUE_PARK, 0));
                break;
            case RED:
                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                        .splineTo(DriveConstants.RED_PARK, 0));
        }

    }

    /**
     * @param drive
     * @param side
     * @param actionType
     */
    public void driveGoal(SampleMecanumDrive drive, Side side, ActionType actionType, GoalType goalType) {
        switch (side) {
            case BLUE:
                switch (actionType) {
                    case WOBBLE:
                        switch (goalType) {
                            case A:
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                        .splineTo(DriveConstants.BLUE_A, 0));
                                break;
                            case B:
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                        .splineTo(DriveConstants.BLUE_B, 0));
                                break;
                            case C:
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                        .splineTo(DriveConstants.BLUE_C, 0));
                                break;
                            default:
                                break;
                        }
                        //Todo: Add a pickup method
                        break;
                    case HIGH_GOAL:
                        followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                .splineTo(DriveConstants.BLUE_HIGH_GOAL, 0));
                        //Todo: Shoot
                        break;
                    default:
                        break;
                }
                break;
            case RED:
                switch (actionType) {
                    case WOBBLE:
                        switch (goalType) {
                            case A:
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate()).splineTo(DriveConstants.RED_A, 0));
                                break;
                            case B:
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate()).splineTo(DriveConstants.RED_B, 0));
                                break;
                            case C:
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate()).splineTo(DriveConstants.RED_C, 0));
                                break;
                            default:
                                break;
                        }
                        //TODO: Pickup method
                        break;
                    case HIGH_GOAL:
                        followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                .splineTo(DriveConstants.RED_HIGH_GOAL, 0));
                        //TODO: Shoot
                    default:
                        break;
                }
                break;
        }
        park(drive, side);
        this.opMode.requestOpModeStop();

    }

    public void driveGoal(SampleTankDrive drive, Side side, ActionType actionType, GoalType goalType) {

    }

    //Call this to update the autonomous class.
    public void update() {
        if (drive instanceof SampleMecanumDrive)
            ((SampleMecanumDrive) drive).update();
        if (drive instanceof SampleTankDrive)
            ((SampleTankDrive) drive).update();
        drive.updatePoseEstimate();
    }

    public void followTrajectory(SampleMecanumDrive drive, TrajectoryBuilder builder) {
        drive.followTrajectory(builder.build());
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
