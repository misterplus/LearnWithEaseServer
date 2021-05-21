package team.one.lwes.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class UserUtils {

    private static final JSONArray CITY_LIST = getCityList();
    private static final List<String> SCHOOL_LIST = getSchoolList();

    private static List<String> getSchoolList() {
        try {
            ClassPathResource resource = new ClassPathResource("school_data.txt");
            return new FileReader(resource.getFile()).readLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAccid(@NotNull String username) {
        return username.toLowerCase();
    }

    public static String getToken(@NotNull String username, @NotNull String password) {
        return SecureUtil.md5(username + password);
    }

    public static boolean isUsernameValid(@NotNull String username) {
        return TextUtils.isLegalUsername(username) && username.length() >= 6 && username.length() <= 16;
    }

    public static boolean isPasswordValid(@NotNull String password) {
        return TextUtils.isLegalPassword(password) && TextUtils.getPasswordComplexity(password) > 1 && password.length() >= 6 && password.length() <= 16;
    }

    public static boolean isEducationValid(int bak, int grade) {
        if (grade < 0)
            return false;
        switch (bak) {
            case 0:
                return grade < 7;
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
                return grade < 4;
            case 5:
            case 7:
                return grade < 5;
            default:
                return false;
        }
    }

    public static boolean isCityValid(String province, String city, String area) {
        for (JSONObject p : CITY_LIST.jsonIter()) {
            if (p.get("name").equals(province)) {
                JSONArray cities = p.getJSONArray("cityList");
                for (JSONObject c : cities.jsonIter()) {
                    if (c.get("name").equals(city)) {
                        JSONArray areas = c.getJSONArray("cityList");
                        for (JSONObject a : areas.jsonIter()) {
                            if (a.get("name").equals(area)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static JSONArray getCityList() {
        try {
            ClassPathResource resource = new ClassPathResource("china_city_data.json");
            return new JSONArray(new FileReader(resource.getFile()).readString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSchoolValid(int bak, String school) {
        return bak > 3 ? SCHOOL_LIST.contains(school) : TextUtils.isEmpty(school);
    }

    public static boolean isTimeStudyValid(int timeStudy) {
        return timeStudy >= 0 && timeStudy < 7;
    }

    public static boolean isTimeRestValid(int timeRest) {
        return timeRest >= 0 && timeRest < 5;
    }

    public static boolean isContentStudyValid(int contentStudy) {
        return contentStudy >= 0 && contentStudy < 13;
    }
}
