package cn.webapp.aop.annotation;

import cn.webapp.advice.ZxRequestBodyAdvice;
import cn.webapp.advice.ZxResponseBodyAdvice;
import cn.webapp.configuration.bean.SecretKeyConfig;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * Author:zx
 * DateTime:2019/4/9 16:44
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({SecretKeyConfig.class,
        ZxRequestBodyAdvice.class,
        ZxResponseBodyAdvice.class})
public @interface EnableSecurity{

}
