package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardFormController {
    public AnchorPane anchorDash;
    public AnchorPane MainAnchorPane;
    public JFXButton btnComplaints;
    public JFXButton btnPoliceReport;
    public JFXButton btnCitizen;
    public JFXButton btnDash;
    public JFXButton btnSignOut;
    public JFXButton btnEmployee;
    public JFXButton btnCrime;
    public JFXButton btnTraffic;


    @FXML
    void ManageTraffic(ActionEvent event) throws IOException {
        resetButtonStyles();
        btnTraffic.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/MangeTraffic_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }


    public void ManageComplaints(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnComplaints.setStyle("-fx-background-color:#00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageComplaints_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageCrime(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnCrime.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageCrime_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageEmployees(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnEmployee.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageEmployees_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void MangePoliceReport(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnPoliceReport.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");


        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManagePoliceReport_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageCitizen(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnCitizen.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageCitizen_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void SignOutButtonOnAction(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnSignOut.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/LogIn_Form .fxml"));
        MainAnchorPane.getChildren().setAll(node);
    }

    private void resetButtonStyles() {
        btnComplaints.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnPoliceReport.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnCitizen.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnDash.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnSignOut.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnCrime.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnTraffic.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
    }

    public void DashBoardOnAction(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnDash.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/DashBoard_Form.fxml"));
        MainAnchorPane.getChildren().setAll(node);

    }
}
