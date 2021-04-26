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
import java.util.HashMap;
import java.util.Map;
import cn.concox.comm.util.http.Result;
import cn.concox.comm.util.http.SendRequest;
import com.alibaba.fastjson.JSONObject;

public class WeChatUtil {

	public static String getAccess_Token() {
		try {
			Map<String, String> params = new HashMap<String, String>();
			Result result = SendRequest.sendGet(WeChatGlobals.WeChatURL.GETACCESS_TOKEN_URL, null, params, "utf-8");
			String html = result.getHtml(result, "UTF-8");
			JSONObject json = JSONObject.parseObject(html);
			String access_token = String.valueOf(json.get("access_token"));
			return access_token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getJsapiTicket(String token) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", "jsapi");
			params.put("access_token", token);
			Result result = SendRequest.sendGet(WeChatGlobals.WeChatURL.GET_JSAPI_TICKET_URL, null, params, "utf-8");
			String html = result.getHtml(result, "UTF-8");
			JSONObject json = JSONObject.parseObject(html);
			String jsapi_ticket = String.valueOf(json.get("ticket"));
			return jsapi_ticket;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
