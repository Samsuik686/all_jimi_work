package cn.concox.app.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;

public interface PhoneRechargeReportMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page getPhoneRechargeListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;
	
	public List<T> querySum(T entity) throws DataAccessException;
	
	public T queryTotal(T entity) throws DataAccessException;
}
