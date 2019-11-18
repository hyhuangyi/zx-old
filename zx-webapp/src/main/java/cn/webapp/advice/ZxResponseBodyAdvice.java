package cn.webapp.advice;

import cn.common.exception.ZXException;
import cn.common.pojo.ResultDO;
import cn.common.util.encrypt.RSAUtils;
import cn.webapp.aop.annotation.Encrypt;
import cn.webapp.configuration.bean.SecretKeyConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by huangYi on 2018/8/18
 * 全局返回对象增强类,进行统一封装
 **/
@ControllerAdvice
public class ZxResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecretKeyConfig secretKeyConfig;
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        /*针对swagger请求不对其拦截*/
        if (serverHttpRequest.getURI().getPath().contains("/swagger")) {
            log.info("返回参数={}", JSONObject.toJSON(o));
            return o;
        }
        /*从Logback中获取requestId*/
        String requestId = null;
        try {
            requestId = MDC.get("requestId");
            MDC.clear();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        /*是否加密 服务端这里私钥加密,客户端公钥解密*/
        if (methodParameter.getMethod().isAnnotationPresent(Encrypt.class) && secretKeyConfig.isOpen()) {//是
            String privateKey = secretKeyConfig.getPrivateKey();
            try {
                if (StringUtils.isEmpty(privateKey)) {
                    throw new NullPointerException("私钥不能为空");
                }
                String result;
                if(o instanceof ResultDO){
                    ResultDO r=(ResultDO)o;
                    result =RSAUtils.privateEncrypt(JSON.toJSONString(r.getData()), RSAUtils.getPrivateKey(privateKey));
                }else {
                    result=RSAUtils.privateEncrypt(JSON.toJSONString(o), RSAUtils.getPrivateKey(privateKey));
                }
                ResultDO res= new ResultDO(requestId,"200","成功",result);
                log.info("返回参数={}", JSONObject.toJSON(res));
                return res;
            } catch (Exception e) {
                throw new ZXException("加密错误");
            }
        }else{//否
            if (o instanceof ResultDO) {
                ((ResultDO) o).setRequestId(requestId);
                log.info("返回参数={}", JSONObject.toJSON(o));
                return o;
            } else {
                //controller层中返回的类型是String，但是在ResponseBodyAdvice实现类中，我们把响应的类型修改成了ResultDO。
                // 这就导致了，上面的这段代码在选择处理MessageConverter的时候，依旧根据之前的String类型选择对应String类型的StringMessageConverter。
                // 而在StringMessageConverter类型，他只接受String类型的返回类型，我们在ResponseBodyAdvice中将返回值从String类型改成ResultDO类型之后，
                // 调用StringMessageConverter方法发生类型强转。ResultDO无法转换成String，发生类型转换异常。
                //所以解决该异常最好的方式就是重写StringMessageConverter方法，让他可以解决ResultDO类型的转化。
                // 后来我没有用这种方式，我觉得这样相对比较麻烦，所以换了一种思路，在ResponseBodyAdvice中做了针对String类型返回值的修改

                ResultDO res= new ResultDO(requestId,"200","成功",o);
                /**
                 if(o instanceof String){
                 return JSON.toJSONString(res);
                 }
                 **/
                log.info("返回参数={}", JSONObject.toJSON(res));
                /*重写StringMessageConverter的解析器则不需要这样转 WebMvcConfig下的configureMessageConverters 已重写*/
                return res;

            }
        }
    }
}
