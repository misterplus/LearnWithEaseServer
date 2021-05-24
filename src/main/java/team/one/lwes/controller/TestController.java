package team.one.lwes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.Constants;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/secret")
    public String getSecret() {
        return Constants.getInstance().APP_SECRET;
    }

    @RequestMapping("/key")
    public String getKey() {
        return Constants.getInstance().APP_KEY;
    }
}
