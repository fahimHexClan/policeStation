package lk.ijse.policeStation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrimeDto {
    private  String CrimeID;
    private String Deatails;
    private String Date;
    private String TxtTypeOfCrime;
    private String TxtLocation;
    private String CriminalRecord;
    private String Injuries;
    private String TxtMotiveReson;
    private String TxtWeponUsed;
    private String TxtStatus;




}
