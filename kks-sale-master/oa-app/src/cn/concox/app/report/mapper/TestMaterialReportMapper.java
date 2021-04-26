package cn.concox.app.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.TestMaterialReport;

public interface TestMaterialReportMapper<T> extends BaseSqlMapper<T>{
	@SuppressWarnings("rawtypes")
	public Page getTestMaterialReportList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	
	public List<TestMaterialReport> getTestMaterialReportList(@Param("po") T po) throws DataAccessException;
	
	@SuppressWarnings("rawtypes")
	public Page getTestMaterialReportRercentList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	
	public List<TestMaterialReport> getTestMaterialReportRercentList(@Param("po") T po) throws DataAccessException;
	
}
