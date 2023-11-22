package lk.ijse.policeStation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MangeTrafficFormController {

    public AnchorPane SubAnchorPane;
    public AnchorPane mgAnchore;

    public void ManageFines(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageFines_Form.fxml"));
        SubAnchorPane.getChildren().setAll(node);
    }

    public void ManageDriver(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageDriverForm.fxml"));
        SubAnchorPane.getChildren().setAll(node);
    }

    public void ManageVehicle(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageVehicle_Form.fxml"));
        SubAnchorPane.getChildren().setAll(node);
    }
}
