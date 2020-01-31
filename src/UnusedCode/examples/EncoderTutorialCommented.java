/* this doesn't have to do with the code itself. it defines where this java class will be.
it will appear when you create a new java class. */
package org.firstinspires.ftc.teamcode.examples;

/* Created by Maya Shah
Special thanks to Casey for many of the explanations to components. */

/* these are import statements. these are all the import statements you need for this specific opMode.
 * An import statement lets you write code for certain robot components.
 * if you start writing code for a specific component of your robot and it shows errors (such as "cannot resolve symbol")
 * click the red word with the error. if it suggests an import, press alt+enter (or command+1 on a mac)
 * and it will create an import statement */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/* This line declares 3 things: whether the opmode is a teleop or autonomous,
the name that you see on your phone screen when selecting it,
and what group you will find it.
Autonomous1 opModes are when your robot is not controlled by a driver, only by pre-written code.
@Autonomous1 means your opMode is an autonomous. This means it will show up on the phone underneath the Autonomous1 selection
The other option is Teleop, meaning your robot is driver operated.
@Teleop means that your opMode is a Teleop and will show up on the phone underneath the Teleop selection.
Ignore "group = "Linear Opmode"" */
@Autonomous(name = "Encoder Tutorial /w Comments", group = "Linear Opmode")

/* This line (@Disabled) makes the opmode not appear on your phone screen when you go to select it.
 * To enable the opmode so you can use it, comment it out by writing "//" before it. Now it will show up on the phone.
 * If you don't want to see it in opmode selection, simply take out the "//" and it will be disabled again*/
@Disabled

/* This is the start of your new opmode. The name after "public class MUST BE THE SAME AS THE NAME OF THE FILE,
 * but doesn't have to be the same name as the name that shows up on your phone.
 * This will normally fill in automatically when you create a new Java class.
 * Don't worry about the "extends LinearOpMode" but make sure you include it. */
public class EncoderTutorialCommented extends LinearOpMode {

    /* This is a timer. Leave this in here. */
    private ElapsedTime runtime = new ElapsedTime();

    /* This is where you add the motors, servos, and sensors in your code
     * To add another motor, use the same format, just change the name from leftFront or leftBack to something else.
     * Right now, there are four motors declared. They are called leftFront, leftBack, rightFront, and rightBack. */
    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;

    /* This is a variable we are declaring specifically for this encoder opMode, called "ticks"
     * The "int" declares it as an integer variable, meaning it has to be a whole number (no decimals).
     * Ticks will be used later in this opMode as a measurement of distance.
     * Each motor with an encoder has a certain number of ticks per full rotation
     * For example, a Neverest 40 (with a built in encoder) has 1120 ticks per full rotation.
     * A Neverest 60 has 1680 ticks.
     * In this opMode you will be able to input the number of ticks you want your robot to go,
     * and it will go that number of ticks.
     * We will reference ticks later in the opMode.*/
    int ticks = 1120;
    /* The next variable is the diameter of your wheels.
    * Your goal is to be  able to input any distance you want your robot to go, and it will go that far
    * The number of ticks in one wheel revolution and the wheel circumfrence (which you can find given wheel diameter)
    * Will give you the number of ticks per inch so you can tell the robot how far to go.
    * */
    double wheelDiameter = 2;

    /* This is another variable we are declaring specifically for this encoder opMode, called "drivePower".
     * The "double" declares it as a double variable, meaning it can be a whole number or decimals.
     * Drive power will let you choose how fast you want your robot to go when it drives
     * In this opMode you will be able to input the speed you want your robot to go,
     * between -1 and 1 (note that 0 will make the power nothing, so your robot won't move)
     * We will reference drivePower later in the opMode.*/
    double drivePower;

    /* Write in the runOpMode() { and everything in the brackets is what you are telling the robot to do when you press play on your phone
     * Make sure to include the @Override before your runOpMode class, but just ignore it*/
    @Override
    public void runOpMode() {

        /* "telemetry" puts messages on the driver's station phone while the op mode is running.
        * This one will show up when you initialize your robot. It will say "Status: Initialize"*/
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /* Here you are referencing the robot configuration you wrote in your robot controller phone
        the name for the motor in the phone has to match what is in the "quotes"
        the name before the equal (=) sign has to match the name you call it when you declared them above
        */
        leftFront = hardwareMap.dcMotor.get("Left_Front");
        leftBack = hardwareMap.dcMotor.get("Left_Back");
        rightFront = hardwareMap.dcMotor.get("Right_Front");
        rightBack = hardwareMap.dcMotor.get("Right_Back");

        /* Here you are setting the direction of the motors.
        To make a tank drive robot go forward, you have to reverse the left motors
        they are opposite to the right motors, so they will make the wheels move in the opposite direction of the right wheels
        but you want the wheels to move in the same direction
        */
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        /* This is where the program waits until the play button is pressed.
        Keep this in every op mode
        */
        waitForStart();
        runtime.reset();

        /* Remember when we defined the variables ticks drivePower above?
         * Now we are saying what each of the variables is.
         * The first line defines the # of ticks per revolution of the motors.
         * The motors we are using have built-in encoders with 1120 ticks per full revolution, so we say that.
         * If your motors have a different # of ticks, write that number
         * This particular op mode only works if all the wheel motors have the same # of ticks
         * The next line defines the power you want your motors to be
         * A higher power means your robot will go at a faster speed.
         * You can put any value from 0.0–1.0
         * 0 will not make your robot go anywhere.
         */

        drivePower = 1.0;




        /* Here you are setting each wheel motor to use the encoder on it */
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /* Here you are resetting each wheel motor.
         * This is important so that the encoders count up to the correct number and go to the right distance */
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /* Here you are setting the distance you want your robot to go.
        * In this instance, you are setting the distance the robot will go to one full wheel rotation
        * Your robot will go forward the circumfrence of the wheels
        */
        leftBack.setTargetPosition(ticks);
        leftFront.setTargetPosition(ticks);
        rightFront.setTargetPosition(ticks);
        rightBack.setTargetPosition(ticks);

        /* Here you are resetting the tick count on each wheel motor's encoder so they start at 0.
         * This is important so that the encoders count up to the correct number and go to the right distance */
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        leftFront.setPower(drivePower);
        leftBack.setPower(drivePower);
        rightFront.setPower(drivePower);
        rightBack.setPower(drivePower);


        while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy() && opModeIsActive()) {
            //wait until target position in reached
        }

        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }
}
