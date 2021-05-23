package team.one.lwes.bean;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NERtcResponse {
    private int code;
    private String token;

    public boolean isSuccess() {
        return code == 200;
    }

    public Response toResponse() {
        JSONObject json = new JSONObject();
        if (token != null)
            json.set("token", token);
        return new Response(code, json);
    }
}
