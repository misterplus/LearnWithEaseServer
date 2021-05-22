package team.one.lwes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.bean.Response;
import team.one.lwes.dao.impl.LoginInfoDaoImpl;
import team.one.lwes.util.APIUtils;
import team.one.lwes.util.UserUtils;

import java.util.UUID;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private LoginInfoDaoImpl loginDao;

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public Response getToken(@RequestParam @NonNull String username, @RequestParam @NonNull String password, @RequestParam @NonNull String channelName) {
        String accid = UserUtils.getAccid(username);
        boolean authPassed = UserUtils.auth(loginDao, accid, password);
        if (!authPassed)
            return new Response(302, "username or password incorrect");
        UUID uuid = UUID.nameUUIDFromBytes(accid.getBytes());
        long uid = uuid.getMostSignificantBits();
        return APIUtils.getRoomToken(uid, channelName);
    }
}
