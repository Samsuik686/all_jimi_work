package cn.concox.app.basicdata.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Gzbjmanage;

public interface GzbjmanageMapper<T> extends BaseSqlMapper<T>{
	
	@SuppressWarnings("rawtypes")
	public Page getGzbjmanageList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	
	public List<Gzbjmanage> getGzbjmanageList(@Param("po") T po) throws DataAccessException;
	
	public BigDecimal getSumPriceByIds(String... ids);
	
	public String getGRoupConcat(String... ids);
	
	public int findCountByGzbj(Gzbjmanage gzbjmanage);
}
