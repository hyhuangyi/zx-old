package cn.webapp.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by huangYi on 2018/8/22
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidatedRequest {
}
