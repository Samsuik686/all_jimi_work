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
package cn.concox.wechat.uti.pay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class MD5 {

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public MD5() {
	}

	// 日志
	private static Logger log = Logger.getLogger(MD5.class);

	/**
	 * 签名
	 * 
	 * @param text
	 *            待签名字符串
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String sign(String text, String key, String charset) throws UnsupportedEncodingException {
		text = text + "&key=" + key;
		log.info("拼接字符串：" + text);
		return GetMD5Code(getContentBytes(text, charset)).toUpperCase();
	}

	/**
	 * 验证签名
	 * 
	 * @author Li.Shangzhi
	 * @createTime 2015年8月4日 上午9:55:32
	 * @param text
	 *            待签名字符串
	 * @param sign
	 *            签名
	 * @param key
	 * @return
	 */
	public static boolean verify(String text, String key, String sign, String charset) {
		text = text + "&key=" +key;
		log.info("拼接字符串：" + text);
		String mysign = GetMD5Code(getContentBytes(text, charset)).toUpperCase();
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public static String GetMD5Code(byte[] data) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(data));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (null == charset || charset.trim().length() == 0) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
