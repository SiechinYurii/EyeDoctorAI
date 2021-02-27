package sample;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileWorker {

    public static String chooseFile() {
        String sourceImagePath;
        String fileName;
        JFrame fileDialogFrame = new JFrame();
        FileDialog fileDialog = new FileDialog(fileDialogFrame, "Выберите файл", FileDialog.LOAD);
        fileDialog.setVisible(true);
        fileName = fileDialog.getFile();
        if (fileName == null) {
            return null;
        } else {
            sourceImagePath = fileDialog.getDirectory() + fileName;
            return sourceImagePath;
        }
    }


    public static void copy(String input, String output) throws IOException {
        File inputFile = new File(input);
        File outputFile = new File(output);

        Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }




//    public static void resize(String inputImagePath, String outputImagePath) throws IOException {
//
//        int scaledHeight;
//        int scaledWidth;
//
//        // reads input image
//        java.io.File inputFile = new java.io.File(inputImagePath);
//        BufferedImage inputImage = ImageIO.read(inputFile);
//
//        //
//
//        if(inputImage.getWidth() < inputImage.getHeight()){     // vertical image
//            int imageHeight = inputImage.getHeight();
//            double coefficient = (double) imageHeight / (double) Main.mainWindowHeight;
//
//            scaledHeight = Main.mainWindowHeight;
//            scaledWidth = (int) Math.round((double) inputImage.getWidth() / coefficient);
//        } else {                                                // horizontal image or squared
//            int imageWidth = inputImage.getWidth();
//            double coefficient = (double) imageWidth / (double) Main.mainWindowWidth;
//
//            scaledWidth = Main.mainWindowWidth;
//            scaledHeight = (int) Math.round((double) inputImage.getHeight() / coefficient);
//        }
//
//
//        // creates output image
//        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
//
//        // scales the input image to the output image
//        Graphics2D g2d = outputImage.createGraphics();
//        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
//        g2d.dispose();
//
//        // extracts extension of output file
//        String formatName = outputImagePath.substring(outputImagePath
//                .lastIndexOf(".") + 1);
//
//        // writes to output file
//        ImageIO.write(outputImage, formatName, new java.io.File(outputImagePath));
//    }

}
