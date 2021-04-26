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
package cn.concox.wechat.message;
/**
 * 事件名称
 * @author Li.ShangZhi 
 * @version v1.0
 * @Time：2016-09-13 09:25:16
 */
public final class EventType {
	/**
	 * 关注事件
	 */
	public static final String SUBSCRIBE = "subscribe";
	/**
	 * 取消关注事件
	 */
	public static final String UNSUBSCRIBE = "unsubscribe";
	/**
	 * 取消关注事件
	 */
	public static final String CLICK = "CLICK";

	private EventType() {
	}

}
