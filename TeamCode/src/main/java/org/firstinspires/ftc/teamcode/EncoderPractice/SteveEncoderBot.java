package org.firstinspires.ftc.teamcode.EncoderPractice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SteveEncoderBot {

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

    public SteveEncoderBot(LinearOpMode opMode) {
        this.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        InitializeHardware();
        wheelCircumference = wheelRadius * Math.PI * 2;
        ticksPerIn = 2*((ticksPerRev * 1) / wheelCircumference);
        ticksPerDegree = 2*(ticksPerRev/130);

        telemetry.addData("lf position", allDrive[0].getCurrentPosition());
        telemetry.addData("lb position", allDrive[1].getCurrentPosition());
        telemetry.addData("rf position", allDrive[2].getCurrentPosition());
        telemetry.addData("rb position", allDrive[3].getCurrentPosition());
        telemetry.update();
    }

    private void InitializeHardware() {
        leftDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        leftDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        rightDrive[0] = hardwareMap.dcMotor.get("Right_Front");
        rightDrive[1] = hardwareMap.dcMotor.get("Right_Back");

        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.FORWARD);

        allDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        allDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        allDrive[2] = hardwareMap.dcMotor.get("Right_Front");
        allDrive[3] = hardwareMap.dcMotor.get("Right_Back");
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

    public void StopDriving() {
        setPower(allDrive, 0);
    }


    /**
     * makes tank drive robot with 4 of the same motors drive specified distance
     * positive power is forward, negative power is backward (I think? need to test.)
     *
     * @param ticks distance in # of ticks
     * @param speed speed btwn -1 and 1
     */

    public void driveTicks(double ticks, double speed) {

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, ticks);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);


        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        StopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * drive distance specified
     * positive power is forward, negative power is backward
     *
     * @param distance distance in inches, negative to go backward and positive to go forward.
     * @param speed    speed btwn -1 and 1
     */

    public void driveDistance(double distance, double speed) {

        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive, distance * ticksPerIn);

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        StopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * turn in place to the left degrees specified
     *
     * @param degrees degrees between 0 and 360
     * @param speed   speed btwn 0 and 1
     */

    public void turnLeft(int degrees, double speed) {
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(leftDrive, -((degrees) * ticksPerDegree));
        setWheelTargetPosition(rightDrive, ((degrees) * ticksPerDegree));

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        StopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * turn in place degrees specified to the right
     *
     * @param degrees degrees turn between 0 and 360
     * @param speed   speed between 0 and 1
     */
    public void turnRight(int degrees, double speed) {
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(leftDrive, (degrees * ticksPerDegree));
        setWheelTargetPosition(rightDrive, -(degrees * ticksPerDegree));

        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive, speed);

        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            //wait until target position in reached
        }
        StopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
