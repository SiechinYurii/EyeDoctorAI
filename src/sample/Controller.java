package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Controller {

    @FXML public ImageView imageView;
    @FXML public Button loadImageButton;
    @FXML public Label label1;

    public void buttonAction(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
        ImageFaceDetector imageFaceDetector = context.getBean("imageFaceDetector", ImageFaceDetector.class);
        imageFaceDetector.setController(this);
        imageFaceDetector.detect();
        context.close();
    }

    public void initialize(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        loadImageButton.setOnMouseClicked(event -> buttonAction());
    }

}

