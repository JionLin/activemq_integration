package com.jiaolin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author johnny
 * @Classname TopicProduces
 * @Description
 * @Date 2022/4/24 11:49
 */
@SpringBootApplication
@EnableScheduling
public class TopicProduces {
    public static void main(String[] args) {
        SpringApplication.run(TopicProduces.class, args);
    }
}
