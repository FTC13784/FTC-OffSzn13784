package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

public class MathsUtil {

    public static Vector2d fromPose2D(Pose2d pose2d) {
        return new Vector2d(pose2d.getX(), pose2d.getY());
    }
}
