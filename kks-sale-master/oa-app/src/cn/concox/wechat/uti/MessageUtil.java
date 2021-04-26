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
package cn.concox.wechat.uti;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import cn.concox.wechat.message.response.Article;
import cn.concox.wechat.message.response.NewsMessage;
import cn.concox.wechat.message.response.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;


public class MessageUtil {

	public static String getSendUrl() {
		String url = WeChatGlobals.WeChatURL.sendmessage.replace(WeChatGlobals.Other.PLACEHOLDER, WeChatGlobals.WeChatInfo.getACCESS_TOKEN());
		return url;
	}

	public static String getTextMessages(String OpenId, String ContentTXT) {
		MessageText msgs = new MessageText();
		msgs.setTouser(OpenId);
		msgs.setMsgtype("text");
		Text text = new Text();
		text.setContent(ContentTXT);
		msgs.setText(text);
		return JSONObject.toJSONString(msgs);
	}

	public static String getImageMessages(String openId, String description, String url, String picurl, String title) {
		ImageMessage im = new ImageMessage();
		im.setTouser(openId);
		im.setMsgtype("news");
		Articles articles = new Articles();
		ImgContent imgContent = new ImgContent();
		imgContent.setDescription(description);
		imgContent.setPicurl(picurl);
		imgContent.setTitle(title);
		imgContent.setUrl(url);
		List<ImgContent> icList = new ArrayList<ImgContent>();
		icList.add(imgContent);
		articles.setArticles(icList);
		;
		im.setNews(articles);
		return JSONObject.toJSONString(im);
	}

	/**
	 * 
	 * 文本消息对象转换成xml
	 * 
	 * 
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * 
	 * @return xml
	 */

	public static String textMessageToXml(TextMessage textMessage) {

		xstream.alias("xml", textMessage.getClass());

		return xstream.toXML(textMessage);

	}

	/**
	 * 
	 * 图文消息对象转换成xml
	 * 
	 * 
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * 
	 * @return xml
	 */

	public static String newsMessageToXml(NewsMessage newsMessage) {

		xstream.alias("xml", newsMessage.getClass());

		xstream.alias("item", new Article().getClass());

		return xstream.toXML(newsMessage);

	}

	/**
	 * 
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * 
	 * 
	 * @date 2013-05-19
	 */

	private static XStream xstream = new XStream(new XppDriver() {

		public HierarchicalStreamWriter createWriter(Writer out) {

			return new PrettyPrintWriter(out) {

				// 对所有xml节点的转换都增加CDATA标记

				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {

					super.startNode(name, clazz);

				}

				protected void writeText(QuickWriter writer, String text) {

					if (cdata) {

						writer.write("<![CDATA[");

						writer.write(text);

						writer.write("]]>");

					} else {

						writer.write(text);

					}

				}

			};

		}
	});
}

class MessageText {

	private String touser;
	private String msgtype;
	private Text text;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
}

class Text {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

class ImageMessage {
	private String touser;

	private String msgtype = "news";

	private Articles news;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Articles getNews() {
		return news;
	}

	public void setNews(Articles news) {
		this.news = news;
	}

}

class Articles {
	private List<ImgContent> articles;

	public List<ImgContent> getArticles() {
		return articles;
	}

	public void setArticles(List<ImgContent> articles) {
		this.articles = articles;
	}

}

class ImgContent {
	private String title;

	private String description;

	private String url;

	private String picurl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

}
