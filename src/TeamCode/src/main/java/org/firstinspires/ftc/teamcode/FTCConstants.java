package org.firstinspires.ftc.teamcode;

public class FTCConstants {
    public static final float ONE_BLOCK = -1900 / 2;

    public static final int CM_TO_TICKS(int centimetres) {
        return (int) (centimetres / .03526);
    }

    public static final int DEGREES_TO_TICKS(double degrees) {
        return (int) (degrees * 1200 / 90);
    }
}
