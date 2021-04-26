package cn.concox.app.basicdata.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.LockIdUrlService;
import cn.concox.app.common.page.Page;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.LockIdUrl;
import cn.concox.vo.commvo.CommonParam;

@Controller
@Scope("prototype")
public class LockIdUrlController extends BaseController{
	private static Logger log = Logger.getLogger(LockIdUrlController.class);
	
	@Resource(name="lockIdUrlService")
	private LockIdUrlService lockIdUrlService;
	
	/**
	 * 智能锁ID二维码分页查询
	 * 
	 * @param lockIdUrl
	 * @param req
	 * @return
	 */
	@RequestMapping("lockIdUrl/lockIdUrlList")
	@ResponseBody
	public APIContent getLockIdUrlListPage(@ModelAttribute LockIdUrl lockIdUrl, @ModelAttribute CommonParam comp, HttpServletRequest req) {
		try {
			Page<LockIdUrl> lockIdUrlPage = lockIdUrlService.getLockIdUrlListPage(lockIdUrl, comp.getCurrentpage(), comp.getPageSize());
			req.getSession().setAttribute("totalValue", lockIdUrlPage.getTotal());
			return super.putAPIData(lockIdUrlPage.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取智能锁ID二维码数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 更新 智能锁ID二维码信息
	 * 
	 * @param lockIdUrl
	 * @param req
	 * @return
	 */
	@RequestMapping("lockIdUrl/addOrUpdateLockIdUrl")
	@ResponseBody
	public APIContent addOrUpdateLockIdUrl(@ModelAttribute LockIdUrl lockIdUrl, HttpServletRequest req) {
		try {
			if(lockIdUrlService.isExists(lockIdUrl) == 0){
				if(lockIdUrl.getId() > 0){
					lockIdUrlService.updateLockIdUrl(lockIdUrl);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "智能锁ID二维码信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
				}else{
					lockIdUrlService.insertLockIdUrl(lockIdUrl);
					return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "智能锁ID二维码信息新增"+ Globals.OPERA_SUCCESS_CODE_DESC);
				}
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "智能锁ID二维码信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新智能锁ID二维码数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新智能锁ID二维码数据" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 新增 智能锁ID二维码信息
	 * 
	 * @param lockIdUrl
	 * @param req
	 * @return
	 */
	@RequestMapping("lockIdUrl/insertLockIdUrl")
	@ResponseBody
	public APIContent insertLockIdUrl(@ModelAttribute LockIdUrl lockIdUrl, HttpServletRequest req) {
		try {
			if(lockIdUrlService.isExists(lockIdUrl) == 0){
				lockIdUrlService.insertLockIdUrl(lockIdUrl);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "智能锁ID二维码信息新增"+ Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "智能锁ID二维码信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新智能锁ID二维码数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 修改智能锁ID二维码信息
	 * 
	 * @param lockIdUrl
	 * @param req
	 * @return
	 */
	@RequestMapping("lockIdUrl/updateLockIdUrl")
	@ResponseBody
	public APIContent updateLockIdUrl(@ModelAttribute LockIdUrl lockIdUrl, HttpServletRequest req) {
		try {
			if(lockIdUrlService.isExists(lockIdUrl) == 0){
				lockIdUrlService.updateLockIdUrl(lockIdUrl);
				return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "智能锁ID二维码信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "智能锁ID二维码信息已存在,请检查" );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新智能锁ID二维码数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 删除智能锁ID二维码信息
	 * 
	 * @param lockIdUrl
	 * @param req
	 * @return
	 */
	@RequestMapping("lockIdUrl/deleteLockIdUrl")
	@ResponseBody
	public APIContent deleteLockIdUrl(@ModelAttribute LockIdUrl lockIdUrl, HttpServletRequest req) {
		try {
			lockIdUrlService.deleteLockIdUrl(lockIdUrl);
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "智能锁ID二维码信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			log.error("删除智能锁ID二维码数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 获取智能锁ID二维码详情
	 * 
	 * @param lockIdUrl
	 * @param req
	 * @return
	 */
	@RequestMapping("lockIdUrl/getLockIdUrl")
	@ResponseBody
	public APIContent getLockIdUrl(@ModelAttribute LockIdUrl lockIdUrl, HttpServletRequest req) {
		try {
			LockIdUrl z = lockIdUrlService.getLockIdUrl(lockIdUrl);
			return super.putAPIData(z);
		} catch (Exception e) {
			log.error("获取智能锁ID二维码数据失败:",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	@RequestMapping("lockIdUrl/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute("lockIdUrl") LockIdUrl lockIdUrl, HttpServletRequest request) {
		try {
			List<LockIdUrl> c = lockIdUrlService.getLockIdUrlList(lockIdUrl);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取所有智能锁ID二维码信息列表失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取智能锁ID二维码信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("lockIdUrl/matchLongStr")
	@ResponseBody
	public APIContent matchLongStr(@RequestParam("urlPrefix") String urlPrefix, HttpServletRequest request) {
		try {
			LockIdUrl c = lockIdUrlService.matchLongStr(urlPrefix);
			if(null != c){
				return super.putAPIData(c);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取智能锁ID二维码信息列表" + Globals.OPERA_FAIL_CODE_DESC);	
			}
		} catch (Exception e) {
			log.error("获取所有智能锁ID二维码信息列表失败",e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取智能锁ID二维码信息列表" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
