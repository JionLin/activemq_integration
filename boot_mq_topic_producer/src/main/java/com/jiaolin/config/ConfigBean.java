package com.jiaolin.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

/**
 * @author johnny
 * @Classname ConfigBean
 * @Description
 * @Date 2022/4/24 11:49
 */
@Component
public class ConfigBean {

    @Value("${myTopic}")
    private String myTopic;

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(myTopic);
    }
}
