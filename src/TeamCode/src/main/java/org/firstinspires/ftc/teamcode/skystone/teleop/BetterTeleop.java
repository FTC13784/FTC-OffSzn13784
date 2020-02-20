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
@TeleOp(name = "Better Teleop", group = "!tele")

// disable telemetry
//@Disabled

// GoodTeleop class
public class BetterTeleop extends LinearOpMode {


    // declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    private DcMotor leftIntakeMotor = null, rightIntakeMotor = null;
    private DcMotor intakeMotorLift = null;
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



        boolean foundationOpen = false;
        boolean a = false;



        // initialization finished
        telemetry.addData("Status", "Initialization finished");
        telemetry.update();


        // initialization of hardware variables - REMEMBER TO CHANGE CONFIGURATION ON PHONES AS WELL
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");


        foundationFront = hardwareMap.get(Servo.class, "ff");
        foundationBack = hardwareMap.get(Servo.class, "fb");

        leftIntakeMotor = hardwareMap.get(DcMotor.class, "li");
        leftIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeMotor = hardwareMap.get(DcMotor.class, "ri");
        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotorLift = hardwareMap.get(DcMotor.class, "il");

        //rightClawServo = hardwareMap.get(Servo.class, "cr");
        //leftClawServo = hardwareMap.get(Servo.class, "cl");


        // wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // initial position
        setupLift();
        closeFoundation();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // TODO: cleanup lift code into a separate method

            // MOTION FUNCTIONS
            double speedMult = 0.75;


            // triggers for better motion control
            if (gamepad1.left_stick_button) speedMult = 0.25;

            sendPowerToMotor(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, speedMult);


            // Intake in and out like the burger joint
            if (gamepad1.right_trigger>.5)
                powerIntake();
            else if (gamepad1.left_trigger>.5)
                {powerOuttake();
                telemetry.addLine("Outtake");}
            else stopIntake();



            //move intake up and down
            if (gamepad1.y)
                liftIntake();
            if (gamepad1.x)
                lowerIntake();


            //toggle for foundation
            if (gamepad1.a&&!a) {
                if (foundationOpen) {
                    foundationOpen = false;
                    closeFoundation();
                } else {
                    foundationOpen = true;
                    openFoundation();
                }
            }
            a=gamepad1.a;



            telemetry.addLine("IntakeHeight"+intakeMotorLift.getCurrentPosition());
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





    // lift initialization
    void setupLift() {
        intakeMotorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //This is temmportary for debugging
        intakeMotorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //intakeMotorLift.setPower(1);
    }

    // foundation mover code
    void openFoundation() {
        //Originally 1
        foundationFront.setPosition(0.3);
        foundationBack.setPosition(1 - foundationFront.getPosition());
    }

    void closeFoundation() {
        //Originally 0.5
        foundationFront.setPosition(1);
        foundationBack.setPosition(1 - foundationFront.getPosition());
    }

    void powerIntake() {
        leftIntakeMotor.setPower(0.5);
        rightIntakeMotor.setPower(-0.5);
    }
    void powerOuttake() {
        leftIntakeMotor.setPower(-0.5);
        rightIntakeMotor.setPower(0.5);
    }


    void stopIntake() {
        leftIntakeMotor.setPower(0);
        rightIntakeMotor.setPower(0);
    }
    //going to be replaced with encoder stuffs
    void liftIntake() {
        intakeMotorLift.setPower(0.5);
    }

    void lowerIntake() {
        intakeMotorLift.setPower(-0.5);
    }
}
