package com.github.trans.common.request;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.trans.common.annotation.Signature;
import com.github.trans.common.constants.TransConstants;

public class PaymentRequest extends TransRequest{
	
	
	
	/**签名方式，MD5或者SHA256**/
	@NotBlank(message = "signType不能为空")
	@Pattern(message = "目前签名只有MD5", regexp = "(MD5{1})")
	@Signature(required = false, desc = "签名方式")
	private String signType;
	
	/** 签名(必选)[必选参数经过加密后赋值给这个属性]签名数据 **/
	@NotBlank(message = "sign不能为空")
	@Signature(required = false, desc = "签名")
	private String sign;
	
	/** 加密数据(必选)加密数据，具体请见加密规则 **/
	@NotBlank(message = "signature不能为空")
	@Signature(required = false, desc = "加密签名")
	private String signature;
	
	/**商户号***/
	@NotBlank(message = "customerNo不能为空")
	@Signature(required = true, desc = "商户号")
	@Pattern(regexp = "^[0-9]*$", message = "非法商户号,请输入正确的数字")
	private String customerNo;
	
	/**编码格式UTF-8**/
	@NotBlank(message = "inputCharset不能为空,编码格式为：UTF-8")
	@Signature(required = true, desc = "参数编码字符集,编码格式为：UTF-8")
	private String inputCharset;
	
	/**后台回调地址**/
	@NotBlank(message = "notifyUrl不能为空")
	@Pattern(regexp = TransConstants.URL_REGEXP, message = "非法回调地址")
	private String notifyUrl;
	
	/**跳转页面地址**/
	@NotBlank(message = "returnUrl不能为空")
	@Pattern(regexp = TransConstants.URL_REGEXP, message = "非法页面转发地址")
	private String returnUrl;
	
	/**请求IP地址**/
	@NotBlank(message = "clientIp不能为空")
	@Pattern(regexp = TransConstants.IP_REGEXP,message = "请求IP格式不正确")
	private String clientIp;
	
	/**支付订单号**/
	@NotBlank(message = "orderNo不能为空")
	@Signature(required = true, desc = "交易订单号")
	private String orderNo;
	
	/**订单时间(可选)商户订单时间，格式：YYYYMMDDHHMMSS **/
	@NotBlank(message = "orderTime不能为空")
	@Signature(required = true, desc = "交易时间")
	private String orderTime;
	
	/** 商户该笔订单的总金额，以元为单位，精确到小数点后两位 **/
	@NotBlank(message = "orderTime不能为空")
	@Signature(required = true, desc = "交易时间")
	@Pattern(regexp = TransConstants.DECIMAL_REGEXP, message = "非法金额,请输入正整数,单位【元】,精确到小数点后两位")
	private String payAmount;
	
	/****币种,目前只有人民币-CNY**/
	@NotBlank(message = "currency不能为空")
	private String currency;
	
	/**交易类型,41-微信,42-支付宝,43-QQ钱包,52-网银银行,60-京东钱包,61-银联二维码,62-微信H5,63-QQH5**/
	@NotBlank(message = "payType不能为空")
	@Signature(required = true, desc = "交易类型,41-微信,42-支付宝,43-QQ钱包,52-网银银行,60-京东钱包,61-银联二维码,62-微信H5,63-QQH5")
	private String payType;
	
	/***主题***/
	@NotBlank(message = "subject不能为空")
	@Signature(required = true, desc = "主题")
	private String subject;
	
	/***描述***/
	@NotBlank(message = "desc不能为空")
	@Signature(required = true, desc = "描述")
	private String desc;
	
	/***版本号**/
	@NotBlank(message = "version不能为空")
	@Signature(required = true, desc = "接口版本")
	private String version;
	
	/***扩展字段，JSON数据格式***/
	@NotBlank(message = "feature不能为空")
	@Signature(required = true, desc = "扩展字段")
	private String feature;
	
	
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getInputCharset() {
		return inputCharset;
	}
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	/**
	 * 部分参数需要在签名前进行base64解码,不进行解码操作会出现验签失败
	 */
	public void base64Decoder() {
		Decoder base64Decoder = Base64.getDecoder();
		try {
			if(StringUtils.isNotBlank(subject) && StringUtils.isNotBlank(desc)) {
				this.subject = new String(base64Decoder.decode(subject), "UTF-8");
				this.desc = new String(base64Decoder.decode(desc), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 部分参数需要在签名前进行base64编码,不进行编码操作会出现验签失败
	 */
	public void base64Encoder() {
		Encoder base64Encoder = Base64.getEncoder();
		try {
			if(StringUtils.isNotBlank(subject) && StringUtils.isNotBlank(desc)) {
				this.subject = new String(base64Encoder.encode(subject.getBytes("utf-8")), "UTF-8");
				this.desc = new String(base64Encoder.encode(desc.getBytes("utf-8")), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
}
