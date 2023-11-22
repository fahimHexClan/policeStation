package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.policeStation.dto.DriverDto;
import lk.ijse.policeStation.dto.PoliceReportDto;
import lk.ijse.policeStation.model.DriverModel;
import lk.ijse.policeStation.model.PoliceReportModel;
import lk.ijse.policeStation.model.VehicleModel;
import lk.ijse.policeStation.tm.DriverTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageDriverFormController {

    public TableColumn ClmVehicleId;
    public TableColumn ClmFinesStatus;
    public AnchorPane DAnchor;
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
    private TableView<DriverTm> TableDriver;

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

        String LicenceNumber = TxtLicenseNumber.getText();
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
        ArrayList<String> vehicleIds = VehicleModel.getAllVehicleIds();

        ObservableList<String> vehicleIdList = FXCollections.observableArrayList(vehicleIds);
        CmbVehicleId.setItems(vehicleIdList);

    }



    private void visualize() {
        ClmDriverId.setCellValueFactory(new PropertyValueFactory<>("TxtDriverId"));
        ClmDriverName.setCellValueFactory(new PropertyValueFactory<>("TxtDriverName"));
        ClmAddress.setCellValueFactory(new PropertyValueFactory<>("TxtAddress"));
        ClmContactNumber.setCellValueFactory(new PropertyValueFactory<>("TxtContactNumber"));
        ClmGender.setCellValueFactory(new PropertyValueFactory<>("TxtGender"));
        ClmDob.setCellValueFactory(new PropertyValueFactory<>("TxtDob"));
        ClmLicenseNumber.setCellValueFactory(new PropertyValueFactory<>("TxtLicenseNumber"));
        ClmVehicleId.setCellValueFactory(new PropertyValueFactory<>("CmbVehicleId"));
        ClmFinesStatus.setCellValueFactory(new PropertyValueFactory<>("s"));

    }

    private void setTable() throws SQLException, ClassNotFoundException {

        ArrayList<DriverDto> allDrivers = DriverModel.getAllDrivers();

        ArrayList<DriverTm> driverList = new ArrayList<>();
        for (DriverDto driver : allDrivers) {
            DriverTm driverTm = new DriverTm();

            driverTm.setTxtDriverId(driver.getTxtDriverId());
            driverTm.setTxtDriverName(driver.getTxtDriverName());
            driverTm.setTxtAddress(driver.getTxtAddress());
            driverTm.setTxtContactNumber(driver.getTxtContactNumber());
            driverTm.setTxtGender(driver.getTxtGender());
            driverTm.setTxtDob(driver.getTxtDob());
            driverTm.setTxtLicenseNumber(driver.getTxtLicenseNumber());
            driverTm.setCmbVehicleId(driver.getCmbVehicleId());
            driverTm.setS(driver.getS());
            driverList.add(driverTm);
        }

        ObservableList<DriverTm> driverTms = FXCollections.observableArrayList(driverList);
        TableDriver.setItems(driverTms);

    }


    @FXML
    void BackOnAction(ActionEvent event) throws IOException {
        javafx.scene.Node node = (Node) FXMLLoader.load(getClass().getResource("/view/MangeTraffic_Form.fxml"));
        DAnchor.getChildren().setAll(node);
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
        TxtDriverId.clear();
        TxtDriverName.clear();
        TxtAddress.clear();
        TxtContactNumber.clear();
        TxtGender.clear();
        TxtDob.clear();
        TxtLicenseNumber.clear();
        CmbVehicleId.getSelectionModel().clearSelection();
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        String id = TxtDriverId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete" + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();
        ButtonType pressedButton = null;
        if (buttonType.isPresent()) {
            pressedButton = buttonType.get();
        }
        if (pressedButton.equals(ButtonType.YES)) {
            try {
                boolean isDeleted = DriverModel.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Data Deleted Successfully").show();
                    setTable();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Data Not Deleted ").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.INFORMATION, "Operation Fail ").show();

                e.printStackTrace();
            }
        }
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
    void BtnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String code = TxtDriverId.getText();

        Optional<DriverDto> driver = DriverModel.search(code);

        if (driver.isPresent()) {
            DriverDto driverDto = driver.get();
            setData(driverDto);
        } else {
            new Alert(Alert.AlertType.ERROR, " No Items Found").show();
        }
    }

    private void setData(DriverDto driverDto) {
        TxtDriverId.setText(driverDto.getTxtDriverId());
        TxtDriverName.setText(driverDto.getTxtDriverName());
        TxtAddress.setText(driverDto.getTxtAddress());
        TxtContactNumber.setText(driverDto.getTxtContactNumber());
        TxtGender.setText(driverDto.getTxtGender());
        TxtDob.setText(driverDto.getTxtDob());
        TxtLicenseNumber.setText(driverDto.getTxtLicenseNumber());

        CmbVehicleId.getSelectionModel().select(driverDto.getCmbVehicleId());

    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateDriver()) {
            DriverDto updatedDriver = CollectDriverData();

            try {
                boolean isUpdated = DriverModel.update(updatedDriver);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Data updated successfully").show();
                    // After updating, refresh the table or perform any other necessary actions
                    setTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Data not updated").show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
