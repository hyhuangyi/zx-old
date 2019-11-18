package cn.common.util.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by huangYi on 2018/8/16
 **/
@Component
public class SpringUtil implements ApplicationContextAware {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    private static ApplicationContext applicationContext;

    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        logger.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getApplicationContext()" +
                "获取applicationContext对象,applicationContext="+ SpringUtil.applicationContext+"========");
    }

    /**获取applicationContext */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**通过name获取 Bean */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**通过class获取Bean */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**通过name,以及Clazz返回指定的Bean */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    /*
    public static  void main(String[]args){
        //不开启服务访问bean
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring/spring.xml");
        City city=(City) ac.getBean("city");
        System.out.println(city.getCode());
    }*/
}
