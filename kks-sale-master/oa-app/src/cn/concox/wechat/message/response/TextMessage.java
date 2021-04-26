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
 * 消息内容
 * @author Li.ShangZhi 
 * @version v1.0
 * @Time：2016-09-13 09:25:39
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {

		return Content;

	}

	public void setContent(String content) {

		Content = content;

	}

}