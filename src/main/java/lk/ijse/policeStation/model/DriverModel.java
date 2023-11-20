package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.DriverDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {

    public static boolean save(DriverDto driverDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into Driver(DriverId,DriverName,address,contactNumber,gender,Dob,licenseNum,VehicleId) values(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, driverDto.getTxtDriverId());
        stm.setString(2, driverDto.getTxtDriverName());
        stm.setString(3, driverDto.getTxtAddress());
        stm.setString(4, driverDto.getTxtContactNumber());
        stm.setString(5, driverDto.getTxtGender());
        stm.setString(6, driverDto.getTxtDob());
        stm.setString(7, driverDto.getTxtLicenseNumber());
        stm.setString(8, driverDto.getCmbVehicleId());


        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }
}

