/*
 * Created: 2016-7-12
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 **/
package cn.concox.comm.vo;

import java.io.Serializable;

public class APIContent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2127409162712908650L;
	
	public APIContent(){}
	
	public APIContent(Object data){
		this.data=data;
	}
	
	/**
	 * API 接口返回数据包
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 * 
	 * @author Li.Shangzhi
	 * @date 2016-7-14 16:31:30
	 */
	public APIContent(String code,String msg,Object data){
		this.data=data;
		this.code=code;
		this.msg=msg;
	}
	
	public APIContent(Object data,String request){
		this.data=data;
		this.reqeust=request;
	}
	
	public APIContent(String code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	public APIContent(String code,String msg,String request){
		this.code=code;
		this.msg=msg;
		this.reqeust=request;
	}
	
	public APIContent(String code,String msg,Object rows,Integer total){
		this.code=code;
		this.msg=msg;
		this.rows=rows;
		this.total = total;
	}
	
	/**
	 * 请求
	 */
	private String reqeust;
	/**
	 * 返回的数据
	 */
	private Object data;
	
	/**
	 * 错误码，为空表示成功
	 */
	private String code;
	
	/**
	 * 错误信息，当code不为空时返回的错误信息
	 */
	private String msg;
	
	/**
	 * easyui中，若使用datagrid URL方式进行初始化数据时
	 * 否则浏览器会报rows is not object异常
	 * add by wg.he 2013/11/01
	 */
	private Integer total;
	
	private Object rows;
	
	private String other;
	
	public APIContent(Integer total, Object rows,String code) {
		super();
		this.total = total;
		this.rows = rows;
		this.code = code;
	}
	
	public APIContent(Integer total, Object rows,String code,String msg,String other) {
		super();
		this.total = total;
		this.rows = rows;
		this.code = code;
		this.msg = msg;
		this.other = other;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public String getReqeust() {
		return reqeust;
	}
	public void setReqeust(String reqeust) {
		this.reqeust = reqeust;
	}
	 
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
 
	 
}
