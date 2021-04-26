/*
 * Created: 2016-08-19
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.app.report.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.app.basicdata.service.WorkDateService;
import cn.concox.comm.util.DateUtil;
import cn.concox.vo.basicdata.*;
import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.app.report.mapper.HwmReportMapper;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.util.JavaNetURLRESTFulClient;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.report.DateReport;
import cn.concox.vo.report.HwmReport;
import cn.concox.vo.workflow.Workflow;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import net.sf.json.JSONObject;

@Service("hwmReportService")
@Scope("prototype")
public class HwmReportService {

	@Resource(name = "hwmReportMapper")
	private HwmReportMapper<HwmReport> reportMapper;
	
	@Resource(name = "workflowMapper")
	private WorkflowMapper<Workflow> workflowMapper;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "workDateService")
	private WorkDateService workDateService;
	Logger logger = LoggerFactory.getLogger(HwmReportService.class);
	/**
	 * 收件月报表数据源
	 * 
	 * @param dateReport
	 *            报表对象
	 * @return List<DateReport> 数据集合
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public List<DateReport> getReceiveList(DateReport dateReport)
			throws IllegalArgumentException, IllegalAccessException {
		List<DateReport> nativeList = reportMapper.getReceiveList(dateReport);
		packagingList(nativeList, "date");
		nativeList.add(getMonthDr(nativeList));
		return nativeList;
	}

	/**
	 * 数据再次封装成31天格式
	 * 
	 * @param dateList
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private List<DateReport> packagingList(List<DateReport> dateList,
			String fieldName) throws IllegalArgumentException,
			IllegalAccessException {
		if (null != dateList && dateList.size() > 0) {
			for (DateReport report : dateList) {
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
				if (report.getDaysNum().contains(",")) {
					String[] arry = StringUtil.split(report.getDaysNum(), ",");
					for (String s : arry) {
						String[] date = StringUtil.split(s, "-");
						map.put(Integer.parseInt(date[0]),
								Integer.parseInt(date[1]));
					}
				} else {
					String[] date = StringUtil.split(report.getDaysNum(), "-");
					map.put(Integer.parseInt(date[0]),
							Integer.parseInt(date[1]));
				}
				Field[] fields = report.getClass().getDeclaredFields();
				for (Field f : fields) {
					f.setAccessible(true);
					if (f.getName().contains(fieldName)) {
						if (StringUtil.isDigit(f.getName().substring(fieldName.length(),
								f.getName().length()))) {
							Integer val = map.get(Integer.parseInt(f.getName()
									.substring(fieldName.length(), f.getName().length())));
							if (null != val) {
								f.set(report, val);
							}
						}
					}

				}
			}
		}
		return dateList;
	}

	/**
	 * 收件月报表-导出
	 * @param dateReport
	 * @param request
	 * @param response
	 * @throws ParseException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void receiveExport(DateReport dateReport,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException, IOException, IllegalArgumentException,
			IllegalAccessException {
		List<DateReport> list = getReceiveList(dateReport);
		String[] fieldNames = new String[] {"主板型号", "1日", "2 日", "3日", "4日", "5日",
				"6日", "7日", "8日", "9日", "10日", "11日", "12日", "13日", "14日", "15日", "16日",
				"17日", "18日", "19日", "20日", "21日", "22日", "23日", "24日", "25日", "26日",
				"27日", "28日", "29日", "30日", "31日", "收件月统计" };
		Map map = new HashMap();
		map.put("size", list.size() + 2);
		map.put("peList", list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("收件月报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath("freemarker"))
				.append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("ReceiveReport.ftl").toString();

		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,
				template, map);
	}

	/**
	 * 寄件月报表数据源
	 * @param dateReport
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<DateReport> getDeliverList(DateReport dateReport)
			throws IllegalArgumentException, IllegalAccessException {
		List<DateReport> nativeList = reportMapper.getDeliverList(dateReport);
		packagingList(nativeList, "date");
		nativeList.add(getMonthDr(nativeList));
		return nativeList;
	}

	/**
	 * 寄件月报表-导出
	 * @param dateReport
	 * @param request
	 * @param response
	 * @throws ParseException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void deliverExport(DateReport dateReport,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException, IOException, IllegalArgumentException,
			IllegalAccessException {
		List<DateReport> list = getDeliverList(dateReport);
		String[] fieldNames = new String[] {"主板型号", "1日", "2 日", "3日", "4日", "5日",
				"6日", "7日", "8日", "9日", "10日", "11日", "12日", "13日", "14日", "15日", "16日",
				"17日", "18日", "19日", "20日", "21日", "22日", "23日", "24日", "25日", "26日",
				"27日", "28日", "29日", "30日", "31日", "寄件月统计" };
		Map map = new HashMap();
		map.put("size", list.size() + 2);
		map.put("peList", list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("寄件月报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath("freemarker"))
				.append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("DeliverReport.ftl").toString();

		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,
				template, map);
	}

	/**
	 * 机型分类报表-数据源
	 * @param report
	 * @return
	 */
	public List<HwmReport> getClassifyModelList(HwmReport report) {
		List<HwmReport> result = new ArrayList<HwmReport>();
		HwmReport reportCount=new HwmReport();
		Long count = 0L;
		Long totalNum = reportMapper.getClassifyModelTotal(report);
		if (null != totalNum && totalNum > 0) {
			result = reportMapper.getClassifyModelList(report);
			NumberFormat formater = new DecimalFormat("#.##");
			for (HwmReport r : result) {
				BigDecimal ratio = new BigDecimal(r.getUsage()).divide(
						new BigDecimal(totalNum), 4, RoundingMode.FLOOR)
						.multiply(new BigDecimal(100));
				r.setRatio(formater.format(ratio.doubleValue()) + "%");
				count += r.getUsage();
			}
		}
		reportCount.setModel("总计");
		reportCount.setUsage(count);
		result.add(reportCount);
		return result;
	}

	/**
	 * 机型分类报表-导出
	 * @param report
	 * @param req
	 * @param reps
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void classifyModelExport(HwmReport report, HttpServletRequest req,
			HttpServletResponse reps) throws ParseException, IOException {
		String isWarry = report.getIsWarranty();
		String strWarry = "";
		if(isWarry.equals("0")){
			strWarry = "保内";
		}else if(isWarry.equals("1")){
			strWarry = "保外";
		}
		String startTime = report.getStartTime(),
				endTime = report.getEndTime();
		StringBuilder dispaly = new StringBuilder();
		boolean stNull = (startTime == null || "".equals(startTime)),etNull = (endTime == null || "".equals(endTime));
		if(!stNull && !etNull){
			dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		if(stNull && !etNull){
			 dispaly.append("(--").append(endTime).append(")");
		}
		if(!stNull && etNull){
			 Date date = new Date();
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");       
		     endTime = sdf.format(date).toString();
		     dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		List<HwmReport> list = getClassifyModelList(report);
		String[] fieldNames = new String[] {"主板型号", "数量", "百分比" };
		Map map = new HashMap();
		map.put("dispalyDate", dispaly);
		map.put("isWarry", strWarry);
		map.put("size", null !=list?list.size() + 2:0);
		map.put("peList", list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder(strWarry+"机型分类报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(req.getRealPath("freemarker"))
				.append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("ClassifyReport.ftl").toString();

		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(req, reps, exportFile, fileName, template, map);
	}

	/**
	 * 超时机器未寄出报表数据源
	 * @param report
	 * @return
	 */
	public Page<HwmReport> getTimeoutAcceptListPage(HwmReport report,Integer currentpage, Integer pageSize) {
		Page<HwmReport> page = new Page<HwmReport>();
		page.setCurrentPage(currentpage);
		page.setSize(pageSize);
		page = reportMapper.getTimeoutAcceptListPage(page,report);
		setTimeOutContent(page.getResult());
		return page;
	}

	/**
	 * 超时未寄出报表数据
	 * @param report
	 * @return
	 */
	public Page<HwmReport> getAllTimeoutAcceptPage(HwmReport report,Page<HwmReport> page){
		reportMapper.getAllTimeoutAcceptPage(report,page);
		return page;
	}

	public List<HwmReport> getAllTimeoutList(HwmReport report){
		return reportMapper.getAllTimeoutAcceptList(report);
	}

	/**
	 * 自动备注超期原因
	 * @param reports
	 */
	public void autoTimeoutReason(List<HwmReport> reports){
		for(HwmReport report:reports){
			//已经有超期原因的直接跳过
			if(report.getTimeoutReason() != null && !StringUtil.isEmpty(report.getTimeoutReason())){
				continue;
			}
			//
		}
	}

	/**
	 * 应返还时间规则，超过应返回时间则算超期
	 *  受理        终检时间
	 * 周一上午     周三下午
	 * 周一下午     周四下午
	 * 周二上午     周四下午
	 * 周二下午     周五下午
	 * 周三上午	   周六上午
	 * 周三下午     周一下午
	 * 周四上午     周一下午
	 * 周四下午     周一下午
	 * 周五上午     周二下午
	 * 周五下午     周二下午
	 * 周六上午     周三下午
	 *
	 * 筛选超时未寄出数据
	 * 前提：
	 * 1.放假形式为连续的下午+N天，或者来连续N天，不会出现其他的放假情况
	 * 2.一次放假后距离下一次放假至少三天。
	 * 3.非工作时间不受理维修设备
	 * 4.超期时间超过15天可能不正确
	 * 否则以下规则会出问题。
	 * 半天假前的第三个工作日按周三处理，第二个工作日按周四处理，第一个中昨日按周五处理，放假当天按周六处理，
	 * 寄出时间按照规则顺推，多放一天假就多往后推一天
	 * 例如：
	 * 周一、二、三工作、周四上午工作，周四下午和周五周六放假，周日工作、下周的周一工作…
	 * 那么这周的周一按正常的周三处理，周二按周四处理、周三按周五处理，周四按周六处理。
	 *
	 *
	 * @param list
	 * @return
	 */
	public List<HwmReport> fiterTimeoutEqp(List<HwmReport> list) throws java.text.ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//超期的维修设备
		List<HwmReport> timeoutList = new ArrayList<>();

		//当前时间
		Calendar now = Calendar.getInstance();
		//当前时间是上午还是下午，0表示上午，1表示下午
		int nowTimeSlot = now.get(Calendar.HOUR_OF_DAY) >=12 ? 1:0;
//		System.out.println("now："+now.toString()+" "+nowTimeSlot);

		Calendar acceptTime = Calendar.getInstance();
		//表示受理是上午还是下午，12点前表示上午。
		int acceptTimeSlot;

		//最早受理时间-10天
		Calendar minAcceptTime = Calendar.getInstance();
		minAcceptTime.setTime(list.get(0).getAcceptanceTime());
		minAcceptTime.add(Calendar.DAY_OF_MONTH,-10);
//		System.out.println("minAcceptTime："+minAcceptTime.toString());
//		System.out.println("minAcceptTime："+minAcceptTime.getTime());
		//最晚受理时间+20天
		Calendar maxAcceptTime = Calendar.getInstance();
		maxAcceptTime.setTime(list.get(list.size()-1).getAcceptanceTime());
		maxAcceptTime.add(Calendar.DAY_OF_MONTH,20);
//		System.out.println("maxAcceptTime："+maxAcceptTime);
//		System.out.println("maxAcceptTime："+maxAcceptTime.getTime());

		//暂存从最早受理时间到最晚受理时间+20天之间的所有非工作日。 按照日期时间排序
		List<WorkDate> workDates = workDateService.getHolidayBetween(minAcceptTime.getTime(),maxAcceptTime.getTime(),100,1);
		//合并非工作日为假期区间
		List<Holiday> holidays = megerToHoliday(workDates);
//		System.out.println("合并非工作日为区间："+holidays);
		for(HwmReport report:list){
			//获取受理时间
			acceptTime.setTime(report.getAcceptanceTime());
			acceptTimeSlot = now.get(Calendar.HOUR_OF_DAY) >=12 ? 1:0;
			//获取最近的放假时间（要求该放假时间必须在受理时间后面）。
			Holiday holiday = getLatestHoliday(holidays,acceptTime);
			//如果holiday与受理时间是同一天，则表示在半天假的12点以后也受理了。直接当作上午受理
			//按道理不会出现这种情况，因为售后说了放假期间不受理
			if(DateUtils.isSameDay(holiday.getFrom().getNormalDate(),acceptTime.getTime())){
				acceptTimeSlot = 0;
			}
			//
			int dayDiff = getDayDiff(acceptTime.getTime(),holiday.getFrom().getNormalDate());
//			System.out.println("acceptTime"+acceptTime.toString()+"   dayDiff："+dayDiff);
			//计算应返还时间
			Date backTime = shouldBackTime(dayDiff,acceptTimeSlot,holiday,acceptTime.getTime());
//			System.out.println("backTime："+backTime.toString());
			report.setBackTime(format.format(backTime));

			//计算超期时间，0表示未超期
			int timeoutDay = timeoutDays(report,backTime,now.getTime(),nowTimeSlot,holidays);
//			System.out.println("timeoutDay："+timeoutDay);
			if(timeoutDay >0 ){
				report.setTimeoutDate((double)timeoutDay);
				timeoutList.add(report);
			}

		}
		return timeoutList;
	}

	/**
	 * 计算超期时间
	 * 如果已经发送装箱，则用装箱时间计算超期天数
	 * 如果未发送装箱，则用当前时间计算
	 * 0表示未超期
	 * 超期天数排除休息日。半天放假的算一天，但是两个半天也只累计一天
	 * @param hwmReport 维修信息
	 * @param backTime 应返还时间
	 * @param now 当前时间
	 * @param slot
	 * @param holidays 用来排除休息日
	 * @return
	 */
	public int timeoutDays(HwmReport hwmReport,Date backTime,Date now,int slot,List<Holiday> holidays) throws java.text.ParseException {
		int timeoutDay = 0;
		List<Holiday> sectionHoliday;
		Date startTime = DateUtils.addDays(backTime,1);
		Date endTime = null;
		//如果已经发送装箱，则超期时间从发送装箱算
		if(hwmReport.getSendPackTime()!=null) {
			endTime = hwmReport.getSendPackTime();
		}else {
			endTime = now;
		}
		//holidays的假日最多在最大受理时间的20天之后，一般情况下不会超期20天。
		//首先计算不排除休息日时的超期时间，如果很长的话就需要重新获取假日。以15天为界限。
		timeoutDay =getDayDiff(backTime,endTime);
//		System.out.println("未排除假期天数："+timeoutDay);
		//没有超期就先返回
		if (timeoutDay <=0 ){
			return 0;
		}

		//获取返还日期到发送装箱日期间的假期数
		if(timeoutDay >=15){

		}

//		System.out.println("超期开始时间："+startTime);
//		System.out.println("结束时间："+endTime);

		//获取重合假期区间
		sectionHoliday = sectionDay(holidays, startTime, endTime);
//		System.out.println("重合区间："+sectionHoliday);
		//计算天数
		CountDay countDay = countDay(sectionHoliday);
//		System.out.println("天数："+countDay);
		//计算最终天数
		timeoutDay = countDay.getCompleteDays()+countDay.getHalfDays()/2;
		timeoutDay += countDay.getHalfDays()%2;
		return Math.max(timeoutDay,0);
	}

	/**
	 * 计算这些假期区间有多少天+多少半天
	 * @param holidays
	 * @return
	 */
	public CountDay countDay(List<Holiday> holidays){
		CountDay countDay = null;
		int complete=0;
		int half=0;
		for(Holiday day:holidays){
			int dayDiff = getDayDiff(day.getFrom().getNormalDate(), day.getTo().getNormalDate());
			//不是全天放假就是放半天假
			if(day.getFrom().getWorkType() != 0){
				dayDiff--;
				half++;
			}
			complete+=dayDiff;
		}
		countDay = new CountDay(complete,half);
		return countDay;
	}

	/**
	 * 计算结果，多少天+多少个半天
	 */
	public static class CountDay{
		int completeDays;
		int halfDays;

		public CountDay() {
		}

		public CountDay(int completeDays, int halfDays) {
			this.completeDays = completeDays;
			this.halfDays = halfDays;
		}

		public int getCompleteDays() {
			return completeDays;
		}

		public void setCompleteDays(int completeDays) {
			this.completeDays = completeDays;
		}

		public int getHalfDays() {
			return halfDays;
		}

		public void setHalfDays(int halfDays) {
			this.halfDays = halfDays;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CountDay countDay = (CountDay) o;
			return completeDays == countDay.completeDays &&
					halfDays == countDay.halfDays;
		}

		@Override
		public int hashCode() {
			return Objects.hash(completeDays, halfDays);
		}
	}


	/**
	 * 计算时间区间start到end与holidays重合的区间。
	 * [start,end]左闭右闭，只看日期
	 * 返回值可能会直接引用startTime、endTime以及holiday中的值
	 * @param holidays 按照日期排序，并且假期之间不会重合
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Holiday> sectionDay(List<Holiday> holidays,Date startTime,Date endTime){
		List<Holiday> totalSection = new ArrayList<>();
		for(Holiday holiday:holidays){
			WorkDate from = holiday.getFrom();
			WorkDate to = holiday.getTo();
			Date fromDate = from.getNormalDate();
			Date toDate = to.getNormalDate();
			//假期的左区间已经超过endTime则直接退出循环,说明后面的假期不会再与区间[s,e]重合了
			//from比start时间大，且不是同一天
			if(fromDate.compareTo(endTime)>0
					&& !DateUtils.isSameDay(fromDate,endTime)){
				break;
			}
			//假期的右区间在开始时间之前，则没有重合，直接判断下一个假期区间
			if(toDate.compareTo(startTime)>0 && !DateUtils.isSameDay(toDate,startTime)){
				continue;
			}
			//一定有重合
			Holiday section = new Holiday();
			//假期区间与超期开始时间相比，谁靠右谁是左区间。
			if(fromDate.compareTo(startTime)>0 || DateUtils.isSameDay(fromDate,startTime)){
				holiday.setFrom(from);
			}else{
				//backNext大，则backNext为左区间。
				//由于大前提的第一条，所以不backNext的日期不与From重合，那么重合的假期一定是整天放假
				WorkDate workDate = new WorkDate(null,startTime,0,1,DateUtil.dayOfWeek(startTime));
				section.setFrom(workDate);
			}
			//结束时间与假期右区间比较，谁靠左谁是右区间
			if(toDate.compareTo(endTime)<0
					|| DateUtils.isSameDay(toDate,endTime)){
				section.setTo(to);
			}else{
				WorkDate workDate = new WorkDate(null,endTime,0,1,DateUtil.dayOfWeek(endTime));
				holiday.setTo(workDate);
			}
			totalSection.add(section);
		}
		return totalSection;
	}

	/**
	 * 根据假期和受理时间距假期开始时间天数计算应返还日期
	 * @param dayDiff
	 * @param holiday
	 * @param slot 受理是上午还是下午
	 * @return
	 */
	public Date shouldBackTime(int dayDiff,int slot,Holiday holiday,Date acceptTime){
		//假期最后一天
		Date to = holiday.getTo().getNormalDate();
		switch (dayDiff){
			//假期当天，按周六处理，假期结束时间加三天
			case 0:return DateUtils.addDays(to,3);
			//假期前一天，按周五处理
			case 1:return DateUtils.addDays(to,2);
			//假期前2天，按周四处理
			case 2: return DateUtils.addDays(to,1);
			//假期前3天，按周三处理
			case 3:
				if(slot == 0)
					return holiday.getFrom().getNormalDate();
				else
					return DateUtils.addDays(to,1);
			//假期前四天，前五天...等按照周一，周二...处理，上午受理的往后推两天，下午受理的往后推三天
			default:
				//上午
				if(slot == 0) {
					return DateUtils.addDays(acceptTime,2);
				}else{
					return DateUtils.addDays(acceptTime,3);
				}
		}
	}
	/**
	 * 将假日合并为一个个的区间。
	 * 例如，2020-10-01下午到2020-10-03放假，那么就转换为一个区间（Holiday），from是2020-10-01，To是2020-10-03
	 * @param list
	 * @return 多个假期区间
	 */
	public List<Holiday> megerToHoliday(List<WorkDate> list){
		Collections.sort(list, new Comparator<WorkDate>() {
			@Override
			public int compare(WorkDate o1, WorkDate o2) {
				return o1.getNormalDate().compareTo(o2.getNormalDate());
			}
		});
		List<Holiday> holidays = new ArrayList<>();
		if(list.size() == 1){
			holidays.add(new Holiday(list.get(0),list.get(0)));
			return holidays;
		}

		for(int i=0;i<list.size()-1;){
			Holiday holiday  = new Holiday();
			holiday.setFrom(list.get(i));
			//获取i的下一天
			Date nextI = DateUtils.addDays(list.get(i).getNormalDate(),1);
			for(int j=i+1;j<list.size();j++){
				WorkDate workDateJ = list.get(j);
				//判断i，j两天是否连续，如果不连续，则表示j是一个新区间的开始。j-1则是上一个区间的结束
				//或者一直连续，但是j到了最后一个。由于假期寻找到了最大受理时间的20天后，这里不判断也没问题
				if(!DateUtils.isSameDay(nextI,workDateJ.getNormalDate()) ||
						(j==list.size()-1 && DateUtils.isSameDay(nextI,workDateJ.getNormalDate()))){
					holiday.setTo(list.get(j-1));
					i = j;
					break;
				}
			}
			holidays.add(holiday);
		}
		return holidays;
	}


	/**
	 * 获取大于等于calendar且离calendar最近的一个假期区间
	 * @param holidays 假期区间，要求按照日期由小到大排好序
	 * @param calendar
	 * @return
	 */
	public Holiday getLatestHoliday(List<Holiday> holidays,Calendar calendar){
		for(Holiday day:holidays){
			//如果放假时间比calendar大，并且不是同一天，则说明day就是离calendar最近的一个假期。
			// （实际上holiday的WorkDay都是某天的00:00:00，所以只需要判断From比calendar大就行）
			if(day.getFrom().getNormalDate().compareTo(calendar.getTime()) >0
					&& !DateUtils.isSameDay(day.getFrom().getNormalDate(),calendar.getTime()))
				return day;

			//如果是同一天，则也直接返回，可以这么做的原因如下
			// 1.fiterTimeoutEqp()方法的大前提。
			// 2.售后部门说了，放假的时候不会受理设备，所以这里一定是放半天假的上午受理的。
			if(DateUtils.isSameDay(day.getFrom().getNormalDate(),calendar.getTime())){
				return day;
			}
			continue;
		}
		//到这里才返回表示这个受理时间不在任何一个holiday区间之前
		//这里就已经出错了
		//但是放假区间一直找到了最大受理时间的后20天。如果不是连续20天没有假，应该不会出现这种情况
		return null;
	}

	/**
	 * 返回两个日期间的天数差，同一天返回0.
	 * d1在d2之前返回正值，d1在d2之后返回负值
	 * @param d1
	 * @param d2
	 */
	public int getDayDiff(Date d1,Date d2){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			long startDateTime = dateFormat.parse(dateFormat.format(d1)).getTime();
			long endDateTime = dateFormat.parse(dateFormat.format(d2)).getTime();
			return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			logger.error("日期转换异常"+e.toString());
		}
		return 0;
	}


	
	private void setTimeOutContent(List<HwmReport> list){
		if (null != list && list.size() > 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");			
			for (HwmReport re : list) {
				//2017-03-04开始上班时间规格改变为周六上午上班
				if(re.getAcceptanceTime().getTime() >= DateTimeUtil.getDateTime(GlobalCons.BASETIME).getTime()){
					Timestamp backTimeState = new Timestamp(getInvalidTime(re.getAcceptanceTime()).getTime());	
					if(null !=re.getPackTime()){
						re.setTimeoutDate(countTimeoutDate(backTimeState, re.getPackTime()));
					}else{
						re.setTimeoutDate(countTimeoutDate(backTimeState, new Date()));
					}
					re.setBackTime(df.format(backTimeState));
				}else{
					Timestamp backTimeState = new Timestamp(getInvalidTime1(re.getAcceptanceTime()).getTime());				
					if(null !=re.getPackTime()){
						re.setTimeoutDate(countTimeoutDate1(backTimeState, re.getPackTime()));
					}else{
						re.setTimeoutDate(countTimeoutDate1(backTimeState, new Date()));
					}
					re.setBackTime(df.format(backTimeState));
				}
			}
		}
	}

	/**
	 * 超时机器未寄出报表-导出
	 * @param report
	 * @param req
	 * @param reps
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void timeoutAcceptExport(HwmReport report, HttpServletRequest req,
			HttpServletResponse reps) throws ParseException, IOException {
		List<HwmReport> list = reportMapper.getAllTimeoutAcceptList(report);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(HwmReport hwmReport:list){

			if(hwmReport.getPayTime() == null){
				hwmReport.setPayTimeStr("");
			}else{
				hwmReport.setPayTimeStr(simpleDateFormat.format(hwmReport.getPayTime()));
			}

			if(hwmReport.getSendPriceTime()  == null){
				hwmReport.setSendPriceTimeStr("");
			}else {
				hwmReport.setSendPriceTimeStr(simpleDateFormat.format(hwmReport.getSendPriceTime()));
			}

			if(hwmReport.getSendFicheckTime()  == null){
				hwmReport.setSendFicheckTimeStr("");
			}else {
				hwmReport.setSendFicheckTimeStr(simpleDateFormat.format(hwmReport.getSendFicheckTime()));
			}

			if(hwmReport.getTimeoutBackTime() == null){
				hwmReport.setTimeoutBackTimeStr("");
			}else{
				hwmReport.setTimeoutBackTimeStr(simpleDateFormat.format(hwmReport.getTimeoutBackTime()));
			}
		}
//		setTimeOutContent(list);

		String[] fieldNames = new String[] {"送修单位", "批次号", "IMEI", "主板型号", "送修日期", "应返回日期", 
				"发送装箱日期", "维修人", "超/天", "状态描述", "超期原因", "责任人","发送终检时间","客户付款日期",
				"维修发送报价日期","终检人","备注" };
		Map map = new HashMap();
		map.put("size", list.size() + 2);
		map.put("peList", list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("超3天机器未寄出报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(req.getRealPath("freemarker"))
				.append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("TimeoutReport.ftl").toString();

		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(req, reps, exportFile, fileName, template, map);
	}

	/**
	 * （星期六上班基准时间 与截止计算时间的时间差）
	 * 	除以7，奇数：星期六不上班，偶数，上班
	 * @param baseTime  DateTimeUtil.getDateTime(GlobalCons.BASETIME)
	 * @param calTime 截止计算时间
	 * @return
	 */
	private static double isWork(Date baseTime, Date calTime) {
		baseTime = setZero(baseTime);
		calTime  = setZero(calTime);
		double t =Math.floor(DateTimeUtil.dateDiff(baseTime, calTime));
		return t;
	}
	
	/**
	 * 获得传入日期所在星期六并把时分秒置为0
	 * @param d
	 * @return
	 */
	private static Date setZero(Date d){
		Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
	    if(cal.get(Calendar.DAY_OF_WEEK) == 1){
			cal.add(Calendar.DATE, -1);   
		}
		cal.set(Calendar.DAY_OF_WEEK,0);//获得传入日期所在星期六并把时分秒置为0
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MINUTE,0);
		return cal.getTime();
	}
	
	/**
	 *  获取失效时间 按上班工作日计算(大小周 2017-3-28）
	 * @param startDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Date getInvalidTime(Date startDate) {
		double t = isWork(DateTimeUtil.getDateTime(GlobalCons.BASETIME), startDate);
		//这周的周
		Boolean isWork = false;
		if((t / 7) % 2 == 0){
			isWork = true;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		// 1~7 表示星期天~星期六
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// 计算返回日期
		/*24号上午12点之前受理，26号下午18点之前寄出
   		    下午              		    27*/
		if(startDate.getHours() > 12){
			//受理时间在下午，就延长一天
			if (week_index > 3) {// 大于星期3情况下
				if(isWork && week_index == 4){//星期六上班
					cal.add(Calendar.DAY_OF_WEEK, 4);
				}else{
					cal.add(Calendar.DAY_OF_WEEK, 5);
				}
			} else {// 其他时间
				cal.add(Calendar.DAY_OF_WEEK, 3);
			}
		}else{
			//受理时间是上午
			if (week_index > 3) {// 大于星期3情况下
				if(isWork && week_index == 4){//星期六上班
					cal.add(Calendar.DAY_OF_WEEK, 2);
				}else if(isWork && week_index == 5){//星期六上班
					cal.add(Calendar.DAY_OF_WEEK, 3);
				}else{
					cal.add(Calendar.DAY_OF_WEEK, 4);
				}
			} else {// 其他时间
				cal.add(Calendar.DAY_OF_WEEK, 2);
			}
		}
		return cal.getTime();
		
	}

	/**
	 * 计算超出失效时间工作日 一个周工作天数5或6天，实际天数7天; 单位天，未排除节日情况（2017-3-28）;
	 * @param invalidTime
	 * @param currentDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static double countTimeoutDate(Date invalidTime, Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(invalidTime);//过期时间
		int inv_day_index = cal.get(Calendar.DAY_OF_WEEK) -1;
		int inv_week_index = cal.get(Calendar.WEEK_OF_YEAR);
		int inv_year_index = cal.get(Calendar.YEAR);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();//获取过期时间年的最后一周
		try {
			cl.setTime(sdf.parse(inv_year_index +"-12-31"));
			if(cl.get(Calendar.WEEK_OF_YEAR) == 1){
				cl.setTime(sdf.parse(inv_year_index +"-12-24"));//Calendar会把2017-12-31当成2018年的第一周
			}
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}   
		int inv_lastweek_index = cl.get(Calendar.WEEK_OF_YEAR);//过期时间最后一周
		
		Calendar cul_cal = Calendar.getInstance();
		cul_cal.setTime(currentDate);//当前时间
		int cul_day_index = cul_cal.get(Calendar.DAY_OF_WEEK) -1;
		int cul_week_index = cul_cal.get(Calendar.WEEK_OF_YEAR);
		int cul_year_index = cul_cal.get(Calendar.YEAR);
		
		double dateDiff = Math.ceil(DateTimeUtil.dateDiff(cal.getTime(), currentDate));// 计算超期的天数(最大的整数)
		double count = 0;//星期六上班次数
		
		double t1 = Math.ceil(isWork(DateTimeUtil.getDateTime(GlobalCons.BASETIME), invalidTime)/7)%2;//过期时间所在星期六，偶数，星期六上班
		double t2 = Math.ceil(isWork(DateTimeUtil.getDateTime(GlobalCons.BASETIME), currentDate)/7)%2;//当前时间所在星期六，偶数，星期六上班
		double count2 = Math.ceil(isWork(invalidTime, currentDate)/7);//当前日期是第几个星期
		
		
		if(inv_week_index == cul_week_index){//超期日期和当前日期在同一周
			if(cul_day_index == 0 || cul_day_index == 6){//星期日或星期六
				if(t2 == 0){
					count = 1;
				}else{
					count = 0;
				}
				return dateDiff - count;
			}else{
				return dateDiff;
			}
		}else{//超期日期和当前日期不在同一周
			if(t1 == 0 && t2 == 0){//超期星期六上班，当前日期星期六上班，cout2为偶数
				if(cul_day_index == 0 || (cul_day_index == 6 && currentDate.getHours() >= 18)){
					count = count2/2 + 1;
				}else{
					count = count2/2;
				}
			}else if(t1 != 0 && t2 != 0){//超期星期六不上班，当前日期星期六不上班，cout2为偶数
				count = count2/2;
			}else if(t1 == 0 && t2 != 0){//超期星期六上班，当前日期星期六不上班，cout2为奇数
				count = (count2 + 1)/2;
			}else if(t1 != 0 && t2 == 0){//超期星期六不上班，当前日期星期六上班，cout2为奇数
				if(cul_day_index == 0 || (cul_day_index == 6 && currentDate.getHours() >= 18)){
					count = (count2 + 1)/2;
				}else{
					count = (count2 - 1)/2;
				}
			}
			double countDate = 0;
			if(inv_year_index == cul_year_index){//同一年
				if(cul_day_index == inv_day_index){
					countDate = (cul_week_index - inv_week_index)*5 + count;//一周5天加上星期六上班的天数
				}else if(cul_day_index == 0 || cul_day_index == 6){//星期日或星期六
					countDate = (cul_week_index - inv_week_index -1)*5 + count + (5 - inv_day_index);
				}else{
					countDate = (cul_week_index - inv_week_index -1)*5 + count + (5 - inv_day_index) + cul_day_index;
				}
			}else{//过期日期的年份不是当前年份
				if(cul_day_index == inv_day_index){
					countDate = (cul_week_index + inv_lastweek_index - inv_week_index)*5 + count;//一周5天加上星期六上班的天数
				}else if(cul_day_index == 0 || cul_day_index == 6){//星期日或星期六
					countDate = (cul_week_index + inv_lastweek_index - inv_week_index -1)*5 + count + (5 - inv_day_index);
				}else{
					countDate = (cul_week_index + inv_lastweek_index - inv_week_index -1)*5 + count + (5 - inv_day_index) + cul_day_index;
				}
			}
			if(currentDate.getHours() >= 18){//超过6点算第二天
				countDate += 1;
			}
			return countDate;
		}
	}
	
	
	/**
	 * 获取失效时间 按上班工作日计算，周六半天，周日不计入,未排除节日情况（弃用）
	 * @param startDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Date getInvalidTime1(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		// 1~7 表示星期天~星期六
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// 计算返回日期
		/*24号上午12点之前受理，26号下午18点之前寄出
   		    下午              		    27*/
		if(startDate.getHours() > 12){
			//受理时间在下午，就延长一天
			if (week_index > 3) {// 大于星期3情况下
				cal.add(Calendar.DAY_OF_WEEK, 5);
			} else {// 其他时间
				cal.add(Calendar.DAY_OF_WEEK, 3);
			}
		}else{
			//受理时间是上午
			if (week_index > 3) {// 大于星期3情况下
				cal.add(Calendar.DAY_OF_WEEK, 4);
			} else {// 其他时间
				cal.add(Calendar.DAY_OF_WEEK, 2);
			}
		}
		return cal.getTime();
	}

	/**
	 * 计算超出失效时间工作日 一个周工作天数5.5天，实际天数7天; 单位0.5为半天，周日不算，周六半天，未排除节日情况（弃用）;
	 * @param invalidTime
	 * @param currentDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static double countTimeoutDate1(Date invalidTime, Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(invalidTime);
		int invalidWeek_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int invalidHours = cal.getTime().getHours();
//		System.out.println("失效日期星期：" + invalidWeek_index);
//		System.out.println("失效日期时：" + invalidWeek_index);
		// 计算超的天数
		int dateDiff = (int)DateTimeUtil.dateDiff(cal.getTime(), currentDate);
//		System.out.println("相差天数:" + dateDiff);
		double intervalDate = (dateDiff / 7) * 5.5;
//		System.out.println("间隔星期工作日:" + intervalDate);
		cal.setTime(currentDate);
		int c_week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
//		System.out.println("当前星期:" + c_week_index);
		double workDate = 0.0;
		if (invalidWeek_index == c_week_index) {
			if(currentDate.getHours() > 18){
				workDate = 1;
			}else{
				workDate = 0.5;
			}			
		} else if (c_week_index == 0 || c_week_index == 6) {
			workDate = 5 - invalidWeek_index + 0.5;
		} else if ((c_week_index - invalidWeek_index) == 1) {
//			int c_hours = cal.getTime().getHours();
			if (invalidHours <= 12) {
				workDate = 1;
			} else {
				workDate = 0.5;
			}
//			System.out.println("当前时差：" + workDate);
		} else if (c_week_index < invalidWeek_index) {
			if (6 - invalidWeek_index != 0) {
				workDate = c_week_index + (6 - invalidWeek_index - 0.5);
			} else {
				workDate = c_week_index;
			}
		} else {
			workDate = c_week_index - invalidWeek_index;
		}
		workDate = intervalDate + workDate ;
		return workDate;
	}
	
	/**
	 * 收件年报表数据源
	 * @param dateReport
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<DateReport> getReceiveYearList(DateReport dateReport) throws IllegalArgumentException, IllegalAccessException {
		List<DateReport> nativeList = reportMapper.getReceiveYearList(dateReport);
		packagingList(nativeList, "month");
		nativeList.add(getYearDr(nativeList));
		return nativeList;
	}

	/**
	 * 收件年报表-导出
	 * @param dateReport
	 * @param req
	 * @param reps
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void receiveYearExport(DateReport dateReport,
			HttpServletRequest req, HttpServletResponse reps) throws IllegalArgumentException, IllegalAccessException, ParseException, IOException {
		List<DateReport> list = getReceiveYearList(dateReport);
		String[] fieldNames = new String[] {"主板型号", "1月", "2 月", "3月", "4月", "5月",
				"6月", "7月", "8月", "9月", "10月", "11月", "12月","收件年统计" };
		Map map = new HashMap();
		map.put("size", list.size() + 2);
		map.put("peList", list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("收件年报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(req.getRealPath("freemarker"))
				.append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("ReceiveYearReport.ftl").toString();

		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(req, reps, exportFile, fileName, template, map);

	}

	/**
	 * 寄件年报表数据源
	 * @param dateReport
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<DateReport> getDeliverYearList(DateReport dateReport) throws IllegalArgumentException, IllegalAccessException {
		List<DateReport> nativeList = reportMapper.getDeliverYearList(dateReport);
		packagingList(nativeList, "month");
		nativeList.add(getYearDr(nativeList));
		return nativeList;
	}

	/**
	 * 寄收件年报表总计行数据
	 * @param nativeList
	 * @return
	 */
	private DateReport getYearDr(List<DateReport> nativeList){
		DateReport dr = new DateReport();
		Integer sum1=0,sum2=0,sum3=0,sum4=0,sum5=0,sum6=0,sum7=0,sum8=0,sum9=0,sum10=0,sum11=0,sum12=0;
		long sum13=0;
			for (int i = 0; i < nativeList.size(); i++) {
				sum1=nativeList.get(i).getMonth1()==null?sum1+0 : sum1+ nativeList.get(i).getMonth1();
				sum2=nativeList.get(i).getMonth2()==null?sum2+0 : sum2+ nativeList.get(i).getMonth2();
				sum3=nativeList.get(i).getMonth3()==null?sum3+0 : sum3+ nativeList.get(i).getMonth3();
				sum4=nativeList.get(i).getMonth4()==null?sum4+0 : sum4+ nativeList.get(i).getMonth4();
				sum5=nativeList.get(i).getMonth5()==null?sum5+0 : sum5+ nativeList.get(i).getMonth5();
				sum6=nativeList.get(i).getMonth6()==null?sum6+0 : sum6+ nativeList.get(i).getMonth6();
				sum7=nativeList.get(i).getMonth7()==null?sum7+0 : sum7+ nativeList.get(i).getMonth7();
				sum8=nativeList.get(i).getMonth8()==null?sum8+0 : sum8+ nativeList.get(i).getMonth8();
				sum9=nativeList.get(i).getMonth9()==null?sum9+0 : sum9+ nativeList.get(i).getMonth9();
				sum10=nativeList.get(i).getMonth10()==null?sum10+0 : sum10+ nativeList.get(i).getMonth10();
				sum11=nativeList.get(i).getMonth11()==null?sum11+0 : sum11+ nativeList.get(i).getMonth11();
				sum12=nativeList.get(i).getMonth12()==null?sum12+0 : sum12+ nativeList.get(i).getMonth12();
				sum13=nativeList.get(i).getTotalNum()==null?sum13+0 : sum13+ nativeList.get(i).getTotalNum();
			}
			dr.setModel("总计");
			if(sum1!=0) dr.setMonth1(sum1);
			if(sum2!=0) dr.setMonth2(sum2);
			if(sum3!=0) dr.setMonth3(sum3);
			if(sum4!=0) dr.setMonth4(sum4);
			if(sum5!=0) dr.setMonth5(sum5);
			if(sum6!=0) dr.setMonth6(sum6);
			if(sum7!=0) dr.setMonth7(sum7);
			if(sum8!=0) dr.setMonth8(sum8);
			if(sum9!=0) dr.setMonth9(sum9);
			if(sum10!=0) dr.setMonth10(sum10);
			if(sum11!=0) dr.setMonth11(sum11);
			if(sum12!=0) dr.setMonth12(sum12);
			if(sum13!=0) dr.setTotalNum(sum13);
		return dr;
	}

	/**
	 * 寄收件月报表总计行数据
	 * @param nativeList
	 * @return
	 */
	private DateReport getMonthDr(List<DateReport> nativeList){
		DateReport dr = new DateReport();
		Integer sum1=0,sum2=0,sum3=0,sum4=0,sum5=0,sum6=0,sum7=0,sum8=0,sum9=0,sum10=0,sum11=0,sum12=0,sum13=0,sum14=0,sum15=0,sum16=0,
				sum17=0,sum18=0,sum19=0,sum20=0,sum21=0,sum22=0,sum23=0,sum24=0,sum25=0,sum26=0,sum27=0,sum28=0,sum29=0,sum30=0,sum31=0;
		long sum32=0;
			for (int i = 0; i < nativeList.size(); i++) {
				sum1=nativeList.get(i).getDate1()==null?sum1+0 : sum1+ nativeList.get(i).getDate1();
				sum2=nativeList.get(i).getDate2()==null?sum2+0 : sum2+ nativeList.get(i).getDate2();
				sum3=nativeList.get(i).getDate3()==null?sum3+0 : sum3+ nativeList.get(i).getDate3();
				sum4=nativeList.get(i).getDate4()==null?sum4+0 : sum4+ nativeList.get(i).getDate4();
				sum5=nativeList.get(i).getDate5()==null?sum5+0 : sum5+ nativeList.get(i).getDate5();
				sum6=nativeList.get(i).getDate6()==null?sum6+0 : sum6+ nativeList.get(i).getDate6();
				sum7=nativeList.get(i).getDate7()==null?sum7+0 : sum7+ nativeList.get(i).getDate7();
				sum8=nativeList.get(i).getDate8()==null?sum8+0 : sum8+ nativeList.get(i).getDate8();
				sum9=nativeList.get(i).getDate9()==null?sum9+0 : sum9+ nativeList.get(i).getDate9();
				sum10=nativeList.get(i).getDate10()==null?sum10+0 : sum10+ nativeList.get(i).getDate10();
				sum11=nativeList.get(i).getDate11()==null?sum11+0 : sum11+ nativeList.get(i).getDate11();
				sum12=nativeList.get(i).getDate12()==null?sum12+0 : sum12+ nativeList.get(i).getDate12();
				sum13=nativeList.get(i).getDate13()==null?sum13+0 : sum13+ nativeList.get(i).getDate13();
				sum14=nativeList.get(i).getDate14()==null?sum14+0 : sum14+ nativeList.get(i).getDate14();
				sum15=nativeList.get(i).getDate15()==null?sum15+0 : sum15+ nativeList.get(i).getDate15();
				sum16=nativeList.get(i).getDate16()==null?sum16+0 : sum16+ nativeList.get(i).getDate16();
				sum17=nativeList.get(i).getDate17()==null?sum17+0 : sum17+ nativeList.get(i).getDate17();
				sum18=nativeList.get(i).getDate18()==null?sum18+0 : sum18+ nativeList.get(i).getDate18();
				sum19=nativeList.get(i).getDate19()==null?sum19+0 : sum19+ nativeList.get(i).getDate19();
				sum20=nativeList.get(i).getDate20()==null?sum20+0 : sum20+ nativeList.get(i).getDate20();
				sum21=nativeList.get(i).getDate21()==null?sum21+0 : sum21+ nativeList.get(i).getDate21();
				sum22=nativeList.get(i).getDate22()==null?sum22+0 : sum22+ nativeList.get(i).getDate22();
				sum23=nativeList.get(i).getDate23()==null?sum23+0 : sum23+ nativeList.get(i).getDate23();
				sum24=nativeList.get(i).getDate24()==null?sum24+0 : sum24+ nativeList.get(i).getDate24();
				sum25=nativeList.get(i).getDate25()==null?sum25+0 : sum25+ nativeList.get(i).getDate25();
				sum26=nativeList.get(i).getDate26()==null?sum26+0 : sum26+ nativeList.get(i).getDate26();
				sum27=nativeList.get(i).getDate27()==null?sum27+0 : sum27+ nativeList.get(i).getDate27();
				sum28=nativeList.get(i).getDate28()==null?sum28+0 : sum28+ nativeList.get(i).getDate28();
				sum29=nativeList.get(i).getDate29()==null?sum29+0 : sum29+ nativeList.get(i).getDate29();
				sum30=nativeList.get(i).getDate30()==null?sum30+0 : sum30+ nativeList.get(i).getDate30();
				sum31=nativeList.get(i).getDate31()==null?sum31+0 : sum31+ nativeList.get(i).getDate31();
				sum32=nativeList.get(i).getTotalNum()==null?sum32+0 : sum32+ nativeList.get(i).getTotalNum();
//				if (nativeList.get(i).getMonth2()==null) sum2 = sum2+0;
//				else sum2 += nativeList.get(i).getMonth2();
			}
			dr.setModel("总计");
			if(sum1!=0) dr.setDate1(sum1);
			if(sum2!=0) dr.setDate2(sum2);
			if(sum3!=0) dr.setDate3(sum3);
			if(sum4!=0) dr.setDate4(sum4);
			if(sum5!=0) dr.setDate5(sum5);
			if(sum6!=0) dr.setDate6(sum6);
			if(sum7!=0) dr.setDate7(sum7);
			if(sum8!=0) dr.setDate8(sum8);
			if(sum9!=0) dr.setDate9(sum9);
			if(sum10!=0) dr.setDate10(sum10);
			if(sum11!=0) dr.setDate11(sum11);
			if(sum12!=0) dr.setDate12(sum12);
			if(sum13!=0) dr.setDate13(sum13);
			if(sum14!=0) dr.setDate14(sum14);
			if(sum15!=0) dr.setDate15(sum15);
			if(sum16!=0) dr.setDate16(sum16);
			if(sum17!=0) dr.setDate17(sum17);
			if(sum18!=0) dr.setDate18(sum18);
			if(sum19!=0) dr.setDate19(sum19);
			if(sum20!=0) dr.setDate20(sum20);
			if(sum21!=0) dr.setDate21(sum21);
			if(sum22!=0) dr.setDate22(sum22);
			if(sum23!=0) dr.setDate23(sum23);
			if(sum24!=0) dr.setDate24(sum24);
			if(sum25!=0) dr.setDate25(sum25);
			if(sum26!=0) dr.setDate26(sum26);
			if(sum27!=0) dr.setDate27(sum27);
			if(sum28!=0) dr.setDate28(sum28);
			if(sum29!=0) dr.setDate29(sum29);
			if(sum30!=0) dr.setDate30(sum30);
			if(sum31!=0) dr.setDate31(sum31);
			if(sum32!=0) dr.setTotalNum(sum32);
		return dr;
	}
	
	/**
	 * 寄件年报表-导出
	 * @param dateReport
	 * @param req
	 * @param reps
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void deliverYearExport(DateReport dateReport,
			HttpServletRequest req, HttpServletResponse reps) throws IllegalArgumentException, IllegalAccessException, ParseException, IOException {
		List<DateReport> list = getDeliverYearList(dateReport);
		String[] fieldNames = new String[] {"主板型号", "1月", "2 月", "3月", "4月", "5月",
				"6月", "7月", "8月", "9月", "10月", "11月", "12月","寄件年统计"};
		Map map = new HashMap();
		map.put("size", list.size() + 2);
		map.put("peList", list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("寄件年报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(req.getRealPath("freemarker"))
				.append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("DeliverYearReport.ftl").toString();

		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(req, reps, exportFile, fileName, template, map);

	}

	/**
	 * 维修总明细报表数据源
	 * @param report
	 * @return List<HwmReport>
	 */
	public List<HwmReport> getRepairDetailList(HwmReport report, String startTime, String endTime) {
		List<HwmReport> list = new ArrayList<HwmReport>();
		if(null != report.getRepairDetailFlag() && !"".equals(report.getRepairDetailFlag()) && "defalut".equals(report.getRepairDetailFlag())){
			list = reportMapper.getRepairDetailListPage(report);
			setRepairDetailList(list);
		}else{
			List<String> hList =reportMapper.getImeiListForRepairDetail(report);
			String s = "";
			StringBuilder sbImei = new StringBuilder();
			if(hList.size() > 0){
				if(hList.size() == 1){
					s = hList.get(0);
				}else{
					for (int i = 0; i < hList.size(); i++) {
						if(i != 0){
							sbImei.append("," + hList.get(i)) ;
						}else{
							sbImei.append(hList.get(i));
						}
					}
					s = sbImei.toString();
				}
				if(null != s){
					list = reportMapper.getRepairDetailList(s, startTime, endTime);
					setRepairDetailList(list);
				}
			}
		}
		return list;
	}

	private void setRepairDetailList(List<HwmReport> list) {
		if(null != list && list.size()>0){
			for(HwmReport re:list){
				String[] cjArrys = StringUtil.split(re.getCjgzDesc());
				if(cjArrys.length != 0){
					StringBuilder sb = new StringBuilder();
					List<Cjgzmanage> cjList = reportMapper.getCJGZList(cjArrys);
					for(Cjgzmanage cjgz:cjList){
						sb.append(cjgz.getInitheckFault()+";");
					}
					re.setCjgzDesc(sb.substring(0, sb.length()-1));
				}
				String[] zzArrys = StringUtil.split(re.getZzgzDesc());
				if(zzArrys.length != 0){
					StringBuilder zzgzDesc = new StringBuilder();
					StringBuilder zzgzType = new StringBuilder();
					List<Zgzmanage> zzList = reportMapper.getZZGZList(zzArrys);
					for(Zgzmanage zzgz:zzList){
						zzgzDesc.append(zzgz.getProceMethod()+";");
						zzgzType.append(zzgz.getFaultType()+";");
					}
					re.setZzgzType(zzgzType.substring(0, zzgzType.length()-1));
					re.setZzgzDesc(zzgzDesc.substring(0, zzgzDesc.length()-1));
				}
			}
		}
	}
	
	public Page<HwmReport> getRepairDetailList(HwmReport report, Integer currentpage, Integer pageSize, String startTime, String endTime) {
		Page<HwmReport> page = new Page<HwmReport>();
		if(null != report.getRepairDetailFlag() && !"".equals(report.getRepairDetailFlag()) && "defalut".equals(report.getRepairDetailFlag())){
			page.setCurrentPage(currentpage);
			page.setSize(pageSize);
			page = reportMapper.getRepairDetailListPage(page,report);
			setRepairDetailList(page.getResult());
		}else{
			List<String> hList =reportMapper.getImeiListForRepairDetail(report);
			String s = "";
			StringBuilder sbImei = new StringBuilder();
			if(hList.size() > 0){
				if(hList.size() == 1){
					s = hList.get(0);
				}else{
					for (int i = 0; i < hList.size(); i++) {
						if(i != 0){
							sbImei.append("," + hList.get(i)) ;
						}else{
							sbImei.append(hList.get(i));
						}
					}
					s = sbImei.toString();
				}
				page.setCurrentPage(currentpage);
				page.setSize(pageSize);
				page = reportMapper.getRepairDetailList(page, s, startTime, endTime);
				
				setRepairDetailList(page.getResult());
			}
		} 
	
		for (HwmReport rep : page.getResult()) {
			String json = JavaNetURLRESTFulClient.restSale(rep.getImei().trim(),"", Globals.AMS_Salesurl); 
			if(null != json){
				JSONObject jsonObject = JSONObject.fromObject(json);
				String msg = jsonObject.getString("msg"); 
				if("0".equals(msg)){
					Saledata data = (Saledata) JSONObject.toBean(jsonObject,Saledata.class);  
					rep.setBill(data.getBill());
				}			 
			}
		}

		return page;
	}
	
	/**
	 * 维修总明细报表-导出
	 * @param report
	 * @param req
	 * @param reps
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void repairDetailExport(HwmReport report, HttpServletRequest req, HttpServletResponse reps, String startTime, String endTime) throws ParseException, IOException {
		List<HwmReport> list = getRepairDetailList(report, startTime, endTime);
		String[] fieldNames = new String[] {"送修批号","送修单位", "主板型号","创建类型", "IMEI", "二次返修次数", "保内/保外", "送修备注", "初检故障",
				"受理时间", "故障类别", "最终故障", "处理措施","维修备注","发送装箱时间", "取机时间", "维修工程师","终检人", "测试结果","测试员","放弃报价", "进度" };
		Map map = new HashMap();
		String isWarry = report.getIsWarranty();
		String strWarry = "";
		if(isWarry.equals("0")){
			strWarry = "保内";
		}else if(isWarry.equals("1")){
			strWarry = "保外";
		}
		StringBuilder dispaly = new StringBuilder();
		boolean stNull = (startTime == null || "".equals(startTime)),etNull = (endTime == null || "".equals(endTime));
		if(!stNull && !etNull){
			dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		if(stNull && !etNull){
			 dispaly.append("(--").append(endTime).append(")");
		}
		if(!stNull && etNull){
			 Date date = new Date();
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");       
		     endTime = sdf.format(date).toString();
		     dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
		}
		map.put("dispalyDate", dispaly);
		map.put("isWarry", strWarry);
		map.put("size", list.size() + 2);
		map.put("peList", list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder(strWarry+"维修总明细报表").append(
				GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(req.getRealPath("freemarker"))
				.append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
				.append("RepairDetailReport.ftl").toString();

		Template template = freeMarkerConfigurer.getConfiguration()
				.getTemplate(templatePath);
		FreemarkerManager.down(req, reps, exportFile, fileName, template, map);
	}

	/**
	 *  超三天报表修改
	 * @param report
	 */
	public void timeoutUpdateInfo(String dutyOfficer, String timeoutRemark, String timeoutReason, String[] ids) {
		workflowMapper.updateForTimeout(dutyOfficer, timeoutRemark, timeoutReason, ids);
	}
	
	public static void main(String[] args) {
	double	t=	isWork(DateTimeUtil.getDateTime("2017-04-01 00:00:00"),DateTimeUtil.getDateTime("2017-04-02 00:00:00"));
	System.out.println(t);
		
	}

	public List<String> timeoutEngineer(HwmReport report){
		return reportMapper.selectTimeoutEngineer(report);
	}

	public List<Integer> timeoutState(HwmReport report){
		return  reportMapper.selectTimeoutState(report);
	}
}
