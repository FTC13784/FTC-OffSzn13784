/**
 * Base constructor for all autonomous programs.
 * <p>
 * Contributors:
 *
 * @author Edwardidk
 * @contributors FavouriteDragon
 * @nothing Commandjoe
 * <p>
 * <p>
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
 * left color sensor -> color
 * right color sensor -> color2 (retired - no second color sensor)
 * <p>
 * <p>
 * <p>
 * Conversions:
 * driving forward - ticks = distance in cm / .03526
 * turns - ticks = degrees * 1200 / 90
 * <p>
 * <p>
 * <p>
 * ===========
 * ||METHODS||
 * ===========
 * <p>
 * BASIC METHODS
 * -------------
 * <p>
 * CONSTRUCTOR / INITIALIZATION
 * EncoderFunLight (LinearOpMode opmode) - constructor, initializes robot
 * void initializeHardware() - initializes hardware on robot, autoruns alongside constructor
 * <p>
 * RAW DRIVE
 * void setDirection(DcMotor[] motors, DcMotorSimple.Direction direction) - configure motors to
 * correct directions
 * void setMode(DcMotor[] motors, DcMotor.RunMode mode) - set movement mode for motors
 * void setWheelTargetPosition(DcMotor[] motors, double distance) - set target position for motor
 * boolean isBusy(DcMotor[] motors) - test if motors are currently running an action and return
 * as boolean
 * void setPower(DcMotor[] motors, double power) - set power for motors
 * void stopDriving() - stop all motor actions
 * <p>
 * <p>
 * USABLE METHODS
 * --------------
 * <p>
 * COMPLEX DRIVE
 * Cardinal Direction Drive:
 * void driveCm(double distance, double speed) - drive in forward direction for distance
 * in centimeters
 * void driveBackCm(double distance, double speed) - driveCm in backwards direction
 * void driveLeftCm(double distance, double speed) - driveCm in left direction
 * void driveRightCm(double distance, double speed) - driveCm in right direction
 * <p>
 * Diagonal Drive:
 * (retired) void drive_lf(double ticks, double speed) - drive in left-front direction for ticks
 * (retired) void drive_rf(double ticks, double speed) - drive in right-front direction for ticks
 * (retired) void drive_lb(double ticks, double speed) - drive in left-back direction for ticks
 * (retired) void drive_rb(double ticks, double speed) - drive in right-back direction for ticks
 * <p>
 * Turn:
 * void turnLeft(double degrees, double speed) - turn left for degrees
 * void turnRight(double degrees, double speed) - turn right for degrees
 * <p>
 * Timed:
 * (retired) void driveTime(double time, double speed) - drive forward for time in seconds - ONLY
 * WORKS FOR WHOLE SECONDS
 * <p>
 * <p>
 * AUXILIARY
 * Lift:
 * void setupLift() - initialize lift
 * void controlLiftMotor(double targetBlock) - change lift height to height in blocks
 * void extendCM(double centimetres, double power) - extend lift to distance in centimeters
 * <p>
 * Claw:
 * void openClaw() - open front claw
 * void closeClaw() - open back claw
 * <p>
 * Foundation Mover:
 * void openFoundation() - open foundation grabber
 * void closeFoundation() - close foundation grabber
 * <p>
 * Light Sensor:
 * void driveUntilAlpha(double threshold, double speed) - drive until alpha threshold is reached
 * void driveUntilPicture(double lumThreshold, double threshold, double speed) - drive until
 * threshold is between nothing and block threshold
 * <p>
 * Testing:
 * void lightTele() - permanent displays telemetry for light sensor
 * void lightTele(double blockThresh, double skyThresh) - permanent loop, base lightTele + display
 * what program thinks its detecting
 */

// Autonomous package
package org.firstinspires.ftc.teamcode.skystone.autonomous;

// import packages

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AutoTransitioner;
import org.firstinspires.ftc.teamcode.FTCConstants;

import static org.firstinspires.ftc.teamcode.FTCConstants.BLOCK_LENGTH;

// EncoderFunLight class
public class EncoderFunLight extends Encoder {
    // variable for block size
    public ColorSensor colorSensor, colorSensor2;
    public DcMotor liftMotor, extensionMotor;
    public Servo foundationFront, foundationBack, leftClawServo, rightClawServo;
    public LinearOpMode opMode;
    public Telemetry telemetry;
    public TouchSensor touchSensor;

    // initialize variables
    DcMotor[] allDrive = new DcMotor[4];
    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];
    HardwareMap hardwareMap;

    // these are the measurements you need to change for a different tank drive robot
    /* private final double TICKSPERREV = 1120;
    private final double WHEELRADIUS = 2; // inches

    private double ticksPerIn;
    private double wheelCircumference;
    private double ticksPerDegree;*/


    // EncoderFunLight object
    //TODO: Remove stopDriving from all programs and make people call it manually
    public EncoderFunLight(LinearOpMode opMode) {
        // setup opMode
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        // initialize hardware
        initializeHardware();
        AutoTransitioner.transitionOnStop(opMode, "Better Teleop");

        // math functions to calculate parameters based on final variables
        /* wheelCircumference = WHEELRADIUS * Math.PI * 2;
        ticksPerIn = 2 * (TICKSPERREV / wheelCircumference);
        ticksPerDegree = 2 * (TICKSPERREV / 130); */

        // telemetry
        telemetry.addData("lf, lb, rf, rb", allDrive[0].getCurrentPosition() + ", " +
                allDrive[1].getCurrentPosition() + ", " + allDrive[2].getCurrentPosition() + ", " +
                allDrive[3].getCurrentPosition());
        telemetry.addData("rgb", colorSensor.red() + ", " + colorSensor.green() +
                ", " + colorSensor.blue());
        telemetry.addData("luminosity, color total", colorSensor.alpha() + " " +
                colorSensor.argb());
        telemetry.update();
    }

    // HARDWARE INITIALIZATION
    private void initializeHardware() {
        // directional drive motor initialization
        leftDrive[0] = hardwareMap.dcMotor.get("lf");
        leftDrive[1] = hardwareMap.dcMotor.get("lb");
        rightDrive[0] = hardwareMap.dcMotor.get("rf");
        rightDrive[1] = hardwareMap.dcMotor.get("rb");

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        // general drive motor initialization
        allDrive[0] = hardwareMap.dcMotor.get("lf");
        allDrive[1] = hardwareMap.dcMotor.get("lb");
        allDrive[2] = hardwareMap.dcMotor.get("rf");
        allDrive[3] = hardwareMap.dcMotor.get("rb");

        // color sensor
        colorSensor = hardwareMap.colorSensor.get("color");
        // colorSensor2 = hardwareMap.colorSensor.get("color2");

        // doesn't work b/c we don't have fourth connector
        // colorSensor.enableLed(false);

        //touch sensor
        touchSensor = hardwareMap.get(TouchSensor.class, "ts");
        // other motors
        liftMotor = hardwareMap.get(DcMotor.class, "raise");
        extensionMotor = hardwareMap.get(DcMotor.class, "extend");
        foundationFront = hardwareMap.get(Servo.class, "ff");
        foundationBack = hardwareMap.get(Servo.class, "fb");

        // claw servo
        rightClawServo = hardwareMap.get(Servo.class, "cr");
        leftClawServo = hardwareMap.get(Servo.class, "cl");
    }

    // BASIC DRIVE FUNCTIONS
    private void setDirection(DcMotor[] motors, DcMotorSimple.Direction direction) {
        for (DcMotor motor : motors) {
            motor.setDirection(direction);
        }
    }

    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    // set target position in # of ticks
    private void setWheelTargetPosition(DcMotor[] motors, double distance) {
        double targetPosition;
        for (DcMotor motor : motors) {
            targetPosition = Math.round(distance);
            motor.setTargetPosition((int) targetPosition);
        }
    }

    private void setWheelPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            if (motor.getPower() > power) {
                for (double i = motor.getPower(); i > power; i -= 0.20)
                    motor.setPower(i);
                motor.setPower(power);
            } else if (motor.getPower() < power) {
                for (double i = motor.getPower(); i < power; i += 0.20)
                    motor.setPower(i);
                motor.setPower(power);
            }
        }
    }
    private boolean isBusy(DcMotor[] motors) {
        for (DcMotor motor : motors) {
            if (motor.isBusy()) {
                return true;
            }
        }
        return false;
    }

    private void setPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            if (motor.getPower() != power) {
                if (motor.getPower() > power) {
                    for (double i = motor.getPower(); i > power; i -= 0.20) {
                        motor.setPower(i);
                    }
                } else {
                    for (double i = motor.getPower(); i < power; i += 0.20) {
                        motor.setPower(i);
                    }
                }
            }

            motor.setPower(power);
        }
    }

    public void stopDriving() {
        // stop all motors
        setPower(allDrive, 0);

        // resets the motors to normal
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);
    }

    public void driveTicks(int ticks, double speed) {

        // ensure directions are correct
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive, speed);

        // unused telemetry
        while (isBusy(allDrive) && opMode.opModeIsActive()) {

        }

        // stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }
    // COMPLEX DRIVE FUNCTIONS
    public void driveCm(double distance, double speed) {
        // convert cm to ticks
        int ticks = (int) (distance / .03526);

        // ensure directions are correct
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive, speed);

        // unused telemetry
        while (isBusy(allDrive) && opMode.opModeIsActive()) {

        }

        // stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveBackCm(double distance, double speed) {
        // convert cm to ticks
        int ticks = (int) (distance / .03526);

        setDirection(leftDrive, DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        // stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //TODO: Continuous method for driving right, and a predicate

    public void driveContinuousLeftCm(double speed) {
        //double distance = 5;
        //int ticks = (int) (distance / .03526);
        leftDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelPower(allDrive, speed);
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive() && !touchSensor.isPressed()) {
        }
        setPower(allDrive, 0);
    }

    public void driveLeftCm(double distance, double speed) {
        // convert cm to ticks
        int ticks = (int) (distance / .03526);
        leftDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive, ticks);
        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
            // unused telemetry
            /* telemetry.addData("Backwards Power", drivePower);
            telemetry.addData("Backwards Ticks", ticks);
            telemetry.update();
            ticks--;*/
        }
        // stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveRightCm(double distance, double speed) {
        int ticks = (int) (distance / .03526);
        leftDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive, ticks);
        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive, speed);
        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }
        //  stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive_lf(double ticks, double speed) {
        leftDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive, ticks);
        leftDrive[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive, speed);
        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive_rf(double ticks, double speed) {
        leftDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive, ticks);
        leftDrive[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive, speed);
        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }
        // stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive_lb(double ticks, double speed) {
        leftDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive, ticks);
        leftDrive[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // leftDrive[1].setPower(speed);
        // rightDrive[0].setPower(speed);
        setPower(allDrive, speed);
        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }
        // stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive_rb(double ticks, double speed) {
        leftDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive, ticks);
        leftDrive[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive, speed);
        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }

        //  stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turnLeftTicks(int ticks, double speed) {
        setDirection(leftDrive, DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }

        telemetry.update();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void turnLeft(double degrees, double speed) {
        // convert degrees to ticks
        double ticks = FTCConstants.degreesToTicks(degrees);
        telemetry.addData("Ticks: ", ticks);

        setDirection(leftDrive, DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }

        telemetry.update();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // used for debugging
    /*
    public void turnLeftTicks(double ticks, double speed) {
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }

        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }
    */

    public void turnRight(double degrees, double speed) {
        // convert degrees to ticks
        double ticks = FTCConstants.degreesToTicks(degrees);

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);
        // set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }

        //  stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveTime(int time, double speed) throws InterruptedException {
        int i = time;
        setPower(allDrive, speed);
        while (i > 0) {
            setPower(allDrive, speed);
            wait(1000);
            i--;
        }
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // AUXILIARY FUNCTIONS
    // lift initialization
    public void setupLift() {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(1);
    }

    // lift motor code
    public void controlLiftMotor(double targetBlock) {
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

    public void extendCM(double centimetres, double power) {
        int ticks = FTCConstants.cmToTicks(centimetres);

        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setDirection(power < 0 ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extensionMotor.setTargetPosition(Math.round(ticks));
        extensionMotor.setPower(power);
        while (opMode.opModeIsActive() && extensionMotor.isBusy()) {
            //Wait until it's done
        }
        extensionMotor.setPower(0);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    // claw code
    public void openClaw() {
        leftClawServo.setPosition(0);
        rightClawServo.setPosition(1 - leftClawServo.getPosition());
    }

    public void closeClaw() {
        leftClawServo.setPosition(1);
        rightClawServo.setPosition(1 - leftClawServo.getPosition());
    }


    // foundation mover code
    public void openFoundation() {
        foundationFront.setPosition(0.3);
        foundationBack.setPosition(1 - foundationFront.getPosition());
    }

    public void closeFoundation() {
        foundationFront.setPosition(1);
        foundationBack.setPosition(1 - foundationFront.getPosition());
    }


    // light sensor code
    public int driveUntilPicture(double threshold, FTCConstants.DIRECTION direction) {
        int blocks = 0;
        // TODO: make robot more efficient


        colorSensor.enableLed(true);

        // drive until falls under yellow threshold or reaches last block
        while (colorSensor.argb() > threshold && blocks < 5) {
            switch (direction) {
                case LEFT:
                    driveCm(BLOCK_LENGTH, 0.35F);
                    break;
                case RIGHT:
                    driveRightCm(BLOCK_LENGTH, 0.35F);
                    break;
            }

            telemetry.addData("RGB, B || Y?", colorSensor.argb() +
                    ((colorSensor.argb() < threshold) ? "black" : "yellow"));

            blocks++;
        }

        colorSensor.enableLed(false);

        return blocks;
    }

    public void lightTele() {
        while (true) {
            telemetry.addData("rgb", colorSensor.red() + ", " + colorSensor.green() + ", " + colorSensor.blue());
            telemetry.addData("luminosity, color total", colorSensor.alpha() + " " + colorSensor.argb());
            telemetry.update();
        }
    }

    public void lightTele(double blockThresh, double skyThresh) {
        while (true) {
            telemetry.addData("rgb", colorSensor.red() + ", " + colorSensor.green() + ", " + colorSensor.blue());
            telemetry.addData("luminosity, color total", colorSensor.alpha() + " " + colorSensor.argb());

            if (colorSensor.alpha() < skyThresh) {
                telemetry.addData("block", "skystone");
            } else if (colorSensor.argb() < blockThresh) {
                telemetry.addData("block", "normal");
            } else {
                telemetry.addData("block", "n/a");
            }

            telemetry.update();
        }
    }
}
