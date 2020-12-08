package org.firstinspires.ftc.teamcode.drive.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This is our master teleop class. Always use OpMode instead of LinearOpMode!
 * OpModes are much more flexible and dynamic.
 * In the future I'll have get and set methods that people can override for their own custom teleops.
 * Probably also want a dynamic list or something of usable buttons and their functions.
 * Gotta make it useful for anyone new coding.
 */
@TeleOp(group = "Teleop", name = "Master")
public class MasterTeleop extends OpMode {

    SampleMecanumDrive drive;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        //Todo: initialize shiz.
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
    }

}
