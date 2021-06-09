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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Response fetch(@CurrentUser LoginInfo user) {
        //get recommended rooms for current user
        JSONObject recs = new JSONObject();
        Set<String> recIds = new HashSet<>();
        Response userResp = APIUtils.getUserInfo(user.getAccid());
        UserInfo userInfo = JSONUtil.toBean(userResp.getUinfos().getJSONObject(0).getStr("ex"), UserInfo.class);
        Preference pref = userInfo.getPref();
        if (!userInfo.getSchool().equals("") && pref.isSameSchool()) {
            // add to response, add to set for deduplication
            recs.set("school", new JSONArray(roomDao.getRoomsBySchool(userInfo.getSchool())));
            recIds.addAll(roomDao.getRoomsBySchool(userInfo.getSchool()));
        }
        //deduplicate
        List<String> contentStudy = roomDao.getRoomsByContentStudy(userInfo.getPref().getContentStudy());
        contentStudy.removeAll(recIds);
        //add to response, add to set
        recs.set("content", new JSONArray(contentStudy));
        recIds.addAll(contentStudy);

        if (pref.isSameCity()) {
            List<String> sameCity = roomDao.getRoomsByPlace(userInfo.getProvince(), userInfo.getCity(), userInfo.getArea());
            sameCity.removeAll(recIds);
            recs.set("city", new JSONArray(sameCity));
            recIds.addAll(sameCity);
        }

        if (pref.isSameGender()) {
            List<String> sameGender = roomDao.getRoomsByGender(userResp.getUinfos().getJSONObject(0).getInt("gender"));
            sameGender.removeAll(recIds);
            recs.set("gender", new JSONArray(sameGender));
            recIds.addAll(sameGender);
        }

        return new Response(200, recs);
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
}
