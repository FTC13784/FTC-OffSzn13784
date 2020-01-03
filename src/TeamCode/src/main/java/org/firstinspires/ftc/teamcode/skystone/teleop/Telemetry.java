package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "FoundationTele", group = "Telemetry")
public class Telemetry extends LinearOpMode {
    private Servo foundationBack = null;

    @Override
    public void runOpMode() {
        waitForStart();

        foundationBack = hardwareMap.get(Servo.class, "fb");

        while (opModeIsActive()) {
            telemetry.addData("fb pos", foundationBack.getPosition());
            telemetry.update();
        }
    }
}
