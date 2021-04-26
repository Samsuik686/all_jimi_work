package cn.concox.app.cumstomFlow.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.cumstomFlow.CustomFlowCache;
import cn.concox.app.cumstomFlow.service.CustomFlowService;
import cn.concox.app.system.service.MenusService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.customProcess.CustomFlow;
import sso.comm.model.UserInfo;

/**
 * 
 * @author 183
 * @version 2020年5月28日
 */

@Controller
@RequestMapping("/customFlow")
@Scope("prototype")
public class CustomFlowController extends BaseController {
	Logger logger = Logger.getLogger(CustomFlowController.class);

	@Autowired
	private CustomFlowService customFlowService;
	
	@Resource(name="menusService")
	private MenusService service;
	
	@Autowired
	private CustomFlowCache customFlowCache;

	/**
	 * 获取自定义流程数据列表
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:13:03
	 * @param pm
	 * @param com
	 * @param req
	 * @return
	 */
	@RequestMapping("/customFlowList")
	@ResponseBody
	public APIContent getCustomFlowList(@ModelAttribute CustomFlow pm, @ModelAttribute CommonParam com,
			HttpServletRequest req) {
		try {
			Page<CustomFlow> page = customFlowService.customFlowList(pm, com.getCurrentpage(), com.getPageSize());
			req.getSession().setAttribute("totalValue", page.getTotal());
			List<CustomFlow> list = page.getResult();
			return super.putAPIData(list);
		} catch (Exception e) {
			logger.error("获取自定义流程数据失败:" + e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 插入自定义流程数据列表
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:13:03
	 * @param pm
	 * @param com
	 * @param req
	 * @return
	 */
	@RequestMapping("/insertCustomFlow")
	@ResponseBody
	public APIContent insertCustomFlow(String xml, String remark, HttpServletRequest req) {
		try {
			if (StringUtil.isEmpty(xml)) {
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "请上传流程图");
			}
			UserInfo user = (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
			String result = customFlowService.customFlowCreate(xml, remark, user);
			
			if(null == result){
				// 刷新缓存
				customFlowCache.updateModelNameMap();
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "创建流程成功");				
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, result);
			}
		} catch (Exception e) {
			logger.error("插入自定义流程数据失败:" + e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, e.getMessage());
		}
	}

	/**
	 * 获取自定义流程数据列表
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:13:03
	 * @param pm
	 * @param com
	 * @param req
	 * @return
	 */
	@RequestMapping("/customFlowPicture")
	public String customFlowPicture(String name, String forward, HttpServletRequest req, HttpServletResponse response) {
		try {
			// 1.新建 2.查看
			if ("1".equals(forward)) {
				req.setAttribute("forward", 1);
				return "page/customFlow/customFlowMake";
			} else if ("2".equals(forward)) {
				if (StringUtil.isEmpty(name)) {
					nullRecords(response, "请出入流程图名称");
					return "";
				}
				String decode = new String(name.getBytes("ISO-8859-1"),"UTF-8");
				String xml = customFlowService.customFlowXml(decode);
				if (StringUtil.isEmpty(xml)) {
					nullRecords(response, "找不到流程图");
					return "";
				}
				req.setAttribute("xml", xml);
				req.setAttribute("forward", 2);
				return "page/customFlow/customFlowMake";
			} else {
				nullRecords(response, "请选择跳转功能");
				return null;
			}
		} catch (Exception e) {
			logger.error("获取自定义流程数据列表失败:" + e);
			nullRecords(response, "未知异常");
			return null;
		}
	}
	
	/**
	 * 删除自定义流程数据列表
	 * 
	 * @author 183
	 * @version 2020年5月28日 下午2:13:03
	 * @param pm
	 * @param com
	 * @param req
	 * @return
	 */
	@RequestMapping("/customFlowDelete")
	@ResponseBody
	public APIContent customFlowDelete(String name, HttpServletRequest req) {
		try {
			if(StringUtil.isEmpty(name)){
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "查不到流程信息");
			}
			CustomFlow cf = customFlowService.selectCustomFlow(name);
			if (cf == null) {
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "查不到流程信息");
			}
			if (cf.getStatus() != 0) {
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "只能删除生效的记录");
			}
			customFlowService.deleteFlow(cf);
			
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			logger.error("删除自定义流程数据失败:" + e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 跳转错误说明
	 * 
	 * @author TangYuping
	 * @version 2017年4月12日 上午11:51:01
	 * @param response
	 */
	public void nullRecords(HttpServletResponse response, String mess) {

		try {
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.println("<script language='javascript'>");
			out.println("alert('" + mess + "');");
			out.println("history.back();");
			out.print("</script>");
			out.close();
		} catch (IOException e) {
			logger.error("跳转失败:" + e);
		}
	}
	
}
