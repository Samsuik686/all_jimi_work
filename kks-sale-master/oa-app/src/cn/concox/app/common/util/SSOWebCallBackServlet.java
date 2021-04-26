/*
 * Created: 2013-9-27
 * This software consists of contributions made by concox R&D.
 * @author: Li Zhongjie
 * CallBackController.java
 */
package cn.concox.app.common.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import sso.comm.model.UserInfo;
import sso.comm.util.SSOUtils;
import cn.concox.comm.Globals;
import cn.concox.comm.util.CVConfigUtil;

/**
 * 该类是SSO登录完成以后的回调Servlet
 * SSO登录完成以后，我们能取到SSO返回给我们的access_token(登录令牌)，uId(用户ID)，登录前的地址uri
 * 我们拿到这些数据以后就实现了登录，根据用户ID我们可以设置自己系统的权限验证，等等。
 * SSO只是一个登录服务器，只是告诉你存在这个用户。
 * @author lizhongjie
 */
@Controller
public class SSOWebCallBackServlet extends HttpServlet {

	private static final long serialVersionUID = 4753915526306650632L;

	static Logger loginCallBackLog=Logger.getLogger("loginCallBack");
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//用户uId，拿到用户ID表示用户登录成功。
		String uId=request.getParameter("uId");
		//SSO系统登录令牌，在登录令牌有效期内，使用各个接入SSO的系统无需重复登录，与SSO服务器的通信凭证。
		String access_token=request.getParameter("access_token");
		loginCallBackLog.info("登录后回调方法被调用，access_token="+access_token);
		
		//uri的作用是登录操作完成以后返回登录前的地址。
		String callBackUri=request.getParameter("uri");
	 
		HttpSession session=request.getSession();
		
		SSOUtils sso=new SSOUtils();
		try {
			//将用户信息存入session
			UserInfo userInfo=sso.getUserInfo(uId);
			session.setAttribute(Globals.USER_KEY, userInfo);
			session.setAttribute("uId", userInfo.getuId());
			session.setAttribute("uName", userInfo.getuName());

			//将登录令牌存入Session
			session.setAttribute(Globals.ACCESS_TOKEN, access_token);
			session.setAttribute(Globals.MAIL_COUNT_ADDRESS, CVConfigUtil.get(Globals.MAIL_COUNT)+access_token);
			
			session.setAttribute(Globals.WEBMAIL_ADDRESS_PROP,  CVConfigUtil.get(Globals.WEBMAIL_ADDRESSS));
			//登录系列操作完成后，跳回用户登录之前的地址。
			
			loginCallBackLog.info("登录后回调方法被调用，uId="+userInfo.getuId()+",uName="+userInfo.getuName());
			
			if(!StringUtils.isBlank(callBackUri)){
				if(callBackUri.indexOf(".jsp")!=-1){
					//先跳转到/oa-web/主目录页面，然后在main.jsp里面再跳转到目的地址
					String childCallBackURL;
					int getRootIndex=HttpParamsParse.getIndexByChar(callBackUri, '/', 4);
					childCallBackURL=callBackUri.substring(getRootIndex+1);
					session.setAttribute("childCallBackURL", childCallBackURL);
					callBackUri=callBackUri.substring(0,getRootIndex);
				}
				loginCallBackLog.info("登录后回调方法被调用，重定向URL="+callBackUri);
				response.sendRedirect(callBackUri);
			}
			
		} catch (Exception e) {
			loginCallBackLog.info("SSOWebCallBackServlet 异常："+e.toString());
		}

	}
	
}
