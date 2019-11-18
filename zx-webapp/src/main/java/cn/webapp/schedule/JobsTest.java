package cn.webapp.schedule;

import cn.common.entity.City;
import cn.common.util.sys.SpringUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by huangYi on 2018/8/16
 **/
//关闭定时任务
//@Component
public class JobsTest {
    //日志
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static long TIME=60*1000;

    /**
     * fixedDelay 等到方法执行完成后延迟配置的时间再次执行该方法
     * fixedRate  不用等待上一次调用完成
     * initialDelay 第一次执行延迟时间，只是做延迟的设定，并不会控制其他逻辑
     * @throws Exception
     */
    @Scheduled(fixedDelay=TIME)
    public void fixedDelayJob() throws Exception{
        /*Thread.sleep(3000);*/
        Thread current = Thread.currentThread();
        logger.info("定时任务-fixedDelay" +"|"+current.getName()+"|"+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" >>fixedDelay执行....");
    }


    @Scheduled(fixedRate=TIME)
    public void fixedRateJob() throws Exception{
        /*通过SpringUtil获取bean*/
        City city=(City) SpringUtil.getBean("city");
        /*Thread.sleep(3000);*/
        Thread current = Thread.currentThread();
        logger.info("定时任务-fixedRate"+"|"+current.getName()+"|"+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" >>fixedRate执行...."+city.getFullName());
    }

    @Scheduled(cron="0 15 3 * * ?")
    public void cronJob(){
        Thread current = Thread.currentThread();
        logger.info("定时任务-cron"+ "|"+current.getName()+"|"+DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+" >>cron执行....");
    }

}
