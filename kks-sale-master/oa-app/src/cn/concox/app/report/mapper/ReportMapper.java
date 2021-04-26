package cn.concox.app.report.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.RepairAgainDetail;
import cn.concox.vo.report.Report;

public interface ReportMapper<T> extends BaseSqlMapper<T>{

	public List<Report> getAccqueteList(Report report) throws DataAccessException;; 
	
	public Integer getCountRepair(Report report) throws DataAccessException;;
	
	public List<Report> getRepairAgainList(Report report) throws DataAccessException;
	
	public Page<RepairAgainDetail> getRepairAgainDetailListPage(@Param(PageInterceptor.PAGE_KEY) Page<RepairAgainDetail> page, @Param("report")RepairAgainDetail report);
	
	public List<RepairAgainDetail> getRepairAgainDetailList(RepairAgainDetail report);

	public Integer getGiveUpRepairCount(Report report) throws DataAccessException;
	
	public Integer countTwiceRepair(Report report) throws DataAccessException;

}
