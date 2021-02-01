package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static final int mainWindowWidth = 900;
    public static final int mainWindowHeight = 900;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Поиск контуров картинки");
        primaryStage.setScene(new Scene(root, mainWindowWidth, mainWindowHeight));
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

}
