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
    Mat img;
    JFrame frame;

    ImageFaceDetector(String imagePath){

        if (frame != null){
            frame.dispose();
        }
        img = Imgcodecs.imread(imagePath);
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);

        CascadeClassifier faceCC = new CascadeClassifier();
        faceCC.load("C:\\Program Files (x86)\\Common Files\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml");
        CascadeClassifier eyeCC = new CascadeClassifier();
        eyeCC.load("C:\\Program Files (x86)\\Common Files\\opencv\\build\\etc\\haarcascades\\haarcascade_eye.xml");



        MatOfRect face_MOR = new MatOfRect();
        faceCC.detectMultiScale(img, face_MOR);
        List<Rect> face_rects = face_MOR.toList();

        Mat face;
        MatOfRect eyes_MOR;
        List<Rect> eyes_rects;


        for (Rect i: face_rects) {
            face = new Mat(img, i);

            eyes_MOR = new MatOfRect();
            eyeCC.detectMultiScale(face, eyes_MOR);
            eyes_rects = eyes_MOR.toList();
                if(eyes_rects.size() == 2) {
                    drawRect(i);
                    for (Rect j : eyes_rects) {
                        j.x += i.x;
                        j.y += i.y;
                        drawRect(j);
                    }
                }else if (eyes_rects.size() > 2){
                    drawRect(i);

                    int p1 = 0;
                    Rect rect1 = new Rect();
                    int p2 = 0;
                    Rect rect2 = new Rect();
                    for (Rect j : eyes_rects) {
                        int p = j.height+j.width;
                        if(p > p1){
                            p2 = p1;
                            rect2 = rect1;
                            p1 = p;
                            rect1 = j;

                            rect1.x += i.x;
                            rect1.y += i.y;
                        } else if(p > p2){
                            p2 = p;
                            rect2 = j;
                            rect2.x += i.x;
                            rect2.y += i.y;
                        }
                    }
                    drawRect(rect1);
                    drawRect(rect2);
                }
            System.out.println();
        }

        createWindow(img);

    }


    void drawRect(Rect rect){
        Point p1 = new Point(rect.x, rect.y);
        Point p2 = new Point(rect.x + rect.width, rect.y + rect.height);
        Scalar color = new Scalar(255, 255, 255);

        Imgproc.rectangle(img, p1, p2, color, 2);
    }


    void createWindow(Mat img){

        frame = new JFrame("."); // create and set up the window.

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
