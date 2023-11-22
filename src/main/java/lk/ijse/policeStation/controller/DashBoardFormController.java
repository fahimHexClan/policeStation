package lk.ijse.policeStation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardFormController {
    public AnchorPane anchorDash;
    public AnchorPane MainAnchorPane;

    public void ManageTraffic(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/MangeTraffic_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }


    public void ManageComplaints(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageComplaints_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageCrime(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageCrime_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageEmployees(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageEmployees_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void MangePoliceReport(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManagePoliceReport_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void DashBoard(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/DashBoard_Form.fxml"));
       MainAnchorPane.getChildren().setAll(node);
    }

    public void ManageCitizen(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageCitizen_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void SignOutButtonOnAction(ActionEvent actionEvent) throws IOException {
        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/LogIn_Form .fxml"));
        MainAnchorPane.getChildren().setAll(node);
    }
}
