/**
 * Program to contain universal constants and functions.
 *
 * @author FavouriteDragon
 * @contributors Edwardidk
 */

package org.firstinspires.ftc.teamcode;

public class FTCConstants {
    // value for lifter, block height variable
    public static final float ONE_BLOCK = -1900 / 2;

    // in centimetres
    public static final double ONE_SQUARE = 59;
    public static final double ROBOT_WIDTH = 44;

    // TODO: figure out thresholds for blocks
    // thresholds for light sensor
    public static final double BLOCK_THRESHOLD = 0.0;
    public static final double SKYSTONE_THRESHOLD = 0.0;

    public static final int cmToTicks(double centimetres) {
        return (int) (centimetres / .03526);
    }

    public static final int degreesToTicks(double degrees) {
        return (int) (degrees * 1200 / 90);
    }
}
