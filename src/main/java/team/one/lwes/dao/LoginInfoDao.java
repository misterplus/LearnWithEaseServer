package team.one.lwes.dao;

public interface LoginInfoDao {
    public String getToken(String accid);
    public boolean saveLoginInfo(String accid, String token);
}
