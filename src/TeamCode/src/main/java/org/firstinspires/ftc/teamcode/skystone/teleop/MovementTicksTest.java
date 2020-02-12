package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;

@TeleOp(name = "Movement Test", group = "!tele")
public class MovementTicksTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        EncoderFunLight bot = new EncoderFunLight(this);
        waitForStart();

        bot.driveTicks(1000, 0.4F);
        bot.stopDriving();
        bot.sleep(1000);
        bot.driveTicks(2000, 0.4F);
        bot.stopDriving();
        bot.sleep(1000);
        bot.driveTicks(5000, 0.4F);
        bot.stopDriving();
        bot.sleep(1000);
        bot.driveTicks(10000, 0.4F);
        bot.stopDriving();
    }
}
