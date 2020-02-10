package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Touch Sensor Test", group = "!tele")
public class TouchSensorTelemetry  extends LinearOpMode {

    TouchSensor sensor;
    @Override
    public void runOpMode() throws InterruptedException {
        sensor = hardwareMap.get(TouchSensor.class, "ts");

        while (opModeIsActive()) {
            telemetry.addData("Touch Sensor Push: ", sensor.getValue());
            telemetry.addData("Touch Sensor State: ", sensor.isPressed());
            telemetry.update();
        }
    }
}
