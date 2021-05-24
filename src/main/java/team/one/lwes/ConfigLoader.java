package team.one.lwes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigLoader {
    @Autowired
    private Config config;

    @Autowired
    private static ConfigLoader instance;

    public static ConfigLoader getInstance() {
        return instance;
    }

    public Config getConfig() {
        return config;
    }
}
