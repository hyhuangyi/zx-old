package cn.webapp.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by huangYi on 2018/8/14
 **/
@Target(ElementType.METHOD)//表示该注解用于什么地方
@Retention(RetentionPolicy.RUNTIME)// 定义该注解的生命周期  RUNTIME 运行期也保留该注解
@Documented//表示是否将注解信息添加在java文档中
public @interface OperateLog {
    String operation() default "";
}
