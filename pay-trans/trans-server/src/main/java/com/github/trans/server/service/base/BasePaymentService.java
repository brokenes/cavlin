package com.github.trans.server.service.base;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appmodel.domain.result.ModelResult;
import com.github.pattern.client.service.AgentServiceClient;
import com.github.pattern.client.service.CustomerPaymentChannelInfoServiceClient;
import com.github.pattern.client.service.CustomerServiceClient;
import com.github.pattern.client.service.PaymentChannelServiceClient;
import com.github.pattern.client.service.PaymentTemplateServiceClient;
import com.github.pattern.client.service.ShopServiceClient;
import com.github.pattern.common.domain.Agent;
import com.github.pattern.common.domain.Customer;
import com.github.pattern.common.domain.CustomerPaymentChannelInfo;
import com.github.pattern.common.domain.PaymentChannel;
import com.github.pattern.common.domain.PaymentTemplate;
import com.github.pattern.common.domain.Shop;
import com.github.trans.common.annotation.PayResponseCodeEnum;
import com.github.trans.common.domain.PaymentOrder;
import com.github.trans.common.request.TransRequest;
import com.github.trans.common.response.TransResponse;
import com.github.trans.common.service.PaymentOrderService;
import com.github.trans.common.service.ThirdChannelService;
import com.github.trans.common.utils.BeanValidatorUtils;
import com.github.trans.common.utils.PaySignUtil;
import com.github.trans.context.ThirdChannelContext;

public abstract class BasePaymentService<R extends TransRequest,Q extends TransResponse> {

	private final static Logger LOGGER = LoggerFactory.getLogger(BasePaymentService.class);
	
	@Autowired
	private AgentServiceClient agentServiceClient;
	@Autowired
	private ShopServiceClient shopServiceClient;
	@Autowired
	private CustomerServiceClient customerServiceClient;
	@Resource
	private ThirdChannelContext thirdChannelContext;
	@Autowired
	private CustomerPaymentChannelInfoServiceClient customerPaymentChannelInfoServiceClient;
	@Autowired
	private PaymentTemplateServiceClient paymentTemplateServiceClient;
	@Autowired
	private PaymentChannelServiceClient paymentChannelServiceClient;
	@Autowired
	private PaymentOrderService paymentOrderServiceImpl;
	
	
	protected ModelResult<Q> checkPaymentOrder(R paymentRequest){
		ModelResult<Q> modelResult = new ModelResult<Q>();
		String customerOrderNo = paymentRequest.getPayOrderNo();
		String customerNo = paymentRequest.getCustomerNo();
		ModelResult<List<PaymentOrder>> orderModelResult = paymentOrderServiceImpl.selectByCstomerOrderNo(customerOrderNo);
		if(!orderModelResult.isSuccess()) {
			String errorCode = orderModelResult.getErrorCode();
			String errorMsg = orderModelResult.getErrorMsg();
			LOGGER.error("商户号customerNo = 【{}】查询订单错误码errorCode = 【{}】,错误消息描述errorMsg = 【{}】",customerNo,errorCode,errorMsg);
			modelResult.withError(errorCode, errorMsg);
			return modelResult;
		}
		List<PaymentOrder> list = orderModelResult.getModel();
		if(CollectionUtils.isNotEmpty(list)) {
			String errorCode = "0";
			String errorMsg = "该支付订单已存在,请勿重复提交!";
			modelResult.withError(errorCode, errorMsg);
			LOGGER.error("商户号customerNo = 【{}】重复提交订单，订单号customerOrderNo = 【{}】",customerNo,customerOrderNo);
			return modelResult;
		}
		return modelResult;
	}
	
	protected ModelResult<PaymentOrder> checkQueryOrder(R Request){
		ModelResult<PaymentOrder> modelResult = new ModelResult<PaymentOrder>();
		String customerOrderNo = Request.getPayOrderNo();
		String customerNo = Request.getCustomerNo();
		ModelResult<List<PaymentOrder>> orderModelResult = paymentOrderServiceImpl.selectByCstomerOrderNo(customerOrderNo);
		if(!orderModelResult.isSuccess()) {
			String errorCode = orderModelResult.getErrorCode();
			String errorMsg = orderModelResult.getErrorMsg();
			LOGGER.error("商户号customerNo = 【{}】查询订单错误码errorCode = 【{}】,错误消息描述errorMsg = 【{}】",customerNo,errorCode,errorMsg);
			modelResult.withError(errorCode, errorMsg);
			return modelResult;
		}
		List<PaymentOrder> list = orderModelResult.getModel();
		if(CollectionUtils.isEmpty(list)) {
			String errorCode = "0";
			String errorMsg = "该支付订单已不存在!";
			modelResult.withError(errorCode, errorMsg);
			LOGGER.error("商户号customerNo = 【{}】查询订单不存在，订单号customerOrderNo = 【{}】",customerNo,customerOrderNo);
			return modelResult;
		}
		PaymentOrder paymentOrder = list.get(0);
		modelResult.setModel(paymentOrder);;
		return modelResult;
	}
	
	/***
	 * 检查请求参数是否为空
	 * @param paymentRequest
	 * @return
	 */
	protected ModelResult<Q> checkRequestParamter(R paymentRequest){
		ModelResult<Q> modelResult = new ModelResult<Q>();
		if(paymentRequest == null) {
			LOGGER.error("用户请求对象为空！");
			modelResult.withError("0", "非法请求数据");
			return modelResult;
		}
		LOGGER.info("商户请求参数编码对象数据:paymentRequest = 【{}】",paymentRequest);
		//paymentRequest.base64Decoder();
		LOGGER.info("商户请求参数解码对象数据:paymentRequest = 【{}】",paymentRequest);
		List<String> list = BeanValidatorUtils.validateParam(paymentRequest);
		if(CollectionUtils.isNotEmpty(list)) {
			String errorMsg = list.toString();
			LOGGER.warn("支付请求参数包含以下属性数据为空,errorMsg = 【{}】",errorMsg);
			modelResult.withError("0", errorMsg);
			return modelResult;
		}
		return modelResult;
	}
	
	
	protected ModelResult<Customer> checkPaymentStatus(R paymentRequest){
		ModelResult<Customer> modelResult = new ModelResult<Customer>();
		String customerNo = paymentRequest.getCustomerNo();
		ModelResult<Customer> customerModelResult = customerServiceClient.selectByCustomerNo(customerNo);
		if(!customerModelResult.isSuccess()) {
			String errorMsg = customerModelResult.getErrorMsg();
			LOGGER.error("商户号:customerNo = 【{}】请求错误,errorMsg = 【{}】",customerNo,errorMsg);
			modelResult.withError("0",errorMsg);
			return modelResult;
		}
		if(customerModelResult.getModel() == null) {
			LOGGER.error("商户号:customerNo = 【{}】请求商户不存在",customerNo);
			modelResult.withError("0","请求商户不存在");
			return modelResult;
		}
		Customer customer = customerModelResult.getModel();
		int customerStatus = customer.getStatus();
		if(customerStatus == 0) {
			LOGGER.error("商户号customerNo = 【{}】已停用",customerNo);
			modelResult.withError("0","请求商户已停用");
			return modelResult;
		}
		Integer shopId = customer.getShopId();
		LOGGER.info("商户号customerNo = 【{}】,门店ID为shopID = 【{}】",customerNo,shopId);
		ModelResult<Shop> shopModelResult = shopServiceClient.selectByPrimaryKey(customer.getShopId());
		if(!shopModelResult.isSuccess()) {
			String errorMsg = shopModelResult.getErrorMsg();
			LOGGER.error("商户号customerNo = 【{}】,门店ID为shopID = 【{}】请求错误,errorMsg = 【{}】",customerNo,shopId,errorMsg);
			modelResult.withError("0",errorMsg);
			return modelResult;
		}
		if(shopModelResult.getModel() == null) {
			LOGGER.error("商户号customerNo = 【{}】,门店ID为shopID = 【{}】不存在",customerNo,shopId);
			modelResult.withError("0","请求商户对应的门店不存在");
			return modelResult;
		}
		Shop shop = shopModelResult.getModel();
		int shopStatus = shop.getStatus();
		if(shopStatus == 0) {
			LOGGER.error("商户号customerNo = 【{}】,门店ID为shopID = 【{}】已停用",customerNo,shopId);
			modelResult.withError("0","请求商户对应的门店已停用");
			return modelResult;
		}
		Integer agentId = shop.getAgentId();
		ModelResult<Agent> agentModelResult = agentServiceClient.selectByPrimaryKey(agentId);
		if(!agentModelResult.isSuccess()) {
			String errorMsg = agentModelResult.getErrorMsg();
			LOGGER.error("商户号customerNo = 【{}】,门店ID为shopID = 【{}】,代理商ID为agentId = 【{}】请求错误,errorMsg = 【{}】",customerNo,shopId,agentId,errorMsg);
			modelResult.withError("0",errorMsg);
			return modelResult;
		}
		if(agentModelResult.getModel() == null) {
			LOGGER.error("商户号customerNo = 【{}】,门店ID为shopID = 【{}】,代理商ID为agentId = 【{}】不存在",customerNo,shopId,agentId);
			modelResult.withError("0","请求商户对应的代理商不存在");
			return modelResult;
		}
		Agent agent = agentModelResult.getModel();
		int agentStatus = agent.getStatus();
		if(agentStatus == 0) {
			LOGGER.error("商户号customerNo = 【{}】,门店ID为shopID = 【{}】,代理商ID为agentId = 【{}】已停用",customerNo,shopId,agentId);
			modelResult.withError("0","请求商户对应的代理商已停用");
			return modelResult;
		}
		modelResult.setModel(customer);
		return modelResult;
	}
	
	
	/***
	 * 检查请求签名
	 * @param paymentRequest
	 * @return
	 */
	protected ModelResult<Q> checkRequestSign(R paymentRequest,Customer customer){
		ModelResult<Q> modelResult = new ModelResult<Q>();
		String customerNo = customer.getCustomerNo();
		String merKey = customer.getCipher();
		try {
			String sign  = PaySignUtil.requestMd5Sign(paymentRequest, merKey);
			String customerSign = paymentRequest.getSign();
			if(!sign.equals(customerSign)) {
				LOGGER.error("商户号customerNo = 【{}】验签失败，商户传递customerSign = 【{}】,系统验证 = 【{}】不一致",customerNo,customerSign,sign);
				String errorCode = PayResponseCodeEnum.CHECK_SIGN_FAILURE.getCode();
				String errorMsg = PayResponseCodeEnum.CHECK_SIGN_FAILURE.getMsg();
				modelResult.withError(errorCode,errorMsg);
			}
		}catch(Exception e) {
			LOGGER.error("商户号customerNo = 【{}】验签失败，失败原因 errorMsg = 【{}】",customerNo,e.getMessage());
			String errorCode = PayResponseCodeEnum.CHECK_SIGN_FAILURE.getCode();
			String errorMsg = PayResponseCodeEnum.CHECK_SIGN_FAILURE.getMsg();
			modelResult.withError(errorCode,errorMsg);
		}
		return modelResult;
	}
	
	
	protected ModelResult<CustomerPaymentChannelInfo> selectPaymentChannel(Customer customer,String payType){
		ModelResult<CustomerPaymentChannelInfo> modelResult = new ModelResult<CustomerPaymentChannelInfo>();
		String customerNo = customer.getCustomerNo();
		Integer customerId = customer.getCustomerId();
		ModelResult<List<CustomerPaymentChannelInfo>> channelInfoModelResult = customerPaymentChannelInfoServiceClient.selectByCustomerIdAndPayType(customerId,payType);
		if(!channelInfoModelResult.isSuccess() || CollectionUtils.isEmpty(channelInfoModelResult.getModel())) {
			String errorCode = "0";
			String errorMsg = "获取支付账号渠道失败或没有分配账号渠道";
			LOGGER.warn("商户customeNo = 【{}】获取用户账号渠道失败 errorCode = 【{}】,errorMsg = 【{}】",customerNo,errorCode,errorMsg);
			modelResult.withError(errorCode, errorMsg);
			return modelResult;
		}
		List<CustomerPaymentChannelInfo> list = channelInfoModelResult.getModel();
		int listSize = list.size();
		int index = 0;
		if(listSize > 1) {
			 index = new Random().nextInt(listSize - 1);
		}
		CustomerPaymentChannelInfo customerPaymentChannelInfo = list.get(index);
		if(customerPaymentChannelInfo == null) {
			String errorCode = "0";
			String errorMsg = "获取支付账号渠道失败或没有分配账号渠道";
			LOGGER.warn("商户customeNo = 【{}】获取用户账号渠道失败 errorCode = 【{}】,errorMsg = 【{}】",customerNo,errorCode,errorMsg);
			modelResult.withError(errorCode, errorMsg);
			return modelResult;
		}
		modelResult.setModel(customerPaymentChannelInfo);
		return modelResult;
	}
	
	protected ModelResult<ThirdChannelService> selectThirdChannel(CustomerPaymentChannelInfo customerPaymentChannelInfo){
		ModelResult<ThirdChannelService> modelResult = new ModelResult<ThirdChannelService>();
		Integer paymentChannelId = customerPaymentChannelInfo.getPaymentChannelId();
		String customerNo = customerPaymentChannelInfo.getCustomerNo();
		ModelResult<PaymentChannel> paymentChannelModelResult = paymentChannelServiceClient.selectByPrimaryKey(paymentChannelId);
		if(!paymentChannelModelResult.isSuccess() || paymentChannelModelResult.getModel() == null) {
			String errorCode = "0";
			String errorMsg = "获取支付渠道失败";
			LOGGER.warn("商户customeNo = 【{}】获取用户渠道失败 errorCode = 【{}】,errorMsg = 【{}】",customerNo,errorCode,errorMsg);
			modelResult.withError(errorCode, errorMsg);
			return modelResult;
		}
		PaymentChannel paymentChannel = paymentChannelModelResult.getModel();
		Integer paymentTemplateId = paymentChannel.getPaymentTemplateId();
		ModelResult<PaymentTemplate> templateModelResult = paymentTemplateServiceClient.selectByPrimaryKey(paymentTemplateId);
		if(!templateModelResult.isSuccess() || templateModelResult.getModel() == null) {
			String errorCode = "0";
			String errorMsg = "商户没有添加对应的支付渠道模板";
			LOGGER.warn("商户customeNo = 【{}】获取用户渠道模板失败 errorCode = 【{}】,errorMsg = 【{}】",customerNo,errorCode,errorMsg);
			modelResult.withError(errorCode, errorMsg);
			return modelResult;
		}
		PaymentTemplate paymentTemplate = templateModelResult.getModel();
		String templateName = paymentTemplate.getTemplateName();
		LOGGER.info("商户customeNo = 【{}】获取支付渠道模板名称templateName = 【{}】",customerNo,templateName);
		if(StringUtils.isBlank(templateName)) {
			String errorCode = "0";
			String errorMsg = "商户没有添加对应的支付渠道模板名称";
			LOGGER.warn("商户customeNo = 【{}】获取用户渠道模板失败 errorCode = 【{}】,errorMsg = 【{}】",customerNo,errorCode,errorMsg);
			modelResult.withError(errorCode, errorMsg);
			return modelResult;
		}
		modelResult = thirdChannelContext.strategy(templateName);
		return modelResult;
	}
	
	/***
	 * 检查风控
	 * @param paymentRequest
	 * @return
	 */
	protected ModelResult<Q> checkRisk(R paymentRequest){
		ModelResult<Q> modelResult = new ModelResult<Q>();
		return modelResult;
	}
}
