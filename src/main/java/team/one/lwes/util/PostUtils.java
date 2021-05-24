package team.one.lwes.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.one.lwes.Config;

import java.time.Instant;

@Component
public class PostUtils {

    public static Config config;

    @Autowired
    public PostUtils(Config config) {
        PostUtils.config = config;
    }

    private static String getTimeStamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    public static HttpRequest getBasicPost(@NotNull String url) {
        String nonce = RandomUtil.randomNumbers(8);
        String curTime = getTimeStamp();
        return HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("AppKey", config.getAppKey())
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("CheckSum", getCheckSum(nonce, curTime));
    }

    private static String getCheckSum(String nonce, String curTime) {
        return SecureUtil.sha1(config.getAppSecret() + nonce + curTime);
    }
}
