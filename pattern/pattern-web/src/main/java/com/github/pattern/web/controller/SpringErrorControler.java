package com.github.pattern.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SpringErrorControler implements ErrorController{

	private static Logger logger = LoggerFactory.getLogger(SpringErrorControler.class);
	
	private static final String SERVLERT_STATUS_CODE = "javax.servlet.error.status_code";
	
	@RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute(SERVLERT_STATUS_CODE);
        logger.info("当前用户请求响应状态码为：statusCode = 【{}】",statusCode);
        if(statusCode == 500){
            return "/500";
        }else if(statusCode == 404){
            return "/404";
        }else if(statusCode == 403){
            return "/403";
        }else{
            return "/500";
        }
    }

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
