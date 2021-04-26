/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2017.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording, 
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 * 
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2017年6月28日    TangYuping         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.comm.sms.other;

/**
 * @FileName SMSConstants.java
 * @Description: 
 *
 * @Date 2017年6月28日 下午2:16:40
 * @author TangYuping
 * @version 1.0
 */
public class SMSConstants {
//	public static final String SMS_APP_ID = "4fb8da91c70e44d1bc3fb6bdbae03fd0";
//	
//	public static final String SMS_APP_NAME = "个人机产品";
//	
//	public static final String SMS_TEMPLATE_ID = "84856"; // 发送短信的模板ID  去哪的模板，后期确定个人APP模板后再改
	
	public static final String SMS_SUCCESS = "000000";  //短信发送成功
	
	public static final String SMS_FAIL = "101100";		//短信发送失败
	
	public static final String SMS_FAIL_PARAM = "100008";  //请求参数错误
	
	public static final String SMS_PARAM_INVALID_FORMAT = "105112";  //请求参数[param]格式错误
	
	public static final String VOICE_FAIL_PARAM = "102100"; //请求参数错误
	
	
	public static final String SMS_APPID = "c3e6e1da63784b8cb08bfdf0f3161768";//售后app应用Id
	
	public static final String ACCEPT_TEMPLATE_ID = "kks-sale.handling";//受理短信模板ID
	
	public static final String PRICE_TEMPLATE_ID = "kks-sale.waiting-payment";//报价短信模板ID
	
	public static final String PACK_TEMPLATE_ID = "kks-sale.consignment";//装箱短信模板ID
	
	public static final String VALIDATE_TEMPLATE_ID = "kks-sale.verification";//验证码短信模板ID
	
	public static final String PASSWORD_TEMPLATE_ID = "kks-sale.get-password";//密码短信模板ID
}
