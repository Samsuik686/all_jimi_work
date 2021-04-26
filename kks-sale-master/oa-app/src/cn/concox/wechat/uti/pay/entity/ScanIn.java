/*
 * Created: 2016-09-13
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
 */
package cn.concox.wechat.uti.pay.entity;
import cn.concox.wechat.uti.WeChatGlobals.WeChatInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class ScanIn extends BaseIn {

	/**
	 * 商户系统内部的订单号,32 个字符内、可包含字母,确保 在商户系统唯一
	 */
	private String out_trade_no;

	/**
	 * 商品描述 String(127)
	 */
	private String body;

	/**
	 * 总金额，以分为单位，不允许包含任何字
	 */
	private Integer total_fee;

	/**
	 * 订单生成的机器IP
	 */
	private String spbill_create_ip;

	/**
	 * 接收威富通通知的URL
	 */
	private String notify_url = WeChatInfo.NOTIFY_URL;

	/**
	 * 订单生成时间 格式为yyyymmddhhmmss
	 */
	private String time_start;

	/**
	 * 订单失效时间 格式为yyyymmddhhmmss
	 */
	private String time_expire;

	/**
	 * 随机字符串，不长于32 位
	 */
	private String nonce_str;

	private String trade_type = "NATIVE";

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

}
