/*

TODO: servo initialize to 0, x --> 1, y --> 0

*/

// team package
package org.firstinspires.ftc.teamcode.skystone.teleop;

// import statement

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//import org.firstinspires.ftc.teamcode.skystone.SkyStoneUtils;

// set teleop mode
@TeleOp(name = "Robert1", group = "Linear Opmode")

// main
/**
 * @author robert does stuff
 */

public class RobertTeleop1 extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack, raise, extend;

    // initialize motor array as well as variables
    //DcMotor[] allDrive = new DcMotor[4];
    // start timer
    private ElapsedTime runtime = new ElapsedTime();
    private double prevRunTime = 0;
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

            rightbump = gamepad1.right_bumper;
            leftbump = gamepad1.left_bumper;
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

            FrontLeftPower = Range.clip(FrontLeftPower, -1, 1);
            BackLeftPower = Range.clip(BackLeftPower, -1, 1);
            FrontRightPower = Range.clip(FrontRightPower, -1, 1);
            BackRightPower = Range.clip(BackRightPower, -1, 1);

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

            // write the values to the motors
            //Remember to make them accelerate! We want to change the power every tick.
            if (leftFront.getPower() != FrontLeftPower) {
                if (BackRightPower > leftFront.getPower()) {
                    for (double i = leftFront.getPower(); i < FrontLeftPower; i += 0.15) {
                        leftFront.setPower(i);
                    }
                }
                else {
                    for (double i = leftFront.getPower(); i > FrontLeftPower; i -= 0.15) {
                        leftFront.setPower(i);
                    }
                }
                leftFront.setPower(FrontLeftPower);
            }
            if (leftBack.getPower() != BackLeftPower) {
                if (BackLeftPower > leftBack.getPower()) {
                    for (double i = leftBack.getPower(); i < BackLeftPower; i += 0.15) {
                        leftBack.setPower(i);
                    }
                }
                else {
                    for (double i = leftBack.getPower(); i > BackLeftPower; i -= 0.15) {
                        leftBack.setPower(i);
                    }
                }
                leftBack.setPower(BackLeftPower);
            }
            if (rightFront.getPower() != FrontRightPower) {
                if (FrontRightPower > rightFront.getPower()) {
                    for (double i = rightFront.getPower(); i < FrontRightPower; i += 0.15) {
                        rightFront.setPower(i);
                    }
                }
                else {
                    for (double i = rightFront.getPower(); i > FrontRightPower; i -= 0.15) {
                        rightFront.setPower(i);
                    }
                }
                rightFront.setPower(FrontRightPower);
            }
                if (rightBack.getPower() != BackRightPower) {
                    if (BackRightPower > rightBack.getPower()) {
                        for (double i = rightBack.getPower(); i < BackRightPower; i += 0.15) {
                            rightBack.setPower(i);
                        }
                    }
                    else {
                        for (double i = rightBack.getPower(); i > BackRightPower; i -= 0.15) {
                            rightBack.setPower(i);
                        }
                    }
                    rightBack.setPower(BackRightPower);
                }
                float raisePos = raise.getCurrentPosition();
                telemetry.addData("ENCODER(r)", "raisePos: " + raisePos);


                if (rightbump && getRuntime() > upCoolDown) {
                    targetBlock++;
                    upCoolDown = getRuntime() + .2; //cooldown of .2s
                }
                if (leftbump && getRuntime() > downCoolDown) {
                    targetBlock--;
                    downCoolDown = getRuntime() + .2; //cooldown of .2s
                }


                telemetry.addData("Raiseblock", "going to: " + targetBlock);


                //We want to account for half blocks/being able to pick up blocks as wel as place them.
                int targetPos = Math.round(targetBlock * oneBlock / 2);

                telemetry.addData("Raisepos", "going to: " + targetPos);


                //raise.getCurrentPosition();
                raise.setTargetPosition(targetPos);


                if (leftTrigger) {
                    if (extendTarget == 0)
                        extendTarget = 8500;
                    else extendTarget = 0;
                }


                //extend.getCurrentPosition();
               // extend.setTargetPosition(extendTarget);

                telemetry.addData("ExtendTarget", "Value: " + extend.getTargetPosition());

                telemetry.addData("ExtendEncode", "Value: " + extend.getCurrentPosition());

                telemetry.update();

                prevRunTime = getRuntime();
        }
    }

        public void accelerateTo (DcMotor motor,float power){

        }
}
