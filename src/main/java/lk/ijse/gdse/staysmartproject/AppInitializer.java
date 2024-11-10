package lk.ijse.gdse.staysmartproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/WelcomePage.fxml"));

        stage.setTitle("Stay Smart");

        Scene scene = new Scene(new Group(load));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/software-icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}