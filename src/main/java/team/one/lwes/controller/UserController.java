package team.one.lwes.controller;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import team.one.lwes.bean.*;
import team.one.lwes.dao.impl.LoginInfoDaoImpl;
import team.one.lwes.util.APIUtils;
import team.one.lwes.util.UserUtils;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginInfoDaoImpl loginDao;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody @NonNull User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String name = user.getName();
        UserInfo ex = user.getEx();
        int age = ex.getAge();
        int grade = ex.getGrade();
        int bak = ex.getBak();
        String province = ex.getProvince();
        String city = ex.getCity();
        String area = ex.getArea();
        String school = ex.getSchool();
        Preference pref = ex.getPref();
        int timeStudy = pref.getTimeStudy();
        int timeRest = pref.getTimeRest();
        int contentStudy = pref.getContentStudy();
        if (!UserUtils.isUsernameValid(username))
            return Response.invalidParamResp("username");
        else if (!UserUtils.isPasswordValid(password))
            return Response.invalidParamResp("password");
        else if (name.isEmpty() || name.length() > 16)
            return Response.invalidParamResp("name");
        else if (age < 1 || age > 120)
            return Response.invalidParamResp("age");
        else if (!UserUtils.isCityValid(province, city, area))
            return Response.invalidParamResp("city");
        else if (!UserUtils.isEducationValid(bak, grade))
            return Response.invalidParamResp("education");
        else if (!UserUtils.isSchoolValid(bak, school))
            return Response.invalidParamResp("school");
        else if (!UserUtils.isTimeStudyValid(timeStudy))
            return Response.invalidParamResp("timeStudy");
        else if (!UserUtils.isTimeRestValid(timeRest))
            return Response.invalidParamResp("timeRest");
        else if (!UserUtils.isContentStudyValid(contentStudy))
            return Response.invalidParamResp("contentStudy");
        user.setUsername(UserUtils.getAccid(username));
        user.setPassword(UserUtils.getToken(user.getUsername(), password));
        Response resp = APIUtils.register(user);
        if (resp.isSuccess())
            loginDao.saveLoginInfo(user.getUsername(), user.getPassword());
        return resp; // credentials or error msg
    }

    // May produce invalid credentials, login will NOT always pass
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public Response convert(@RequestParam @NonNull String username, @RequestParam @NonNull String password) {
        if (!UserUtils.isUsernameValid(username))
            return Response.invalidParamResp("username");
        else if (!UserUtils.isPasswordValid(password))
            return Response.invalidParamResp("password");
        String accid = UserUtils.getAccid(username);
        String token = UserUtils.getToken(accid, password);
        return new Response(200, new JSONObject(new LoginInfo(accid, token, null)));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestParam @NonNull String username, @RequestParam @NonNull String oldPassword, @RequestParam @NonNull String newPassword) {
        if (!UserUtils.isUsernameValid(username))
            return Response.invalidParamResp("username");
        else if (!UserUtils.isPasswordValid(oldPassword))
            return Response.invalidParamResp("oldPassword");
        else if (!UserUtils.isPasswordValid(newPassword))
            return Response.invalidParamResp("newPassword");
        String accid = UserUtils.getAccid(username);
        boolean authPassed = UserUtils.auth(loginDao, accid, oldPassword);
        if (!authPassed)
            return new Response(302, "username or password incorrect");
        String newToken = UserUtils.getToken(accid, newPassword);
        Response resp = APIUtils.update(accid, newToken);
        if (resp.isSuccess()) {
            resp.setInfo(new JSONObject(new LoginInfo(null, newToken, null)));
            loginDao.updateToken(accid, newToken);
        }
        return resp;
    }
}
