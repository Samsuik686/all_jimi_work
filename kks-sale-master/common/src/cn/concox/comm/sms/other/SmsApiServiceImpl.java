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

import org.springframework.stereotype.Service;

import cn.concox.comm.util.StringUtil;



/**
 * @FileName SmsApiServiceImpl.java
 * @Description:
 *
 * @Date 2017年6月28日 上午11:34:17
 * @author TangYuping
 * @version 1.0
 */
@Service
public class SmsApiServiceImpl implements ISmsApiService {

	@Override
	public String sendSms(String appId, String templateId, String param, String to) {
		if (!StringUtil.isRealEmpty(appId) && !StringUtil.isRealEmpty(templateId) && !StringUtil.isRealEmpty(param)
				&& !StringUtil.isRealEmpty(to)) {
			String result = UCPaasUtils.sendSms(appId, templateId, param, to);
			return result;
		} else {
			return SMSConstants.SMS_FAIL_PARAM;
		}
	}

	public static void main(String[] args) {
		SmsApiServiceImpl sms = new SmsApiServiceImpl();
		String result = sms.sendSms("4fb8da91c70e44d1bc3fb6bdbae03fd0", "84856", "个人应用,4256478,60", "15278015466");
		System.out.println(result);
	}


}
