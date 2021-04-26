package cn.concox.app.basicdata.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.PriceGroupPjDetailsService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.PriceGroupPjDetails;
import cn.concox.vo.commvo.CommonParam;
/**
 *报价配件料组合详情管理控制 
 */
@Controller
@Scope("prototype")
public class PriceGroupPjDetailsController extends BaseController {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="priceGroupPjDetailsService")
	private PriceGroupPjDetailsService priceGroupPjDetailsService;
	
	/**
	 * 报价配件料组合详情分页查询
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/priceGroupPjDetailsListPage")
	@ResponseBody
	public APIContent getPriceGroupPjDetailsListPage(@ModelAttribute PriceGroupPjDetails groupPjDetails, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			String tempIds = req.getParameter("tempIds");
			if(!StringUtils.isBlank(tempIds)){
				groupPjDetails.setTempIds(tempIds);
			}
			Page<PriceGroupPjDetails> pjlListPage=priceGroupPjDetailsService.getPriceGroupPjDetailsListPage(groupPjDetails, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", pjlListPage.getTotal());
			return super.putAPIData(pjlListPage.getResult());
		}catch(Exception e){
			logger.error("获取报价配件料组合详情数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 报价配件料组合详情不分页查询
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/priceGroupPjDetailsList")
	@ResponseBody
	public APIContent getPriceGroupPjDetailsList(@ModelAttribute PriceGroupPjDetails groupPjDetails, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			String tempIds = req.getParameter("tempIds");
			if(!StringUtils.isBlank(tempIds)){
				groupPjDetails.setTempIds(tempIds);
			}
			List<PriceGroupPjDetails> pjlList=priceGroupPjDetailsService.getPriceGroupPjDetailsList(groupPjDetails);
			return super.putAPIData(pjlList);
		}catch(Exception e){
			logger.error("获取报价配件料组合详情数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取单个报价配件料组合详情详情
	 * @param groupPjDetails 
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/getPriceGroupPjDetailsInfo")
	@ResponseBody
	public APIContent getPriceGroupPjDetailsInfo(@ModelAttribute PriceGroupPjDetails groupPjDetails,HttpServletRequest req){
		try{
			groupPjDetails = priceGroupPjDetailsService.getPriceGroupPjDetailsInfo(groupPjDetails);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, groupPjDetails);      
		}catch(Exception e){
			logger.error("获取报价配件料组合详情数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增 / 修改报价配件料组合详情信息 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/addOrUpdatePriceGroupPjDetailsInfo")
	@ResponseBody
	public APIContent addOrUpdateGroupPjDetailsInfo(@ModelAttribute PriceGroupPjDetails groupPjDetails,HttpServletRequest req){
		try{
			if(priceGroupPjDetailsService.isExists(groupPjDetails) == 0){
				if(groupPjDetails.getId()>0){
					priceGroupPjDetailsService.updatePriceGroupPjDetailsInfo(groupPjDetails);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改报价配件料组合详情信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					priceGroupPjDetailsService.insertPriceGroupPjDetailsInfo(groupPjDetails);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"新增报价配件料组合详情信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "报价配件料组合详情信息已存在,请检查" );
			}
		}catch(Exception e){
			logger.error("更新报价配件料组合详情数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增时查看是否重复
	 * @param groupPjDetails
	 * @param request
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/checkRepeat")
	@ResponseBody
	public APIContent checkRepeat (HttpServletRequest req) {
		try {
			String ids=req.getParameter("ids");
			String groupId=req.getParameter("groupId");
			int ret = 0;
			if(null != ids && !StringUtil.isEmpty(ids)&&null != groupId && !StringUtil.isEmpty(groupId)){
				String id[] = ids.split(",");
				for (String s : id) {
					ret += priceGroupPjDetailsService.findCountForGroupPj(groupId, s);
				}
			}
			return super.putAPIData(ret);
		} catch (Exception e) {
			logger.error("新增时查看是否重复失败", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增 报价配件料组合详情信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/addInfo")
	@ResponseBody
	public APIContent addInfo(@ModelAttribute PriceGroupPjDetails groupPjDetails, HttpServletRequest req) {
		try {
			String ids = req.getParameter("ids");
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(",");
				for (String s : id) {
					groupPjDetails.setPjlId(Integer.valueOf(s));
					priceGroupPjDetailsService.insertPriceGroupPjDetailsInfo(groupPjDetails);
				}
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "报价配件料组合详情信息新增" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("新增报价配件料组合详情数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 修改报价配件料组合详情信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/updateInfo")
	@ResponseBody
	public APIContent updateInfo(@ModelAttribute PriceGroupPjDetails groupPjDetails, HttpServletRequest req) {
		try {
			priceGroupPjDetailsService.updatePriceGroupPjDetailsInfo(groupPjDetails);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "报价配件料组合详情信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("修改报价配件料组合详情数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 删除报价配件料组合详情信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/deleteInfo")
	@ResponseBody
	public APIContent deleteInfo(HttpServletRequest req) {
		try {
			String ids =req.getParameter("ids");
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(","); 
				priceGroupPjDetailsService.deletePriceGroupPjDetailsInfo(id);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "报价配件料组合详情信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
		} catch (Exception e) {
			logger.error("删除报价配件料组合详情数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 关闭组合料新增时若groupid是null，删除报价配件料组合详情信息
	 * 
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/deleteInfoIsNull")
	@ResponseBody
	public APIContent deleteInfoIsNull(HttpServletRequest req) {
		try {
			String groupId = req.getParameter("groupId");
			priceGroupPjDetailsService.deleteInfoIsNull(Integer.valueOf(groupId));
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "报价配件料组合详情信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("删除报价配件料组合详情数据失败:", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 维修报价选择组合获取配件料
	 * @param groupPjDetails
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPjDetails/getPjlByGroupIds")
	@ResponseBody
	public APIContent getPjlByGroupIds(@ModelAttribute PriceGroupPjDetails groupPjDetails, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			String ids =req.getParameter("groupIds");
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(",");
				List<PriceGroupPjDetails> pjlList=priceGroupPjDetailsService.getPjlByGroupIds(id);
				return super.putAPIData(pjlList);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			logger.error("获取报价配件料组合详情数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
