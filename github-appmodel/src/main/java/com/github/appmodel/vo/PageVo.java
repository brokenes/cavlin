package com.github.appmodel.vo;

import com.github.appmodel.base.BaseObject;

public class PageVo extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1827641287964208659L;
	
	private long total;
	public Object rows;
	private boolean isSuccess;
	private String code;
	private String msg;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
