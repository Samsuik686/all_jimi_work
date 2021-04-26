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
package cn.concox.comm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MakeBytes {
	
	 
	 
	 /** 加密*/
	 public static byte[] enCode(byte[] reqCmd){
			byte[] q4s = new byte[reqCmd.length];
			byte[] h4s = new byte[reqCmd.length];
			byte[] bys = new byte[reqCmd.length];
			for(int i=0;i<reqCmd.length;i++){
				int qq4 = (reqCmd[i] >> 4) & 0xf;
				int qh4 = reqCmd[i] & 0xf;
				q4s[i] = (byte)qq4;
				h4s[i] =(byte)qh4;
			}
			for(int i=0,j=h4s.length-1;i<h4s.length;i++,j--){
				int q4 = q4s[i];
				int h4 = h4s[j];
				bys[i] = (byte)(((h4 & 0xf) << 4 ) | ( q4 & 0xf));
			}
			byte tmp = bys[0];
			bys[0]=bys[bys.length-1];
			bys[bys.length-1] = tmp;
			return bys;
	}

	/** 解密*/
	public static byte[] deCode(byte[] reqCmd){
		byte[] q4s = new byte[reqCmd.length];
		byte[] h4s = new byte[reqCmd.length];
		byte[] bys = new byte[reqCmd.length];
		for(int i=0;i<reqCmd.length;i++){
			int qh4 = (reqCmd[i] >> 4) & 0xf;
			int qq4 = reqCmd[i] & 0xf;
			q4s[i] = (byte)qq4;
			h4s[i] =(byte)qh4;
		}
		for(int i=0,j=h4s.length-1;i<h4s.length;i++,j--){
			int q4 = q4s[i];
			int h4 = h4s[j];
			bys[i] = (byte)(((q4 & 0xf) << 4 ) | ( h4 & 0xf));
		}
		byte tmp = bys[0];
		bys[0]=bys[bys.length-1];
		bys[bys.length-1] = tmp;
		return bys;
	}
	
	/** 字节流压缩*/
	public static byte[] gzip(byte[] pContent) throws IOException{
		byte[] vResult = null;
		ByteArrayOutputStream vByteOut = new ByteArrayOutputStream();
		GZIPOutputStream vOut = new GZIPOutputStream(vByteOut);
		vOut.write(pContent, 0, pContent.length);
		vOut.flush();
		vOut.close();
		vByteOut.close();
		vResult = vByteOut.toByteArray();
		return vResult;
	}
	
	/** 字节流解压*/
	public static byte[] unGzip(byte[] pContent) throws IOException{
		byte[] vResult = null;
		ByteArrayInputStream vByteIn = new ByteArrayInputStream(pContent);
		ByteArrayOutputStream vByteOut = new ByteArrayOutputStream();
		GZIPInputStream vIn = new GZIPInputStream(vByteIn);
		byte[] vTmp = new byte[4096];
		int vSize = -1;
		while(-1!=(vSize=vIn.read(vTmp, 0, vTmp.length))){
			vByteOut.write(vTmp,0, vSize);
		}
		vByteOut.flush();
		vIn.close();
		vResult = vByteOut.toByteArray();
		vByteOut.close();
		return vResult;
	}
	
	public static void main(String[] args) {
		String test="进口商的风景拉克是假发雷克萨sx觉得发了啊跨世纪的弗兰克啊少得可怜房价更权威u更i无二破i更我企鹅pga搜吧级哦撒度让他我离开家而过来看  阿肯色加来开发机构评为u更老卡机是路口大厦路口的见嘎斯的机关枪我配合骄傲是的架构阿飞文件哦改价啊路口圣诞节和轻微破发了我苦尽甘来卡仕达";
		byte[] jiami;
		try {
			System.out.println(System.currentTimeMillis());
			jiami = enCode(test.getBytes("GBK"));
			
			//jiami2 = enCode(test.getBytes("Unicode"));
		
			System.out.println(jiami);
			System.out.println(System.currentTimeMillis());
//			System.out.println(jiami.length);
//			System.out.println(jiami2.length);
//			System.out.println(new String(jiami));
//			System.out.println(new String(deCode(jiami),"GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
