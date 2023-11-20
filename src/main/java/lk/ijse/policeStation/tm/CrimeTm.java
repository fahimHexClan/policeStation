package lk.ijse.policeStation.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrimeTm {
        private  String CrimeID;
        private String CriminalRecord;
        private String Date;
        private String Deatails;
        private String Injuries;
        private String TxtLocation;
        private String TxtMotiveReson;
        private String TxtStatus;
        private String TxtTypeOfCrime;
        private String TxtWeponUsed;
    }


