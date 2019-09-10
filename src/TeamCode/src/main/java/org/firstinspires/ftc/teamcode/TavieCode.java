package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Tavie Kittredge November 2018
 * Single Controller OmniWheel Drivecode
 * left controller uses y axis for forward/backward and x for right/left
 * right controller uses x axis for rotations around center (requires motors to be at about same power)
 * Configure with leftFront_drive, rightFront_drive, leftBack_drive, rightBack_drive
 */

@TeleOp(name = "Omni Drive", group = "Linear Opmode")
@Disabled
public class TavieCode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;
    //private DcMotor hangMotor;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        // doesn't run all the time - waits for game to start
        waitForStart();
        runtime.reset();

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        //hangMotor.setPower(0);

        // run loop
        while (opModeIsActive()) {

            //what are these variables?
            double upDown = gamepad1.left_stick_y;
            double rightLeft = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;
            double hanger = gamepad1.right_stick_y;

            //hangMotor.setPower(hanger / 2);

            //dead zones in controller ?
            if (upDown < .1 && upDown > -.1) {
                upDown = 0;
            }
            if (rightLeft < .1 && rightLeft > -.1) {
                rightLeft = 0;
            }
            if (rotate < .1 && rotate > -.1) {
                rotate = 0;
            }


            //this is the chunk I need to understand to understand how to code omni directional
            if (upDown == 0 && rightLeft == 0 && rotate == 0) {
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
                rightFrontDrive.setPower(0);
                rightBackDrive.setPower(0);
            } else if (rotate * rotate > .1) { //right controller
                if (rotate > .5) { //clockwise
                    leftFrontDrive.setPower(.2);
                    leftBackDrive.setPower(.2);
                    rightFrontDrive.setPower(-.2);
                    rightBackDrive.setPower(-.2);
                } else if (rotate < -.5) { //counter-clockwise
                    leftFrontDrive.setPower(-.2);
                    leftBackDrive.setPower(-.2);
                    rightFrontDrive.setPower(.2);
                    rightBackDrive.setPower(.2);
                }
            } else if (upDown * upDown > rightLeft * rightLeft) { //left controller
                if (upDown > .5) {//forward
                    leftFrontDrive.setPower(.3);
                    leftBackDrive.setPower(.3);
                    rightFrontDrive.setPower(.3);
                    rightBackDrive.setPower(.3);
                } else if (upDown < -.5) {//backward
                    leftFrontDrive.setPower(-.3);
                    leftBackDrive.setPower(-.3);
                    rightFrontDrive.setPower(-.3);
                    rightBackDrive.setPower(-.3);
                }

            } else if (upDown * upDown < rightLeft * rightLeft) {
                if (rightLeft > .5) {//right (reversed left side)
                    leftFrontDrive.setPower(.3);
                    leftBackDrive.setPower(-.3);
                    rightFrontDrive.setPower(-.3);
                    rightBackDrive.setPower(.3);
                } else if (rightLeft < -.5) {//left (reversed left side)
                    leftFrontDrive.setPower(-.3);
                    leftBackDrive.setPower(.3);
                    rightFrontDrive.setPower(.3);
                    rightBackDrive.setPower(-.3);
                }

            }

            // Telemetry - drive and front/right strength
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Controller", "forward (%.2f), right (%.2f)", upDown, rightLeft);
            telemetry.update();
        }
    }

    private void InitializeHardware(){
        // Initialize the hardware variables. Note that the strings used here as parameters
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack_drive");
        //hangMotor = hardwareMap.get(DcMotor.class, "hang_motor");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD); //all motors should be positive going forward - double check
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        //hangMotor.setDirection(DcMotor.Direction.FORWARD);

        //set to run without encoder
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //this should have the setPower work with speed rather than power
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //this code should be the first place to debug
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //why/where setMaxSpeed?
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //hangMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
