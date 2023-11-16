package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.policeStation.dto.ComplaintDto;
import lk.ijse.policeStation.model.ComplaintModel;

import java.sql.SQLException;
import java.util.Optional;

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
        String id = TxtComplainId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete" + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();
        ButtonType pressedButton = null;
        if (buttonType.isPresent()) {
            pressedButton = buttonType.get();
        }
        if (pressedButton.equals(ButtonType.YES)) {
            try {
                boolean isDeleted= ComplaintModel.delete(id);
                if (isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Data Deleted Successfully").show();
                }else {
                    new Alert(Alert.AlertType.INFORMATION,"Data Not Deleted ").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.INFORMATION,"Operation Fail ").show();

                e.printStackTrace();
            }
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        ComplaintDto complaintDto =CollectComplaintData();

        try {
            //meka use kare citizen id case ekak wena hinda
            if (isCitizenIdValid(complaintDto.getCitizenId())) {
                boolean isSuccess = ComplaintModel.save(complaintDto);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Data added").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
                }
            }else {
                // Display an alert if the CitizenId is not valid
                new Alert(Alert.AlertType.ERROR, "Invalid CitizenId. Please check the Citizen table.").show();
            }
        } catch (SQLException e) {
            System.err.println("SQL Error saving complaint:");
            System.err.println("OfficerId: " + complaintDto.getOfficerId());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving complaint. Check logs for details.").show();
        } catch (ClassNotFoundException e) {

            //officer id foreign key case ekak wena hindaha meka use kara
            System.err.println("Class Not Found Error saving complaint:");
            System.err.println("OfficerId: " + complaintDto.getOfficerId());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving complaint. Check logs for details.").show();
        }


    }

    //sql error ekak aapu hindha
    private boolean isCitizenIdValid(String citizenId) {
        return citizenId != null && !citizenId.trim().isEmpty();
    }

    private ComplaintDto CollectComplaintData(){
        String ComplaintId =TxtComplainId.getText();
        String description =TxtDescriptionOfIncident.getText();
        String date =TxtDate.getText();
        String CitizenId =TxtCitizenId.getText();
        String officerId =TxtOfficerId.getText();
        String TypeOfIncident =TxtTypeOfIncident.getText();
        String LocationOfIncident =TxtLocationOfIncident.getText();
        String Evidence =TxtEvidence.getText();
        String WitnessInformation =TxtWitnessInformation.getText();
        String SuspectName =TxtSuspectName.getText();
        String SuspectAddress =TxtSuspectAddress.getText();
        String SuspectContactNumber =TxtSuspectContactNumber.getText();
        String StatusOfTheComplaint =TxtStatusOfTheComplaint.getText();

        ComplaintDto complaintDto=new ComplaintDto();
        complaintDto.setComplainId(ComplaintId);
        complaintDto.setDescriptionOfIncident(description);
        complaintDto.setDate(date);
        complaintDto.setCitizenId(CitizenId);
        complaintDto.setOfficerId(officerId);
        complaintDto.setTypeOfIncident(TypeOfIncident);
        complaintDto.setLocationOfIncident(LocationOfIncident);
        complaintDto.setEvidence(Evidence);
        complaintDto.setWitnessInformation(WitnessInformation);
        complaintDto.setSuspectName(SuspectName);
        complaintDto.setSuspectAddress(SuspectAddress);
        complaintDto.setSuspectContactNumber(SuspectContactNumber);
        complaintDto.setStatusOfTheComplaint(StatusOfTheComplaint);

        return complaintDto;
    }

    public void setData(ComplaintDto complaintDto){
        TxtComplainId.setText(complaintDto.getComplainId());
        TxtDescriptionOfIncident.setText(complaintDto.getDescriptionOfIncident());
        TxtDate.setText(complaintDto.getDate());
        TxtCitizenId.setText(complaintDto.getCitizenId());
        TxtOfficerId.setText(complaintDto.getOfficerId());
        TxtTypeOfIncident.setText(complaintDto.getTypeOfIncident());
        TxtLocationOfIncident.setText(complaintDto.getLocationOfIncident());
        TxtEvidence.setText(complaintDto.getEvidence());
        TxtWitnessInformation.setText(complaintDto.getWitnessInformation());
        TxtSuspectName.setText(complaintDto.getSuspectName());
        TxtSuspectAddress.setText(complaintDto.getSuspectAddress());
        TxtSuspectContactNumber.setText(complaintDto.getSuspectContactNumber());
        TxtStatusOfTheComplaint.setText(complaintDto.getStatusOfTheComplaint());

    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String code=TxtComplainId.getText();

        try {
            Optional<ComplaintDto> complaint= ComplaintModel.search(code);

            if (complaint.isPresent()){
                ComplaintDto complaintDto=complaint.get();
                setData(complaintDto);
            }else {
                new Alert(Alert.AlertType.ERROR," No Items Found").show();
            }
        } catch (ClassNotFoundException |SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Something Wrong").show();
            e.printStackTrace();
        }
    }



    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        ComplaintDto updatedComplaintDto = CollectComplaintData();

        try {
            boolean isUpdated = ComplaintModel.update(updatedComplaintDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Data updated successfully").show();
                // After updating, refresh the table or perform any other necessary actions
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
