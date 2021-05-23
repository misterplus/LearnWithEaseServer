package team.one.lwes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import team.one.lwes.bean.LoginInfo;
import team.one.lwes.dao.LoginInfoDao;

import java.util.List;

@Component
public class LoginInfoDaoImpl implements LoginInfoDao {

    @Autowired
    private JdbcTemplate db;

    @Override
    public String getToken(String accid) {
        List<LoginInfo> info = db.query("select token from login_info where accid = ?", new BeanPropertyRowMapper(LoginInfo.class), accid);
        if (info.size() > 0)
            return info.get(0).getToken();
        return null;
    }

    @Override
    public void saveLoginInfo(String accid, String token) {
        db.update("insert into login_info values(?,?)", accid, token);
    }

    @Override
    public void updateToken(String accid, String token) {
        db.update("update login_info set token = ? where accid = ?", token, accid);
    }

    @Override
    public long getUid(String accid) {
        List<LoginInfo> info = db.query("select uid from login_info where accid = ?", new BeanPropertyRowMapper(LoginInfo.class), accid);
        if (info.size() > 0)
            return info.get(0).getUid();
        return -1;
    }

}
