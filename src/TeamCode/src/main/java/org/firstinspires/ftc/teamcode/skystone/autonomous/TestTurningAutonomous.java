package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FTCConstants;
import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;

/**
 * Drives forward, grabs foundation, goes back, and parks ON THE LINE
 * for points.
 *
 * @author FavouriteDragon
 */

@Autonomous(name = "Turning Test", group = "Autonomous")
public class TestTurningAutonomous extends LinearOpMode {

    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new EncoderFunLight(this);

        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runTime.reset();

        bot.openFoundation();
        bot.closeClaw();

        bot.turnLeft(45, 0.2F);
        bot.sleep(2000);

        bot.turnLeft(90, 0.2F);
        bot.sleep(2000);

        bot.turnLeft(180, 0.2F);
        bot.sleep(2000);

        bot.turnLeft(45, 0.35F);
        bot.sleep(2000);

        bot.turnLeft(90, 0.35F);
        bot.sleep(2000);

        bot.turnLeft(180, 0.35F);
        bot.sleep(2000);
    }
}
