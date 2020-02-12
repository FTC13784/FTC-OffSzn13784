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

@Autonomous(name = "AutonomousFoundationRedBack", group = "Red Autonomous")
public class AutonomousFoundationRedBack extends LinearOpMode {

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

        //Drive right
        bot.driveCm(1 * FTCConstants.ONE_SQUARE, 0.4F);

        while (!bot.touchSensor.isPressed())
            bot.driveLeftCm(2, 0.4);

        //Grab foundation
        bot.closeFoundation();

        //Deliver Foundation
        bot.driveRightCm(2 * FTCConstants.ONE_SQUARE - 31, 0.3F);

        //Release Foundation
        bot.openFoundation();

        //Park
        //Go back to the original position
        bot.driveBackCm(2 * FTCConstants.ONE_SQUARE, 0.3);
        //Go past line on the left, extend motor
        bot.closeFoundation();
    }
}
