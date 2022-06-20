package com.jiaolin.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author johnny
 * @Classname QueueConsumer
 * @Description 进行监听消息
 * @Date 2022/4/24 10:57
 */
@Component
public class QueueConsumer {


    @JmsListener(destination = "${myQueue}")
    public void receive(TextMessage textMessage) {
        try {
            System.out.println("获取的信息为:\t" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


}
