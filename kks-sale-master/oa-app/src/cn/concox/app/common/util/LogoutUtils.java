package cn.concox.app.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sso.comm.model.UserInfo;
import sso.comm.model.UserLogin;
import sso.comm.util.SSOUtils;

import cn.concox.comm.Globals;
import cn.concox.comm.util.CVConfigUtil;
import cn.concox.comm.util.CodeMsgPackage;
import cn.concox.comm.vo.APIContent;


@Controller
@Scope("prototype")
public class LogoutUtils {
	
	Logger log=Logger.getLogger("interceptor");
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/oaLogout")
	@ResponseBody
	public APIContent logoutUtils(HttpServletRequest request,HttpServletResponse response){
			UserInfo userInfo=(UserInfo)request.getSession().getAttribute(Globals.USER_KEY);
			String getReqURL=request.getRequestURL().toString();
			try {
				
				if(userInfo!=null){
					String getUId=userInfo.getuId();
					request.getSession().removeAttribute(Globals.ACCESS_TOKEN);//用户token信息删除
					request.getSession().removeAttribute(Globals.USER_KEY);//用户Login后信息
					request.getSession().removeAttribute(Globals.USER_ID_KEY);//uId
					request.getSession().removeAttribute(Globals.USER_MENULIST_KEY);//用户左边菜单显示列表
					request.getSession().removeAttribute(Globals.USER_POWER_KEY);//用户对应的functionURL匹配
					request.getSession().removeAttribute(Globals.ALL_MENU_AND_FUNC_KEY);//角色分配权限时列表
					request.getSession().removeAttribute(Globals.ALL_FUNCTIONURL_KEY);//角色分配权限时列表
					request.getSession().removeAttribute(Globals.COMMON_FUNCTIONURL_KEY);//公共请求的functionURL
					request.getSession().removeAttribute("uId");//
					request.getSession().removeAttribute("uName");//
					
				    getReqURL=getReqURL.substring(0, getReqURL.lastIndexOf("/"));//回到系统根目录
				    SSOUtils ssoUtils=new SSOUtils();
				    ssoUtils.ssoLogOutByUId(getUId);
				    
				}else {//add by wg.he 2013/12/24
					
					int cutPoint=3;
					String getOAAccessProjectName=CVConfigUtil.get(Globals.OAACCESSPROJECTNAME);
					if(!StringUtils.isBlank(getOAAccessProjectName)){//带项目名
						cutPoint=4;
					}
					int getIndex=HttpParamsParse.getIndexByChar(getReqURL, '/', cutPoint);
					getReqURL=getReqURL.substring(0,getIndex);
					
				}
			
				String ssoLogin=SSOUtils.builderSSOLoginURL(String.valueOf(getReqURL));//重新请求SSO到登录页面
				return new APIContent(Globals.USER_NOLOGIN_CODE,ssoLogin);
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("用户退出操作异常："+e.toString());
				return new APIContent(Globals.USER_LOGOUT_CODE_EXC, Globals.USER_LOGOUT_CODE_EXC_DESC);
			}
	}
	
	/**
	 * 用户修改密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/oaEditPassword")
	@ResponseBody
	public APIContent oaEditPassword(HttpServletRequest request,HttpServletResponse response){
			UserInfo userInfo=(UserInfo)request.getSession().getAttribute(Globals.USER_KEY);
			String getUId=userInfo.getuId();
			String newPassword=request.getParameter("newpass");
			
			String oldPassword=request.getParameter("oldpass");
			
			UserLogin ul=new UserLogin();
			ul.setuId(getUId);
			ul.setuPwd(newPassword);
			
		    String getReqURL=request.getRequestURL().toString();
		    getReqURL=getReqURL.substring(0, getReqURL.lastIndexOf("/"));//回到系统根目录
		    SSOUtils ssoUtils=new SSOUtils();
		    try {
		    	//判断之前的密码是否正确
		    	UserLogin userLogin=new UserLogin();
				userLogin.setuId(getUId);
				userLogin.setuPwd(oldPassword);
			    String getUIdP=ssoUtils.validUserLogin(userLogin);
			    if(StringUtils.isBlank(getUIdP)){//原密码无效
			    	return new APIContent(Globals.USERLOGINSTATUS,Globals.USERLOGINSTATUS_DESC);
			    }else {
			    	//更新密码操作
			    	if(ssoUtils.updateUserPassWord(ul)){
						ssoUtils.ssoLogOutByUId(getUId);
						String ssoLogin=SSOUtils.builderSSOLoginURL(String.valueOf(getReqURL));
						return new APIContent(newPassword,ssoLogin);
					}else {
						return new APIContent(Globals.USER_NOLOGIN_EDITPD_CODE,Globals.USER_NOLOGIN_EDITPD_CODE_DESC);
					}
			    }
		    	
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("用户"+getUId+"修改密码操作异常："+e.toString());
				return new APIContent(Globals.USER_NOLOGIN_EDITPD_CODE,Globals.USER_NOLOGIN_EDITPD_CODE_DESC);
			}
	}
	
	/**
	 * 获取用户Session是否过期
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSessionStatus")
	@ResponseBody
	public APIContent getSessionStatus(HttpServletRequest request,HttpServletResponse response){
			try {
				UserInfo userInfo=(UserInfo)request.getSession().getAttribute(Globals.USER_KEY);
				StringBuffer url=request.getRequestURL();
				String getRI=request.getParameter("uri");
				
				
				int getIndex=HttpParamsParse.getIndexByChar(url.toString(), '/', 4);
				String getReqURI=url.toString().substring(0,getIndex+1);
				
				String getReturnURI=StringUtils.isBlank(getRI) ? "" : getReqURI+getRI;
				String ssoLogin=SSOUtils.builderSSOLoginURL(String.valueOf(getReturnURI));
			    
				if(userInfo==null){//未登录
						return new APIContent(Globals.USER_NOLOGIN_CODE,ssoLogin);
				}else {
						return new APIContent(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
				}
			} catch (Exception e) {
				log.error("获取用户Session状态操作异常："+e.toString());
				return new APIContent(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
	}
	
	
}
