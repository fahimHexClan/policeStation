package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userModel {

    public static boolean validateUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM User WHERE  UserName = ? AND Password = ? And UserId = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setObject(1, userDto.getUsrName());
            stm.setObject(2, userDto.getPassword());
            stm.setObject(3, userDto.getUsrId());

            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

}
