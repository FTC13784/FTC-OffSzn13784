package org.firstinspires.ftc.teamcode.skystone.autonomous.blue;

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

@Autonomous(name = "AutonomousFoundationBlueBack", group = "Blue Autonomous")
public class AutonomousFoundationBlueBack extends LinearOpMode {

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


        //Drive left one square (drive a little less due to the robot's jank)
        bot.driveBackCm(FTCConstants.ONE_SQUARE * 1.25, 0.4F);

        //Drive up to the foundation
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
        bot.driveBackCm(0.25 * FTCConstants.ONE_SQUARE, 0.325);

        bot.openFoundation();

        //Park
        bot.driveCm(1.3F * FTCConstants.ONE_SQUARE, 0.35F);
        bot.closeFoundation();
        bot.turnLeft(180, 0.2F);
        bot.driveBackCm(0.5F * FTCConstants.ONE_SQUARE, 0.385);
        bot.stopDriving();
    }
}
