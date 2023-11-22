package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.DriverDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class DriverModel {

    public static boolean save(DriverDto driverDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "INSERT INTO Driver(DriverId, DriverName, address, contactNumber, gender, Dob, licenseNum, VehicleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, driverDto.getTxtDriverId());
            stm.setString(2, driverDto.getTxtDriverName());
            stm.setString(3, driverDto.getTxtAddress());
            stm.setString(4, driverDto.getTxtContactNumber());
            stm.setString(5, driverDto.getTxtGender());
            stm.setString(6, driverDto.getTxtDob());
            stm.setString(7, driverDto.getTxtLicenseNumber());
            stm.setString(8, driverDto.getCmbVehicleId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<DriverDto> getAllDrivers() throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Driver";
        PreparedStatement stm = connection.prepareStatement(sql);
        ArrayList<DriverDto> List = new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String DriverId = rs.getString(1);
            String DriverName = rs.getString(2);
            String address = rs.getString(3);
            String contactNumber = rs.getString(4);
            String gender = rs.getString(5);
            String Dob = rs.getString(6);
            String licenseNum = rs.getString(7);
            String VehicleId = rs.getString(8);

            DriverDto driverDto = new DriverDto();

            driverDto.setTxtDriverId(DriverId);
            driverDto.setTxtDriverName(DriverName);
            driverDto.setTxtAddress(address);
            driverDto.setTxtContactNumber(contactNumber);
            driverDto.setTxtGender(gender);
            driverDto.setTxtDob(Dob);
            driverDto.setTxtLicenseNumber(licenseNum);
            driverDto.setCmbVehicleId(VehicleId);


            List.add(driverDto);
        }
        return List;

    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM Driver WHERE DriverId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Optional<DriverDto> search(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Driver WHERE DriverId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, code);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String DriverId = rs.getString(1);
            String DriverName = rs.getString(2);
            String address = rs.getString(3);
            String contactNumber = rs.getString(4);
            String gender = rs.getString(5);
            String Dob = rs.getString(6);
            String licenseNum = rs.getString(7);
            String VehicleId = rs.getString(8);

            DriverDto driverDto = new DriverDto();
            driverDto.setTxtDriverId(DriverId);
            driverDto.setTxtDriverName(DriverName);
            driverDto.setTxtAddress(address);
            driverDto.setTxtContactNumber(contactNumber);
            driverDto.setTxtGender(gender);
            driverDto.setTxtDob(Dob);
            driverDto.setTxtLicenseNumber(licenseNum);
            driverDto.setCmbVehicleId(VehicleId);

            return Optional.of(driverDto);

        } else {
            return Optional.empty();
        }
    }

    public static boolean update(DriverDto updatedDriver) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE Driver SET DriverName=?, address=?, contactNumber=?, gender=?, Dob=?, licenseNum=?, VehicleId=?  WHERE DriverId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1,  updatedDriver.getTxtDriverName());
            stm.setString(2, updatedDriver.getTxtAddress());
            stm.setString(3, updatedDriver.getTxtContactNumber());
            stm.setString(4, updatedDriver.getTxtGender());
            stm.setString(5, updatedDriver.getTxtDob());
            stm.setString(6, updatedDriver.getTxtLicenseNumber());
            stm.setString(7, updatedDriver.getCmbVehicleId());
            stm.setString(8, updatedDriver.getTxtDriverId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}