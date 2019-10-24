package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutonomousTurn", group = "Autonomous")
public class AutoTurn extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    EncoderFun ourBot;

    @Override
    public void runOpMode() throws InterruptedException {
        ourBot = new EncoderFun(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        telemetry.addData("Status", "Start received");
        telemetry.update();

        ourBot.turnLeft(1100,1.0);

        telemetry.addData("Statues", "Done");
        telemetry.update();
    }
}
