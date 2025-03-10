package lk.ijse.policeStation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Appinitailizer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start( Stage stage) throws Exception {
        Parent rootNode=FXMLLoader.load(this.getClass().getResource("/view/LogIn_Form .fxml"));

        Scene scene = new Scene(rootNode);
        stage.setTitle("Police Management System");
        stage.centerOnScreen();
        stage.setScene(scene);

        stage.show();

    }
}
