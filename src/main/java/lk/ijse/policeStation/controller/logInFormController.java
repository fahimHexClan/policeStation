package lk.ijse.policeStation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.policeStation.DB.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class logInFormController {

    @FXML
    private TextField UsrName;

    @FXML
    private TextField UsrPassword;

    @FXML
    void BtnLogInOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if (UsrName.getText().isBlank() == false && UsrPassword.getText().isBlank() == false) {
            validation();

        }
    }

    private void validation() throws SQLException, ClassNotFoundException, IOException {
        Connection connection= DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(2) FROM User WHERE UserName = ? AND Password = ?";
        PreparedStatement stm=connection.prepareStatement(sql);
        stm.setObject(1,UsrName.getText());
        stm.setObject(2,UsrPassword.getText());
        ResultSet resultSet = stm.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count > 0) {
                // Login successful



                createDashbard();
            } else {
                // Login failed


                new Alert(Alert.AlertType.WARNING, "Try again with the correct username and password").show();
            }
        }

    }

    private void createDashbard() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard_Form.fxml"));
            Object dashboardRoot = loader.load();

            Scene dashboardScene = new Scene((Parent) dashboardRoot);

            Stage stage = (Stage) UsrName.getScene().getWindow();

            stage.setScene(dashboardScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


