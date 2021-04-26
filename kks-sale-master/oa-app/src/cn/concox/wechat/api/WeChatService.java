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
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.concox.comm.util.ConfigUtil;
import cn.concox.comm.util.http.Result;
import cn.concox.comm.util.http.SendRequest;
import cn.concox.wechat.uti.MessageUtil;
import cn.concox.wechat.uti.WeChatGlobals;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信端发送服务
 * @author Li.ShangZhi 
 * @version v1.0
 * @Time：2016-09-13 09:24:41
 */
public class WeChatService {
	private static Logger logger = Logger.getLogger(WeChatService.class);
	
	private static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token";
	private static final String SEND_MSG_URL ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	private static final String ENCODING="utf-8";
	
	public static final String getToken(){
		String access_token ="";
		try {
			Map<String, String> params=new HashMap<String, String>();
			params.put("grant_type", "client_credential");
			params.put("appid",ConfigUtil.getString("appID"));
			params.put("secret",ConfigUtil.getString("appSecret"));
			Result result = SendRequest.sendPost(GET_TOKEN_URL, null, params, ENCODING);
			String jsonStr = result.getHtml(result, ENCODING);
			JSONObject json=JSONObject.parseObject(jsonStr);
			access_token=String.valueOf(json.get("access_token"));
			if("null".equalsIgnoreCase(access_token)){
				access_token = null;
			}
		} catch (Exception e) {
			logger.error("getToken() is error!"+e); 
		}
		return access_token;
	}
	
	/**
	 * 微信消息推送(文本消息)
	 * 
	 * @param openId 接受者OPPENID
	 * @param content 发送内容
	 * @return
	 */
	public static final boolean sendMsg(String openId,String content){
		try {
			String access_token= WeChatGlobals.WeChatInfo.ACCESS_TOKEN;
			
			if(null==access_token || "".equals(access_token)){
				WeChatGlobals.WeChatInfo.ACCESS_TOKEN = getToken();
				access_token = WeChatGlobals.WeChatInfo.ACCESS_TOKEN.toString();
			}
			
			String sendJson =MessageUtil.getTextMessages(openId, content);
			int returnCode = SendRequest.sendPostBody(SEND_MSG_URL+access_token,sendJson, null, ENCODING);
			if(returnCode == 200){
				return true;
			}
			//token过期 更新token,并再发送一次
			if(returnCode == 40014){
				WeChatGlobals.WeChatInfo.ACCESS_TOKEN = WeChatService.getToken();
				sendMsg(openId,content);
			}
			
		} catch (Exception e) {
			logger.error("weixin: sendMsg() is error!"+e); 
		}
		return false;
	}
	
	public static void main(String[] args) {
		sendMsg("ohxpOxIdrkxOsjtFsl8UgT9hw5tA", "客服消息测试!");
	}
}
