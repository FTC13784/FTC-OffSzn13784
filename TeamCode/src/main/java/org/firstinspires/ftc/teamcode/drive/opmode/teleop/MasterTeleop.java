package org.firstinspires.ftc.teamcode.drive.opmode.teleop;

import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.LightningMecanumDrive;

/**
 * This is our master teleop class. Always use OpMode instead of LinearOpMode!
 * OpModes are much more flexible and dynamic.
 * In the future I'll have get and set methods that people can override for their own custom teleops.
 * Probably also want a dynamic list or something of usable buttons and their functions.
 * Gotta make it useful for anyone new coding.
 */
@TeleOp(group = "Teleop", name = "Master")
public class MasterTeleop extends OpMode {

    LightningMecanumDrive drive;
    double powerLevel;
    double defaultAngle;

    @Override
    public void init() {
        drive = new LightningMecanumDrive(hardwareMap);
        //Todo: initialize shiz.
        powerLevel = drive.getPowerLevel();
        //10 degrees per tick
        defaultAngle = drive.getTurnDegrees();
    }

    @Override
    public void loop() {


        //To the centre from anywhere on the field
        if (gamepad1.dpad_up) {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineTo(DriveConstants.CENTRE, 0)
                            .build()
            );
        }

        //Left High Goal
        if (gamepad1.left_bumper) {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineTo(DriveConstants.BLUE_HIGH_GOAL, 0)
                            .build()
            );
        }

        //Right High Goal
        if (gamepad1.right_bumper) {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineTo(DriveConstants.RED_HIGH_GOAL, 0)
                            .build()
            );
        }

        //The only way to move is trajectory; accept nothing else.
        //Need a power level for distance
        TrajectoryBuilder trajectory = drive.trajectoryBuilder(drive.getPoseEstimate())
                .forward(powerLevel * gamepad1.left_stick_y)
                .strafeRight(powerLevel * gamepad1.left_stick_x);
        //Assuming right is positive, left is negative (have to test). If this is not the case, make it
        //strafe left instead.
        drive.followTrajectory(trajectory.build());
        drive.turn(Math.toRadians(powerLevel * defaultAngle * gamepad1.right_stick_x));
    }

}
