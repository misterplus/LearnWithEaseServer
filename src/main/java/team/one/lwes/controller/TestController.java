package team.one.lwes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.one.lwes.Config;
import team.one.lwes.util.PostUtils;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/secret")
    public String getSecret() {
        return PostUtils.config.getAppSecret();
    }
}
