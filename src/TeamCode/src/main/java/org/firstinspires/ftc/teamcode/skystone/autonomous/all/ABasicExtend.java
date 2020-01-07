/**
 * Basic program that just extends arm across center line.
 *
 * @author Edwardidk
 */

package org.firstinspires.ftc.teamcode.skystone.autonomous.all;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;

@Autonomous(name = "ABasicExtend", group = "!all")
public class ABasicExtend extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    EncoderFunLight ourBot;

    @Override
    public void runOpMode() throws InterruptedException {
        ourBot = new EncoderFunLight(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        ourBot.setupLift();

    }
}
