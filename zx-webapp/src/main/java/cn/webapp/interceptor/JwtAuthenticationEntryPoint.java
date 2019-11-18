package cn.webapp.interceptor;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by huangYi on 2018/8/26
 *
 * 用户试图访问受保护的资源而不提供任何凭证 我们应该发送一个401未经授权的响应,因为没有重定向到登录页面
 * 抛出异常
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        //throw new RuntimeException(new AccessDeniedException("Unauthorized"));

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未传token信息！");
    }
}
