package com.jiaolin.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author johnny
 * @Classname TopicConsumer
 * @Description
 * @Date 2022/4/24 11:35
 */
@Component
public class TopicConsumer {

    @JmsListener(destination = "${myTopic}")
    public void receive(TextMessage message) throws JMSException {
        System.out.println("接收到的消息为:\t" + message.getText());
    }
}
