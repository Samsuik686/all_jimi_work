/*
 * Created: 2012-9-5 
 * ==================================================================================================
 *
 * Cicove Technology Corp. Ltd. License, Version 1.0
 *
 * Copyright (c) 2011-2012 Cicove Tech. Co.,Ltd.   
 * 
 * Published by R&D Department, All rights reserved.
 * 
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 *
 * This software consists of contributions made by Cicove R&D.
 * @author: lizhongjie
 */
package cn.concox.comm.mail.other;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

public class CVMailUtil {
	private static String[] mails;
	static Logger logger=Logger.getLogger("workflowInfo");

	static {
		mails = CVConfigUtil.get("business-send-emails").replaceAll("\n\t\t", "").trim().split(";");
	}

	/**
	 * @Title: sendMailForeach 
	 * @Description:轮询发送邮件(文本格式)，成功就停止
	 * @param to 收件人地址
	 * @param title 标题
	 * @param textContent  内容
	 * @return 
	 * @author HuangGangQiang
	 * @date 2017年7月21日 下午3:53:13
	 */
	public static boolean sendMailForeach(String[] to, String title, String textContent){
		if(null != mails && mails.length > 0){
			for (int i = 0; i < mails.length; i++) {
				try {
					boolean flag = sendMail(mails[i],  to, title, textContent);
					if(flag){
						break;
					}
				} catch (MessagingException e) {
					logger.info("发送失败");
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param fromInfo
	 *            发送地址
	 * @param to
	 *            收件人地址
	 * @param title
	 *            标题
	 * @param textContent
	 *            内容
	 * @param type
	 * @return
	 * @throws MessagingException
	 */
	public static boolean sendMail(String fromInfo, String[] to, String title, String textContent) throws MessagingException {
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
			return true;
		} catch (MessagingException ex) {
			return false;
		}
	}


	private static String getFromAccount(String fromInfo) {
		return fromInfo.substring(0, fromInfo.lastIndexOf(','));
	}

	private static String getFromPwd(String fromInfo) {
		return fromInfo.substring(fromInfo.lastIndexOf(',') + 1);
	}

	public static void main(String[] args) throws MessagingException {
		CVMailUtil.sendMail(mails[0],"751102404@qq.com".split(","),"康凯斯测试","ceshi 123");
	}
	
	public static void chongfu(int nums[]){
		//int [] nums={1,1,1,2,2,2,3,5,6,3,1};
		//key 元素 value：重复了几次
		Map<Integer, Integer> countmap = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			if (countmap.containsKey(nums[i])) {
				Integer value=countmap.get(nums[i]);
				value=value+1;
				//key 元素 value：重复了几次
				countmap.put(nums[i],value);
			} else {
				countmap.put(nums[i],0);
				Integer value=countmap.get(nums[i]);
				if(null==value){
					value=1;
				}
				countmap.put(nums[i], value);
			}
		}
		//System.out.println(countmap.toString());
	}
}
