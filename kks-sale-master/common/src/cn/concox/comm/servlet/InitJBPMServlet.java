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
package cn.concox.comm.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitJBPMServlet extends HttpServlet {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1903981703399293297L;

	/****
	 * 系统启动时初始化流程引擎
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		//ProcessEngine pe = Configuration.getProcessEngine();
	}

}
