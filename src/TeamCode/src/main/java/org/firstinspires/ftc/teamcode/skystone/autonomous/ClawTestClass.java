package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "ClawTest", group = "AutoTest")
public class ClawTestClass extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        EncoderFunLight bot = new EncoderFunLight(this);
        waitForStart();
        bot.raiseClaw();
        bot.openClaw();
        bot.sleep(1000);
        bot.lowerClaw();
        bot.closeClaw();

    }
}
