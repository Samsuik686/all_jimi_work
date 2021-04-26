package cn.concox.app.report.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.report.mapper.PhoneRechargeReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.DateUtil;
import cn.concox.vo.report.PhoneRechargeReport;
import freemarker.template.Template;

@Service("phoneRechargeReportService")
@Scope("prototype")
public class PhoneRechargeReportService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "phoneRechargeReportMapper")
	private PhoneRechargeReportMapper<PhoneRechargeReport> phoneRechargeReportMapper;

	@SuppressWarnings("unchecked")
	public Page<PhoneRechargeReport> getRechargeListPage(PhoneRechargeReport phoneRecharge, int currentPage, int pageSize) {
		Page<PhoneRechargeReport> phoneRechargeListPage = new Page<PhoneRechargeReport>();
		phoneRechargeListPage.setCurrentPage(currentPage);
		phoneRechargeListPage.setSize(pageSize);
		phoneRechargeListPage = phoneRechargeReportMapper.getPhoneRechargeListPage(phoneRechargeListPage, phoneRecharge);
		this.sumReport(phoneRechargeListPage.getResult());
		return phoneRechargeListPage;
	}
	
	/**
	 * 汇总 手机卡充值报表 需付给卡商，充值面值，收客户费用
	 * @author TangYuping
	 * @version 2017年1月18日 下午4:20:23
	 * @param list
	 * @return
	 */
	public List<PhoneRechargeReport> sumReport(List<PhoneRechargeReport> list){
		if(list != null && list.size() >0 ){
			BigDecimal sumPayAble = BigDecimal.ZERO, sumPayOther = BigDecimal.ZERO;
			BigDecimal sumRatePrice = BigDecimal.ZERO;
			PhoneRechargeReport phoneObj = new PhoneRechargeReport();
			for(PhoneRechargeReport report : list){
				if(report.getPayable() == null){ //收客户费用
					sumPayAble = sumPayAble.add(BigDecimal.ZERO);
				}else{
					sumPayAble = sumPayAble.add(report.getPayable());
				}
				if(report.getRatePrice() == null){ //税费
					sumRatePrice = sumRatePrice.add(BigDecimal.ZERO);
				}else{
					sumRatePrice = sumRatePrice.add(report.getRatePrice());
				}
				if(report.getPayOther() == null){ //需付给卡商
					sumPayOther = sumPayOther.add(BigDecimal.ZERO);
				}else{
					sumPayOther = sumPayOther.add(report.getPayOther());
				}
			}
			phoneObj.setMonthNumber("总计");
			phoneObj.setPayable(sumPayAble);
			phoneObj.setPayOther(sumPayOther);
			phoneObj.setRatePrice(sumRatePrice);
			list.add(phoneObj);
		}
		return list;
	}

	public PhoneRechargeReport getRecharge(PhoneRechargeReport phoneRecharge) {
		return phoneRechargeReportMapper.getT(phoneRecharge.getCardId());
	}

	public void insertRecharge(PhoneRechargeReport phoneRecharge) {
		phoneRechargeReportMapper.insert(phoneRecharge);
	}

	public void updateRecharge(PhoneRechargeReport phoneRecharge) {
		phoneRechargeReportMapper.update(phoneRecharge);
	}

	public void deleteRecharge(PhoneRechargeReport recharge) {
        List<Integer> list = new ArrayList<>();
        String[] str = recharge.getCardIdsStr().split(",");
        for (int i = 0; i < str.length; i++) {
            list.add(Integer.valueOf(str[i]));
        }
        recharge.setCardIds(list);
	    
		phoneRechargeReportMapper.delete(recharge);
	}

	public List<PhoneRechargeReport> queryList(PhoneRechargeReport phoneRecharge) {
		List<PhoneRechargeReport> reports = phoneRechargeReportMapper.queryList(phoneRecharge);
		if (reports != null && reports.size() > 0) {
//			PhoneRechargeReport p = queryTotal(phoneRecharge);
//			if (p != null) {
//				reports.add(reports.size(), p);
//			}
			this.sumReport(reports);
		}
		return reports;
	}

	public int isExists(PhoneRechargeReport phoneRecharge) {
		return phoneRechargeReportMapper.isExists(phoneRecharge);
	}

	public List<PhoneRechargeReport> querySum(PhoneRechargeReport phoneRecharge) {
		return phoneRechargeReportMapper.querySum(phoneRecharge);
	}

	public PhoneRechargeReport queryTotal(PhoneRechargeReport phoneRecharge) {
		return phoneRechargeReportMapper.queryTotal(phoneRecharge);
	}

	/**
	 * 导出数据
	 * 
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(PhoneRechargeReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<PhoneRechargeReport> reports = queryList(report);
		String[] fieldNames = new String[] { "日期", "客户名称", "IMEI号", "手机号", "充值面值(￥)", "月数", "收客户费用(￥)", "需付给卡商(￥)", "税费(￥)", "状态", "汇款方式", "移动系统充值", "备注"};
		Map map = new HashMap();
		map.put("size", reports.size() + 2);
		map.put("peList", reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);

		List<PhoneRechargeReport> reports1 = querySum(report);
		String[] fieldNames1 = new String[] { "项目", "费用(￥)" };
		map.put("size1", reports1.size() + 2);
		map.put("peList1", reports1);
		map.put("fieldNames1", fieldNames1);
		map.put("cosLenth1", fieldNames1.length);

		String fileName = new StringBuilder("手机卡充值报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("PhoneRecharge.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	/**
	 * 导入数据
	 * 
	 * @param filePath
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<PhoneRechargeReport> successList = new ArrayList<PhoneRechargeReport>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file, null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						PhoneRechargeReport s = new PhoneRechargeReport();
						// PhoneRechargeReport对象的属性值{rechargeDate[0]、cusName[1]、imei[2]、phone[3]、faceValue[4]、monthNumber[5]、
						// payable[6]、payOther[7]、ratePrice[8]、orderStatus[9]、account[10]、isMobileCharge[11]、remark[12]}
						Object[] obj = new Object[13];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String) obj[0]) || StringUtils.isBlank((String) obj[3]) || StringUtils.isBlank((String) obj[4]) || StringUtils.isBlank((String) obj[5]) 
										|| StringUtils.isBlank((String) obj[6]) || StringUtils.isBlank((String) obj[7]) ||StringUtils.isBlank((String) obj[10])) {
									throw new RuntimeException();
								} else {
									s.setRechargeDate(DateUtil.getTimestampByStr((String) obj[0]));
									s.setCusName((String) obj[1]);
									s.setImei(((String) obj[2]).replaceAll(",", ""));
									s.setPhone(((String) obj[3]).replaceAll(",", ""));
									s.setFaceValue(BigDecimal.valueOf(Double.valueOf((String) obj[4])));
									s.setMonthNumber((String) obj[5]);
									s.setPayable(BigDecimal.valueOf(Double.valueOf((String) obj[6])));
									s.setPayOther(BigDecimal.valueOf(Double.valueOf((String) obj[7])));
									s.setRatePrice(StringUtils.isBlank((String) obj[8]) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf((String) obj[8])));
									s.setOrderStatus((String) obj[9]);
//									s.setAccount((String) obj[10]);
									String payMethod = (String) obj[10];
									if(payMethod.equals("人工付款")){
										s.setRemittanceMethod("personPay");
									}else if(payMethod.equals("支付宝付款")){
										s.setRemittanceMethod("aliPay");
									}else if(payMethod.equals("微信付款")){
										s.setRemittanceMethod("weChatPay");
									}else{
										s.setRemittanceMethod(payMethod);
									}
									
									if(obj[11] != null){
										s.setIsMobileCharge(((String) obj[11]).equals("是") ? 0 : 1);														
									}else{
										s.setIsMobileCharge(1);																			
									}
									s.setRemark((String) obj[12]);
										s.setIsDiscount(1);
									if (isExists(s) == 0) {
										try {
											phoneRechargeReportMapper.insert(s);
											successList.add(s);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										repeatList.add((repeatList.size() + 1) + ":第" + (j + 1) + "行数据已存在,未导入,请检查......");
									}
								}
							} catch (Exception e) {
								if (errorList.size() < 1000) {// 最多保存1000条错误数据
									errorList.add((errorList.size() + 1) + ":第" + (j + 1) + "行导入错误,有必填数据未填写......");
								}
							}
						}
					}
					if (errorList.size() < 1) {

						if (successList.size() > 0) {
							errorList.add("导入成功！！！ 成功数据" +successList.size()+"条");
						} else {
							errorList.add("可导入数据已全部存在！！！");
						}
						if (repeatList.size() > 0) {
							errorList.addAll(repeatList);
						}
					} else {
						errorList.add(0, "导入失败.......");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return errorList;
	}
}
