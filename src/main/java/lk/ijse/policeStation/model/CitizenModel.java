package lk.ijse.policeStation.model;

import javafx.scene.control.Alert;
import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CitizenModel {
    public static boolean save(CitizenDto citizenDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into Citizen(CitizenId,name,address,contactNumber,gender,Dob) values(?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, citizenDto.getCitizenId());
        stm.setString(2, citizenDto.getName());
        stm.setString(3, citizenDto.getAddress());
        stm.setString(4, citizenDto.getContactNumber());
        stm.setString(5, citizenDto.getGender());
        stm.setString(6, citizenDto.getDob());

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

            CitizenDto citizenDto = new CitizenDto();
            citizenDto.setCitizenId(code);
            citizenDto.setName(name);
            citizenDto.setAddress(address);
            citizenDto.setContactNumber(contactNumber);
            citizenDto.setGender(gender);
            citizenDto.setDob(Dob);


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
}
