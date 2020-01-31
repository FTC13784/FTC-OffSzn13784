// IMPORTANT: THIS PROGRAM HAS BEEN RETIRED - USE ENCODER FUN LIGHT INSTEAD

package org.firstinspires.ftc.teamcode.skystone.autonomous.unused;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//import com.qualcomm.robotcore.hardware.ColorSensor;

public class EncoderFun extends Encoder {

    DcMotor[] allDrive = new DcMotor[4];
    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];

    //ColorSensor colorSensor;

    HardwareMap hardwareMap;
    LinearOpMode opMode;
    Telemetry telemetry;

    //these are the measurements you need to change for a different tank drive robot
    private double ticksPerRev = 1120;
    private double wheelRadius = 2; // inches

    private double ticksPerIn;
    private double wheelCircumference;
    private double ticksPerDegree;

    //long start, now, duration = 0;
    //double currentPower;
    // private Servo servoClaw;

    public EncoderFun(LinearOpMode opMode) {
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        InitializeHardware();
        wheelCircumference = wheelRadius * Math.PI * 2;
        ticksPerIn = 2 * (ticksPerRev / wheelCircumference);
        ticksPerDegree = 2 * (ticksPerRev / 130);

        telemetry.addData("lf position", allDrive[0].getCurrentPosition());
        telemetry.addData("lb position", allDrive[1].getCurrentPosition());
        telemetry.addData("rf position", allDrive[2].getCurrentPosition());
        telemetry.addData("rb position", allDrive[3].getCurrentPosition());
        // telemetry.addData("rgb", colorSensor.red() + ", " + colorSensor.green() + ", " + colorSensor.blue());
        // telemetry.addData("luminosity, color total", colorSensor.alpha() + " " + colorSensor.argb());
        telemetry.update();
    }

    private void InitializeHardware() {
        leftDrive[0] = hardwareMap.dcMotor.get("lf");
        leftDrive[1] = hardwareMap.dcMotor.get("lb");
        rightDrive[0] = hardwareMap.dcMotor.get("rf");
        rightDrive[1] = hardwareMap.dcMotor.get("rb");

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        allDrive[0] = hardwareMap.dcMotor.get("lf");
        allDrive[1] = hardwareMap.dcMotor.get("lb");
        allDrive[2] = hardwareMap.dcMotor.get("rf");
        allDrive[3] = hardwareMap.dcMotor.get("rb");
        // colorSensor = hardwareMap.colorSensor.get("color");
        // servoClaw = hardwareMap.servo.get("servoClaw");
    }

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
        for (DcMotor motor : motors) {

            double targetPosition = Math.round(distance);
            motor.setTargetPosition((int) targetPosition);
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
                    for (double i = motor.getPower(); i > power; i -= 0.25) {
                        motor.setPower(i);
                    }
                } else {
                    for (double i = motor.getPower(); i < power; i += 0.25) {
                        motor.setPower(i);
                    }
                }
            }
            motor.setPower(power);
        }
    }

    private void stopDriving() {
        setPower(allDrive, 0);

        // resets the motors to normal
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);
    }

    public void driveCm(double distance, double speed) {

        int ticks = (int) (distance / .03526);

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveBackCm(double distance, double speed) {

        int ticks = (int) (distance / .03526);

        setDirection(leftDrive, DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveLeftCm(double distance, double speed) {

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
            //wait until target position in reached
        }
        stopDriving();
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
        stopDriving();
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
        stopDriving();
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
        stopDriving();
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
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turnLeft(double degrees, double speed) {

        double ticks = degrees * 1200 / 90;
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

    public void turnRight(double degrees, double speed) {

        double ticks = degrees * 1200 / 90;
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

    public void driveTime(double time, double speed) throws InterruptedException {
        double i = time;
        setPower(allDrive, speed);
        while (i > 0) {
            setPower(allDrive, speed);
            wait(1000);
            i--;
        }
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /* public void clawPress() {
        servoClaw.setPosition(1);
        telemetry.addData("Claw", "Close");
    }

    public void clawDrop() {
        servoClaw.setPosition(0);
        telemetry.addData("Claw", "Open");
    }


    public void driveUntilAlpha (double threshold, double speed) {
        colorSensor.enableLed(true);

        while (colorSensor.argb() < threshold) {
            try {
                driveTime(200, speed);
            }
            catch (InterruptedException e) {
                telemetry.addData("Interrupted!", "Exception");
                telemetry.update();
            }
        }

        colorSensor.enableLed(false);
    }

    public void driveUntilPicture (double lumThreshold, double threshold, double speed) {
        boolean looping = true;

        while (looping) {
            driveUntilAlpha(threshold, speed);

            if (colorSensor.alpha() < lumThreshold) {
                looping = false;
           }
        }
    } */
}
