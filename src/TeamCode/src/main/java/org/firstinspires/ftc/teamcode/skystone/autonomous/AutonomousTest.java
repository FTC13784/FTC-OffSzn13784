package org.firstinspires.ftc.teamcode.skystone.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.skystone.SkyStoneUtils;

@Autonomous(name = "Autonomous", group = "Linear Opmode")
public class AutonomousTest extends LinearOpMode {
    // start timer
    private ElapsedTime runtime = new ElapsedTime();

    // initialize motor array as well as variables
    DcMotor[] allDrive = new DcMotor[4];

    DcMotor leftFront, leftBack, rightFront, rightBack;
    Servo servoRelocL, servoRelocR;

    @Override
    public void runOpMode() throws InterruptedException {
        SkyStoneUtils.initializeTeleOpHardware(allDrive, leftBack, leftFront, rightBack, rightFront, servoRelocL, servoRelocR, hardwareMap);

        waitForStart();
        runtime.reset();


        // TODO: Get vuforia to work properly
        VuforiaLocalizer vuforia;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // Ed's license key - only 1k uses per month :C
        parameters.vuforiaLicenseKey = "AWWeNcn/////AAABmbzEQcjD+EkFoiSZqjg+u5lx/yGKr1cswTXJyKam9aypsQkEusq7NS661eR2F6TTehJxCMN09Rn7Y7AQtYBYflZ1EexJOBKAHZeqEMal0bsoxZ35GB9T7xlcCW+2nGCIhyhsB01Yxt84BFQzC7COawfvIXgJFS8a7kg5nekjFdY0tPuR9niPUTmklKhc9RqIQVL1KXp6P5ypinxVm3xpAUosB+AEupCvA6NkEbZS4gFAZoytSjBfrvkMHtLPlUJYESEnt8mY7m0CKSj1V3hf8J9o7b8kgFZqqmGgZhvqSgFMaBQiRc7HM/mNqTGIT11dWeqYgWlHTHO7k2xFwgtSzxsD8AHqB2qtCG+uR+A3TxUm";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        // this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);


    }
}
