package cn.concox.app.rolePrivilege.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.rolePrivilege.FunctionDetails;
import cn.concox.vo.rolePrivilege.MenusFunction;
import cn.concox.vo.rolePrivilege.RoleMgt;

public interface RoleMgtMapper<T> extends BaseSqlMapper<T> {

	 public Long queryRoleListByNameCount() throws  DataAccessException; 
     
	 public Page queryRoleListByNamePage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	 
	 public List<MenusFunction> getAllMenuFunc() throws  DataAccessException;
	 
	 public List<FunctionDetails> queryFuncListByMenuId(Integer menuId) throws  DataAccessException;
	 
	 public List<RoleMgt> getAllUserRoleList() throws  DataAccessException;
	 
	 /**
	  * 根据名字获取角色做角色唯一判断
	  */
	 public RoleMgt getRoleByName(RoleMgt role) throws DataAccessException;
	 
	 
}
