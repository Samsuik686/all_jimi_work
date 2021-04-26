package cn.concox.app.user.controller.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import cn.concox.app.rolePrivilege.mapper.RoleMenusMapper;
import cn.concox.app.rolePrivilege.mapper.UserRoleMgtMapper;
import cn.concox.app.user.mapper.UserInfoMapper;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 

import sso.comm.model.UserInfo;
import sso.comm.model.UserOrganizational;
import sso.comm.util.SSOUtils;

import javax.annotation.Resource;


@Service("userService")
@Scope("prototype")
public class UserService {
	Logger log=Logger.getLogger(UserService.class);

	@Resource(name="roleMenusMapper")
	RoleMenusMapper roleMenusMapper;
	@Resource(name="userRoleMgtMapper")
	UserRoleMgtMapper userRoleMgtMapper;
	@Resource(name = "userInfoMapper")
	private UserInfoMapper userInfoMapper;

	@Transactional(propagation=Propagation.REQUIRED) 
	public void UserUpdate(UserInfo ui, String omainId
			,String osecondId
			,String JoinDateTime
			,String PositiveDateTime
			,String Birthday
			,String ContractStart
			,String ContractEnd
			,String JumpDate){
		
		
		SSOUtils utils=new SSOUtils();
		Date joinDateTime=new Date();
		Date positiveDateTime=new Date();
		Date birthday=new Date();
		Date contractStart=new Date();
		Date contractEnd=new Date();
		Date jumpDate=new Date();
		List<UserOrganizational> luorg=new ArrayList<UserOrganizational>();
		//servitce.addMenus(); 
		log.info("员工信息修改开始");
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
			if(JoinDateTime!="")
				joinDateTime=sdf.parse(JoinDateTime);
			if(PositiveDateTime!="")
			    positiveDateTime=sdf.parse(PositiveDateTime);
			if(Birthday!="")
		     birthday=sdf.parse(Birthday);
			if(ContractStart!="")
			 contractStart=sdf.parse(ContractStart);
			if(ContractEnd!="")
			 contractEnd=sdf.parse(ContractEnd);
			  
			
			if(JumpDate!=""){
				jumpDate=sdf.parse(JumpDate);
				if(ui.getuStatus()==1)
					ui.setuJumpDate(jumpDate);
			}
		          
			
			ui.setuBirthday(birthday);
			ui.setuJoinDateTime(joinDateTime);
			ui.setuPositiveDateTime(positiveDateTime);
			ui.setuContractEnd(contractEnd);
			ui.setuContractStart(contractStart);
			
			utils.updateUserInfo(ui);
			
			/**
			 * 用户组织架构插入
			 */
			if(omainId!="")
			{

				UserOrganizational uorg=new UserOrganizational();
			uorg.setuId(ui.getuId());
			uorg.setoId(Integer.valueOf(omainId));
			uorg.setmDpt(1);
			luorg.add(uorg);
			
			}
			if(osecondId!="")
			{ 
				String[] osecondIdarray=osecondId.split(","); 
		      for (int i = 0; i < osecondIdarray.length; i++) 
		      {
		  		UserOrganizational uorg=new UserOrganizational();
		    	    uorg.setuId(ui.getuId());
					uorg.setoId(Integer.valueOf(osecondIdarray[i]));
					uorg.setmDpt(0);
					luorg.add(uorg);
		      }
		    }
			if(omainId!=""||osecondId!="")
			{
			utils.updateOrInsertUserOrganizationals(ui.getuId(), luorg);
			}
 
		} 
		catch (Exception e) {
			log.error("员工信息修改失败！");
			log.error(e.toString());
		}
	}

	/**
	 * 获取具有权限的用户
	 * @param menuId 权限id
	 * @return
	 */
	public List<UserInfo> getUserListByMenu(Integer menuId) {
		List<Integer> roleIds = roleMenusMapper.getRolerInfosByMenuId(menuId);
		List<String> userIds =  userRoleMgtMapper.getUserIdByRoleId(roleIds);
		SSOUtils ssoUtils = new SSOUtils();
		List<UserInfo> userInfos = new ArrayList<>();
		try {
			for (String id : userIds) {
				userInfos.add(ssoUtils.getUserInfo(id));
			}
		}catch (Exception e){
			log.error("获取用户信息失败",e);
		}
		return userInfos;
	}

	public String getMailByUname(String uname){
		return  userInfoMapper.selectMailByUname(uname);
	}
}
