package org.firstinspires.ftc.teamcode.skystone.autonomous.blue;

import android.os.SystemClock;
import android.provider.Settings;

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

@Autonomous(name = "AutonomousParkingBlueForward", group = "Blue Autonomous")
public class AutonomousParkingBlueForward extends LinearOpMode {

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
        bot.driveCm(1 * FTCConstants.ONE_SQUARE, 0.4F);
        bot.driveLeftCm(1 * FTCConstants.ONE_SQUARE, 0.4F);
//        //Drive forward to allow room to rotate
//        bot.driveLeftCm(FTCConstants.ROBOT_WIDTH / 2, 0.4F);
//        //Rotate to face the other direction
//        bot.turnLeft(180, FTCConstants.TURNING_POWER);
//        //Drive back a little more to account for inaccuracies
//        bot.driveLeftCm(FTCConstants.ROBOT_WIDTH / 4, 0.35F);
//       // bot.extendCM(20, 0.5F, SystemClock.currentThreadTimeMillis());
//        bot.closeFoundation();
        bot.stop();
    }
}
