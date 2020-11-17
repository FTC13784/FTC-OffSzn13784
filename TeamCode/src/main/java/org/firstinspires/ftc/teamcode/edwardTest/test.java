package org.firstinspires.ftc.teamcode.edwardTest;

// import packages
import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.Encoder;

// TeleOp declaration
// @TeleOp(name = "Test Program", group = "test")

// disable telemetry
// @Disabled

public class test extends LinearOpMode {
    // declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    // run OpMode
    @Override
    public void runOpMode() {

        // initialization telemetry
        telemetry.addData("Status", "Initializing");
        telemetry.update();


        // initialization finished
        telemetry.addData("Status", "Initialization finished");
        telemetry.update();


        // initialization of hardware variables - REMEMBER TO CHANGE CONFIGURATION ON PHONES AS WELL
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");


        runtime.reset();


    }
}
