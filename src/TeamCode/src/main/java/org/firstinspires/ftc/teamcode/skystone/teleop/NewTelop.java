/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal struct ure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

/*
Config:

left front motor -> lf
right front motor -> rf
left back motor -> lb
right back motor -> rb

lifter motor -> raise
extender motor -> extend
 */

@TeleOp(name = "Test Teleop", group = "Linear Opmode")
//@Disabled
public class NewTelop extends LinearOpMode {

    //TODO: Map all non-movement code to gamepad2, for a second auxiliary driver.

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    private DcMotor liftMotor = null;
    private DcMotor extensionMotor = null;

    private Servo frontClawServo = null;
    private Servo backClawServo = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");

        liftMotor = hardwareMap.get(DcMotor.class, "raise");
        extensionMotor = hardwareMap.get(DcMotor.class, "extend");

        frontClawServo = hardwareMap.get(Servo.class, "cf");
        backClawServo = hardwareMap.get(Servo.class, "bf");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        openClaw();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Motion
            double speedMult = 1;

            if (gamepad1.right_trigger < 0.5)
                speedMult = 1.5;
            else if (gamepad1.left_trigger < 0.5)
                speedMult = 0.5;

            sendPowerToMotor(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, speedMult);

            //Auxiliary
            //TODO: Add target positions for blocks.
            if (gamepad2.left_bumper && liftMotor.getCurrentPosition() > 0)
                liftMotor.setPower(-0.5);
            else if (gamepad2.right_bumper && liftMotor.getCurrentPosition() < 3)
                liftMotor.setPower(0.5);
            else liftMotor.setPower(0);

            extensionMotor.setPower(gamepad2.dpad_up ? 0.5 : gamepad2.dpad_down ? -0.5 : 0);

            if (gamepad2.x) closeClaw();
            if (gamepad2.y) openClaw();

            telemetry.addData("x:", gamepad1.left_stick_x);
            telemetry.addData("y:", gamepad1.left_stick_y);
            telemetry.addData("r:", gamepad1.right_stick_x);
            telemetry.addData("l:", gamepad2.right_stick_y);
            telemetry.addData("e:", gamepad2.left_stick_y);
            telemetry.update();
        }
    }

    void sendPowerToMotor(double x, double y, double r, double speedMult) {
        //Reduces sensitivity

        if (Math.abs(x) < 0.1) {
            x = 0;
        }
        if (Math.abs(y) < 0.1) {
            y = 0;
        }

        double backLeftPower = -x + y - r;
        double backRightPower = x - y - r;
        double frontLeftPower = x + y - r;
        double frontRightPower = -x - y - r;

        //Clipping.
        backLeftPower = Range.clip(backLeftPower, -1F, 1F);
        backRightPower = Range.clip(backRightPower, -1F, 1F);
        frontLeftPower = Range.clip(frontLeftPower, -1F, 1F);
        frontRightPower = Range.clip(frontRightPower, -1F, 1F);

        //Triggers for speeding up and slowing down.
        backLeftPower *= speedMult;
        backRightPower *= speedMult;
        frontLeftPower *= speedMult;
        frontRightPower *= speedMult;

        //Acceleration.

        if (leftFrontDrive.getPower() != frontLeftPower) {
            if (backRightPower > leftFrontDrive.getPower()) {
                for (double i = leftFrontDrive.getPower(); i < frontLeftPower; i += 0.3) {
                    leftFrontDrive.setPower(i);
                }
            } else {
                for (double i = leftFrontDrive.getPower(); i > frontLeftPower; i -= 0.3) {
                    leftFrontDrive.setPower(i);
                }
            }
            leftFrontDrive.setPower(frontLeftPower);
        }
        if (leftBackDrive.getPower() != backLeftPower) {
            if (backLeftPower > leftBackDrive.getPower()) {
                for (double i = leftBackDrive.getPower(); i < backLeftPower; i += 0.3) {
                    leftBackDrive.setPower(i);
                }
            } else {
                for (double i = leftBackDrive.getPower(); i > backLeftPower; i -= 0.3) {
                    leftBackDrive.setPower(i);
                }
            }
            leftBackDrive.setPower(backLeftPower);
        }
        if (rightFrontDrive.getPower() != frontRightPower) {
            if (frontRightPower > rightFrontDrive.getPower()) {
                for (double i = rightFrontDrive.getPower(); i < frontRightPower; i += 0.3) {
                    rightFrontDrive.setPower(i);
                }
            } else {
                for (double i = rightFrontDrive.getPower(); i > frontRightPower; i -= 0.3) {
                    rightFrontDrive.setPower(i);
                }
            }
            rightFrontDrive.setPower(frontRightPower);
        }
        if (rightBackDrive.getPower() != backRightPower) {
            if (backRightPower > rightBackDrive.getPower()) {
                for (double i = rightBackDrive.getPower(); i < backRightPower; i += 0.3) {
                    rightBackDrive.setPower(i);
                }
            } else {
                for (double i = rightBackDrive.getPower(); i > backRightPower; i -= 0.3) {
                    rightBackDrive.setPower(i);
                }
            }
            rightBackDrive.setPower(backRightPower);
        }
    }

    void openClaw() {
        frontClawServo.setPosition(1);
        backClawServo.setPosition(0);
    }

    void closeClaw() {
        frontClawServo.setPosition(.2);
        backClawServo.setPosition(.8);
    }
}
