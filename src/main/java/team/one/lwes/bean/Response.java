package team.one.lwes.bean;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int code;
    private JSONObject info;
    private EnterRoomData chatroom;
    //token field is for video room token
    private String desc, token;
    private JSONArray uinfos, recs;

    public Response(int code, JSONArray recs) {
        this.code = code;
        this.recs = recs;
    }

    public Response(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Response(int code, JSONObject info) {
        this.code = code;
        this.info = info;
    }

    public Response(int code) {
        this.code = code;
    }

    public static Response invalidParamResp(String paramName) {
        return new Response(414, paramName + " is invalid");
    }

    public static Response authFailedResp() {
        return new Response(302, "username or password incorrect");
    }

    //not this, this is only for server side check
    @JsonIgnore
    public boolean isSuccess() {
        return code == 200;
    }
}
