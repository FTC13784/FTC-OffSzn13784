package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.LightningMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "autonomous")
public class AutonomousBluePark extends MasterAutonomous {

    //HELL YEAH IT WORKS
    //2 rules: SIDEWAYS, AND RUN IT TWICE EACH TIME. START POSITIONING IS EXACT OR YOU ARE FUCKED.
    //Didn't set heading for running it sideways; did that change???
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
        return DriveConstants.ActionType.PARK;
    }

    @Override
    public Drive getDrive() {
        return new LightningMecanumDrive(hardwareMap);
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

