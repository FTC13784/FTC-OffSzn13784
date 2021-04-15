package org.firstinspires.ftc.teamcode.drive.opmode.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
@TeleOp(group = "Teleop")
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
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override   
    public void loop() {
        powerLevel = drive.getPowerLevel();


        /** Control functions for resetting position **/
        //To the centre from anywhere on the field
     /*   if (controller1.dpadUpOnce()) {
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
        }**/

        /** Controlled Movement**/
        drive.setWeightedDrivePower(
                new Pose2d(
                        -gamepad1.left_stick_x * powerLevel * 0.75,
                        -gamepad1.left_stick_y * powerLevel * 0.75,
                        -gamepad1.right_stick_y * powerLevel * 0.75
                )
        );


        /** Intake and Shooting **/
        //Should be right and left but leo and soren are fuckin nerds
        if (controller1.Y())
            drive.powerShooter();
        else drive.shooterOff();

        if (controller1.X())
            drive.powerIntake();
        else drive.intakeOff();

        /** Miscellaneous **/

        //Speed toggle
        if (controller1.AOnce())
            drive.togglePowerLevel();

    }

}
