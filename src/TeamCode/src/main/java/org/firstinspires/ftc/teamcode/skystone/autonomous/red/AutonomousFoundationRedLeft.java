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

@Autonomous(name = "AutonomousFoundationRedLeft", group = "Red Autonomous")
public class AutonomousFoundationRedLeft extends LinearOpMode {

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
        bot.driveCm(2 * FTCConstants.ONE_SQUARE - FTCConstants.ROBOT_WIDTH / 3 , 0.4F);

        //Drive Forward. 44 is roughly the width of the robot
        bot.driveLeftCm(2 * FTCConstants.ONE_SQUARE - 43, 0.4F);

        //Grab foundation
        bot.closeFoundation();

        //Deliver Foundation
        bot.driveRightCm(2 * FTCConstants.ONE_SQUARE - 42, 0.4F);

        //Release Foundation
        bot.openFoundation();

        //Park
        //Go back to the original position
        bot.driveBackCm(2 * FTCConstants.ONE_SQUARE - FTCConstants.ROBOT_WIDTH / 3, 0.3);
        //Go past line on the left, extend motor
        bot.driveBackCm(1 * FTCConstants.ONE_SQUARE, 0.35F);
        //Not extending for now
        //bot.extendCM(20, 0.5, getRuntime());
        bot.stop();
        bot.closeFoundation();
    }
}
