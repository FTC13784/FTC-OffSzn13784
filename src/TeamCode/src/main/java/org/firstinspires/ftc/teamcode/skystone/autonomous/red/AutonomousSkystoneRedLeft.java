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
public class AutonomousSkystoneRedLeft extends LinearOpMode {
    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        FTCConstants.DIRECTION direction = FTCConstants.DIRECTION.LEFT;

        bot = new EncoderFunLight(this);

        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runTime.reset();

        bot.openFoundation();
        bot.closeClaw();

        int blocks = 0;

        // drive up to wall to reset orientation
        bot.driveLeftCm(ONE_SQUARE + 2, 0.35F);

        // drive up to skystones
        bot.driveCm(ONE_SQUARE, 0.35F);

        // drive until skystone detected
        blocks = bot.driveUntilPicture(800, direction);

        bot.closeClaw();

        bot.driveBackCm(ONE_SQUARE, 0.35F);

        bot.turnRight(90, FTCConstants.TURNING_POWER);

        bot.driveCm(4.5 * ONE_SQUARE - blocks * BLOCK_LENGTH, 0.35F);

        bot.openClaw();

    }
}
