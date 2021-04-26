/*
 * Created: 2016-7-12
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
 **/
package cn.concox.comm.mail;

import javax.mail.MessagingException;

import cn.concox.comm.util.CVConfigUtil;

import java.io.UnsupportedEncodingException;

public class CVMailUtil {

	private static int sendNo = 0;

	public static boolean sendMailText(String fromInfo,
			String to, String title, String textContent)
			throws MessagingException, UnsupportedEncodingException {
		// 这个类主要是设置邮件
		CVMailSendInfo mailInfo = new CVMailSendInfo();
		mailInfo.setValidate(true);
		mailInfo.setUserName(getFromAccount(fromInfo));
		mailInfo.setPassword(getFromPwd(fromInfo));
		mailInfo.setFromAddress(getFromAccount(fromInfo));
		mailInfo.setToAddress(to);
		mailInfo.setSubject(title);
		mailInfo.setContent(textContent);
		
		try {
			CVMailSender.sendTextMail(mailInfo);
			sendNo = 0;
			return true;
		} catch (MessagingException | UnsupportedEncodingException ex) {
			// 发送失败需要发送短消息给用户，提示重新发送，或者重新发送
			if (String.valueOf(sendNo).equals(CVConfigUtil.get("sendemial-numberoftimes")))
				throw ex;
			sendNo++;
			return sendMailText(fromInfo, to, title, textContent);
		}// 发送文体格式
	}
	
	private static String getFromAccount(String fromInfo){
		return fromInfo.substring(0,fromInfo.lastIndexOf(','));
	}
	private static String getFromPwd(String fromInfo){
		return fromInfo.substring(fromInfo.lastIndexOf(',')+1);
	}
}
