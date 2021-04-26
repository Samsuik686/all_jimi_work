package cn.concox.app.workflow.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.SxdwmanageMapper;
import cn.concox.app.workflow.mapper.WorkflowTotalpayMapper;
import cn.concox.comm.Globals;
import cn.concox.comm.mail.other.CVMailUtil;
import cn.concox.comm.sms.other.SMSConstants;
import cn.concox.comm.sms.other.UCPaasUtils;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Sxdwmanage;
import cn.concox.vo.workflow.WorkflowTotalpay;

@Service("workflowTotalpayService")
@Scope("prototype")
public class WorkflowTotalpayService {

	Logger logger = Logger.getLogger("workflowInfo");
	
	@Resource(name = "sxdwmanageMapper")
	private SxdwmanageMapper<Sxdwmanage> sxdwmanageMapper;

	@Resource(name = "workflowTotalpayMapper")
	private WorkflowTotalpayMapper<WorkflowTotalpay> workflowTotalpayMapper;

	@Resource(name = "workflowPriceService")
	private WorkflowPriceService workflowPriceService;

	public WorkflowTotalpay getInfo(WorkflowTotalpay totalpay) {
		return workflowTotalpayMapper.getT(totalpay.getId());
	}

	public void update(WorkflowTotalpay totalpay) {
		workflowTotalpayMapper.update(totalpay);
	}

	public List<WorkflowTotalpay> getPriceList(WorkflowTotalpay totalpay) {
		return workflowTotalpayMapper.queryList(totalpay);
	}

	/**
	 * 根据批次查询
	 * @param repairNumber
	 * @return
	 */
	public WorkflowTotalpay getByRepairNumber(String repairNumber){
		return workflowTotalpayMapper.getByRepairNumber(repairNumber);
	}
	
	/**
	 * API 新增批次报价总记录
	 * 
	 * @param WorkflowTotalpay
	 *            .repairNumber 批次号
	 * @param WorkflowTotalpay
	 *            .remAccount 账号
	 * @param WorkflowTotalpay
	 *            .payTime 付款时间
	 * @param WorkflowTotalpay
	 *            .logCost 物流费
	 * @param WorkflowTotalpay
	 *            .repairMoney 维修总费用
	 * @param WorkflowTotalpay
	 *            .totalMoney 报价总费用（维修总费用+物流费）
	 * @param WorkflowTotalpay
	 *            .createTime 创建时间 ....
	 * @return Code -1 插入失败 Code 0 插入成功 Code 1 参数错误
	 */
	public Integer doInsert(WorkflowTotalpay totalpay) {
		try {
			if (null != totalpay && !StringUtil.isEmpty(totalpay.getRepairNumber()) && null != totalpay.getTotalMoney()) {
				if (totalpay.getIsPay() == 0) {
					//人工确认付款
					if (null == totalpay.getPayTime()) {
						totalpay.setPayTime(new Timestamp(System.currentTimeMillis()));
					}
					totalpay.setRemAccount(Globals.PERSONPAY);
					if (!workflowTotalpayMapper.exists(totalpay.getRepairNumber())) {
						totalpay.setCreateTime(new Timestamp(System.currentTimeMillis()));
						workflowTotalpayMapper.insert(totalpay);
					} else {
						totalpay.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						workflowTotalpayMapper.update(totalpay);
					}
					workflowTotalpayMapper.updateByRepairnNmber(totalpay.getRepairNumber());
					workflowPriceService.updateStateByRepairnNmber(totalpay.getRepairNumber());
					
					workflowPriceService.updatePricePj(totalpay.getRepairNumber(),totalpay);
				} else {
					totalpay.setRemAccount("");
					if (!workflowTotalpayMapper.exists(totalpay.getRepairNumber())) {
						totalpay.setCreateTime(new Timestamp(System.currentTimeMillis()));
						workflowTotalpayMapper.insert(totalpay);
					} else {
						totalpay.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						workflowTotalpayMapper.update(totalpay);
					}
					workflowPriceService.updateStateByRepairnNmberIsNotPay(totalpay.getRepairNumber());
					// 非人工确认发送短信
					if (totalpay.getIsAutoPay()) {
						// TODO SMS 短信发送
						{
							Sxdwmanage sxdwmanage = sxdwmanageMapper.getByRepairNumber(totalpay.getRepairNumber());
							logger.info("【报价】发送短信：" + "送修单位id：" + sxdwmanage.getCId() 
															+ ",送修单位：" + sxdwmanage.getCusName() 
															+ ",手机号：" + sxdwmanage.getPhone() 
															+ ",批次号：" + totalpay.getRepairNumber());
							
							//发送短信
							String ret = UCPaasUtils.sendSms(SMSConstants.SMS_APPID, SMSConstants.PRICE_TEMPLATE_ID, 
															new StringBuilder(sxdwmanage.getCusName())
																.append(",")
																.append(totalpay.getRepairNumber())
																.toString(), 
																sxdwmanage.getPhone());
							logger.info("报价短信发送："+ret);
							//发送邮件
							if(!StringUtil.isRealEmpty(sxdwmanage.getEmail()) && sxdwmanage.getEmail().trim().indexOf("@") > 0){
								boolean flag =	CVMailUtil.sendMailForeach(sxdwmanage.getEmail().split(","),
												"几米售后部维修服务推送",
												"尊敬的客户，"+sxdwmanage.getCusName()+"，您批次号为"+totalpay.getRepairNumber()+"的送修设备正等待付款。");
								logger.info("【报价】邮件发送】：" + (flag ? "成功!送修单位id：" + sxdwmanage.getCId() + ",送修单位：" + sxdwmanage.getCusName() + ",手机号：" + sxdwmanage.getPhone() + ",批次号：" + totalpay.getRepairNumber()
																		: "失败"));
							}
							
						}
					}
				}
				return 0; // TODO 插入成功
			} else {
				return 1; // TODO 参数错误
			}
		} catch (Exception e) {
			logger.error("批次报价错误：", e);
			logger.info("API -- 新增批次报价总记录    " + e.toString());
			return -1; // TODO 插入失败
		}
	}

	public List<WorkflowTotalpay> getTotalpayDate(String repairNumber){
		return workflowTotalpayMapper.selectTotalpayByRepairNumber(repairNumber);
	}
}
