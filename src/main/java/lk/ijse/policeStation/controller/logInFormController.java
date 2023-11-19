package lk.ijse.policeStation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.policeStation.dto.UserDto;
import lk.ijse.policeStation.model.userModel;

import java.io.IOException;
import java.sql.SQLException;

public class logInFormController {

    @FXML
    private TextField UsrId;

    @FXML
    private TextField UsrName;

    @FXML
    private TextField UsrPassword;

    @FXML
    void BtnLogInOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if (!UsrName.getText().isBlank() && !UsrPassword.getText().isBlank()&& ! UsrId.getText().isBlank()) {
            validation();
        }
    }

    private void validation() throws SQLException, ClassNotFoundException {
        UserDto userDto = new UserDto(UsrName.getText(), UsrPassword.getText(), UsrId.getText());

        if (userModel.validateUser(userDto)) {
            // Login successful
            createDashboard();
        } else {
            // Login failed
            new Alert(Alert.AlertType.WARNING, "Try again with the correct username and password").show();
            UsrPassword.clear();
            UsrName.clear();
            UsrId.clear();  // Clear user ID as well
        }
    }


    private void createDashboard() {
        try {
            // Load the Dashboard Form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard_Form.fxml"));
            Object dashboardRoot = loader.load();

            // Set the Dashboard Scene
            setScene(dashboardRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setScene(Object root) {
        // Set the scene for the current window
        getStage().getScene().setRoot((javafx.scene.Parent) root);
    }

    private javafx.stage.Stage getStage() {
        return (javafx.stage.Stage) UsrName.getScene().getWindow();
    }
}
