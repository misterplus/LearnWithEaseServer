package team.one.lwes.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import org.jetbrains.annotations.NotNull;
import team.one.lwes.Constants;

import java.time.Instant;

public class PostUtils {

    private static String getTimeStamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    public static HttpRequest getBasicPost(@NotNull String url) {
        String nonce = RandomUtil.randomNumbers(8);
        String curTime = getTimeStamp();
        return HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("AppKey", Constants.getInstance().APP_KEY)
                .header("Nonce", nonce)
                .header("CurTime", curTime)
                .header("CheckSum", getCheckSum(nonce, curTime));
    }

    private static String getCheckSum(String nonce, String curTime) {
        return SecureUtil.sha1(Constants.getInstance().APP_SECRET + nonce + curTime);
    }
}
