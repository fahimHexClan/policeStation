package lk.ijse.policeStation.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTm {
    private String TxtEngineNum;
    private String TxtModelId;
    private String TxtOwnerId;
    private String TxtPlateNum;
    private String TxtVehicleId;
    private String TxtVehicleType;
}
