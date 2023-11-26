package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.dto.ComplaintDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CitizenModel {
    public static boolean save(CitizenDto citizenDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into Citizen(CitizenId,name,address,contactNumber,gender,Dob,Citizenphoto) values(?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, citizenDto.getCitizenId());
        stm.setString(2, citizenDto.getName());
        stm.setString(3, citizenDto.getAddress());
        stm.setString(4, citizenDto.getContactNumber());
        stm.setString(5, citizenDto.getGender());
        stm.setString(6, citizenDto.getDob());


        byte[] photoBytes = citizenDto.getImgview();
        if (photoBytes != null) {
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes)) {
                stm.setBlob(7, inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            stm.setNull(7, java.sql.Types.BLOB);
        }

        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM Citizen WHERE CitizenId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Optional<CitizenDto> search(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Citizen WHERE CitizenId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String code = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(3);
            String contactNumber = rs.getString(4);
            String gender = rs.getString(5);
            String Dob = rs.getString(6);
            Blob empImgBlob = rs.getBlob(7);

            CitizenDto citizenDto = new CitizenDto();
            citizenDto.setCitizenId(code);
            citizenDto.setName(name);
            citizenDto.setAddress(address);
            citizenDto.setContactNumber(contactNumber);
            citizenDto.setGender(gender);
            citizenDto.setDob(Dob);

            if (empImgBlob != null) {
                byte[] empImgBytes = empImgBlob.getBytes(1, (int) empImgBlob.length());
                citizenDto.setImgview(empImgBytes);
            } else {
                citizenDto.setImgview(null);
            }

            return Optional.of(citizenDto);

        } else {
            return Optional.empty();
        }
    }

    public static ArrayList<CitizenDto> getAllCitizens() throws SQLException, ClassNotFoundException {


        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Citizen";
        PreparedStatement stm = connection.prepareStatement(sql);
        ArrayList<CitizenDto> List =new ArrayList<>();
        ResultSet rs=stm.executeQuery();
        while (rs.next()){
           String CitizenId=rs.getString(1);
           String name=rs.getString(2);
           String address=rs.getString(3);
           String contactNumber=rs.getString(4);
           String gender=rs.getString(5);
           String Dob=rs.getString(6);

           CitizenDto citizenDto= new CitizenDto();

           citizenDto.setCitizenId(CitizenId);
           citizenDto.setName(name);
           citizenDto.setAddress(address);
           citizenDto.setContactNumber(contactNumber);
           citizenDto.setGender(gender);
           citizenDto.setDob(Dob);

           List.add(citizenDto);
        }
        return List;
    }
    public static boolean update(CitizenDto citizenDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE Citizen SET name=?, address=?, contactNumber=?, gender=?, Dob=?, Citizenphoto=? WHERE CitizenId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, citizenDto.getName());
            stm.setString(2, citizenDto.getAddress());
            stm.setString(3, citizenDto.getContactNumber());
            stm.setString(4, citizenDto.getGender());
            stm.setString(5, citizenDto.getDob());

            byte[] photoBytes = citizenDto.getImgview();
            if (photoBytes != null) {
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes)) {
                    stm.setBlob(6, inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                stm.setNull(6, java.sql.Types.BLOB);
            }

            stm.setString(7, citizenDto.getCitizenId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        }
    }

    public static List<String> getCitizenIds() throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement("SELECT CitizenId FROM Citizen").executeQuery();

        List<String> ids = new ArrayList<>();
        while (resultSet.next()) {
            ids.add(resultSet.getString("CitizenId"));
        }
        return ids;
    }


}
