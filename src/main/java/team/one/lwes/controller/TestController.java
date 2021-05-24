package team.one.lwes.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.Constants;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${lwe.app.secret}")
    private String APP_SECRET;

    @RequestMapping("/secret")
    public String getSecret() {
        return APP_SECRET;
    }

    @RequestMapping("/key")
    public String getKey() {
        return Constants.APP_KEY;
    }
}
