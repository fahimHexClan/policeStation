package lk.ijse.policeStation.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenTm {
    private String CitizenId;
    private String name;
    private String address;
    private String contactNumber;
    private String gender;
    private String Dob;

}
