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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.report.mapper.ReportMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.report.RepairAgainDetail;
import cn.concox.vo.report.Report;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service("reportService")
@Scope("prototype")
public class ReportService {

    @Resource(name = "reportMapper")
    private ReportMapper<Report> reportMapper;

    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 配件报价报表数据源
     * 
     * @param report
     *            报表对象
     * @return List<Report> 数据集合
     */
    public List<Report> getAccqueteList(Report report) {
        return reportMapper.getAccqueteList(report);
    }

    /**
     * 导出数据
     * 
     * @param reports
     * @param request
     * @param response
     * @throws IOException
     * @throws ParseException
     * @throws MalformedTemplateNameException
     * @throws TemplateNotFoundException
     */
    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    public void ExportDatas(Report report, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Report> reports = reportMapper.getAccqueteList(report);

        String[] fieldNames = new String[] { "市场型号", "主板型号", "编码", "名称", "规格", "用量", "零售价（￥）" };
        Map map = new HashMap();
        map.put("size", reports.size() + 2);
        map.put("peList", reports);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("配件报价报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("AccQuote.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

    /**
     * 获取受理总数
     * 
     * @return
     */
    public Integer getCountRepair(Report report) {
        return reportMapper.getCountRepair(report);
    }

    /**
     * 获取放弃维修总数
     * 
     * @return
     */
    public Integer getGiveUpRepair(Report report) {
        // TODO Auto-generated method stub
        return reportMapper.getGiveUpRepairCount(report);
    }

    /**
     * 获取二次维修总数
     * 
     * @return
     */
    public Integer countTwiceRepair(Report report) {
        // TODO Auto-generated method stub
        return reportMapper.countTwiceRepair(report);
    }

    /**
     * 二次返修报表
     * 
     * @param report
     * @return
     */
    public List<Report> getRepairAgainList(Report report) {
        List<Report> reportList = reportMapper.getRepairAgainList(report);
        for (Report r : reportList) {
            if (StringUtils.isBlank(r.getReturnTimes())) {
                r.setReturnTimes("0");
                r.setReturnTimesP("/");
            }
            if (StringUtils.isBlank(r.getReturnTimesA())) {
                r.setReturnTimesA("0");
                r.setReturnTimesP("0%");
            }
        }
        return reportList;
    }

    /**
     * 导出二次维修数据
     * 
     * @param report
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    public void doExportRepairADatas(Report report, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Report> reports = getRepairAgainList(report);
        Integer sumCount = getCountRepair(report);// 受理数量
        Integer giveUpRepair = getGiveUpRepair(report); // 放弃维修总数
        Integer twiceRepair = countTwiceRepair(report);// 二次返修数量
        DecimalFormat format = new DecimalFormat("0.00");
        String twiceRepairStr = twiceRepair + "("
                + format.format((double) twiceRepair / (sumCount - giveUpRepair) * 100) + "%)";
        String[] fieldNames = new String[] { "维修员  ", "返修数量 ", "二次维修数量 ", "返修率 " };

        String startTime = report.getStratTime(), endTime = report.getEndTime();
        StringBuilder dispaly = new StringBuilder();
        boolean stNull = (startTime == null || "".equals(startTime)), etNull = (endTime == null || "".equals(endTime));
        if (!stNull && !etNull) {
            dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
        }
        if (stNull && !etNull) {
            dispaly.append("(--").append(endTime).append(")");
        }
        if (!stNull && etNull) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endTime = sdf.format(date).toString();
            dispaly.append("(").append(startTime).append("--").append(endTime).append(")");
        }
        Map map = new HashMap();
        map.put("dispalyDate", dispaly);
        map.put("size", reports.size() + 3);
        map.put("sumCount", sumCount);
        map.put("giveUpRepair", giveUpRepair);
        map.put("twiceRepair", twiceRepairStr);
        map.put("peList", reports);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("二次返修报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("RepaAgain.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

    /**
     * 导出二次维修详情数据
     * 
     * @param report
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    public void doExportRepairDetail(RepairAgainDetail report, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String engineer = report.getEngineer();
        if (null != engineer && !"".equals(engineer)) {
            report.setEngineer(new String(engineer.getBytes("iso8859-1"), "utf-8"));
        }
        List<RepairAgainDetail> reports = getRepairAgainDetailList(report);
        String[] fieldNames = new String[] { "送修单位", "机型", "IMEI", "保内/保外", "初检故障",  "最终故障", "处理方法", "上次受理时间", "上次维修工程师",
                "受理时间", "维修工程师", "返修次数", "送修备注" };
        Map map = new HashMap();
        map.put("size", reports.size() + 2);
        map.put("peList", reports);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("维修员二次返修详情报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.FREEMARKER_REPORTS).append("EngineerRepairAgain.ftl")
                .toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

    public Page<RepairAgainDetail> getRepairAgainDetailListPage(RepairAgainDetail report, Integer currentpage,
            Integer pageSize) {
        Page<RepairAgainDetail> page = new Page<RepairAgainDetail>();
        page.setCurrentPage(currentpage);
        page.setSize(pageSize);
        page = reportMapper.getRepairAgainDetailListPage(page, report);
        return page;

    }

    public List<RepairAgainDetail> getRepairAgainDetailList(RepairAgainDetail report) {
        return reportMapper.getRepairAgainDetailList(report);

    }

}
