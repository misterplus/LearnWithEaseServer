package team.one.lwes.dao;

import team.one.lwes.bean.LoginInfo;

public interface LoginInfoDao {
    String getToken(String accid);

    void saveLoginInfo(String accid, String token);

    void updateToken(String accid, String token);

    long getUid(String accid);

    LoginInfo getUser(String accid);
}
