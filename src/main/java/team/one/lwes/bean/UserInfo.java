package team.one.lwes.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.one.lwes.enums.Background;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int age, grade;
    private Background bak;
    private boolean status; //true = graduated
    private String province, city, area, school; // ids
}
