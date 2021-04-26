package cn.concox.app.report.mapper;

import java.util.List;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.FgzReport;

public interface FgzReportMapper<T> extends BaseSqlMapper<T> {
	
	public List<FgzReport> fenBreakdownList(FgzReport report); 
	
}
