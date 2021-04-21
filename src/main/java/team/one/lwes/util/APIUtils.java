package team.one.lwes.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import team.one.lwes.bean.Response;

public class APIUtils {

    public static Response register(@NotNull String accid, @NotNull String token) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/user/create.action")
                .form(
                        "accid", accid,
                        "token", token
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }
}
