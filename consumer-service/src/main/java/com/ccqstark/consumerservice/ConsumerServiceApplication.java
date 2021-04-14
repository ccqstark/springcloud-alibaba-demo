package com.ccqstark.consumerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumerServiceApplication {

    //Java Config声明RestTemplate对象
    //在应用启动时自动执行restTemplate()方法创建RestTemplate对象，其BeanId为restTemplate。
    @Bean
    @LoadBalanced //使RestTemplate对象自动支持Ribbon负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerServiceApplication.class, args);
    }

}
