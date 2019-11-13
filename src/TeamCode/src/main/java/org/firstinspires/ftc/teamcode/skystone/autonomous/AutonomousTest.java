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

        ourBot.driveTicks(5000, 1.0);

        wait(1000);
        telemetry.addData("Power", ourBot.drivePower);
        telemetry.update();

        ourBot.driveBackTicks(5000,1.0);
        telemetry.addData("Backwards Power", ourBot.drivePower);
        telemetry.update();

        wait(1000);

        ourBot.turnLeft(1000,1.0);
        ourBot.turnRight(1000,1.0);
        ourBot.turnLeft(1000,1.0);

        telemetry.addData("Statues", "Done");

        telemetry.update();

    }
}
