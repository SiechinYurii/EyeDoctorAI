package sample;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.opencv.core.*;
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
        frame.setSize(Main.sceneX, Main.sceneY);
        frame.setLocation(Main.sceneX+20, 0);
        frame.setVisible(true);
    }





    public FindContours(Mat src) {
        /// Load source image
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /// Convert image to gray and blur it
        Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(srcGray, srcGray, new Size(3, 3));

        // Create and set up the window.
        frame = new JFrame("Finding contours in your image demo");
        // Set up the content pane.
        Image img = HighGui.toBufferedImage(src);

        //addComponentsToPane
        Container pane = frame.getContentPane();
        JPanel imgPanel = new JPanel();

        Mat blackImg = Mat.zeros(srcGray.size(), CvType.CV_8U);
        imgContoursLabel = new JLabel(new ImageIcon(HighGui.toBufferedImage(blackImg)));
        imgPanel.add(imgContoursLabel);

        pane.add(imgPanel, BorderLayout.CENTER);


        // Display the window.
        frame.pack();
        frame.setSize(Main.sceneX, Main.sceneY);
        frame.setLocation(Main.sceneX+20, 0);
        frame.setVisible(true);
        update();
    }


    private void update() {

        /// Detect edges using Canny
        Mat cannyOutput = new Mat();
        Imgproc.Canny(srcGray, cannyOutput, threshold, threshold * 2);
        /// Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        /// Draw contours
        Mat drawing = Mat.zeros(cannyOutput.size(), CvType.CV_8UC3);
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(256, 256, 256);
            Imgproc.drawContours(drawing, contours, i, color, 2, Imgproc.LINE_8, hierarchy, 0, new Point());
        }

        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(drawing))); // рисовалка
        frame.repaint();
    }
}