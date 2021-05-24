package team.one.lwes;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "lwe")
public class Config {

    private String appSecret;

    private String appKey;
}
