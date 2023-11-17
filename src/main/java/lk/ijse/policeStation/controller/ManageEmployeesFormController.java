package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ManageEmployeesFormController {

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
        EmployeesDto employeesDto =CollectEmployeesData();

        boolean isSuccess = EmployeesModel.save(employeesDto);
        if (isSuccess ){
            new Alert(Alert.AlertType.INFORMATION,"Data added").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Data Not Added").show();
        }

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
