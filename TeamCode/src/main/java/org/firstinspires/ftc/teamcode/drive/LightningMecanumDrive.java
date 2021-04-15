package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public class  LightningMecanumDrive extends SampleMecanumDrive {


    //OK HERE WE GO kP AND kD HAVE TO HAVE OPPOSITE SIGNS
    //8 -16 -16 or -8 16 16 work well for translational
//    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(0, 0, 0);
//    public static PIDCoefficients HEADING_PID = new PIDCoefficients(0, 0, 0);

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
        feeder = hardwareMap.crservo.get("prime");

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setDirection(DcMotorSimple.Direction.FORWARD);

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

    public void powerFeeder() {
        this.feeder.setPower(0.5);
    }

    public void powerFeeder(float power) {
        this.feeder.setPower(power);
    }

    public void feederOff() {
        this.feeder.setPower(0);
    }

    public double getDistanceCm(UltrasonicSensor sensor) {
        return sensor.getUltrasonicLevel();
    }

    //Based on old code
    /*
    // BASIC DRIVE FUNCTIONS
    private void setDirection(DcMotor[] motors, DcMotorSimple.Direction direction) {
        for (DcMotor motor : motors) {
            motor.setDirection(direction);
        }
    }

    private void setMode(DcMotor[] motors, DcMotor.RunMode mode) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    // set target position in # of ticks
    private void setWheelTargetPosition(DcMotor[] motors, double distance) {
        double targetPosition;
        for (DcMotor motor : motors) {
            targetPosition = Math.round(distance);
            motor.setTargetPosition((int) targetPosition);
        }
    }

    private void setWheelPower(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            /*if (motor.getPower() > power) {
                for (double i = motor.getPower(); i > power; i -= 0.20)
                    motor.setPower(i);
                motor.setPower(power);
            } else if (motor.getPower() < power) {
                for (double i = motor.getPower(); i < power; i += 0.20)
                    motor.setPower(i);
                motor.setPower(power);
            }**/
      /*      motor.setPower(power);
}
    }

private boolean isBusy(DcMotor[]motors){
        for(DcMotor motor:motors){
        if(motor.isBusy()){
        return true;
        }
        }
        return false;
        }

private void setPower(DcMotor[]motors,double power){
        for(DcMotor motor:motors){
        if(motor.getPower()!=power){
        if(motor.getPower()>power){
        for(double i=motor.getPower();i>power;i-=0.20){
        motor.setPower(i);
        }
        }else{
        for(double i=motor.getPower();i<power; i+=0.20){
        motor.setPower(i);
        }
        }
        }

        motor.setPower(power);
        }
        }

public void stopDriving(){
        // stop all motors
        setPower(allDrive,0);

        // resets the motors to normal
        setDirection(leftDrive,DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive,DcMotorSimple.Direction.FORWARD);
        }

public void driveTicks(int ticks,double speed){

        // ensure directions are correct
        setDirection(leftDrive,DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive,DcMotorSimple.Direction.FORWARD);

        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive,ticks);

        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive,speed);

        // unused telemetry
        while(isBusy(allDrive)&&opMode.opModeIsActive()){

        }

        // stopDriving();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

// COMPLEX DRIVE FUNCTIONS
public void driveCm(double distance,double speed){
        // convert cm to ticks
        int ticks=(int)(distance/.03526);

        // ensure directions are correct
        setDirection(leftDrive,DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive,DcMotorSimple.Direction.FORWARD);

        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive,ticks);

        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive,speed);

        // unused telemetry
        while(isBusy(allDrive)&&opMode.opModeIsActive()){

        }

        // stopDriving();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

public void driveBackCm(double distance,double speed){
        // convert cm to ticks
        int ticks=(int)(distance/.03526);

        setDirection(leftDrive,DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive,DcMotorSimple.Direction.REVERSE);

        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive,ticks);

        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);

        // Set drive power
        setPower(allDrive,speed);

        while(isBusy(allDrive)&&opMode.opModeIsActive()){
        //wait until target position in reached
        }
        // stopDriving();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

public void driveLeftCm(double distance,double speed){
        // convert cm to ticks
        int ticks=(int)(distance/.03526);
        leftDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive,ticks);
        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive,speed);

        while(isBusy(allDrive)&&opMode.opModeIsActive()){
        // wait until target position in reached
        // unused telemetry
            /* telemetry.addData("Backwards Power", drivePower);
            telemetry.addData("Backwards Ticks", ticks);
            telemetry.update();
            ticks--;*/
}
// stopDriving();
     /*   setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

public void driveRightCm(double distance,double speed){
        int ticks=(int)(distance/.03526);
        leftDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive,ticks);
        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive,speed);
        while(isBusy(allDrive)&&opMode.opModeIsActive()){
        // wait until target position in reached
        }
        //  stopDriving();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

public void turnLeftTicks(int ticks,double speed){
        setDirection(leftDrive,DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive,DcMotorSimple.Direction.FORWARD);

        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive,ticks);

        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive,speed);


        while(isBusy(allDrive)&&opMode.opModeIsActive()){
        // wait until target position in reached
        }

        telemetry.update();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

public void turnLeft(double degrees,double speed){
        // convert degrees to ticks
        double ticks=FTCConstants.degreesToTicks(degrees);
        telemetry.addData("Ticks: ",ticks);

        setDirection(leftDrive,DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive,DcMotorSimple.Direction.FORWARD);

        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive,ticks);

        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);

        // set drive power
        setPower(allDrive,speed);


        while(isBusy(allDrive)&&opMode.opModeIsActive()){
        // wait until target position in reached
        }

        telemetry.update();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

// used for debugging
    /*
    public void turnLeftTicks(double ticks, double speed) {
        setDirection(leftDrive, DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive, DcMotorSimple.Direction.REVERSE);
        setMode(allDrive, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive, ticks);
        setMode(allDrive, DcMotor.RunMode.RUN_TO_POSITION);
        // set drive power
        setPower(allDrive, speed);
        while (isBusy(allDrive) && opMode.opModeIsActive()) {
            // wait until target position in reached
        }
        stopDriving();
        setMode(allDrive, DcMotor.RunMode.RUN_USING_ENCODER);
    }
    */
/*
public void turnRight(double degrees,double speed){
        // convert degrees to ticks
        double ticks=FTCConstants.degreesToTicks(degrees);

        setDirection(leftDrive,DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive,DcMotorSimple.Direction.REVERSE);

        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setWheelTargetPosition(allDrive,ticks);

        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);
        // set drive power
        setPower(allDrive,speed);


        while(isBusy(allDrive)&&opMode.opModeIsActive()){
        // wait until target position in reached
        }

        //  stopDriving();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }


public void driveContinuousCm(float speed,Predicate<EncoderFunLight> filter){
        // ensure directions are correct
        setDirection(leftDrive,DcMotorSimple.Direction.REVERSE);
        setDirection(rightDrive,DcMotorSimple.Direction.FORWARD);
        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelPower(allDrive,speed);
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(allDrive,speed);

        while(isBusy(allDrive)&&opMode.opModeIsActive()&&!filter.test(this)){
        }
        setPower(allDrive,0);

        }

public void driveContinuousRightCm(float speed,Predicate<EncoderFunLight> filter){
        leftDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[0].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[1].setDirection(DcMotorSimple.Direction.FORWARD);
        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelTargetPosition(allDrive,ticks);
        setMode(allDrive,DcMotor.RunMode.RUN_TO_POSITION);
        setPower(allDrive,speed);
        while(isBusy(allDrive)&&opMode.opModeIsActive()&&!filter.test(this)){
        // wait until target position in reached
        }
        //  stopDriving();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }

public void driveContinuousLeftCm(float speed,Predicate<EncoderFunLight> filter){
        leftDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        leftDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive[0].setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive[1].setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelPower(allDrive,speed);
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(allDrive,speed);

        while(isBusy(allDrive)&&opMode.opModeIsActive()&&!filter.test(this)){
        }
        setPower(allDrive,0);
        }

public void driveBackCm(float speed,Predicate<EncoderFunLight> filter){
        setDirection(leftDrive,DcMotorSimple.Direction.FORWARD);
        setDirection(rightDrive,DcMotorSimple.Direction.REVERSE);
        setMode(allDrive,DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setWheelPower(allDrive,speed);
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(allDrive,speed);

        while(isBusy(allDrive)&&opMode.opModeIsActive()&&!filter.test(this)){
        }
        setPower(allDrive,0);
        }


public void driveTime(int time,double speed)throws InterruptedException{
        int i=time;
        setPower(allDrive,speed);
        while(i>0){
        setPower(allDrive,speed);
        wait(1000);
        i--;
        }
        stopDriving();
        setMode(allDrive,DcMotor.RunMode.RUN_USING_ENCODER);
        }
        **/
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

