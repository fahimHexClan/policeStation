package lk.ijse.policeStation.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DashBoardFormController {
    public AnchorPane anchorDash;
    public AnchorPane MainAnchorPane;
    public JFXButton btnComplaints;
    public JFXButton btnPoliceReport;
    public JFXButton btnCitizen;
    public JFXButton btnDash;
    public JFXButton btnSignOut;
    public JFXButton btnEmployee;
    public JFXButton btnCrime;
    public JFXButton btnTraffic;
    public Label NumberOfCrimes;
    public Label NumberOfFines;
    public BarChart ComplaintDetails;
    public CategoryAxis ComplaintsDay;
    public NumberAxis ComplaintsCount;
    public Label NumberOfEmployee;
    public Label NumberOfCitizens;
    public Label NumberOfComplaints;
    public Label NumberOfReports;
    public Label TimeLbl;
    public Label LblDate;

    public void initialize() throws SQLException {
        updateNumberOfCrimes();
        updateNumberOfFines();
        updateComplaintDetailsChart();
        updateNumberOfEmployees();
        updateNumberOfCitizens();
        updateNumberOfComplaints();
        updateNumberOfReports();
        LoadDateAndTime();
    }
    private void LoadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        LblDate.setText(f.format(date));

        //time set karaganna
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            TimeLbl.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void updateComplaintDetailsChart() {
        try {
            // Get complaint details from the database
            Map<String, Integer> complaintDetails = ComplaintModel.getComplaintDetails();

            // Create a data series for the bar chart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Complaints Details"); // Change this line with your desired heading

            // Add data to the series
            for (Map.Entry<String, Integer> entry : complaintDetails.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            // Set the data to the bar chart
            ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();
            chartData.add(series);
            ComplaintDetails.setData(chartData);

            // Manually set tick marks with whole numbers
            ObservableList<String> categories = FXCollections.observableArrayList(complaintDetails.keySet());
            ComplaintsDay.setCategories(categories);
            ComplaintsCount.setAutoRanging(false);
            ComplaintsCount.setLowerBound(0);
            ComplaintsCount.setUpperBound(getMaxComplaintCount(complaintDetails) + 1);
            ComplaintsCount.setTickUnit(1);

            // Set custom colors for the bars
            for (XYChart.Data<String, Number> data : series.getData()) {
                int value = data.getYValue().intValue();
                String color;
                if (value < 5) {
                    color = "#25CCF7"; // Green color for values less than 5
                } else if (value < 10) {
                    color = "#ffcc00"; // Yellow color for values between 5 and 10
                } else {
                    color = "#ff0000"; // Red color for values greater than or equal to 10
                }
                Node node = data.getNode();
                node.setStyle("-fx-bar-fill: " + color + ";");
                addTooltip(node, String.valueOf(value));
            }
            // Set the heading color directly on the CategoryAxis label
            String headingColor = "#25CCF7"; // Change this color as per your requirement
            Node axisLabel = ComplaintsDay.lookup(".label");
            if (axisLabel != null) {
                axisLabel.setStyle("-fx-text-fill: " + headingColor + ";");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addTooltip(Node node, String text) {
        Tooltip tooltip = new Tooltip(text);
        Tooltip.install(node, tooltip);
    }

    private int getMaxComplaintCount(Map<String, Integer> complaintDetails) {
        return complaintDetails.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }
    @FXML
    void ManageTraffic(ActionEvent event) throws IOException {
        resetButtonStyles();
        btnTraffic.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/MangeTraffic_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }


    public void ManageComplaints(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnComplaints.setStyle("-fx-background-color:#00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageComplaints_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageCrime(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnCrime.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageCrime_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageEmployees(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnEmployee.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageEmployees_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void MangePoliceReport(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnPoliceReport.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");


        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManagePoliceReport_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void ManageCitizen(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnCitizen.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/ManageCitizen_Form.fxml"));
        anchorDash.getChildren().setAll(node);
    }

    public void SignOutButtonOnAction(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnSignOut.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/LogIn_Form .fxml"));
        MainAnchorPane.getChildren().setAll(node);
    }

    private void resetButtonStyles() {
        btnComplaints.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnPoliceReport.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnCitizen.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnDash.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnSignOut.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnEmployee.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnCrime.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
        btnTraffic.setStyle("-fx-background-color: #e4e6eb; -fx-background-radius: 0;");
    }

    public void DashBoardOnAction(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        btnDash.setStyle("-fx-background-color: #00BF63; -fx-background-radius: 0;");

        Node node = (Node) FXMLLoader.load(getClass().getResource("/view/DashBoard_Form.fxml"));
        MainAnchorPane.getChildren().setAll(node);

    }
    private void updateNumberOfCrimes() {
        try {
            // Get the number of crimes from the database
            int numberOfCrimes = CrimeModel.getAllCrimes().size();

            // Update the label text
            NumberOfCrimes.setText(String.valueOf(numberOfCrimes));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void updateNumberOfFines() {
        try {
            // Get the number of fines from the database
            int numberOfFines = FinesModel.getAllFines().size();

            // Update the label text
            NumberOfFines.setText(String.valueOf(numberOfFines));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateNumberOfEmployees() {
        try {
            // Get the number of employees from the database
            int numberOfEmployees = EmployeesModel.getAllEmployees().size();

            // Update the label text
            NumberOfEmployee.setText(String.valueOf(numberOfEmployees));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void updateNumberOfCitizens() {
        try {
            // Get the number of citizens from the database
            int numberOfCitizens = CitizenModel.getAllCitizens().size();

            // Update the label text
            NumberOfCitizens.setText(String.valueOf(numberOfCitizens));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateNumberOfComplaints() {
        try {
            // Get the number of complaints from the database
            int numberOfComplaints = ComplaintModel.getComplaintDetails().size();

            // Update the label text
            NumberOfComplaints.setText(String.valueOf(numberOfComplaints));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateNumberOfReports() {
        try {
            // Get the number of police reports from the database
            int numberOfReports = PoliceReportModel.getAllReports().size();

            // Update the label text
            NumberOfReports.setText(String.valueOf(numberOfReports));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

