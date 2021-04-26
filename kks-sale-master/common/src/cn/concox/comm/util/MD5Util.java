/*
 * Created: 2016-08-15 10:56:02
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
package cn.concox.comm.util;
import java.security.MessageDigest;
/**
 * 生成MD5加密字符
 * 
 * @author Li.Shangzhi
 * @date 2016-08-15 10:55:29
 */
public class MD5Util {
	private static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
        	e.printStackTrace();
//            System.out.println(e.getMessage());
        }
    }

    /**
     * 用于获取一个String的md5值
     * @param string
     * @return
     */
    public static String getMD5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(getMD5("hello world"));
    }
    
    
    /**
	 * MD5数字签名
	 * 
	 * @param src
	 * @return
	 */
	public static String md5Digest(String src) {
		// 定义数字签名方法, 可用：MD5, SHA-1
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(src.getBytes("UTF-8"));
			for (int i = 0; i < b.length; i++) {
				String s = Integer.toHexString(b[i] & 0xFF);
				if (s.length() == 1) {
					sb.append("0");
				}
				sb.append(s.toLowerCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
