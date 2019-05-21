package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * 基础微服务引导类
 */
@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端配置
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    /**
     * 初始化工作
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

}
