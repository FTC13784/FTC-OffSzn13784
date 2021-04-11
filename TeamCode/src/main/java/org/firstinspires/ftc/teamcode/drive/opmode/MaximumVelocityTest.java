package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(group = "autonomous")
public class MaximumVelocityTest extends LinearOpMode {

    DcMotorEx motor;
    double currentVel, maxVel = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "rightFront");
        waitForStart();

        if (isStopRequested())
            return;

        currentVel = motor.getVelocity();

        if (currentVel > maxVel) {
            maxVel = currentVel;
        }

        telemetry.addData("current velocity", currentVel);
        telemetry.addData("maximum velocity", maxVel);
        telemetry.update();
    }
}
