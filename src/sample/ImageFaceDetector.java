package sample;


import javafx.scene.paint.Paint;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.scene.image.Image;


public class ImageFaceDetector {

    private Mat img;
    private final String sourceImagePath;
    private final static String imagePath = "./src/sample/assets/temp.jpg";
    private final Controller controller;

    public ImageFaceDetector(Controller controller){
        this.controller = controller;
        sourceImagePath = FileWorker.chooseFile();
        if(!fileCheck()){return;}
        detect();
        setProcessedImage();
    }

    public boolean fileCheck(){
        controller.imageView.setImage(null);
        controller.label1.setTextFill(Paint.valueOf("#FF0000"));

        if (sourceImagePath == null){
            controller.label1.setText("Файл не выбран");
            return false;
        }

        try {
            FileWorker.copy(sourceImagePath, imagePath);
        }catch (IOException e){
            controller.label1.setText("Ошибка копирования файла");
            return false;
        }

        img = Imgcodecs.imread(imagePath);
        if (img.empty()){
            controller.label1.setText("Невозможно прочитать файл:" + sourceImagePath);
            return false;
        }


        controller.label1.setTextFill(Paint.valueOf("#00FF00"));
        controller.label1.setText("Вы выбрали файл: " + sourceImagePath);
        Image image = new Image("file:///" + sourceImagePath);
        controller.imageView.setImage(image);
        return true;
    }

    public void detect(){
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

        if(face_rects.size() == 0) {
            System.out.println("face_rects.size() == 0");
            MatOfRect eyes_MOR = new MatOfRect();
            eyeCC.detectMultiScale(img, eyes_MOR);
            List<Rect> eyes_rects = eyes_MOR.toList();
            for (Rect j : eyes_rects) {
                drawRect(j);
            }
        } else {
            Mat face;
            MatOfRect eyes_MOR;
            List<Rect> eyes_rects;

            for (Rect i : face_rects) {
                face = new Mat(img, i);

                eyes_MOR = new MatOfRect();
                eyeCC.detectMultiScale(face, eyes_MOR);
                eyes_rects = eyes_MOR.toList();
                if (eyes_rects.size() == 2) {
                    drawRect(i);
                    for (Rect j : eyes_rects) {
                        j.x += i.x;
                        j.y += i.y;
                        drawRect(j);
                    }
                } else if (eyes_rects.size() > 2) {
                    drawRect(i);

                    int p1 = 0;
                    Rect rect1 = new Rect();
                    int p2 = 0;
                    Rect rect2 = new Rect();
                    for (Rect j : eyes_rects) {
                        int p = j.height + j.width;
                        if (p > p1) {
                            p2 = p1;
                            rect2 = rect1;
                            p1 = p;
                            rect1 = j;
                            rect1.x += i.x;
                            rect1.y += i.y;
                        } else if (p > p2) {
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
        }
    }

    private void drawRect(Rect rect){
        Point p1 = new Point(rect.x, rect.y);
        Point p2 = new Point(rect.x + rect.width, rect.y + rect.height);
        Scalar color = new Scalar(255, 255, 255);

        Imgproc.rectangle(img, p1, p2, color, 4);
    }

    private void setProcessedImage(){
        Imgcodecs.imwrite(imagePath, img);
        Image image = new Image("file:///" + new File(imagePath).getAbsolutePath());
        controller.imageView.setImage(image);
    }

}
