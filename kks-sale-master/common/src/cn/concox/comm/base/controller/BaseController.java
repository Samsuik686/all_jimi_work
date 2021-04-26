/*
 * Created: 2016-7-16
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
 * @file: BaseController.java
 * @functionName : BaseController
 */
package cn.concox.comm.base.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sso.comm.model.UserInfo;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.util.http.Result;
import cn.concox.comm.util.http.SendRequest;
import cn.concox.comm.vo.APIContent;

import com.alibaba.fastjson.JSONObject;
public class BaseController {
	protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	private String redirect_uri;

	private String appID;

	private String appSecret;

	private HttpServletRequest  request;

	@SuppressWarnings("unused")
	private ServletContext context;

	public ServletContext getContext() {
		return this.getRequest().getSession().getServletContext();
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public HttpServletRequest getRequest() {
		if (this.request==null) {
			request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		}
		return request;
	}

	/**
	 * 操作对象 
	 * 
	 * @param obj 结果集
	 * @return
	 */
	protected APIContent putAPIData(Object obj) {
		return new APIContent(obj);
	}

	/**
	 * 操作状态
	 * 
	 * @param code  状态码
	 * @param msg   状态详情
	 * @return
	 */
	protected APIContent operaStatus(String code,String msg) {
		return new APIContent(code, msg);
	}

	/**
	 * 返回API
	 * 
	 * @param code   状态码
	 * @param msg    状态详情
	 * @param obj    结果集
	 * @return
	 */
	protected APIContent returnJson(String code,String msg,Object obj) {
		return new APIContent(code, msg,obj);
	}

	/**
	 * 获取当前登录用户 ID
	 * @param req
	 * @return
	 */
	protected String getSessionUserName(HttpServletRequest req) {
		UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
		if(null !=user) return user.getuName(); else return null;
	}
	
	/**
	 * 获取当前登录用户
	 * @author TangYuping
	 * @version 2017年1月10日 下午4:22:05
	 * @param req
	 * @return
	 */
	protected UserInfo getSessionUser(HttpServletRequest req){
		UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
		if(user != null ){
			return user;
		}else{
			return new UserInfo();
		}
	}

	/**
	 * 获取OpenId
	 * 
	 * @param request
	 * @return
	 */
	public String getWeChatOpenId(HttpServletRequest request) {
		String openid = "";
		String code = request.getParameter("code");
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (!StringUtil.isEmpty(code)) {
				Result result = SendRequest.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appID+"&secret="+appSecret+"&code=" + code + "&grant_type=authorization_code", null, map, "utf-8");
				String jsonstr = result.getHtml(result, "UTF-8");
				JSONObject obj = JSONObject.parseObject(jsonstr);
				openid = obj.get("openid") + "";
				logger.info("WinXin-User OpenId:" + openid);
			}
		} catch (Exception e) {
			logger.error("WinXin getOpenId() is error!");
		}
		return openid;
	}

	/**
	 * 去微信绑定
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String toUnWinXinBind(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("redirect_uri", redirect_uri);
		model.addAttribute("appid", appID);
		return "/wechat/user/winxinlogin";
	}

	/**
	 * SSO Check
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void CheckSSO(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String SSOFalg = (String) request.getSession().getAttribute(GlobalCons.BATCHNUMBER);
		String SSOFalg = (String) request.getSession().getAttribute(GlobalCons.PHONENUMBER);
		if(StringUtil.isRealEmpty(SSOFalg)){
			response.sendRedirect(new StringBuilder(request.getContextPath())
			.append("/page") 
			.append("/alipay")
			.append("/login.jsp")
			.toString()
					);
		}
	}

	protected void SSOOut(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect(new StringBuilder(request.getContextPath())
		.append("/page")
		.append("/alipay")
		.append("/login.jsp")
		.toString());
	}

	protected void SSOIn(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect(new StringBuilder(request.getContextPath())
		.append("/page/alipay/ClientIndex.jsp")
		.toString());
	}


	protected void TOWeChatJSP(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect(new StringBuilder(request.getContextPath())
		.append("/wechat") 
		.append("/login.jsp")
		.toString());
	}
	public void returnJson(HttpServletResponse response, HttpServletRequest request, APIContent apiContent) {
		try {
			JSONObject json = new JSONObject(true);
			json.put("code", apiContent.getCode());
			json.put("data", apiContent.getData());
			json.put("msg", apiContent.getMsg());

			String contentType = "application/json; charset=UTF-8";
			if (request != null) {
				String accept = request.getHeader("accept");
				if (accept != null && !accept.contains("json") && !isXMLHttpRequest(request)) {
					contentType = "text/html; charset=UTF-8";
				}
			}
			response.setContentType(contentType);
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error("returnJson is error!", e);
			}
		}
	}
	public boolean isXMLHttpRequest(HttpServletRequest request) {
		String ajax = request.getHeader("X-Requested-With");
		if (ajax != null && ajax.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}

		return false;

	}

}
