package cn.webapp.advice;

import cn.common.exception.ZXException;
import cn.webapp.aop.annotation.Decrypt;
import cn.webapp.configuration.bean.SecretKeyConfig;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import java.lang.reflect.Type;

/**
 * Created by huangYi on 2019/8/6.
 * 全局请求增强类，进行统一封装
 * RequestBodyAdvice，针对所有以@RequestBody的参数，在读取请求body之前或者在body转换成对象之前可以做相应的增强
 **/
@ControllerAdvice
public class ZxRequestBodyAdvice implements RequestBodyAdvice {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecretKeyConfig secretKeyConfig;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        /*是否解密 客户端公钥加密,服务端这里私钥解密*/
        if (methodParameter.getMethod().isAnnotationPresent(Decrypt.class) && secretKeyConfig.isOpen()) {//是
            try {
                return new ZxHttpInputMessage(httpInputMessage,secretKeyConfig.getPrivateKey());
            } catch (Exception e) {
                log.error(e.getMessage(),e);
               throw new ZXException("解密异常");
            }
        }
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        //打印入参
        log.info("请求参数={}", JSONObject.toJSON(body));
        return body;
    }
}
