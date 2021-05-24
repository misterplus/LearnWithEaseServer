package team.one.lwes;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Constants {

    public static final String APP_SECRET = getSecret();

    private static String getSecret() {
        try {
            ClassPathResource resource = new ClassPathResource("application.properties");
            InputStream inputStream = resource.getInputStream();
            Properties properties=new Properties();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
            properties.load(reader);
            return properties.getProperty("lwe.app.secret");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String APP_KEY = "57b4c463e5a9cca9ca364a8d0d8c9f39";
}
