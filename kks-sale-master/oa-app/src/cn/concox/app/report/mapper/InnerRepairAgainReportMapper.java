package cn.concox.app.report.mapper;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.InnerRepairAgainReport;

@SuppressWarnings("hiding")
public interface InnerRepairAgainReportMapper<T> extends BaseSqlMapper<T>{
	public Integer getCountRepair(InnerRepairAgainReport report) throws DataAccessException;;
	
	public List<InnerRepairAgainReport> getRepairAgainList(InnerRepairAgainReport report) throws DataAccessException;;

}
