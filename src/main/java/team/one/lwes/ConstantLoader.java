package team.one.lwes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ConstantLoader {

    @Value("${lwe.app.secret}")
    public String APP_SECRET;

    @RequestMapping("/secret")
    public String getSecret() {
        return Constants.getInstance().APP_SECRET;
    }
}
