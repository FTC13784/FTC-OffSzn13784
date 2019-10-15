package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class EncoderFun extends Encoder {

    DcMotor[] allDrive = new DcMotor[4];
    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];

    HardwareMap hardwareMap;
    LinearOpMode opMode;
    Telemetry telemetry;

    //these are the measurements you need to change for a different tank drive robot.
    double ticksPerRev = 1120;
    double wheelRadius = 2; // inches

    double ticksPerIn;
    double wheelCircumference;
    double ticksPerDegree;

    public EncoderFun(LinearOpMode opMode) {
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        InitializeHardware();
        wheelCircumference = wheelRadius * Math.PI * 2;
        ticksPerIn = 2 * ((ticksPerRev * 1) / wheelCircumference);
        ticksPerDegree = 2 * (ticksPerRev / 130);

        telemetry.addData("lf position", allDrive[0].getCurrentPosition());
        telemetry.addData("lb position", allDrive[1].getCurrentPosition());
        telemetry.addData("rf position", allDrive[2].getCurrentPosition());
        telemetry.addData("rb position", allDrive[3].getCurrentPosition());
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

    public void stopDriving() {
        setPower(allDrive, 0);
    }

    public void driveTicks(double ticks, double speed) {

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
}
