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
package cn.concox.wechat.message.response;
/**
 * 回复的消息基类（公众帐号 -> 普通用户）
 * @author Li.ShangZhi 
 * @version v1.0
 * @Time：2016-09-13 09:25:31
 */
public class BaseMessage {

	// 接收方帐号（收到的OpenID）

	private String ToUserName;

	// 开发者微信号

	private String FromUserName;

	// 消息创建时间 （整型）

	private long CreateTime;

	// 消息类型（text/music/news）

	private String MsgType;

	// 位0x0001被标志时，星标刚收到的消息

	private int FuncFlag;

	public String getToUserName() {

		return ToUserName;

	}

	public void setToUserName(String toUserName) {

		ToUserName = toUserName;

	}

	public String getFromUserName() {

		return FromUserName;

	}

	public void setFromUserName(String fromUserName) {

		FromUserName = fromUserName;

	}

	public long getCreateTime() {

		return CreateTime;

	}

	public void setCreateTime(long createTime) {

		CreateTime = createTime;

	}

	public String getMsgType() {

		return MsgType;

	}

	public void setMsgType(String msgType) {

		MsgType = msgType;

	}

	public int getFuncFlag() {

		return FuncFlag;

	}

	public void setFuncFlag(int funcFlag) {

		FuncFlag = funcFlag;

	}

}