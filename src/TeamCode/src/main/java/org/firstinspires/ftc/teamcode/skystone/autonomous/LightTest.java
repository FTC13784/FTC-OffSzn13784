package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "LightTest", group = "Autonomous")
public class LightTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    EncoderFunLight ourBot;

    @Override
    public void runOpMode() throws InterruptedException {
        ourBot = new EncoderFunLight(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        telemetry.addData("Status", "Start received");
        telemetry.update();

       //ourBot.driveUntilAlpha(500, 50);

        ourBot.lightTele();

        telemetry.addData("Statues", "Done");
        telemetry.update();

    }
}
