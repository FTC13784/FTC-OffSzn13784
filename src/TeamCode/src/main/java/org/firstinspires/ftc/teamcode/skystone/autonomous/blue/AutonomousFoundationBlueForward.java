package org.firstinspires.ftc.teamcode.skystone.autonomous.blue;

import com.qualcomm.ftccommon.FtcAboutActivity;
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

         //Drive left one square (drive a little less due to the robot's jank)
        bot.driveBackCm(FTCConstants.ONE_SQUARE * 1.25F, 0.4F);

        //Drive up to the foundation
        bot.driveContinuousLeftCm(0.385F, new Predicate<EncoderFunLight>() {
            @Override
            public boolean test(EncoderFunLight encoderFunLight) {
                return encoderFunLight.touchSensor.isPressed();
            }
        });
        
        //Grab foundation
        bot.closeFoundation();


        //Release Foundation
        bot.openFoundation();

        //Park
        //Drive to the right
        bot.driveCm(FTCConstants.ONE_SQUARE * 1.25F, 0.385F);
        bot.closeFoundation();
        runTime.reset();
        //Lower the intake
        bot.moveIntake(-0.25F, new Predicate<EncoderFunLight>() {
            @Override
            public boolean test(EncoderFunLight encoderFunLight) {
                telemetry.addData("Runtime: ", runTime.seconds());
                return runTime.seconds() > 1.5;
            }
        });
        bot.driveLeftCm(1 * FTCConstants.ONE_SQUARE, 0.385);
        bot.driveCm(0.5F * FTCConstants.ONE_SQUARE, 0.385);
        bot.stopDriving();
    }
}
