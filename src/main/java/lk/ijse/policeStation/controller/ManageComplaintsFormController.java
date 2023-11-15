package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ManageComplaintsFormController {

    @FXML
    private JFXTextField TxtCitizenId;

    @FXML
    private JFXTextField TxtComplainId;

    @FXML
    private JFXTextField TxtDate;

    @FXML
    private JFXTextField TxtDescriptionOfIncident;

    @FXML
    private JFXTextField TxtEvidence;

    @FXML
    private JFXTextField TxtLocationOfIncident;

    @FXML
    private JFXTextField TxtOfficerId;

    @FXML
    private JFXTextField TxtStatusOfTheComplaint;

    @FXML
    private JFXTextField TxtSuspectAddress;

    @FXML
    private JFXTextField TxtSuspectContactNumber;

    @FXML
    private JFXTextField TxtSuspectName;

    @FXML
    private JFXTextField TxtTypeOfIncident;

    @FXML
    private JFXTextField TxtWitnessInformation;

    @FXML
    void BtnClearOnAction(ActionEvent event) {
    TxtCitizenId.clear();
    TxtComplainId.clear();
    TxtDescriptionOfIncident.clear();
    TxtDate.clear();
    TxtOfficerId.clear();
    TxtTypeOfIncident.clear();
    TxtLocationOfIncident.clear();
    TxtEvidence.clear();
    TxtWitnessInformation.clear();
    TxtSuspectName.clear();
    TxtSuspectAddress.clear();
    TxtSuspectContactNumber.clear();
    TxtStatusOfTheComplaint.clear();
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void BtnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {

    }

}
