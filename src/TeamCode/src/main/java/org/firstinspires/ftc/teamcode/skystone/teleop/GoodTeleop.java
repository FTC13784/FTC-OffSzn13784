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
import com.qualcomm.robotcore.hardware.CRServo;
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

@TeleOp(name = "Good Teleop", group = "Linear Opmode")
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

    private CRServo foundationFront = null;
    private Servo foundationBack = null;

    private Servo leftClawServo = null;
    private Servo rightClawServo = null;
    float oneBlock = -1900/2;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        boolean rightBumper;
        boolean leftBumper;
        //float initialFrontRightPower, initialBackRightPower, initialFrontLeftPower, initialBackLeftPower;

        double targetBlock = 0;
        double upCoolDown = -10;
        double downCoolDown = -10;


        telemetry.addData("Status", "Initialized");


        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");

        liftMotor = hardwareMap.get(DcMotor.class, "raise");
        extensionMotor = hardwareMap.get(DcMotor.class, "extend");
        foundationFront = hardwareMap.get(CRServo.class, "ff");
        foundationBack = hardwareMap.get(Servo.class, "fb");

        rightClawServo = hardwareMap.get(Servo.class, "cr");
        leftClawServo = hardwareMap.get(Servo.class, "cl");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        setupLift();
        openClaw();
        foundationFront.setPower(0);
        foundationBack.setPosition(1);
       // openFoundation();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //TODO: Cleanup lift code into a separate method

            //Motion
            double speedMult = 1;

            if (gamepad1.right_trigger > 0.5 || gamepad1.left_trigger > 0.5)
                speedMult = 0.5;

            telemetry.addData("Right Trigger", gamepad1.right_trigger);
            telemetry.addData("Left Trigger", gamepad1.left_trigger);
            telemetry.update();

            sendPowerToMotor(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, speedMult);

            //Auxiliary
            //TODO: Add target positions for blocks.

            extensionMotor.setPower(gamepad2.dpad_up ? 0.5 : gamepad2.dpad_down ? -0.5 : 0);

            if (gamepad2.x) closeClaw();
            if (gamepad2.y) openClaw();

            //if (gamepad2.a) openFoundation();
            //if (gamepad2.b) closeFoundation();

            telemetry.addData("Status", "Run Time: " + runtime.toString());

            float raisePos = liftMotor.getCurrentPosition();
            telemetry.addData("ENCODER(r)", "raisePos: " + raisePos);

            //Lift motor stuff.
            rightBumper = gamepad2.right_bumper;
            leftBumper = gamepad2.left_bumper;

            if (rightBumper && getRuntime() > upCoolDown) {
                targetBlock++;
                upCoolDown = getRuntime() + 0.2; //cooldown of .2s
            }
            if (leftBumper && getRuntime() > downCoolDown) {
                targetBlock--;
                downCoolDown = getRuntime() + 0.2; //cooldown of .2s
            }
            targetBlock = Math.max(targetBlock, 0);

            //Cap the lift motor to stop crashing
            //NOTE! this allows targetBlock to increase to one more than the max value.
            //in the case that it is above the max value it will round to the max value.
            //the final increment will simply set it to the max height (probably less than oneBlock
            if ((targetBlock-1) * oneBlock < -5500)
                targetBlock--;

            telemetry.addData("Raiseblock", "going to: " + targetBlock);
            controlLiftMotor(targetBlock);

            telemetry.update();
            telemetry.addData("foundationFront:", foundationFront.getDirection());
            telemetry.addData("foundationBack:", foundationBack.getPosition());
           // telemetry.addData("x:", gamepad1.left_stick_x);
            //telemetry.addData("y:", gamepad1.left_stick_y);
           // telemetry.addData("r:", gamepad1.right_stick_x);
           // telemetry.addData("l:", gamepad2.right_stick_y);
           // telemetry.addData("e:", gamepad2.left_stick_y);
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

        double backLeftPower = x + y - r;
        double backRightPower = -y + x - r;
        double frontLeftPower = y - x - r;
        double frontRightPower = -y - x - r;

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
                for (double i = leftFrontDrive.getPower(); i < frontLeftPower; i += 0.25) {
                    leftFrontDrive.setPower(i);
                }
            } else {
                for (double i = leftFrontDrive.getPower(); i > frontLeftPower; i -= 0.25) {
                    leftFrontDrive.setPower(i);
                }
            }
            leftFrontDrive.setPower(frontLeftPower);
        }
        if (leftBackDrive.getPower() != backLeftPower) {
            if (backLeftPower > leftBackDrive.getPower()) {
                for (double i = leftBackDrive.getPower(); i < backLeftPower; i += 0.25) {
                    leftBackDrive.setPower(i);
                }
            } else {
                for (double i = leftBackDrive.getPower(); i > backLeftPower; i -= 0.25) {
                    leftBackDrive.setPower(i);
                }
            }
            leftBackDrive.setPower(backLeftPower);
        }
        if (rightFrontDrive.getPower() != frontRightPower) {
            if (frontRightPower > rightFrontDrive.getPower()) {
                for (double i = rightFrontDrive.getPower(); i < frontRightPower; i += 0.25) {
                    rightFrontDrive.setPower(i);
                }
            } else {
                for (double i = rightFrontDrive.getPower(); i > frontRightPower; i -= 0.25) {
                    rightFrontDrive.setPower(i);
                }
            }
            rightFrontDrive.setPower(frontRightPower);
        }
        if (rightBackDrive.getPower() != backRightPower) {
            if (backRightPower > rightBackDrive.getPower()) {
                for (double i = rightBackDrive.getPower(); i < backRightPower; i += 0.25) {
                    rightBackDrive.setPower(i);
                }
            } else {
                for (double i = rightBackDrive.getPower(); i > backRightPower; i -= 0.25) {
                    rightBackDrive.setPower(i);
                }
            }
            rightBackDrive.setPower(backRightPower);
        }
    }


    void controlLiftMotor(double targetBlock) {
        int targetPos = (int) Math.round(targetBlock * oneBlock);
        if (targetPos < -5500) {
            telemetry.addData("Warning", "targetPos value is too low!");
            targetPos = -5500;
        }
        if (targetPos > 0) {
            telemetry.addData("Warning", "targetPos value is too high!");
            targetPos = 0;
        }

        telemetry.addData("Raisepos", "going to: " + targetPos);
        liftMotor.setTargetPosition(targetPos);
    }

    void openClaw() {
        leftClawServo.setPosition(1);
        rightClawServo.setPosition(1 - leftClawServo.getPosition());
    }

    void closeClaw() {
        leftClawServo.setPosition(.2);
        rightClawServo.setPosition(1 - leftClawServo.getPosition());
    }

    void setupLift() {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(1);
    }

    void openFoundation() {
       //foundationFront.setPosition(0.1);
       // foundationBack.setPosition(0.4);
    }

    void closeFoundation() {
        //foundationFront.setPosition(0);
        //foundationBack.setPosition(0);

    }
}
