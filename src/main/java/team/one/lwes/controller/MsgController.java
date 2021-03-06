package team.one.lwes.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.Config;
import team.one.lwes.annotation.CC;
import team.one.lwes.bean.Response;
import team.one.lwes.bean.RoomInfo;
import team.one.lwes.bean.UserInfo;
import team.one.lwes.dao.impl.StudyRoomInfoDaoImpl;
import team.one.lwes.util.APIUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private StudyRoomInfoDaoImpl roomDao;

    @Autowired
    private Config config;

    @CC
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public Response receiveMsg(HttpServletRequest request, @RequestBody String content) {
        String contentType = request.getContentType();
        String appKey = request.getHeader("AppKey");
        String md5 = request.getHeader("MD5");
        String curTime = request.getHeader("CurTime");
        String checkSum = request.getHeader("CheckSum");
        String checkSumCal = SecureUtil.sha1(config.getAppSecret() + md5 + curTime);
        if (!contentType.equals("application/json; charset=utf-8") || !checkSumCal.equals(checkSum) || !SecureUtil.md5(content).equals(md5) || !appKey.equals(config.getAppKey())) {
            return new Response(414);
        }
        JSONObject json = new JSONObject(content);
        int eventType = json.getInt("eventType");
        String roomId = json.getStr("roomId");
        switch (eventType) {
            case 9: {
                String accid = json.getStr("accid");
                String event = json.getStr("event");
                if (event.equals("IN"))
                    new Thread(() -> saveRoom(accid, roomId)).start();
                else
                    new Thread(() -> {
                        removeRoom(roomId);
                        APIUtils.closeRoom(roomId, accid);
                    }).start();
                break;
            }
            default:
        }
        return new Response(200);
    }

    private void saveRoom(String accid, String roomId) {
        Response user = APIUtils.getUserInfo(accid);
        Response room = APIUtils.getRoomInfo(roomId);
        if (user.isSuccess() && room.isSuccess()) {
            UserInfo userInfo = JSONUtil.toBean(user.getUinfos().getJSONObject(0).getStr("ex"), UserInfo.class);
            RoomInfo roominfo = JSONUtil.toBean(room.getChatroom().getExt(), RoomInfo.class);
            roomDao.saveStudyRoomInfo(roomId, roominfo.getTimeStudy(), roominfo.getTimeRest(), roominfo.getContentStudy(), user.getUinfos().getJSONObject(0).getInt("gender"), userInfo.getProvince(), userInfo.getCity(), userInfo.getArea(), userInfo.getSchool(), accid);
        } else {
            saveRoom(accid, roomId);
        }
    }

    private void removeRoom(String roomId) {
        roomDao.removeStudyRoomInfo(roomId);
    }
}
