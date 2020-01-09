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

@Autonomous(name = "AutonomousParkingRedRight", group = "Red Autonomous")
public class AutonomousParkingRedRight extends LinearOpMode {

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
        //Go one square the left
        bot.driveBackCm(1 * FTCConstants.ONE_SQUARE, 0.35);
        //Drive forward to allow room to rotate
        bot.driveLeftCm(FTCConstants.ROBOT_WIDTH / 3, 0.35F);
        //Rotate to face the other direction
        bot.turnLeft(180, 0.25F);
        //Drive back
        bot.driveLeftCm(FTCConstants.ROBOT_WIDTH / 2.5, 0.35F);
        //Extend
        bot.extensionMotor.setTargetPosition(2);
    }
}
