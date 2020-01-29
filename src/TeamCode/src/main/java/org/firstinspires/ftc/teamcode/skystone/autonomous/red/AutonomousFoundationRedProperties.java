package org.firstinspires.ftc.teamcode.skystone.autonomous.red;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FTCConstants;
import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;
import org.firstinspires.ftc.teamcode.util.PropertiesLoader;

/**
 * Drives forward, grabs foundation, goes back, and parks ON THE LINE
 * for points.
 *
 * @author FavouriteDragon
 */

@Autonomous(name = "AutonomousFoundationRedProperties", group = "Red Autonomous")
public class AutonomousFoundationRedProperties extends LinearOpMode {

    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new EncoderFunLight(this);
        PropertiesLoader loader = new PropertiesLoader("foundation");

        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runTime.reset();

        bot.openFoundation();
        bot.closeClaw();

        /*** Test Properties Code **/

        //Drive right
        bot.driveCm(loader.getFloatProperty("driveRight"), loader.getFloatProperty("drivePower"));

        //Drive Forward
        bot.driveLeftCm(loader.getFloatProperty("driveForward"), loader.getFloatProperty("drivePower"));

        //Grab the foundation
        bot.closeFoundation();

        //Drive back
        bot.driveRightCm(loader.getFloatProperty("driveBack"), loader.getFloatProperty("drivePower"));

        //Release the foundation
        bot.openFoundation();

        //Drive to the line
        bot.driveBackCm(loader.getFloatProperty("driveLeft"), loader.getFloatProperty("drivePower"));

        //Drive up to the bridge
        bot.driveLeftCm(loader.getFloatProperty("driveForwardToBridge"), loader.getFloatProperty("drivePower"));

        /*** Test Properties End **/
        
        bot.stop();
        bot.closeFoundation();
    }
}
