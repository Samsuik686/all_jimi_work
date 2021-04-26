package cn.concox.app.material.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;

public interface ChangeboardMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page getListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	
	public int getCountByWfId(@Param("wfId") Integer wfId);
}
