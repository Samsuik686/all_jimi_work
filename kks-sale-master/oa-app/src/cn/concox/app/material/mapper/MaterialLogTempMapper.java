package cn.concox.app.material.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.material.MaterialLogTemp;

/**
 * <pre>
 * MaterialLogTempMapper 数据访问接口
 * </pre>
 */
public interface MaterialLogTempMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page getMaterialLogTempList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 
	
	public List<MaterialLogTemp> getMaterialLogTempList(@Param("po") T po) throws  DataAccessException; 

	public void deleteByIds(String[] ids);
}
