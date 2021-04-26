/*
 * Created: 2016-10-10 10:02:23
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
package cn.concox.comm.sms;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import cn.concox.comm.util.StringUtil;
import cn.concox.comm.util.http.Result;
import cn.concox.comm.util.http.SendRequest;
/**
 * 短信发送
 * @author Li.Shangzhi
 * @date
 */
public class SMSUtil {
	
	public static String  SendSMS(SMSToken token,String mobile, String params) throws ClientProtocolException, IOException{
		if(null !=token && !StringUtil.isRealEmpty(token.valid) && !StringUtil.isRealEmpty(token.appId) && !StringUtil.isRealEmpty(token.sign)){
			Map<String, String> paramsMap=new HashMap<String, String>();
			paramsMap.put(SMSAPI.APIHead.appId,token.appId); 
			paramsMap.put(SMSAPI.APIHead.sign,token.sign);
			paramsMap.put(SMSAPI.APIHead.time,token.time);
			paramsMap.put(SMSAPI.APIHead.mobile, mobile);
			paramsMap.put(SMSAPI.APIHead.params, params);
			
			if(SMSAPI.valid.aftersalse1.equals(token.valid)){
				paramsMap.put(SMSAPI.APIHead.templateCode, SMSAPI.valid.aftersalse1);
			}else if(SMSAPI.valid.aftersalse2.equals(token.valid)){
				paramsMap.put(SMSAPI.APIHead.templateCode, SMSAPI.valid.aftersalse2);
			}else if(SMSAPI.valid.aftersalse3.equals(token.valid)){
				paramsMap.put(SMSAPI.APIHead.templateCode, SMSAPI.valid.aftersalse3);
			}
			Result result = SendRequest.sendPost(token.url, null, paramsMap, "utf-8");
			return result.getHtml(result, "utf-8");
		}else{
			return SMSAPI.SMS_FAILPARA;
		}
	}
	
	public static String  SendMAIL(SMSToken token,String toAddresss, String titile, String content) throws ClientProtocolException, IOException{
		if(null !=token && !StringUtil.isRealEmpty(token.valid) && !StringUtil.isRealEmpty(token.appId) && !StringUtil.isRealEmpty(token.sign)){
			Map<String, String> paramsMap=new HashMap<String, String>();
			paramsMap.put(MAILAPI.APIHead.appId,token.appId); 
			paramsMap.put(MAILAPI.APIHead.sign,token.sign);
			paramsMap.put(MAILAPI.APIHead.time,token.time);
			paramsMap.put(MAILAPI.APIHead.toAddresss, toAddresss);
			paramsMap.put(MAILAPI.APIHead.titile, titile);
			paramsMap.put(MAILAPI.APIHead.content, content);
			Result result = SendRequest.sendPost(token.url, null, paramsMap, "utf-8");
			return result.getHtml(result, "utf-8");
		}else{
			return MAILAPI.MAIL_FAILPARA;
		}
	}
	
	/**
	 * 测试案例
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		SMSToken token = new SMSToken("er7941a3fab34906a3f891b20957906b", "ce7941a3fab34906a3f891b20968906b","http://openapi.aichezaixian.com/sms/send", SMSAPI.valid.aftersalse1);
		
		String code = SMSUtil.SendSMS(token, "13680348517", "短信测试客户,PH20161010105524"); 
		System.out.println(code);
		
//		APIContent content=JSONObject.parseObject(code, APIContent.class);
//		System.out.println(content.getCode());
		
		
//		SMSToken token = new SMSToken("er7941a3fab34906a3f891b20957906b", "ce7941a3fab34906a3f891b20968906b","http://apisat.aichezaixian.com/mail/send", SMSAPI.valid.aftersalse1);		
//		String code = SMSUtil.SendMAIL(token, "1052847294@qq.com", "康凯斯售后部维修服务推送","邮件测试客户,PH20161010105524"); 
//		System.out.println(code);
		
	}

	
}
