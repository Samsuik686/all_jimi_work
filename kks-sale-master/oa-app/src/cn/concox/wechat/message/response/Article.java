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
 * 图文消息
 * @author Li.ShangZhi 
 * @version v1.0
 * @Time：2016-09-13 09:25:25
 */
public class Article {
	// 图文消息名称

	private String Title;

	// 图文消息描述

	private String Description;

	// 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80

	private String PicUrl;

	// 点击图文消息跳转链接

	private String Url;

	public String getTitle() {

		return Title;

	}

	public void setTitle(String title) {

		Title = title;

	}

	public String getDescription() {

		return null == Description ? "" : Description;

	}

	public void setDescription(String description) {

		Description = description;

	}

	public String getPicUrl() {

		return null == PicUrl ? "" : PicUrl;

	}

	public void setPicUrl(String picUrl) {

		PicUrl = picUrl;

	}

	public String getUrl() {

		return null == Url ? "" : Url;

	}

	public void setUrl(String url) {

		Url = url;

	}
}
