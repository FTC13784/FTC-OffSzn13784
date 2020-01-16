package org.firstinspires.ftc.teamcode.skystone.autonomous.blue;

import com.qualcomm.ftccommon.FtcAboutActivity;
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

@Autonomous(name = "AutonomousFoundationBlueForward", group = "Blue Autonomous")
public class AutonomousFoundationBlueForward extends LinearOpMode {

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

        //Drive right until wall

        //Drive Left 45 cm
        bot.driveBackCm(FTCConstants.ONE_SQUARE / 2, 0.35F);

        //Drive Forward. 44 is roughly the width of the robot
        bot.driveLeftCm(2 * FTCConstants.ONE_SQUARE - 47, 0.35F);

        //Grab foundation
        bot.closeFoundation();
        //Deliver Foundation
        bot.driveRightCm(2 * FTCConstants.ONE_SQUARE - 35, 0.4F);

        //Release Foundation
        bot.openFoundation();

        //Park
        //Original Position
        bot.driveCm(FTCConstants.ONE_SQUARE / 2, 0.35);
        //Go past line on the right, extend motor
        bot.driveCm(1 * FTCConstants.ONE_SQUARE, 0.35);
        bot.driveLeftCm(1 * FTCConstants.ONE_SQUARE, 0.4F);
//        //Drive forward to allow room to rotate
//        bot.driveLeftCm(FTCConstants.ROBOT_WIDTH / 2, 0.3F);
//        //Rotate to face the other direction
//        bot.turnLeft(180, FTCConstants.TURNING_POWER);
//        //Drive back a little more to account for inaccuracies
//        bot.driveLeftCm(FTCConstants.ROBOT_WIDTH, 0.3F);
//        bot.extendCM(20, 0.5F, getRuntime());
        bot.closeFoundation();
        bot.stop();
        bot.stopDriving();
    }
}
