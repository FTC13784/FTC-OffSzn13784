package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

@TeleOp(name = "Kiddie Code")
public class KiddieCode extends LinearOpMode {

    //Setting up hardware
    private DcMotor RF;
    private DcMotor LF;
    private DcMotor RB;
    private DcMotor LB;
    private DcMotor Lift;
    private Servo RightArm;
    private Servo LeftArm;

    //everything for the hardware map
    public void mapHardware(){
        RF = hardwareMap.get(DcMotor.class, "RF");
        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB") ;
        LB = hardwareMap.get(DcMotor.class, "LB");
        Lift = hardwareMap.get(DcMotor.class, "Lift");
        RightArm = hardwareMap.get(Servo.class, "RA");
        LeftArm = hardwareMap.get(Servo.class, "LA");

        RF.setDirection(DcMotor.Direction.REVERSE);
        LF.setDirection(DcMotor.Direction.FORWARD);
        RB.setDirection(DcMotor.Direction.REVERSE);
        LB.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void runOpMode(){
        //starts the hardware
        mapHardware();

        //power for the tank drive
        double leftPower;
        double rightPower;

        //initializes the code
        waitForStart();

        while(opModeIsActive()){
            //sets the power to the joystick
            leftPower = gamepad1.left_stick_y;
            rightPower = gamepad1.right_stick_y;

            //sends that power to the motors
            LF.setPower(leftPower / 2);
            LB.setPower(leftPower / 2);
            RF.setPower(rightPower / 2);
            RB.setPower(rightPower / 2);

            //lift code
            if(gamepad1.a) {
                Lift.setPower(0.25);
            } else if(gamepad1.y){
                Lift.setPower(-0.25);
            } else {
                Lift.setPower(0);
            }

            if(gamepad1.left_trigger > 0.5){
                LeftArm.setDirection(Servo.Direction.FORWARD);
                LeftArm.setPosition(0.25);
            } else if(gamepad1.right_trigger > 0.5){
                RightArm.setDirection(Servo.Direction.FORWARD);
                RightArm.setPosition(0.25);
            }
            else if (gamepad1.left_bumper) {
                LeftArm.setDirection(Servo.Direction.REVERSE);
                LeftArm.setPosition(0.25);
            }else if (gamepad1.right_bumper) {
                RightArm.setDirection(Servo.Direction.REVERSE);
                RightArm.setPosition(0.25);
            }

//            RightArm.setPosition(.5);
//            LeftArm.setPosition(.5);

        }

    }

}