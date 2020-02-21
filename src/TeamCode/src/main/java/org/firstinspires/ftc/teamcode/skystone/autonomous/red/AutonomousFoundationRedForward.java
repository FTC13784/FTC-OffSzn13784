package org.firstinspires.ftc.teamcode.skystone.autonomous.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Predicate;
import org.firstinspires.ftc.teamcode.FTCConstants;
import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;

/**
 * Drives forward, grabs foundation, goes back, and parks ON THE LINE
 * for points.
 *
 * @author FavouriteDragon
 */

@Autonomous(name = "AutonomousFoundationRedForward", group = "Red Autonomous")
public class AutonomousFoundationRedForward extends LinearOpMode {

    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new EncoderFunLight(this);

        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runTime.reset();

        bot.openFoundation();

        //Drive right
        bot.driveCm(1.25F * FTCConstants.ONE_SQUARE, 0.4F);

        //Drive Left 45 cm

        bot.driveContinuousLeftCm(0.385F, new Predicate<EncoderFunLight>() {
            @Override
            public boolean test(EncoderFunLight encoderFunLight) {
                return encoderFunLight.touchSensor.isPressed();
            }
        });
        //Grab foundation
        bot.closeFoundation();

        //Deliver Foundation
        bot.driveRightCm(2 * FTCConstants.ONE_SQUARE, 0.325F);

        //Release Foundation
        bot.openFoundation();

        //Park
        // Go towards the line
        bot.driveBackCm(1.75 * FTCConstants.ONE_SQUARE, 0.385);
        bot.closeFoundation();
        bot.driveLeftCm(1 * FTCConstants.ONE_SQUARE, 0.385);
        bot.driveBackCm(0.675F * FTCConstants.ONE_SQUARE, 0.385);

        bot.closeFoundation();
        bot.stopDriving();
    }
}
