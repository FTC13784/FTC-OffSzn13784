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


@TeleOp(name = "Robert is a Chad Holonomic", group = "Linear Opmode")
// @Autonomous(...) is the other common choice
//@Disabled
public class robertCode extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor[] allDrive = new DcMotor[4];
    Servo servoRelocR, servoRelocL;







    DcMotor leftFrontMotor;
    DcMotor leftBackMotor;
    DcMotor rightFrontMotor;
    DcMotor rightBackMotor;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        InitializeHardware();



        waitForStart();
        runtime.reset();
        float servoRelocRPos = 0;
        float servoRelocLPos = 1;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();


            if (Math.abs(gamepad1.left_stick_y) < 0.1) {
                gamepad1.left_stick_y = 0;
            }
            if (Math.abs(gamepad1.right_stick_y) < 0.1) {
                gamepad1.right_stick_y = 0;
            }
            if (Math.abs(gamepad1.left_stick_x) < 0.1) {
                gamepad1.left_stick_x = 0;
            }
            if (Math.abs(gamepad1.right_stick_x) < 0.1) {
                gamepad1.right_stick_x = 0;
            }

            // setPower(allDrive, -gamepad1.left_stick_x);

            // left stick controls direction
            // right stick X controls rotation





            float gamepad1LeftY = gamepad1.left_stick_y;
            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1RightX = gamepad1.right_stick_x;

            boolean gamepad1x = gamepad1.x;
            boolean gamepad1y = gamepad1.y;

            // holonomic formulas
            float FrontLeftPower = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            float BackLeftPower = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float FrontRightPower = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            float BackRightPower = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;

            // clip the right/left values so that the values never exceed +/- 1
            FrontLeftPower = Range.clip(FrontLeftPower, -1, 1);
            BackLeftPower = Range.clip(BackLeftPower, -1, 1);
            FrontRightPower = Range.clip(FrontRightPower, -1, 1);
            BackRightPower = Range.clip(BackRightPower, -1, 1);

            if (gamepad1x){
                servoRelocLPos = 1;
                servoRelocRPos = 0;
            }
            if (gamepad1y){
                servoRelocLPos = 0;
                servoRelocRPos = 1;
            }


            // write the values to the motors
            servoRelocL.setPosition(Range.clip(servoRelocLPos, 0, 1));
            servoRelocR.setPosition(Range.clip(servoRelocRPos, 0, 1));


            leftFrontMotor.setPower(FrontLeftPower);
            leftBackMotor.setPower(BackLeftPower);
            rightFrontMotor.setPower(FrontRightPower);
            rightBackMotor.setPower(BackRightPower);


        }
    }

    private void InitializeHardware() {
        leftFrontMotor = hardwareMap.dcMotor.get("Left_Front");
        leftBackMotor = hardwareMap.dcMotor.get("Left_Back");
        rightFrontMotor = hardwareMap.dcMotor.get("Right_Front");
        rightBackMotor = hardwareMap.dcMotor.get("Right_Back");

        allDrive[0] = hardwareMap.dcMotor.get("Left_Front");
        allDrive[1] = hardwareMap.dcMotor.get("Left_Back");
        allDrive[2] = hardwareMap.dcMotor.get("Right_Front");
        allDrive[3] = hardwareMap.dcMotor.get("Right_Back");

        //servoGrab = hardwareMap.servo.get("servoGrab");
        servoRelocR =hardwareMap.servo.get("servoRelocR");
        servoRelocL =hardwareMap.servo.get("servoRelocL");

    }
}