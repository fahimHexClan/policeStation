package lk.ijse.policeStation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesDto {
    private byte[] ImgEmployee;
    private String TxtAddress;
    private String TxtContactNumber;
    private String TxtDob;
    private String TxtEmpId;
    private String TxtEmpName;
    private String TxtEmpType;
    private String TxtGender;
    private String TxtOfficerId;
    private String TxtRank;
    private String TxtUsrId;

    public byte[] getImageData() {
        return ImgEmployee;
    }
}
