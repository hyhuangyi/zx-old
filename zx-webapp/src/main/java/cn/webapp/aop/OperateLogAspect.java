package cn.webapp.aop;

import cn.common.util.ip.IpUtil;
import cn.common.servlet.ServletContextHolder;
import cn.webapp.aop.annotation.OperateLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by huangYi on 2018/8/14
 * 操作记录aspect
 * 在方法上加注解@OperateLog
 * 用法示例：@OperateLog(operation = "启用用户，姓名为#{#name}")
 * #{#name} 动态解析参数,需要取参数字段则这么写，当然参数也可以是对象
 * 如果不需要取获取参数里的值直接@OperateLog(operation = "启用用户")
 **/
@Aspect
@Component
public class OperateLogAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(cn.webapp.aop.annotation.OperateLog)")
    public void pointCut(){}

    @AfterReturning(value = "pointCut()")
    public void AfterReturning(JoinPoint joinPoint){
        /*joinPoint.getArgs()获取参数数组 参数可以是对象也可以是单个值
          只有方法参数列表存在参数才能获取到*/
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if(method!=null&&method.isAnnotationPresent(OperateLog.class)){
            OperateLog operateLog=method.getAnnotation(OperateLog.class);
            String operation=operateLog.operation();
            String action = executeTemplate(operation, joinPoint,method);
            String operatorName="zx";
           // String operatorName= ServletContextHolder.getToken().getUsername();
            String ip=IpUtil.getIpAddress(ServletContextHolder.getRequest());
            // TODO: 2019/7/3  暂作logger打印，可建表存库
            logger.info("ip："+ip+",name："+operatorName+",action："+action+"----->"+method.getDeclaringClass().getName()+"类的"+method.getName()+"方法");

        }
    }
    /*解析注解中的描述信息*/
    private String executeTemplate(String template, JoinPoint joinPoint,Method method) {
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        Object[] args = joinPoint.getArgs();
        if (args.length == parameterNames.length) {
            for (int i = 0, len = args.length; i < len; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return parser.parseExpression(template, new TemplateParserContext()).getValue(context, String.class);
    }
}
