package cn.concox.app.workflow.controller;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.vo.basicdata.Sxdwmanage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.app.common.util.PrintOrder;
import cn.concox.app.report.service.XspjcostsReportService;
import cn.concox.app.workflow.service.WorkflowPackService;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.JavaNetURLRESTFulClient;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Saledata;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.commvo.TimeTree;
import cn.concox.vo.report.XspjcostsReport;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowPack;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class WorkflowPackController extends BaseController {
	private static Logger logger = Logger.getLogger("workflowInfo");

	@Resource(name = "workflowPackService")
	private WorkflowPackService workflowPackService;
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	@Resource(name = "xspjcostsReportService")
	public XspjcostsReportService xspjcostsReportService;
	
	@Value("${ams_sales_url}")
    private String AMS_SALES_URL;
	
	/**
	 * 获取所有装箱数据
	 * 
	 * @param w
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/packList")
	@ResponseBody
	public APIContent getPackList(@ModelAttribute Workflow w,HttpServletRequest req) {
		try {
			List<Workflow> wfList = workflowPackService.getPackLists(w);
			if(null!=wfList&&wfList.size()>0){
				return super.putAPIData(wfList);
			}else{
				return super.putAPIData(null);
			}
		} catch (Exception e) {
			logger.error("获取所有装箱数据失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取所有装箱数据
	 * 
	 * @param w
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/packListPage")
	@ResponseBody
	public APIContent packListPage(@ModelAttribute Workflow w, @ModelAttribute CommonParam comp,HttpServletRequest req) {
		try {
			Page<Workflow> wfListPage = workflowPackService.getPackListPage(w,comp.getCurrentpage(),comp.getPageSize());
			req.getSession().setAttribute("totalValue", wfListPage.getTotal());
			List<Workflow> wl=wfListPage.getResult();
			return super.putAPIData(wl);
		} catch (Exception e) {
			logger.error("获取所有装箱数据失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 把装箱数据放入公共数据里
	 * @param wfp
	 * @param wf
	 */
	public void setProperties(WorkflowPack wfp,Workflow wf){
		if(null!=wfp){
			wf.setW_packId(wfp.getId());
			wf.setW_expressCompany(wfp.getExpressCompany());
			wf.setW_expressNO(wfp.getExpressNO());
			wf.setW_packingNO(wfp.getPackingNO());
			wf.setW_shipper(wfp.getShipper());
			wf.setW_packer(wfp.getPacker());
			wf.setW_packState(wfp.getPackState());
			wf.setW_packTime(wfp.getPackTime());
			wf.setW_packRemark(wfp.getPackRemark());
		}
	}
	
	/**
	 * 获取装箱详情
	 * 
	 * @param wfp
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/getInfo")
	@ResponseBody
	public APIContent getInfo(@ModelAttribute WorkflowPack wfp, HttpServletRequest req) {
		try {
			String wfId=req.getParameter("id");
			String packId=req.getParameter("packId");
			WorkflowPack wfp1 = workflowPackService.getInfo(new Long(packId).intValue());
			Workflow wf=workflowService.getInfo(new Long(wfId).intValue(), true);
			wf.setW_packer(super.getSessionUserName(req));
			if(wfp1.getPackingNO()==null){
				//装箱单号自动生成，生成规则 日期+批次号
				String packingNO = DateTimeUtil.formatString(DateTimeUtil.now(),"yyyyMMdd")+wf.getRepairnNmber();
				wfp1.setPackingNO(packingNO);
			}
			setProperties(wfp1,wf);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, wf);
		} catch (Exception e) {
			logger.error("获取装箱详情失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 修改装箱
	 * 
	 * @param wfp
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/updateInfo")
	@ResponseBody
	public APIContent updatePackInfo(@ModelAttribute WorkflowPack wfp, HttpServletRequest req) {
		try {
			String packDate=req.getParameter("packDate");//装箱时间
			String repairnNmber=req.getParameter("repairnNmber");
			if(!StringUtils.isBlank(packDate)){
				wfp.setPackTime(Timestamp.valueOf(packDate));
			}
			if(!StringUtils.isBlank(repairnNmber)){
				wfp.setRepairnNmber(repairnNmber);;
			}
			wfp.setPacker(super.getSessionUserName(req));
			wfp.setPackState(1L);//已处理
			workflowPackService.updateInfo(wfp);
			List<XspjcostsReport> xList = xspjcostsReportService.getByRepairNumber(repairnNmber);
			if(null!=xList && xList.size()>0){
				for (XspjcostsReport x : xList) {
					x.setExpressNO(wfp.getExpressNO());
					x.setExpressType(wfp.getExpressCompany());
					xspjcostsReportService.updateXspjcosts(x);
				}
			}
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "修改装箱"+ Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("修改装箱失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 *保存装箱备注
	 * 
	 * @param wfp
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/updatePackRemark")
	@ResponseBody
	public APIContent updatePackRemark(@ModelAttribute WorkflowPack wfp, HttpServletRequest req) {
		try {
			workflowPackService.updatePackRemark(wfp);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "修改装箱备注"+ Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("修改装箱备注失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 查看送修批次的设备装箱状态
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/imeiSateList")
	@ResponseBody
	public APIContent getimeiSateList(HttpServletRequest req) {
		try {
			String repairnNmber=req.getParameter("repairnNmber");
			String imei = req.getParameter("imei");
			String state = req.getParameter("state");

			List<Workflow> wfList = workflowPackService.getStateByRepairnNmber(repairnNmber, imei, state);		
			return super.putAPIData(wfList);
		} catch (Exception e) {
			logger.error("查看详情失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	@RequestMapping("pack/checkPage")
	@ResponseBody
	public APIContent checkPage(HttpServletRequest req) {
		try {
			String repairnNmber=req.getParameter("repairnNmber");
			List<Workflow> wfList = workflowPackService.getStateByRepairnNmber(repairnNmber,"","");
			for(Workflow wf:wfList){
				if(wf.getSendPackTime() == null){
					return super.operaStatus(Globals.OPERA_FAIL_CODE,"设备未全部发送到装箱，不能进行装箱操作");
				}
				if(null !=wf.getCountOfRepairnNmber() && null!=wf.getMachinaInPackCount() && !wf.getCountOfRepairnNmber().equals(wf.getMachinaInPackCount())){
					return super.operaStatus(Globals.OPERA_FAIL_CODE,"设备未全部扫描确认发送到装箱，不能进行装箱操作");
				}
				
				if(null != wf.getW_FinispassFalg() && wf.getW_FinispassFalg() != -1 && wf.getState() == 7){//终检未发送到装箱，状态是已终检，待装箱
					return super.operaStatus(Globals.OPERA_FAIL_CODE,"该批次下有设备还在终检工站，不能进行装箱操作");
				}else if(null != wf.getW_repairStateFalg() && wf.getW_repairStateFalg() != -1 && wf.getState() == 8){//维修放弃报价未发送到装箱，状态是放弃报价，待装箱
					return super.operaStatus(Globals.OPERA_FAIL_CODE,"该批次下有设备还在维修工站，不能进行装箱操作");
				}else if( wf.getState() != 7 && -1 != wf.getState() && wf.getState() != 8){
					return super.operaStatus(Globals.OPERA_FAIL_CODE,"该批次下存在不符合装箱条件，不能进行装箱操作");
				}
			}
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("查看详情失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	} 
	
	/**
	 * 打印快递单
	 * @param wf
	 * @return
	 */
	@RequestMapping("pack/deliveryprint")
	@ResponseBody
	public APIContent deliveryPrint(HttpServletRequest req) {
		try {
			String wfId=req.getParameter("id");
			String expressType=req.getParameter("expressType");
			Workflow wf=workflowService.getInfo(new Long(wfId).intValue(), true);
			if("2".equals(expressType)){
				String[] wfp = new String[] { "钱玲芳", "深圳市几米物联有限公司", "广东省深圳市宝安区67区留仙一路高新奇工业园C栋4楼","13480175400",wf.getW_linkman(),wf.getW_cusName(),wf.getW_address(),wf.getW_phone() };
				int[][] position = new int[][] { { 65, 95 }, { 55, 110 }, { 20, 170 },{ 70,190 },{330,95},{ 320,110 },{ 285,170 },{ 330,190} };
				PrintOrder.printReport(wfp,position);//百世汇通
			}else{
				String[] wfp = new String[] { "深圳市几米物联有限公司", "钱玲芳", "广东省深圳市宝安区67区留仙一路高新奇工业园C栋4楼","13480175400",wf.getW_cusName(),wf.getW_linkman(),wf.getW_address(),wf.getW_phone() };
				int[][] position = new int[][] { { 102, 110 }, { 255, 110 }, { 50, 130 },{ 75,150 },{ 102,170 },{ 255,170 },{ 35,210 },{ 75,230} };
				PrintOrder.printReport(wfp,position);//顺丰速运
			}		
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "打印快递单"+ Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("打印快递单失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	/**
	 * 首页设备查询
	 * 
	 * @param comp
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/deviceListPage")
	@ResponseBody
	public APIContent deviceListPage(@ModelAttribute CommonParam comp, @ModelAttribute Workflow workflow, HttpServletRequest req) {
		try {
			String crucial=req.getParameter("crucial");
			String fashion=req.getParameter("fashion");
			Page<Workflow> wfListPage = workflowService.getDeviceListPage(crucial,fashion, workflow, comp.getCurrentpage(),comp.getPageSize());
			req.getSession().setAttribute("totalValue", wfListPage.getTotal());
			return super.putAPIData(wfListPage.getResult());
		} catch (Exception e) {
			logger.error("获取所有设备数据失败:" , e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 根据查询条件查询出workflow信息
	 * 然后根据imie和送修批号修改所有查询出的workflow的客户信息。
	 * @param workflow 部分查询条件（开始时间和结束时间）
	 * @param sxdw 修改后的客户信息
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/modifyCustomerByQuery")
	@ResponseBody
	public APIContent modifyCustomerByQuery(@ModelAttribute Workflow workflow, @ModelAttribute Sxdwmanage sxdw,HttpServletRequest req){
		try {
			//获取查询方式
			String crucial=req.getParameter("crucial");
			//获取关键字
			String fashion=req.getParameter("fashion");
			//每次最多修改500条
			CommonParam comp = new CommonParam();
			comp.setCurrentpage(0);
			comp.setPageSize(501);
			//根据查询条件查询出需要修改的数据
			Page<Workflow> wfListPage = workflowService.getDeviceListPage(crucial,fashion, workflow, comp .getCurrentpage(),comp.getPageSize());
			List<Workflow> workflows = wfListPage.getResult();

			if(workflows == null || workflows.size() == 0){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "请选择要修改的数据");
			}
			if(workflows.size() >= 500){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "一次最多修复500条数据");
			}
			for(Workflow w : workflows){
				//根据imei和维修批号修改客户
				workflowService.updateCustomer(w,sxdw);
			}
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改客户信息"+Globals.OPERA_SUCCESS_CODE_DESC);
		}catch (Exception e){
			logger.error("修改客户信息错误:" , e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 管理员查询
	 * @param comp
	 * @param req
	 * @return
	 */
	@RequestMapping("pack/directiveListPage")
	@ResponseBody
	public APIContent directiveListPage(@ModelAttribute CommonParam comp,HttpServletRequest req) {
		try {
			String operator=req.getParameter("operator");
			String workstation=req.getParameter("workstation");
			String startTime=req.getParameter("startTime");
			String endTime=req.getParameter("endTime");
			Page<Workflow> wfListPage = workflowService.directiveListPage(operator,workstation,startTime,endTime,comp.getCurrentpage(),comp.getPageSize());
			req.getSession().setAttribute("totalValue", wfListPage.getTotal());
			return super.putAPIData(wfListPage.getResult());
		} catch (Exception e) {
			logger.error("获取所有设备数据失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 
	 * @author TangYuping
	 * @version 2017年1月5日 下午6:59:38
	 * @param wf
	 * @return
	 */
	@RequestMapping("pack/updateImei")
	@ResponseBody
	public APIContent updateImei(@ModelAttribute Workflow wf){
		if(wf.getImei() != null && StringUtils.isNotBlank(wf.getImei())){
			workflowPackService.updateImei(wf);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改IMEI"+Globals.OPERA_SUCCESS_CODE_DESC);
		}else{
			return super.operaStatus(Globals.OPERA_FAIL_CODE,"没有找到IMEI数据");
		}
		
	}
	
	/**
	 * 首页导出所有字段
	 * @param wf
	 * @param req
	 * @param resp
	 */
	@RequestMapping("pack/ExportDatas")
	@ResponseBody
	public void exportDatas(@ModelAttribute Workflow wf, HttpServletRequest req, HttpServletResponse resp){
		try {
			workflowService.ExportDatas(wf, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出首页数据失败:" + e.toString());
		}
	}
	
	@RequestMapping("pack/matchImei")
	@ResponseBody
	public APIContent matchImei(HttpServletRequest req) {
		try {
			String imei = req.getParameter("imei");
			List<Workflow> wfList = workflowPackService.matchImei(imei);		
			return super.putAPIData(wfList);
		} catch (Exception e) {
			logger.error("查看详情失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("pack/getTimeList")
	@ResponseBody
	public APIContent getTimeList(HttpServletRequest req ){
		logger.info("开始获取列表信息");
		try{
			List<TimeTree> tList = new ArrayList<TimeTree>();
				tList = workflowPackService.getTimeList();
				return super.putAPIData(tList);
		}catch(Exception e){
			logger.info("获取列表信息失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		}
	}
	
	/**
	 * @Title: ExportDatas 
	 * @Description:导出装箱
	 * @param wf
	 * @param request
	 * @param response 
	 * @author HuangGangQiang
	 * @date 2017年9月6日 上午10:39:54
	 */
	@RequestMapping("pack/ExportPackDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Workflow wf,HttpServletRequest request,HttpServletResponse response){
		try{
			workflowPackService.ExportDatas(wf,request,response);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出装箱数据失败:"+e.toString());
		}
	}
	
	/**
     * 修复没有出货日期
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("pack/fixOutDateNull")
    @ResponseBody
    public APIContent fixOutDateNull(@ModelAttribute Workflow workflow, HttpServletRequest req) {
        try {
        	String crucial=req.getParameter("crucial");
			String fashion=req.getParameter("fashion");
			CommonParam comp = new CommonParam();
			comp.setCurrentpage(0);
			comp.setPageSize(501);
			Page<Workflow> wfListPage = workflowService.getDeviceListPage(crucial,fashion, workflow, comp .getCurrentpage(),comp.getPageSize());
			List<Workflow> imeis = wfListPage.getResult();
			
        	if(imeis == null || imeis.size() == 0){
        		return super.operaStatus(Globals.OPERA_FAIL_CODE, "请选择要修复的数据");
        	}
        	if(imeis.size() >= 500){
        		return super.operaStatus(Globals.OPERA_FAIL_CODE, "一次最多修复500条数据");
        	}
        	
        	for(Workflow imei : imeis){
        		String json = JavaNetURLRESTFulClient.restSale(imei.getImei().trim(), "", AMS_SALES_URL);
        		if(StringUtil.isEmpty(json)){
        			continue;
        		}
        		
        		JSONObject jsonObject = JSONObject.fromObject(json);
        		String msg = jsonObject.getString("msg");
        		if (!"0".equals(msg)) {
        			continue;
        		}
        		Saledata data = (Saledata) JSONObject.toBean(jsonObject, Saledata.class);
        		workflowService.updateOutdateByImei(imei.getImei(), DateFormate(data.getOutDate()));
        	}
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "修复成功");
        } catch (Exception e) {
            logger.error("获取受理 分页数据失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    public Timestamp DateFormate(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(time);
        return new Timestamp(date.getTime());
    }
}
