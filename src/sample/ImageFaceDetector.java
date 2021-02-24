package sample;


import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ImageFaceDetector {

    ImageFaceDetector(String imagePath){

        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml');
        //eye_cascade = cv2.CascadeClassifier('haarcascade_eye.xml');
        //FXMLLoader face_cascade = new FXMLLoader(getClass().getResource("C:\\Program Files (x86)\\Common Files\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml"));
        //FXMLLoader eye_cascade = new FXMLLoader(getClass().getResource("C:\\Program Files (x86)\\Common Files\\opencv\\build\\etc\\haarcascades\\haarcascade_eye.xml"));


        Mat img = Imgcodecs.imread(imagePath);
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);


        CascadeClassifier faceCC = new CascadeClassifier();
        CascadeClassifier eyeCC = new CascadeClassifier();
        faceCC.load("C:\\Program Files (x86)\\Common Files\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml");
        eyeCC.load("C:\\Program Files (x86)\\Common Files\\opencv\\build\\etc\\haarcascades\\haarcascade_eye.xml");

        MatOfRect MOR_face = new MatOfRect();
        faceCC.detectMultiScale(img, MOR_face);
        List<Rect> rects = MOR_face.toList();

        Point p1 = new Point();
        Point p2 = new Point();
        Scalar color = new Scalar(255, 255, 255);
        Rect rect = new Rect();
        Mat eye = new Mat(img, rect);

        System.out.println(imagePath);
        for (Rect i: rects) {
            p1.x = i.x;
            p1.y = i.y;
            p2.x = i.x + i.width;
            p2.y = i.y + i.height;
            Imgproc.rectangle(img, p1, p2, color, 2);
        }
        System.out.println();



        //Imgproc.rectangle(imgGray, p1, p2, color, 2);
        createWindow(img);

    }


    void createWindow(Mat img){

        JFrame frame = new JFrame("Finding contours in your image demo"); // create and set up the window.

        // addComponentsToPane
        Container pane = frame.getContentPane();
        JPanel imgPanel = new JPanel();

        //Mat blackImg = Mat.zeros(srcGray.size(), CvType.CV_8U);
        JLabel imgContoursLabel = new JLabel(new ImageIcon(HighGui.toBufferedImage(img)));
        imgPanel.add(imgContoursLabel);

        pane.add(imgPanel, BorderLayout.CENTER);

        // display the window.
        frame.pack();
        frame.setSize(imgContoursLabel.getWidth() + 20,imgContoursLabel.getHeight() + 53);
        frame.setLocation(Main.mainWindowWidth + 1, 0);
        frame.setVisible(true);



        imgContoursLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(img))); // рисовалка
        frame.repaint();
    }

}
