package team.one.lwes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team.one.lwes.bean.LoginInfo;
import team.one.lwes.dao.LoginInfoDao;

import java.util.List;

@Repository
public class LoginInfoDaoImpl implements LoginInfoDao {

    private static final LoginInfoDaoImpl instance = new LoginInfoDaoImpl();

    public static LoginInfoDaoImpl getInstance() {
        return instance;
    }

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
    public boolean saveLoginInfo(String accid, String token) {
        int affected = db.update("insert into login_info values(?,?)", accid, token);
        return affected != 0;
    }
}
