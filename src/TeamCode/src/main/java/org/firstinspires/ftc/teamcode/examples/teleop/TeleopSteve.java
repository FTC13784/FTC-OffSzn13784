package org.firstinspires.ftc.teamcode.examples.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Teleop Steve Test", group="Linear Opmode")  // @Autonomous1(...) is the other common choice
//@Disabled
public class TeleopSteve extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor[] allDrive = new DcMotor[4];
    DcMotor[] leftDrive = new DcMotor[2];
    DcMotor[] rightDrive = new DcMotor[2];

    double ticksPerRev = 1120;
    double ticksPerDegree = 2*(ticksPerRev/130);

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            setPower(leftDrive, -gamepad1.left_stick_y);
            setPower(rightDrive, -gamepad1.right_stick_y);

            if (Math.abs(gamepad1.left_stick_y) < 0.1) {
                gamepad1.left_stick_y = 0;
            }
            if (Math.abs(gamepad1.right_stick_y) < 0.1) {
                gamepad1.right_stick_y = 0;
            }

            //turn right test. supposed to go one full rotation turn right
            double speed = 0.5;
            double degrees = 180;
            if (gamepad1.a) {
                setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                setWheelTargetPosition(leftDrive, (ticksPerDegree*degrees));
                setWheelTargetPosition(rightDrive, -(ticksPerDegree*degrees));

                setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);

                // Set drive power
                setPower(allDrive, speed);

                while (isBusy(allDrive) && opModeIsActive()) {
                    //wait until target position in reached
                }
                StopDriving();
                setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
            }

        }
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

    private void setPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
        }
    }

    public void StopDriving(){
        setPower(allDrive, 0);
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
}