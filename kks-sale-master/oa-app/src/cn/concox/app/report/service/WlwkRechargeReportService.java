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
import cn.concox.app.report.mapper.WlwkRechargeReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.DateUtil;
import cn.concox.vo.report.WlwkRechargeReport;
import freemarker.template.Template;

@Service("wlwkRechargeReportService")
@Scope("prototype")
public class WlwkRechargeReportService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "wlwkRechargeReportMapper")
	private WlwkRechargeReportMapper<WlwkRechargeReport> wlwkRechargeReportMapper;

	@SuppressWarnings("unchecked")
	public Page<WlwkRechargeReport> getRechargeListPage(WlwkRechargeReport recharge, int currentPage, int pageSize) {
		Page<WlwkRechargeReport> rechargeListPage = new Page<WlwkRechargeReport>();
		rechargeListPage.setCurrentPage(currentPage);
		rechargeListPage.setSize(pageSize);
		rechargeListPage = wlwkRechargeReportMapper.getWlwkRechargeListPage(rechargeListPage, recharge);
		this.sumReport(rechargeListPage.getResult());
		return rechargeListPage;
	}
	
	/**
	 * 汇总物联网卡 充值面额，收客户费用，需付给卡商 数据
	 * @author TangYuping
	 * @version 2017年1月18日 下午3:00:10
	 * @param list
	 * @return
	 */
	public List<WlwkRechargeReport> sumReport(List<WlwkRechargeReport> list){
		if(list != null && list.size() >0 ){
			BigDecimal sumPayAble = BigDecimal.ZERO, sumPayOther = BigDecimal.ZERO;
			BigDecimal sumRatePrice = BigDecimal.ZERO;
			WlwkRechargeReport wlObj = new WlwkRechargeReport();
			for(WlwkRechargeReport report : list){
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
			wlObj.setMonthNumber("总计");
			wlObj.setPayable(sumPayAble);
			wlObj.setPayOther(sumPayOther);
			wlObj.setRatePrice(sumRatePrice);
			list.add(wlObj);
		}
		return list;
	}

	public WlwkRechargeReport getRecharge(WlwkRechargeReport recharge) {
		return wlwkRechargeReportMapper.getT(recharge.getWlwkId());
	}

	public void insertRecharge(WlwkRechargeReport recharge) {
		wlwkRechargeReportMapper.insert(recharge);
	}

	public void updateRecharge(WlwkRechargeReport recharge) {
		wlwkRechargeReportMapper.update(recharge);
	}

	public void deleteRecharge(WlwkRechargeReport recharge) {
        List<Integer> list = new ArrayList<>();
        String[] str = recharge.getWlwkIdsStr().split(",");
        for (int i = 0; i < str.length; i++) {
            list.add(Integer.valueOf(str[i]));
        }
        recharge.setWlwkIds(list);
	    
		wlwkRechargeReportMapper.delete(recharge);
	}

	public List<WlwkRechargeReport> queryList(WlwkRechargeReport recharge) {
		List<WlwkRechargeReport> reports = wlwkRechargeReportMapper.queryList(recharge);
		if (reports != null && reports.size() > 0) {
//			WlwkRechargeReport w = queryTotal(recharge);
//			if (w != null) {
//				reports.add(reports.size(), w);
//			}
			this.sumReport(reports);
		}
		return reports;
	}

	public int isExists(WlwkRechargeReport recharge) {
		return wlwkRechargeReportMapper.isExists(recharge);
	}

	public List<WlwkRechargeReport> querySum(WlwkRechargeReport wlwkRecharge) {
		return wlwkRechargeReportMapper.querySum(wlwkRecharge);
	}

	public WlwkRechargeReport queryTotal(WlwkRechargeReport wlwkRecharge) {
		return wlwkRechargeReportMapper.queryTotal(wlwkRecharge);
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
	public void ExportDatas(WlwkRechargeReport report, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<WlwkRechargeReport> reports = queryList(report);		
		String[] fieldNames = new String[] { "日期", "客户名称", "手机号", "套餐", "充值面值(￥)", "月数", "收客户费用(￥)", "需付给卡商(￥)", "税费(￥)", "状态", "汇款方式", "备注"};
		Map map = new HashMap();
		map.put("size", reports.size() + 2);
		map.put("peList", reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);

		List<WlwkRechargeReport> reports1 = querySum(report);
		String[] fieldNames1 = new String[] { "项目", "费用(￥)" };
		map.put("size1", reports1.size() + 2);
		map.put("peList1", reports1);
		map.put("fieldNames1", fieldNames1);
		map.put("cosLenth1", fieldNames1.length);

		String fileName = new StringBuilder("物联网卡充值报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("WlwkRecharge.ftl").toString();
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
		List<WlwkRechargeReport> successList = new ArrayList<WlwkRechargeReport>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file, null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						WlwkRechargeReport s = new WlwkRechargeReport();
						// WlwkRechargeReport对象的属性值{rechargeDate[0]、cusName[1]、phone[2]、formula[3]、faceValue[4]、monthNumber[5]、payable[6]、
						// payOther[7]、ratePrice[8]、orderStatus[9]、account[10]、remark[11]}
						Object[] obj = new Object[12];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String) obj[0]) || StringUtils.isBlank((String) obj[1]) || 
										StringUtils.isBlank((String) obj[2]) || StringUtils.isBlank((String) obj[3]) || 
										StringUtils.isBlank((String) obj[4]) || StringUtils.isBlank((String) obj[5]) || 
										StringUtils.isBlank((String) obj[6]) || StringUtils.isBlank((String) obj[7]) || StringUtils.isBlank((String) obj[10])) {
									throw new RuntimeException();
								} else {
									s.setRechargeDate(DateUtil.getTimestampByStr((String) obj[0]));
									s.setCusName((String) obj[1]);
									s.setPhone(((String) obj[2]).replaceAll(",", ""));
									s.setFormula((String) obj[3]);
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
									
									s.setRemark((String) obj[11]);
									if (isExists(s) == 0) {
										try {
											wlwkRechargeReportMapper.insert(s);
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
