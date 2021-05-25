package team.one.lwes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.one.lwes.dao.impl.LoginInfoDaoImpl;

@Component
public class LWEAuthService implements AuthService {

    @Autowired
    private LoginInfoDaoImpl loginDao;

    @Override
    public boolean auth(String accid, String token) {
        String savedToken = loginDao.getToken(accid);
        return token.equals(savedToken);
    }

}
