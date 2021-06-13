package team.one.lwes.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import team.one.lwes.LearnWithEaseServerApplication;
import team.one.lwes.bean.Response;
import team.one.lwes.bean.RoomInfo;
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

    public static Response getRoomToken(long uid, @NotNull String roomId) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/user/getToken.action")
                .form(
                        "uid", uid,
                        "channelName", roomId,
                        "repeatUse", false,
                        "expireAt", 30
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }

    public static Response createChatRoom(@NonNull String creator, @NonNull String name, @NonNull RoomInfo ext) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/chatroom/create.action")
                .form(
                        "creator", creator,
                        "name", name,
                        "ext", JSONUtil.toJsonStr(ext)
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }

    public static Response getUserInfo(@NotNull String accid) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/user/getUinfos.action")
                .form(
                        "accids", "[\"" + accid + "\"]"
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }

    public static Response getRoomInfo(@NotNull String roomid) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/chatroom/get.action")
                .form(
                        "roomid", roomid
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }

    public static Response closeRoom(@NotNull String roomid, @NotNull String operator) {
        HttpResponse resp = PostUtils.getBasicPost("https://api.netease.im/nimserver/chatroom/toggleCloseStat.action")
                .form(
                        "roomid", roomid,
                        "operator", operator,
                        "valid", false
                )
                .timeout(5000)
                .execute();
        return JSONUtil.toBean(resp.body(), Response.class);
    }
}
