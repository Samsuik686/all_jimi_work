package cn.concox.app.report.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.concox.app.common.page.Page;
import cn.concox.app.report.service.RechargeReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.RechargeReport;

@Controller
@Scope("prototype")
public class RechargeReportController extends BaseController {
    private static Logger logger = Logger.getLogger(RechargeReportController.class);

    @Resource(name = "rechargeReportService")
    public RechargeReportService rechargeReportService;

    /**
     * 根据查询条件显示数据
     * 
     * @param xspjcostsReport
     * @param request
     */
    public void queryAbout(RechargeReport rechargeReport, HttpServletRequest request) {
        String startTime = request.getParameter("startDate");
        if (!StringUtils.isBlank(startTime)) {
            rechargeReport.setStartTime(startTime);
        }

        String endTime = request.getParameter("endDate");
        if (!StringUtils.isBlank(endTime)) {
            rechargeReport.setEndTime(endTime);
        }
    }

    /**
     * 分页查询
     * 
     * @param recharge
     * @param request
     * @return
     */
    @RequestMapping("recharge/rechargeList")
    @ResponseBody
    public APIContent getRechargeListPage(@ModelAttribute RechargeReport rechargeReport,
            @ModelAttribute CommonParam comp, HttpServletRequest request) {
        try {
            queryAbout(rechargeReport, request);
            Page<RechargeReport> rechargeListPage = rechargeReportService.getRechargeListPage(rechargeReport,
                    comp.getCurrentpage(), comp.getPageSize());
            request.getSession().setAttribute("totalValue", rechargeListPage.getTotal());
            return super.putAPIData(rechargeListPage.getResult());
        } catch (NumberFormatException e) {
            logger.error("查询平台充值信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询平台充值信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("recharge/addOrUpdateRecharge")
    @ResponseBody
    public APIContent addOrUpdateRecharge(@ModelAttribute("recharge") RechargeReport recharge,
            HttpServletRequest request) {
        try {
            String rechargeTime = request.getParameter("rechargeTime");
            if (!StringUtils.isBlank(rechargeTime)) {
                recharge.setRechargeDate(Timestamp.valueOf(rechargeTime));
            }
            if (rechargeReportService.isExists(recharge) == 0) {
                if (recharge.getRechId() > 0) {
                    rechargeReportService.updateRecharge(recharge);
                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "平台充值信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
                } else {
                    rechargeReportService.insertRecharge(recharge);
                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "平台充值信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "平台充值信息已存在,请检查");
            }
        } catch (Exception e) {
            logger.error("更新平台充值信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新平台充值信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("recharge/addRecharge")
    @ResponseBody
    public APIContent addRecharge(@ModelAttribute("recharge") RechargeReport recharge, HttpServletRequest request) {
        try {
            String rechargeTime = request.getParameter("rechargeTime");
            if (!StringUtils.isBlank(rechargeTime)) {
                recharge.setRechargeDate(Timestamp.valueOf(rechargeTime));
            }
            if (rechargeReportService.isExists(recharge) == 0) {
                rechargeReportService.insertRecharge(recharge);
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "平台充值信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "平台充值信息已存在,请检查");
            }
        } catch (Exception e) {
            logger.error("新增平台充值信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "新增平台充值信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("recharge/deleteRecharge")
    @ResponseBody
    public APIContent deleteRecharge(@ModelAttribute("recharge") RechargeReport recharge, HttpServletRequest request) {
        try {
            rechargeReportService.deleteRecharge(recharge);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "平台充值信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("平台充值信息删除失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "平台充值信息删除" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("recharge/getRecharge")
    @ResponseBody
    public APIContent getRecharge(@ModelAttribute("recharge") RechargeReport recharge, HttpServletRequest request) {
        try {
            RechargeReport c = rechargeReportService.getRecharge(recharge);
            return super.putAPIData(c);
        } catch (Exception e) {
            logger.error("获取平台充值信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取平台充值信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("recharge/getList")
    @ResponseBody
    public APIContent getList(@ModelAttribute("recharge") RechargeReport recharge, HttpServletRequest request) {
        try {
            queryAbout(recharge, request);
            List<RechargeReport> c = rechargeReportService.queryList(recharge);
            return super.putAPIData(c);
        } catch (Exception e) {
            logger.error("获取平台充值信息列表失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取平台充值信息列表" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 导出平台充值数据
     * 
     * @param recharge
     * @param request
     * @param response
     */
    @RequestMapping("recharge/ExportDatas")
    @ResponseBody
    public void ExportDatas(@ModelAttribute RechargeReport recharge, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            queryAbout(recharge, request);
            rechargeReportService.ExportDatas(recharge, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出平台充值数据失败:" + e.toString());
        }
    }

    /**
     * 导入平台充值
     * 
     * @param request
     * @param response
     */
    @RequestMapping("recharge/ImportDatas")
    @ResponseBody
    public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
        List<String> errorList = new ArrayList<String>();
        try {
            errorList = rechargeReportService.ImportDatas(file, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入平台充值信息失败:" + e.toString());
            errorList.add("导入平台充值信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
        }
        return super.putAPIData(errorList);
    }
}
