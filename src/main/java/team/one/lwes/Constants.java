package team.one.lwes;

public class Constants {

    private static final Constants instance = new Constants();

    public static Constants getInstance() {
        return instance;
    }

    private final ConstantLoader loader = new ConstantLoader();

    public String APP_SECRET = loader.APP_SECRET;

    public String APP_KEY = "57b4c463e5a9cca9ca364a8d0d8c9f39";
}
