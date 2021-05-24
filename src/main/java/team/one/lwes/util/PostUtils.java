package team.one.lwes.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.time.Instant;

@EnableAutoConfiguration
public class PostUtils {

    private static final String APP_KEY = "57b4c463e5a9cca9ca364a8d0d8c9f39";

    @Value("${lwe.app.secret}")
    private static String APP_SECRET;

    private static String getTimeStamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    public static HttpRequest getBasicPost(@NotNull String url) {
        String nonce = RandomUtil.randomNumbers(8);
        String curTime = getTimeStamp();
        return HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("AppKey", APP_KEY)
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("CheckSum", getCheckSum(nonce, curTime));
    }

    private static String getCheckSum(String nonce, String curTime) {
        return SecureUtil.sha1(APP_SECRET + nonce + curTime);
    }
}
