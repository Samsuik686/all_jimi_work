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
package cn.concox.wechat.uti;

public interface WeChatGlobals {

	public class WeChatInfo {
		//----------------------原康凯斯微信相关-------------------------------------
		
		// // WeChat 公众号
		// public static final String TOKEN = "Concox_Jimi";
		// // wx787b3da9e76044d2
		// public static final String APPID = "wx5a658ef5be2df240";
		// // 3aa98bf2ae78504b5b7679f50a629aae
		// public static final String AESKey =
		// "YgCyPnIh7kOrDtq5EWrvaxvCwvIMbcyAFdhsl8bB5km";

		// WeChat 微信扫码
		// 1585927871
//		public static final String MCH_ID = "1387890602";
//		public static final String MCH_KEY = "jOyvHCvrL8EaA6PaqzConcoxJimi2016";
//		public static final String CHARSET = "UTF-8";

		
		// --------------------------现几米相关----------------------------------------
		
		// WeChat 公众号
		public static final String TOKEN = "Concox_Jimi";
		// wx787b3da9e76044d2
		public static final String APPID = "wx787b3da9e76044d2";
		// 3aa98bf2ae78504b5b7679f50a629aae
		public static final String AESKey = "3aa98bf2ae78504b5b7679f50a629aae";

		// WeChat 微信扫码
		// 1585927871
		public static final String MCH_ID = "1585927871";
		public static final String MCH_KEY = "JIMIwulian12344321concox12344321";
		public static final String CHARSET = "UTF-8";
		
		// ========================================================================================================//

		// TODO 统一下单URL
		public static final String URL_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		// TODO 查询订单URL
		public static final String URL_ORDERQUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

		// TODO 关闭订单URL
		public static final String URL_CLOSEORDER = "https://api.mch.weixin.qq.com/pay/closeorder";

		// TODO WeChat Online NOTIFY_URL（测试环境）
		// public static final String NOTIFY_URL =
		// "http://leeandfly.wicp.net/oa-web/wechatpay/bizback";

		// TODO WeChat Online NOTIFY_URL （线上环境）
		public static final String NOTIFY_URL = "http://www.concox400.com/sale-web/wechatpay/bizback";
		
		// 测试环境回调地址，必须使用IP
//		public static final String NOTIFY_URL = "http://113.108.62.204:31120/sale-web/wechatpay/bizback";

		// ========================================================================================================//

		// 几米客户服务
		public static final String PRODUCT = "几米客户服务";

		public static String ACCESS_TOKEN;

		public static String getACCESS_TOKEN() {
			return ACCESS_TOKEN;
		}

		public static void setACCESS_TOKEN(String aCCESS_TOKEN) {
			ACCESS_TOKEN = aCCESS_TOKEN;
		}

	}

	public class WeChatURL {
		/**
		 * 获取ACCESS_TOKEN地址
		 **/
		public static final String GETACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ WeChatInfo.APPID + "&secret=" + WeChatInfo.AESKey;
		/**
		 * 获取jsapi_ticket地址
		 */
		public static final String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

		/**
		 * 发送客服消息接口
		 **/
		public static final String sendmessage = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=#";

	}

	// 指令时间相关
	public class TimeUnit {
		public static final int SENDCOMMANDTIME = 15;// 单位秒
	}

	public class Other {
		/**
		 * 占位符
		 **/
		public static final String PLACEHOLDER = "#";
	}

}
