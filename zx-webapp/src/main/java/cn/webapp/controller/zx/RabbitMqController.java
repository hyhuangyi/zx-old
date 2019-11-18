package cn.webapp.controller.zx;

import cn.webapp.configuration.RabbitMQConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangYi on 2018/9/12
 **/
@Api(description = "rabbitMq")
@RestController
public class RabbitMqController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/comm/rabbitMq",method = RequestMethod.POST)
    @ApiOperation(value = "rabbitMq测试")
    public Object sendMessage() {
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, i);
            }
        }).start();
        return "ok";
    }
}
@Component
class Consumer {

//    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(Message message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }
}
