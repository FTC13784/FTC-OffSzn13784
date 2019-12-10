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
@TeleOp(name = "lift_test", group = "Linear Opmode")

// main
/**
 * @author robert does stuff
 */

public class lift_test extends LinearOpMode {

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
        float oneBlock = -1900;
        double targetBlock = 0;
        double upCoolDown = -10;
        double downCoolDown = -10;
        int extendTarget = 0;


        telemetry.addData("Status", "Initialized");


        //SkyStoneUtils.initializeTeleOpHardware(allDrive, leftBack, leftFront, rightBack, rightFront, servoRelocL, servoRelocR, hardwareMap);

        raise = hardwareMap.dcMotor.get("raise");


        waitForStart();
        runtime.reset();

        raise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        raise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        raise.setPower(1);


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Debugging
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            float raisePos = raise.getCurrentPosition();
            telemetry.addData("ENCODER(r)", "raisePos: " + raisePos);
            rightbump = gamepad1.right_bumper;
            leftbump = gamepad1.left_bumper;


            if (rightbump && getRuntime() > upCoolDown) {
                targetBlock++;
                upCoolDown = getRuntime() + .2; //cooldown of .2s
            }
            if (leftbump && getRuntime() > downCoolDown) {
                targetBlock--;
                downCoolDown = getRuntime() + .2; //cooldown of .2s
            }
            if(targetBlock<0) targetBlock=0;

            telemetry.addData("Raiseblock", "going to: " + targetBlock);

            //max = 6000
            //one block = 1950

            //We want to account for half blocks/being able to pick up blocks as wel as place them
            int targetPos = (int) Math.round(targetBlock * oneBlock);
            if (targetPos<-5700){
                telemetry.addData("Warning", "targetPos value is too low!");
                targetPos=-5700;
                targetBlock--;
            }
            if (targetPos>0){
                telemetry.addData("Warning", "targetPos value is too high!");
                targetPos=0;
            }

            telemetry.addData("Raisepos", "going to: " + targetPos);

            raise.setTargetPosition(targetPos);

            telemetry.update();
        }
    }
}
