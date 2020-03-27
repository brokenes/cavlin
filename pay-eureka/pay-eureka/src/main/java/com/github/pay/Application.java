package com.github.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * eureka
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class Application {
	
    public static void main( String[] args ){
        SpringApplication.run(Application.class, args);
        System.err.println(">>>>>>>>>>>>>>>>>> enureka启动成功 <<<<<<<<<<<<<<<<<<<<<<");
    }
    
}
