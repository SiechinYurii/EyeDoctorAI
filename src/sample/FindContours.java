package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

class FindContours {

    private Mat srcGray = new Mat();
    private JFrame frame;
    private JLabel imgContoursLabel;
    private int threshold = 100;

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
        Imgproc.blur(srcGray, srcGray, new Size(3, 3)); // blur it

        frame = new JFrame("Finding contours in your image demo"); // create and set up the window.


        // addComponentsToPane
        Container pane = frame.getContentPane();
        JPanel imgPanel = new JPanel();

        Mat blackImg = Mat.zeros(srcGray.size(), CvType.CV_8U);
        imgContoursLabel = new JLabel(new ImageIcon(HighGui.toBufferedImage(blackImg)));
        imgPanel.add(imgContoursLabel);

        pane.add(imgPanel, BorderLayout.CENTER);


        // display the window.
        frame.pack();
        frame.setSize(imgContoursLabel.getWidth(),imgContoursLabel.getHeight());
        frame.setLocation(Main.mainWindowWidth + 1, 0);
        frame.setVisible(true);
        update();
    }


    private void update() {

        /// detect edges using Canny
        Mat cannyOutput = new Mat();
        Imgproc.Canny(srcGray, cannyOutput, threshold, threshold * 2);

        /// find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        /// draw contours
        Mat drawing = Mat.zeros(cannyOutput.size(), CvType.CV_8UC3);
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(256, 256, 256);
            Imgproc.drawContours(drawing, contours, i, color, 2, Imgproc.LINE_8, hierarchy, 0, new Point());
        }

        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(drawing))); // рисовалка
        frame.repaint();
    }
}