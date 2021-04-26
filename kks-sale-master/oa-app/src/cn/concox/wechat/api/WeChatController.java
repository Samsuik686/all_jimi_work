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
package cn.concox.wechat.api;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * WeChat
 * @author Li.ShangZhi 
 * @version v1.0
 * @Time：2016-09-13 09:24:54
 */
@Controller
@RequestMapping(value = "/wechat")
public class WeChatController extends WeChatHandleController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WeChatController.class);

	/**
	 * 微信服务器验证入口
	 *
	 * @param request
	 *            请求
	 * @return 响应内容
	 */
	@RequestMapping(value = "/enter", method = { RequestMethod.GET })
	protected void bind(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("bind(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$

		if (isLegal(request)) {
			// 绑定微信服务器成功
			super.print(response, request.getParameter("echostr"));
		} else {
			// 绑定微信服务器失败
			super.print(response, "");
		}

		logger.debug("bind(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
	}
	/**
	 * 微信消息交互入口
	 *
	 * @param request
	 *            http 请求对象 响应给微信服务器的消息报文
	 * 
	 */
	@RequestMapping(value = "/enter", method = { RequestMethod.POST })
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("process(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$

		if (!isLegal(request)) {
			super.print(response, "");
		}
		super.print(response, processRequest(request));

		logger.debug("process(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
	}
}