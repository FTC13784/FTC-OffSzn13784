package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class EncoderFunLight extends Encoder {

    DcMotor[] allDrive = new DcMotor[4];
    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];

    ColorSensor colorSensor;

    HardwareMap hardwareMap;
    LinearOpMode opMode;
    Telemetry telemetry;

    //these are the measurements you need to change for a different tank drive robot.
    double ticksPerRev = 1120;
    double wheelRadius = 2; // inches

    double ticksPerIn;
    double wheelCircumference;
    double ticksPerDegree;

    public EncoderFunLight(LinearOpMode opMode) {
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        InitializeHardware();
        wheelCircumference = wheelRadius * Math.PI * 2;
        ticksPerIn = 2 * (ticksPerRev / wheelCircumference);
        ticksPerDegree = 2 * (ticksPerRev / 130);

        telemetry.addData("rgb", colorSensor.red() + ", " + colorSensor.green() + ", " + colorSensor.blue());
        telemetry.addData("luminosity, color total", colorSensor.alpha() + " " + colorSensor.argb());
        telemetry.update();
    }

    private void InitializeHardware() {
        colorSensor = hardwareMap.colorSensor.get("color");
        colorSensor.enableLed(false);
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
        double target = distance;
        for (DcMotor motor : motors) {

            double targetPosition = Math.round(target);
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
            motor.setPower(power);
        }
    }

    public void stopDriving(double power) {
       /* for (double i = power; i > 0; i -= 0.05F) {
            setPower(allDrive, i);
            System.out.println("Power is " + i);
        }**/
        setPower(allDrive, 0);
        //Resets the motors to normal.
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);
    }

    public void driveTicks(double ticks, double speed) {

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
            telemetry.addData("Forwards Power", drivePower);
            telemetry.addData("Ticks", ticks);
            telemetry.update();
            ticks--;
        }
        stopDriving(speed);
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveBackTicks(double ticks, double speed) {

        setDirection(leftDrive, DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
            telemetry.addData("Backwards Power", drivePower);
            telemetry.addData("Backwards Ticks", ticks);
            telemetry.update();
            ticks--;
        }
        stopDriving(speed);
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turnLeft(double ticks, double speed) {

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);
        // Set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        stopDriving(speed);
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void turnRight(double ticks, double speed) {

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);
        // Set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        stopDriving(speed);
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
        stopDriving(speed);
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveUntilAlpha (double threshold, double speed) {
        colorSensor.enableLed(true);

        while (colorSensor.argb() < threshold) {
            driveTicks(200, speed);
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
