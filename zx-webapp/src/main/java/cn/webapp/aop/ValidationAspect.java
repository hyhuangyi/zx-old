package cn.webapp.aop;

import cn.common.exception.ValidException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by huangYi on 2018/8/22
 * 参数校验 统一返回数据信息
 */
@Aspect
@Component
@Order(1)
public class ValidationAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before(value="@annotation(cn.webapp.aop.annotation.ValidatedRequest)")
    public void ValidationBefore(JoinPoint jp) throws Exception{
        Object[] args = jp.getArgs();
        for (Object arg : args){
            if (arg instanceof BindingResult){
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()){
                    FieldError fieldError = result.getFieldError();
                    String message=fieldError.getField() + fieldError.getDefaultMessage();
                    logger.error(message);
                    throw new ValidException(message);
                }
            }
        }
    }

}
