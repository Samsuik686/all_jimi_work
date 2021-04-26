package cn.concox.app.report.mapper;

import java.util.List;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.ZgzReport;

public interface ZgzReportMapper<T> extends BaseSqlMapper<T>{
	
	public List<ZgzReport> getBreakdownList(ZgzReport report); 
	
	
	public List<ZgzReport> getBreakdownChart(ZgzReport report); 

}
