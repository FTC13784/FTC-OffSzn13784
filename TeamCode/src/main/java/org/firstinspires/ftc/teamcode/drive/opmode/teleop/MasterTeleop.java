package org.firstinspires.ftc.teamcode.drive.opmode.teleop;

import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.LightningMecanumDrive;
import org.firstinspires.ftc.teamcode.util.Controller;

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
    Controller controller1, controller2;

    @Override
    public void init() {
        drive = new LightningMecanumDrive(hardwareMap);
        //Todo: initialize shiz.
        powerLevel = drive.getPowerLevel();
        //10 degrees per tick
        defaultAngle = drive.getTurnDegrees();
        controller1 = new Controller(gamepad1);
        controller2 = new Controller(gamepad2);
    }

    @Override   
    public void loop() {
        powerLevel = drive.getPowerLevel();


        /** Control functions for resetting position **/
        //To the centre from anywhere on the field
        if (controller1.dpadUpOnce()) {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineTo(DriveConstants.CENTRE, 0)
                            .build()
            );
        }

        //Left High Goal
        if (controller1.leftBumperOnce()) {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineTo(DriveConstants.BLUE_HIGH_GOAL, 0)
                            .build()
            );
        }

        //Right High Goal
        if (controller1.rightBumperOnce()) {
            drive.followTrajectory(
                    drive.trajectoryBuilder(drive.getPoseEstimate())
                            .splineTo(DriveConstants.RED_HIGH_GOAL, 0)
                            .build()
            );
        }

        /** Controlled Movement**/
        //The only way to move is trajectory; accept nothing else.
        //Need a power level for distance
        TrajectoryBuilder trajectory = drive.trajectoryBuilder(drive.getPoseEstimate())
                .forward(powerLevel * gamepad1.left_stick_y)
                .strafeRight(powerLevel * gamepad1.left_stick_x);
        //Assuming right is positive, left is negative (have to test). If this is not the case, make it
        //strafe left instead.
        drive.followTrajectory(trajectory.build());
        drive.turn(Math.toRadians(powerLevel * defaultAngle * gamepad1.right_stick_x));

        /** Intake and Shooting **/
        if (controller1.rightBumper())
            drive.powerShooter();
        else drive.shooterOff();

        if (controller1.leftBumper())
            drive.powerIntake();
        else drive.intakeOff();

        /** Miscellaneous **/

        //Speed toggle
        if (controller1.AOnce())
            drive.togglePowerLevel();

    }

}
