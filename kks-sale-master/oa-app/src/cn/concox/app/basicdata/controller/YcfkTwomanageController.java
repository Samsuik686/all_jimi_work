package cn.concox.app.basicdata.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.comm.mail.CVMailSendInfo;
import cn.concox.comm.mail.CVMailSender;
import cn.concox.comm.mail.CVMailUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.service.YcfkTwomanageService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.YcfkManageFile;
import cn.concox.vo.basicdata.YcfkTwomanage;
import cn.concox.vo.commvo.CommonParam;

/**
 * <pre>
 * YcfkTwomanageController 异常反馈管理
 * </pre>
 */
@Controller
@Scope("prototype")
public class YcfkTwomanageController extends BaseController {
	Logger log=Logger.getLogger(YcfkTwomanageController.class);
	
	@Resource(name="ycfkTwomanageService")
	private YcfkTwomanageService ycfkTwomanageService;
	
	/**
	 * 查询条件(日期)
	 * @param ycfkTwomanage
	 * @param req
	 */
	public void searchBy(YcfkTwomanage ycfkTwomanage,HttpServletRequest req){
		String startTime=req.getParameter("startTime");
		String endTime=req.getParameter("endTime");
//		String treeDate=req.getParameter("treeDate");
		
		if(!StringUtils.isBlank(startTime)){
			ycfkTwomanage.setStartTime(startTime);
		}
		if(!StringUtils.isBlank(endTime)){
			ycfkTwomanage.setEndTime(endTime);
		}
//		if(!StringUtils.isBlank(treeDate)){
//			ycfkTwomanage.setTreeDate(treeDate);
//		}
	}
	
	/*
	 * 获取异常反馈列表，分页
	 */
	@RequestMapping("ycfkTwomanage/ycfkTwomanageList")
	@ResponseBody
	public APIContent getYcfkTwomanageListPage(@ModelAttribute YcfkTwomanage ycfkTwomanage, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			searchBy(ycfkTwomanage, req);
			ycfkTwomanage.setCurrLoginer(super.getSessionUserName(req));
			String userId = super.getSessionUser(req).getuId();
			Page<YcfkTwomanage> ycfkTwomanageListPage=ycfkTwomanageService.getYcfkTwomanageListPage(ycfkTwomanage, comp.getCurrentpage(), comp.getPageSize(), userId);
			req.getSession().setAttribute("totalValue", ycfkTwomanageListPage.getTotal());
			return super.putAPIData(ycfkTwomanageListPage.getResult());
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取异常反馈列表失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 查询异常反馈
	 */
	@RequestMapping("ycfkTwomanage/selectYcfkTwomanage")
	@ResponseBody
	public APIContent selectYcfkTwomanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute YcfkTwomanage ycfkTwomanage){
		log.info("异常反馈查询开始");
		try {
			if(ycfkTwomanage.getBackId()!=null){
				YcfkTwomanage ycfkTwomanage1=ycfkTwomanageService.select(ycfkTwomanage);
				return super.putAPIData(new APIContent(Globals.OPERA_SUCCESS_CODE, "查询成功!", ycfkTwomanage1));
			}else{
				log.error("异常反馈查询失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			log.error("异常反馈查询失败！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/*
	 * 更新和新增异常反馈
	 */
	@RequestMapping("ycfkTwomanage/addOrUpdateYcfkTwomanage")
	@ResponseBody
	public APIContent addOrUpdateYcfkTwomanage(HttpServletRequest req, @ModelAttribute YcfkTwomanage ycfkTwomanage, 
			@RequestParam(value="files", required=false)MultipartFile[] multipartFile){
		log.info("更新异常反馈信息开始");
		try {
			String feedBackTime=req.getParameter("feedBackTime");
			String completionTime=req.getParameter("completionTime");
			String warranty=req.getParameter("warranty");
			if(!StringUtils.isBlank(warranty)){
				ycfkTwomanage.setWarranty(Integer.valueOf(warranty));
			}
			if(!StringUtils.isBlank(feedBackTime)){
				ycfkTwomanage.setFeedBackDate(Timestamp.valueOf(feedBackTime));
			}
			if(!StringUtils.isBlank(completionTime)){
				ycfkTwomanage.setCompletionDate(Timestamp.valueOf(completionTime));
			}
			
			if (1==(ycfkTwomanage.getCompletionState())) {
				ycfkTwomanageService.flushCurrentSite(ycfkTwomanage);	
			}
			
			UserInfo user = (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
			if(null != ycfkTwomanage.getBackId() && ycfkTwomanage.getBackId()>0){
				if(ycfkTwomanageService.isExists(ycfkTwomanage)==0){
				  ycfkTwomanageService.setCurrentSite(ycfkTwomanage);
				  YcfkTwomanage old = ycfkTwomanageService.select(ycfkTwomanage);
					
					if(!StringUtil.isRealEmpty(ycfkTwomanage.getMeasures())){
						ycfkTwomanage.setMeasures(old.getMeasures() + ycfkTwomanageService.generateMeasuresTxt(req, ycfkTwomanage.getMeasures(), 
								ycfkTwomanage.getCurrentSite()));
					}else{
						ycfkTwomanage.setMeasures(old.getMeasures());
					}
					ycfkTwomanageService.update(ycfkTwomanage, multipartFile, user.getuName());
					return new APIContent(Globals.OPERA_SUCCESS_CODE,"更新异常反馈信息成功"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "异常反馈信息已存在,请检查" );
				}
			}else{
				if(ycfkTwomanageService.isExists(ycfkTwomanage)==0){
					ycfkTwomanageService.setCurrentSite(ycfkTwomanage);
					ycfkTwomanage.setMeasures(ycfkTwomanageService.generateMeasuresTxt(req, ycfkTwomanage.getMeasures(),
							ycfkTwomanage.getCurrentSite()));
					if(ycfkTwomanage.getCompletionState()==null||"".equals(ycfkTwomanage.getCompletionState())){
						ycfkTwomanage.setCompletionState(1); //不选状态 默认已完成
					}
					ycfkTwomanage.setFollowupPeople(user.getuName());   //新增时设置跟进人为当前登录人													
					ycfkTwomanageService.insert(ycfkTwomanage, multipartFile, user.getuName());
					return new APIContent(Globals.OPERA_SUCCESS_CODE,"新增异常反馈信息成功"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "异常反馈信息已存在,请检查" );
				}
			}
		} catch (Exception e) {
			log.error("更新异常反馈信息失败！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	/*
	 * 删除异常反馈
	 */
	@RequestMapping("ycfkTwomanage/deleteYcfkTwomanage")
	@ResponseBody
	public APIContent deleteYcfkTwomanage(HttpServletRequest req,HttpServletResponse resp,@ModelAttribute YcfkTwomanage ycfkTwomanage){
		log.info("删除异常反馈开始");
		try {
			String idsemp = req.getParameter("ids");
			String[] ids = idsemp.split(",");
			if(null != ids && ids.length > 0){
				ycfkTwomanageService.detele(ids);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"故障信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				log.error("删除异常反馈失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			log.error("删除异常反馈！",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 导出异常反馈信息数据
	 * @param YcfkTwomanage
	 * @param req
	 * @return
	 */
	@RequestMapping("ycfkTwomanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute YcfkTwomanage ycfkTwomanage,HttpServletRequest req,HttpServletResponse response){
		try{
			searchBy(ycfkTwomanage, req);
			ycfkTwomanage.setCurrLoginer(super.getSessionUserName(req));
			String userId = super.getSessionUser(req).getuId();
			ycfkTwomanageService.ExportDatas(ycfkTwomanage,req,response,userId);
		}catch(Exception e){
			e.printStackTrace();
			log.error("导出异常反馈信息数据失败:"+e.toString());
		}
	}
	/**
	 * 列表查询不分页
	 */
	@RequestMapping("ycfkTwomanage/queryList")
	@ResponseBody
	public  APIContent queryList(@ModelAttribute YcfkTwomanage ycfkTwomanage){
		log.info("列表查询开始");
		 try{
			List<YcfkTwomanage> listYcfkTwomanage=ycfkTwomanageService.queryList(ycfkTwomanage);
			return super.putAPIData(listYcfkTwomanage);
		 }catch(Exception e){
		    log.error("获取异常反馈列表失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		 }
	}
	
	/**
	 * 导入异常反馈信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("ycfkTwomanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = ycfkTwomanageService.ImportDatas(file, request);
		} catch (Exception e) {
			log.error("导入异常反馈信息失败:",e);
			errorList.add("导入异常反馈信息失败:,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
	
	/**
	 * 异常反馈发送数据到下一工站
	 * @author TangYuping
	 * @version 2017年3月9日 下午1:50:01
	 * @param ycfk
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("ycfkTwomanage/sendDataToNextSite")
	@ResponseBody
	public APIContent sendDataToNextSite (@ModelAttribute YcfkTwomanage ycfk, 
			HttpServletRequest req, HttpServletResponse resp) {
		try {
			String idsemp = req.getParameter("ids");
			ycfk.setFollows(Arrays.asList(ycfk.getFollowupPeople().split(",")));
			boolean b = ycfkTwomanageService.sendData(ycfk, idsemp, req);
			if (b) {
				//发送邮件、、已作废，此功能不再使用
//				boolean isAllSendMail = ycfkTwomanageService.sendMail(ycfk);
				//发送抄送邮件
				boolean b1 = ycfkTwomanageService.sendMailCopy(idsemp);
				String msg = "数据发送下一工站成功";
//				if(!isAllSendMail || !b1){
//					msg = msg + ",发送邮件失败";
//				}else{
//					msg = msg +  "发送邮件失败";
//				}
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, msg);
			} else {
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}			
		} catch (Exception e){
			log.error("发送下一站失败"+e);
			e.printStackTrace();
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
		
	}
	
	/**
	 * 异常反馈下载附件
	 * @author TangYuping
	 * @version 2017年4月14日 下午2:19:18
	 * @param file
	 * @param req
	 * @param response
	 */
	@RequestMapping("ycfkTwomanage/downLoadFile")
	@ResponseBody
	public void downProductFile (@ModelAttribute YcfkManageFile file, HttpServletRequest req, 
			HttpServletResponse response) {	
		try {
			ycfkTwomanageService.downLoadFile(file, req, response);
		} catch (Exception e) {
			logger.error("下载文件失败:",e);
		}
	}
	
	/**
	 * 删除附件信息
	 * @author TangYuping
	 * @version 2017年4月14日 下午2:22:57
	 * @param file
	 * @return
	 */
	@RequestMapping("ycfkTwomanage/deleteFileByFid")
	@ResponseBody
	public APIContent deleteFileByFid (@ModelAttribute YcfkManageFile file) {
		try {
			ycfkTwomanageService.deleteFileByFid(file);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);	
			} catch (Exception e) {
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "删除附件信息失败！");
			}
	}
	
	/**
	 * 根据异常反馈ID获取对应的附件信息
	 * @author TangYuping
	 * @version 2017年4月14日 上午11:48:51
	 * @param yid
	 * @return
	 */
	@RequestMapping("ycfkTwomanage/getFileByYid")
	@ResponseBody
	public APIContent getFileByYid(@Param("yid")String yid){
		try {
			List<YcfkManageFile> fileList = ycfkTwomanageService.getFileByYid(Integer.valueOf(yid));
			return super.putAPIData(fileList);
		} catch (Exception e) {
			log.error("根据异常反馈ID获取附件失败！"+e);
			return super.putAPIData( new ArrayList<YcfkManageFile>());
		}
		
	}
	
}
