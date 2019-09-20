package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This should maybe make the servo that controls the grabber work
 * Uses the y button to move the grabber down, defaults to a raised state
 */

@TeleOp
public class ClawCode extends LinearOpMode {
    private Servo servoClaw;
    // run until the end of the match (driver presses STOP)

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Init");
        Thread.sleep(10000);
        servoClaw = hardwareMap.get(Servo.class, "servoClaw");

        while (opModeIsActive()) {
            // check to see if we need to move the servo.
            servoClaw.setPosition(0);
            if (gamepad1.y) {
                // move to 90 degrees.
                servoClaw.setPosition(1);
            } else {
                // move to 0 degrees.
                servoClaw.setPosition(0);
            }
            telemetry.addData("Servo Position", servoClaw.getPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }

    }
}//
