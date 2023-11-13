package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.UserDto;

import java.sql.Connection;
import java.sql.SQLException;

public class userModel {
    public static void save(UserDto userDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        connection.prepareStatement("");
    }
}
