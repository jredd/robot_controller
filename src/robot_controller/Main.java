package robot_controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Scene scene;

    @Override
    public void start(Stage primary_stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("robot_controller_ui.fxml"));
        primary_stage.setTitle("Robot Remote");
        scene = new Scene(root, 700, 900);
        primary_stage.setScene(scene);
        primary_stage.setResizable(false);

        String css = Main.class.getResource("styles/main.css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        primary_stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
