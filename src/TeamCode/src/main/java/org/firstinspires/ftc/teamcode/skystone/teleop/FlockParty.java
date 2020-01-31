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
@TeleOp(name = "Flock Party", group = "!tele")
public class FlockParty extends LinearOpMode {


    // declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor liftMotor = null;

    // run OpMode
    @Override
    public void runOpMode() {

        // initialization telemetry
        telemetry.addData("Status", "Initializing");
        telemetry.update();


        // declaration of variables
        boolean rightBumper;
        boolean leftBumper;
        boolean rightTrigger = false;
        boolean leftTrigger = false;
        boolean foundationOpen = true;
        boolean clawOpen = false;
        // float initialFrontRightPower, initialBackRightPower, initialFrontLeftPower, initialBackLeftPower;

        double targetBlock = 0;
        double upCoolDown = -10;
        double downCoolDown = -10;


        // initialization finished
        telemetry.addData("Status", "Initialization finished");
        telemetry.update();


        // initialization of hardware variables - REMEMBER TO CHANGE CONFIGURATION ON PHONES AS WELL
        leftFrontDrive = hardwareMap.get(DcMotor.class, "fl");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "fr");
        leftBackDrive = hardwareMap.get(DcMotor.class, "bl");
        rightBackDrive = hardwareMap.get(DcMotor.class, "br");

        liftMotor = hardwareMap.get(DcMotor.class, "av");



        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // initial position
        setupLift();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // TODO: cleanup lift code into a separate method

            // MOTION FUNCTIONS
            double speedMult = 0.675;


            // triggers for better motion control
            if (gamepad1.left_stick_button) speedMult = 0.25;

            sendPowerToMotor(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, speedMult);


             // claw functions


            // lift motor telemetry
            //  telemetry.addData("Status", "Run Time: " + runtime.toString());

            // lift motor functions
            rightBumper = gamepad1.right_bumper;
            leftBumper = gamepad1.left_bumper;

            if (rightBumper && getRuntime() > upCoolDown) {
                liftMotor.setPower(0.5);
                targetBlock++;
                upCoolDown = getRuntime() + 0.2; //cooldown of .2s
            }
            if (leftBumper && getRuntime() > downCoolDown) {
                liftMotor.setPower(-0.5);
                targetBlock--;
                downCoolDown = getRuntime() + 0.2; //cooldown of .2s
            }
            targetBlock = Math.max(targetBlock, 0);

            // cap the lift motor to stop crashing
            /** NOTE: allows targetBlock to increase to one more than the max value
             * in the case that it is above the max value it will round to the max value
             * the final increment will simply set it to the max height (probably less than oneBlock)
             */
            if ((targetBlock +

                                                                                                                                                                                     1) * FTCConstants.ONE_BLOCK < -5500)
                targetBlock--  ;

            // raiseBlock telemetry
            telemetry.addData("Raiseblock", "going to: " + targetBlock);
            controlLiftMotor(targetBlock);

            telemetry.addData("Lift Motor", "raisePos: " + liftMotor.getCurrentPosition());
            telemetry.update();
        }
    }

    // movement code
    void sendPowerToMotor(double x, double y, double r, double speedMult) {
        // sensitivity
        if (Math.abs(x) < 0.025) {
            x = 0;
        }

        if (Math.abs(y) < 0.025) {
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
            if (frontLeftPower > leftFrontDrive.getPower()) {
                for (double i = leftFrontDrive.getPower(); i < frontLeftPower; i += 0.20) {
                    leftFrontDrive.setPower(i);
                }
            } else {
                for (double i = leftFrontDrive.getPower(); i > frontLeftPower; i -= 0.20) {
                    leftFrontDrive.setPower(i);
                }
            }
            leftFrontDrive.setPower(frontLeftPower);
        }


        if (leftBackDrive.getPower() != backLeftPower) {
            if (backLeftPower > leftBackDrive.getPower()) {
                for (double i = leftBackDrive.getPower(); i < backLeftPower; i += 0.20) {
                    leftBackDrive.setPower(i);
                }
            } else {
                for (double i = leftBackDrive.getPower(); i > backLeftPower; i -= 0.20) {
                    leftBackDrive.setPower(i);
                }
            }
            leftBackDrive.setPower(backLeftPower);
        }

        if (rightFrontDrive.getPower() != frontRightPower) {
            if (frontRightPower > rightFrontDrive.getPower()) {
                for (double i = rightFrontDrive.getPower(); i < frontRightPower; i += 0.20) {
                    rightFrontDrive.setPower(i);
                }
            } else {
                for (double i = rightFrontDrive.getPower(); i > frontRightPower; i -= 0.20) {
                    rightFrontDrive.setPower(i);
                }
            }
            rightFrontDrive.setPower(frontRightPower);
        }

        if (rightBackDrive.getPower() != backRightPower) {
            if (backRightPower > rightBackDrive.getPower()) {
                for (double i = rightBackDrive.getPower(); i < backRightPower; i += 0.20) {
                    rightBackDrive.setPower(i);
                }
            } else {
                for (double i = rightBackDrive.getPower(); i > backRightPower; i -= 0.20) {
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

        liftMotor.setTargetPosition(targetPos);


    }

    // lift initialization
    void setupLift() {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(1);
    }

}
