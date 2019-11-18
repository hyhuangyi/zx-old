package cn.webapp.aop;


import cn.common.dao.mapper.ExcuteTimeDao;
import cn.common.entity.SysExcuteTimeLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by huangYi on 2018/8/21
 * 执行时间记录
 **/
@Aspect
@Component
@Order(2)
public class TimeCountAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExcuteTimeDao excuteTimeDao;

    @Pointcut("@annotation(cn.webapp.aop.annotation.TimeCount)")
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object  around(ProceedingJoinPoint pjp) throws Throwable{
        Object object=null;
        long startTime=System.currentTimeMillis();

        //执行过程
        object= pjp.proceed();

        long endTime= System.currentTimeMillis();
        long time=endTime-startTime;
        Method method=((MethodSignature) pjp.getSignature()).getMethod();
        String methodName=method.getDeclaringClass().getName()+"."+method.getName();
        record(methodName,time);
        logger.info(methodName+"执行了"+time+"耗秒");
        return object;
    }

    private void record(String methodName,long time){
        SysExcuteTimeLog log=new SysExcuteTimeLog();
        log.setCreateDate(new Date());
        log.setExcuteMethod(methodName);
        log.setUpdateDate(new Date());
        log.setOperateDate(new Date());
        log.setExcuteTime((int)time);
        excuteTimeDao.insert(log);
    }
}
