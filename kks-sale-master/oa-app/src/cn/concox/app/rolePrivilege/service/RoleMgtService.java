package cn.concox.app.rolePrivilege.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.common.page.Page;
import cn.concox.app.rolePrivilege.mapper.RoleMenusMapper;
import cn.concox.app.rolePrivilege.mapper.RoleMgtMapper;
import cn.concox.vo.rolePrivilege.FunctionDetails;
import cn.concox.vo.rolePrivilege.MenusFunction;
import cn.concox.vo.rolePrivilege.RoleMenus;
import cn.concox.vo.rolePrivilege.RoleMgt;

@Service("roleMgtService")
@Scope("prototype")
public class RoleMgtService {

	@Resource(name="roleMgtMapper")
	private RoleMgtMapper<RoleMgt> roleMgtMapper;
	
	@Resource(name="roleMenusMapper")
	private RoleMenusMapper<RoleMenus> roleMenusMapper;
	
	//RoleMgt操作
	public Page<RoleMgt> getRoleListByRoleMgtBean(RoleMgt roleMgt,int currentPage,int pageSize){
		Page<RoleMgt> roleListPage = new Page<RoleMgt>();
		roleListPage.setCurrentPage(currentPage);
		roleListPage.setSize(pageSize);
		roleListPage = roleMgtMapper.queryRoleListByNamePage(roleListPage, roleMgt);
		return roleListPage;
	} 
	
	public List<RoleMgt> getAllUserRoleList(){
		List<RoleMgt> allUserRoleList =  new ArrayList<RoleMgt>();
		return roleMgtMapper.getAllUserRoleList();
		
	}
	
	public void updateRoleInfo(RoleMgt roleMgt){
		roleMgtMapper.update(roleMgt);
	} 
	
	public void deleteRoleInfo(RoleMgt roleMgt){
		roleMgtMapper.delete(roleMgt);
	} 
	
	public List<MenusFunction> getAllMenuFunc(){
		List<MenusFunction> menuFuncList = new ArrayList<MenusFunction>();
		menuFuncList = roleMgtMapper.getAllMenuFunc();
		return menuFuncList;
	}
	
	public List<FunctionDetails> queryFuncListByMenuId(Integer menuId){
		List<FunctionDetails> fd = new ArrayList<FunctionDetails>();
		fd = roleMgtMapper.queryFuncListByMenuId(menuId);
		return fd;
	}
	
	public Integer getAutoIncreateId(RoleMgt roleMgt){
		roleMgtMapper.insert(roleMgt);
		Integer getId=roleMgt.getRolerId();
		return getId;
			
	}
	
	/**
	 * 根据角色名称查询角色
	 * @author tangyuping
	 * @param roleMgt
	 * @return
	 */
	public RoleMgt getRoleByName(RoleMgt roleMgt){
		RoleMgt role = roleMgtMapper.getRoleByName(roleMgt);
		if(role != null){			
			return role;
		}else{
			return null;
		}
	}
	
	//RoleMenus操作
	public void roleMenusSave(RoleMenus roleMenus){
		roleMenusMapper.insert(roleMenus);
	}
	
	public void deleRoleMenus(Integer rolerId){
		roleMenusMapper.deleRoleMenus(rolerId);
	}
	
	public RoleMgt getRolerInfosByRolerId(Integer rolerId){
		return roleMenusMapper.getRolerInfosByRolerId(rolerId);
	}
	
}
