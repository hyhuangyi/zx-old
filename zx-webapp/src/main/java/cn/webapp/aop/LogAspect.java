package cn.webapp.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 使用@Aspect 注解的类， Spring 将会把它当作一个特殊的Bean（一个切面），也就是
 * 不对这个类本身进行动态代理  //指定当前类为切面类
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
    @Pointcut("@annotation(cn.webapp.aop.annotation.LogInfo)")
    public void logInfo() {
    }
    /**
     * 前置通知，方法调用前被调用
     * @param joinPoint
     */
    @Before(value = "logInfo()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        //参数值
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //最关键的一步:通过这获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Map<String,Object> map=new HashMap();
        for(int i=0;i<parameterNames.length;i++){
            map.put(parameterNames[i],args[i]);
        }
        String paramMap = JSON.toJSONString(map,true);
        LOGGER.info("请求request ==> url={}, class={}, method={}, req={}", new Object[]{
                httpServletRequest.getRequestURL(), className, methodName, paramMap});

    }

    /**
     * 处理完请求返回内容
     * @param retVal
     * @throws Throwable
     */
    @AfterReturning(value = "logInfo()", returning = "retVal")
    public void doAfter(JoinPoint joinPoint, Object retVal) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();//获取response
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 返回参数
        String responseStr = "";
        if (retVal != null) {
            responseStr = JSON.toJSONString(retVal,true);
        }
        // 响应内容
        LOGGER.info("返回response ==>  class={}, method={}, res={}", className, methodName, responseStr);
    }

}
