package cn.webapp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by huangYi on 2018/10/15
 **/
@Component
public class MyCommandRunner implements CommandLineRunner {
    private  Logger logger= LoggerFactory.getLogger(this.getClass());

    @Value("${auto.loginurl}")
    private String loginUrl;

    @Value("${auto.googleexcute}")
    private String googleExcutePath;

    @Value("${auto.openurl}")
    private boolean isOpen;
    @Override
    public void run(String... strings) throws Exception {
        if(isOpen){
            String cmd = googleExcutePath +" "+ loginUrl;
            Runtime run = Runtime.getRuntime();
            try{
                run.exec(cmd);
                logger.info("启动浏览器打开项目成功");
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}
