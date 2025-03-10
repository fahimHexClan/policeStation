package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.policeStation.dto.CrimeDto;
import lk.ijse.policeStation.model.CrimeModel;
import lk.ijse.policeStation.tm.CrimeTm;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageCrimeFormController {
    @FXML
    private TableColumn<CrimeTm, String> clmCrimeId;

    @FXML
    private TableColumn<CrimeTm, String> lmTypeOfCrime;

    @FXML
    private TableColumn<CrimeTm, String> ClmCrimeDetails;

    @FXML
    private TableColumn<CrimeTm, String> ClmCriminalRecord;

    @FXML
    private TableColumn<CrimeTm, String> ClmDate;

    @FXML
    private TableColumn<CrimeTm, String> ClmInjuries;

    @FXML
    private TableColumn<CrimeTm, String> ClmLocation;

    @FXML
    private TableColumn<CrimeTm, String> ClmMotiveReson;

    @FXML
    private TableColumn<CrimeTm, String> ClmStatus;

    @FXML
    private TableColumn<CrimeTm, String> ClmWeponsUsed;

    @FXML
    private TableView<CrimeTm> TableCrimeDetails;

    @FXML
    private JFXTextField TxtCrimeID;

    @FXML
    private JFXTextField TxtCriminalRecord;

    @FXML
    private JFXTextField TxtDate;

    @FXML
    private JFXTextField TxtDeatails;

    @FXML
    private JFXTextField TxtInjuries;

    @FXML
    private JFXTextField TxtLocation;

    @FXML
    private JFXTextField TxtMotiveReson;

    @FXML
    private JFXTextField TxtStatus;

    @FXML
    private JFXTextField TxtTypeOfCrime;

    @FXML
    private JFXTextField TxtWeponUsed;

    public void initialize() {
        setTable();
        visualize();

    }

    private void visualize() {
        clmCrimeId.setCellValueFactory(new PropertyValueFactory<>("crimeID"));
        ClmCrimeDetails.setCellValueFactory(new PropertyValueFactory<>("deatails"));
        ClmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        lmTypeOfCrime.setCellValueFactory(new PropertyValueFactory<>("txtTypeOfCrime"));
        ClmLocation.setCellValueFactory(new PropertyValueFactory<>("txtLocation"));
        ClmCriminalRecord.setCellValueFactory(new PropertyValueFactory<>("criminalRecord"));
        ClmInjuries.setCellValueFactory(new PropertyValueFactory<>("injuries"));
        ClmMotiveReson.setCellValueFactory(new PropertyValueFactory<>("txtMotiveReson"));
        ClmWeponsUsed.setCellValueFactory(new PropertyValueFactory<>("txtWeponUsed"));
        ClmStatus.setCellValueFactory(new PropertyValueFactory<>("txtStatus"));
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
        TxtCrimeID.clear();
        TxtTypeOfCrime.clear();
        TxtDeatails.clear();
        TxtCriminalRecord.clear();
        TxtDate.clear();
        TxtInjuries.clear();
        TxtLocation.clear();
        TxtMotiveReson.clear();
        TxtStatus.clear();
        TxtWeponUsed.clear();
        TableCrimeDetails.getSelectionModel().clearSelection();
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = TxtCrimeID.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete" + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();
        ButtonType pressedButton = null;
        if (buttonType.isPresent()) {
            pressedButton = buttonType.get();
        }
        if (pressedButton.equals(ButtonType.YES)) {
            boolean isDeleted = CrimeModel.delete(id);
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
        if (validateCrime()) {
            CrimeDto crimeDto = collectCrimeData();

            try {
                boolean isSuccess = CrimeModel.save(crimeDto);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Data added").show();
                    BtnClearOnAction(null);
                    setTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
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

    private CrimeDto collectCrimeData() {
        String CrimeId = TxtCrimeID.getText();
        String description = TxtDeatails.getText();
        String date = TxtDate.getText();
        String typeOfCrime = TxtTypeOfCrime.getText();
        String criminalRecord = TxtCriminalRecord.getText();
        String injuries = TxtInjuries.getText();
        String location = TxtLocation.getText();
        String motiveReason = TxtMotiveReson.getText();
        String status = TxtStatus.getText();
        String weaponsUsed = TxtWeponUsed.getText();

        CrimeDto crimeDto = new CrimeDto();

        crimeDto.setCrimeID(CrimeId);
        crimeDto.setDeatails(description);
        crimeDto.setDate(date);
        crimeDto.setTxtTypeOfCrime(typeOfCrime);
        crimeDto.setCriminalRecord(criminalRecord);
        crimeDto.setInjuries(injuries);
        crimeDto.setTxtLocation(location);
        crimeDto.setTxtMotiveReson(motiveReason);
        crimeDto.setTxtStatus(status);
        crimeDto.setTxtWeponUsed(weaponsUsed);


        return crimeDto;
    }


    private void setTable() {
        try {

            ArrayList<CrimeDto> allCrimes = CrimeModel.getAllCrimes();

            ArrayList<CrimeTm> crime = new ArrayList<>();

            for (CrimeDto Crime : allCrimes) {
                CrimeTm crimeTm = new CrimeTm();
                crimeTm.setCrimeID(Crime.getCrimeID());
                crimeTm.setDeatails(Crime.getDeatails());
                crimeTm.setDate(Crime.getDate());
                crimeTm.setTxtTypeOfCrime(Crime.getTxtTypeOfCrime());
                crimeTm.setTxtLocation(Crime.getTxtLocation());
                crimeTm.setCriminalRecord(Crime.getCriminalRecord());
                crimeTm.setInjuries(Crime.getInjuries());
                crimeTm.setTxtMotiveReson(Crime.getTxtMotiveReson());
                crimeTm.setTxtWeponUsed(Crime.getTxtWeponUsed());
                crimeTm.setTxtStatus(Crime.getTxtStatus());

                crime.add(crimeTm);

            }

            ObservableList<CrimeTm> CrimeTms = FXCollections.observableArrayList(crime);

            TableCrimeDetails.setItems(CrimeTms);


        } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }

}

    private boolean validateCrime() {
        String crimeIDText = TxtCrimeID.getText();
        boolean isCrimeIdValidated = Pattern.matches("[D][0-9]{3,}", crimeIDText);
        if (!isCrimeIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Crime ID! (must need to use same like this pattern [D00+])").show();
            return false;
        }

        String detailsText = TxtDeatails.getText();
        boolean isDetailsValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", detailsText);
        if (!isDetailsValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid details").show();
            return false;
        }

        String dateText = TxtDate.getText();
        // aniwaren me pattern ekata thiyanna ona 2000-10-08 me wage syntax ekak
        boolean isDateValidated = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dateText);
        if (!isDateValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth the right pattern is (Year-MM-DD)").show();
            return false;
        }

        String typeOfCrimeText = TxtTypeOfCrime.getText();
        boolean isTypeOfCrimeValidated = Pattern.matches("[A-Za-z0-9/.\\s]{1,}", typeOfCrimeText);
        if (!isTypeOfCrimeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid typeOfCrime").show();
            return false;
        }
        String locationText = TxtLocation.getText();
        boolean isLocationValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", locationText);
        if (!isLocationValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Location").show();
            return false;
        }

        String criminalRecordText = TxtCriminalRecord.getText();
        boolean isCriminalRecordValidated = Pattern.matches("[A-Za-z0-9/.\\s]{1,}", criminalRecordText);
        if (!isCriminalRecordValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid criminalRecord").show();
            return false;
        }
        String injuriesText = TxtInjuries.getText();
        boolean isInjuriesValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", injuriesText);
        if (!isInjuriesValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid injuries").show();
            return false;
        }

        String motiveResonText = TxtMotiveReson.getText();
        boolean isMotiveReasonValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", motiveResonText);
        if (!isMotiveReasonValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Motive Reson").show();
            return false;
        }

        String weponUsedText = TxtWeponUsed.getText();
        boolean isWeponUsedValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", weponUsedText);
        if (!isWeponUsedValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid injuries weponUsed").show();
            return false;
        }

        String statusText = TxtStatus.getText();
        boolean isStatusValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", statusText);
        if (!isStatusValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid status").show();
            return false;
        }

        return true;
    }


    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String code = TxtCrimeID.getText();

        try {
            Optional<CrimeDto> Crime = CrimeModel.search(code);

            if (Crime.isPresent()) {
                CrimeDto crimeDto = Crime.get();
                setData(crimeDto);
            } else {
                new Alert(Alert.AlertType.ERROR, " No Items Found").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error").show();
        }
    }


    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateCrime()) {
            CrimeDto updatedCrimeDto = collectCrimeData();

            try {
                boolean isUpdated = CrimeModel.update(updatedCrimeDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Data updated successfully").show();
                    setTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Data not updated").show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        }
    }

    public void setData(CrimeDto crimeDto) {
        TxtCrimeID.setText(crimeDto.getCrimeID());
        TxtDeatails.setText(crimeDto.getDeatails());
        TxtDate.setText(crimeDto.getDate());
        TxtTypeOfCrime.setText(crimeDto.getTxtTypeOfCrime());
        TxtLocation.setText(crimeDto.getTxtLocation());
        TxtCriminalRecord.setText(crimeDto.getCriminalRecord());
        TxtInjuries.setText(crimeDto.getInjuries());
        TxtMotiveReson.setText(crimeDto.getTxtMotiveReson());
        TxtWeponUsed.setText(crimeDto.getTxtWeponUsed());
        TxtStatus.setText(crimeDto.getTxtStatus());
    }

    public void ReportOnAction(ActionEvent actionEvent) {
    }
}
