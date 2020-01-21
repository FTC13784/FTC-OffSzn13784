/**
 * Program starts on red side, detects for skystone, places on moved foundation, and parks on
 *      left side.
 *
 * @author Edwardidk
 */

package org.firstinspires.ftc.teamcode.skystone.autonomous.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FTCConstants;
import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;

import static org.firstinspires.ftc.teamcode.FTCConstants.BLOCK_LENGTH;
import static org.firstinspires.ftc.teamcode.FTCConstants.ONE_SQUARE;

@Autonomous(name = "AutonomousSkystoneRedLeft", group = "Red Autonomous")
public class  AutonomousSkystoneRedLeft extends LinearOpMode {
    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new EncoderFunLight(this);

        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runTime.reset();

        bot.setupLift();
        bot.openFoundation();
        bot.closeClaw();

        int blocks;

        // drive up to wall to reset orientation
        bot.driveLeftCm(ONE_SQUARE + 2, 0.35F);

        bot.openClaw();
        bot.extendCM(10, 0.3F);
        // drive up to skystones
        bot.driveCm(1.3 * ONE_SQUARE, 0.35F);

        bot.driveLeftCm(0.2 * ONE_SQUARE, 0.35F);

        // drive until skystone detected
        blocks = bot.driveUntilPicture(1000, FTCConstants.DIRECTION.RIGHT);

        // grab block
        bot.closeClaw();

        // drive back and orient so robot can move under bridge
        bot.driveBackCm(0.6 * ONE_SQUARE, 0.35F);

        // drive to moved foundation
        bot.driveRightCm(3.5 * ONE_SQUARE - blocks * BLOCK_LENGTH, 0.50F);

        // let go of block onto moved foundation
        bot.openClaw();

        bot.driveLeftCm(1.5 * ONE_SQUARE, 0.35F);
    }
}
