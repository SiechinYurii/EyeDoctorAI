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
import sun.nio.cs.UTF_32;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class Controller {

    @FXML private ImageView imageView;
    @FXML private Button loadImageButton;
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

            try {
                for (File myFile : new File(tempFolder).listFiles()) { // эта строка кидает warning is=null во время дебага
                    if (myFile.isFile()) myFile.delete();
                }
            }
            finally {

            }


            // choosing File
            try {
                chooseFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // FindCountours


//            String absolutePath = sourceImagePath;
//            String absolutePathUnicode = null;
//            try {
//                absolutePathUnicode = new String(absolutePath.getBytes(StandardCharsets.ISO_8859_1), "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            System.out.println("sourceImagePath: " + sourceImagePath);
//            System.out.println("absolutePath: " + absolutePath);
//            System.out.println("absolutePathUnicode: " + absolutePathUnicode);
//            Mat src = Imgcodecs.imread(absolutePathUnicode); // imagePath


            Mat src = Imgcodecs.imread(imagePath); // imagePath


            if (src.empty()) {
                label1.setText("Неверный формат файла: " + sourceImagePath);
                imageView.setImage(null);
                return;
            }

            label1.setText("Вы выбрали файл: " + sourceImagePath);
            Image image = new Image("file:///" + sourceImagePath);
            imageView.setImage(image);


            ImageFaceDetector IFD = new ImageFaceDetector(imagePath);

        });


    }
}



//ToDO
//Spring. TempClean in final spring method
//Refactoring