package org.firstinspires.ftc.teamcode.examples.encoderPractice;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Steve Encoder Testing", group = "Linear Opmode")
//@Disabled

public class EncoderTesting extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    SteveEncoderBot ourBot;

    @Override
    public void runOpMode() throws InterruptedException {
        ourBot = new SteveEncoderBot(this);

        telemetry.addData("Status", "Steve Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        telemetry.addData("Status", "Start received");
        telemetry .update();

        ourBot.driveDistance(12, 0.75);
        ourBot.turnRight(90, 0.5);
        ourBot.driveDistance(12, 0.75);
        ourBot.turnRight(90, 0.5);
        ourBot.driveDistance(12, 0.75);
        ourBot.turnRight(90, 0.5);
        ourBot.driveDistance(12, 0.75);
        ourBot.turnRight(90, 0.5);


        telemetry.addData("Status", "done");
        telemetry.update();
    }
}