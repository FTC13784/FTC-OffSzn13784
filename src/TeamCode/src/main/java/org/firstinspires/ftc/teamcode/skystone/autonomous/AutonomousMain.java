package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutonomousMain extends LinearOpMode {

    //Will be used for property files
    @Override
    public void runOpMode() throws InterruptedException {

    }

    public EncoderFunLight getBot() {
        return new EncoderFunLight(this);
    }
}
