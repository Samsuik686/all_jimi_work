package cn.concox.app.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.CallbackReport;

public interface CallbackReportMapper<T> extends BaseSqlMapper<T> {

	public Page<CallbackReport> getCallbackListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po);
	
	public List<CallbackReport> getCallbackList(CallbackReport callbackReport);
}
