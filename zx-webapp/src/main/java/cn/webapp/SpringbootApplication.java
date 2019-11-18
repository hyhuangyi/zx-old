package cn.webapp;

import cn.common.entity.City;
import cn.webapp.aop.annotation.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by huangYi on 2018/3/7
 **/
@SpringBootApplication
@EnableScheduling//开启定时任务
@EnableCaching//开启缓存
@EnableAsync//异步使@Async生效
@EnableTransactionManagement//开启事务
@EnableSecurity //开启自动加密
@ImportResource("classpath:spring/*.xml")
@MapperScan("cn.common.dao.mapper")//添加对mapper包扫描
@ComponentScan(basePackages={"cn.webapp","cn.common"})
@ServletComponentScan(basePackages = "cn.webapp")
/*使用@ServletComponentScan注解后 Servlet可以直接通过@WebServlet注解自动注册
Filter可以直接通过@WebFilter注解自动注册，Listener可以直接通过@WebListener 注解自动注册*/
public class SpringbootApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return  builder.sources(SpringbootApplication.class);
    }

    public static void main(String[] args) {
      ApplicationContext context= SpringApplication.run(SpringbootApplication.class, args);
      City city=(City) context.getBean("city");
      System.out.println("code="+city.getCode()+";fullName="+city.getFullName());
    }
}
