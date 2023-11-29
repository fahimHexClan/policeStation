package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.dto.PoliceReportDto;
import lk.ijse.policeStation.model.CitizenModel;
import lk.ijse.policeStation.model.PoliceReportModel;
import lk.ijse.policeStation.model.userModel;
import lk.ijse.policeStation.tm.CitizenTm;
import lk.ijse.policeStation.tm.PoliceReportTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManagePoliceReportFormController {
    public TableView TablePoliceReportDetails;
    public TableColumn ClmCitizenIds;
    public TableColumn ClmUserIds;
    public TableColumn ClmReportId;
    public TableColumn ClmDate;
    public TableColumn ClmDescription;
    public ComboBox CmdUserIds;
    public ComboBox CmdCitizenIds;
    public JFXTextField TxtDate;
    public JFXTextField TxtReportId;
    public JFXTextField TxtDescription;

    public void initialize() throws SQLException, ClassNotFoundException {
        setTable();
        visualize();
        try {
            ArrayList<String> citizenIds = loadCitizenIds();
            ArrayList<String> userIds = userModel.loadUserIds(); // Call the method from UserModel
            CmdCitizenIds.getItems().addAll(citizenIds);
            CmdUserIds.getItems().addAll(userIds);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void visualize() {
        ClmReportId.setCellValueFactory(new PropertyValueFactory<>("TxtReportId"));
        ClmDescription.setCellValueFactory(new PropertyValueFactory<>("TxtDescription"));
        ClmDate.setCellValueFactory(new PropertyValueFactory<>("TxtDate"));
        ClmCitizenIds.setCellValueFactory(new PropertyValueFactory<>("CmdCitizenIds"));
        ClmUserIds.setCellValueFactory(new PropertyValueFactory<>("CmdUserIds"));

    }

    private ArrayList<String> loadCitizenIds() throws SQLException, ClassNotFoundException {
        ArrayList<CitizenDto> citizenList = new CitizenModel().getAllCitizens();
        ArrayList<String> citizenIds = new ArrayList<>();

        for (CitizenDto citizen : citizenList) {
            citizenIds.add(citizen.getCitizenId());
        }

        return citizenIds;
    }

    public void BtnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code = TxtReportId.getText();

        Optional<PoliceReportDto> PoliceReport = PoliceReportModel.search(code);

        if (PoliceReport.isPresent()) {
            PoliceReportDto policeReportDto = PoliceReport.get();
            setData(policeReportDto);
        } else {
            new Alert(Alert.AlertType.ERROR, " No Items Found").show();
        }
    }

    private void setData(PoliceReportDto policeReportDto) {
        TxtReportId.setText(policeReportDto.getTxtReportId());
        TxtDescription.setText(policeReportDto.getTxtDescription());
        TxtDate.setText(policeReportDto.getTxtDate());

        CmdCitizenIds.getSelectionModel().select(policeReportDto.getCmdCitizenIds());
        CmdUserIds.getSelectionModel().select(policeReportDto.getCmdUserIds());


    }

    public void BtnSaveOnAction(ActionEvent actionEvent) {
        if (validatePoliceReport()) {
            PoliceReportDto policeReportDto = CollectpoliceReportData();

            try {
                boolean isSuccess = PoliceReportModel.save(policeReportDto);
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Data added").show();

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

    private boolean validatePoliceReport() {
        String date = TxtDate.getText();
        // aniwaren me pattern ekata thiyanna ona 2000-10-08 me wage syntax ekak
        boolean isDateValidated = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", date);
        if (!isDateValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth the right pattern is (Year-MM-DD)").show();
            return false;
        }
        String Description = TxtDescription.getText();
        boolean isDescriptionValidated = Pattern.matches("[\\w\\d\\s]{3,}", Description);
        if (!isDescriptionValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description must need to add more than 3 letters").show();
            return false;
        }
        // wordes or number 3kata wediya thiyanna onee A-z,a-z,and 0-9 add karanna puluwan
        String ReportId = TxtReportId.getText();
        boolean isReportIdValidated = Pattern.matches("[R][0-9]{3,}", ReportId);
        if (!isReportIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Report ID! (must need to use same like this pattern [R00+])").show();
            return false;
        }
        return true;
    }

    private PoliceReportDto CollectpoliceReportData() {
        PoliceReportDto policeReportDto = new PoliceReportDto();
        policeReportDto.setCmdUserIds(CmdUserIds.getValue().toString());
        policeReportDto.setTxtDate(TxtDate.getText());
        policeReportDto.setTxtReportId(TxtReportId.getText());
        policeReportDto.setTxtDescription(TxtDescription.getText());
        policeReportDto.setCmdCitizenIds(CmdCitizenIds.getValue().toString());
        return policeReportDto;
    }

    private void setTable() throws SQLException, ClassNotFoundException {

        ArrayList<PoliceReportDto> allReports = PoliceReportModel.getAllReports();

        ArrayList<PoliceReportTm> policeReport = new ArrayList<>();
        for (PoliceReportDto report : allReports) {
            PoliceReportTm policeReportTm = new PoliceReportTm();
            policeReportTm.setTxtReportId(report.getTxtReportId());
            policeReportTm.setTxtDescription(report.getTxtDescription());
            policeReportTm.setTxtDate(report.getTxtDate());
            policeReportTm.setCmdCitizenIds(report.getCmdCitizenIds());
            policeReportTm.setCmdUserIds(report.getCmdUserIds());

            policeReport.add(policeReportTm);
        }

        ObservableList<PoliceReportTm>policeReportTms  = FXCollections.observableArrayList(policeReport);

        TablePoliceReportDetails.setItems(policeReportTms);


    }

    public void BtnUpdateOnAction(ActionEvent actionEvent) {
        if (validatePoliceReport()) {
            PoliceReportDto updatedPoliceReportDto = CollectpoliceReportData();

            try {
                boolean isUpdated = PoliceReportModel.update(updatedPoliceReportDto);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Data updated successfully").show();
                    // After updating, refresh the table or perform any other necessary actions
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


    public void BtnDeleteOnAction(ActionEvent actionEvent) {
        String id = TxtReportId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete" + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();
        ButtonType pressedButton = null;
        if (buttonType.isPresent()) {
            pressedButton = buttonType.get();
        }
        if (pressedButton.equals(ButtonType.YES)) {
            try {
                boolean isDeleted = PoliceReportModel.delete(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Data Deleted Successfully").show();
                    setTable();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Data Not Deleted ").show();
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

    public void BtnClearOnAction(ActionEvent actionEvent) {
        TxtReportId.clear();
        TxtDescription.clear();
        TxtDate.clear();
        CmdCitizenIds.getSelectionModel().clearSelection();
        CmdUserIds.getSelectionModel().clearSelection();
    }

    public void ReportOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        Connection connection = connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM PoliceReport;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/Report/police.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, resultSetDataSource);
        net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);
    }
}