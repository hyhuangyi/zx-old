package cn.webapp.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by huangYi on 2018/8/18
 * 日志处理filter
 **/
@WebFilter(urlPatterns = "/*")
public class RequestLogFilter extends OncePerRequestFilter {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //请求requestId
        MDC.put("requestId", UUID.randomUUID().toString().replaceAll("-", "").toLowerCase().substring(0, 16));
        logReqParams(httpServletRequest, httpServletResponse);
        //不需要过滤直接传给下一个过滤器
       filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void logReqParams(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        this.logHeadParams(request,sb);
        this.logParams(request,sb);
        logger.info(sb.toString());
    }

    /**
     * 请求头信息
     * @param request
     * @param sb
     */
    protected void logHeadParams(HttpServletRequest request,StringBuffer sb) {
        sb.append("logRegHeaders:" + request.getRequestURI() + "\n");
        Enumeration<String> names = request.getHeaderNames();
        if (names != null) {
            while (names.hasMoreElements()) {
                String element = names.nextElement();
                sb.append(element + ": " + request.getHeader(element) + "\n");
            }
        }
    }

    /**
     * 路径与请求参数
     * @param request
     * @param sb
     */
    protected void logParams(HttpServletRequest request,StringBuffer sb) {
        sb.append("logReqURL:" +request.getRequestURL()+ "\n");
        sb.append("logReqParams:" + "\n");
        Enumeration<String> names = request.getParameterNames();
        if (names != null) {
            while (names.hasMoreElements()) {
                String element = names.nextElement();
                String value = request.getParameter(element);
                sb.append(element + ": " + value + "\n");
            }
        }
    }

}
