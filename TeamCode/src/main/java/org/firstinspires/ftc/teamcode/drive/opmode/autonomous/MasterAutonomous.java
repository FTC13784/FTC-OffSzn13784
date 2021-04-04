    package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;

//@Autonomous(group = "autonomous", name = "master")
public abstract class MasterAutonomous extends LinearOpMode {

    AutonomousDriver driver;

    //TODO: Possibly make action type an array?
    //Nah you park anyway who cares


    @Override
    public void waitForStart() {
        super.waitForStart();
        driver = new AutonomousDriver(getSide(), getGoal(), getAction(),
                getDrive(), hardwareMap, getDriveType(), getMode());
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialises
        waitForStart();
        if (isStopRequested()) {
            return;
        }
        driver.start();
        driver.update();


    }


  /*  @Override
    public void init() {
        driver = new AutonomousDriver(getSide(), getGoal(), getAction(),
                getDrive(), hardwareMap, getDriveType(), getMode());

    }

    @Override
    public void start() {
        super.start();
        driver.start();
    }

    @Override
    public void loop() {
        driver.update();
    }
**/
    /**
     * @return The side to return. Blue or Red. Pretty easy to understand.
     */
    public abstract DriveConstants.Side getSide();

    /**
     * @return The goal type. A, B, C, or none.
     * If you're shooting, just return ActionType.NONE.
     */
    public abstract DriveConstants.GoalType getGoal();

    /**
     * @return The action type. Wobble, HighGoal, PowerShot, e.t.c.
     */
    public abstract DriveConstants.ActionType getAction();

    /**
     * @return The Drive class to be used in the AutonomousDriver. Return anyone you want, but make sure its a child
     * of SampleMecanumDrive. You can also return SampleMecanumDrive itself.
     * Example usage: return new SampleMecanumDrive(hardwaremap);
     */
    public abstract Drive getDrive();

    /**
     * @return Return the drive type of the AutonomousDriver: Mecanum or Tank. Use Mecanum. For the love of god.
     */
    public abstract AutonomousDriver.DriveType getDriveType();

    /**
     * @return The OpMode being passed to the AutonomousDriver. Just return the class itself/this.
     */
    public abstract OpMode getMode();
}
