package team.one.lwes.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import team.one.lwes.annotation.Auth;
import team.one.lwes.annotation.CurrentUser;
import team.one.lwes.bean.*;
import team.one.lwes.dao.impl.StudyRoomInfoDaoImpl;
import team.one.lwes.util.APIUtils;
import team.one.lwes.util.UserUtils;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private StudyRoomInfoDaoImpl roomInfoDao;

    @Auth
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public Response getToken(@CurrentUser LoginInfo user, @RequestParam @NonNull String roomId) {
        long uid = user.getUid();
        //channelName is actually chatroom id cause we want to make things consistent
        Response resp = APIUtils.getRoomToken(uid, roomId);
        //return uid here bc the client needs to join the room with it
        if (resp.isSuccess())
            resp.setInfo(new JSONObject().set("uid", uid));
        return resp;
    }

    @Auth
    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    public Response fetch(@CurrentUser LoginInfo user) {
        return null;
    }

    @Auth
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response create(@CurrentUser LoginInfo user, @RequestBody @NonNull RoomBasic room) {
        String accid = user.getAccid();
        RoomInfo ext = room.getExt();
        String name = room.getName();
        int maxUsers = ext.getMaxUsers();
        int timeStudy = ext.getTimeStudy();
        int timeRest = ext.getTimeRest();
        int contentStudy = ext.getContentStudy();
        if (maxUsers > 4 || maxUsers < 2)
            return Response.invalidParamResp("maxUsers");
        if (!UserUtils.isTimeRestValid(timeRest))
            return Response.invalidParamResp("timeRest");
        if (!UserUtils.isTimeStudyValid(timeStudy))
            return Response.invalidParamResp("timeStudy");
        if (!UserUtils.isContentStudyValid(contentStudy))
            return Response.invalidParamResp("contentStudy");
        Response chatroom = APIUtils.createChatRoom(accid, name, ext);
        if (!chatroom.isSuccess()) //failed to create chatroom, abort
            return chatroom;
        EnterRoomData enterRoomData = chatroom.getChatroom();
        String roomId = enterRoomData.getRoomid(); // get returned roomId
        long uid = user.getUid();
        Response roomToken = APIUtils.getRoomToken(uid, roomId); //token for video room
        if (!roomToken.isSuccess())
            return roomToken; //this shouldn't happen tho
        enterRoomData.setToken(roomToken.getToken());
        enterRoomData.setUid(uid);
        //TODO: add room info to database for later fetching
        new Thread(() -> saveRoomToDB(accid, roomId, room)).start();

        return chatroom;
    }

    private void saveRoomToDB(String accid, String roomId, RoomBasic room) {
        Response user = APIUtils.getUserInfo(accid);
        if (user.isSuccess()) {
            UserInfo userInfo = JSONUtil.toBean(user.getUinfos().getJSONObject(0).getStr("ex"), UserInfo.class);
            roomInfoDao.saveStudyRoomInfo(roomId, room.getExt().getTimeStudy(), room.getExt().getTimeRest(), room.getExt().getContentStudy(), user.getInfo().getInt("gender"), userInfo.getProvince(), userInfo.getCity(), userInfo.getArea(), userInfo.getSchool());
        } else {
            saveRoomToDB(accid, roomId, room);
        }
    }
}
