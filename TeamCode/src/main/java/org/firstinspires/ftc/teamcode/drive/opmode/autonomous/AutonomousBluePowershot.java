package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "autonomous", name = "blue_powershot")
public class AutonomousBluePowershot extends MasterAutonomous {

    @Override
    public DriveConstants.Side getSide() {
        return DriveConstants.Side.BLUE;
    }

    @Override
    public DriveConstants.GoalType getGoal() {
        return DriveConstants.GoalType.NONE;
    }

    @Override
    public DriveConstants.ActionType getAction() {
        return DriveConstants.ActionType.POWER_SHOT;
    }

    @Override
    public Drive getDrive() {
        return new SampleMecanumDrive(hardwareMap);
    }

    @Override
    public AutonomousDriver.DriveType getDriveType() {
        return AutonomousDriver.DriveType.MECANUM;
    }

    @Override
    public OpMode getMode() {
        return this;
    }
}
