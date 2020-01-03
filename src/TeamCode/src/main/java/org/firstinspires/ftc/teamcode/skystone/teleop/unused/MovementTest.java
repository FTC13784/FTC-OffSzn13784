/*

TODO: servo initialize to 0, x --> 1, y --> 0

*/

// team package
package org.firstinspires.ftc.teamcode.skystone.teleop.unused;

// import statement

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

// set teleop mode
@TeleOp(name = "Motor Test", group = "Linear Opmode")

// main
/**
 * @author John (FavouriteDragon) - or so he thinks
 * Just a test class for controlling the robot. Copied over from TeleopChad, but cleaned up.
 */

//TODO: Actually test this. The robot is currently out of commission.
public class MovementTest extends LinearOpMode {

    // start timer
    private ElapsedTime runtime = new ElapsedTime();

    // initialize motor array as well as variables
    DcMotor[] allDrive = new DcMotor[4];

    DcMotor leftFront, leftBack, rightFront, rightBack;
    Servo servoRelocL, servoRelocR;

    // opmode
    @Override
    public void runOpMode() throws InterruptedException {
        float[] powerTelemetry = new float[4];
        float servoRelocRPos = 0;
        float servoRelocLPos = 1;

        telemetry.addData("Status", "Initialized");
        telemetry.addData("FLP", Float.toString(powerTelemetry[0]));
        telemetry.addData("FRP", Float.toString(powerTelemetry[1]));
        telemetry.addData("BLP", Float.toString(powerTelemetry[2]));
        telemetry.addData("BRP", Float.toString(powerTelemetry[3]));
        telemetry.addData("LServo", Float.toString(servoRelocLPos));
        telemetry.addData("RServo", Float.toString(servoRelocRPos));
        telemetry.update();

        SkyStoneUtils.initializeTeleOpHardware(allDrive, leftBack, leftFront, rightBack, rightFront, servoRelocL, servoRelocR, hardwareMap);

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Debugging
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            powerTelemetry = SkyStoneUtils.handleGamepadControls(gamepad1, leftBack, leftFront, rightBack, rightFront, servoRelocL, servoRelocR);
            telemetry.addData("FLP", Float.toString(powerTelemetry[0]));
            telemetry.addData("FRP", Float.toString(powerTelemetry[1]));
            telemetry.addData("BLP", Float.toString(powerTelemetry[2]));
            telemetry.addData("BRP", Float.toString(powerTelemetry[3]));
            telemetry.addData("LServo", Float.toString(servoRelocLPos));
            telemetry.addData("RServo", Float.toString(servoRelocRPos));

            telemetry.update();
        }
    }
}
