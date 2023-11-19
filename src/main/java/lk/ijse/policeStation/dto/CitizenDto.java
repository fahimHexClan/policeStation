package lk.ijse.policeStation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDto {
    private String CitizenId;
    private String ContactNumber;
    private String Dob;
    private String Gender;
    private String Name;
    private String Address;
    private byte[] imgview;
}
