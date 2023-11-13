package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManageCitizenFormController {

    @FXML
    private TableColumn<?, ?> ColAddress;

    @FXML
    private TableColumn<?, ?> ColCitzenId;

    @FXML
    private TableColumn<?, ?> ColContactNumber;

    @FXML
    private TableColumn<?, ?> ColDob;

    @FXML
    private TableColumn<?, ?> ColGender;

    @FXML
    private TableColumn<?, ?> ColName;

    @FXML
    private Label ContactNumberTxt;

    @FXML
    private TableView<?> TblCitizen;

    @FXML
    private JFXTextField TxtAddcress;

    @FXML
    private JFXTextField TxtCitizenId;

    @FXML
    private JFXTextField TxtContactNumber;

    @FXML
    private JFXTextField TxtDob;

    @FXML
    private JFXTextField TxtGender;

    @FXML
    private JFXTextField Txtname;

    @FXML
    void BtnClearOnAction(ActionEvent event) {

    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {

    }

}
