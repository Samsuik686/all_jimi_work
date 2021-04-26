package cn.concox.app.rolePrivilege.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.common.page.Page;
import cn.concox.app.rolePrivilege.mapper.RoleMenusMapper;
import cn.concox.app.rolePrivilege.mapper.RoleMgtMapper;
import cn.concox.app.rolePrivilege.mapper.UserRoleMgtMapper;
import cn.concox.vo.rolePrivilege.FunctionDetails;
import cn.concox.vo.rolePrivilege.MenusFunction;
import cn.concox.vo.rolePrivilege.RoleMenus;
import cn.concox.vo.rolePrivilege.RoleMgt;
import cn.concox.vo.rolePrivilege.UserRoleMgt;

@Service("userRoleMgtService")
@Scope("prototype")
public class UserRoleMgtService {

	@Resource(name="userRoleMgtMapper")
	private UserRoleMgtMapper<UserRoleMgt> userRoleMgtMapper;
	
	@Resource(name="roleMgtService")
	private RoleMgtService roleMgtService;
	
	//RoleMgt操作
	public Page<UserRoleMgt> getUserRoleListByUId(UserRoleMgt userRoleMgt,int currentPage){
		Page<UserRoleMgt> userRoleListPage = new Page<UserRoleMgt>();
		userRoleListPage.setCurrentPage(currentPage);
		userRoleListPage = userRoleMgtMapper.getUserRoleListByUId(userRoleListPage,userRoleMgt);
		return userRoleListPage;
	} 
	
	public Page<UserRoleMgt> getRoleList(UserRoleMgt userRoleMgt,int currentPage){
		Page<UserRoleMgt> userRoleListPage = new Page<UserRoleMgt>();
		userRoleListPage.setCurrentPage(currentPage);
		userRoleListPage = userRoleMgtMapper.getRoleList(userRoleListPage,userRoleMgt);
		return userRoleListPage;
	}
	
	
	
	public List<RoleMgt> getAllUserRoleList(){
		List<RoleMgt> allUserRoleList =  new ArrayList<RoleMgt>();
		allUserRoleList=roleMgtService.getAllUserRoleList();
		return allUserRoleList;
	}
	
	public void userRoleAdd(Map userRoleAddMap){
		userRoleMgtMapper.userRoleAdd(userRoleAddMap);
	}
	
	public Integer getUserRoleIsExists(Map userRoleAddMap){
		return userRoleMgtMapper.getUserRoleIsExists(userRoleAddMap);
	}
	
	public void deleteUserRoleInfo(Map userRoleDeleteMap){
		userRoleMgtMapper.deleteUserRoleInfo(userRoleDeleteMap);
	}
	
	public List<String> getUserRoleIdListByUId(String userId){
		return userRoleMgtMapper.getUserRoleIdListByUId(userId);
	}
	/**
	 * 
	 * @param newUserRoleAddMap,其中rolerId参数固定传int类型，值为44
	 * 如userId="xiangwang",creater="hewugang",rolerId=44
	 * @return
	 */
	@Transactional
	public void newUserDefaultRoleAdd(Map newUserRoleAddMap){
		userRoleMgtMapper.deleteUserRoleInfo(newUserRoleAddMap);
		userRoleMgtMapper.userRoleAdd(newUserRoleAddMap);
	}
	
	/**
	 * @Title: getRoleIdCount 
	 * @Description:删除角色时判断该角色是否已分配给用户
	 * @param rolerId
	 * @return 
	 * @author HuangGangQiang
	 * @date 2017年8月7日 下午1:58:55
	 */
	 public int getRoleIdCount(Integer rolerId){
		 return userRoleMgtMapper.getRoleIdCount(rolerId);
	 }
}
