package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.FinesDto;
import lk.ijse.policeStation.model.DriverModel;
import lk.ijse.policeStation.model.FinesModel;
import lk.ijse.policeStation.tm.FinesTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageFinesFormController {

    public ComboBox<String> CmbDriverId;
    public AnchorPane SubPane;
    @FXML
    private TableColumn<?, ?> ClmDriverID;

    @FXML
    private TableColumn<?, ?> ClmFinesAmount;

    @FXML
    private TableColumn<?, ?> ClmFinesDate;

    @FXML
    private TableColumn<?, ?> ClmFinesDescription;

    @FXML
    private TableColumn<?, ?> ClmFinesId;

    @FXML
    private TableView<FinesTm> TableFines;

    @FXML
    private JFXTextField TxtFinesAmount;

    @FXML
    private JFXTextField TxtFinesDate;

    @FXML
    private JFXTextField TxtFinesDescrip;

    @FXML
    private JFXTextField TxtFinesId;
    private FinesModel finesModel;
    private DriverModel driverModel=new DriverModel();

    public void initialize() throws SQLException, ClassNotFoundException {
        setTable();
        visualize();
        finesModel=new FinesModel();
        loadDriverIds();
    }

    private void loadDriverIds() {
        try {
            ArrayList<String> driverIds = DriverModel.getAllDriverIds();
            CmbDriverId.getItems().addAll(driverIds);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtnClearOnAction(ActionEvent event) {
        TxtFinesId.clear();
        TxtFinesDescrip.clear();
        TxtFinesAmount.clear();
        TxtFinesDate.clear();
        CmbDriverId.getSelectionModel().clearSelection();
    }
    @FXML
    void BtnSaveOnAction(ActionEvent event) throws SQLException {
        if (validateFines()) {
            FinesDto finesDto = CollectFines();
            Connection connection = null;

            try {
                connection = DatabaseConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                boolean isSuccess = finesModel.save(finesDto);

                boolean b = driverModel.updateStatus(finesDto, CmbDriverId.getSelectionModel().getSelectedItem());
                if (!b) {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
                    return;
                }
                if (isSuccess) {
                    new Alert(Alert.AlertType.INFORMATION, "Data added").show();
                    setTable();
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Data Not Added").show();
                }

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                connection.rollback();
                e.printStackTrace();
            }finally {
                connection.setAutoCommit(true);
            }
        }
    }
    private void visualize() {
        ClmDriverID.setCellValueFactory(new PropertyValueFactory<>("CmbDriverId"));
        ClmFinesId.setCellValueFactory(new PropertyValueFactory<>("TxtFinesId"));
        ClmFinesDescription.setCellValueFactory(new PropertyValueFactory<>("TxtFinesDescrip"));
        ClmFinesAmount.setCellValueFactory(new PropertyValueFactory<>("TxtFinesAmount"));
        ClmFinesDate.setCellValueFactory(new PropertyValueFactory<>("TxtFinesDate"));

    }
    private void setTable() throws SQLException, ClassNotFoundException {
        ArrayList<FinesDto> finesList = FinesModel.getAllFines();

        ArrayList<FinesTm> fines = new ArrayList<>();
        for (FinesDto finesDto : finesList) {
            FinesTm finesTm = new FinesTm();
            finesTm.setTxtFinesId(finesDto.getTxtFinesId());
            finesTm.setTxtFinesDescrip(finesDto.getTxtFinesDescrip());
            finesTm.setTxtFinesAmount(finesDto.getTxtFinesAmount());
            finesTm.setTxtFinesDate(finesDto.getTxtFinesDate());
            finesTm.setCmbDriverId(finesDto.getCmbDriverId());

            fines.add(finesTm);
        }

        ObservableList<FinesTm> finesTms = FXCollections.observableArrayList(fines);
        TableFines.setItems(finesTms);
    }
    private FinesDto CollectFines() {
        FinesDto finesDto = new FinesDto();
        finesDto.setCmbDriverId(CmbDriverId.getValue().toString());
        finesDto.setTxtFinesId(TxtFinesId.getText());
        finesDto.setTxtFinesDescrip(TxtFinesDescrip.getText());
        finesDto.setTxtFinesAmount(Double.valueOf(TxtFinesAmount.getText()));
        finesDto.setTxtFinesDate(TxtFinesDate.getText());

        return finesDto;
    }

    private boolean validateFines() {
        String FinesId = TxtFinesId.getText();
        boolean isFinesIdValidated = Pattern.matches("[F][0-9]{3,}", FinesId);
        if (!isFinesIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Driver ID! (must need to use same like this pattern [F00+])").show();
            return false;
        }

        String FinesDate = TxtFinesDate.getText();
        // aniwaren me pattern ekata thiyanna ona 2000-10-08 me wage syntax ekak
        boolean isDateValidated = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", FinesDate);
        if (!isDateValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid date of birth the right pattern is (Year-MM-DD)").show();
            return false;
        }

        String FinesDescription = TxtFinesDescrip.getText();
        boolean isFinesValidated = Pattern.matches("[\\w\\d\\s]{3,}", FinesDescription);
        if (!isFinesValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address must need to add more than 3 letters").show();
            return false;
        }

        String finesAmount = TxtFinesAmount.getText();
        try {
            Double.parseDouble(finesAmount);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Fines Amount! (must be a valid numeric value)").show();
            return false;
        }
        String driverId = CmbDriverId.getValue();
        if (driverId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Driver ID").show();
            return false;
        }

        return true;
    }

    @FXML
    void BtnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String code = TxtFinesId.getText();

        Optional<FinesDto> fines = FinesModel.search(code);

        if (fines.isPresent()) {
            FinesDto finesDto = fines.get();
            setData(finesDto);
        } else {
            new Alert(Alert.AlertType.ERROR, " No Items Found").show();
        }
    }

    private void setData(FinesDto finesDto) {
        TxtFinesId.setText(finesDto.getTxtFinesId());
        TxtFinesDescrip.setText(finesDto.getTxtFinesDescrip());
        TxtFinesAmount.setText(String.valueOf(Double.valueOf(finesDto.getTxtFinesAmount())));
        TxtFinesDate.setText(finesDto.getTxtFinesDate());

        CmbDriverId.getSelectionModel().select(finesDto.getCmbDriverId());
    }




    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateFines()) {
            FinesDto updatedFines = CollectFines();

            try {
                boolean isUpdated = FinesModel.update(updatedFines);

                if (isUpdated) {
                    boolean isDriverStatusUpdated = driverModel.updateStatus(updatedFines, CmbDriverId.getValue());

                    if (isDriverStatusUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "Data updated successfully").show();
                        setTable();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Driver status not updated").show();
                    }
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



    @FXML
    void ButtonBack(ActionEvent event) throws IOException {
        javafx.scene.Node node = (Node) FXMLLoader.load(getClass().getResource("/view/MangeTraffic_Form.fxml"));
        SubPane.getChildren().setAll(node);

    }

    }
