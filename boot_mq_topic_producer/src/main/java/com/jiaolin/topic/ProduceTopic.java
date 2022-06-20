package com.jiaolin.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

/**
 * @author johnny
 * @Classname ProduceTopic
 * @Description
 * @Date 2022/4/24 11:53
 */
@Component
public class ProduceTopic {

    @Autowired
    private Topic topic;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage() {
        String value = UUID.randomUUID().toString().substring(0, 4);
        jmsMessagingTemplate.convertAndSend(topic, value);
        System.out.println("发送成功:\t" + value);
    }

    @Scheduled(fixedDelay = 3000)
    public void sendScheduleMessage() {
        String value = UUID.randomUUID().toString().substring(0, 4);
        jmsMessagingTemplate.convertAndSend(topic, value);
        System.out.println("发送成功:\t" + value);
    }
}
