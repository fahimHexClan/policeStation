package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.model.CitizenModel;
import lk.ijse.policeStation.tm.CitizenTm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageCitizenFormController {

    @FXML
    private TableColumn<CitizenTm, String> ColAddress;

    @FXML
    private TableColumn<CitizenTm, String> ColCitzenId;

    @FXML
    private TableColumn<CitizenTm, String> ColContactNumber;

    @FXML
    private TableColumn<CitizenTm, String> ColDob;

    @FXML
    private TableColumn<CitizenTm, String> ColGender;

    @FXML
    private TableColumn<CitizenTm, String> ColName;

    @FXML
    private Label ContactNumberTxt;

    @FXML
    private TableView<CitizenTm> TblCitizen;

    @FXML
    private JFXTextField TxtAddress;

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

    public void initialize(){
        setTable();
        visualize();
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
    CitizenDto citizenDto =CollectCitizenData();

        try {
          boolean isSuccess = CitizenModel.save(citizenDto);
            if (isSuccess ){
                new Alert(Alert.AlertType.INFORMATION,"Data added").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Data Not Added").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    private CitizenDto CollectCitizenData(){
        String CitizenId =TxtCitizenId.getText();
        String ContactNumber =TxtContactNumber.getText();
        String Dob =TxtDob.getText();
        String Gender =TxtGender.getText();
        String Name =Txtname.getText();
        String Address =TxtAddress.getText();

        CitizenDto citizenDto=new CitizenDto();
        citizenDto.setCitizenId(CitizenId);
        citizenDto.setContactNumber(ContactNumber);
        citizenDto.setDob(Dob);
        citizenDto.setGender(Gender);
        citizenDto.setName(Name);
        citizenDto.setAddress(Address);

        return citizenDto;
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {

    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        String id = TxtCitizenId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete" + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();
        ButtonType pressedButton = null;
        if (buttonType.isPresent()) {
            pressedButton = buttonType.get();
        }
        if (pressedButton.equals(ButtonType.YES)) {
            try {
               boolean isDeleted= CitizenModel.delete(id);
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
    void BtnUpdateOnAction(ActionEvent event) {

    }

    public void BtnSearchOnAction(ActionEvent actionEvent) {
    String code=TxtCitizenId.getText();

        try {
           Optional<CitizenDto> Citizen= CitizenModel.search(code);

           if (Citizen.isPresent()){
            CitizenDto citizenDto=Citizen.get();
            setData(citizenDto);
           }else {
               new Alert(Alert.AlertType.ERROR," No Items Found").show();
           }
        } catch (ClassNotFoundException |SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Something Wrong").show();
            e.printStackTrace();
        }
    }

    public void setData(CitizenDto citizenDto){
        TxtCitizenId.setText(citizenDto.getCitizenId());
        Txtname.setText(citizenDto.getName());
        TxtAddress.setText(citizenDto.getAddress());
        TxtContactNumber.setText(citizenDto.getContactNumber());
        TxtGender.setText(citizenDto.getGender());
        TxtDob.setText(citizenDto.getDob());
    }

    public void setTable(){
        try {

            ArrayList<CitizenDto>allCitizens= CitizenModel.getAllCitizens();

            ArrayList<CitizenTm> citizen=new ArrayList<>();

            for (CitizenDto Citizen:allCitizens){
              CitizenTm citizenTm  =new CitizenTm();
              citizenTm.setCitizenId(Citizen.getCitizenId());
              citizenTm.setName(Citizen.getName());
              citizenTm.setAddress(Citizen.getAddress());
              citizenTm.setContactNumber(Citizen.getContactNumber());
              citizenTm.setGender(Citizen.getGender());
              citizenTm.setDob(Citizen.getDob());

              citizen.add(citizenTm);

            }

          ObservableList<CitizenTm> CitizenTms = FXCollections.observableArrayList(citizen);

            TblCitizen.setItems(CitizenTms);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void visualize(){
        ColCitzenId.setCellValueFactory(new PropertyValueFactory<>("CitizenId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        ColContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        ColGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ColDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
    }
}
