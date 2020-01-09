package org.firstinspires.ftc.teamcode.skystone.autonomous.blue;

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

@Autonomous(name = "AutonomousFoundationBlueLeft", group = "Blue Autonomous")
public class AutonomousFoundationBlueLeft extends LinearOpMode {

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

        //Drive Forward. 44 is roughly the width of the robot
        bot.driveLeftCm(2 * FTCConstants.ONE_SQUARE - 44, 0.35F);

        //Grab foundation
        bot.closeFoundation();

        //Deliver Foundation
        bot.driveRightCm(2 * FTCConstants.ONE_SQUARE - 44, 0.35F);

        //Release Foundation
        bot.openFoundation();

        //Park
        //Go one square the right
        bot.driveCm(1 * FTCConstants.ONE_SQUARE, 0.35);
        //Extend
        bot.extensionMotor.setTargetPosition(2);
    }
}
