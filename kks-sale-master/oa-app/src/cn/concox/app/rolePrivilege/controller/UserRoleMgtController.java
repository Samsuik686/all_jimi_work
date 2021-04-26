package cn.concox.app.rolePrivilege.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sso.comm.model.UserInfo;
import sso.comm.util.JsonUtils;
import cn.concox.app.common.page.Page;
import cn.concox.app.rolePrivilege.service.RoleMgtService;
import cn.concox.app.rolePrivilege.service.UserRoleMgtService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.ZtreeShow;
import cn.concox.vo.rolePrivilege.RoleMgt;
import cn.concox.vo.rolePrivilege.UserRoleMgt;

@Controller
@Scope("prototype")
public class UserRoleMgtController extends BaseController {
	
	Logger roleLog=Logger.getLogger("privilege");
	
	@Resource(name="userRoleMgtService")
	private UserRoleMgtService userRoleMgtService;
	
	@Resource(name="roleMgtService")
	private RoleMgtService roleMgtService;
	
	/**
	 * 用户角色列表分页
	 * @param userRoleMgt
	 * @param req
	 * @return
	 */
	@RequestMapping("rolePrivilege/userRoleList")
	@ResponseBody
	public APIContent getRoleListPage(@ModelAttribute UserRoleMgt userRoleMgt,HttpServletRequest req){
		roleLog.info("用户角色列表开始查询");
		String getUId=userRoleMgt.getuId();
		try{
			int getCurrentPage=1;
			if(!StringUtils.isBlank(req.getParameter("currentpage"))){
				getCurrentPage=Integer.parseInt(req.getParameter("currentpage"));
			}
			Page<UserRoleMgt> roleListPage;
			if(StringUtils.isBlank(getUId)){
				roleListPage=userRoleMgtService.getRoleList(userRoleMgt, getCurrentPage);
			}else {
				roleListPage=userRoleMgtService.getUserRoleListByUId(userRoleMgt,getCurrentPage);
			}
			req.getSession().setAttribute("totalValue", roleListPage.getTotal());
			return super.putAPIData(roleListPage.getResult());
			
		}catch(Exception e){
			e.printStackTrace();
			roleLog.error("用户角色列表查询失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("rolePrivilege/allUserRoleList")
	@ResponseBody
	public APIContent getAllRoleListInfo(HttpServletRequest req){
		roleLog.info("所有角色列表开始查询");
		try{
			List<RoleMgt> allUserRoleList=userRoleMgtService.getAllUserRoleList();
			List datas = new ArrayList();
			for (int i = 0; i < allUserRoleList.size(); i++) {
				RoleMgt r = allUserRoleList.get(i);
				ZtreeShow obj = new ZtreeShow();
				obj.setId(r.getRolerId().toString());
				obj.setText(r.getRolerName());
				datas.add(obj);
			}
			
			String jsonStr = JsonUtils.beanToJson(datas);
			return super.putAPIData(allUserRoleList);
		}catch(Exception e){
			e.printStackTrace();
			roleLog.error("所有角色列表查询失败:"+e.toString());
			return super.putAPIData(false);
		}
	}
	
	
	
	@RequestMapping("rolePrivilege/userRoleAdd")
	@ResponseBody
	public APIContent userRoleAdd(@RequestParam("userId") String userId,@RequestParam("rolerId") String rolerId){
		roleLog.info("当前用户角色设置开始");
		try{
			
			Map userRoleAddMap=new HashMap();
			//删除用户对应的所有角色ID，表oa_user_roler
			userRoleAddMap.put("userId", userId);
			userRoleMgtService.deleteUserRoleInfo(userRoleAddMap);
			
			String[] getRolerId=rolerId.split(",");
			for(String rolerId_p:getRolerId){
				userRoleAddMap.put("rolerId", rolerId_p);
				userRoleMgtService.userRoleAdd(userRoleAddMap);
			}
				
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"用户角色设置"+Globals.OPERA_SUCCESS_CODE_DESC);
			
		}catch(Exception e){
			e.printStackTrace();
			roleLog.error("当前用户角色设置失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	
	@RequestMapping("rolePrivilege/userRoleDelete")
	@ResponseBody
	public APIContent deleteUserRoleInfo(@RequestParam("userId") String userId,@RequestParam("rolerId") Integer rolerId){
		roleLog.info("用户角色信息删除开始");
		try{
			Map userRoleDeleteMap=new HashMap();
			userRoleDeleteMap.put("userId", userId);
			userRoleDeleteMap.put("rolerId", rolerId);
			userRoleMgtService.deleteUserRoleInfo(userRoleDeleteMap);//主表oa_user_roler
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"用户角色信息删除"+Globals.OPERA_SUCCESS_CODE_DESC);
		}catch(Exception e){
			roleLog.error("用户角色信息删除失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	@RequestMapping("rolePrivilege/getUserRoleIdListByUId")
	@ResponseBody
	public APIContent getUserRoleIdListByUId(@RequestParam("userId") String userId){
		roleLog.info("用户角色ID列表开始查询");
		try{
			List<String> userRoleIdList=userRoleMgtService.getUserRoleIdListByUId(userId);
			return super.putAPIData(userRoleIdList);
		}catch(Exception e){
			e.printStackTrace();
			roleLog.error("用户角色ID列表查询失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
	
	/**
	 * 后台获取当前登录人角色
	 * @author TangYuping
	 * @version 2017年1月16日 下午6:06:45
	 * @param req
	 * @return
	 */
	@RequestMapping("rolePrivilege/getCurrentUserRoleId")
	@ResponseBody
	public APIContent getUserRoleIdListByUId(HttpServletRequest req){
		try{
			UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
			if(user != null ){
				String userId = user.getuId();
				List<String> userRoleIdList=userRoleMgtService.getUserRoleIdListByUId(userId);
				return super.putAPIData(userRoleIdList);
			}else{
				return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
			}
			
		}catch(Exception e){
			e.printStackTrace();			
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}
