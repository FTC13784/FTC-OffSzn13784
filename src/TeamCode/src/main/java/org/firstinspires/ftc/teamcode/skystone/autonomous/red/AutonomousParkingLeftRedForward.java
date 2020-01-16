package org.firstinspires.ftc.teamcode.skystone.autonomous.red;

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

@Autonomous(name = "AutonomousParkingLeftRedForward", group = "Red Autonomous")
public class AutonomousParkingLeftRedForward extends LinearOpMode {

    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new EncoderFunLight(this);

        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runTime.reset();
        bot.closeFoundation();
        bot.closeClaw();

        //Park
        //Go past line on the right, extend motor
        bot.driveCm(1 * FTCConstants.ONE_SQUARE, 0.35);
        bot.driveLeftCm(1 * FTCConstants.ONE_SQUARE, 0.35F);
      //  bot.extendCM(20, 0.5, getRuntime());
    }
}
