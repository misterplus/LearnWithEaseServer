package team.one.lwes.controller;

import cn.hutool.json.JSONArray;
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

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private StudyRoomInfoDaoImpl roomDao;

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
    @RequestMapping(value = "/fetch", method = RequestMethod.POST)
    public Response fetch(@RequestBody User user) {
        //get recommended rooms for current user
        UserInfo userInfo = user.getEx();
        String gender = String.valueOf(user.getGender());
        Preference pref = userInfo.getPref();
        String school = !userInfo.getSchool().equals("") && pref.isSameSchool() ? userInfo.getSchool() : "%%";
        int contentStudy = pref.getContentStudy();
        String province, city, area;
        if (pref.isSameCity()) {
            province = userInfo.getProvince();
            city = userInfo.getCity();
            area = userInfo.getArea();
        }
        else
            province = city = area = "%%";
        gender = pref.isSameGender() ? gender : "%%";
        List<StudyRoomInfo> info = roomDao.fetchRecs(pref.getTimeStudy(), pref.getTimeRest(), contentStudy, gender, province, city, area, school);
        return new Response(200, new JSONArray(info));
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
        return chatroom;
    }

    @Auth
    @RequestMapping(value = "/updateTimeStudy", method = RequestMethod.POST)
    public Response updateTimeStudy(@CurrentUser LoginInfo user, @RequestParam String roomId, @RequestParam int timeStudy) {
        StudyRoomInfo info = roomDao.getRoom(roomId);
        if (info == null)
            return new Response(404, "room not found");
        if (!info.getCreator().equals(user.getAccid()))
            return Response.authFailedResp();
        roomDao.updateTimeStudy(roomId, timeStudy);
        return new Response(200);
    }

    @Auth
    @RequestMapping(value = "/updateTimeRest", method = RequestMethod.POST)
    public Response updateTimeRest(@CurrentUser LoginInfo user, @RequestParam String roomId, @RequestParam int timeRest) {
        StudyRoomInfo info = roomDao.getRoom(roomId);
        if (info == null)
            return new Response(404, "room not found");
        if (!info.getCreator().equals(user.getAccid()))
            return Response.authFailedResp();
        roomDao.updateTimeRest(roomId, timeRest);
        return new Response(200);
    }

    @Auth
    @RequestMapping(value = "/updateContentStudy", method = RequestMethod.POST)
    public Response updateContentStudy(@CurrentUser LoginInfo user, @RequestParam String roomId, @RequestParam int contentStudy) {
        StudyRoomInfo info = roomDao.getRoom(roomId);
        if (info == null)
            return new Response(404, "room not found");
        if (!info.getCreator().equals(user.getAccid()))
            return Response.authFailedResp();
        roomDao.updateContentStudy(roomId, contentStudy);
        return new Response(200);
    }
}
