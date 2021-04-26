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
import java.util.List;
public class NewsMessage extends BaseMessage {
	// 图文消息个数，限制为10条以内

	private int ArticleCount;

	// 多条图文消息信息，默认第一个item为大图

	private List<Article> Articles;

	public int getArticleCount() {

		return ArticleCount;

	}

	public void setArticleCount(int articleCount) {

		ArticleCount = articleCount;

	}

	public List<Article> getArticles() {

		return Articles;

	}

	public void setArticles(List<Article> articles) {

		Articles = articles;

	}
}
