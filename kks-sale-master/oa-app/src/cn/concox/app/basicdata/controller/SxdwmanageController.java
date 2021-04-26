package cn.concox.app.basicdata.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.concox.app.basicdata.service.SxdwmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.BatchNumUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Sxdwmanage;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.workflow.Workflow;

@Controller
@Scope("prototype")
public class SxdwmanageController extends BaseController {
	private static Logger log = Logger.getLogger(SxdwmanageController.class);

	@Resource(name = "sxdwmanageService")
	public SxdwmanageService sxdwmanageService;
	
	@Resource(name="workflowService")
	private WorkflowService workflowService;
	
	
	/**
	   *  校验手机号是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping("sxdwmanage/phoneCheck")
	@ResponseBody
	public APIContent phoneCheck(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			int result = sxdwmanageService.phoneCheck(sxdwmanage);
			
			// 修改或是新增
			if (sxdwmanage.getCId() == null) {
				// 新增
				if (result == 0) {
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "校验手机号：" + Globals.OPERA_SUCCESS_CODE_DESC);
				} else {
					return super.operaStatus(Globals.PHONE_HAD_EXIST, "校验手机号：" + Globals.PHONE_HAD_EXIST_DESC);
				}
			} else {
				// 修改
				if (result == 1) {
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "校验手机号：" + Globals.OPERA_SUCCESS_CODE_DESC);
				} else {
					sxdwmanage.setCId(null);
					if (sxdwmanageService.phoneCheck(sxdwmanage) == 0) {
						return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "校验手机号：" + Globals.OPERA_SUCCESS_CODE_DESC);
					} else {
						return super.operaStatus(Globals.PHONE_HAD_EXIST, "校验手机号：" + Globals.PHONE_HAD_EXIST_DESC);
					}
				}
			}
		} catch (NumberFormatException e) {
			log.error("校验手机号失败：" + e.toString());
			return super.operaStatus(Globals.PHONE_HAD_EXIST, "校验手机号：" + Globals.PHONE_HAD_EXIST_DESC);
		}
	}
	
	/**
	   *  校验地址是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping("sxdwmanage/addressCheck")
	@ResponseBody
	public APIContent addressCheck(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			int result = sxdwmanageService.addressCheck(sxdwmanage);
			
			// 修改或是新增
			if (sxdwmanage.getCId() == null) {
				// 新增
				if (result == 0) {
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "校验地址：" + Globals.OPERA_SUCCESS_CODE_DESC);
				} else {
					return super.operaStatus(Globals.ADDRESS_HAD_EXIST, "校验地址：" + Globals.ADDRESS_HAD_EXIST_DESC);
				}
			} else {
				// 修改
				if (result == 1) {
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "校验地址：" + Globals.OPERA_SUCCESS_CODE_DESC);
				} else {
					sxdwmanage.setCId(null);
					if (sxdwmanageService.addressCheck(sxdwmanage) == 0) {
						return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "校验地址：" + Globals.OPERA_SUCCESS_CODE_DESC);
					} else {
						return super.operaStatus(Globals.ADDRESS_HAD_EXIST, "校验地址：" + Globals.ADDRESS_HAD_EXIST_DESC);
					}
				}
			}
		} catch (NumberFormatException e) {
			log.error("校验地址失败：" + e.toString());
			return super.operaStatus(Globals.ADDRESS_HAD_EXIST, "校验地址：" + Globals.ADDRESS_HAD_EXIST_DESC);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param sxdwmanage
	 * @param request
	 * @return
	 */
	@RequestMapping("sxdwmanage/sxdwmanageList")
	@ResponseBody
	public APIContent getSxdwmanageListPage(@ModelAttribute Sxdwmanage sxdwmanage, @ModelAttribute CommonParam comp, HttpServletRequest request) {
		try {
//			int getCurrentPage = 1;
//			if (!StringUtils.isBlank(request.getParameter("currentpage"))) {
//				getCurrentPage = Integer.parseInt(request.getParameter("currentpage"));
//			}
			Page<Sxdwmanage> sxdwmanageListPage = sxdwmanageService.getSxdwmanageListPage(sxdwmanage, comp.getCurrentpage(), comp.getPageSize());
			request.getSession().setAttribute("totalValue", sxdwmanageListPage.getTotal());
			return super.putAPIData(sxdwmanageListPage.getResult());
		} catch (NumberFormatException e) {
			log.error("查询送修单位信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询送修单位信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	//TODO 将新增和修改时判断重复的sql分别写会简单很多，后期重构
	@RequestMapping("sxdwmanage/addOrUpdateSxdwmanage")
	@ResponseBody
	public APIContent addOrUpdateSxdwmanage(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			if(sxdwmanage.getCId() > 0){
				if(sxdwmanageService.isExists(sxdwmanage) == 1){
					// 单位名称未变化 直接更新
					sxdwmanage.setUpdateBy(super.getSessionUserName(request));
					sxdwmanage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					sxdwmanageService.updateSxdwmanage(sxdwmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "送修单位信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
				}
				else{
					// 单位名称变化了 需要再次校验
					Integer cId = sxdwmanage.getCId();
					sxdwmanage.setCId(null);
					if (sxdwmanageService.isExists(sxdwmanage) < 1 ) {
						sxdwmanage.setCId(cId);
						sxdwmanage.setUpdateBy(super.getSessionUserName(request));
						sxdwmanage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						sxdwmanageService.updateSxdwmanage(sxdwmanage);
						return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "送修单位信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
					}
					else {
						return super.operaStatus(Globals.OPERA_FAIL_CODE, "送修单位信息已存在,请检查" );
					}
				}
			}else{
				sxdwmanage.setCId(null);
				if(sxdwmanageService.isExists(sxdwmanage) < 1){
					sxdwmanage.setCreateBy(super.getSessionUserName(request));
					sxdwmanage.setCreateTime(new Timestamp(System.currentTimeMillis()));
					sxdwmanageService.insertSxdwmanage(sxdwmanage);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "送修单位信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
				}
				else{
					return super.operaStatus(Globals.OPERA_FAIL_CODE, "送修单位信息已存在,请检查" );
				}
			}
		} catch (Exception e) {
			log.error("更新送修单位信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新送修单位信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("sxdwmanage/insertSxdwmanage")
	@ResponseBody
	public APIContent insertSxdwmanage(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			if(sxdwmanageService.isExists(sxdwmanage)==0){
				sxdwmanage.setCreateBy(super.getSessionUserName(request));
				sxdwmanage.setCreateTime(new Timestamp(System.currentTimeMillis()));
				sxdwmanageService.insertSxdwmanage(sxdwmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "送修单位信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "送修单位信息已存在,请检查" );
			}
		} catch (Exception e) {
			log.error("送修单位信息增加失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "送修单位信息增加" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("sxdwmanage/updateSxdwmanage")
	@ResponseBody
	public APIContent updateSxdwmanage(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			if(sxdwmanageService.isExists(sxdwmanage)==0){
				sxdwmanage.setUpdateBy(super.getSessionUserName(request));
				sxdwmanage.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				sxdwmanageService.updateSxdwmanage(sxdwmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "送修单位信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "送修单位信息已存在,请检查" );
			}
		} catch (Exception e) {
			log.error("送修单位信息修改失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "送修单位信息修改" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("sxdwmanage/deleteSxdwmanage")
	@ResponseBody
	public APIContent deleteSxdwmanage(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			if(sxdwmanageService.checkSupportDel(sxdwmanage)){
				sxdwmanageService.deleteSxdwmanage(sxdwmanage);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "送修单位信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
			}
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "该送修单位存在关联数据，不可删除");
		} catch (Exception e) {
			log.error("送修单位信息删除失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "送修单位信息删除" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("sxdwmanage/getSxdwmanage")
	@ResponseBody
	public APIContent getSxdwmanage(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			Sxdwmanage c = sxdwmanageService.getSxdwmanage(sxdwmanage);
			if(null != c && null != c.getCId()){
				//TODO 获取送修批号
				Workflow workflow = workflowService.getBatchNo(c.getCId().toString());
				if(null != workflow){
					String rep=workflow.getRepairnNmber();
					if(!StringUtil.isRealEmpty(rep)){
						if(workflowService.isExistsAndPay(rep) > 0){
							c.setRepairnum(BatchNumUtil.getBatchNum());
							c.setIsExistsAndPay(1); // 生成的新的送修批号
						}else{
							//判断该批次是否已报价
							if(workflowService.getRepairNumberCountPriced(rep) > 0){
								c.setPricedCount(1);
								c.setOldRepairNumber(rep);
							}
							//送修批号只保存一天
							c.setRepairnum(rep); 
							c.setIsExistsAndPay(0);  //同一个送修批号
							c.setIsDelivery(workflow.getPayDelivery());
						}
					}
				}else{
					c.setRepairnum(BatchNumUtil.getBatchNum());
				}
			}
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取送修单位信息失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取送修单位信息" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("sxdwmanage/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("sxdwmanage") Sxdwmanage sxdwmanage, HttpServletRequest request) {
		try {
			List<Sxdwmanage> c = sxdwmanageService.queryList(sxdwmanage);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取送修单位信息列表失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取送修单位信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 导出送修单位数据
	 * 
	 * @param sxdwmanage
	 * @param request
	 * @param response
	 */
	@RequestMapping("sxdwmanage/ExportDatas")
	@ResponseBody
	public void ExportDatas(@ModelAttribute Sxdwmanage sxdwmanage, HttpServletRequest request, HttpServletResponse response) {
		try {
			sxdwmanageService.ExportDatas(sxdwmanage, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导出送修单位数据失败:" + e.toString());
		}
	}

	/**
	 * 导入送修单位
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("sxdwmanage/ImportDatas")
	@ResponseBody
	public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorList = new ArrayList<String>();
		try {
			errorList = sxdwmanageService.ImportDatas(file, request);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入送修单位信息失败:" + e.toString());
			errorList.add("导入送修单位信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
		}
		return super.putAPIData(errorList);
	}
}
