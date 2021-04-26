package cn.concox.app.material.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;

/**
 * <pre>
 * StoresManageMapper 数据访问接口
 * </pre>
 * @author Li.Bifeng 
 * @version v1.0
 */
public interface StoresManageMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page queryStoresManageListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
}
