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
import cn.concox.app.material.service.MaterialLogService;
import cn.concox.app.report.mapper.XspjcostsReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.DateUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.report.XspjcostsReport;
import freemarker.template.Template;

@Service("xspjcostsReportService")
@Scope("prototype")
public class XspjcostsReportService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name = "xspjcostsReportMapper")
	private XspjcostsReportMapper<XspjcostsReport> xspjcostsReportMapper;
	
	@Resource(name="materialLogService")
	private MaterialLogService materialLogService;

	@SuppressWarnings("unchecked")
	public Page<XspjcostsReport> getXspjcostsListPage(XspjcostsReport xspjcosts, int currentPage, int pageSize) {
		Page<XspjcostsReport> xspjcostsListPage = new Page<XspjcostsReport>();
		xspjcostsListPage.setCurrentPage(currentPage);
		xspjcostsListPage.setSize(pageSize);
		xspjcostsListPage = xspjcostsReportMapper.getXspjcostsListPage(xspjcostsListPage, xspjcosts);
		getTotal(xspjcostsListPage.getResult());
		return xspjcostsListPage;
	}

	public XspjcostsReport getXspjcosts(XspjcostsReport xspjcosts) {
		return xspjcostsReportMapper.getT(xspjcosts.getSaleId());
	}

	@Transactional
	public void insertXspjcosts(XspjcostsReport xspjcosts) {
		try {
			xspjcostsReportMapper.insert(xspjcosts);
			materialLogService.outBoundXSPj(xspjcosts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateXspjcosts(XspjcostsReport xspjcosts) {
		xspjcostsReportMapper.update(xspjcosts);
	}

	public void deleteXspjcosts(XspjcostsReport xspjcosts) {
		xspjcostsReportMapper.delete(xspjcosts);
	}

	public List<XspjcostsReport> queryList(XspjcostsReport xspjcosts) {
		return xspjcostsReportMapper.queryList(xspjcosts);
	}
	
	public int isExists(XspjcostsReport xspjcosts){
		return xspjcostsReportMapper.isExists(xspjcosts);
	}
	
	public List<XspjcostsReport> getByRepairNumber(String repairNumber){
		return xspjcostsReportMapper.getByRepairNumber(repairNumber);
	}
	
	/**
	 * 添加总计数据
	 * @param result
	 */
	public void getTotal(List<XspjcostsReport> result){
		if(result != null && result.size() >0){
			XspjcostsReport xr=new XspjcostsReport();
			Integer number=0;
			BigDecimal payPrice=new BigDecimal(0.00);
			BigDecimal logCost=new BigDecimal(0.00);
			BigDecimal ling=new BigDecimal(0.00);
			BigDecimal ratePrice=new BigDecimal(0.00);
			BigDecimal sumPrice=new BigDecimal(0.00);
			for (int i = 0; i < result.size(); i++) {
				number=result.get(i).getNumber()==null?number+0 : number+ result.get(i).getNumber();
				payPrice=result.get(i).getPayPrice()==null? payPrice.add(ling): payPrice.add((result.get(i)).getPayPrice());
				logCost=result.get(i).getLogCost()==null? logCost.add(ling): logCost.add((result.get(i)).getLogCost());
				ratePrice=result.get(i).getRatePrice()==null? ratePrice.add(ling): ratePrice.add((result.get(i)).getRatePrice());
				sumPrice=result.get(i).getSumPrice()==null? sumPrice.add(ling): sumPrice.add((result.get(i)).getSumPrice());
			}
			xr.setCusName("总计");
			xr.setNumber(number);
			xr.setPayPrice(payPrice);
			xr.setLogCost(logCost);
			xr.setRatePrice(ratePrice);
			xr.setSumPrice(sumPrice);
			result.add(xr);
		}
	}
	
	/**
	 * 导出数据
	 * @param report
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(XspjcostsReport report, HttpServletRequest request,HttpServletResponse response) throws IOException { 
		List<XspjcostsReport> reports = queryList(report);
		getTotal(reports);
		String[] fieldNames = new String[] {"客户名称","销售日期","主板型号 ","物料编码", "M|T", "物料名称","组合名称","数量","单价(￥)","收款原因","配件费(￥)","运费(￥)","税费(￥)","总费用(￥)","是否开发票","税率(%)","汇款方式","付款日期","快递方式","快递单号","确认结果","备注原因"};
		Map map = new HashMap();
		map.put("size", reports.size()+2);
		map.put("peList",reports);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("销售配件收费报表")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS)
									.append("Xspjcosts.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
	/**
	 * 导入数据
	 * @param filePath
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<XspjcostsReport> successList = new ArrayList<XspjcostsReport>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						XspjcostsReport s = new XspjcostsReport();
						// XspjcostsReport对象的属性值{cusName[0]、saleDate[1]、marketModel[2]、proName[3]、number[4]、unitPrice[5]、payReason[6]、payPrice[7]、
						//logCost[8]、ratePrice[9]、sumPrice[10]、receipt[11]、rate[12]、payMethod[13]、payDate[14]、expressType[15]、expressNO[16]、payState[17]、remark[18]}
						Object[] obj = new Object[19];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2]) || StringUtils.isBlank((String)obj[3]) || 
									StringUtils.isBlank((String)obj[4]) || StringUtils.isBlank((String)obj[5]) || StringUtils.isBlank((String)obj[6]) || StringUtils.isBlank((String)obj[7]) ||
									StringUtils.isBlank((String)obj[10]) || StringUtils.isBlank((String)obj[13]) || StringUtils.isBlank((String)obj[14])) {
									throw new RuntimeException();
								} else {
									s.setCusName((String) obj[0]);
									s.setSaleDate(DateUtil.getTimestampByStr((String)obj[1]));
									s.setMarketModel((String) obj[2]);
									s.setProName(obj[3].toString());
									s.setNumber(Integer.valueOf(obj[4].toString()));
									s.setUnitPrice(BigDecimal.valueOf(Double.valueOf((String) obj[5])));
									s.setPayReason((String) obj[6]);
									s.setPayPrice(BigDecimal.valueOf(Double.valueOf((String) obj[7])));
									if(!StringUtil.isRealEmpty((String)obj[8])){
										s.setLogCost(BigDecimal.valueOf(Double.valueOf((String) obj[8])));
									}
									if(!StringUtil.isRealEmpty((String)obj[9])){
										s.setRatePrice(BigDecimal.valueOf(Double.valueOf((String) obj[9])));
									}else{
										s.setRatePrice(BigDecimal.ZERO);
									}
									s.setSumPrice(BigDecimal.valueOf(Double.valueOf((String) obj[10])));
									s.setReceipt(Integer.valueOf(((String)obj[11]).equals("是") ? 0:1));
									s.setRate(StringUtil.isRealEmpty((String)obj[12]) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf((String) obj[11])));
									
									String payMethod = (String) obj[13];
									if(payMethod.equals("人工付款")){
										s.setPayMethod("personPay");
									}else if(payMethod.equals("支付宝付款")){
										s.setPayMethod("aliPay");
									}else if(payMethod.equals("微信付款")){
										s.setPayMethod("weChatPay");
									}else{
										s.setPayMethod(payMethod);
									}
									s.setPayDate(DateUtil.getTimestampByStr((String)obj[14]));
									s.setExpressType((String) obj[15]);
									s.setExpressNO((String) obj[16]);
									s.setPayState((String) obj[17]);
									s.setRemark((String) obj[18]);
									if(isExists(s)==0){
										try {
											xspjcostsReportMapper.insert(s);
											successList.add(s);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else{
										repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行数据已存在,未导入,请检查......");
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
							errorList.add("导入成功！！！");
						}else{
							errorList.add("可导入数据已全部存在！！！");
						}
						if(repeatList.size()>0){
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
