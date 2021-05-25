package team.one.lwes.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import team.one.lwes.annotation.Auth;
import team.one.lwes.service.LWEAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LWEAuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(Auth.class) == null)
            return true;
        String accid = request.getHeader("accid");
        String token = request.getHeader("token");
        boolean authPassed = authService.auth(accid, token);
        if (!authPassed) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        request.setAttribute("accid", accid);
        return true;
    }
}
