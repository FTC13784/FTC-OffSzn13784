package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous1", group = "Autonomous")
public class Autonomous1 extends LinearOpMode {
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

        ourBot.drive_rf(3500, 1.0);
        telemetry.addData("RF Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.driveCm(200, 1.0);
        telemetry.addData("Forward Power", ourBot.drivePower);
        telemetry.update();

        telemetry.addData("This works", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.driveBackCm(20, 1.0);
        telemetry.addData("Backwards Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.drive_lb(100, 1.0);
        telemetry.addData("LB Power", ourBot.drivePower);
        telemetry.update();

        ourBot.sleep(400);

        ourBot.driveBackCm(400, 1.0);
        telemetry.addData("Backwards Power", ourBot.drivePower);
        telemetry.update();

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
