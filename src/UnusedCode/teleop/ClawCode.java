package org.firstinspires.ftc.teamcode.skystone.teleop.unused;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class ClawCode extends LinearOpMode {
    // run until the end of the match (driver presses STOP)
    private Servo servoClaw;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Init");
        servoClaw = hardwareMap.servo.get("servoClaw");
        Thread.sleep(2000);

        while (opModeIsActive()) {
            waitForStart();
            if (gamepad1.y) {
                telemetry.addData("Status", "Pressed");
                servoClaw.setPosition(1);
                telemetry.addData("Status", "OK");
            } else {
                telemetry.addData("Status", "Not");
                servoClaw.setPosition(0);
            }
            telemetry.update();
            telemetry.addData("Servo Position", servoClaw.getPosition());
        }

    }
}//
