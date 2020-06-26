package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane p = fxmlLoader.load(getClass().getResource("sample.fxml").openStream());
        Controller controller = fxmlLoader.getController();
        controller.start();
        primaryStage.setTitle("Lab3");
        primaryStage.setScene(new Scene(p, 1100, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
