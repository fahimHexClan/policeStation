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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.dto.EmployeesDto;
import lk.ijse.policeStation.model.CitizenModel;
import lk.ijse.policeStation.model.EmployeesModel;
import lk.ijse.policeStation.model.OfficerModel;
import lk.ijse.policeStation.model.userModel;
import lk.ijse.policeStation.tm.CitizenTm;
import lk.ijse.policeStation.tm.EmployeeTm;
import org.controlsfx.control.textfield.TextFields;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageEmployeesFormController {

    public TableView <EmployeeTm>TblEmployee;
    public TableColumn<EmployeeTm, String> ClmEmpId;
    public TableColumn <EmployeeTm, String> ColmOfficerId;
    public TableColumn <EmployeeTm, String> ComUserId;
    public TableColumn <EmployeeTm, String> ColmAddress;
    public TableColumn <EmployeeTm, String> ColmContactNum;
    public TableColumn <EmployeeTm, String> ClmEmpName;
    public TableColumn <EmployeeTm, String> ClmEmployeeType;
    public TableColumn <EmployeeTm, String> ClmRank;
    public TableColumn <EmployeeTm, String> ClmDob;
    public TableColumn <EmployeeTm, String> ClmGender;
    @FXML
    private ImageView ImgEmployee;

    @FXML
    private JFXTextField TxtAddress;

    @FXML
    private JFXTextField TxtContactNumber;

    @FXML
    private JFXTextField TxtDob;

    @FXML
    private JFXTextField TxtEmpId;

    @FXML
    private JFXTextField TxtEmpName;

    @FXML
    private JFXTextField TxtEmpType;

    @FXML
    private JFXTextField TxtGender;

    @FXML
    private JFXTextField TxtOfficerId;

    @FXML
    private JFXTextField TxtRank;

    @FXML
    private JFXTextField TxtUsrId;

    public void initialize() throws SQLException, ClassNotFoundException {
        LoardUsers();
        LoardOfficers();
        setTable();
        visualize();
    }

    private void LoardUsers() {
        try {
            List<String> userIds = userModel.loadUserIds();
            TextFields.bindAutoCompletion(TxtUsrId, userIds);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading user IDs").show();
            e.printStackTrace();
        }
    }

    private void LoardOfficers() throws SQLException, ClassNotFoundException {
        List<String> officerIds = OfficerModel.getOfficerIds();
        TextFields.bindAutoCompletion(TxtOfficerId, officerIds);

    }

    private void setTable() {
        try {

            ArrayList<EmployeesDto> allEmployees = EmployeesModel.getAllEmployees();

            ArrayList<EmployeeTm> employee = new ArrayList<>();

            for (EmployeesDto Employee : allEmployees) {
                EmployeeTm employeeTm = new EmployeeTm();
                employeeTm.setTxtEmpId(Employee.getTxtEmpId());
                employeeTm.setTxtEmpName(Employee.getTxtEmpName());
                employeeTm.setTxtContactNumber(Employee.getTxtContactNumber());
                employeeTm.setTxtGender(Employee.getTxtGender());
                employeeTm.setTxtEmpType(Employee.getTxtEmpType());
                employeeTm.setTxtRank(Employee.getTxtRank());
                employeeTm.setTxtDob(Employee.getTxtDob());
                employeeTm.setTxtOfficerId(Employee.getTxtOfficerId());
                employeeTm.setTxtUsrId(Employee.getTxtUsrId());

                employee.add(employeeTm);

            }

            ObservableList<EmployeeTm> EmployeeTms = FXCollections.observableArrayList(employee);

            TblEmployee.setItems(EmployeeTms);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void visualize() {
        ClmEmpId.setCellValueFactory(new PropertyValueFactory<>("TxtEmpId"));
        ClmEmpName.setCellValueFactory(new PropertyValueFactory<>("TxtEmpName"));
        ColmAddress.setCellValueFactory(new PropertyValueFactory<>("TxtAddress"));
        ColmContactNum.setCellValueFactory(new PropertyValueFactory<>("TxtContactNumber"));
        ClmGender.setCellValueFactory(new PropertyValueFactory<>("TxtGender"));
        ClmEmployeeType.setCellValueFactory(new PropertyValueFactory<>("TxtEmpType"));
        ClmRank.setCellValueFactory(new PropertyValueFactory<>("TxtRank"));
        ClmDob.setCellValueFactory(new PropertyValueFactory<>("TxtDob"));
        ColmOfficerId.setCellValueFactory(new PropertyValueFactory<>("TxtOfficerId"));
        ComUserId.setCellValueFactory(new PropertyValueFactory<>("TxtUsrId"));
    }

    @FXML
    void ImageAddOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            ImgEmployee.setImage(selectedImage);
        }
    }

    public void BtnSaveOnAction(ActionEvent actionEvent) {
        if (validateEmployee()) {
            EmployeesDto employeesDto = CollectEmployeesData();

            boolean isSuccess = EmployeesModel.save(employeesDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.INFORMATION, "Data added").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
            }
        }
    }

    private boolean validateEmployee() {
        String employeeIdText = TxtEmpId.getText();
        boolean isEmployeeIDValidated = Pattern.matches("[E][0-9]{3,}", employeeIdText);
        if (!isEmployeeIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID Must need to add([Exxxxxx])!").show();
            return false;
        }

        //validate employee name
        String empNameText = TxtEmpName.getText();
        boolean isEmpNameValidated = Pattern.matches("[A-Za-z]{3,}", empNameText);
        if (!isEmpNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee name (pls add more then 3 letters)").show();
            return false;
        }

        //validate employee address
        String addressText = TxtAddress.getText();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee address(pls add more then 3 letters)").show();
            return false;
        }

        //validate employee contact number
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

        // Validate Employee Type
        String employeeTypeText = TxtEmpType.getText();
        boolean isEmployeeTypeValidated = Pattern.matches("Full Time|Part Time", employeeTypeText);
        if (!isEmployeeTypeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee Type because u chosse this(Full Time|Part Time) ").show();
            return false;
        }

        // Validate Police Ranking
        String rankingText = TxtRank.getText();
        boolean isRankingValidated = Pattern.matches("[A-Za-z]+", rankingText);
        if (!isRankingValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Police Ranking (not allowed numbers )").show();
            return false;
        }

        String dobText = TxtDob.getText();
        // aniwaren me pattern ekata thiyanna ona 2000-10-08
        boolean isDobValidated = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dobText);
        if (!isDobValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth the right pattern is (Year-MM-DD)").show();
            return false;
        }
        // Validate Officer Id
        String officerIdText = TxtOfficerId.getText();
        boolean isOfficerIdValidated = Pattern.matches("[O][0-9]{3,}", officerIdText);
        if (!isOfficerIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Officer ID(must need to use same like this pattern [O00+])").show();
            return false;
        }

        // Validate User Id
        String userIdText = TxtUsrId.getText();
        boolean isUserIdValidated = Pattern.matches("[U][0-9]{3,}", userIdText);
        if (!isUserIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid User ID (must need to use same like this pattern [U00+])").show();
            return false;
        }

        // Validate Photo (you may want to check if an image is selected)
        Image photo = ImgEmployee.getImage();
        if (photo == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a photo").show();
            return false;
        }


        return true;
    }


    private EmployeesDto CollectEmployeesData() {
        String employeeId = TxtEmpId.getText();
        String empName = TxtEmpName.getText();
        String address = TxtAddress.getText();
        String contactNumber = TxtContactNumber.getText();
        String gender = TxtGender.getText();
        String employeeType = TxtEmpType.getText();
        String ranking = TxtRank.getText();
        String dob = TxtDob.getText();
        String officerId = TxtOfficerId.getText();
        String userId = TxtUsrId.getText();
        Image photo = ImgEmployee.getImage();

        EmployeesDto employeesDto = new EmployeesDto();
        employeesDto.setTxtEmpId(employeeId);
        employeesDto.setTxtEmpName(empName);
        employeesDto.setTxtAddress(address);
        employeesDto.setTxtContactNumber(contactNumber);
        employeesDto.setTxtGender(gender);
        employeesDto.setTxtEmpType(employeeType);
        employeesDto.setTxtRank(ranking);
        employeesDto.setTxtDob(dob);
        employeesDto.setTxtOfficerId(officerId);
        employeesDto.setTxtUsrId(userId);
        employeesDto.setImgEmployee(convertImageToBytes(photo));

        return employeesDto;
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

    public void BtnUpdateOnAction(ActionEvent actionEvent) {
        if (validateEmployee()) {
            EmployeesDto updatedEmployeeDto = CollectEmployeesData();

            try {
                boolean isUpdated = EmployeesModel.update(updatedEmployeeDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Data updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Data not updated").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void BtnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = TxtEmpId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete" + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();
        ButtonType pressedButton = null;
        if (buttonType.isPresent()) {
            pressedButton = buttonType.get();
        }
        if (pressedButton.equals(ButtonType.YES)) {
            boolean isDeleted= EmployeesModel.delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Data Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Data Not Deleted ").show();
            }
        }
    }

    public void BtnClearOnAction(ActionEvent actionEvent) {
        TxtEmpId.clear();
        TxtEmpName.clear();
        TxtAddress.clear();
        TxtContactNumber.clear();
        TxtGender.clear();
        TxtEmpType.clear();
        TxtRank.clear();
        TxtDob.clear();
        TxtOfficerId.clear();


        ImgEmployee.setImage(null);

        new Alert(Alert.AlertType.INFORMATION, "Cleared all fields").show();
    }

    public void BtnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=TxtEmpId.getText();

        Optional<EmployeesDto> employee= EmployeesModel.search(code);

        if (employee.isPresent()){
            EmployeesDto employeesDto=employee.get();
            setData(employeesDto);
        }else {
            new Alert(Alert.AlertType.ERROR," No Items Found").show();
        }
    }

    private void setData(EmployeesDto employeesDto) {
        TxtEmpId.setText(employeesDto.getTxtEmpId());
        TxtEmpName.setText(employeesDto.getTxtEmpName());
        TxtAddress.setText(employeesDto.getTxtAddress());
        TxtContactNumber.setText(employeesDto.getTxtContactNumber());
        TxtGender.setText(employeesDto.getTxtGender());
        TxtEmpType.setText(employeesDto.getTxtEmpType());
        TxtRank.setText(employeesDto.getTxtRank());
        TxtDob.setText(employeesDto.getTxtDob());
        TxtOfficerId.setText(employeesDto.getTxtOfficerId());
        TxtUsrId.setText(employeesDto.getTxtUsrId());

        byte[] imageData = employeesDto.getImgEmployee();
        if (imageData != null) {
            Image photo = new Image(new ByteArrayInputStream(imageData));
            ImgEmployee.setImage(photo);
        } else {
            // If there is no image data, you may want to clear the existing image in the ImageView
            ImgEmployee.setImage(null);
        }

    }
}
