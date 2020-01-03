package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Drives forward, grabs foundation, goes back, and parks ON THE LINE
 * for points.
 *
 * @author FavouriteDragon
 */

@Autonomous(name = "AutonomousFoundation", group = "Autonomous")
public class AutonomousFoundation extends LinearOpMode {

    private ElapsedTime runTime = new ElapsedTime();

    EncoderFunLight bot;
    long start, now, duration;

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
