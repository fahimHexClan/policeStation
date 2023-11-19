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
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.model.CitizenModel;
import lk.ijse.policeStation.tm.CitizenTm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

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


    public void initialize() {
        setTable();
        visualize();
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
    void BtnSaveOnAction(ActionEvent event) {
        if (validateCitizen()) {
            CitizenDto citizenDto = CollectCitizenData();

            try {
                boolean isSuccess = CitizenModel.save(citizenDto);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Data added").show();
                    // Clear the form after successful save

                    // Refresh the table
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

    private boolean validateCitizen() {
        // wordes or number 3kata wediya thiyanna onee A-z,a-z,and 0-9 add karanna puluwan
        String citizenIdText = TxtCitizenId.getText();
        boolean isCitizenIdValidated = Pattern.matches("[C][0-9]{3,}", citizenIdText);
        if (!isCitizenIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Citizen ID! (must need to use same like this pattern [C00+])").show();
            return false;
        }

        // numer 1-9 wenakam but total numers 10 must thiyanna ona
        String contactNumberText = TxtContactNumber.getText();
        boolean isContactNumberValidated = Pattern.matches("[0-9]{10}", contactNumberText);
        if (!isContactNumberValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();
            return false;
        }


        String dobText = TxtDob.getText();
        // aniwaren me pattern ekata thiyanna ona 2000-10-08 me wage syntax ekak
        boolean isDobValidated = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dobText);
        if (!isDobValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth the right pattern is (Year-MM-DD)").show();
            return false;
        }

        // aniwaren me deken ekak select karanna oanaMale|Female
        String genderText = TxtGender.getText();
        boolean isGenderValidated = Pattern.matches("Male|Female", genderText);
        if (!isGenderValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid gender the rigt format is(Male|Female)").show();
            return false;
        }

        // aniwaren a-z orA-z words 3kata wediya thiyanna ona
        String nameText = Txtname.getText();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid name must need to add more tha 3 letters").show();
            return false;
        }

        //  address eka words 3ta wediya A-Z,a-z,1-9 with space thiyanna one
        String addressText = TxtAddress.getText();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid address").show();
            return false;
        }
        // meka work na
        if (imageView.getImage() == null || imageView.getImage().isError()) {
            new Alert(Alert.AlertType.ERROR, "Please select a valid image").show();
            return false;
        }


        return true;
    }

    private CitizenDto CollectCitizenData() {
        String CitizenId = TxtCitizenId.getText();
        String ContactNumber = TxtContactNumber.getText();
        String Dob = TxtDob.getText();
        String Gender = TxtGender.getText();
        String Name = Txtname.getText();
        String Address = TxtAddress.getText();
        Image photo = imageView.getImage();

        CitizenDto citizenDto = new CitizenDto();
        citizenDto.setCitizenId(CitizenId);
        citizenDto.setContactNumber(ContactNumber);
        citizenDto.setDob(Dob);
        citizenDto.setGender(Gender);
        citizenDto.setName(Name);
        citizenDto.setAddress(Address);
        citizenDto.setImgview(convertImageToBytes(photo));
        return citizenDto;
    }

    private byte[] convertImageToBytes(Image image) {
        BufferedImage bufferedImage = convertImageToBufferedImage(image);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error converting image to byte array", e);
        }
    }

    private BufferedImage convertImageToBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        writableImage = new WritableImage(pixelReader, width, height);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                javafx.scene.paint.Color color = pixelReader.getColor(x, y);
                int argb = (int) (color.getOpacity() * 255) << 24 |
                        (int) (color.getRed() * 255) << 16 |
                        (int) (color.getGreen() * 255) << 8 |
                        (int) (color.getBlue() * 255);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        return bufferedImage;
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
                boolean isDeleted = CitizenModel.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Data Deleted Successfully").show();
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
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateCitizen()) {
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
    }

    public void BtnSearchOnAction(ActionEvent actionEvent) {
        String code = TxtCitizenId.getText();

        try {
            Optional<CitizenDto> Citizen = CitizenModel.search(code);

            if (Citizen.isPresent()) {
                CitizenDto citizenDto = Citizen.get();
                setData(citizenDto);
            } else {
                new Alert(Alert.AlertType.ERROR, " No Items Found").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something Wrong").show();
            e.printStackTrace();
        }
    }

    public void setData(CitizenDto citizenDto) {
        TxtCitizenId.setText(citizenDto.getCitizenId());
        Txtname.setText(citizenDto.getName());
        TxtAddress.setText(citizenDto.getAddress());
        TxtContactNumber.setText(citizenDto.getContactNumber());
        TxtGender.setText(citizenDto.getGender());
        TxtDob.setText(citizenDto.getDob());
        byte[] imageData = citizenDto.getImgview();
        if (imageData != null) {
            Image photo = new Image(new ByteArrayInputStream(imageData));
            imageView.setImage(photo);
        } else {
            // If there is no image data, you may want to clear the existing image in the ImageView
            imageView.setImage(null);
        }
    }

    public void setTable() {
        try {

            ArrayList<CitizenDto> allCitizens = CitizenModel.getAllCitizens();

            ArrayList<CitizenTm> citizen = new ArrayList<>();

            for (CitizenDto Citizen : allCitizens) {
                CitizenTm citizenTm = new CitizenTm();
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

    public void visualize() {
        ColCitzenId.setCellValueFactory(new PropertyValueFactory<>("CitizenId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        ColContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        ColGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ColDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
    }


    @FXML
    void PhotoAddButtonOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                Image selectedImage = new Image(selectedFile.toURI().toURL().toString());
                imageView.setImage(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error loading the image").show();
            }
        }
    }
}
