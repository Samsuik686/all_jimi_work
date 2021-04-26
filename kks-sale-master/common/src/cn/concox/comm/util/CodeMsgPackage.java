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

import cn.concox.comm.Globals;

public class CodeMsgPackage {

	/**
	 * PrintWriter输出JSON
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String getJSONByCodeAndMsg(String code,String msg){
		return "{\"rows\":\""+null+"\",\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";//FF与Chrome不支持单引号
	}
}
