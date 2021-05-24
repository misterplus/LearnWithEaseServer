package team.one.lwes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

@Controller
@EnableAutoConfiguration
public class Constants {
    private static final Constants instance = new Constants();

    public static Constants getInstance() {
        return instance;
    }

    @Value("${lwe.app.secret}")
    public String APP_SECRET;

    public String APP_KEY = "57b4c463e5a9cca9ca364a8d0d8c9f39";
}
