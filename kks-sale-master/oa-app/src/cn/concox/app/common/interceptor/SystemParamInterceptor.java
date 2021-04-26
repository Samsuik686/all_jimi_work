/*
 * Created: 2016-08-22 18:33:05
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
package cn.concox.app.common.interceptor;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sso.comm.model.UserInfo;
import sso.comm.util.SSOUtils;
import cn.concox.app.common.util.AdapterMapsUtil;
import cn.concox.app.common.util.HttpParamsParse;
import cn.concox.app.system.service.MenusService;
import cn.concox.comm.Globals;
import cn.concox.comm.util.CVConfigUtil;
import cn.concox.comm.util.CodeMsgPackage;
import cn.concox.vo.system.Menus;
/**
 * 适配器管理
 * {
 *  用户权限过滤，菜单过滤
 * }
 * @author Li.Shangzhi
 * @date 2016-08-22 18:33:11
 */
public class SystemParamInterceptor extends HandlerInterceptorAdapter{
	
	Logger urlFilterLog=Logger.getLogger("interceptor");
	
	//不需要登录的请求URL配置在mvc:interceptors的属性ignoreLogin中
	private String ignoreLogin="";
	
	@Resource(name="menusService")
	private MenusService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
			 
		    String httpType=request.getHeader("X-Requested-With");//XMLHttpRequest
		    String getReqReferer=request.getHeader("referer");//http://localhost:8080/oa-web/page/rolePrivilege/roleMgt.jsp
			UserInfo userInfo= (UserInfo)request.getSession().getAttribute(Globals.USER_KEY);
			String getReqURI=request.getRequestURI();
			
			urlFilterLog.info("Spring Intercepter开始拦截HTTP请求,"+"请求类型："+httpType+",请求URI:"+getReqURI+",请求URL："+request.getRequestURL().toString());
			
			// 判断访问OA地址中否带有项目名
			int cutPoint=1;
			String getOAAccessProjectName=CVConfigUtil.get(Globals.OAACCESSPROJECTNAME);
			if(StringUtils.isBlank(getOAAccessProjectName)){//访问无项目名
				//getReqURI=/system/MenuSelect，以匹配用户是否有此URL
				int getIndex=HttpParamsParse.getIndexByChar(getReqURI, '/', cutPoint);
				getReqURI=getReqURI.substring(getIndex+1);
				urlFilterLog.info("访问项目名：无"+",截取后的URI="+getReqURI) ;
			}else {//带项目名访问
				//getReqURI=/oa-web/system/MenuSelect,去掉项目名oa-web，以匹配用户是否有此URL
				cutPoint=2;
				int getIndex=HttpParamsParse.getIndexByChar(getReqURI, '/', cutPoint);
				getReqURI=getReqURI.substring(getIndex+1);
				urlFilterLog.info("访问项目名："+getOAAccessProjectName+",截取后的URI="+getReqURI) ;
			}
			
			
			if(reqSSOFlag(getReqURI)){
				
				
				if(null==userInfo){//未登录
					
					String returnURL;
					if(!StringUtils.isBlank(getReqReferer)){
						if(getReqReferer.indexOf(getOAAccessProjectName)<0){
							getReqReferer =	getReqReferer + getOAAccessProjectName;
						}
						returnURL=getReqReferer;
					}else {
						returnURL=getReqURI;
					}
					
					String ssoLogin=SSOUtils.builderSSOLoginURL(String.valueOf(returnURL));
					urlFilterLog.info("当前用户session为空，即未登录。请求SSO URL："+ssoLogin);
					
					HttpSession session=request.getSession();
					int ssoDomainIndex=HttpParamsParse.getIndexByChar(ssoLogin, '/', 4);
					String ssoDomainURL=ssoLogin.substring(0,ssoDomainIndex);
					session.setAttribute("ssoDomainURL", ssoDomainURL);
					
					if(null!=httpType&&Globals.REQUEST_XML.equals(httpType)){//Ajax异步请求
						
						PrintWriter writ=response.getWriter();
						writ.write(CodeMsgPackage.getJSONByCodeAndMsg(Globals.USER_NOLOGIN_CODE, ssoLogin));
						writ.flush();
						return false;
						
				    }else {//普通HTTP请求，如静态JSP页面
				    	response.sendRedirect(ssoLogin);
				    	return false;
				    }
					
				}else {//已登录过
					String getUId=userInfo.getuId();
						
						//设置用户菜单列表并保存到session
						if(setUserMenuIds(request,getUId)){
							
							if(!Globals.ADMINACCOUNT.equals(getUId)){
								
								//获取menu_details表中所有的权限:functionURL，并进行匹配。
								if(getAllFunctionURL(request,getReqURI)){//匹配表menu_details中存在此URI
								
									//获取用户MenuIds对应的functionIds及判断是否有权限操作
									if(powerTag(request,getReqURI,getUId)){//有访问权限
										
										urlFilterLog.info("当前用户已登录过,访问"+getReqURI+"权限判断:有");
										return true;
									}else {
										urlFilterLog.info("当前用户已登录过,访问"+getReqURI+"权限判断:无");
										PrintWriter writ=response.getWriter();
										writ.write(CodeMsgPackage.getJSONByCodeAndMsg(Globals.USER_MENUNOPOWER_CODE, Globals.USER_MENUNOPOWER_URL));
										writ.flush();
										return false;
									}
								}else {
									urlFilterLog.info("当前用户已登录过,访问"+getReqURI+"权限判断:有");
									return true;
//									//用户请求的URI在FunctionDetails表不存在。
//									urlFilterLog.info("用户请求的URI="+getReqURI+"在FunctionDetails表不存在");
//									PrintWriter writ=response.getWriter();
//									writ.write(CodeMsgPackage.getJSONByCodeAndMsg(Globals.USER_REQINVALID_CODE, Globals.USER_REQINVALID_CODE_URL));
//									writ.flush();
//									return false;
								}
							}else {
								return true;
							}
							
						}else {
							urlFilterLog.info("当前用户已登录过,该用户被赋予的角色与角色菜单表不匹配");
							PrintWriter writ=response.getWriter();
							writ.write(CodeMsgPackage.getJSONByCodeAndMsg(Globals.USER_MENUNOSET_CODE, Globals.USER_MENUNOPOWER_URL));
							writ.flush();
							return false;
						}
				}
			}else {
				return true;
			}
			
	}
	
	public boolean reqSSOFlag(String getReqURI){
		boolean reqSSOFlag=true;
		if(AdapterMapsUtil.CheckUrl(getReqURI)){
			reqSSOFlag=false;
		}
		return reqSSOFlag;
	}
	
	/**
	 * 所有权限URL设置
	 * @param request
	 * @param getReqURI
	 * @return
	 */
	public boolean getAllFunctionURL(HttpServletRequest request,String getReqURI){
		boolean allURLMatchTag=false;
		List<String> allfuncURLList= (List<String>)request.getSession().getAttribute(Globals.ALL_FUNCTIONURL_KEY);
		if(allfuncURLList==null || allfuncURLList.size()==0){
			allfuncURLList=service.queryAllFunctionURL();
			request.getSession().setAttribute(Globals.ALL_FUNCTIONURL_KEY,allfuncURLList);//所有权限URL设置
		}
		
		if(getPowerTag(getReqURI,allfuncURLList)){//所有的FunctionURL中有匹配到
			allURLMatchTag=true;
		}
		
		return allURLMatchTag;
	}
	
	/**
	 * 设置用户的菜单列表
	 * @param request
	 * @param getUId
	 * @return
	 */
	public boolean setUserMenuIds(HttpServletRequest request,String getUId){
		boolean menuTag=true;
		List<Menus> parentMenuList= (List<Menus>)request.getSession().getAttribute(Globals.USER_MENULIST_KEY);
		if(parentMenuList==null){
			parentMenuList=service.queryParentMenuListByUId(getUId);//一级菜单列表获取
			if(parentMenuList==null || parentMenuList.size()==0 ){
				menuTag=false;
			}else {
				for(int i=0;i<parentMenuList.size();i++){
					int getParentMenuId=parentMenuList.get(i).getMenuId();
					List<Menus> childMenuList=service.queryChildMenusListByUIDAndParentMenuIds(getUId, getParentMenuId);//二级菜单列表获取
					parentMenuList.get(i).setChildren(childMenuList);
				}
				request.getSession().setAttribute(Globals.USER_MENULIST_KEY,parentMenuList);//用户菜单列表设置
			}
		}
		return menuTag;
	}
	
	/**
	 * 请求URL与用户的functionURL是否匹配 
	 * @param request
	 * @param getReqURI
	 * @param getUId
	 * @return
	 */
	public boolean powerTag(HttpServletRequest request,String getReqURI,String getUId){
		boolean powerTag=false;
		//if(ignoreFileFilter(getReqURI)){//从applicationcontext-oa-common.xml中取
		if(ignoreCommonURLFilter(request,getReqURI)){//从表t_oa_menus_details中取，并且menuId like '2%'
			return true;
		}else {
			List<String> userFunctionURLS= (List<String>)request.getSession().getAttribute(Globals.USER_POWER_KEY);
			if(userFunctionURLS==null){
				 userFunctionURLS=service.queryUserFunctionURLsByUId(getUId);
				 if(userFunctionURLS==null){
					 return powerTag;
				 }else {
					 request.getSession().setAttribute(Globals.USER_POWER_KEY,userFunctionURLS);//用户权限操作functionURL清单设置
				 }
			}
			
			if(getPowerTag(getReqURI,userFunctionURLS)){//用户对应的FunctionURL中有匹配到
				powerTag=true;
			}
			
			return powerTag;
		}
		
	}
	
	public boolean ignoreCommonURLFilter(HttpServletRequest request,String getReqURI){
		boolean commonURLTag=false;
		List<String> commonFunctionURLS= (List<String>)request.getSession().getAttribute(Globals.COMMON_FUNCTIONURL_KEY);
		if(commonFunctionURLS==null){
			commonFunctionURLS=service.queryCommonFunctionURL();
			 if(commonFunctionURLS==null){
				 return commonURLTag;
			 }else {
				 request.getSession().setAttribute(Globals.COMMON_FUNCTIONURL_KEY,commonFunctionURLS);//公共FunctionURL设置
			 }
		}
		
		if(getPowerTag(getReqURI,commonFunctionURLS)){//公共FunctionURL中有匹配到
			commonURLTag=true;
		}
		
		return commonURLTag;
		
	}
	
	/**
	 * ignoreLogin配置的URL不用过匹配
	 * @param getReqURI
	 * @return
	 */
	public boolean ignoreFileFilter(String getReqURI){
		boolean flag = false;
		if(StringUtils.isNotEmpty(ignoreLogin)){
			String[] ignoreURLConfigs = ignoreLogin.split(",");
			for(String f : ignoreURLConfigs){
				if(getReqURI.trim().equals(f.trim())){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * getReqURI与funcURLs匹配判断公共方法
	 * @param getReqURI
	 * @param funcURLs
	 * @return
	 */
	public boolean getPowerTag(String getReqURI,List<String> funcURLs){
		boolean powerTag=false;
		int tag=0;
		for(String funcURL:funcURLs){
			if(!StringUtils.isBlank(funcURL)){
				//如果有多个URL，用逗号分开的情况
				if(funcURL.indexOf(",")!=-1){
					String[] manysURL=funcURL.split(",");
					for(String url:manysURL){
						if(getReqURI.trim().equals(url.trim())){
							return true;
						}else {
							continue;
						}
					}
				}else {
					if(getReqURI.trim().equals(funcURL.trim())){
						return true;
					}else {
						continue;
					}
				}
			}
		}
		return powerTag;
	}
	
	public String getIgnoreLogin() {
		return ignoreLogin;
	}

	public void setIgnoreLogin(String ignoreLogin) {
		this.ignoreLogin = ignoreLogin;
	}
	
}
