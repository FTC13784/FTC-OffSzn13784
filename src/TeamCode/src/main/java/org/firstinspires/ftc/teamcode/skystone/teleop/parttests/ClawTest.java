/*

TODO: servo initialize to 0, x --> 1, y --> 0

*/

// team package
package org.firstinspires.ftc.teamcode.skystone.teleop.parttests;

// import statement

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


// set teleop mode
@TeleOp(name = "ClawTest", group = "Linear Opmode")

// main
/**
 * @author robert does stuff
 */

public class ClawTest extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack, raise, extend;

    // initialize motor array as well as variables
    //DcMotor[] allDrive = new DcMotor[4];
    // start timer
    private ElapsedTime runtime = new ElapsedTime();
    private double prevRunTime = 0;
    Servo cl, cr;

    // opmode
    @Override
    public void runOpMode() throws InterruptedException {



        telemetry.addData("Status", "Initialized");




        cl=hardwareMap.servo.get("cl");
        cr=hardwareMap.servo.get("cr");
        double clSetPos=0;
        double crSetPos=0;

        boolean xPress;
        boolean yPress;
        double lPos;
        double rPos;

        waitForStart();
        runtime.reset();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Debugging
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            xPress=gamepad1.x;
            yPress=gamepad1.y;
            lPos=gamepad1.left_stick_x/2+.5;
            rPos=gamepad1.right_stick_x/2+.5;

            cl.setPosition(lPos);
            cr.setPosition(rPos);

            telemetry.addData("clawsetpos","cl: "+lPos+"  cr: "+rPos);
            telemetry.update();
            prevRunTime = getRuntime();
        }
    }


}
