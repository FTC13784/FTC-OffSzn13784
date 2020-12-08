package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "drive", name = "master")
public class MasterAutonomous extends OpMode {

    AutonomousDriver driver;

    @Override
    public void init() {
        driver = new AutonomousDriver(DriveConstants.Side.BLUE, DriveConstants.GoalType.A, DriveConstants.ActionType.PARK,
                new SampleMecanumDrive(hardwareMap), hardwareMap, AutonomousDriver.DriveType.MECANUM, this);

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
}
