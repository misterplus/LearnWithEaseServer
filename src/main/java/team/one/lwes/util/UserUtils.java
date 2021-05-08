package team.one.lwes.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.one.lwes.bean.Area;
import team.one.lwes.bean.City;
import team.one.lwes.bean.Province;
import team.one.lwes.bean.UserInfo;
import org.jetbrains.annotations.NotNull;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    private static Logger logger = LoggerFactory.getLogger(UserUtils.class);

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

    public static boolean isAgeValid(int age) {
        return TextUtils.isLegalAge(age);
    }

    public static boolean isPlacedValid(String province,String city,String area) {
        int num = 0;
        FileReader fileReader = new FileReader("classpath:china_city_data.json");
        String s = fileReader.readString();
        List<Province> provinces = JSONUtil.toList(s,Province.class);
        logger.info("this is "+ provinces);
        for (Province value : provinces) {
            logger.info("this is "+ value);
            List<City> cities = JSONUtil.toList(value.getCityList().toString(), City.class);
            if (value.getName().equals(province)){
                num++;
            }
            for (City item : cities) {
                List<Area> areas = JSONUtil.toList(item.getCityList().toString(), Area.class);
                if (item.getName().equals(city)){
                    num++;
                }
                for (Area element : areas) {
                    if (element.getName().equals(area)) {
                        num++;
                    }
                }
            }
        }
        if (num==3 && !province.equals("") && !city.equals("") && !area.equals("")){
            logger.info("this is "+ provinces);
            return true;
        } else {
            return false;
        }

//        JSONArray jsonArray = new JSONArray(s);
//        JSONObject jsonObject = jsonArray.toJSONObject(jsonArray);
    }

}
