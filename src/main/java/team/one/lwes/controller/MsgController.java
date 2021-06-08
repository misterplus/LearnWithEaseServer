package team.one.lwes.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.Config;
import team.one.lwes.LearnWithEaseServerApplication;
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
        Logger logger= LoggerFactory.getLogger(LearnWithEaseServerApplication.class);
        String contentType = request.getContentType();
        String appKey = request.getHeader("AppKey");
        String md5 = request.getHeader("MD5");
        String curTime = request.getHeader("CurTime");
        String checkSum = request.getHeader("CheckSum");
        String checkSumCal = SecureUtil.sha1(config.getAppSecret() + md5 + curTime);
        boolean b1 = !contentType.equals("application/json");
        boolean b2 = !checkSumCal.equals(checkSum);
        boolean b3 = !SecureUtil.md5(content).equals(md5);
        boolean b4 = !appKey.equals(config.getAppKey());
        logger.info(b1 + " " + b2 + " " + b3 + " " + b4);
        if (b1 || b2 || b3 || b4) {
            return new Response(414);
        }
        JSONObject json = new JSONObject(content);

        logger.info("debug:");
        logger.info(json.getStr("eventType"));
        return new Response(200);
    }
}
