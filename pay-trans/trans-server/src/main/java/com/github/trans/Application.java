package com.github.trans;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * trans-server
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.github.admin.client","com.github.pattern.client","com.github.channel.client"})
@MapperScan("com.github.trans.server.dao")
public class Application 
{
    public static void main( String[] args ) {
    	
       SpringApplication.run(Application.class, args);
       System.err.println(">>>>>>>>>>>>>>>>>>trans-server已经启动<<<<<<<<<<<<<<<<<<<<<<");
        
    }
}
