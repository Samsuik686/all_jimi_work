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
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.concox.app.aipay.service.CustomerClientService;
import cn.concox.app.workflow.service.WorkflowPriceService;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.aipay.CustomerClient;
import cn.concox.vo.aipay.PayRecord;
import cn.concox.vo.workflow.Workflow;
import cn.concox.wechat.api.WeChatPayService;
import cn.concox.wechat.uti.WeChatGlobals.WeChatInfo;
@Controller
@RequestMapping(value = "/wechatdev")
public class WeChatDeviceController extends BaseController{

	
	@Resource(name="workflowPriceService")
	private WorkflowPriceService workflowPriceService;
	
	@Resource(name="customerClientService")
	private CustomerClientService customerClientService;
	
	/**
	 * 跳转维修控制中心
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/toDevCon", method = { RequestMethod.GET, RequestMethod.POST })
	public void toDevList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.TOWeChatJSP(request, response); 
	}
	/**
	 * WeChat 登录验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doWeChatLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public void doWeChatLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		APIContent apiContent = new APIContent();
		try{
			String cnumber = request.getParameter("cnumber");
			String cphone  = request.getParameter("cphone");
			
			if(!StringUtil.isRealEmpty(cnumber) && !StringUtil.isRealEmpty(cphone)){
				CustomerClient client = new CustomerClient();
				client.setPhone(cphone);
				client.setRepairnNmber(cnumber);
				if(customerClientService.checkClientLogin(client)){
					request.getSession().setAttribute(GlobalCons.BATCHNUMBER, cnumber);
					request.getSession().setAttribute(GlobalCons.PHONENUMBER, cphone);
					request.getSession().setAttribute(GlobalCons.CHECKFALG, 0);
					apiContent.setCode(Globals.OPERA_SUCCESS_CODE);
					returnJson(response, request, apiContent);
				}else{
					apiContent.setCode(Globals.OPERA_FAIL_CODE);
					request.getSession().setAttribute(GlobalCons.CHECKFALG, 1);
					returnJson(response, request, apiContent);
				}
			}else{
				request.getSession().setAttribute(GlobalCons.CHECKFALG, 1);
				apiContent.setCode(Globals.OPERA_FAIL_CODE);
				returnJson(response, request, apiContent);
			}
		}catch(Exception e){
			logger.error("登录验证错误:",e);
			 request.getSession().setAttribute(GlobalCons.CHECKFALG, 2);
			 apiContent.setCode(Globals.OPERA_FAIL_CODE);
			 returnJson(response, request, apiContent);
		}
	}
	
	/**
	 * WeChat 登录验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doWeChatPwdLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public void doWeChatPwdLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		APIContent apiContent = new APIContent();
		try{
			String loginPhone = request.getParameter("loginPhone");
			String loginPwd  = request.getParameter("loginPwd");
			
			if(!StringUtil.isRealEmpty(loginPhone) && !StringUtil.isRealEmpty(loginPwd)){
				CustomerClient client = new CustomerClient();
				client.setPhone(loginPhone);
				client.setLoginPwd(loginPwd);
				if(customerClientService.checkClientLogin(client)){
					request.getSession().setAttribute(GlobalCons.PHONENUMBER, loginPhone);
					request.getSession().setAttribute(GlobalCons.CHECKFALG, 0);
					apiContent.setCode(Globals.OPERA_SUCCESS_CODE);
					returnJson(response, request, apiContent);
				}else{
					apiContent.setCode(Globals.OPERA_FAIL_CODE);
					request.getSession().setAttribute(GlobalCons.CHECKFALG, 1);
					returnJson(response, request, apiContent);
				}
			}else{
				request.getSession().setAttribute(GlobalCons.CHECKFALG, 1);
				apiContent.setCode(Globals.OPERA_FAIL_CODE);
				returnJson(response, request, apiContent);
			}
		}catch(Exception e){
			logger.error("登录验证错误:",e);
			 request.getSession().setAttribute(GlobalCons.CHECKFALG, 2);
			 apiContent.setCode(Globals.OPERA_FAIL_CODE);
			 returnJson(response, request, apiContent);
		}
	}
	
	/**
	 * 加载送修数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/doGetOrders", method = { RequestMethod.GET, RequestMethod.POST })
	public void doGetOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
		APIContent apiContent = new APIContent();
		String cnumber =(String) request.getSession().getAttribute(GlobalCons.BATCHNUMBER); 
		CustomerClient client = new  CustomerClient();
		client.setRepairnNmber(cnumber);
		List<CustomerClient> clients = customerClientService.getWeChatAllDatas(client);
		apiContent.setData(clients);
		apiContent.setCode(Globals.OPERA_SUCCESS_CODE);
		returnJson(response, request, apiContent);
	}
	
	/**
	 * @Title: doGetRepairNumber 
	 * @Description:加载登录手机号的所有批次
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @author HuangGangQiang
	 * @date 2017年9月12日 上午10:54:19
	 */
	@RequestMapping(value = "/doGetRepairNumber", method = { RequestMethod.GET, RequestMethod.POST })
	public void doGetRepairNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
		APIContent apiContent = new APIContent();
		String loginPhone =(String) request.getSession().getAttribute(GlobalCons.PHONENUMBER); 
		CustomerClient client = new  CustomerClient();
		client.setPhone(loginPhone);
		List<Workflow> wfList = customerClientService.getRepairNumberList(client);
		apiContent.setData(wfList);
		apiContent.setCode(Globals.OPERA_SUCCESS_CODE);
		returnJson(response, request, apiContent);
	}
	
	/**
	 * @Title: showRepairNumberInfo 
	 * @Description:选择批次的详情
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @author HuangGangQiang
	 * @date 2017年9月12日 下午3:59:55
	 */
	@RequestMapping(value = "/showRepairNumberInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public void showRepairNumberInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String repairNumber = request.getParameter("repairNumber");
		APIContent apiContent = new APIContent();
		if(!StringUtil.isRealEmpty(repairNumber)){
			request.getSession().setAttribute(GlobalCons.BATCHNUMBER, repairNumber);
			apiContent.setCode(Globals.OPERA_SUCCESS_CODE);
		}else{
			apiContent.setCode(Globals.OPERA_FAIL_CODE);
		}
		returnJson(response, request, apiContent);
	}
	
	/**
	 * 获取总金额
	 * @param request 
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getTotalPrice", method = { RequestMethod.GET, RequestMethod.POST })
	public void getTotalPrice(HttpServletRequest request, HttpServletResponse response){
		try {
			APIContent apiContent = new APIContent();
			WeChatPayService ws = new WeChatPayService();
			CustomerClient client = new CustomerClient();
			String cnumber =(String) request.getSession().getAttribute(GlobalCons.BATCHNUMBER); 
			client.setRepairnNmber(cnumber);
			
			if(!workflowPriceService.isAllPay(client.getRepairnNmber())){
				apiContent.setCode(Globals.OPERA_FAIL_CODE);
				apiContent.setMsg("该批次还有设备未报价，不允许付款");
			}else{
				//判断是否已支付
				boolean payState = customerClientService.getPayState(cnumber);          
				if(payState){
					CustomerClient sum = new CustomerClient();
					sum.setTotalPrice(new BigDecimal(0.00));
					apiContent.setMsg("费用已支付");
					apiContent.setCode(Globals.OPERA_FAIL_CODE);
				}else{
					//查询报价的费用是否和维修表、报价表的最新数据一致
					BigDecimal pricePay = workflowPriceService.bathSumRepairPriceByPrice(cnumber);
					
					BigDecimal repairPay = workflowPriceService.bathSumRepairPriceByRepair(cnumber);
					
					CustomerClient sum = customerClientService.getSum(client); 
					
					if(null == pricePay){
						pricePay = BigDecimal.ZERO;
					}
					if(null == repairPay){
						repairPay = BigDecimal.ZERO;
					}
					
					//总费用 = 配件配 + 税费 + 维修费 + 运费
					BigDecimal sumPay = sum.getBatchPjCosts().add(sum.getRatePrice()).add(sum.getSumRepair()).add(sum.getLogCost());
					if(pricePay.compareTo(repairPay) != 0 || pricePay.compareTo(sum.getSumRepair()) != 0 
							|| repairPay.compareTo(sum.getSumRepair()) != 0 || sumPay.compareTo(sum.getTotalPrice()) != 0){
						apiContent.setCode(Globals.OPERA_FAIL_CODE);
						apiContent.setMsg("报价费用有误，请联系售后服务确认");
					}else{
						String QRpath = null;
						if(null !=sum){
							try {
								PayRecord record = customerClientService.createPayRecord(cnumber,sum.getTotalPrice(), WeChatInfo.PRODUCT, "微信支付");
								QRpath = ws.getQrcodeUrl(record.getOutTradeNo(),record.getName(), record.getAmount(), "172.16.0.189",null);
								apiContent.setCode(Globals.OPERA_SUCCESS_CODE);
							} catch (Exception e) {
								apiContent.setCode(Globals.OPERA_FAIL_CODE);
							}
						}
						apiContent.setMsg(QRpath);
					}
					apiContent.setData(sum);
				}
			}
			returnJson(response, request, apiContent);
		} catch (Exception e) {
			logger.error("获取总金额错误:",e);
		}
	}
}
