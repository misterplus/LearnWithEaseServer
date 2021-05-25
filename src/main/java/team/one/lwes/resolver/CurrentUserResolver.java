package team.one.lwes.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import team.one.lwes.annotation.CurrentUser;
import team.one.lwes.bean.LoginInfo;
import team.one.lwes.dao.impl.LoginInfoDaoImpl;

@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private LoginInfoDaoImpl loginDao;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(LoginInfo.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accid = (String) webRequest.getAttribute("accid", RequestAttributes.SCOPE_REQUEST);
        return loginDao.getUser(accid);
    }
}
