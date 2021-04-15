package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.LightningMecanumDrive;

@Autonomous(group = "autonomous")
public class AutonomousPowerIntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        LightningMecanumDrive drive  = new LightningMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        drive.powerIntake();
        drive.powerFeeder();

        //keeps it running
        while (!isStopRequested() && opModeIsActive());
    }
}
