package team.one.lwes.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import team.one.lwes.bean.Response;
import team.one.lwes.bean.User;

public class APIUtils {

    public static Response register(@NotNull User user) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/user/create.action")
                .form(
                        "accid", user.getUsername(),
                        "token", user.getPassword(),
                        "name", user.getName(),
                        "gender", user.getGender(),
                        "ex", JSONUtil.toJsonStr(user.getEx())
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }

    public static Response update(@NotNull String accid, @NotNull String token) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/user/update.action")
                .form(
                        "accid", accid,
                        "token", token
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }
}
