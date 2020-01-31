package org.firstinspires.ftc.teamcode.examples.teleop;
//package org.firstinspires.ftc.teamcode.examples.encoderPractice;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
	Holonomic concepts from:
	http://www.vexforum.com/index.php/12370-holonomic-drives-2-0-a-video-tutorial-by-cody/0
   Robot wheel mapping:
          X FRONT X
        X           X
      X  FL       FR  X
              X
             XXX
              X
      X  BL       BR  X
        X           X
          X       X
*/


@TeleOp(name = "Robert is a Claw Holonomic", group = "Linear Opmode")
// @Autonomous1(...) is the other common choice
//@Disabled
public class grabboCode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //DcMotor[] allDrive = new DcMotor[4];
    Servo servoClaw;








    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();



        waitForStart();
        runtime.reset();
        float servoClawPos = 0;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Servo", "servoClawPos: " + servoClawPos);
            telemetry.update();



            // setPower(allDrive, -gamepad1.left_stick_x);

            // left stick controls direction
            // right stick X controls rotation







            float gamepad1ltrigger = gamepad1.left_trigger;



            if (gamepad1ltrigger<=.5){
                servoClawPos = 0;
            }
            else{
                servoClawPos =1;
            }


            // write the values to the motors
            servoClaw.setPosition(Range.clip(servoClawPos, 0, 1));






        }
    }

    private void InitializeHardware() {

        servoClaw =hardwareMap.servo.get("servoClaw");

    }
}