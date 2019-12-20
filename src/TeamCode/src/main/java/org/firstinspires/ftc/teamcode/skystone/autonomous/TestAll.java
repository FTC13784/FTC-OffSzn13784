package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous1", group = "Autonomous")
public class TestAll extends LinearOpMode {
    // start timer
    private ElapsedTime runtime = new ElapsedTime();

    EncoderFun ourBot;
    long start, now, duration = 0;
    // private RobotState currentState = RobotState.start;

    @Override
    public void runOpMode() throws InterruptedException {
        ourBot = new EncoderFun(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        telemetry.addData("Status", "Start received");
        telemetry.update();

        ourBot.driveLeftCm(80, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.driveRightCm(80, 1.0);
        telemetry.addData("  Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.driveCm(80, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.driveBackCm(80, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.drive_rf(1000, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.drive_lb(1000, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.drive_lf(1000, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.drive_rb(1000, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.turnRight(90, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(3000);

        ourBot.turnLeft(90, 1.0);
        telemetry.addData("Left Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(3000);

        telemetry.addData("Statues", "Done");

        telemetry.update();

    }

   /* private enum RobotState {
        start,
        driveForward,
        driveRight,
        driveLeft,
        driveForwardRight,
        driveFowardLeft,
        drive,
        driveBackward,
        driveBackRight,
        driveBackLeft,
        stop,
        wait,
        turnLeft,
        turnRight,
        clawUp,
        clawDown,
        clawForward,
        clawBack,
        detectStone,
        detectLine;
    }
**/


}
