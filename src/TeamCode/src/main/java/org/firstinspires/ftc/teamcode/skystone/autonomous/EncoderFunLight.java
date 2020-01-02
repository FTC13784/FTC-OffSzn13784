// Autonomous package
package org.firstinspires.ftc.teamcode.skystone.autonomous;

// import packages
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// EncoderFunLight class
public class EncoderFunLight extends Encoder {
    // initialize variables
    DcMotor[] allDrive = new DcMotor[4];
    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];

    ColorSensor colorSensor;

    HardwareMap hardwareMap;
    LinearOpMode opMode;
    Telemetry telemetry;

    // these are the measurements you need to change for a different tank drive robot
    /* private final double TICKSPERREV = 1120;
    private final double WHEELRADIUS = 2; // inches

    private double ticksPerIn;
    private double wheelCircumference;
    private double ticksPerDegree;*/

    // EncoderFunLight object
    public EncoderFunLight(LinearOpMode opMode) {
        // setup opMode
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        // initialize hardware
        InitializeHardware();

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

    private void InitializeHardware() {
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

        // doesn't work b/c we don't have fourth connector
        // colorSensor.enableLed(false);
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
        double targetPosition;

        for (DcMotor motor : motors) {
            targetPosition = Math.round(distance);
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

    public void stopDriving() {
        // stop all motors
        setPower(allDrive, 0);

        // resets the motors to normal
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);
    }

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
        /* while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
            telemetry.addData("Forwards Power", drivePower);
            telemetry.addData("Ticks", ticks);
            telemetry.update();
            ticks--;
        } */

        stopDriving();
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
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
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
        // convert degrees to ticks
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
        // convert degrees to ticks
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

    // TODO: CLAWCODE
    // currently not working
    /* public void clawPress() {
        servoClaw.setPosition(1);
        telemetry.addData("Claw", "Close");
    }
    public void clawDrop() {
        servoClaw.setPosition(0);
        telemetry.addData("Claw", "Open");
    }*/

    // LIGHT SENSOR CODE
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
    }

    public void lightTele () {
        while (true) {
            telemetry.addData("rgb", colorSensor.red() + ", " + colorSensor.green() + ", " + colorSensor.blue());
            telemetry.addData("luminosity, color total", colorSensor.alpha() + " " + colorSensor.argb());
            telemetry.update();
        }
    }

    public void lightTele (double blockThresh, double skyThresh) {
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
