package com.jiaolin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author johnny
 * @Classname MqProducerApplication
 * @Description
 * @Date 2022/4/23 19:05
 */
@SpringBootApplication
@EnableScheduling
public class MqProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqProducerApplication.class, args);
    }
}
