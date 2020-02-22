package org.firstinspires.ftc.teamcode.skystone.autonomous.blue;

/**
 * Bot uses CV to detect skystones and move them across the bridge
 * Bot's arm face should be pointing toward skystones, bot should be aligned next to quarry
 * Back faces toward audience
 *
 * @author Edwardidk
 */

// import packages
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FTCConstants;
import org.firstinspires.ftc.teamcode.skystone.autonomous.EncoderFunLight;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

// autonomous declaration
@Autonomous(group = "NoFSkystone", name = "SkystoneBlueNoF")
public class AutonomousSkystoneBlueNoF extends LinearOpMode {
    // bot initialization
    EncoderFunLight bot;
    private ElapsedTime runTime = new ElapsedTime();

    // CV initialization
    OpenCvCamera phoneCam;
    StageSwitchingPipeline stageSwitchingPipeline;

    // CV viewer placement variable crap
    //0 means skystone, 1 means yellow stone
    //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
    private static int valMid = -1;
    private static int valLeft = -1;
    private static int valRight = -1;

    private static float rectHeight = .6f / 8f;
    private static float rectWidth = 1.5f / 8f;

    private static float offsetX = 0f / 8f; //changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    private static float offsetY = -2.25f / 8f; //changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive

    private static float[] midPos = {4f / 8f + offsetX, 4f / 8f + offsetY};//0 = col, 1 = row
    private static float[] leftPos = {2f / 8f + offsetX, 4f / 8f + offsetY};
    private static float[] rightPos = {6f / 8f + offsetX, 4f / 8f + offsetY};
    //moves all rectangles right or left by amount. units are in ratio to monitor

    private final int rows = 640;
    private final int cols = 480;


    // skystone location variable - FIGURE OUT ORDER
    // TODO: location
    public int skystoneLocations;

    @Override
    public void runOpMode() throws InterruptedException {
        //Use .properties files to dictate what to do in the program
        /**
         * Options:
         * @coloured True or false boolean for whether to go after the special skystones or whether
         * to just get the first couple.
         * @amount The amount of skystones to get. Coloured skystones auto-cap at two
         * @parkWall Boolean for whether to park at the wall (bottom) or at the bridge (top)
         */

        bot = new EncoderFunLight(this);

        // map and initialize hardware
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();

        // initialize pipeline and stream
        stageSwitchingPipeline = new StageSwitchingPipeline();
        phoneCam.setPipeline(stageSwitchingPipeline);
        phoneCam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);

        // wait for play to be pressed
        waitForStart();

        while (opModeIsActive()) {
            updateTelemetryData(telemetry);

            // align against audience wall (TBD)
            // bot.driveLeftCm(FTCConstants.ONE_SQUARE * 1.1, 40);

            // orient in CV location
            // TODO: Find CV location
            // bot.driveCm(FTCConstants.ONE_SQUARE, 40);
            updateTelemetryData(telemetry);

            // test for first three blocks
            if (valLeft == 0) {
                skystoneLocations = 0;
            } else if (valMid == 0) {
                skystoneLocations = 1;
            } else if (valRight == 0) {
                skystoneLocations = 2;
            }

            bot.openClaw();

            switch(skystoneLocations) {
                case 2:

                    break;
                case 1:
                    bot.driveCm(FTCConstants.BLOCK_LENGTH, 40);
                    break;
                case 0:
                    bot.driveCm(FTCConstants.BLOCK_LENGTH * 2, 40);
                    break;
                default:
                    // TODO: copypaste furthest code
            }

            // grab block
            bot.driveLeftCm(FTCConstants.ONE_SQUARE * 1.1, 40);
            bot.closeClaw();

            bot.raiseClaw();

            // code for driving blocks down and crap
            bot.driveRightCm(FTCConstants.ONE_SQUARE * 0.2, 40);
            bot.driveBackCm(FTCConstants.ONE_SQUARE * 1, 40);

            bot.driveLeftCm(FTCConstants.ONE_SQUARE * 0.2, 40);

            bot.lowerClaw();
            bot.openClaw();

            bot.raiseClaw();

            bot.driveRightCm(FTCConstants.ONE_SQUARE * 0.2, 40);
            bot.driveCm(FTCConstants.ONE_SQUARE * 2, 40);


            // code for grabbing second block
            bot.driveLeftCm(FTCConstants.ONE_SQUARE * 0.2, 40);
            bot.closeClaw();

            bot.raiseClaw();


            // drop second block off
            bot.driveRightCm(FTCConstants.ONE_SQUARE * 0.2, 40);
            bot.driveBackCm(FTCConstants.ONE_SQUARE * 2, 40);

            bot.driveLeftCm(FTCConstants.ONE_SQUARE * 0.2, 40);

            bot.lowerClaw();
            bot.openClaw();

            bot.raiseClaw();





            // code for dragging foundation
            // TODO

        }
    }

    public void updateTelemetryData (Telemetry telemetry) {
        telemetry.addData("Values", valLeft + "   " + valMid + "   " + valRight);
        telemetry.addData("Height", rows);
        telemetry.addData("Width", cols);
        telemetry.update();
        sleep(100);
    }

    //detection pipeline
    static class StageSwitchingPipeline extends OpenCvPipeline {
        Mat yCbCrChan2Mat = new Mat();
        Mat thresholdMat = new Mat();
        Mat all = new Mat();
        List<MatOfPoint> contoursList = new ArrayList<>();

        enum Stage {//color difference. greyscale
            detection,//includes outlines
            THRESHOLD,//b&w
            RAW_IMAGE,//displays raw view
        }

        private Stage stageToRenderToViewport = Stage.detection;
        private Stage[] stages = Stage.values();

        @Override
        public void onViewportTapped() {
            /*
             * Note that this method is invoked from the UI thread
             * so whatever we do here, we must do quickly.
             */

            int currentStageNum = stageToRenderToViewport.ordinal();

            int nextStageNum = currentStageNum + 1;

            if (nextStageNum >= stages.length) {
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override
        public Mat processFrame(Mat input) {
            contoursList.clear();
            /*
             * This pipeline finds the contours of yellow blobs such as the Gold Mineral
             * from the Rover Ruckus game.
             */

            //color diff cb.
            //lower cb = more blue = skystone = white
            //higher cb = less blue = yellow stone = grey
            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);//converts rgb to ycrcb
            Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);//takes cb difference and stores

            //b&w
            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 102, 255, Imgproc.THRESH_BINARY_INV);

            //outline/contour
            Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            yCbCrChan2Mat.copyTo(all);//copies mat object
            //Imgproc.drawContours(all, contoursList, -1, new Scalar(255, 0, 0), 3, 8);//draws blue contours


            //get values from frame
            double[] pixMid = thresholdMat.get((int) (input.rows() * midPos[1]), (int) (input.cols() * midPos[0]));//gets value at circle
            valMid = (int) pixMid[0];

            double[] pixLeft = thresholdMat.get((int) (input.rows() * leftPos[1]), (int) (input.cols() * leftPos[0]));//gets value at circle
            valLeft = (int) pixLeft[0];

            double[] pixRight = thresholdMat.get((int) (input.rows() * rightPos[1]), (int) (input.cols() * rightPos[0]));//gets value at circle
            valRight = (int) pixRight[0];

            //create three points
            Point pointMid = new Point((int) (input.cols() * midPos[0]), (int) (input.rows() * midPos[1]));
            Point pointLeft = new Point((int) (input.cols() * leftPos[0]), (int) (input.rows() * leftPos[1]));
            Point pointRight = new Point((int) (input.cols() * rightPos[0]), (int) (input.rows() * rightPos[1]));

            //draw circles on those points
            Imgproc.circle(all, pointMid, 5, new Scalar(255, 0, 0), 1);//draws circle
            Imgproc.circle(all, pointLeft, 5, new Scalar(255, 0, 0), 1);//draws circle
            Imgproc.circle(all, pointRight, 5, new Scalar(255, 0, 0), 1);//draws circle

            //draw 3 rectangles
            Imgproc.rectangle(//1-3
                    all,
                    new Point(
                            input.cols() * (leftPos[0] - rectWidth / 2),
                            input.rows() * (leftPos[1] - rectHeight / 2)),
                    new Point(
                            input.cols() * (leftPos[0] + rectWidth / 2),
                            input.rows() * (leftPos[1] + rectHeight / 2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//3-5
                    all,
                    new Point(
                            input.cols() * (midPos[0] - rectWidth / 2),
                            input.rows() * (midPos[1] - rectHeight / 2)),
                    new Point(
                            input.cols() * (midPos[0] + rectWidth / 2),
                            input.rows() * (midPos[1] + rectHeight / 2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//5-7
                    all,
                    new Point(
                            input.cols() * (rightPos[0] - rectWidth / 2),
                            input.rows() * (rightPos[1] - rectHeight / 2)),
                    new Point(
                            input.cols() * (rightPos[0] + rectWidth / 2),
                            input.rows() * (rightPos[1] + rectHeight / 2)),
                    new Scalar(0, 255, 0), 3);

            switch (stageToRenderToViewport) {
                case THRESHOLD: {
                    return thresholdMat;
                }

                case detection: {
                    return all;
                }

                case RAW_IMAGE: {
                    return input;
                }

                default: {
                    return input;
                }
            }
        }

    }
}