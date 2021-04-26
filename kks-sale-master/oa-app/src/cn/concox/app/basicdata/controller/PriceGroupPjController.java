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

import cn.concox.app.basicdata.service.PriceGroupPjService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.basicdata.PriceGroupPj;
import cn.concox.vo.commvo.CommonParam;
/**
 * 报价配件料组合管理控制 
 */
@Controller
@Scope("prototype")
public class PriceGroupPjController extends BaseController {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="priceGroupPjService")
	private PriceGroupPjService priceGroupPjService;
	
	/**
	 * 报价配件料组合管理分页查询
	 * @param groupPj
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPj/priceGroupPjList")
	@ResponseBody
	public APIContent getGroupPjList(@ModelAttribute PriceGroupPj groupPj, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			Page<PriceGroupPj> groupPjListPage=priceGroupPjService.getPriceGroupPjList(groupPj, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", groupPjListPage.getTotal());
			return super.putAPIData(groupPjListPage.getResult());
		}catch(Exception e){
			logger.error("获取报价配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取报价配件料组合
	 * @param groupPj 
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPj/getPriceGroupPjInfo")
	@ResponseBody
	public APIContent getPriceGroupPjInfo(@ModelAttribute PriceGroupPj groupPj,HttpServletRequest req){
		try{
			groupPj = priceGroupPjService.getPriceGroupPjInfo(groupPj);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, groupPj);      
		}catch(Exception e){
			logger.error("获取报价配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * @Title: getPriceGroupPjlByIds 
	 * @Description:维修报价选择组合配件料
	 * @param req
	 * @return 
	 * @author 20160308
	 * @date 2017年11月22日 上午10:29:42
	 */
	@RequestMapping("priceGroupPj/getPriceGroupPjlByIds")
	@ResponseBody
	public APIContent getPriceGroupPjlByIds(HttpServletRequest req){
		try{
			String ids =req.getParameter("ids"); //报价配件料组合id
			if(null != ids && !StringUtils.isBlank(ids)){
				String id[] = ids.split(",");
				List<Pjlmanage> pjlList = priceGroupPjService.getPjlIdsByGroupPjIds(id);
				
				return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, pjlList);  
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
		}catch(Exception e){
			logger.error("获取报价配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 新增 / 修改报价配件料组合信息 
	 * @param groupPj
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPj/addOrUpdatePriceGroupPjInfo")
	@ResponseBody
	public APIContent addOrUpdatePriceGroupPjInfo(@ModelAttribute PriceGroupPj groupPj,HttpServletRequest req){
		try{
			if(priceGroupPjService.isExists(groupPj) == 0){
				if(groupPj.getGroupPjId()>0){
					priceGroupPjService.updatePriceGroupPjInfo(groupPj);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"修改报价配件料组合信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					priceGroupPjService.insertPriceGroupPjInfo(groupPj);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"新增报价配件料组合信息"+Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "报价配件料组合信息已存在,请检查" );
			}
		}catch(Exception e){
			logger.error("更新报价配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除报价配件料组合信息
	 * @param groupPj
	 * @param req
	 * @return
	 */
	@RequestMapping("priceGroupPj/deletePriceGroupPjInfo")
	@ResponseBody
	public APIContent deletePriceGroupPjInfo(@ModelAttribute PriceGroupPj groupPj,HttpServletRequest req){
		try{
			if(priceGroupPjService.checkSupportDel(groupPj) > 0){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "该报价配件料组合存在关联数据，不可删除");
			}
			priceGroupPjService.deletePriceGroupPjInfo(groupPj);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"报价配件料组合信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("删除报价配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	
	/**
	 * @Title: deletePriceGroupPjInfo 
	 * @Description:自动生成报价配件料组合
	 * @param groupPj
	 * @param req
	 * @return 
	 * @author 20160308
	 * @date 2017年11月20日 上午10:04:39
	 */
	@RequestMapping("priceGroupPj/addAll")
	@ResponseBody
	public APIContent addAll(HttpServletRequest req){
		try{
			String model = req.getParameter("model");
			String keyType = req.getParameter("keyType");
			String keyDesc = req.getParameter("keyDesc");
			priceGroupPjService.getPjlIdsByKeyDesc(model, null, keyDesc, keyType);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"自动生成报价配件料组合"+Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			logger.error("自动生成报价配件料组合数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);	
		}
	}
}
