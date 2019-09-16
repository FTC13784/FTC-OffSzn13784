package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This should maybe make the servo that controls the grabber work
 * Uses the y button to move the grabber down, defaults to a raised state
 */

@TeleOp
public class ServoCode extends LinearOpMode {
    private Servo servoLeft, servoRight;
    // run until the end of the match (driver presses STOP)

    @Override
    public void runOpMode() throws InterruptedException {
        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
        servoRight = hardwareMap.get(Servo.class, "servoRight");

        while (opModeIsActive()) {
            // check to see if we need to move the servo.
            servoLeft.setPosition(0);
            if (gamepad1.y) {
                // move to 0 degrees.
                servoLeft.setPosition(0.5);
                servoRight.setPosition(0);
            } else {
                // move to 90 degrees.
                servoLeft.setPosition(0);
                servoRight.setPosition(0.5);
            }
            telemetry.addData("Servo Position", servoLeft.getPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }

    }
}//
