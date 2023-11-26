package lk.ijse.policeStation.model;

import lk.ijse.policeStation.DB.DatabaseConnection;
import lk.ijse.policeStation.dto.CitizenDto;
import lk.ijse.policeStation.dto.ComplaintDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComplaintModel {

    public static boolean save(ComplaintDto complaintDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "insert into Complaint(ComplaintId ,description,date,CitizenId ,OfficerId,TypeOfIncident,LocationOfIncident,Evidence,WitnessInformation,SuspectName,SuspectAddress,SuspectContactNumber,StatusOfTheComplaint) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, complaintDto.getComplainId());
        stm.setString(2, complaintDto.getDescriptionOfIncident());
        stm.setString(3, complaintDto.getDate());
        stm.setString(4, complaintDto.getCitizenId());
        stm.setString(5, complaintDto.getOfficerId());
        stm.setString(6, complaintDto.getTypeOfIncident());
        stm.setString(7, complaintDto.getLocationOfIncident());
        stm.setString(8, complaintDto.getEvidence());
        stm.setString(9, complaintDto.getWitnessInformation());
        stm.setString(10, complaintDto.getSuspectName());
        stm.setString(11, complaintDto.getSuspectAddress());
        stm.setString(12, complaintDto.getSuspectContactNumber());
        stm.setString(13, complaintDto.getStatusOfTheComplaint());

        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "DELETE FROM Complaint WHERE ComplaintId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        int affectedRows = stm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Optional<ComplaintDto> search(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Complaint WHERE ComplaintId=?";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            String code = rs.getString(1);
            String description = rs.getString(2);
            String date = rs.getString(3);
            String CitizenId = rs.getString(4);
            String officerId = rs.getString(5);
            String TypeofIncident = rs.getString(6);
            String LocationofIncident = rs.getString(7);
            String Evidence = rs.getString(8);
            String WitnessInformation = rs.getString(9);
            String SuspectName = rs.getString(10);
            String SuspectAddress = rs.getString(11);
            String SuspectContactNumber = rs.getString(12);
            String StatusOfTheComplaint = rs.getString(13);

            ComplaintDto complaintDto = new ComplaintDto();
            complaintDto.setComplainId(code);
            complaintDto.setDescriptionOfIncident(description);
            complaintDto.setDate(date);
            complaintDto.setCitizenId(CitizenId);
            complaintDto.setOfficerId(officerId);
            complaintDto.setTypeOfIncident(TypeofIncident);
            complaintDto.setLocationOfIncident(LocationofIncident);
            complaintDto.setEvidence(Evidence);
            complaintDto.setWitnessInformation(WitnessInformation);
            complaintDto.setSuspectName(SuspectName);
            complaintDto.setSuspectAddress(SuspectAddress);
            complaintDto.setSuspectContactNumber(SuspectContactNumber);
            complaintDto.setStatusOfTheComplaint(StatusOfTheComplaint);


            return Optional.of(complaintDto);

        } else {
            return Optional.empty();
        }
    }

    public static boolean update(ComplaintDto complaintDto) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "UPDATE Complaint SET description=?, date=?, CitizenId=?, OfficerId=?, TypeOfIncident=?, LocationOfIncident=?, Evidence=?, WitnessInformation=?, SuspectName=?, SuspectAddress=?, SuspectContactNumber=?, StatusOfTheComplaint=? WHERE ComplaintId=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, complaintDto.getDescriptionOfIncident());
            stm.setString(2, complaintDto.getDate());
            stm.setString(3, complaintDto.getCitizenId());
            stm.setString(4, complaintDto.getOfficerId());
            stm.setString(5, complaintDto.getTypeOfIncident());
            stm.setString(6, complaintDto.getLocationOfIncident());
            stm.setString(7, complaintDto.getEvidence());
            stm.setString(8, complaintDto.getWitnessInformation());
            stm.setString(9, complaintDto.getSuspectName());
            stm.setString(10, complaintDto.getSuspectAddress());
            stm.setString(11, complaintDto.getSuspectContactNumber());
            stm.setString(12, complaintDto.getStatusOfTheComplaint());
            stm.setString(13, complaintDto.getComplainId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;
        }
    }


}
