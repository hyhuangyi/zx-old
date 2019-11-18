package cn;

import cn.common.entity.Token;
import cn.webapp.configuration.RabbitMQConfig;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMQTest extends BaseJunitTest  {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*发送信息*/
    @Test
    public void send(){
        Token token=new Token();
        token.setPhone("110");
        token.setEmail("1234@qq.com");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY,token);
    }

    /*接受信息*/
    @Test
    public void accept(){
        System.out.println(rabbitTemplate.receiveAndConvert(RabbitMQConfig.QUEUE_NAME));
    }
}
