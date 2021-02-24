package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.io.*;



public class Controller {

    @FXML private ImageView imageView;
    @FXML private Button loadImageButton;
    @FXML private Slider slider;
    @FXML public Label label1;


    FindContours FC;
    String imagePath;
    String fileName;
    String sourceImagePath = null;
    final String tempFolder = "./src/sample/assets/temp"; // относительный путь


    private void chooseFile() throws IOException {
        fileName = null;
        JFrame fileDialogFrame = new JFrame();
        FileDialog fileDialog = new FileDialog(fileDialogFrame, "Choose a file", FileDialog.LOAD);
        fileDialog.setVisible(true);
        fileName = fileDialog.getFile();

        if (fileName == null) {
            chooseFile();
        } else {
            sourceImagePath = fileDialog.getDirectory() + fileName;
            imagePath = tempFolder + "/" + fileName;
            try {
                Cutter.resize(sourceImagePath, imagePath);
            } catch (IOException ex) {
                System.out.println("Error resizing the image");
                ex.printStackTrace();
            }
        }
    }


    public void initialize(){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        FC = new FindContours();

        loadImageButton.setOnMouseClicked(event -> {

            FC.closeFrame();

            // Cleaning temp
            for (File myFile : new File(tempFolder).listFiles()) { // эта строка кидает warning is=null во время дебага
                if (myFile.isFile()) myFile.delete();
            }

            // choosing File
            try {
                chooseFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // FindCountours
            Mat src = Imgcodecs.imread(imagePath);

            if (src.empty()) {
                label1.setText("Неверный формат файла: " + sourceImagePath);
                return;
            }

            label1.setText("Вы выбрали файл: " + sourceImagePath);
            Image image = new Image("file:///" + sourceImagePath);
            imageView.setImage(image);
            slider.setValue(100);
            //FC = new FindContours(src);


            ImageFaceDetector IFD = new ImageFaceDetector(imagePath);

        });

        // slider
        slider.setOnMouseDragged(event -> {
        });
    }
}



//ToDO
//Spring. TempClean in final spring method
//Refactoring