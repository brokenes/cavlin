package com.github.appmodel.domain.result;

import org.apache.commons.lang3.StringUtils;

import com.github.appmodel.vo.ErrorMsgVo;
import com.github.appmodel.vo.PageVo;
import com.github.appmodel.vo.ResultVo;

public class ResultUtils {
	
	public  static final String SUCCESS_MSG = "请求成功!";
	public static final String FAIL_MSG = "请求失败!";
	public static final String SUCCESS_CODE = "1";
	public static final String FAIL_CODE = "0";
	
	public static ResultVo  buildResult(ModelResult<?> modelResult){
		ResultVo resultVo = new ResultVo();
		if(!modelResult.isSuccess()) {
			resultVo.setData("");
			resultVo.setIsSuccess(false);
			resultVo.setCode(StringUtils.isNotBlank(modelResult.getErrorCode()) ? modelResult.getErrorCode() : FAIL_CODE);
			resultVo.setMsg(StringUtils.isNotBlank(modelResult.getErrorMsg()) ? modelResult.getErrorMsg() : FAIL_MSG);
		} else {
			resultVo.setData(modelResult.getModel());
			resultVo.setCode(SUCCESS_CODE);
			resultVo.setIsSuccess(true);
			resultVo.setMsg(SUCCESS_MSG);
		}
		return resultVo;
	}
	
	public static ErrorMsgVo buildErrorMsg(String code,Object data) {
		ErrorMsgVo errorMsgVo = new ErrorMsgVo(code,data);
		errorMsgVo.setMsg("请求失败");
		errorMsgVo.setSuccess(false);
		return errorMsgVo;
	}
	
	public static PageVo buildPageResult(ModelResult<PageVo> modelResult){
		PageVo pageVo = new PageVo();
		if(modelResult.isSuccess()) {
			pageVo = modelResult.getModel();
			pageVo.setCode(SUCCESS_CODE);
			pageVo.setSuccess(true);
			pageVo.setMsg(SUCCESS_MSG);
		} else {
			pageVo.setRows("");
			pageVo.setTotal(0);
			pageVo.setSuccess(false);
			pageVo.setCode(StringUtils.isNotBlank(modelResult.getErrorCode()) ? modelResult.getErrorCode() : FAIL_CODE);
			pageVo.setMsg(StringUtils.isNotBlank(modelResult.getErrorMsg()) ? modelResult.getErrorMsg() : FAIL_MSG);
		}
		return pageVo;
	}
}
