/*

TODO: servo initialize to 0, x --> 1, y --> 0

*/

// team package
package org.firstinspires.ftc.teamcode.skystone.teleop.Part_tests;

// import statement

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//import org.firstinspires.ftc.teamcode.skystone.SkyStoneUtils;

// set teleop mode
@TeleOp(name = "chad_test", group = "Linear Opmode")

// main
/**
 * @author robert does stuff
 */

public class chad_test extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack, raise, extend;

    // initialize motor array as well as variables
    //DcMotor[] allDrive = new DcMotor[4];
    // start timer
    private ElapsedTime runtime = new ElapsedTime();
    //Servo servoRelocL, servoRelocR, servoClaw;

    // opmode
    @Override
    public void runOpMode() throws InterruptedException {
        float[] powerTelemetry = new float[4];
        float servoRelocRPos = 0;
        float servoRelocLPos = 1;
        //for raise
        boolean leftTrigger = false;
        boolean rightTrigger = false;
        boolean rightbump = false;
        boolean leftbump = false;
        float initialFrontRightPower, initialBackRightPower, initialFrontLeftPower, initialBackLeftPower;
        float oneBlock = -2000;
        //TODO: Change this into a double to raise and lower the servo by half blocks.
        int targetBlock = 0;
        double upCoolDown = -500;
        double downCoolDown = -500;
        int extendTarget = 0;


        telemetry.addData("Status", "Initialized");


        //SkyStoneUtils.initializeTeleOpHardware(allDrive, leftBack, leftFront, rightBack, rightFront, servoRelocL, servoRelocR, hardwareMap);
        leftFront = hardwareMap.dcMotor.get("lf");
        leftBack = hardwareMap.dcMotor.get("lb");
        rightFront = hardwareMap.dcMotor.get("rf");
        rightBack = hardwareMap.dcMotor.get("rb");
        raise = hardwareMap.dcMotor.get("raise");
        extend = hardwareMap.dcMotor.get("extend");
        //servoRelocL=hardwareMap.servo.get("servoRelocL");
        //servoRelocR=hardwareMap.servo.get("servoRelocR");
        //servoClaw=hardwareMap.servo.get("servoClaw");


        waitForStart();
        runtime.reset();

        raise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        raise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        raise.setPower(1);
        extend.setPower(1);


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Debugging
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1LeftY = gamepad1.left_stick_y;
            float gamepad1RightX = gamepad1.right_stick_x;

            rightTrigger = gamepad1.right_trigger < 0.5;
            leftTrigger = gamepad1.left_trigger < 0.5;

            if (Math.abs(gamepad1LeftX) < 0.1 && Math.abs(gamepad1LeftX) > -0.1) {
                gamepad1LeftX = 0;
            }
            if (Math.abs(gamepad1LeftY) < 0.1 && Math.abs(gamepad1LeftY) > -0.1) {
                gamepad1LeftY = 0;
            }
            if (Math.abs(gamepad1RightX) < 0.1 && Math.abs(gamepad1RightX) > -0.1) {
                gamepad1RightX = 0;
            }


            // holonomic formulas
            float FrontLeftPower = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float BackLeftPower = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            float FrontRightPower = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float BackRightPower = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

            initialBackLeftPower = BackLeftPower;
            initialFrontLeftPower = FrontLeftPower;
            initialFrontRightPower = FrontRightPower;
            initialBackRightPower = BackRightPower;

            if (rightTrigger) {
                FrontLeftPower = initialFrontLeftPower * 0.5F;
                BackLeftPower = initialBackLeftPower * 0.5F;
                FrontRightPower = initialFrontRightPower * 0.5F;
                BackRightPower = initialBackRightPower * 0.5F;
            } else if (leftTrigger) {
                FrontLeftPower = initialFrontLeftPower * 1.5F;
                BackLeftPower = initialBackLeftPower * 1.5F;
                FrontRightPower = initialFrontRightPower * 1.5F;
                BackRightPower = initialBackRightPower * 1.5F;
            } else {
                FrontLeftPower = initialFrontLeftPower;
                BackLeftPower = initialBackLeftPower;
                FrontRightPower = initialFrontRightPower;
                BackRightPower = initialBackRightPower;
            }

            FrontLeftPower = Range.clip(FrontLeftPower, -1, 1);
            BackLeftPower = Range.clip(BackLeftPower, -1, 1);
            FrontRightPower = Range.clip(FrontRightPower, -1, 1);
            BackRightPower = Range.clip(BackRightPower, -1, 1);

            // write the values to the motors
            leftFront.setPower(FrontLeftPower);
            leftBack.setPower(BackLeftPower);
            rightFront.setPower(FrontRightPower);
            rightBack.setPower(BackRightPower);
            if (gamepad1.a) {leftFront.setPower(FrontLeftPower);
                telemetry.addLine("LeftFront");
            }
            if (gamepad1.b) {leftFront.setPower(FrontRightPower);
                telemetry.addLine("RightFront");
            }

            telemetry.update();
        }
    }
}
