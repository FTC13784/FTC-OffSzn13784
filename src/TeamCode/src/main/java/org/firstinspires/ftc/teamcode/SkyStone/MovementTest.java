/*

TODO: servo initialize to 0, x --> 1, y --> 0

*/

// team package
package org.firstinspires.ftc.teamcode.SkyStone;

// import statement
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

// set teleop mode
@TeleOp(name = "Motor Test", group = "Linear Opmode")

// make code not appear on phone
@Disabled

// main
public class MovementTest extends LinearOpMode {
    // start timer
    private ElapsedTime runtime = new ElapsedTime();

    // initialize motor array as well as variables
    DcMotor[] allDrive = new DcMotor[4];

    DcMotor leftFrontMotor;
    DcMotor leftBackMotor;
    DcMotor rightFrontMotor;
    DcMotor rightBackMotor;

    // opmode
    @Override
    public void runOpMode() throws InterruptedException {

    }
}
