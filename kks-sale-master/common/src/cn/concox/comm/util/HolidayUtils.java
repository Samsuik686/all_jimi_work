package cn.concox.comm.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 计算两个日期之间工作日，排除节假日、周六周日，加上补班天数
 * 暂未使用
 * @author 86183
 *
 */
public class HolidayUtils {

	private static Logger logger = Logger.getLogger(HolidayUtils.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	
	public static void main(String[] args) {
		System.out.println(HolidayUtils.calDaysBetweenDate("2020-4-24", "2020-5-15"));
	}
	
	/**
	 * 查询节假日，工作日
	 * @return
	 * @throws IOException
	 */
	public static Map<String,Object> getHoliday() {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Set<String> holidays = new HashSet<>();
		Set<String> workdays = new HashSet<>();
		resultMap.put("holidays", holidays);
		resultMap.put("workdays", workdays);
		resultMap.put("code", 0);
		resultMap.put("mess", "查询成功");
		try {
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(
					"https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query="
					+ new SimpleDateFormat("yyyy").format(new Date())
					+ "年&resource_id=6018&format=json",
					String.class);
			String json = forEntity.getBody();
			if (StringUtils.isEmpty(json)) {
				logger.error("查询失败！");
				resultMap.put("code", 1);
				resultMap.put("mess", "查询失败");
				return resultMap;
			}
			ObjectMapper om = new ObjectMapper();
			JsonNode readTree = om.readTree(json);
			JsonNode statusNode = readTree.get("status");
			if (statusNode == null || !"0".equals(statusNode.asText())) {
				logger.error("查询失败！返回状态为：" + statusNode);
				resultMap.put("code", 2);
				resultMap.put("mess", "查询失败！返回状态为：" + statusNode);
				return resultMap;
			}

			JsonNode holidayNode = readTree.get("data").get(0).get("holiday");
			Iterator<JsonNode> iterator = holidayNode.iterator();
			while (iterator.hasNext()) {
				JsonNode next = iterator.next();
				Iterator<JsonNode> iterator2 = next.get("list").iterator();
				while (iterator2.hasNext()) {
					JsonNode next2 = iterator2.next();
					// {"date":"2020-1-1","status":"1"}
					// System.out.println(next2);
					@SuppressWarnings("unchecked")
					Map<String, String> convertValue = om.convertValue(next2, Map.class);
					String date = convertValue.get("date");
					String status = convertValue.get("status");
					if ("1".equals(status)) {
						holidays.add(date);
					} else if ("2".equals(status)) {
						workdays.add(date);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询失败！未知异常",e);
			resultMap.put("code", 3);
			resultMap.put("mess", "查询失败！未知异常");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 计算两个日期间工作日，排除周六周日，节假日
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static int calDaysBetweenDate(String startDay, String endDay) {
		int days = 0;
		Map<String, Object> holiday = getHoliday();
		Set<String> holidays = (Set<String>) holiday.get("holidays");
		Set<String> workdays = (Set<String>) holiday.get("workdays");
		
//		if (holidays == null || holidays.size() == 0 || workdays == null || workdays.size() == 0) {
//			logger.error("获取节假日信息失败");
//			return -1;
//		}
		try {
			Date startDate = sdf.parse(startDay);
			Date endDate = sdf.parse(endDay);
			while (startDate.getTime() <= endDate.getTime()) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				days += dayOperate(cal,holidays,workdays);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				startDate = cal.getTime();
			}
		} catch (ParseException e) {
			logger.error("计算日期失败",e);
		}
		return days;
	}

	/**
	 * 判断工作日、节假日累计天数
	 * @param cal
	 * @param workdays 
	 * @param holidays 
	 * @return
	 */
	private static int dayOperate(Calendar cal, Set<String> holidays, Set<String> workdays) {
		String startDate = sdf.format(cal.getTime());
		if (holidays.contains(startDate)) {
			return 0;
		}
		if (workdays.contains(startDate)) {
			return 1;
		}
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return 0;
		} else {
			return 1;
		}
	}

}
