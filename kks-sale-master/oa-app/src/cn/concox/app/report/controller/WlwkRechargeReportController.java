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
import cn.concox.app.report.service.WlwkRechargeReportService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.report.WlwkRechargeReport;

@Controller
@Scope("prototype")
public class WlwkRechargeReportController extends BaseController {
    private static Logger logger = Logger.getLogger(WlwkRechargeReportController.class);

    @Resource(name = "wlwkRechargeReportService")
    public WlwkRechargeReportService wlwkRechargeReportService;

    /**
     * 根据查询条件显示数据
     * 
     * @param xspjcostsReport
     * @param request
     */
    public void queryAbout(WlwkRechargeReport rechargeReport, HttpServletRequest request) {
        String searchNOsTemp = request.getParameter("searchNOs");
        String searFormulasTemp = request.getParameter("searFormulas");
        if (!StringUtils.isBlank(searchNOsTemp)) {
            List<String> ls = new ArrayList<String>();
            String[] searchNO = searchNOsTemp.split(",");
            String searchNOs = "";
            if (searchNO.length > 0) {
                for (String s : searchNO) {
                    if (null != s && !"".equals(s)) {
                        ls.add(s);
                    }
                }
                if (ls.size() > 0) {
                    for (int i = 0; i < ls.size(); i++) {
                        if (i != ls.size() - 1) {
                            searchNOs += "\"" + ls.get(i) + "\"" + ",";
                        } else {
                            searchNOs += "\"" + ls.get(i) + "\"";
                        }
                    }
                }
                rechargeReport.setSearchNOs(searchNOs);
            } else {
                rechargeReport.setSearchNOs(null);
            }
        }

        if (!StringUtils.isBlank(searFormulasTemp)) {
            String[] searFormulas = searFormulasTemp.split(",");
            StringBuilder sb = new StringBuilder();
            if (searFormulas.length > 0) {
                for (int i = 0; i < searFormulas.length; i++) {
                    if (i != searFormulas.length - 1) {
                        sb.append("\"").append(searFormulas[i]).append("\",");
                    } else {
                        sb.append("\"").append(searFormulas[i]).append("\"");
                    }
                }
                rechargeReport.setSearFormulas(sb.toString());
            }
        }

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
    @RequestMapping("wlwkRechargeReport/rechargeList")
    @ResponseBody
    public APIContent getRechargeListPage(@ModelAttribute WlwkRechargeReport rechargeReport,
            @ModelAttribute CommonParam comp, HttpServletRequest request) {
        try {
            queryAbout(rechargeReport, request);
            Page<WlwkRechargeReport> rechargeListPage = wlwkRechargeReportService.getRechargeListPage(rechargeReport,
                    comp.getCurrentpage(), comp.getPageSize());
            request.getSession().setAttribute("totalValue", rechargeListPage.getTotal());
            return super.putAPIData(rechargeListPage.getResult());
        } catch (NumberFormatException e) {
            logger.error("查询物联网卡充值信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询物联网卡充值信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("wlwkRechargeReport/addOrUpdateRecharge")
    @ResponseBody
    public APIContent addOrUpdateRecharge(@ModelAttribute("recharge") WlwkRechargeReport recharge,
            HttpServletRequest request) {
        try {
            String rechargeTime = request.getParameter("rechargeTime");
            if (!StringUtils.isBlank(rechargeTime)) {
                recharge.setRechargeDate(Timestamp.valueOf(rechargeTime));
            }
            if (!StringUtil.isEmpty(recharge.getWlwkIdsStr())) {
                List<Integer> list = new ArrayList<>();
                String[] str = recharge.getWlwkIdsStr().split(",");
                for (int i = 0; i < str.length; i++) {
                    list.add(Integer.valueOf(str[i]));
                }
                recharge.setWlwkIds(list);

                wlwkRechargeReportService.updateRecharge(recharge);
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物联网卡充值信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
            }

            if (wlwkRechargeReportService.isExists(recharge) == 0) {
                wlwkRechargeReportService.insertRecharge(recharge);
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物联网卡充值信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "物联网卡充值信息已存在,请检查");
            }
        } catch (Exception e) {
            logger.error("更新物联网卡充值信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "更新物联网卡充值信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("wlwkRechargeReport/deleteRecharge")
    @ResponseBody
    public APIContent deleteRecharge(@ModelAttribute("recharge") WlwkRechargeReport recharge, HttpServletRequest request) {
        try {
            wlwkRechargeReportService.deleteRecharge(recharge);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物联网卡充值信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("物联网卡充值信息删除失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "物联网卡充值信息删除" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("wlwkRechargeReport/getRecharge")
    @ResponseBody
    public APIContent getRecharge(@ModelAttribute("recharge") WlwkRechargeReport recharge, HttpServletRequest request) {
        try {
            WlwkRechargeReport c = wlwkRechargeReportService.getRecharge(recharge);
            return super.putAPIData(c);
        } catch (Exception e) {
            logger.error("获取物联网卡充值信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取物联网卡充值信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("wlwkRechargeReport/getList")
    @ResponseBody
    public APIContent getList(@ModelAttribute("recharge") WlwkRechargeReport recharge, HttpServletRequest request) {
        try {
            queryAbout(recharge, request);
            List<WlwkRechargeReport> c = wlwkRechargeReportService.queryList(recharge);
            return super.putAPIData(c);
        } catch (Exception e) {
            logger.error("获取物联网卡充值信息列表失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取物联网卡充值信息列表" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("wlwkRechargeReport/querySum")
    @ResponseBody
    public APIContent querySum(@ModelAttribute("recharge") WlwkRechargeReport recharge, HttpServletRequest request) {
        try {
            queryAbout(recharge, request);
            List<WlwkRechargeReport> c = wlwkRechargeReportService.querySum(recharge);
            return super.putAPIData(c);
        } catch (Exception e) {
            logger.error("获取物联网卡充值信息列表失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取物联网卡充值信息列表" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 导出物联网卡充值数据
     * 
     * @param recharge
     * @param request
     * @param response
     */
    @RequestMapping("wlwkRechargeReport/ExportDatas")
    @ResponseBody
    public void ExportDatas(@ModelAttribute WlwkRechargeReport recharge, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            queryAbout(recharge, request);
            wlwkRechargeReportService.ExportDatas(recharge, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出物联网卡充值数据失败:" + e.toString());
        }
    }

    /**
     * 导入物联网卡充值
     * 
     * @param request
     * @param response
     */
    @RequestMapping("wlwkRechargeReport/ImportDatas")
    @ResponseBody
    public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
        List<String> errorList = new ArrayList<String>();
        try {
            errorList = wlwkRechargeReportService.ImportDatas(file, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入物联网卡充值信息失败:" + e.toString());
            errorList.add("导入物联网卡充值信息失败,请检查文件内数据是否与导入数据匹配以及文件格式是否正确");
        }
        return super.putAPIData(errorList);
    }
}
