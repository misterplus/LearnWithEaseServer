package team.one.lwes.dao;

public interface LoginInfoDao {
    public String getToken(String accid);
    public void saveLoginInfo(String accid, String token);
    public void updateToken(String accid, String token);
}
