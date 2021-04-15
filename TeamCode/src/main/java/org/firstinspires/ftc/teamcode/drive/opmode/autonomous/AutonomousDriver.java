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
import org.firstinspires.ftc.teamcode.drive.LightningMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

/**
 * Similar to the SampleDrive classes, except designed purely for autonomous.
 * Serves as another layer of abstraction over those classes so autonomous is even easier.
 * Why? Because I'm lazy. Also because this should be usable in future years.
 * Also makes it really easy for newbie coders to learn.
 */
public class  AutonomousDriver {

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
        LightningMecanumDrive mecanumDrive = null;
        SampleTankDrive tankDrive = null;

        switch (side) {
            case BLUE:
                drive.setPoseEstimate(new Pose2d(
                        DriveConstants.BLUE_START.getX(), DriveConstants.BLUE_START.getY(), 0));
                break;
            case RED:
                drive.setPoseEstimate(new Pose2d(
                        DriveConstants.RED_START.getX(), DriveConstants.RED_START.getY(), 0));

        }
        //Drive type!
        switch (driveType) {
            case MECANUM:
                driveGoal((LightningMecanumDrive) drive, side, actionType, goalType);
                break;
            case TANK:
                driveGoal((SampleTankDrive) drive, side, actionType, goalType);
                break;
        }
    }


    public void park(LightningMecanumDrive drive, Side side) {
        switch (side) {
            case BLUE:
                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                        .splineTo(DriveConstants.BLUE_PARK, 180));
                break;
            case RED:
                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                        .splineTo(DriveConstants.RED_PARK, 180));
        }

    }

    /**
     * @param drive
     * @param side
     * @param actionType
     */
    public void driveGoal(LightningMecanumDrive drive, Side side, ActionType actionType, GoalType goalType) {
        switch (side) {
            case BLUE:
                switch (actionType) {
                    case WOBBLE:
                        switch (goalType) {
                            case A:
                                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                        .splineTo(DriveConstants.BLUE_A, 180));
                                break;
                            case B:
                                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                        .splineTo(DriveConstants.BLUE_B, 180));
                                break;
                            case C:
                                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                        .splineTo(DriveConstants.BLUE_C, 180));
                                break;
                            default:
                                break;
                        }
                        //Todo: Add a pickup method
                        break;
                    case HIGH_GOAL:
                        //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                        followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                .splineTo(DriveConstants.BLUE_HIGH_GOAL, 180));
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
                                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate()).splineTo(DriveConstants.RED_A, 180));
                                break;
                            case B:
                                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate()).splineTo(DriveConstants.RED_B, 180));
                                break;
                            case C:
                                //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                                followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate()).splineTo(DriveConstants.RED_C, 180));
                                break;
                            default:
                                break;
                        }
                        //TODO: Pickup method
                        break;
                    case HIGH_GOAL:
                        //Should be 0 but it turns around; probably because I'm too lazy to properly tune it. TODO.
                        followTrajectory(drive, drive.trajectoryBuilder(drive.getPoseEstimate())
                                .splineTo(DriveConstants.RED_HIGH_GOAL, 180));
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
        if (drive instanceof LightningMecanumDrive)
            ((LightningMecanumDrive) drive).update();
        if (drive instanceof SampleTankDrive)
            ((SampleTankDrive) drive).update();
        drive.updatePoseEstimate();
    }

    public void followTrajectory(LightningMecanumDrive drive, TrajectoryBuilder builder) {
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
