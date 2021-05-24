package team.one.lwes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.ConfigLoader;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/secret")
    public String getSecret() {
        return ConfigLoader.getInstance().getConfig().getAppSecret();
    }
}
