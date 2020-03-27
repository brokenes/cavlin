package com.github.trans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * trans-web
 *
 */  
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.github.trans.client"})
public class Application {
	
    public static void main( String[] args ){
    	
    	 SpringApplication.run(Application.class, args);
    	 System.err.println(">>>>>>>>>>>>>>>>>> admin-web启动成功 <<<<<<<<<<<<<<<<<<<<<<");
    	 
    }
}
