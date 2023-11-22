package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.policeStation.dto.CrimeDto;
import lk.ijse.policeStation.dto.VehicleDto;
import lk.ijse.policeStation.model.CrimeModel;
import lk.ijse.policeStation.model.VehicleModel;
import lk.ijse.policeStation.tm.VehicleTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageVehicleFormController {

    public AnchorPane SubAnchor;
    @FXML
    private TableColumn<?, ?> CLmEngineNum;

    @FXML
    private TableColumn<?, ?> ClmModel;

    @FXML
    private TableColumn<?, ?> ClmOwnerId;

    @FXML
    private TableColumn<?, ?> ClmPlateNumber;

    @FXML
    private TableColumn<?, ?> ClmVehicleId;

    @FXML
    private TableColumn<?, ?> ClmVehicleType;

    @FXML
    private TableView<VehicleTm> TableVehicleDetails;

    @FXML
    private JFXTextField TxtEngineNum;

    @FXML
    private JFXTextField TxtModelId;

    @FXML
    private JFXTextField TxtOwnerId;

    @FXML
    private JFXTextField TxtPlateNum;

    @FXML
    private JFXTextField TxtVehicleId;

    @FXML
    private JFXTextField TxtVehicleType;

    public void initialize() {
        setTable();
        visualize();

    }

    private void visualize() {
        ClmVehicleId.setCellValueFactory(new PropertyValueFactory<>("TxtVehicleId"));
        CLmEngineNum.setCellValueFactory(new PropertyValueFactory<>("TxtEngineNum"));
        ClmOwnerId.setCellValueFactory(new PropertyValueFactory<>("TxtOwnerId"));
        ClmPlateNumber.setCellValueFactory(new PropertyValueFactory<>("TxtPlateNum"));
        ClmVehicleType.setCellValueFactory(new PropertyValueFactory<>("TxtVehicleType"));
        ClmModel.setCellValueFactory(new PropertyValueFactory<>("TxtModelId"));

    }

    private boolean validateVehicle() {
        String VehicleId = TxtVehicleId.getText();
        boolean isVehicleIdValidated = Pattern.matches("[V][0-9]{3,}", VehicleId);
        if (!isVehicleIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Vehicle ID! (must need to use same like this pattern [V00+])").show();
            return false;
        }


        String EngineNO = TxtEngineNum.getText();
        boolean isEngineNumValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", EngineNO);
        if (!isEngineNumValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Engine Number Mustly add more than 3 letters or numbers").show();
            return false;
        }

        String Ownerid = TxtOwnerId.getText();
        boolean isOwnerIdValidated = Pattern.matches("[W][0-9]{3,}", Ownerid);
        if (!isOwnerIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Owner ID! (must need to use same like this pattern [W00+])").show();
            return false;
        }

        String NumPlate = TxtPlateNum.getText();
        boolean isNumPlateValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", NumPlate);
        if (!isNumPlateValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid NumPlate").show();
            return false;
        }
        String vType = TxtVehicleType.getText();
        boolean isVTypeValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", vType);
        if (!isVTypeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid VehicleTypes").show();
            return false;
        }

        String Model = TxtModelId.getText();
        boolean isModelValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", Model);
        if (!isModelValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Model").show();
            return false;
        }


        return true;
    }
    @FXML
    void BackOnAction(ActionEvent event) throws IOException {
        javafx.scene.Node node = (Node) FXMLLoader.load(getClass().getResource("/view/MangeTraffic_Form.fxml"));
        SubAnchor.getChildren().setAll(node);
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
        TxtVehicleId.clear();
        TxtEngineNum.clear();
        TxtOwnerId.clear();
        TxtPlateNum.clear();
        TxtVehicleType.clear();
        TxtModelId.clear();
        TableVehicleDetails.getSelectionModel().clearSelection();
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = TxtVehicleId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete" + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();
        ButtonType pressedButton = null;
        if (buttonType.isPresent()) {
            pressedButton = buttonType.get();
        }
        if (pressedButton.equals(ButtonType.YES)) {
            boolean isDeleted = VehicleModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Data Deleted Successfully").show();
                setTable();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Data Not Deleted ").show();
            }
        }
    }



    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (validateVehicle()) {
            VehicleDto vehicleDto = collectVehicleData();

            try {
                boolean isSuccess = VehicleModel.save(vehicleDto);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Data added").show();
                    BtnClearOnAction(null);
                    setTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while saving data").show();
            }
        }
    }

    private VehicleDto collectVehicleData() {
        String VehicleId = TxtVehicleId.getText();
        String EngineNumber = TxtEngineNum.getText();
        String Owner = TxtOwnerId.getText();
        String VehicleNumPlate = TxtPlateNum.getText();
        String VehicleType = TxtVehicleType.getText();
        String Model = TxtModelId.getText();


        VehicleDto vehicleDto = new VehicleDto();

        vehicleDto.setTxtVehicleId(VehicleId);
        vehicleDto.setTxtEngineNum(EngineNumber);
        vehicleDto.setTxtOwnerId(Owner);
        vehicleDto.setTxtPlateNum(VehicleNumPlate);
        vehicleDto.setTxtVehicleType(VehicleType);
        vehicleDto.setTxtModelId(Model);

        return vehicleDto;
    }

    private void setTable() {
        try {

            ArrayList<VehicleDto> allVehicles = VehicleModel.getAllVehicle();

            ArrayList<VehicleTm> vehicle = new ArrayList<>();

            for (VehicleDto vehicleDto : allVehicles) {
                VehicleTm vehicleTm = new VehicleTm();

                vehicleTm.setTxtVehicleId(vehicleDto.getTxtVehicleId());
                vehicleTm.setTxtEngineNum(vehicleDto.getTxtEngineNum());
                vehicleTm.setTxtOwnerId(vehicleDto.getTxtOwnerId());
                vehicleTm.setTxtPlateNum(vehicleDto.getTxtPlateNum());
                vehicleTm.setTxtVehicleType(vehicleDto.getTxtVehicleType());
                vehicleTm.setTxtModelId(vehicleDto.getTxtModelId());

                vehicle.add(vehicleTm);

            }

            ObservableList<VehicleTm> vehicleTms = FXCollections.observableArrayList(vehicle);

            TableVehicleDetails.setItems(vehicleTms);



        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String code = TxtVehicleId.getText();

        try {
            Optional<VehicleDto> vehicleDto = VehicleModel.search(code);

            if (vehicleDto.isPresent()) {
                VehicleDto vehicleDto1 = vehicleDto.get();
                setData(vehicleDto1);
            } else {
                new Alert(Alert.AlertType.ERROR, " No Items Found").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something Wrong").show();
            e.printStackTrace();
        }
    }

    private void setData(VehicleDto vehicleDto1) {
        TxtVehicleId.setText(vehicleDto1.getTxtVehicleId());
        TxtEngineNum.setText(vehicleDto1.getTxtEngineNum());
        TxtOwnerId.setText(vehicleDto1.getTxtOwnerId());
        TxtPlateNum.setText(vehicleDto1.getTxtPlateNum());
        TxtVehicleType.setText(vehicleDto1.getTxtVehicleType());
        TxtModelId.setText(vehicleDto1.getTxtModelId());

    }


    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateVehicle()) {
            VehicleDto updatedVehicleDto = collectVehicleData();

            try {
                boolean isUpdated = VehicleModel.update(updatedVehicleDto);

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
