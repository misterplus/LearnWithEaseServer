package team.one.lwes.dao;

public interface LoginInfoDao {
    String getToken(String accid);

    void saveLoginInfo(String accid, String token);

    void updateToken(String accid, String token);
}
