package team.one.lwes.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.annotation.Auth;
import team.one.lwes.annotation.CurrentUser;
import team.one.lwes.bean.LoginInfo;
import team.one.lwes.bean.Response;
import team.one.lwes.util.APIUtils;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Auth
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public Response getToken(@CurrentUser LoginInfo user, @RequestParam @NonNull String channelName) {
        long uid = user.getUid();
        //channelName is actually chatroom id cause we want to make things consistent
        Response resp = APIUtils.getRoomToken(uid, channelName).toResponse();
        //return uid here bc the client needs to join the room with it
        if (resp.isSuccess())
            resp.getInfo().set("uid", uid);
        return resp;
    }
}
