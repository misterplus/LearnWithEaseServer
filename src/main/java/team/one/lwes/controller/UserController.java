package team.one.lwes.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import team.one.lwes.bean.Response;
import team.one.lwes.bean.LoginInfo;
import team.one.lwes.bean.User;
import team.one.lwes.util.APIUtils;
import team.one.lwes.util.UserUtils;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody @NonNull User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (!UserUtils.isUsernameValid(username))
            return Response.invalidParamResp("username");
        else if (!UserUtils.isPasswordValid(password))
            return Response.invalidParamResp("password");
        user.setUsername(UserUtils.getAccid(username));
        user.setPassword(UserUtils.getToken(username, password));
        return APIUtils.register(user); // credentials or error msg
    }

    // May produce invalid credentials, login will NOT always pass
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public Response convert(@RequestParam @NonNull String username, @RequestParam @NonNull String password) {
        if (!UserUtils.isUsernameValid(username))
            return Response.invalidParamResp("username");
        else if (!UserUtils.isPasswordValid(password))
            return Response.invalidParamResp("password");
        String accid = UserUtils.getAccid(username);
        String token = UserUtils.getToken(username, password);
        return new Response(200, new LoginInfo(accid, token));
    }

}
