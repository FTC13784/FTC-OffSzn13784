/**
 * Contributors:
 *
 * @author Edwardidk, FavouriteDragon
 * @contributors RobertM3
 * <p>
 * Configuration:
 * <p>
 * left front motor -> lf
 * right front motor -> rf
 * left back motor -> lb
 * right back motor -> rb
 * <p>
 * lifter motor -> raise
 * extender motor -> extend
 * <p>
 * foundation front motor -> ff
 * foundation back motor -> fb
 * <p>
 * left claw servo -> cl
 * right claw servo -> cr
 */

/**
 * Configuration:
 * <p>
 * left front motor -> lf
 * right front motor -> rf
 * left back motor -> lb
 * right back motor -> rb
 * <p>
 * lifter motor -> raise
 * extender motor -> extend
 * <p>
 * foundation front motor -> ff
 * foundation back motor -> fb
 * <p>
 * left claw servo -> cl
 * right claw servo -> cr
 */

// TeleOp package
package org.firstinspires.ftc.teamcode.skystone.teleop;

// import packages

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FTCConstants;


// TeleOp declaration
@TeleOp(name = "Good Teleop", group = "!tele")

// disable telemetry
//@Disabled

// GoodTeleop class
public class GoodTeleop extends LinearOpMode {
    // TODO: Map all non-movement code to gamepad2, for a second auxiliary driver.

    // declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor liftMotor = null;
    private DcMotor extensionMotor = null;
    private Servo foundationFront = null;
    private Servo foundationBack = null;
    private Servo leftClawServo = null;
    private Servo rightClawServo = null;

    // run OpMode
    @Override
    public void runOpMode() {

        // initialization telemetry
        telemetry.addData("Status", "Initializing");
        telemetry.update();


        // declaration of variables
        boolean rightBumper;
        boolean leftBumper;
        // float initialFrontRightPower, initialBackRightPower, initialFrontLeftPower, initialBackLeftPower;

        double targetBlock = 0;
        double upCoolDown = -10;
        double downCoolDown = -10;


        // initialization finished
        telemetry.addData("Status", "Initialization finished");
        telemetry.update();


        // initialization of hardware variables - REMEMBER TO CHANGE CONFIGURATION ON PHONES AS WELL
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");

        liftMotor = hardwareMap.get(DcMotor.class, "raise");
        extensionMotor = hardwareMap.get(DcMotor.class, "extend");
        foundationFront = hardwareMap.get(Servo.class, "ff");
        foundationBack = hardwareMap.get(Servo.class, "fb");

        rightClawServo = hardwareMap.get(Servo.class, "cr");
        leftClawServo = hardwareMap.get(Servo.class, "cl");


        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // initial position
        setupLift();
        closeClaw();
        foundationFront.setPosition(0);
        foundationBack.setPosition(1);
        // openFoundation();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // TODO: cleanup lift code into a separate method

            // MOTION FUNCTIONS
            double speedMult = 1;


            // triggers for better motion control
            if (gamepad1.right_trigger > 0.5 || gamepad1.left_trigger > 0.5)
                speedMult = 0.5;

            // telemetry.addData("Right Trigger", gamepad1.right_trigger);
            // telemetry.addData("Left Trigger", gamepad1.left_trigger);
            // telemetry.update();


            // movement function
            sendPowerToMotor(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, speedMult);


            // AUXILIARY FUNCTIONS
            // TODO: Add target positions for blocks.
            extensionMotor.setPower(gamepad2.dpad_up ? 0.5 : gamepad2.dpad_down ? -0.5 : 0);


            // claw functions
            if (gamepad2.x) closeClaw();
            if (gamepad2.y) openClaw();


            // foundation functions
            if (gamepad2.a)
                openFoundation();
            else if (gamepad2.b)
                closeFoundation();


            // lift motor telemetry
            //  telemetry.addData("Status", "Run Time: " + runtime.toString());

            // lift motor functions
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

            // cap the lift motor to stop crashing
            /** NOTE: allows targetBlock to increase to one more than the max value
             * in the case that it is above the max value it will round to the max value
             * the final increment will simply set it to the max height (probably less than oneBlock)
             */
            if ((targetBlock - 1) * FTCConstants.ONE_BLOCK < -5500)
                targetBlock--;

            // raiseBlock telemetry
            telemetry.addData("Raiseblock", "going to: " + targetBlock);
            controlLiftMotor(targetBlock);

            // other telemetry
            // telemetry.addData("foundationFront:", foundationFront.getDirection());
            // telemetry.addData("foundationBack:", foundationBack.getPosition());
            // telemetry.addData("x:", gamepad1.left_stick_x);
            // telemetry.addData("y:", gamepad1.left_stick_y);
            // telemetry.addData("r:", gamepad1.right_stick_x);
            // telemetry.addData("l:", gamepad2.right_stick_y);
            // telemetry.addData("e:", gamepad2.left_stick_y);
            telemetry.addData("Lift Motor", "raisePos: " + liftMotor.getCurrentPosition());
            telemetry.addData("Extension Motor:", extensionMotor.getCurrentPosition());
            telemetry.update();
        }
    }

    // movement code
    void sendPowerToMotor(double x, double y, double r, double speedMult) {
        // sensitivity
        if (Math.abs(x) < 0.1) {
            x = 0;
        }

        if (Math.abs(y) < 0.1) {
            y = 0;
        }


        // motor speed variables
        double backLeftPower = x + y - r;
        double backRightPower = -y + x - r;
        double frontLeftPower = y - x - r;
        double frontRightPower = -y - x - r;

        // clip values from -1 to 1
        backLeftPower = Range.clip(backLeftPower, -1F, 1F);
        backRightPower = Range.clip(backRightPower, -1F, 1F);
        frontLeftPower = Range.clip(frontLeftPower, -1F, 1F);
        frontRightPower = Range.clip(frontRightPower, -1F, 1F);

        // triggers for speeding up and slowing down
        backLeftPower *= speedMult;
        backRightPower *= speedMult;
        frontLeftPower *= speedMult;
        frontRightPower *= speedMult;

        // acceleration
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


    // lift motor code
    void controlLiftMotor(double targetBlock) {
        int targetPos = (int) Math.round(targetBlock * FTCConstants.ONE_BLOCK);
        double positionPlus = liftMotor.getCurrentPosition() + 20;
        double positionMinus = liftMotor.getCurrentPosition() - 20;

        if (targetPos < -5500) {
            telemetry.addData("Warning", "targetPos value is too low!");
            targetPos = -5500;
        }
        if (targetPos > 0) {
            telemetry.addData("Warning", "targetPos value is too high!");
            targetPos = 0;
        }

        telemetry.addData("Raisepos", "going to: " + targetPos);

        //Just makes the lift motor not go to the target position if it's close enough, so it stops
        //trying to run when it's one tick off.
        if (positionPlus > targetPos + 20 && positionMinus < targetPos + 20 && (int) (liftMotor.getCurrentPosition() / FTCConstants.ONE_BLOCK) != targetBlock) {
            liftMotor.setTargetPosition(targetPos);
        }

    }


    // clawcode
    void openClaw() {
        leftClawServo.setPosition(0.3);
        rightClawServo.setPosition(1 - leftClawServo.getPosition());
    }

    void closeClaw() {
        leftClawServo.setPosition(1);
        rightClawServo.setPosition(1 - leftClawServo.getPosition());
    }

    // lift initialization
    void setupLift() {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(1);
    }

    // foundation mover code
    void openFoundation() {
        //Originally 1
        foundationFront.setPosition(0);
        foundationBack.setPosition(foundationFront.getPosition());
    }

    void closeFoundation() {
        //Originally 0.5
        foundationFront.setPosition(0.8);
        foundationBack.setPosition(foundationFront.getPosition());
    }
}
