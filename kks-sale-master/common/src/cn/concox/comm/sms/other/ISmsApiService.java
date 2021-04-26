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
 * @FileName ISmsApiService.java
 * @Description: 
 *
 * @Date 2017年6月28日 上午11:34:17
 * @author TangYuping
 * @version 1.0
 */
public interface ISmsApiService {
	
	/**
	 * @Title: sendSms 
	 * @Description: 短信发送
	 * @param appid
	 * @param templateId 短信模板ID
	 * @param param 多个参数用 , 隔开
	 * @param to 验证码发送到的手机号
	 * @return 
	 * @author TangYuping
	 * @date 2017年6月28日 下午2:06:59
	 */
	public String sendSms (String appId, String templateId, String param, String to);
}
