package lk.ijse.policeStation.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliceReportTm {
    public String CmdUserIds;
    public String TxtDate;
    public String TxtReportId;
    public String TxtDescription;
    public String CmdCitizenIds;
}
