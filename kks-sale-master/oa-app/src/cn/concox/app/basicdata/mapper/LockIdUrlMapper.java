package cn.concox.app.basicdata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.LockIdUrl;

/**
 * <pre>
 * LockIdUrlMapper 数据访问接口
 * </pre>
 */
public interface LockIdUrlMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page getLockIdUrlList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException;
	
	public List<LockIdUrl> getLockIdUrlList(@Param("po") T po) throws  DataAccessException; 
	
	public LockIdUrl matchLongStr(@Param("urlPrefix") String urlPrefix);
}