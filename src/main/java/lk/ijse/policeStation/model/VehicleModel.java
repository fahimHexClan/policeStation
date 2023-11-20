package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class VehicleModel {

    public static boolean save(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into Vehicle (VehicleId,EngineNumber,Owner,VehicleNumPlate,VehicleType,Model) values(?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, vehicleDto.getTxtVehicleId());
        stm.setString(2, vehicleDto.getTxtEngineNum());
        stm.setString(3, vehicleDto.getTxtOwnerId());
        stm.setString(4, vehicleDto.getTxtPlateNum());
        stm.setString(5, vehicleDto.getTxtVehicleType());
        stm.setString(6, vehicleDto.getTxtModelId());

        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

}

    public static ArrayList<VehicleDto> getAllVehicle() throws SQLException, ClassNotFoundException {

        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Vehicle";
        PreparedStatement stm = connection.prepareStatement(sql);
        ArrayList<VehicleDto> List = new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String VehicleId = rs.getString(1);
            String EngineNumber = rs.getString(2);
            String Owner = rs.getString(3);
            String VehicleNumPlate = rs.getString(4);
            String VehicleType = rs.getString(5);
            String Model = rs.getString(6);

            VehicleDto vehicleDto = new VehicleDto();

            vehicleDto.setTxtVehicleId(VehicleId);
            vehicleDto.setTxtEngineNum(EngineNumber);
            vehicleDto.setTxtOwnerId(Owner);
            vehicleDto.setTxtPlateNum(VehicleNumPlate);
            vehicleDto.setTxtVehicleType(VehicleType);
            vehicleDto.setTxtModelId(Model);

            List.add(vehicleDto);
        }
        return List;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM Vehicle WHERE VehicleId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static Optional<VehicleDto> search(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Vehicle WHERE VehicleId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, code);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String VehicleId = rs.getString(1);
            String EngineNumber = rs.getString(2);
            String Owner = rs.getString(3);
            String VehicleNumPlate = rs.getString(4);
            String VehicleType = rs.getString(5);
            String Model = rs.getString(6);


            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setTxtVehicleId(VehicleId);
            vehicleDto.setTxtEngineNum(EngineNumber);
            vehicleDto.setTxtOwnerId(Owner);
            vehicleDto.setTxtPlateNum(VehicleNumPlate);
            vehicleDto.setTxtVehicleType(VehicleType);
            vehicleDto.setTxtModelId(Model);


            return Optional.of(vehicleDto);

        } else {
            return Optional.empty();
        }

    }

    public static boolean update(VehicleDto updatedVehicleDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE Vehicle SET EngineNumber=?, Owner=?, VehicleNumPlate=?, VehicleType=?, Model=? WHERE VehicleId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, updatedVehicleDto.getTxtEngineNum());
            stm.setString(2, updatedVehicleDto.getTxtOwnerId());
            stm.setString(3, updatedVehicleDto.getTxtPlateNum());
            stm.setString(4, updatedVehicleDto.getTxtVehicleType());
            stm.setString(5, updatedVehicleDto.getTxtModelId());


            stm.setString(6, updatedVehicleDto.getTxtVehicleId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        }
    }

    public static ArrayList<String> getAllVehicleIds() {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT VehicleId FROM Vehicle");
             ResultSet rs = stm.executeQuery()) {

            ArrayList<String> vehicleIds = new ArrayList<>();
            while (rs.next()) {
                vehicleIds.add(rs.getString("VehicleId"));
            }
            return vehicleIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
