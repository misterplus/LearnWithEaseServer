package team.one.lwes.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String accid, token, name;
    private int gender;
    private UserInfo ex;
}
