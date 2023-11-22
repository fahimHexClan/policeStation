package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.FinesDto;
import lk.ijse.policeStation.dto.PoliceReportDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class FinesModel {

    public static ArrayList<FinesDto> getAllFines() throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Fines";
        PreparedStatement stm = connection.prepareStatement(sql);
        ArrayList<FinesDto> List =new ArrayList<>();
        ResultSet rs=stm.executeQuery();
        while (rs.next()){
            String FinesId=rs.getString(1);
            String FinesDescription=rs.getString(2);
            Double FinesAmount= Double.valueOf(rs.getString(3));
            String FinesDate=rs.getString(4);
            String DriverId=rs.getString(5);

            FinesDto finesDto= new FinesDto();

            finesDto.setTxtFinesId(FinesId);
            finesDto.setTxtFinesDescrip(FinesDescription);
            finesDto.setTxtFinesAmount(FinesAmount);
            finesDto.setTxtFinesDate(FinesDate);
            finesDto.setCmbDriverId(DriverId);


            List.add(finesDto);
        }
        return List;
    }

    public static boolean update(FinesDto updatedFines) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE Fines SET FinesDescription=?, FinesAmount=?, FinesDate=?, DriverId=? WHERE FinesId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, updatedFines.getTxtFinesDescrip());
            stm.setDouble(2, updatedFines.getTxtFinesAmount());
            stm.setString(3, updatedFines.getTxtFinesDate());
            stm.setString(4, updatedFines.getCmbDriverId());
            stm.setString(5, updatedFines.getTxtFinesId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<FinesDto> search(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Fines WHERE FinesId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, code);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String FinesId = rs.getString(1);
            String FinesDescription = rs.getString(2);
            Double FinesAmount = Double.valueOf(rs.getString(3));
            String FinesDate = rs.getString(4);
            String DriverId = rs.getString(5);

            FinesDto finesDto = new FinesDto();
            finesDto.setTxtFinesId(FinesId);
            finesDto.setTxtFinesDescrip(FinesDescription);
            finesDto.setTxtFinesAmount(FinesAmount);
            finesDto.setTxtFinesDate(FinesDate);
            finesDto.setCmbDriverId(DriverId);

            return Optional.of(finesDto);

        } else {
            return Optional.empty();
        }
    }

    public  boolean save(FinesDto finesDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into Fines(FinesId,FinesDescription,FinesAmount,FinesDate,DriverId) values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, finesDto.getTxtFinesId());
        stm.setString(2, finesDto.getTxtFinesDescrip());
        stm.setDouble(3, finesDto.getTxtFinesAmount());
        stm.setString(4, finesDto.getTxtFinesDate());
        stm.setString(5, finesDto.getCmbDriverId());


        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }
}
