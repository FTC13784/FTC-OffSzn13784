package org.firstinspires.ftc.teamcode.skystone.autonomous.all;

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

@Autonomous(name = "AutonomousParkingRightBack", group = "Blue Autonomous")
public class AutonomousParkingRightBack extends LinearOpMode {

    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new EncoderFunLight(this);

        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runTime.reset();
        //bot.closeFoundation();
        //bot.closeClaw();
        bot.driveRightCm(1*FTCConstants.ONE_SQUARE,.4F);
        bot.driveBackCm(1 * FTCConstants.ONE_SQUARE, 0.4F);
        //Just extend the motor
      //  bot.extendCM(20, 0.5F, getRuntime());
        bot.stop();
    }
}
