package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.dto.CrimeDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class CrimeModel {

    public static boolean save(CrimeDto crimeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into Crime (CrimeId,description,date,crimeType,location,CriminalRecord,InjuriesOrCasualties,Motivereason,WeaponsUsed,Status) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, crimeDto.getCrimeID());
        stm.setString(2, crimeDto.getDeatails());
        stm.setString(3, crimeDto.getDate());
        stm.setString(4, crimeDto.getTxtTypeOfCrime());
        stm.setString(5, crimeDto.getTxtLocation());
        stm.setString(6, crimeDto.getCriminalRecord());
        stm.setString(7, crimeDto.getInjuries());
        stm.setString(8, crimeDto.getTxtMotiveReson());
        stm.setString(9, crimeDto.getTxtWeponUsed());
        stm.setString(10, crimeDto.getTxtStatus());

        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static ArrayList<CrimeDto> getAllCrimes() throws SQLException, ClassNotFoundException {


        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Crime";
        PreparedStatement stm = connection.prepareStatement(sql);
        ArrayList<CrimeDto> List = new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String CrimeId = rs.getString(1);
            String description = rs.getString(2);
            String date = rs.getString(3);
            String crimeType = rs.getString(4);
            String location = rs.getString(5);
            String CriminalRecord = rs.getString(6);
            String InjuriesOrCasualties = rs.getString(7);
            String Motivereason = rs.getString(8);
            String WeaponsUsed = rs.getString(9);
            String Status = rs.getString(10);

            CrimeDto crimeDto = new CrimeDto();

            crimeDto.setCrimeID(CrimeId);
            crimeDto.setDeatails(description);
            crimeDto.setDate(date);
            crimeDto.setTxtTypeOfCrime(crimeType);
            crimeDto.setTxtLocation(location);
            crimeDto.setCriminalRecord(CriminalRecord);
            crimeDto.setInjuries(InjuriesOrCasualties);
            crimeDto.setTxtMotiveReson(Motivereason);
            crimeDto.setTxtWeponUsed(WeaponsUsed);
            crimeDto.setTxtStatus(Status);

            List.add(crimeDto);
        }
        return List;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM Crime WHERE CrimeId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean update(CrimeDto updatedCrimeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE Crime SET description=?, date=?, crimeType=?, location=?, CriminalRecord=?, InjuriesOrCasualties=?, Motivereason=?, WeaponsUsed=? ,Status=? WHERE CrimeId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, updatedCrimeDto.getDeatails());
            stm.setString(2, updatedCrimeDto.getDate());
            stm.setString(3, updatedCrimeDto.getTxtTypeOfCrime());
            stm.setString(4, updatedCrimeDto.getTxtLocation());
            stm.setString(5, updatedCrimeDto.getCriminalRecord());
            stm.setString(6, updatedCrimeDto.getInjuries());
            stm.setString(7, updatedCrimeDto.getTxtMotiveReson());
            stm.setString(8, updatedCrimeDto.getTxtWeponUsed());
            stm.setString(9, updatedCrimeDto.getTxtStatus());


            stm.setString(10, updatedCrimeDto.getCrimeID());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        }
    }


    public static Optional<CrimeDto> search(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Crime WHERE CrimeId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, code);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String crimeId = rs.getString(1);
            String description = rs.getString(2);
            String date = rs.getString(3);
            String crimeType = rs.getString(4);
            String location = rs.getString(5);
            String CriminalRecord = rs.getString(6);
            String InjuriesOrCasualties = rs.getString(7);
            String Motivereason = rs.getString(8);
            String WeaponsUsed = rs.getString(9);
            String Status = rs.getString(10);


            CrimeDto crimeDto = new CrimeDto();
            crimeDto.setCrimeID(crimeId);
            crimeDto.setDeatails(description);
            crimeDto.setDate(date);
            crimeDto.setTxtTypeOfCrime(crimeType);
            crimeDto.setTxtLocation(location);
            crimeDto.setCriminalRecord(CriminalRecord);
            crimeDto.setInjuries(InjuriesOrCasualties);
            crimeDto.setTxtMotiveReson(Motivereason);
            crimeDto.setTxtWeponUsed(WeaponsUsed);
            crimeDto.setTxtStatus(Status);

            return Optional.of(crimeDto);

        } else {
            return Optional.empty();
        }
    }
}