package team.one.lwes.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.Properties;

public class PostUtils {

    private static final String APP_KEY = "57b4c463e5a9cca9ca364a8d0d8c9f39";
    private static final String APP_SECRET = getAppSecret();

    private static String getAppSecret() {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(System.getProperty("user.home") + "\\.gradle\\gradle.properties").getInputStream());
            return prop.getProperty("lwes.secret");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

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
