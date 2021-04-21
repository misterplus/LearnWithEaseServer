package team.one.lwes.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.one.lwes.enums.Background;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int age;
    private Background bak;
    private int grade;
    private boolean status;
    //TODO: city, probably a long
    //TODO: school, probably a long too
}
