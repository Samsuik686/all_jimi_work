package cn.concox.app.report.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Cjgzmanage;
import cn.concox.vo.basicdata.Zgzmanage;
import cn.concox.vo.report.DateReport;
import cn.concox.vo.report.HwmReport;

public interface HwmReportMapper<T> extends BaseSqlMapper<T>{

	public List<DateReport> getReceiveList(DateReport dateReport);

	public List<DateReport> getDeliverList(DateReport dateReport);

	public List<HwmReport> getClassifyModelList(HwmReport report); 
	
	public Long getClassifyModelTotal(HwmReport report); 
	
	public List<HwmReport> getTimeoutAcceptList(HwmReport report);
	

	public List<DateReport> getReceiveYearList(DateReport dateReport); 
	
	public List<DateReport> getDeliverYearList(DateReport dateReport);
	
	public List<String> getImeiListForRepairDetail(HwmReport report);

	public List<Cjgzmanage> getCJGZList(String[] cjArrys);

	public List<Zgzmanage> getZZGZList(String[] zzArrys);
	
	@SuppressWarnings("rawtypes")
	public Page<HwmReport> getTimeoutAcceptListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po);

	@SuppressWarnings("rawtypes")
	public Page<HwmReport> getRepairDetailList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("imeis")String imeis, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	public List<HwmReport> getRepairDetailList(@Param("imeis")String imeis, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	@SuppressWarnings("rawtypes")
	public Page<HwmReport> getRepairDetailListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po);
	
	public List<HwmReport> getRepairDetailListPage(@Param("po") HwmReport report);

	public List<HwmReport> getAllTimeoutAcceptPage(@Param("po")HwmReport report,@Param("page")Page page);

	public List<HwmReport> getAllTimeoutAcceptList(@Param("po")HwmReport report);

	public List<String> selectTimeoutEngineer(@Param("po")HwmReport report);

	public List<Integer> selectTimeoutState(@Param("po")HwmReport report);

}
