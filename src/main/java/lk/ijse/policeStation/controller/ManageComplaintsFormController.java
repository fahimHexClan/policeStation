package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.util.StringConverter;
import lk.ijse.policeStation.dto.ComplaintDto;
import lk.ijse.policeStation.model.CitizenModel;
import lk.ijse.policeStation.model.ComplaintModel;
import lk.ijse.policeStation.model.OfficerModel;
import org.controlsfx.control.textfield.TextFields;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;
public class ManageComplaintsFormController {

    public JFXTextField TxtSuspectEmail;
    public Label LblDate;
    public Label lblTime;
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

    public void initialize() throws SQLException, ClassNotFoundException {
    LoardCitizen();
    LoardOfficers();
    LoadDateAndTime();
    }
    private void LoadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        LblDate.setText(f.format(date));

        //time set karaganna
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

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
                boolean isDeleted = ComplaintModel.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Data Deleted Successfully").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Data Not Deleted ").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while saving data").show();
            }
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (validateComplaints()) {
            ComplaintDto complaintDto = CollectComplaintData();

            try {
                //meka use kare citizen id case ekak wena hinda
                if (isCitizenIdValid(complaintDto.getCitizenId())) {
                    boolean isSuccess = ComplaintModel.save(complaintDto);
                    if (isSuccess) {
                        new Alert(Alert.AlertType.INFORMATION, "Data added.... Successfully sent an email to the suspect.").show();
                      //mail ekak swnd karanawa
                        sendEmailToSuspect(complaintDto.getSuspectEmail(), "Kandegedara PoliceStation Complaint Inquiry", "You have a complaint. Please come to the police office within a week.");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
                    }
                } else {
                    // Display an alert if the CitizenId is not valid
                    new Alert(Alert.AlertType.ERROR, "Invalid CitizenId. Please check the Citizen table.").show();
                }
            } catch (SQLException e) {
                System.err.println("SQL Error saving complaint:");
                System.err.println("OfficerId: " + complaintDto.getOfficerId());
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            } catch (ClassNotFoundException e) {

                //officer id foreign key case ekak wena hindaha meka use kara
                System.err.println("Class Not Found Error saving complaint:");
                System.err.println("OfficerId: " + complaintDto.getOfficerId());
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error saving complaint. Check logs for details.").show();
            }
        }

    }

    private void sendEmailToSuspect(String suspectEmail, String subject, String body) {
        final String username = "fahim1998108@gmail.com";
        final String password = "eugz nweg qmow yjvq";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Assuming you are using Gmail
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(suspectEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent to " + suspectEmail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean validateComplaints() {
        //wordes or number 3kata wediya thiyanna onee A-z,a-z,and 0-9 add karanna puluwan
        String complainIdText = TxtComplainId.getText();
        boolean isComplainIdValidated = Pattern.matches("[H][0-9]{3,}", complainIdText);
        if (!isComplainIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Complain Id ! (must need to use same like this pattern [H00+])").show();
            return false;
        }
        //wordes or number 6kata wediya thiyanna onee A-z,a-z,and 0-9 add karanna puluwan
        String descriptionOfIncidentText = TxtDescriptionOfIncident.getText();
        boolean isDescriptionValidated = Pattern.matches("[A-Za-z0-9]{6,}", descriptionOfIncidentText);
        if (!isDescriptionValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description Of Incident!").show();
            return false;
        }

        String dateText = TxtDate.getText();
        // aniwaren me pattern ekata thiyanna ona 2000-10-08
        boolean isDateValidated = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dateText);
        if (!isDateValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid date the right pattern is (Year-MM-DD)").show();
            return false;
        }
        // wordes or number 3kata wediya thiyanna onee A-z,a-z,and 0-9 add karanna puluwan
        String citizenIdText = TxtCitizenId.getText();
        boolean isCitizenIdValidated = Pattern.matches("[C][0-9]{3,}", citizenIdText);
        if (!isCitizenIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Citizen ID!(must need to use same like this pattern [C00+])").show();
            return false;
        }

        // wordes or number 3kata wediya thiyanna onee A-z,a-z,and 0-9 add karanna puluwan
        String officerIdTextIdText = TxtOfficerId.getText();
        boolean isOfficerIdValidated = Pattern.matches("[O][0-9]{3,}", officerIdTextIdText);
        if (!isOfficerIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Officer Id (must need to use same like this pattern [O00+])!").show();
            return false;
        }

        String txtTypeOfIncidentText = TxtTypeOfIncident.getText();
        boolean isTypeOfIncidentValidated = Pattern.matches("[A-Za-z0-9]{6,}", txtTypeOfIncidentText);
        if (!isTypeOfIncidentValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid TypeOfIncident!").show();
            return false;
        }

        String txtLocationOfIncidentText = TxtLocationOfIncident.getText();
        boolean isLocationOfIncidentValidated = Pattern.matches("[A-Za-z0-9]{6,}", txtLocationOfIncidentText);
        if (!isLocationOfIncidentValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Location!").show();
            return false;
        }

        String txtEvidenceText = TxtEvidence.getText();
        boolean isEvidenceValidated = Pattern.matches("[A-Za-z0-9]{6,}", txtEvidenceText);
        if (!isEvidenceValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Evidence!").show();
            return false;
        }

        String txtWitnessInformationText = TxtWitnessInformation.getText();
        boolean isWitnessInformatinValidated = Pattern.matches("[A-Za-z0-9]{6,}", txtWitnessInformationText);
        if (!isWitnessInformatinValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Witness Informatin!").show();
            return false;
        }

        String txtSuspectNameText = TxtSuspectName.getText();
        boolean isSuspectNameValidated = Pattern.matches("[A-Za-z]{3,15}", txtSuspectNameText);
        if (!isSuspectNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Suspect Name!").show();
            return false;
        }

        String txtSuspectAddressText = TxtSuspectAddress.getText();
        boolean isSuspectAddressValidated = Pattern.matches("[A-Za-z0-9]{3,}", txtSuspectAddressText);
        if (!isSuspectAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Suspect Address!").show();
            return false;
        }

        String txtSuspectContactNumberText = TxtSuspectContactNumber.getText();
        boolean isSuspectContactNumberValidated = Pattern.matches("[0-9]{10}", txtSuspectContactNumberText);
        if (!isSuspectContactNumberValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();
            return false;
        }

        String txtStatusOfTheComplaintText = TxtStatusOfTheComplaint.getText();
        boolean isStatusValidated = Pattern.matches("Pending|Investigating|Closed", txtStatusOfTheComplaintText);
        if (!isStatusValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Status the rigt format is(Pending|Investigating|Closed)").show();
            return false;
        }
        String txtSuspectEmailText = TxtSuspectEmail.getText();
        boolean isSuspectEmailValidated = validateEmail(txtSuspectEmailText);
        if (!isSuspectEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Suspect Email!").show();
            return false;
        }
        return true;
    }

    private boolean validateEmail(String txtSuspectEmailText) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(txtSuspectEmailText).matches();

    }

    //sql error ekak aapu hindha
    private boolean isCitizenIdValid(String citizenId) {
        return citizenId != null && !citizenId.trim().isEmpty();
    }

    private ComplaintDto CollectComplaintData() {
        String ComplaintId = TxtComplainId.getText();
        String description = TxtDescriptionOfIncident.getText();
        String date = TxtDate.getText();
        String CitizenId = TxtCitizenId.getText();
        String officerId = TxtOfficerId.getText();
        String TypeOfIncident = TxtTypeOfIncident.getText();
        String LocationOfIncident = TxtLocationOfIncident.getText();
        String Evidence = TxtEvidence.getText();
        String WitnessInformation = TxtWitnessInformation.getText();
        String SuspectName = TxtSuspectName.getText();
        String SuspectAddress = TxtSuspectAddress.getText();
        String SuspectContactNumber = TxtSuspectContactNumber.getText();
        String StatusOfTheComplaint = TxtStatusOfTheComplaint.getText();
        String SuspectEmail = TxtSuspectEmail.getText();

        ComplaintDto complaintDto = new ComplaintDto();
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
        complaintDto.setSuspectEmail(SuspectEmail);


        return complaintDto;
    }

    public void setData(ComplaintDto complaintDto) {
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
        TxtSuspectEmail.setText(complaintDto.getSuspectEmail());

    }

    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String code = TxtComplainId.getText();

        try {
            Optional<ComplaintDto> complaint = ComplaintModel.search(code);

            if (complaint.isPresent()) {
                ComplaintDto complaintDto = complaint.get();
                setData(complaintDto);
            } else {
                new Alert(Alert.AlertType.ERROR, " No Items Found").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error ").show();
        }
    }


    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateComplaints()) {
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
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error ").show();
            }
        }

    }

    public void CitizenIdOnAction(ActionEvent actionEvent) {

    }

    public void OfficerOnAction(ActionEvent actionEvent) {

    }
    private void LoardCitizen() throws SQLException, ClassNotFoundException {
        List<String> cusId = CitizenModel.getCitizenIds();
        TextFields.bindAutoCompletion(TxtCitizenId,cusId);
}

    private void LoardOfficers() throws SQLException, ClassNotFoundException {
        List<String> officerIds = OfficerModel.getOfficerIds();
        TextFields.bindAutoCompletion(TxtOfficerId, officerIds);
    }

}