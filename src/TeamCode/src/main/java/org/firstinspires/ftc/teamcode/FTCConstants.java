/**
 * Program to contain universal constants and functions.
 *
 * @author FavouriteDragon
 * @contributors Edwardidk
 */

package org.firstinspires.ftc.teamcode;

public class FTCConstants {
    public static final float ONE_BLOCK = -1900 / 2;
    //In centimetres
    public static final double ONE_SQUARE = 59;

    public static final int CM_TO_TICKS(double centimetres) {
        return (int) (centimetres / .03526);
    }

    public static final int DEGREES_TO_TICKS(double degrees) {
        return (int) (degrees * 1200 / 90);
    }
}
