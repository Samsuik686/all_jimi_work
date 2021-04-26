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
package cn.concox.wechat.api;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import cn.concox.wechat.message.EventType;
import cn.concox.wechat.message.ReqType;
import cn.concox.wechat.message.response.Article;
import cn.concox.wechat.message.response.NewsMessage;
import cn.concox.wechat.uti.MessageUtil;
import cn.concox.wechat.uti.RequestUtil;
import cn.concox.wechat.uti.WeChatGlobals;

/**
 * 微信处理
 * @author Li.ShangZhi 
 * @version v1.0
 * @Time：2016-09-13 09:24:47
 */
public class WeChatHandleController {
	private static final Logger logger=Logger.getLogger("privilege");

	private static final String REPLY_MSG = "您好！如有疑问请拨打客服专线：4006165932";

	protected String fromUserName, toUserName;

	/**
	 * 微信消息交互处理
	 * 
	 * 处理微信服务器发来的请求方法
	 * 
	 * @return 处理消息的结果，已经是接口要求的xml报文 
	 */
	public String processRequest(HttpServletRequest request) {
		logger.debug("processRequest(HttpServletRequest) - start"); //$NON-NLS-1$

		String result = "";
		try {
			request.setCharacterEncoding("UTF-8");

			Map<String, Object> reqMap = RequestUtil.parseXml(request, WeChatGlobals.WeChatInfo.TOKEN, WeChatGlobals.WeChatInfo.APPID, WeChatGlobals.WeChatInfo.AESKey);
			fromUserName = (String) reqMap.get("FromUserName");
			toUserName = (String) reqMap.get("ToUserName");
			String msgType = (String) reqMap.get("MsgType");

			logger.debug("收到消息,消息类型:" + msgType);

			// 事件消息
			if (msgType.equals(ReqType.EVENT)) {
				String eventType = (String) reqMap.get("Event");

				if (eventType.equals(EventType.SUBSCRIBE)) {
					// 关注后发送图文消息
					result = handleNewsMsg();
					logger.debug("关注事件");
				} else if (eventType.equals(EventType.UNSUBSCRIBE)) {
					logger.debug("取消关注事件");
				}
			} else {// 文本消息
				result = handleTextMsg(REPLY_MSG);
				logger.debug("文本事件");
				// result = handleNewsMsg();
			}
		} catch (Exception e) {
			logger.error("处理微信服务器发来的请求方法错误！" + e.getMessage());
		}

		logger.debug("processRequest(HttpServletRequest) - end"); //$NON-NLS-1$
		return result;
	}

	/*-------------------------------消息处理------------------------------------*/

	/**
	 * 回复文本消息
	 * 
	 * @param content
	 *            回复消息内容
	 * @return 响应消息
	 */
	protected String handleTextMsg(String content) {
		logger.debug("handleTextMsg(String) - start"); //$NON-NLS-1$

		String result = "";
		String msgType = "text";
		String time = System.currentTimeMillis() + "";
		String textTpl = "<xml>" + "<ToUserName><![CDATA[%1$s]]></ToUserName>" + "<FromUserName><![CDATA[%2$s]]></FromUserName>" + "<CreateTime>%3$s</CreateTime>" + "<MsgType><![CDATA[%4$s]]></MsgType>" + "<Content><![CDATA[%5$s]]></Content>" + "<FuncFlag>0</FuncFlag>" + "</xml>";
		result = String.format(textTpl, fromUserName, toUserName, time, msgType, content);

		logger.debug("handleTextMsg(String) - end"); //$NON-NLS-1$
		return result;
	}

	/**
	 * 回复图文消息
	 * 
	 * @param content
	 *            回复消息内容
	 * @return 响应消息
	 */
	protected String handleNewsMsg() {
		logger.debug("handleNewsMsg() - start"); //$NON-NLS-1$

		String result = "";
		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag(1);

		List<Article> articleList = new ArrayList<Article>();
		// 单图文消息
		Article article = new Article();
		article.setTitle("欢迎关注几米终端客户服务");
		article.setDescription("绑定您送修批号可以立即体验更多服务，请点击绑定...");
		article.setPicUrl("https://mmbiz.qlogo.cn/mmbiz_jpg/YvOTibGQhyvAOmKIJGHswBXtKX0Wl2O2sPNibZFhcBiamdqLd0gDWx1TqjicppLB4B6ZQOtYichaxomj44dytvjvpbw/0?wx_fmt=jpeg");
		article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatGlobals.WeChatInfo.APPID + "&redirect_uri=http%3a%2f%2fwww.concox400.com%2fsale-web%2fwechatdev%2ftoDevCon&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect");
		articleList.add(article);

		// 设置图文消息个数
		newsMessage.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(articleList);
		// 将图文消息对象转换成xml字符串
		result = MessageUtil.newsMessageToXml(newsMessage);

		logger.debug("handleNewsMsg() - end"); //$NON-NLS-1$
		return result;
	}

	/**
	 * 请求合法性判断
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isLegal(HttpServletRequest request) {
		logger.debug("isLegal(HttpServletRequest) - start"); //$NON-NLS-1$

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		// String token = "TOKEN";
		String[] tmpArr = { WeChatGlobals.WeChatInfo.TOKEN, timestamp, nonce };
		Arrays.sort(tmpArr);
		String tmpStr = this.ArrayToString(tmpArr);
		tmpStr = this.SHA1Encode(tmpStr);
		if (tmpStr.equalsIgnoreCase(signature)) {
			logger.debug("isLegal(HttpServletRequest) - end"); //$NON-NLS-1$
			return true;
		} else {
			logger.debug("isLegal(HttpServletRequest) - end"); //$NON-NLS-1$
			return false;
		}
	}

	// 数组转字符串
	public String ArrayToString(String[] arr) {
		logger.debug("ArrayToString(String[]) - start"); //$NON-NLS-1$

		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			bf.append(arr[i]);
		}
		String returnString = bf.toString();
		logger.debug("ArrayToString(String[]) - end"); //$NON-NLS-1$
		return returnString;
	}

	// sha1加密
	public String SHA1Encode(String sourceString) {
		logger.debug("SHA1Encode(String) - start"); //$NON-NLS-1$

		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			logger.warn("SHA1Encode(String) - exception ignored", ex); //$NON-NLS-1$
		}

		logger.debug("SHA1Encode(String) - end"); //$NON-NLS-1$
		return resultString;
	}

	public final String byte2hexString(byte[] bytes) {
		logger.debug("byte2hexString(byte[]) - start"); //$NON-NLS-1$

		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		String returnString = buf.toString().toUpperCase();
		logger.debug("byte2hexString(byte[]) - end"); //$NON-NLS-1$
		return returnString;
	}

	// 向请求端发送返回数据
	public void print(HttpServletResponse response, String content) {
		logger.debug("print(HttpServletResponse, String) - start"); //$NON-NLS-1$

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(content);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.error("返回数据失败!" + e.getMessage());
		}

		logger.debug("print(HttpServletResponse, String) - end"); //$NON-NLS-1$
	}
}