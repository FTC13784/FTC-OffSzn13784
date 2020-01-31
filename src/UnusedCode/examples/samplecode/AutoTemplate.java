package org.firstinspires.ftc.teamcode.examples.samplecode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto Template", group = "Linear Opmode")
@Disabled

public class AutoTemplate extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //define variables (like motors) here

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initialize hardware here

        waitForStart();
        runtime.reset();

        //put robot code in here
    }
}