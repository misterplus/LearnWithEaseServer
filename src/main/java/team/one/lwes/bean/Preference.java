package team.one.lwes.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preference {
    private int timeStudy, timeRest, contentStudy;
    private boolean sameCity, sameSchool, sameGender, newRoomFirst;
}
