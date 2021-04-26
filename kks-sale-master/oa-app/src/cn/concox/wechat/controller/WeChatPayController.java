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
package cn.concox.wechat.controller;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.concox.app.aipay.service.CustomerClientService;
import cn.concox.app.workflow.service.WorkflowPriceService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.aipay.PayRecord;
import cn.concox.wechat.api.WeChatPayService;
import cn.concox.wechat.uti.RequestUtil;
import cn.concox.wechat.uti.WeChatGlobals;
/**
 * WeChat 短信支付回调控制
 * @author Li.Shangzhi
 * @date
 */
@Controller
@RequestMapping(value = "/wechatpay")
public class WeChatPayController extends BaseController{

	@Resource(name="workflowPriceService")
	private WorkflowPriceService workflowPriceService;
	
	@Resource(name="customerClientService")
	private CustomerClientService customerClientService;

	/**
	 * 微信回调支付
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/bizback", method = { RequestMethod.GET, RequestMethod.POST })
	public void doBizBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> reqMap = RequestUtil.parseXml(request, WeChatGlobals.WeChatInfo.TOKEN, WeChatGlobals.WeChatInfo.APPID, WeChatGlobals.WeChatInfo.AESKey);

		String return_code = (String) reqMap.get("return_code");

		if(!StringUtil.isRealEmpty(return_code)&& "SUCCESS".equals(return_code)){
			String out_trade_no = (String) reqMap.get("out_trade_no");
			PayRecord payRecord = customerClientService.dealResult(out_trade_no, null);
			//TODO 支付回回执
			workflowPriceService.UpdateAlipayBackCode(payRecord.getRepairnNmber(), Globals.WECHATPAY);
			try {
				WeChatPayService.WeChatServiceAPI();
			} catch (Exception e) {
				logger.error("WeChat 订单回执失败！",e);
			}
		}
	}

}
