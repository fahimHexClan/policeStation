package lk.ijse.policeStation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliceReportDto {
    public String CmdUserIds;
    public String TxtDate;
    public String TxtReportId;
    public String TxtDescription;
    public String CmdCitizenIds;
}
