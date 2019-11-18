package cn.webapp.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * Created by huangYi on 2018/8/17
 **/
@Component
public class asyncTest {
    public static Random random =new Random();

    /**
     * @Async 异步处理
     * @return
     * @throws Exception
     */
    @Async("taskScheduler")//使用定时任务线程池
    public Future<String> doTaskOne() throws Exception {
        System.out.println(Thread.currentThread().getName()+":开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<String>("任务一完成");
    }

    @Async("myTaskAsyncPool")//使用自定义的线程池
    public Future<String> doTaskTwo() throws Exception {
        System.out.println(Thread.currentThread().getName()+":开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<String>("任务二完成");
    }

    @Async//使用修改过的默认的线程池
    public Future<String> doTaskThree() throws Exception {
        System.out.println(Thread.currentThread().getName()+":开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<String>("任务三完成");
    }
}
