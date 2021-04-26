package cn.concox.app.basicdata.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.GroupPjDetailsService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.GroupPjDetails;
import cn.concox.vo.commvo.CommonParam;
/**
 * 配件料管理控制 
 */
@Controller
@Scope("prototype")
public class GroupPjDetailsController extends BaseController {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="groupPjDetailsService")
	private GroupPjDetailsService groupPjDetailsService;
	
	/**
	 * 配件料分页查询
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPjDetails/groupPjDetailsList")
	@ResponseBody
	public APIContent getGroupPjDetailsList(@ModelAttribute GroupPjDetails groupPjDetails, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			String tempIds = req.getParameter("tempIds");
			if(!StringUtils.isBlank(tempIds)){
				groupPjDetails.setTempIds(tempIds);
			}
			Page<GroupPjDetails> pjlListPage=groupPjDetailsService.getGroupPjDetailsList(groupPjDetails, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", pjlListPage.getTotal());
			return super.putAPIData(pjlListPage.getResult());
		}catch(Exception e){
			logger.error("获取配件料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取单个配件料详情
	 * @param groupPjDetails 
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPjDetails/getGroupPjDetailsInfo")
	@ResponseBody
	public APIContent getGroupPjDetailsInfo(@ModelAttribute GroupPjDetails groupPjDetails,HttpServletRequest req){
		try{
			groupPjDetails = groupPjDetailsService.getGroupPjDetailsInfo(groupPjDetails);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, groupPjDetails);      
		}catch(Exception e){
			logger.error("获取配件料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增 / 修改配件料信息 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPjDetails/addOrUpdateGroupPjDetailsInfo")
	@ResponseBody
	public APIContent addOrUpdateGroupPjDetailsInfo(@ModelAttribute GroupPjDetails groupPjDetails,HttpServletRequest req){
		try{
			if(groupPjDetailsService.isExists(groupPjDetails) == 0){
				if(groupPjDetails.getId()>0){
					groupPjDetailsService.updateGroupPjDetailsInfo(groupPjDetails);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改配件料信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					groupPjDetailsService.insertGroupPjDetailsInfo(groupPjDetails);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"新增配件料信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "配件料信息已存在,请检查" );
			}
		}catch(Exception e){
			logger.error("更新配件料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增时查看是否重复
	 * @param groupPjDetails
	 * @param request
	 * @return
	 */
	@RequestMapping("groupPjDetails/checkRepeat")
	@ResponseBody
	public APIContent checkRepeat (HttpServletRequest req) {
		try {
			String ids=req.getParameter("ids");
			String groupId=req.getParameter("groupId");
			int ret = 0;
			if(null != ids && !StringUtil.isEmpty(ids)&&null != groupId && !StringUtil.isEmpty(groupId)){
				String id[] = ids.split(",");
				for (String s : id) {
					ret += groupPjDetailsService.findCountForGroupPj(groupId, s);
				}
			}
			return super.putAPIData(ret);
		} catch (Exception e) {
			logger.error("新增时查看是否重复失败", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增 配件料信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPjDetails/addInfo")
	@ResponseBody
	public APIContent addInfo(@ModelAttribute GroupPjDetails groupPjDetails, HttpServletRequest req) {
		try {
			String ids = req.getParameter("ids");
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(",");
				for (String s : id) {
					groupPjDetails.setPjlId(Integer.valueOf(s));
					groupPjDetailsService.insertGroupPjDetailsInfo(groupPjDetails);
				}
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "配件料信息新增" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("新增配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 修改配件料信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPjDetails/updateInfo")
	@ResponseBody
	public APIContent updateInfo(@ModelAttribute GroupPjDetails groupPjDetails, HttpServletRequest req) {
		try {
			groupPjDetailsService.updateGroupPjDetailsInfo(groupPjDetails);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "配件料信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("修改配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 删除配件料信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPjDetails/deleteInfo")
	@ResponseBody
	public APIContent deleteInfo(HttpServletRequest req) {
		try {
			String ids =req.getParameter("ids");
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(","); 
				groupPjDetailsService.deleteGroupPjDetailsInfo(id);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "配件料信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("删除配件料数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 关闭组合料新增时若groupid是null，删除配件料信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPjDetails/deleteInfoIsNull")
	@ResponseBody
	public APIContent deleteInfoIsNull(HttpServletRequest req) {
		try {
			String groupId = req.getParameter("groupId");
			groupPjDetailsService.deleteInfoIsNull(Integer.valueOf(groupId));
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "销售配件料组合详情信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("删除销售配件料组合详情信息失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
