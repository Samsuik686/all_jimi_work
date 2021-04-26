package cn.concox.app.basicdata.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.TestMaterialService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.TestMaterial;
import cn.concox.vo.commvo.CommonParam;
/**
 * 试流料管理控制 
 */
@Controller
@Scope("prototype")
public class TestMaterialController extends BaseController {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="testMaterialService")
	private TestMaterialService testMaterialService;
	
	/**
	 * 试流料分页查询
	 * @param testMaterial
	 * @param req
	 * @return
	 */
	@RequestMapping("testMaterial/testMaterialList")
	@ResponseBody
	public APIContent getTestMaterialList(@ModelAttribute TestMaterial testMaterial, @ModelAttribute CommonParam comp, HttpServletRequest req){
		try{
			Page<TestMaterial> pjlListPage=testMaterialService.getTestMaterialList(testMaterial, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", pjlListPage.getTotal());
			return super.putAPIData(pjlListPage.getResult());
		}catch(Exception e){
			logger.error("获取试流料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 获取单个试流料详情
	 * @param testMaterial 
	 * @param req
	 * @return
	 */
	@RequestMapping("testMaterial/getPjlInfo")
	@ResponseBody
	public APIContent getPjlInfo(@ModelAttribute TestMaterial testMaterial,HttpServletRequest req){
		try{
			testMaterial = testMaterialService.getTestMaterialInfo(testMaterial);
			return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, testMaterial);      
		}catch(Exception e){
			logger.error("获取试流料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
