package cn.concox.app.rolePrivilege.mapper;

import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import sso.comm.model.UserInfo;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.rolePrivilege.FunctionDetails;
import cn.concox.vo.rolePrivilege.MenusFunction;
import cn.concox.vo.rolePrivilege.RoleMgt;
import cn.concox.vo.rolePrivilege.UserRoleMgt;

public interface UserRoleMgtMapper<T> extends BaseSqlMapper<T> {

	 public Long queryUserRoleListByUIdCount() throws DataAccessException; 
	
	 public Page getUserRoleListByUId(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	 
	 public Long getRoleListCount() throws DataAccessException; 
		
	 public Page getRoleList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	 
	 public void userRoleAdd(Map userRoleAddMap) throws DataAccessException;
	 
	 public Integer getUserRoleIsExists(Map userRoleAddMap) throws DataAccessException;
	 
	 public void deleteUserRoleInfo(Map userRoleDeleteMap) throws DataAccessException;
	 
	 public void deleteUserRoleInfoByRolerId(Map userRoleDeleteMap) throws DataAccessException;
	 
	 public List<String> getUserRoleIdListByUId(String userId) throws DataAccessException;
	 
	 public List<UserRoleMgt> findUserRoleByRoleId(Integer id) throws   DataAccessException;
	 
	 /**
	  * @Title: getRoleIdCount 
	  * @Description:查询角色是否被用户引用
	  * @param rolerId
	  * @return 
	  * @author HuangGangQiang
	  * @date 2017年8月7日 下午2:21:33
	  */
	 public int getRoleIdCount(@Param("rolerId") Integer rolerId);

	public List<String> getUserIdByRoleId(@Param("list")List<Integer> roleIds);
	 
}
