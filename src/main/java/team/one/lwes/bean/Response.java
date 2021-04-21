package team.one.lwes.bean;

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
    private Object info;
    private String desc;

    public Response(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Response(int code, Object info) {
        this.code = code;
        this.info = info;
    }

    public static Response invalidParamResp(String paramName) {
        return new Response(414, paramName + " is invalid");
    }
}
