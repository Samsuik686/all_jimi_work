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
import cn.concox.app.report.mapper.RechargeReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.DateUtil;
import cn.concox.vo.report.RechargeReport;
import freemarker.template.Template;

@Service("rechargeReportService")
@Scope("prototype")
public class RechargeReportService {
    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource(name = "rechargeReportMapper")
    private RechargeReportMapper<RechargeReport> rechargeReportMapper;

    @SuppressWarnings("unchecked")
    public Page<RechargeReport> getRechargeListPage(RechargeReport recharge, int currentPage, int pageSize) {
        Page<RechargeReport> rechargeListPage = new Page<RechargeReport>();
        rechargeListPage.setCurrentPage(currentPage);
        rechargeListPage.setSize(pageSize);
        rechargeListPage = rechargeReportMapper.getRechargeListPage(rechargeListPage, recharge);
        getTotal(rechargeListPage.getResult());
        return rechargeListPage;
    }

    public RechargeReport getRecharge(RechargeReport recharge) {
        return rechargeReportMapper.getT(recharge.getRechId());
    }

    public void insertRecharge(RechargeReport recharge) {
        if (recharge.getYearNumber() != 0) {
            RechargeReport yearRecharge = recharge;
            yearRecharge.setTerm(0);
            yearRecharge.setNumber(recharge.getYearNumber());
            yearRecharge.setUnitPrice(recharge.getYearUnitPrice());
            yearRecharge.setTotalCollection(recharge.getYearTotalCollection());
            yearRecharge.setRatePrice(recharge.getYearRatePrice());
            rechargeReportMapper.insert(yearRecharge);
        }
        if (recharge.getLifeNumber() != 0) {
            RechargeReport lifeRecharge = recharge;
            lifeRecharge.setTerm(1);
            lifeRecharge.setNumber(recharge.getLifeNumber());
            lifeRecharge.setUnitPrice(recharge.getLifeUnitPrice());
            lifeRecharge.setTotalCollection(recharge.getLifeTotalCollection());
            lifeRecharge.setRatePrice(recharge.getLifeRatePrice());
            rechargeReportMapper.insert(lifeRecharge);
        }
    }

    public void updateRecharge(RechargeReport recharge) {
        rechargeReportMapper.update(recharge);
    }

    public void deleteRecharge(RechargeReport recharge) {
        List<Integer> list = new ArrayList<>();
        String[] str = recharge.getRechIdsStr().split(",");
        for (int i = 0; i < str.length; i++) {
            list.add(Integer.valueOf(str[i]));
        }
        recharge.setRechIds(list);

        rechargeReportMapper.delete(recharge);
    }

    public List<RechargeReport> queryList(RechargeReport recharge) {
        return rechargeReportMapper.queryList(recharge);
    }

    public int isExists(RechargeReport recharge) {
        return rechargeReportMapper.isExists(recharge);
    }

    /**
     * 添加总计数据
     * 
     * @param result
     */
    public void getTotal(List<RechargeReport> result) {
        if (result != null && result.size() > 0) {
            RechargeReport rr = new RechargeReport();
            Integer number = 0;
            BigDecimal totalCollection = BigDecimal.ZERO;
            BigDecimal ling = BigDecimal.ZERO;
            BigDecimal sumRatePrice = BigDecimal.ZERO;
            for (int i = 0; i < result.size(); i++) {
                number = result.get(i).getNumber() == null ? number + 0 : number + result.get(i).getNumber();
                totalCollection = result.get(i).getTotalCollection() == null ? totalCollection.add(ling)
                        : totalCollection.add((result.get(i)).getTotalCollection());
                if (result.get(i).getRatePrice() == null) { // 税费
                    sumRatePrice = sumRatePrice.add(BigDecimal.ZERO);
                } else {
                    sumRatePrice = sumRatePrice.add(result.get(i).getRatePrice());
                }
            }
            rr.setUnitName("总计");
            rr.setNumber(number);
            rr.setTotalCollection(totalCollection);
            rr.setRatePrice(sumRatePrice);
            result.add(rr);
        }
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
    public void ExportDatas(RechargeReport report, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<RechargeReport> reports = queryList(report);
        getTotal(reports);
        String[] fieldNames = new String[] { "单位名称", "充值日期", "IMEI号", "一年/终身", "数量", "单价(￥)", "收款合计(￥)", "税费(￥)",
                "汇款方式", "订单状态", "备注" };
        Map map = new HashMap();
        map.put("size", reports.size() + 2);
        map.put("peList", reports);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("平台充值报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("Recharge.ftl").toString();
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
        List<RechargeReport> successList = new ArrayList<RechargeReport>();// 保存可以插入成功的表的数据
        List<String> repeatList = new ArrayList<String>();// 重复数据
        Object[] result;
        try {
            result = ReadExcel.readXls(file, null);
            for (int i = 0; i < result.length; i++) {
                Object[] m = (Object[]) result[i];
                if (m != null && m.length > 0) {
                    for (int j = 2; j < m.length; j++) { // TODO 行
                        RechargeReport s = new RechargeReport();
                        // RechargeReport对象的属性值{unitName[0]、rechargeDate[1]、imei[2]、term[3]、number[4]、unitPrice[5]、totalCollection[6]、ratePrice[7]、remittanceMethod[8]、orderStatus[9]、remark[10]}
                        Object[] obj = new Object[11];

                        Object[] n = (Object[]) m[j];
                        if (n != null && n.length > 0) {
                            for (int k = 0; k < n.length; k++) { // TODO 列
                                obj[k] = n[k];
                            }
                            try {
                                // 判断非空字段，如有为空，直接抛出异常
                                if (StringUtils.isBlank((String) obj[0]) || StringUtils.isBlank((String) obj[1])
                                        || StringUtils.isBlank((String) obj[3]) || StringUtils.isBlank((String) obj[4])
                                        || StringUtils.isBlank((String) obj[5]) || StringUtils.isBlank((String) obj[6])
                                        || StringUtils.isBlank((String) obj[8])) {
                                    throw new RuntimeException();
                                } else {
                                    s.setUnitName((String) obj[0]);
                                    s.setRechargeDate(DateUtil.getTimestampByStr((String) obj[1]));
                                    s.setImei((String) obj[2]);
                                    s.setTerm((obj[3].toString().equals("一年")) ? 0 : 1);
                                    s.setNumber(Integer.parseInt(obj[4].toString()));
                                    s.setUnitPrice(BigDecimal.valueOf(Double.valueOf((String) obj[5])));
                                    s.setTotalCollection(BigDecimal.valueOf(Double.valueOf((String) obj[6])));
                                    s.setRatePrice(StringUtils.isBlank((String) obj[7]) ? BigDecimal.ZERO : BigDecimal
                                            .valueOf(Double.valueOf((String) obj[7])));
                                    String payMethod = (String) obj[8];
                                    if (payMethod.equals("人工付款")) {
                                        s.setRemittanceMethod("personPay");
                                    } else if (payMethod.equals("支付宝付款")) {
                                        s.setRemittanceMethod("aliPay");
                                    } else if (payMethod.equals("微信付款")) {
                                        s.setRemittanceMethod("weChatPay");
                                    } else {
                                        s.setRemittanceMethod(payMethod);
                                    }
                                    s.setOrderStatus((String) obj[9]);
                                    s.setRemark((String) obj[10]);
                                    if (isExists(s) == 0) {
                                        try {
                                            rechargeReportMapper.insert(s);
                                            successList.add(s);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        repeatList.add((repeatList.size() + 1) + ":第" + (j + 1)
                                                + "行数据已存在,未导入,请检查......");
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
