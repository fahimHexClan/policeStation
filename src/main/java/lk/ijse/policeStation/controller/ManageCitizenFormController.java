package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.model.CitizenModel;
import lk.ijse.policeStation.tm.CitizenTm;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class ManageCitizenFormController {

    public ImageView imageView;
    public Label LblDate;
    public Label lblTime;
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


    //need to add imageview to database


    public void initialize(){
        setTable();
        visualize();
        LoadDateAndTime();
    }

    private void LoadDateAndTime() {
        Date date =new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        LblDate.setText(f.format(date));

        //time set karaganna
        Timeline time= new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime=LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
                );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
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
        TxtCitizenId.clear();
        Txtname.clear();
        TxtAddress.clear();
        TxtContactNumber.clear();
        TxtGender.clear();
        TxtDob.clear();
        imageView.setImage(null);
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
        CitizenDto updatedCitizenDto = CollectCitizenData();

        try {
            boolean isUpdated = CitizenModel.update(updatedCitizenDto);

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

    public void PhotoAddButtonOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        // Check if a file was selected
        if (selectedFile != null) {
            // Convert the file to a URI string
            String imagePath = selectedFile.toURI().toString();

            // Display the selected image in an ImageView (assuming you have an ImageView in your FXML)
            Image image = new Image(imagePath);
            imageView.setImage(image);

            // You can also save the image path in your CitizenDto or elsewhere for future use
            // citizenDto.setImagePath(imagePath);
        }
    }

    }

