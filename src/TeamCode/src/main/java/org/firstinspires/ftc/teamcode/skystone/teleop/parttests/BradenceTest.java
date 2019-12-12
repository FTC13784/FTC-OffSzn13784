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


// set teleop mode
@TeleOp(name = "BradenceTest", group = "Linear Opmode")

// main
/**
 * @author robert does stuff
 */

public class BradenceTest extends LinearOpMode {

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


        waitForStart();
        runtime.reset();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Debugging
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            if(gamepad1.x){
                clSetPos=1;
                crSetPos=-1;
            }
            if(gamepad1.x){
                clSetPos=0;
                crSetPos=0;
            }
            cl.setPosition(clSetPos);
            cr.setPosition(crSetPos);

            telemetry.addData("clawsetpos","cl: "+clSetPos+"  cr:"+crSetPos);
            telemetry.update();
            prevRunTime = getRuntime();
        }
    }


}
