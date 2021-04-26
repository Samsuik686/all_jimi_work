package cn.concox.app.rolePrivilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.rolePrivilege.RoleMgt;


public interface RoleMenusMapper<T> extends BaseSqlMapper<T> {

	public void deleRoleMenus(Integer rolerId) throws   DataAccessException;
	
	public RoleMgt getRolerInfosByRolerId(Integer rolerId) throws   DataAccessException;
	
	public List<Integer> getRolerInfosByMenuId(Integer menuId) throws   DataAccessException;

	public List<Integer> getRolerInfosByMenuIds(@Param("list") List<Integer> menuIds) throws DataAccessException;
}
