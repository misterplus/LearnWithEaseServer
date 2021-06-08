package team.one.lwes.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomInfo {
    private int timeStudy, timeRest, contentStudy, gender;
    private String roomId, province, city, area, school;
}
