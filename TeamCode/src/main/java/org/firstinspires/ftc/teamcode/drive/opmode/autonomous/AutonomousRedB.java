package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "redB", group = "autonomous")
public class AutonomousRedB extends MasterAutonomous {

    @Override
    public DriveConstants.Side getSide() {
        return DriveConstants.Side.RED;
    }

    @Override
    public DriveConstants.GoalType getGoal() {
        return DriveConstants.GoalType.B;
    }

    @Override
    public DriveConstants.ActionType getAction() {
        return DriveConstants.ActionType.WOBBLE;
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
