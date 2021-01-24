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

import javax.swing.*;
import java.awt.*;
import java.io.*;


import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class Controller {

    @FXML private ImageView imageView;
    @FXML private Button loadImageButton;
    @FXML private Slider slider;
    @FXML public Label label1;


    String imagePath;
    FindContours FC;
    String tempPath = "./src/sample/assets/temp"; // относительный путь
    String fileName;
    String sourcePath = null;

    private void chooseFile() throws IOException {
        fileName = null;
        JFrame fileDialogFrame = new JFrame();
        FileDialog fileDialog = new FileDialog(fileDialogFrame, "Choose a file", FileDialog.LOAD);
        fileDialog.setVisible(true);
        fileName = fileDialog.getFile();
        sourcePath = fileDialog.getDirectory() + fileName;
        if (fileName == null) {
            chooseFile();
        }
        else {
            copyFile();
        }
    }

    public void copyFile() throws IOException {
//        //Удаляем файл, если он уже есть с таким именем
//        File fileExistCheck = new File(tempPath+"/"+fileName);
//        if(fileExistCheck.exists()) {
//            fileExistCheck.delete();
//        }

        File source = new File(sourcePath);
        File dest = new File(tempPath+"/"+fileName);

        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }


    public void initialize(){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        FC = new FindContours();

        loadImageButton.setOnMouseClicked(event -> {
            FC.closeFrame();
            // чистим temp
            for (File myFile : new File(tempPath).listFiles()) { // эта строка кидает warning is=null во время дебага
                if (myFile.isFile()) myFile.delete();
            }

            // выбор файла
            try {
                chooseFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //

            imagePath = tempPath + "/" + fileName;
            Mat src = Imgcodecs.imread(imagePath);

            if (src.empty()) {
                label1.setText("Неверный формат файла: " + sourcePath);
                return;
            }

            label1.setText("Вы выбрали файл: " + sourcePath);
            Image image = new Image("file:///" + sourcePath);
            imageView.setImage(image);
            slider.setValue(100);
            FC = new FindContours(src); //imagePath

        });


        slider.setOnMouseDragged(event -> {
            int sliderValue = (int) slider.getValue();
            FC.setThresholdPlusUpdate(sliderValue);
        });
    }
}



//ToDO

//Set drawing window into main window
//Fix size of photo in drawind window


//Spring. TempClean in final spring method
//Refactoring