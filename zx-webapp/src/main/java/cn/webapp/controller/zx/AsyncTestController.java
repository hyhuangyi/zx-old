package cn.webapp.controller.zx;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * Created by huangYi on 2018/8/17
 **/
@RestController
@RequestMapping("/comm/async")
@Api(description = "异步测试相关")
public class AsyncTestController {

    @Autowired
    private cn.webapp.async.asyncTest asyncTest;

    @RequestMapping(value = "test",method = RequestMethod.POST)
    @ApiOperation("异步测试")
    public String testAsync() throws Exception{

        long start = System.currentTimeMillis();
        Future<String> task1=asyncTest.doTaskOne();
        Future<String> task2=asyncTest.doTaskTwo();
        Future<String> task3=asyncTest.doTaskThree();
        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

        return (end-start)+"毫秒";
    }
}
