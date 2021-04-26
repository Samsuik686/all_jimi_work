package cn.concox.app.basicdata.controller;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.concox.app.basicdata.service.GroupPjDetailsService;
import cn.concox.app.basicdata.service.GroupPjService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.GroupPj;
import cn.concox.vo.commvo.CommonParam;
/**
 * 销售配件料组合管理控制 
 */
@Controller
@Scope("prototype")
public class GroupPjController extends BaseController {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="groupPjService")
	private GroupPjService groupPjService;
	
	@Resource(name="groupPjDetailsService")
	private GroupPjDetailsService groupPjDetailsService;
	
	/**
	 * 销售配件料组合管理分页查询
	 * @param groupPj
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPj/groupPjList")
	@ResponseBody
	public APIContent getGroupPjList(@ModelAttribute GroupPj groupPj, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			Page<GroupPj> groupPjListPage=groupPjService.getGroupPjList(groupPj, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", groupPjListPage.getTotal());
			return super.putAPIData(groupPjListPage.getResult());
		}catch(Exception e){
			logger.error("获取销售配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取销售配件料组合
	 * @param groupPj 
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPj/getGroupPjInfo")
	@ResponseBody
	public APIContent getGroupPjInfo(@ModelAttribute GroupPj groupPj,HttpServletRequest req){
		try{
			groupPj = groupPjService.getGroupPjInfo(groupPj);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, groupPj);      
		}catch(Exception e){
			logger.error("获取销售配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	public void queryAbout(GroupPj groupPj,HttpServletRequest req){
//		String tempPjlIds = req.getParameter("tempPjlIds");//配件料id
		String tempDetailsIds = req.getParameter("tempDetailsIds");//详情id
		Integer id = groupPjService.addOrUpdateGroupPjInfo(groupPj);
		if(!StringUtils.isBlank(tempDetailsIds)){
			if (null != id && id > 0){
				String[] tempids = tempDetailsIds.trim().split(",");
				groupPjDetailsService.updateGroupIdByIds(groupPj.getGroupPjId().toString(), tempids);
			}
		}
	}
	
	/**
	 * 新增 / 修改销售配件料组合信息 
	 * @param groupPj
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPj/addOrUpdateGroupPjInfo")
	@ResponseBody
	public APIContent addOrUpdateGroupPjInfo(@ModelAttribute GroupPj groupPj,HttpServletRequest req){
		try{
			if(groupPjService.isExists(groupPj) == 0){
				if(groupPj.getGroupPjId()>0){
					queryAbout(groupPj,req);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改销售配件料组合信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					queryAbout(groupPj,req);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"新增销售配件料组合信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "销售配件料组合信息已存在,请检查" );
			}
		}catch(Exception e){
			logger.error("更新销售配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 修改销售配件料组合信息 
	 * @param groupPj
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPj/updateGroupPjInfo")
	@ResponseBody
	public APIContent updateGroupPjInfo(@ModelAttribute GroupPj groupPj,HttpServletRequest req){
		try{
			groupPjService.updateGroupPjInfo(groupPj);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改销售配件料组合信息"+Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("修改销售配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除销售配件料组合信息
	 * @param groupPj
	 * @param req
	 * @return
	 */
	@RequestMapping("groupPj/deleteGroupPjInfo")
	@ResponseBody
	public APIContent deleteGroupPjInfo(@ModelAttribute GroupPj groupPj,HttpServletRequest req){
		try{
			if(groupPjService.checkSupportDel(groupPj) > 0){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "该销售配件料组合存在关联数据，不可删除");
			}
			if(null != groupPj && groupPj.getGroupPjId()>0){
				groupPjDetailsService.deleteByGroupId(groupPj.getGroupPjId());
			}
			groupPjService.deleteGroupPjInfo(groupPj);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"销售配件料组合信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("删除销售配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * @Title: ImportDatas 
	 * @Description:导入销售配件料组合数据
	 * @param file
	 * @param request
	 * @param response
	 * @return 
	 * @author HuangGangQiang
	 * @date 2017年7月12日 下午6:17:36
	 */
	@RequestMapping("groupPj/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = groupPjService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入销售配件料组合数据失败:" + e.toString());
			errorList.add("导入销售配件料组合数据失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}
