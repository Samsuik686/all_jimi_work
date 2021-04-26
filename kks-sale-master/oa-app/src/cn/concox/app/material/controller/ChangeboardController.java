package cn.concox.app.material.controller;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.concox.app.common.page.Page;
import cn.concox.app.material.service.ChangeboardService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.material.Changeboard;

@Controller
@Scope("prototype")
public class ChangeboardController extends BaseController {
    private static Logger log = Logger.getLogger(ChangeboardController.class);

    @Resource(name = "changeboardService")
    public ChangeboardService changeboardService;

    /**
     * 根据日期查询条件显示数据
     * 
     * @param xspjcostsReport
     * @param request
     */
    public void queryAbout(Changeboard changeboard, HttpServletRequest request) {
        String startTime = request.getParameter("startDate");
        if (!StringUtils.isBlank(startTime)) {
            changeboard.setStartTime(startTime);
        }

        String endTime = request.getParameter("endDate");
        if (!StringUtils.isBlank(endTime)) {
            changeboard.setEndTime(endTime);
        }
    }

    /**
     * 分页查询
     * 
     * @param changeboard
     * @param request
     * @return
     */
    @RequestMapping("changeboard/changeboardList")
    @ResponseBody
    public APIContent getChangeboardListPage(@ModelAttribute Changeboard changeboard, @ModelAttribute CommonParam comp,
            HttpServletRequest request) {
        try {
            queryAbout(changeboard, request);
            Page<Changeboard> changeboardListPage = changeboardService.getChangeboardListPage(changeboard,
                    comp.getCurrentpage(), comp.getPageSize());
            request.getSession().setAttribute("totalValue", changeboardListPage.getTotal());
            return super.putAPIData(changeboardListPage.getResult());
        } catch (NumberFormatException e) {
            log.error("查询保内对换主板信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询保内对换主板信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("changeboard/insertChangeboard")
    @ResponseBody
    public APIContent insertChangeboard(@ModelAttribute("changeboard") Changeboard changeboard,
            HttpServletRequest request) {
        try {
            String s = request.getParameter("appDate");
            if (!StringUtils.isBlank(s)) {
                changeboard.setAppTime(Timestamp.valueOf(s));
            }
            changeboard.setApplicater(super.getSessionUserName(request));
            changeboardService.insertChangeboard(changeboard);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "保内对换主板信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            log.error("保内对换主板信息增加失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "保内对换主板信息增加" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("changeboard/getCountByWfId")
    @ResponseBody
    public APIContent getCountByWfId(@ModelAttribute("changeboard") Changeboard changeboard,
            HttpServletRequest request) {
        try {
            int ret = changeboardService.getCountByWfId(changeboard.getWfId());
            return super.putAPIData(ret);
        } catch (Exception e) {
            log.error("验证是否已申请" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "验证是否已申请" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("changeboard/updateChangeboardBatch")
    @ResponseBody
    public APIContent updateChangeboardBatch(String changeboardStr, HttpServletRequest request) {
        try {
            List<Changeboard> changeboardList = JSON.parseArray(changeboardStr, Changeboard.class);
            changeboardService.updateChangeboard(changeboardList, request);

            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "保内对换主板信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            log.info("保内对换主板信息修改失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "保内对换主板信息修改" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    @RequestMapping("changeboard/updateChangeboard")
    @ResponseBody
    public APIContent updateChangeboard(@ModelAttribute("changeboard") Changeboard changeboard, HttpServletRequest request) {
        try {
            String chargerUpdateTime = request.getParameter("chargerUpdateDate");
            if(!StringUtils.isBlank(chargerUpdateTime)){
                changeboard.setChargerUpdateTime(Timestamp.valueOf(chargerUpdateTime));
            }
            String managerUpdateTime = request.getParameter("managerUpdateDate");
            if(!StringUtils.isBlank(managerUpdateTime)){
                changeboard.setManagerUpdateTime(Timestamp.valueOf(managerUpdateTime));
            }
            String serviceUpdateTime = request.getParameter("serviceUpdateDate");
            if(!StringUtils.isBlank(serviceUpdateTime)){
                if("kong".equals(serviceUpdateTime)){
                    changeboard.setServiceUpdateTime(null);
                }else{
                    changeboard.setServiceUpdateTime(Timestamp.valueOf(serviceUpdateTime));
                }
            }
            String testBackTime = request.getParameter("testBackDate");
            if(!StringUtils.isBlank(testBackTime)){
                if("kong".equals(testBackTime)){
                    changeboard.setTestBackTime(null);
                }else{
                    changeboard.setTestBackTime(Timestamp.valueOf(testBackTime));
                }
            }
            changeboardService.updateChangeboard(changeboard);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "保内对换主板信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            log.info("保内对换主板信息修改失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "保内对换主板信息修改" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("changeboard/deleteChangeboard")
    @ResponseBody
    public APIContent deleteChangeboard(@ModelAttribute("changeboard") Changeboard changeboard,
            HttpServletRequest request) {
        try {
            changeboardService.deleteChangeboard(changeboard);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "保内对换主板信息删除" + Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            log.error("保内对换主板信息删除失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "保内对换主板信息删除" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("changeboard/getChangeboard")
    @ResponseBody
    public APIContent getChangeboard(@ModelAttribute("changeboard") Changeboard changeboard,
            HttpServletRequest request) {
        try {
            Changeboard c = changeboardService.getChangeboard(changeboard);
            return super.putAPIData(c);
        } catch (Exception e) {
            log.error("获取保内对换主板信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取保内对换主板信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("changeboard/getList")
    @ResponseBody
    public APIContent getList(@ModelAttribute("changeboard") Changeboard changeboard, HttpServletRequest request) {
        try {
            List<Changeboard> c = changeboardService.queryList(changeboard);
            return super.putAPIData(c);
        } catch (Exception e) {
            log.error("获取保内对换主板信息列表失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取保内对换主板信息列表" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 导出保内对换主板数据
     * 
     * @param changeboard
     * @param request
     * @param response
     */
    @RequestMapping("changeboard/ExportDatas")
    @ResponseBody
    public void ExportDatas(@ModelAttribute Changeboard changeboard, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            queryAbout(changeboard, request);
            changeboardService.ExportDatas(changeboard, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出保内对换主板数据失败:" + e.toString());
        }
    }
}
