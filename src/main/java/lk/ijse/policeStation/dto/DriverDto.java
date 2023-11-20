package lk.ijse.policeStation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class DriverDto {
    private String CmbVehicleId;
    private String TxtAddress;
    private String TxtContactNumber;
    private String TxtDob;
    private String TxtDriverId;
    private String TxtDriverName;
    private String TxtGender;
    private String TxtLicenseNumber;

}
