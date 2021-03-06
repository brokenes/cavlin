package com.github.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.admin.interceptor.SessionTimeOutInterceptor;

@Configuration
public class CustomeWebMvcConfigurer implements WebMvcConfigurer{

	@Autowired
	private SessionTimeOutInterceptor sessionTimeOutInterceptor;
	
	
	public void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(sessionTimeOutInterceptor)
//       .addPathPatterns("/**","/manager/**")
//       .excludePathPatterns("/login")
//       .excludePathPatterns("/captcha")
//       .excludePathPatterns("/resources/**");
	
	}

	
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/","/js/**","/css/**","/images/**","data/**","plugins/**").addResourceLocations("classpath:/resources/");
//	}

}