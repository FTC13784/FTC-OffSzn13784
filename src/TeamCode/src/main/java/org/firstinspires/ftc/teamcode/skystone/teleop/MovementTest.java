/*

TODO: servo initialize to 0, x --> 1, y --> 0

*/

// team package
package org.firstinspires.ftc.teamcode.skystone.teleop;

// import statement

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.skystone.SkyStoneUtils;

// set teleop mode
@TeleOp(name = "Motor Test", group = "Linear Opmode")

// main
/**
 * @author John (FavouriteDragon)
 * Just a test class for controlling the robot. Copied over from TeleopChad, but cleaned up.
 */

//TODO: Actually test this. The robot is currently out of commission.
public class MovementTest extends LinearOpMode {

    // start timer
    private ElapsedTime runtime = new ElapsedTime();

    // initialize motor array as well as variables
    DcMotor[] allDrive = new DcMotor[4];

    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;

    // opmode
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        SkyStoneUtils.initializeTeleOpHardware(allDrive, leftBack, leftFront, rightBack, rightFront, hardwareMap);

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Debugging
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            SkyStoneUtils.handleGamepadControls(gamepad1, leftBack, leftFront, rightBack, rightFront);


        }
    }
}
