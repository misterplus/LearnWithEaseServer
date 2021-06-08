package team.one.lwes.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.Config;
import team.one.lwes.annotation.CC;
import team.one.lwes.bean.Response;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/msg")
public class MsgController {

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
        if (!contentType.equals("application/json") || !appKey.equals(config.getAppKey()) || !checkSumCal.equals(checkSum) || !SecureUtil.md5(content).equals(md5)) {
            return new Response(414);
        }
        JSONObject json = new JSONObject(content);
        System.out.println(json.get("eventType"));
        return new Response(200);
    }
}
