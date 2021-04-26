package cn.concox.app.common.util;
import java.util.HashMap;
import java.util.Map;
/**
 * AdapterMapsUtil
 * CAS 匿名登录路径配置管理
 * @author Li.Shangzhi
 * @date   2016-08-22 17:04:21
 */
public class AdapterMapsUtil{
	private static Map<String, String> domainMap=new HashMap<String, String>();
	static{
		//TODO 系统作用域,分页，退出管理过滤
		domainMap.put("getSessionStatus", "getSessionStatus");
		domainMap.put("queryCurrentPageSize", "queryCurrentPageSize");
		domainMap.put("oaLogout", "oaLogout");
		domainMap.put("userLogin", "userLogin");
		
		
		//TODO CAS SSO 业务过滤
		domainMap.put("ClientIndex", "ClientIndex");      
		domainMap.put("custClient", "custClient");
		
		//TODO WeChat 微信
		domainMap.put("wechat", "wechat");
		domainMap.put("wechatdev", "wechatdev");
		domainMap.put("wechatpay", "wechatpay");
	}
	
	public static boolean CheckUrl(String url) {
		for (Map.Entry<String, String> entry : domainMap.entrySet()) {
			if(url.indexOf(entry.getValue())!=-1){
				return true;
			}
		}
		return false;
	}
	
	public static String subURLhttp(String url) {
		if (url.indexOf("://") != -1) {
			url = url.substring(url.indexOf("://") + 3, url.length());
		}
		if (url.indexOf("/") != -1) {
			url = url.substring(0, url.indexOf("/"));
		}
		if (url.indexOf(":") != -1) {
			url = url.substring(0, url.indexOf(":"));
		}
		return url.toLowerCase();
	}
	
	
	public static void main(String[] args) {
		for (Map.Entry<String, String> entry : domainMap.entrySet()) {
			   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}

}
