package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.PoliceReportDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class PoliceReportModel {

    public static boolean save(PoliceReportDto policeReportDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into PoliceReport(policeReportId,description,date,CitizenId,UserId) values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, policeReportDto.getTxtReportId());
        stm.setString(2, policeReportDto.getTxtDescription());
        stm.setString(3, policeReportDto.getTxtDate());
        stm.setString(4, policeReportDto.getCmdCitizenIds());
        stm.setString(5, policeReportDto.getCmdUserIds());


        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static ArrayList<PoliceReportDto> getAllReports() throws SQLException, ClassNotFoundException {

        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM PoliceReport";
        PreparedStatement stm = connection.prepareStatement(sql);
        ArrayList<PoliceReportDto> List =new ArrayList<>();
        ResultSet rs=stm.executeQuery();
        while (rs.next()){
            String policeReportId=rs.getString(1);
            String description=rs.getString(2);
            String date=rs.getString(3);
            String CitizenId=rs.getString(4);
            String UserId=rs.getString(5);

            PoliceReportDto policeReportDto= new PoliceReportDto();

            policeReportDto.setTxtReportId(policeReportId);
            policeReportDto.setTxtDescription(description);
            policeReportDto.setTxtDate(date);
            policeReportDto.setCmdCitizenIds(CitizenId);
            policeReportDto.setCmdUserIds(UserId);


            List.add(policeReportDto);
        }
        return List;
    }

    public static Optional<PoliceReportDto> search(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM PoliceReport WHERE policeReportId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, code);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String policeReportId = rs.getString(1);
            String description = rs.getString(2);
            String date = rs.getString(3);
            String CitizenId = rs.getString(4);
            String UserId = rs.getString(5);

            PoliceReportDto policeReportDto = new PoliceReportDto();
            policeReportDto.setTxtReportId(policeReportId);
            policeReportDto.setTxtDescription(description);
            policeReportDto.setTxtDate(date);
            policeReportDto.setCmdCitizenIds(CitizenId);
            policeReportDto.setCmdUserIds(UserId);

            return Optional.of(policeReportDto);

        } else {
            return Optional.empty();
        }
    }

    public static boolean update(PoliceReportDto updatedPoliceReportDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE PoliceReport SET description=?, date=?, CitizenId=?, UserId=? WHERE policeReportId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1,  updatedPoliceReportDto.getTxtDescription());
            stm.setString(2, updatedPoliceReportDto.getTxtDate());
            stm.setString(3, updatedPoliceReportDto.getCmdCitizenIds());
            stm.setString(4, updatedPoliceReportDto.getCmdUserIds());
            stm.setString(5, updatedPoliceReportDto.getTxtReportId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM PoliceReport WHERE policeReportId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }
}