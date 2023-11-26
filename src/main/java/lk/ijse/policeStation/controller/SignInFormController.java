package lk.ijse.policeStation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.policeStation.dto.UserDto;
import lk.ijse.policeStation.model.userModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignInFormController {

    @FXML
    private TextField UsrId;

    @FXML
    private TextField UsrName;

    @FXML
    private TextField UsrPassword;

    @FXML
    void SignInOnAction(ActionEvent event) {
        if (validateInput()) {
            try {
                registerUser();
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while registering the user").show();
            }
        }
    }

    private boolean validateInput() {
        String UserId = UsrId.getText();
        boolean isNameValidated = Pattern.matches("[U][0-9]{3,}",UserId );
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid User Id ! (must need to use same like this pattern [U00+])").show();
            return false;
        }
        String UserName = UsrName.getText();
        boolean isNameeValidated = Pattern.matches("[A-Za-z0-9]{6,}", UserName);
        if (!isNameeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid name !").show();
            return false;
        }

       return true;
    }
    private void registerUser() throws SQLException, ClassNotFoundException, IOException {
        UserDto userDto = new UserDto(UsrName.getText(), UsrPassword.getText(), UsrId.getText());

        if (userModel.registerUser(userDto)) {
            showSuccessAlert("User registered successfully");
            clearFields();
            switchToLoginPage();
        } else {
            showErrorAlert("User registration failed");
        }
    }

    private void showSuccessAlert(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }

    private void showErrorAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void clearFields() {
        UsrName.clear();
        UsrPassword.clear();
        UsrId.clear();
    }

    private void switchToLoginPage() throws IOException {
        Stage stage = (Stage) UsrName.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LogIn_Form .fxml"))));
    }
}
