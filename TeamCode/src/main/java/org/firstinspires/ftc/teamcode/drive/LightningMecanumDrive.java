package org.firstinspires.ftc.teamcode.drive;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;
import com.acmerobotics.roadrunner.followers.TrajectoryFollower;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.util.DashboardUtil;
import org.firstinspires.ftc.teamcode.util.LynxModuleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ANG_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MOTOR_VELO_PID;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.RUN_USING_ENCODER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TRACK_WIDTH;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.encoderTicksToInches;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kA;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kStatic;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kV;

public class LightningMecanumDrive extends SampleMecanumDrive {

    //Need to initialise:
    //Wobble goal mechanism/claw
    //Distance sensor
    //TODO:
    //Intake & load motor
    //Shoot motor
    //Right & left triggers: intake, shooter on respectively. Hold. 360 servos. Use 0-90.

    //bruh

    //TODO:  CHANGE DC TO REX MOTORS
    //Degrees per tick
    private double turnDegrees;
    //How fast everything goes
    private double powerLevel;
    private UltrasonicSensor distanceSensor;
    //Feeder feeds the shooter
    private CRServo feeder;
    //Intake intakes stuff
    private DcMotorEx shooter, intake;

    public LightningMecanumDrive(HardwareMap hardwareMap) {
        super(hardwareMap);

        this.powerLevel = 1.0;
        this.turnDegrees = 10;
        //     distanceSensor = hardwareMap.get(UltrasonicSensor.class, "ds");
        //TODO: add DCMotors for them, and then an additional crservo for intake
  ///     feeder = hardwareMap.crservo.get("intake");

//        intake = hardwareMap.get(DcMotorEx.class, "intake");
//        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
//        intake.setDirection(DcMotorSimple.Direction.FORWARD);
//        shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        //Bro am I smart or what
        //AND THE ANSWER IS YES
        //Wheels went in the wrong direction, in case you couldn't tell.
        //Front of the robot is the direction of the shooter
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public double getTurnDegrees() {
        return this.turnDegrees;
    }

    public double getPowerLevel() {
        return this.powerLevel;
    }

    public void setTurnDegrees(int degrees) {
        this.turnDegrees = degrees;
    }

    public void togglePowerLevel() {
        if (getPowerLevel() < 2)
            setPowerLevel(getPowerLevel() + 0.5);
        else setPowerLevel(0.5);
    }

    public void intakeOff() {
        this.intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.intake.setVelocity(0);
        this.intake.setMotorDisable();
    }

    public void powerIntake() {
        this.intake.setMotorEnable();
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setVelocity(10000000);
    }

    public void powerIntake(double power) {
        this.intake.setMotorEnable();
        this.intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intake.setVelocity(power);
    }

    public void shooterOff() {
        this.shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.shooter.setVelocity(0);
        this.shooter.setMotorDisable();
    }

    public void powerShooter() {
        this.shooter.setMotorEnable();
        this.shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter.setVelocity(10000000);
    }


    public void powerShooter(double power) {
        this.shooter.setMotorEnable();
        this.shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.shooter.setVelocity(power);
    }

    public void setPowerLevel(double level) {
        this.powerLevel = level;
    }

    public double getDistanceCm(UltrasonicSensor sensor) {
        return sensor.getUltrasonicLevel();
    }

//    @Override
//    public Double getExternalHeadingVelocity() {
//        // TODO: This must be changed to match your configuration
//        //                           | Z axis
//        //                           |
//        //     (Motor Port Side)     |   / X axis
//        //                       ____|__/____
//        //          Y axis     / *   | /    /|   (IO Side)
//        //          _________ /______|/    //      I2C
//        //                   /___________ //     Digital
//        //                  |____________|/      Analog
//        //
//        //                 (Servo Port Side)
//        //
//        // The positive x axis points toward the USB port(s)
//        //
//        // Adjust the axis rotation rate as necessary
//        // Rotate about the z axis is the default assuming your REV Hub/Control Hub is laying
//        // flat on a surface
//
//        return 0D;
//     //   return (double) imu.getAngularVelocity().zRotationRate;
//    }
}
