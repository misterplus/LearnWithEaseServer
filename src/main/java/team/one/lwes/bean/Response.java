package team.one.lwes.bean;

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
    private String desc;

    public Response(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Response(int code, JSONObject info) {
        this.code = code;
        this.info = info;
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
