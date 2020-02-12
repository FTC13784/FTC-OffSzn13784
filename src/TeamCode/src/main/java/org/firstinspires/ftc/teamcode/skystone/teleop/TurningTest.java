package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;

@TeleOp(name = "TurningTest", group = "!tele")
public class TurningTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        EncoderFunLight bot = new EncoderFunLight(this);
        waitForStart();

        bot.turnLeftTicks(1000, 0.2F);
        bot.sleep(1000);
        bot.turnLeftTicks(2000, 0.2F);
        bot.sleep(1000);
        bot.turnLeftTicks(5000, 0.2F);
        bot.sleep(1000);
        bot.turnLeftTicks(10000, 0.2F);
    }
}
