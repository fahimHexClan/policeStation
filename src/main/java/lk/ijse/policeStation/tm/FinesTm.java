package lk.ijse.policeStation.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinesTm {
    private String CmbDriverId;
    private Double TxtFinesAmount;
    private String TxtFinesDate;
    private String TxtFinesDescrip;
    private String TxtFinesId;
}
