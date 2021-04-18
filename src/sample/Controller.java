package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Controller {

    @FXML public ImageView imageView;
    @FXML public Button loadImageButton;
    @FXML public Label label1;

    AnnotationConfigApplicationContext context;
    ImageFaceDetector imageFaceDetector;

    public void buttonAction(){
        imageFaceDetector.detect();
    }

    public void initialize(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        imageFaceDetector = context.getBean("imageFaceDetectorBean", ImageFaceDetector.class);
        context.close();

        imageFaceDetector.setController(this);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        loadImageButton.setOnMouseClicked(event -> buttonAction());
    }

}

