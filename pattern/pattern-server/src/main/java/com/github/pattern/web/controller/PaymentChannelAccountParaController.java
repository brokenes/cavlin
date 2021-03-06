package com.github.pattern.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.appmodel.domain.result.ModelResult;
import com.github.pattern.common.domain.PaymentChannelAccountPara;
import com.github.pattern.common.request.PaymentChannelAccountParaRequest;
import com.github.pattern.common.service.PaymentChannelAccountParaService;
import com.github.appmodel.vo.PageVo;

@RestController
@RequestMapping("/pattern/server/paymentChannelAccountPara")
public class PaymentChannelAccountParaController {
	
	
	@Autowired
	private PaymentChannelAccountParaService paymentChannelAccountParaServiceImpl;
	
	
	@PostMapping("/deleteByPrimaryKey/{paymentChannelAccountParaId}")
	public ModelResult<Integer> deleteByPrimaryKey(@PathVariable("paymentChannelAccountParaId")Integer paymentChannelAccountParaId){
		return paymentChannelAccountParaServiceImpl.deleteByPrimaryKey(paymentChannelAccountParaId);
	}

	@PostMapping("/insert")
	public ModelResult<Integer> insert(@RequestBody PaymentChannelAccountPara record){
		return paymentChannelAccountParaServiceImpl.insert(record);
	}

	@PostMapping("/insertSelective")
	public ModelResult<Integer> insertSelective(@RequestBody PaymentChannelAccountPara record){
		return paymentChannelAccountParaServiceImpl.insertSelective(record);
	}

	@PostMapping("/selectByPrimaryKey/{paymentChannelAccountParaId}")
	public ModelResult<PaymentChannelAccountPara> selectByPrimaryKey(@PathVariable("paymentChannelAccountParaId")Integer paymentChannelAccountParaId){
		return paymentChannelAccountParaServiceImpl.selectByPrimaryKey(paymentChannelAccountParaId);
	}

	@PostMapping("/updateByPrimaryKeySelective")
	public ModelResult<Integer> updateByPrimaryKeySelective(@RequestBody PaymentChannelAccountPara record){
		return paymentChannelAccountParaServiceImpl.updateByPrimaryKeySelective(record);
	}

	@PostMapping("/updateByPrimaryKey")
	public ModelResult<Integer> updateByPrimaryKey(@RequestBody PaymentChannelAccountPara record){
		return paymentChannelAccountParaServiceImpl.updateByPrimaryKey(record);
	}
	
	@PostMapping("/selectByPaymentChannelAccountId/{paymentChannelAccountId}")
	ModelResult<List<PaymentChannelAccountPara>> selectByPaymentChannelAccountId(@PathVariable("paymentChannelAccountId")Integer paymentChannelAccountId){
		return paymentChannelAccountParaServiceImpl.selectByPaymentChannelAccountId(paymentChannelAccountId);
	}

}
