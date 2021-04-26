package cn.concox.app.report.mapper;

import java.util.List;
import org.springframework.dao.DataAccessException;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.CollectFeesReport;

public interface CollectFeesReportMapper<T> extends BaseSqlMapper<T>{
	
	public List<CollectFeesReport> getCollectFeesYearReportList(CollectFeesReport entity) throws DataAccessException;
	
	public List<CollectFeesReport> getCollectFeesReportList(CollectFeesReport entity) throws DataAccessException;
}
