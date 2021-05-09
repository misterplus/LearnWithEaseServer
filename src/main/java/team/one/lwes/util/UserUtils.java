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

    public static boolean isEducationValid(int grade,int bak){
        if (grade == 0 && bak>=0 && bak <=7){
            return true;
        }else if (grade == 1 && bak>=0 && bak <=4){
            return true;
        }else if (grade == 2 && bak>=0 && bak <=4){
            return true;
        }else if (grade == 3 && bak>=0 && bak <=4){
            return true;
        }else if (grade == 4 && bak>=0 && bak <=4){
            return true;
        }else if (grade == 5 && bak>=0 && bak <=5){
            return true;
        }else if (grade == 6 && bak>=0 && bak <=4){
            return true;
        }else return grade == 7 && bak >= 0 && bak <= 5;
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

    public static boolean isSchoolValid(String school) {
        return SCHOOL_LIST.contains(school);
    }
}
