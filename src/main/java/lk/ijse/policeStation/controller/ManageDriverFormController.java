package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.policeStation.dto.DriverDto;
import lk.ijse.policeStation.dto.PoliceReportDto;
import lk.ijse.policeStation.model.DriverModel;
import lk.ijse.policeStation.model.PoliceReportModel;
import lk.ijse.policeStation.model.VehicleModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ManageDriverFormController {

    @FXML
    private TableColumn<?, ?> ClmAddress;

    @FXML
    private TableColumn<?, ?> ClmContactNumber;

    @FXML
    private TableColumn<?, ?> ClmDob;

    @FXML
    private TableColumn<?, ?> ClmDriverId;

    @FXML
    private TableColumn<?, ?> ClmDriverName;

    @FXML
    private TableColumn<?, ?> ClmGender;

    @FXML
    private TableColumn<?, ?> ClmLicenseNumber;

    @FXML
    private ComboBox<String> CmbVehicleId;

    @FXML
    private TableView<?> TableDriver;

    @FXML
    private JFXTextField TxtAddress;

    @FXML
    private JFXTextField TxtContactNumber;

    @FXML
    private JFXTextField TxtDob;

    @FXML
    private JFXTextField TxtDriverId;

    @FXML
    private JFXTextField TxtDriverName;

    @FXML
    private JFXTextField TxtGender;

    @FXML
    private JFXTextField TxtLicenseNumber;

    private boolean validateDriver() {

        String DriverId = TxtDriverId.getText();
        boolean isDriverIdValidated = Pattern.matches("[D][0-9]{3,}", DriverId);
        if (!isDriverIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Driver ID! (must need to use same like this pattern [D00+])").show();
            return false;
        }

        String Dob = TxtDob.getText();
        // aniwaren me pattern ekata thiyanna ona 2000-10-08 me wage syntax ekak
        boolean isDobValidated = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", Dob);
        if (!isDobValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth the right pattern is (Year-MM-DD)").show();
            return false;
        }
        String address = TxtAddress.getText();
        boolean isAddressValidated = Pattern.matches("[\\w\\d\\s]{3,}", address);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address must need to add more than 3 letters").show();
            return false;
        }
        String contactNumberText = TxtContactNumber.getText();
        boolean isContactNumberValidated = Pattern.matches("[0-9]{10}", contactNumberText);
        if (!isContactNumberValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee contact number(mustly type 10 numbers)").show();
            return false;
        }

        String genderText = TxtGender.getText();
        boolean isGenderValidated = Pattern.matches("Male|Female", genderText);
        if (!isGenderValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid gender  pls choose this (Male|Female)").show();
            return false;
        }
        String DriverName = TxtDriverName.getText();
        boolean isDriverNameValidated = Pattern.matches("[A-Za-z]{3,}", DriverName);
        if (!isDriverNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Driver name (pls add more then 3 letters)").show();
            return false;
        }

        String LicenceNumber = TxtContactNumber.getText();
        boolean isLicenseNumberValidated = Pattern.matches("[0-9]{10}", LicenceNumber);
        if (!isLicenseNumberValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Licence number(mustly type 10 numbers)").show();
            return false;
        }

        return true;
    }
   public void initialize() throws SQLException, ClassNotFoundException {
       setTable();
       visualize();
       // Assuming there is a method in VehicleModel to get vehicle IDs
       ArrayList<String> vehicleIds = VehicleModel.getAllVehicleIds();

       ObservableList<String> vehicleIdList = FXCollections.observableArrayList(vehicleIds);
       CmbVehicleId.setItems(vehicleIdList);

   }



    private void visualize() {

    }

    private void setTable() {

    }


    @FXML
    void BackOnAction(ActionEvent event) {

    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {

    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (validateDriver()) {
            DriverDto driverDto = CollectDriverData();

            try {
                boolean isSuccess = DriverModel.save(driverDto);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Data added").show();

                    setTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private DriverDto CollectDriverData() {
        DriverDto driverDto = new DriverDto();
        driverDto.setTxtDriverId(TxtDriverId.getText());
        driverDto.setTxtDriverName(TxtDriverName.getText());
        driverDto.setTxtAddress(TxtAddress.getText());
        driverDto.setTxtContactNumber(TxtContactNumber.getText());
        driverDto.setTxtGender(TxtGender.getText());
        driverDto.setTxtDob(TxtDob.getText());
        driverDto.setTxtLicenseNumber(TxtLicenseNumber.getText());
        driverDto.setCmbVehicleId(CmbVehicleId.getValue().toString());

        return driverDto;
    }


    @FXML
    void BtnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {

    }

}
