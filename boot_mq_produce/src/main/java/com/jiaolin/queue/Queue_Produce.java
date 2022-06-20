package com.jiaolin.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

/**
 * @author johnny
 * @Classname Queue_Produce
 * @Description
 * @Date 2022/4/23 21:04
 */
@Component
public class Queue_Produce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void producesMessage() {
        jmsMessagingTemplate.convertAndSend(queue,"produces 发送的消息为"+ UUID.randomUUID().toString().substring(0, 9));
    }

    @Scheduled(fixedDelay = 3000)
    public void scheduleProducesMessage() {
        String value = UUID.randomUUID().toString().substring(0, 9);
        jmsMessagingTemplate.convertAndSend(queue,"produces 消息为"+ value);
        System.out.println("scheduleProducesMessage send ok: "+value);
    }
}
