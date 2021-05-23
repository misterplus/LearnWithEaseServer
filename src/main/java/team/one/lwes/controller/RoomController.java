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
        long uid = loginDao.getUid(accid);
        //channelName is actually chatroom id cause we want to make things consistent
        Response resp = APIUtils.getRoomToken(uid, channelName).toResponse();
        //return uid here bc the client needs to join the room with it
        if (resp.isSuccess())
            resp.getInfo().set("uid", uid);
        return resp;
    }
}
