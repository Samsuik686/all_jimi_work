package cn.concox.comm.filter;
import java.io.IOException;

import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.util.CVConfigUtil;
import cn.concox.comm.util.StringUtil;
/**
 * CAS SSO 单点登录Client 拦截
 * @author Li.Shangzhi
 * @date 2016-08-22 14:29:19
 */
public class AccessFilter implements Filter{

//	private static Logger logger = Logger.getLogger(AccessFilter.class);
	
    @Override  
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)throws IOException, ServletException {    
//    	logger.info("------------------ CAS Filter  - Start");
    	HttpServletRequest request = (HttpServletRequest)req;    
        HttpServletResponse response = (HttpServletResponse)res;  
        
        String SSOFalg = (String) request.getSession().getAttribute(GlobalCons.BATCHNUMBER);
        
        String getOAAccessProjectName=CVConfigUtil.get(Globals.OAACCESSPROJECTNAME);
        if(request.getRequestURI().indexOf("login.jsp")==-1 || StringUtil.isRealEmpty(SSOFalg)){   //TODO 客户登录
        	if(request.getRequestURI().indexOf("ClientIndex.jsp")==-1){
        		response.sendRedirect(new StringBuilder("/"+getOAAccessProjectName)
									  .append("/page")
									  .append("/alipay")
									  .append("/login.jsp")
									  .toString());   
        		return ; 
        	}else{
        		response.sendRedirect(new StringBuilder("/"+getOAAccessProjectName) 
        							  .append("/page")
        							  .append("/alipay")
									  .append("/ClientIndex.jsp")
									  .toString());
        		return ; 
        	}
        }
//        logger.info("----------------- CAS Filter  - End");
        chain.doFilter(req, res);   
    }  
  
    
    
    @Override  
    public void init(FilterConfig config) throws ServletException {  
//        System.out.println(config.toString());  
    }  
	@Override  
    public void destroy() {  
//        System.out.println("destroy!");  
    }
	
	/*public static void main(String[] args) {
		String m = "/oa-web/alipay/resource/ClientIndex.jsp";
		
		System.out.println(m.indexOf("ClientIndex.jsp")); 
	}*/

}
