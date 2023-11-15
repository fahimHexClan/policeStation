package lk.ijse.policeStation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {
    private String CitizenId;
    private String ComplainId;
    private String Date;
    private String DescriptionOfIncident;
    private String Evidence;
    private String LocationOfIncident;
    private String OfficerId;
    private String StatusOfTheComplaint;
    private String SuspectAddress;
    private String SuspectContactNumber;
    private String SuspectName;
    private String TxtTypeOfIncident;
    private String TxtWitnessInformation;
}
