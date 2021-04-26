package cn.concox.app.basicdata.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.GroupPj;
/**
 * 销售配件料组合管理 Mappper
 * @param <T>
 */
public interface GroupPjMapper<T> extends BaseSqlMapper<T>{

	@SuppressWarnings("rawtypes")
	public Page getGroupPjList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	
	public List<GroupPj> getPjlmanageList(@Param("po") T po) throws  DataAccessException;

	public int findCountByGroupPjId(GroupPj groupPj);
}
