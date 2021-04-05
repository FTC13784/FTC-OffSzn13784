package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/*
 * Constants shared between multiple drive types.
 *
 * TODO: Tune or adjust the following constants to fit your robot. Note that the non-final
 * fields may also be edited through the dashboard (connect to the robot's WiFi network and
 * navigate to https://192.168.49.1:8080/dash). Make sure to save the values here after you
 * adjust them in the dashboard; **config variable changes don't persist between app restarts**.
 *
 * These are not the only parameters; some are located in the localizer classes, drive base classes,
 * and op modes themselves.
 */
@Config
public class DriveConstants {

    /*
     * These are motor constants that should be listed online for your motors.
     */
    //Input * output for ticks per rev
    public static final double TICKS_PER_REV = 4 * 288;
    public static final double MAX_RPM = 135;

    /*
     * Set RUN_USING_ENCODER to true to enable built-in hub velocity control using drive encoders.
     * Set this flag to false if drive encoders are not present and an alternative localization
     * method is in use (e.g., tracking wheels).
     *
     * If using the built-in motor velocity PID, update MOTOR_VELO_PID with the tuned coefficients
     * from DriveVelocityPIDTuner.
     */
    public static final boolean RUN_USING_ENCODER = true;
    //TODO: Properly customise this
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(0, 0, 0,
            getMotorVelocityF(MAX_RPM / 60 * TICKS_PER_REV));

    /*
     * These are physical constants that can be determined from your robot (including the track
     * width; it will be tune empirically later although a rough estimate is important). Users are
     * free to chose whichever linear distance unit they would like so long as it is consistently
     * used. The default values were selected with inches in mind. Road runner uses radians for
     * angular distances although most angular parameters are wrapped in Math.toRadians() for
     * convenience. Make sure to exclude any gear ratio included in MOTOR_CONFIG from GEAR_RATIO.
     */
    public static double WHEEL_RADIUS = 2; // in
    public static double GEAR_RATIO = 288 / 4F; // output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 1; // in

    /*
     * These are the feedforward parameters used to model the drive motor behavior. If you are using
     * the built-in velocity PID, *these values are fine as is*. However, if you do not have drive
     * motor encoders or have elected not to use them for velocity control, these values should be
     * empirically tuned.
     */
    public static double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0;

    /*
     * These values are used to generate the trajectories for you robot. To ensure proper operation,
     * the constraints should never exceed ~80% of the robot's actual capabilities. While Road
     * Runner is designed to enable faster autonomous motion, it is a good idea for testing to start
     * small and gradually increase them later after everything is working. All distance units are
     * inches.
     */
    public static double MAX_VEL = 30;
    public static double MAX_ACCEL = 30;
    public static double MAX_ANG_VEL = Math.toRadians(60);
    public static double MAX_ANG_ACCEL = Math.toRadians(60);


    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;
    }

    public static double getMotorVelocityF(double ticksPerSecond) {
        // see https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit#heading=h.61g9ixenznbx
        return 32767 / ticksPerSecond;
    }

    //General constants for autonomous.

    public static final Vector2d
            //Start, Stop
            BLUE_START = new Vector2d(-60, 37),
            RED_START = new Vector2d(-60, -37),
            CENTRE = new Vector2d(0, 0),
            BLUE_PARK = new Vector2d(10, 60),
            RED_PARK = new Vector2d(10, -60),

    //Blue Constants
    BLUE_A = new Vector2d(10, 60),
            BLUE_B = new Vector2d(35, 37),
            BLUE_C = new Vector2d(60, 60),
            BLUE_HIGH_GOAL = new Vector2d(-5, 37),
            BLUE_MEDIUM_GOAL = new Vector2d(-5, -37),
            BLUE_SHORT_GOAL = new Vector2d(55, 37),

    //Red Constants
    RED_A = new Vector2d(10, -60),
            RED_B = new Vector2d(37, -37),
            RED_C = new Vector2d(60, -60),
            RED_HIGH_GOAL = new Vector2d(-5, -37),
            RED_MEDIUM_GOAL = new Vector2d(-5, 37),
            RED_SHORT_GOAL = new Vector2d(55, -37);

    public enum ActionType {
        WOBBLE,
        LOW_GOAL,
        MEDIUM_GOAL,
        HIGH_GOAL,
        POWER_SHOT,
        PARK;
    }

    public enum GoalType {
        A,
        B,
        C,
        NONE;
    }

    public enum Side {
        BLUE,
        RED;
    }

    public static final int MAX_INPUT_DELAY = 20;

}
