package cn.concox.comm.sms;

/**
 * MAIL 状态码
 * @author Ai.Kuangyong
 *
 */
public interface MAILAPI {
	
	public static final String MAIL_SUCCESS      = "0";            		//成功
	
	public static final String MAIL_FAILPARA     = "12002";			   	//参数错误
	
	public static final String MAIL_SERVICERROR  = "13001";			   	//服务出现异常，推送失败
	
	class APIHead{

		public static final String appId    = "appId";            		
		
		public static final String sign    = "sign";
		
		public static final String time    = "time";
		
		public static final String toAddresss   = "toAddresss";   //邮件接收者地址，多个使用’,’进行拼接         		
		
		public static final String titile   = "titile";       //邮件主题

		public static final String content   = "content";     //邮件内容          		
		
	}
	
}
