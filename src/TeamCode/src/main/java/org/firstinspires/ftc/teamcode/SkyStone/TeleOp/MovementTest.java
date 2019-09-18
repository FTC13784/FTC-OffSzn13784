/*

TODO: servo initialize to 0, x --> 1, y --> 0

*/

// team package
package org.firstinspires.ftc.teamcode.SkyStone.TeleOp;

// import statement

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkyStone.SkyStoneUtils;

// set teleop mode
@TeleOp(name = "Motor Test", group = "Linear Opmode")

// main

/*

 */
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
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();


            if (Math.abs(gamepad1.left_stick_y) < 0.1) {
                gamepad1.left_stick_y = 0;
            }
            if (Math.abs(gamepad1.right_stick_y) < 0.1) {
                gamepad1.right_stick_y = 0;
            }

            // setPower(allDrive, -gamepad1.left_stick_x);

            // left stick controls direction
            // right stick X controls rotation

            SkyStoneUtils.handleGamepadControls(gamepad1, leftBack, leftFront, rightBack, rightFront);


        }
    }
}
