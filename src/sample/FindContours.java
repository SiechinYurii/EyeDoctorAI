package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.SimpleBlobDetector;
import org.opencv.features2d.SimpleBlobDetector_Params;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

class FindContours {

    private Mat srcGray = new Mat();
    private JFrame frame;
    private JLabel imgContoursLabel;
    private int threshold = 100;
    private Mat srcBinary = new Mat();
    private Mat test = new Mat();

    public void closeFrame(){
        frame.dispose();
    }

    public void setThresholdPlusUpdate(int threshold) {
        this.threshold = threshold;
        update();
    }

    public FindContours(){
        frame = new JFrame("Выберите файл");
        frame.setVisible(false);
    }



    public FindContours(Mat src) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY); // convert image to gray
        Imgproc.threshold(srcGray, srcBinary, 100, 255, Imgproc.THRESH_BINARY);
        //srcBinary = srcGray;
        frame = new JFrame("Finding contours in your image demo"); // create and set up the window.


        // addComponentsToPane
        Container pane = frame.getContentPane();
        JPanel imgPanel = new JPanel();

        //Mat blackImg = Mat.zeros(srcGray.size(), CvType.CV_8U);
        imgContoursLabel = new JLabel(new ImageIcon(HighGui.toBufferedImage(srcBinary)));
        imgPanel.add(imgContoursLabel);

        pane.add(imgPanel, BorderLayout.CENTER);

        // display the window.
        frame.pack();
        frame.setSize(imgContoursLabel.getWidth() + 20,imgContoursLabel.getHeight() + 53);
        frame.setLocation(Main.mainWindowWidth + 1, 0);
        frame.setVisible(true);
        update();
    }


    private void update() {
        SimpleBlobDetector_Params params = new SimpleBlobDetector_Params();
        params.set_filterByArea(true);
        params.set_minArea((float)1);
//        params.set_maxArea(100);
//        params.set_filterByCircularity(true);
//        params.set_minCircularity((float)1);
//        params.set_filterByConvexity(true);
//        params.set_minConvexity((float)0.10);
//        params.set_filterByColor(true);
//        params.set_filterByInertia(true);
//        params.set_minInertiaRatio((float)0.2);
        SimpleBlobDetector simpleBlobDetector = SimpleBlobDetector.create(params);
        MatOfKeyPoint keypoints = new MatOfKeyPoint();



        simpleBlobDetector.detect(srcBinary, keypoints);
        System.out.println(keypoints.size());

        Mat drawing = new Mat();
        Features2d.drawKeypoints(srcBinary,keypoints,drawing);


        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(drawing))); // рисовалка
        frame.repaint();

    }





}