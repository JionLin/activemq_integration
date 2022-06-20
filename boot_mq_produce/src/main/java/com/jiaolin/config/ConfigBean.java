package com.jiaolin.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

/**
 * @author johnny
 * @Classname ConfigBean
 * @Description
 * @Date 2022/4/23 19:06
 */
@Component
// 允许jms java消息服务
@EnableJms
public class ConfigBean {

    @Value("${myQueue}")
    private String myQueue;

    @Bean
    public ActiveMQQueue queue(){
        return new ActiveMQQueue(myQueue);
    }
}
