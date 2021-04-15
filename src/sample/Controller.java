package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;


public class Controller {

    @FXML public ImageView imageView;
    @FXML public Button loadImageButton;
    @FXML public Label label1;

    public void buttonAction(){
        ImageFaceDetector imageFaceDetector = new ImageFaceDetector(this);
    }

    public void initialize(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        loadImageButton.setOnMouseClicked(event -> buttonAction());
    }

}

