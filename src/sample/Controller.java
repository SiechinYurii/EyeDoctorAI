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




        loadImageButton.setOnMouseClicked(event -> {

            ImageFaceDetector imageFaceDetector = null;
            try {
                imageFaceDetector = new ImageFaceDetector();
            } catch (IOException e) {
                e.printStackTrace();
            }


            String sourceImagePath = imageFaceDetector.getSourceImagePath();
            switch (imageFaceDetector.getErrorMessage()){
                case "Файл не выбран":
                    label1.setText("Файл не выбран");
                    imageView.setImage(null);
                    return;
                case "Невозможно прочитать":
                    label1.setText("Невозможно прочитать: " + sourceImagePath);
                    imageView.setImage(null);
                    return;
                case "Файл готов":
                    label1.setText("Вы выбрали файл: " + sourceImagePath);
                    Image image = new Image("file:///" + sourceImagePath);
                    imageView.setImage(image);
                    break;
                default:

            }


            imageFaceDetector.detect();
            imageView.setImage(imageFaceDetector.getImage());


        });

    }
}

