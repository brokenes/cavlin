package com.github.trans.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.github.trans.request.PaymentRequest;
import com.github.trans.utils.DateUtil;
import com.github.trans.utils.ObjectToMapUtils;
import com.github.trans.utils.OkHttpUtil;
import com.github.trans.utils.PaySignUtil;

@Controller
public class PaymentController {

	private final static Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping("/index")
	public String index() {
		
		return "/pay/index";
		
	}
	
	@RequestMapping("/pay")
	public String pay(PaymentRequest paymentRequest,ModelMap modelMap) {
		paymentRequest.setCurrency("CNY");
		paymentRequest.setCustomerNo("10086877891");
		paymentRequest.setDesc("交易");
		paymentRequest.setFeature("aa");
		paymentRequest.setInputCharset("UTF-8");
		paymentRequest.setNotifyUrl(paymentRequest.getNotifyUrl());
		paymentRequest.setPayOrderNo(generateOrderNo(new Date()));
		paymentRequest.setSubject("支付宝扫码-支付");
		paymentRequest.setPayTime("2020-02-27 11:22:11");
		paymentRequest.setPayAmount(paymentRequest.getPayAmount());
		paymentRequest.setReturnUrl(paymentRequest.getReturnUrl());
		paymentRequest.setClientIp("127.0.0.1");
		paymentRequest.setSignature("a");
		paymentRequest.setVersion("1.0");
		paymentRequest.setSignType("MD5");
		paymentRequest.setPayType(paymentRequest.getPayType());
		String sign = PaySignUtil.requestMd5Sign(paymentRequest, paymentRequest.getMerKey());
		paymentRequest.setSign(sign);
		String result = null;
		try {
			Map<String,String> map = ObjectToMapUtils.toMap(paymentRequest);
			result = OkHttpUtil.getInstance().postWithForm("http://localhost:9005/brokenes/pay", map);
			JSONObject jsonObject = JSONObject.parseObject(result);
			String data = jsonObject.getString("data");
			JSONObject object = JSONObject.parseObject(data);
			String qrCode = object.getString("qrCode");
			if(StringUtils.isNotBlank(qrCode)) {
				modelMap.put("data", qrCode);
			}else {
				modelMap.put("error", "系统错误");	
				return "/error";
			}
					
		} catch (IOException e) {
			return "/error";
		}
		
		return "pay/success";
	}
	
	private synchronized static String generateOrderNo(Date date) {
		String orderId = DateUtil.date2Str("yyyyMMddHHmmssSSS", date);
		String randomNum = new Random().nextInt(100000) + "";
		if (randomNum.length() < 6) {
			for (int x = 0; x <= 6 - randomNum.length(); x++) {
				randomNum += "0";
			}
		}
		return orderId + randomNum;
	}
}
