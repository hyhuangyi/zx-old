package cn.webapp.controller.zx;

import cn.common.util.redis.RedisLockUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

/**
 * Created by huangYi on 2019/6/29.
 **/
@Api(description = "分布式锁测试")
@Controller
public class RedisLockController {

    @ApiOperation("分布式锁测试一")
    @RequestMapping(value = "/comm/redisTest1", method = RequestMethod.POST)
    @ResponseBody
    public void test1() throws Exception {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                boolean lock = RedisLockUtil.lock("yi",8000);
                if (lock) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "获得锁");
                    } finally {
                        RedisLockUtil.releaseLock("yi");
                        System.out.println(Thread.currentThread().getName() + "释放锁成功");
                    }
                } else {
                    System.err.println(Thread.currentThread().getName() + "未获得锁");
                }
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            }).start();

        }
        countDownLatch.await();
        System.out.println("count=" + countDownLatch.getCount() + ",主线程等待结束!");
    }


    @ApiOperation("分布式锁测试二")
    @RequestMapping(value = "/comm/redisTest2", method = RequestMethod.POST)
    @ResponseBody
    public void test2() throws Exception {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                boolean lock = RedisLockUtil.tryLock(new Jedis(), "zx", Thread.currentThread().getName(), 100000);
                if (lock) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "加锁成功");
                    } finally {
                        RedisLockUtil.relaceLock(new Jedis(), "zx", Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getName() + "释放锁成功");
                    }
                } else {
                    System.err.println(Thread.currentThread().getName() + "未获得锁");
                }
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            }).start();
        }
        countDownLatch.await();
        System.out.println("count=" + countDownLatch.getCount() + ",主线程等待结束!");
    }

    @ApiOperation("分布式锁测试三")
    @RequestMapping(value = "/comm/redisTest3", method = RequestMethod.POST)
    @ResponseBody
    public void test3() throws Exception {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                boolean lock = RedisLockUtil.tryLock("hy");
                if (lock) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "获得锁");
                    } finally {
                        RedisLockUtil.unLock("hy");
                        System.out.println(Thread.currentThread().getName() + "释放锁成功");
                    }
                } else {
                    System.err.println(Thread.currentThread().getName() + "未获得锁");
                }
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            }).start();

        }
        countDownLatch.await();
        System.out.println("count=" + countDownLatch.getCount() + ",主线程等待结束!");
    }
}
