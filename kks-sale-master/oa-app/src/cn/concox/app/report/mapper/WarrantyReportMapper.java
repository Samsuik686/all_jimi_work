package cn.concox.app.report.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.WarrantyReport;

public interface WarrantyReportMapper<T> extends BaseSqlMapper<T>{
	@SuppressWarnings("rawtypes")
	public Page getWarrantyReportPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	
	public List<WarrantyReport> getWarrantyReportList(WarrantyReport entity) throws DataAccessException;
	
	public String getFaultName(String... ids);
}
