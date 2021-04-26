package cn.concox.app.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sso.comm.model.Organizational;
import sso.comm.model.UserInfo;
import sso.comm.model.UserLogin;
import sso.comm.util.JsonUtils;
import sso.comm.util.SSOUtils;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;

@Controller
@Scope("prototype")
public class UserController extends BaseController {
        

	Logger log=Logger.getLogger(UserController.class);
	
	@RequestMapping("/user/OrganSelectList")
	@ResponseBody
	public String OrganSelectList(){
 
		SSOUtils utils=new SSOUtils();
		
		//servitce.addMenus();
		log.info("组织架构查询开始");
		try {
			String result=utils.getOrganizationalList("0");
			
		   return result;
		     
		} 
		catch (Exception e) {
			log.error("组织架构查询失败！");
			log.error(e.toString());
			return "{'code':201101,'msg':'参数错误'}";
			
		}
	}
	
	@RequestMapping("/user/OrganInserList")
	@ResponseBody
	public String OrganInserList(@ModelAttribute Organizational org){
 
		SSOUtils utils=new SSOUtils();
		
		//servitce.addMenus();
		log.info("组织架构增加开始");
		try {
			
			utils.addOrganizational(org);
			String result=utils.getOrganizationalList(org.getoParentId().toString());
		    return result;
		     
		} 
		catch (Exception e) {
			log.error("组织架构增加失败！");
			log.error(e.toString());
			return "{'code':201101,'msg':'参数错误'}";
			
		}
	}
	
	@RequestMapping("/user/OrganUpdate")
	@ResponseBody
	public String OrganUpdate(@ModelAttribute Organizational org){
 
		SSOUtils utils=new SSOUtils();
		
		//servitce.addMenus();
		log.info("组织架构修改开始");
		try {
			utils.updateOrganizational(org);
			String result=utils.getOrganizationalList(org.getoParentId().toString());
		    return result;
		} 
		catch (Exception e) {
			log.error("组织架构修改失败！");
			log.error(e.toString());
			return "{'code':201101,'msg':'参数错误'}";
			
		}
	}
	
	@RequestMapping("/user/OrganDelete")
	@ResponseBody
	public String OrganDelete(@RequestParam("oId") String oId){
 
		SSOUtils utils=new SSOUtils();
		
		//servitce.addMenus();
		log.info("组织架构删除开始");
		try {
			utils.removeOrganizational(oId);
			
		     return "true";
		} 
		catch (Exception e) {
			log.error("组织架构删除失败！");
			log.error(e.toString());
			return "{'code':201101,'msg':'参数错误'}";
			
		}
	}
	
	@RequestMapping("/user/UserListByOrgId")
	@ResponseBody
	public APIContent UserListByOrgId(@RequestParam("oId") String oId){
 
		SSOUtils utils=new SSOUtils();
		
		//servitce.addMenus();
		log.info("用户查询开始");
		try {
		  List<UserInfo> result=utils.getUserInfoListByOrgId(oId,Globals.USERSTATUS0);
		   return super.putAPIData(result);
		} 
		catch (Exception e) {
			log.error("用户查询失败！");
			log.error(e.toString());
			return super.putAPIData("{'code':201101,'msg':'参数错误'}");
			
		}
	}
	
	@RequestMapping("/user/SelectUidByUname")
	@ResponseBody
	public APIContent SelectUidByUname(@ModelAttribute UserInfo userInfo){
 
		SSOUtils utils=new SSOUtils();
		
		//servitce.addMenus();
		log.info("用户ID查询开始");
		try {
		  List<UserInfo> result=utils.getUserInfoList(userInfo);
		   return super.putAPIData(result);
		} 
		catch (Exception e) {
			log.error("用户ID查询失败！");
			log.error(e.toString());
			return super.putAPIData("{'code':201101,'msg':'参数错误'}");
			
		}
	}
	
	
	
	@RequestMapping("/user/OrganSelectByProID")
	@ResponseBody
	public String OrganSelect(@RequestParam("oId") String oId){
  
		SSOUtils utils=new SSOUtils();
		String result;
		//servitce.addMenus();
		log.info("组织架构BYID查询开始");
		try {
			if(oId==null||oId.equals("")) 
				result=utils.getOrganizationalList("0");    
			else result=utils.getOrganizationalList(oId);
			
		   return result;
		     
		} 
		catch (Exception e) {
			log.error("查询失败！");
			log.error(e.toString());
			return "{'code':201101,'msg':'参数错误'}";
			
		}
	}
	
	/**
	 * SSO文档：验证用户名密码，如果用户名存在且当前密码正确返回用户uId，否则null或""
	 * @param uId
	 * @param uPSD
	 * @return
	 */
	@RequestMapping("/user/validUserPSD")
	@ResponseBody
	public APIContent SelectUidByUname(@RequestParam("uId") String uId,@RequestParam("uPwd") String uPwd){
		 
		SSOUtils utils=new SSOUtils();
		
		//servitce.addMenus();
		log.info("验证用户账户开始");
		try {
			UserLogin userLogin=new UserLogin();
			userLogin.setuId(uId);
			userLogin.setuPwd(uPwd);
		    String getUId=utils.validUserLogin(userLogin);
		    if(StringUtils.isBlank(getUId)){
		    	return super.operaStatus(Globals.USERLOGINSTATUS,Globals.USERLOGINSTATUS_DESC);
		    }else {
		    	return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
		    }
		}catch (Exception e) {
			log.error("验证用户账户失败，异常："+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,"验证用户账户"+Globals.OPERA_FAIL_CODE_DESC);
			
		}
	}
	@RequestMapping(value="/user/repasswd",method=RequestMethod.POST)
	public String rePasswd(HttpServletRequest req,@RequestParam("uId") String uId) {
		SSOUtils ssoUtils=new SSOUtils();
		UserLogin login=new UserLogin();
		login.setuId(uId);
		login.setuPwd("1111");
		try {
			if(ssoUtils.updateUserPassWord(login)){
				 log.info("员工"+uId+"重置密码成功!");
				 req.setAttribute("msg", "密码重置成功，重置后信息   员工："+login.getuId()+" 密码：1111");
			}else {
				req.setAttribute("msg", "密码重置失败，请稍后再试!");
			}
		} catch (Exception e) {
				req.setAttribute("msg", "密码重置失败，请稍后再试!");
		}
		return "page/repasswd";
	}
	
	
}
