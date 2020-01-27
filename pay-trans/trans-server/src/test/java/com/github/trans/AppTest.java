package com.github.trans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.appmodel.domain.result.ModelResult;
import com.github.trans.common.request.PaymentRequest;
import com.github.trans.common.response.PaymentResponse;
import com.github.trans.common.service.PaymentService;
import com.github.trans.common.utils.PaySignUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@EnableFeignClients(basePackages = {"com.github.admin.client","com.github.pattern.client"})
public class AppTest {

	@Autowired
	private PaymentService paymentServiceImpl;
	
	@Test
	public void _测试支付接口() {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setCurrency("CNY");
		paymentRequest.setCustomerNo("1008634343");
		paymentRequest.setDesc("交易");
		paymentRequest.setFeature("{}");
		paymentRequest.setInputCharset("UTF-8");
		paymentRequest.setNotifyUrl("http://localhost:9001");
		paymentRequest.setOrderNo("2020022718501122");
		paymentRequest.setSubject("支付宝扫码-支付");
		paymentRequest.setOrderTime("2020-02-27");
		paymentRequest.setPayAmount("11.03");
		paymentRequest.setReturnUrl("http://localhost:9001");
		paymentRequest.setClientIp("127.0.0.1");
		
		paymentRequest.setSignature("a");
		paymentRequest.setVersion("1.0");
		paymentRequest.setSignType("MD5");
		paymentRequest.setPayType("0");
		//paymentRequest.base64Encoder();
		String sign = PaySignUtil.requestMd5Sign(paymentRequest, "sdadsdsd");
		paymentRequest.setSign(sign);
		ModelResult<PaymentResponse>  modelResult = paymentServiceImpl.pay(paymentRequest);
		
		if(!modelResult.isSuccess()) {
			System.out.println(modelResult.getErrorCode() + "******" +  modelResult.getErrorMsg());
		}
		
	}
}