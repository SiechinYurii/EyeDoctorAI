package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;

import java.io.*;

public class Controller {

    @FXML private ImageView imageView;
    @FXML private Button loadImageButton;
    @FXML public Label label1;



    public void initialize(){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        ImageFaceDetector.cleanCash(); // убрать в завершающий метод Spring

        if (ImageFaceDetector.frame != null){
            ImageFaceDetector.frame.dispose();
        }


        loadImageButton.setOnMouseClicked(event -> {

            ImageFaceDetector imageFaceDetector = new ImageFaceDetector();


            try {
                if( ! imageFaceDetector.chooseFile()){
                    label1.setText("Файл не выбран");
                    imageView.setImage(null);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



            if(imageFaceDetector.imageCheck()){
                label1.setText("Вы выбрали файл: " + ImageFaceDetector.frame.getName());
                Image image = new Image("file:///" + ImageFaceDetector.frame.getName());
                imageView.setImage(image);
            } else{
                label1.setText("Неверный формат файла: " + ImageFaceDetector.frame.getName());
                imageView.setImage(null);
            }


            imageFaceDetector.detect();


        });

    }
}

