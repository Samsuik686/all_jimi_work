package cn.concox.app.system.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.system.Menus;

public interface MenusMapper<T> extends BaseSqlMapper<T>{
	
	public Menus queryMenuById(Integer menuId) throws DataAccessException;
	public List<Menus> queryMenuByIdChildren(T entity) throws DataAccessException;
	public void deleteChildren(T entity) throws DataAccessException;
	
	public List<String> queryUserFunctionURLsByUId(Map uIdMap) throws DataAccessException;
	public List<Menus> queryParentMenuListByUId(Map uIdMap) throws DataAccessException;
	public List<Menus> queryChildMenusListByUIDAndParentMenuIds(Map menuIdsMap) throws DataAccessException;
	public List<String> queryAllFunctionURL() throws DataAccessException;
	public List<String> queryCommonFunctionURL() throws DataAccessException;
	
	
	
}
