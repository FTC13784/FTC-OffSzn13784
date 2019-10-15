package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutonomousTest", group = "Autonomous")
public class AutonomousTest extends LinearOpMode {

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

        ourBot.driveTicks(50000, 1);
        ourBot.driveTime(10,1);

        telemetry.addData("Statues", "Done");
        telemetry.update();
    }
}
