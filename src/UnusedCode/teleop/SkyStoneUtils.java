package org.firstinspires.ftc.teamcode.skystone.teleop.unused;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class SkyStoneUtils {
    public static void initializeTeleOpHardware(DcMotor[] motors, DcMotor leftBack, DcMotor leftFront,
                                                DcMotor rightBack, DcMotor rightFront, Servo servoRelocL,
                                                Servo servoRelocR, HardwareMap map) {
        leftBack = map.dcMotor.get("Left_Front");
        leftFront = map.dcMotor.get("Left_Back");
        rightBack = map.dcMotor.get("Right_Front");
        rightFront = map.dcMotor.get("Right_Back");

        servoRelocR = map.servo.get("servoRelocR");
        servoRelocL = map.servo.get("servoRelocL");

        motors[0] = map.dcMotor.get("Left_Front");
        motors[1] = map.dcMotor.get("Left_Back");
        motors[2] = map.dcMotor.get("Right_Front");
        motors[3] = map.dcMotor.get("Right_Back");
    }

    // Currently the y controls are inverse if you make the left stick y negative.
    public static float[] handleGamepadControls(Gamepad pad, DcMotor leftBack, DcMotor leftFront,
                                                DcMotor rightBack, DcMotor rightFront,
                                                Servo servoRelocL, Servo servoRelocR) {
        if (Math.abs(pad.left_stick_y) < 0.1) {
            pad.left_stick_y = 0;
        }
        if (Math.abs(pad.right_stick_y) < 0.1) {
            pad.right_stick_y = 0;
        }

        float gamepad1LeftX = pad.left_stick_x;
        float gamepad1LeftY = pad.left_stick_y;
        float gamepad1RightX = pad.right_stick_x;

        boolean gamepad1x = pad.x;
        boolean gamepad1y = pad.y;

        // holonomic formulas
        float FrontLeftPower = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackLeftPower = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        float FrontRightPower = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackRightPower = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;


        // TODO: fix this after Robert edits code
        float servoRelocLPos;
        float servoRelocRPos;

        if (gamepad1x){
            servoRelocLPos = 1;
            servoRelocRPos = 0;
        }

        if (gamepad1y){
            servoRelocLPos = 0;
            servoRelocRPos = 1;
        }

        // clip the right/left values so that the values never exceed +/- 1
        FrontLeftPower = Range.clip(FrontLeftPower, -1, 1);
        BackLeftPower = Range.clip(BackLeftPower, -1, 1);
        FrontRightPower = Range.clip(FrontRightPower, -1, 1);
        BackRightPower = Range.clip(BackRightPower, -1, 1);

        // write the values to the motors
        leftFront.setPower(FrontLeftPower);
        leftBack.setPower(BackLeftPower);
        rightFront.setPower(FrontRightPower);
        rightBack.setPower(BackRightPower);

        // TODO: uncomment after Robert fix
        // servoRelocL.setPosition(Range.clip(servoRelocLPos, 0, 1));
        // servoRelocR.setPosition(Range.clip(servoRelocRPos, 0, 1));

        float[] powers = {FrontLeftPower, FrontLeftPower, BackLeftPower, BackRightPower};
        return powers;
    }
}
