package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.dto.EmployeesDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeesModel {

    public static boolean save(EmployeesDto employeesDto) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "INSERT INTO Employee (EmployeeId, EmpName, address, contactNumber, gender, " +
                             "EmployeeType, Ranking, dob, OfficerId, UserId, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            stm.setString(1, employeesDto.getTxtEmpId());
            stm.setString(2, employeesDto.getTxtEmpName());
            stm.setString(3, employeesDto.getTxtAddress());
            stm.setString(4, employeesDto.getTxtContactNumber());
            stm.setString(5, employeesDto.getTxtGender());
            stm.setString(6, employeesDto.getTxtEmpType());
            stm.setString(7, employeesDto.getTxtRank());
            stm.setString(8, employeesDto.getTxtDob());
            stm.setString(9, employeesDto.getTxtOfficerId());
            stm.setString(10, employeesDto.getTxtUsrId());

            byte[] photoBytes = employeesDto.getImageData();
            if (photoBytes != null) {
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes)) {
                    stm.setBlob(11, inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                stm.setNull(11, java.sql.Types.BLOB);
            }

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions appropriately (e.g., log or show an error message)
            e.printStackTrace();
            return false;
        }
    }

    public static Optional<EmployeesDto> search(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee WHERE EmployeeId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, code);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String empId = rs.getString(1);
            String empName = rs.getString(2);
            String address = rs.getString(3);
            String contactNumber = rs.getString(4);
            String gender = rs.getString(5);
            String EmployeeType = rs.getString(6);
            String ranking = rs.getString(7);
            String dob = rs.getString(8);
            String officerId = rs.getString(9);
            String userId = rs.getString(10);
            Blob empImgBlob = rs.getBlob(11);

            EmployeesDto employeesDto = new EmployeesDto();
            employeesDto.setTxtEmpId(empId);
            employeesDto.setTxtEmpName(empName);
            employeesDto.setTxtAddress(address);
            employeesDto.setTxtContactNumber(contactNumber);
            employeesDto.setTxtGender(gender);
            employeesDto.setTxtEmpType(EmployeeType);
            employeesDto.setTxtRank(ranking);
            employeesDto.setTxtDob(dob);
            employeesDto.setTxtOfficerId(officerId);
            employeesDto.setTxtUsrId(userId);

            // Convert Blob to byte array
            if (empImgBlob != null) {
                byte[] empImgBytes = empImgBlob.getBytes(1, (int) empImgBlob.length());
                employeesDto.setImgEmployee(empImgBytes);
            } else {
                employeesDto.setImgEmployee(null);
            }

            return Optional.of(employeesDto);

        } else {
            return Optional.empty();
        }
    }

    public static boolean update(EmployeesDto updatedEmployeeDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE Employee SET EmpName=?, address=?, contactNumber=?, gender=?, EmployeeType=?, Ranking=?, dob=?, OfficerId=?, UserId=?, photo=? WHERE EmployeeId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, updatedEmployeeDto.getTxtEmpName());
            stm.setString(2, updatedEmployeeDto.getTxtAddress());
            stm.setString(3, updatedEmployeeDto.getTxtContactNumber());
            stm.setString(4, updatedEmployeeDto.getTxtGender());
            stm.setString(5, updatedEmployeeDto.getTxtEmpType());
            stm.setString(6, updatedEmployeeDto.getTxtRank());
            stm.setString(7, updatedEmployeeDto.getTxtDob());
            stm.setString(8, updatedEmployeeDto.getTxtOfficerId());
            stm.setString(9, updatedEmployeeDto.getTxtUsrId());

            byte[] photoBytes = updatedEmployeeDto.getImageData();
            if (photoBytes != null) {
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes)) {
                    stm.setBlob(10, inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                stm.setNull(10, java.sql.Types.BLOB);
            }

            stm.setString(11, updatedEmployeeDto.getTxtEmpId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        }
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM Employee WHERE EmployeeId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<EmployeesDto> getAllEmployees() throws SQLException, ClassNotFoundException {


        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee";
        PreparedStatement stm = connection.prepareStatement(sql);
        ArrayList<EmployeesDto> List =new ArrayList<>();
        ResultSet rs=stm.executeQuery();
        while (rs.next()){
            String EmployeeId=rs.getString(1);
            String EmpName=rs.getString(2);
            String address=rs.getString(3);
            String contactNumber=rs.getString(4);
            String gender=rs.getString(5);
            String EmployeeType=rs.getString(6);
            String Ranking=rs.getString(7);
            String dob=rs.getString(8);
            String OfficerId=rs.getString(9);
            String UserId=rs.getString(10);

            EmployeesDto employeesDto= new EmployeesDto();

            employeesDto.setTxtEmpId(EmployeeId);
            employeesDto.setTxtEmpName(EmpName);
            employeesDto.setTxtAddress(address);
            employeesDto.setTxtContactNumber(contactNumber);
            employeesDto.setTxtGender(gender);
            employeesDto.setTxtEmpType(EmployeeType);
            employeesDto.setTxtRank(Ranking);
            employeesDto.setTxtDob(dob);
            employeesDto.setTxtOfficerId(OfficerId);
            employeesDto.setTxtUsrId(UserId);

            List.add(employeesDto);
        }
        return List;
    }
}
