package cn.concox.comm.sms;
/**
 * SMS 状态码
 * @author Li.Shangzhi
 * @date
 */
public interface SMSAPI {
	
	
	public static final String SMS_SUCCESS      = "0";            		//成功
	
	public static final String SMS_FAILPARA     = "12002";			   	//参数错误
	
	public static final String SMS_SERVICERROR  = "13001";			   	//服务出现异常，推送失败
	
	class APIHead{

		public static final String appId    = "appId";            		
		
		public static final String sign    = "sign";
		
		public static final String time    = "time";
		
		public static final String mobile   = "mobile";            		
		
		public static final String params   = "params";

		public static final String templateCode   = "templateCode";            		
		
	}
	
	class valid{
		
		public static final String aftersalse1   = "valid.aftersales1";            		
		
		public static final String aftersalse2   = "valid.aftersales2";	
		
		public static final String aftersalse3   = "valid.aftersales3";  
	}
}
