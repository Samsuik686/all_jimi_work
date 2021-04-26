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
package cn.concox.comm.exception;


public class EUtils {
	public static StringBuilder emsg(String errorCode,String msg,Object o){
		StringBuilder sb=new StringBuilder();
		sb.append(errorCode);
		sb.append(",");
		sb.append(msg);
		sb.append(",系统错误:");
		sb.append(o);
		return sb;
	}
	
	public static StringBuilder emsg(String errorCode,Object o){
		StringBuilder sb=new StringBuilder();
		sb.append(errorCode);
		sb.append(",sys info:");
		sb.append(o);
		return sb;
	}
}
