package cn.concox.app.report.mapper;

import java.util.List;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.FgzReport;

public interface TopgzReportMapper<T> extends BaseSqlMapper<T> {
	
	public List<FgzReport> everyBreakdownList(FgzReport report);
	
	List<FgzReport> notSendEveryBreakdownList(FgzReport report);
	
	public List<FgzReport> getAllOfModelsList(FgzReport report);

}
