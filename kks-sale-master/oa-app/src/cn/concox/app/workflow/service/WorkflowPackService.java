package cn.concox.app.workflow.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.SjfjmanageMapper;
import cn.concox.app.basicdata.mapper.SxdwmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.report.service.HwmReportService;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.app.workflow.mapper.WorkflowPackMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.mail.other.CVMailUtil;
import cn.concox.comm.sms.other.SMSConstants;
import cn.concox.comm.sms.other.UCPaasUtils;
import cn.concox.comm.util.Convert;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Sjfjmanage;
import cn.concox.vo.basicdata.Sxdwmanage;
import cn.concox.vo.commvo.TimeTree;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowPack;
import freemarker.template.Template;

@Service("workflowPackService")
@Scope("prototype")
public class WorkflowPackService {
	Logger logger = Logger.getLogger("workflowInfo");
	
	@Resource(name="workflowMapper")
	private  WorkflowMapper<Workflow> workflowMapper;
	
	@Resource(name = "workflowPackMapper")
	private WorkflowPackMapper<WorkflowPack> workflowPackMapper;
	
	@Resource(name="sxdwmanageMapper")
	private  SxdwmanageMapper<Sxdwmanage> sxdwmanageMapper;
	
	@Resource(name="sjfjmanageMapper")
	private  SjfjmanageMapper<Sjfjmanage> sjfjmanageMapper;
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	@Autowired
	private HwmReportService hwmReportService;
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	public List<Workflow> getPackLists(Workflow w) {
		return workflowMapper.getPackList(w);
	}
	
	public Page<Workflow> getPackListPage(Workflow w,
			Integer currentpage, Integer pageSize) {
		Page<Workflow> workflows = new Page<Workflow>();
		workflows.setCurrentPage(currentpage);
		workflows.setSize(pageSize);
		workflows = workflowMapper.getPackListPage(workflows, w);
		return workflows;
	}
	
	public WorkflowPack getInfo(int id) {
		return workflowPackMapper.getT(id);
	}

	/**
	 *  新增装箱(供报价和终检使用)
	 * @param WorkflowPack.wfId        联表ID
	 * @param WorkflowPack.Offer    报价员（维修工程师）
	 * @param WorkflowPack.priceState 报价状态 [-1：已发送 0：待报价 1:已处理]
	 * @param WorkflowPack.createTime  创建时间 
	 * @return
	 *  Code   -1  插入失败 
	 *  Code   0   插入成功
	 *  Code   1   参数错误
	 */
	public Integer doInsert(WorkflowPack wfp){
		try{
			if(null!=wfp&&null!=wfp.getRepairnNmber()){
				workflowPackMapper.insert(wfp);
				return 0;
			}else{
				return 1;
			}
		}catch (Exception e) {
			logger.error("新增装箱信息失败！"+e); 
			return -1 ;
		}
		
	}
	
	/**
	 * @Title: updatePackRemark 
	 * @Description:修改装箱备注
	 * @param w 
	 * @author 20160308
	 * @date 2017年11月15日 下午6:28:35
	 */
	public void updatePackRemark(WorkflowPack w) {
			workflowPackMapper.update(w);
	}
	
	public void updateInfo(WorkflowPack w) {
		try {
			workflowPackMapper.update(w);////更新装箱状态	
			Workflow workflow = workflowMapper.getOneByRepairnNmber(w.getRepairnNmber());
			workflowMapper.updateStateByBatchNo(-1L,w.getRepairnNmber());//更新单据状态
			
			{
				//TODO 短信推送 				
				Sxdwmanage sxdwmanage = sxdwmanageMapper.getT(Convert.getInteger(workflow.getSxdwId().toString()));
				logger.info("【装箱】发送短信：" + "送修单位id：" + sxdwmanage.getCId() 
												+ ",送修单位：" + sxdwmanage.getCusName() 
												+ ",手机号：" + sxdwmanage.getPhone()
												+ ",批次号：" + workflow.getRepairnNmber() 
												+ ",快递公司：" + w.getExpressCompany() 
												+ ",快递单号：" + w.getExpressNO());
				
				if(StringUtil.isRealEmpty(w.getExpressNO())){
					return;//快递单号为空，不发送短信
				}
				
				//发送短信
				String ret = UCPaasUtils.sendSms(SMSConstants.SMS_APPID, SMSConstants.PACK_TEMPLATE_ID, 
												new StringBuilder(sxdwmanage.getCusName())
													.append(",")
													.append(workflow.getRepairnNmber())
													.append(",")
													.append(w.getExpressCompany())
													.append(",")
													.append(w.getExpressNO())
													.toString(), 
													sxdwmanage.getPhone());
				logger.info("装箱短信发送："+ret);
				//发送邮件
				if(!StringUtil.isRealEmpty(sxdwmanage.getEmail()) && sxdwmanage.getEmail().trim().indexOf("@") > 0){
					boolean flag =	CVMailUtil.sendMailForeach(sxdwmanage.getEmail().split(","),
						"几米售后部维修服务推送",
						"尊敬的客户，"+sxdwmanage.getCusName()+"，您批次号为"+workflow.getRepairnNmber()+"的送修设备已经装箱发货，快递公司为"+w.getExpressCompany()+"，快递单号为"+w.getExpressNO()+"，请注意查收和评价。");
					logger.info("【装箱】邮件发送】：" + (flag ? "成功!送修单位id：" + sxdwmanage.getCId() + ",送修单位：" + sxdwmanage.getCusName() + ",手机号：" + sxdwmanage.getPhone() + 
																	",批次号：" + workflow.getRepairnNmber() + ",快递公司：" + w.getExpressCompany() + ",快递单号：" + w.getExpressNO() 
															: "失败"));
				}
				
			}
		} catch (Exception e) {
			logger.info("装箱短信发送失败！"+e);	
		}
	}
	
	/**
	 * 查询改批号的所有设备的装箱状态
	 * @param repairnNmber
	 * @return
	 */
	public List<Workflow> getStateByRepairnNmber(String repairnNmber, String imei, String state){
		List<Workflow> workflows= workflowMapper.getImeiState(repairnNmber, imei, state);
		Integer packCount = workflowMapper.getImeiInPackCount(repairnNmber, imei, state);
		Integer countOfRepairnNmber = workflowMapper.getCountOfRepairnNmber(repairnNmber);
		if(null !=workflows && workflows.size()>0){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");						
			for (Workflow wf : workflows) {
				wf.setCountOfRepairnNmber(countOfRepairnNmber);
				wf.setMachinaInPackCount(packCount);
				if(!StringUtil.isEmpty(wf.getW_sjfjDesc())){
					//TODO 随机附件
					String sjfj_Name = sjfjmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_sjfjDesc()));
					String sjfjIds = workflowService.sortIds(wf.getW_sjfjDesc());
					wf.setW_sjfj(sjfjIds);
					wf.setW_sjfjDesc(sjfj_Name);
				}
				if(wf.getAcceptanceTime() != null && StringUtils.isNotBlank(wf.getAcceptanceTime().toString())){
					Timestamp backTimeState = new Timestamp(hwmReportService.getInvalidTime(wf.getAcceptanceTime()).getTime());
					wf.setBackDate(df.format(backTimeState));
				}
				
				if(null != wf.getTestResult() && !"".equals(wf.getTestResult())){
					StringBuffer result = new StringBuffer();
					if(wf.getTestPerson() != null && wf.getTestTime() != null){
						result.append("———"+wf.getTestPerson()+"    "+wf.getTestTime());
						wf.setTestResult(wf.getTestResult()+result);
					}
					
				}
				
			}
		}
		return workflows;
	}
	
	/**
	 * 修改imei
	 * @author TangYuping
	 * @version 2017年1月5日 下午7:03:04
	 * @param wf
	 */
	public void updateImei(Workflow wf){
		try {
		workflowMapper.updateImei(wf);
		workflowMapper.updateRelatedImei(wf);
		} catch (Exception e) {
			logger.info("操作失败！"+e);	
		}
	}

	public List<Workflow> matchImei(String imei) {
		return workflowMapper.matchImei(imei);
	}
	
	
	public List<TimeTree> getTimeList(){
		List<TimeTree> tList = new ArrayList<TimeTree>();
		String type = "0";
		
		TimeTree packTree = new TimeTree();
		packTree.setId("1");
		packTree.setText("待装箱");
	
		List<TimeTree> packTreeList = new ArrayList<TimeTree>();
		//未完成
		List<String> packList = workflowPackMapper.getPackTimeList(type);
		if(null != packList && packList.size() > 0){
			for (String s : packList) {
				TimeTree t = new TimeTree();
				t.setId(s);
				t.setText(s);
				List<String> packList1 = workflowPackMapper.getPackTimeByTreetime(type, s);
				List<TimeTree> tList1 = new ArrayList<TimeTree>();
				if(null != packList1 && packList1.size() > 0){
					for (String s1 : packList1) {
						TimeTree t1 = new TimeTree();
						t1.setId(s1);
						t1.setText(s1);
						tList1.add(t1);
					}
					t.setChildren(tList1);
					packTreeList.add(t);
				}
			}
		}
		packTree.setChildren(packTreeList);
		tList.add(packTree);
		
		type = "1";
		TimeTree packedTree = new TimeTree();
		packedTree.setId("2");
		packedTree.setText("已完成");
	
		List<TimeTree> packedTreeList = new ArrayList<TimeTree>();
		//已完成
		List<String> packedList = workflowPackMapper.getPackedTimeList(type);
		if(null != packedList && packedList.size() > 0){
			for (String s : packedList) {
				TimeTree t = new TimeTree();
				t.setId(s);
				t.setText(s);
				List<String> packedList1 = workflowPackMapper.getPackedTimeByTreetime(type, s);
				List<TimeTree> tList1 = new ArrayList<TimeTree>();
				if(null != packedList1 && packedList1.size() > 0){
					for (String s1 : packedList1) {
						TimeTree t1 = new TimeTree();
						t1.setId(s1);
						t1.setText(s1);
						tList1.add(t1);
					}
					t.setChildren(tList1);
					packedTreeList.add(t);
				}
			}
		}
		packedTree.setChildren(packedTreeList);
		tList.add(packedTree);
		
		return tList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(Workflow wf, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Workflow> wfList= workflowMapper.getPackListPage(wf);
		
		String[] fieldNames = new String[] {"快递单号", "收件人姓名", "收件人手机", "座机号", "收件人地址"};
		Map map = new HashMap();
		map.put("size", wfList.size() + 1);
		map.put("peList",wfList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName = new StringBuilder("装箱管理列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.WORKFLOW).append("workPack.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,template, map);
	}
}
