package cn.concox.app.basicdata.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Pjlmanage;
/**
 * 试流料 Mappper
 * @param <T>
 */
public interface TestMaterialMapper<T> extends BaseSqlMapper<T>{

	@SuppressWarnings("rawtypes")
	public Page getTestMaterialList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 

	public List<Pjlmanage> getTestMaterialList(@Param("po") T po) throws  DataAccessException;
	
	public String getGRoupConcat(String... ids);
}
