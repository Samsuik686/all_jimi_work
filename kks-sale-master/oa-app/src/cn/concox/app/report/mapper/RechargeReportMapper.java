package cn.concox.app.report.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;

public interface RechargeReportMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page getRechargeListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
}
