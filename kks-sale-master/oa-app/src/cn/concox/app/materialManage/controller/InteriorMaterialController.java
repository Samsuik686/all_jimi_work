package cn.concox.app.materialManage.controller;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sso.comm.model.UserInfo;
import cn.concox.app.common.page.Page;
import cn.concox.app.materialManage.service.InteriorMaterialService;
import cn.concox.app.materialManage.service.ProductMaterialFileLogService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.materialManage.InteriorMaterial;
import cn.concox.vo.materialManage.ProductMaterialFileLog;


/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午11:23:13
 */

@Controller
@RequestMapping("/interiorMaterial")
@Scope("prototype")
public class InteriorMaterialController extends BaseController {
	Logger logger = Logger.getLogger(InteriorMaterialController.class);
	
	@Autowired
	private InteriorMaterialService interiorMaterialService;
	
	@Autowired
	private ProductMaterialFileLogService productMaterialFileLogService;
	
	/**
	 * 获取内部材料数据列表
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:34:06
	 * @param interiorMaterial
	 * @param com
	 * @param req
	 * @return
	 */
	@RequestMapping("/interiorMaterialList")
	@ResponseBody
	public APIContent getInteriorMaterialList (@ModelAttribute InteriorMaterial interiorMaterial, @ModelAttribute CommonParam com,
			HttpServletRequest req) {
		try {
			Page<InteriorMaterial> page = interiorMaterialService.interiorMaterialList(interiorMaterial, com.getCurrentpage(), 
					com.getPageSize());
			req.getSession().setAttribute("totalValue",page.getTotal());
			List<InteriorMaterial> list = page.getResult();
			return super.putAPIData(list);
		} catch (Exception e){
			e.printStackTrace();
			logger.error("获取内部材料数据失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
		
	}
	
	/**
	 * 保存，修改内部材料数据信息
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:35:51
	 * @param interiorMaterial
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveOrUpdateInfo")
	@ResponseBody
	public APIContent saveOrUpdateInteriorMaterial(@ModelAttribute InteriorMaterial interiorMaterial,
			@RequestParam(value="interiorFileName", required=false) MultipartFile multipartFile, 
			 HttpServletRequest req){
		try {
			UserInfo user = super.getSessionUser(req);
			interiorMaterialService.saveOrUpdateInteriorMaterial(interiorMaterial, user, multipartFile);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
		
	}

	/**
	 * 下载文件
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:47:40
	 * @param interiorMaterial
	 * @param req
	 * @param response
	 */
	@RequestMapping("/downLoadInteriorFile")
	@ResponseBody
	public void downLoadInteriorFile (@ModelAttribute InteriorMaterial interiorMaterial, HttpServletRequest req, 
			HttpServletResponse response) {	
		try {
			ProductMaterialFileLog pmfl = new ProductMaterialFileLog();//下载日志记录
			pmfl.setIp(ProductMaterialController.getIpAddress(req));
			pmfl.setUserName(super.getSessionUserName(req));
			pmfl.setFileName(interiorMaterial.getFileName());
			pmfl.setFileType("内部资料");
			pmfl.setDownloadTime(new Timestamp(System.currentTimeMillis()));
			interiorMaterialService.downLoadInteriorFile(interiorMaterial, req, response);
			productMaterialFileLogService.insert(pmfl);
			
		} catch (Exception e) {
			logger.error("下载文件失败:",e);
		}
	}
	
	/**
	 * 删除内部材料附件
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:49:36
	 * @param interiorMaterial
	 * @return
	 */
	@RequestMapping("/deleteInteriorMaterialById")
	@ResponseBody
	public APIContent deleteInteriorMaterialById(@ModelAttribute InteriorMaterial interiorMaterial){
		try {
			interiorMaterialService.deleteInteriorMaterialById(interiorMaterial);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "删除文件信息失败！");
		}
	}

		
}
