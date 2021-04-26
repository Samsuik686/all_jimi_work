package cn.concox.app.workflow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowRepairService;
import cn.concox.app.workflow.service.WorkflowTestService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowTest;

/**
 * 
 * @author TangYuping
 * @version 2017年2月20日 下午5:47:55
 */
@Controller
@RequestMapping("/workflowTest")
@Scope("prototype")
public class WorkflowTestController extends BaseController {

    Logger logger = Logger.getLogger("workflowInfo");

    @Autowired
    private WorkflowTestService workflowTestService;
    
    @Autowired
    private WorkflowRepairService workflowRepairService;

    @RequestMapping(value = "/workflowTestList")
    @ResponseBody
    public APIContent workflowTestList(@ModelAttribute Workflow wf, @ModelAttribute CommonParam comp,
            HttpServletRequest req) {
        try {
            Page<Workflow> list = workflowTestService.workflowTestList(wf, comp.getCurrentpage(), comp.getPageSize());
            req.getSession().setAttribute("totalValue", list.getTotal());
            List<Workflow> wl = list.getResult();
            return super.putAPIData(wl);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取测试工站数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 测试站返回数据到 数据来源站 受理/维修/终检
     * 
     * @author TangYuping
     * @version 2017年2月21日 下午3:41:26
     * @param req
     * @return
     */
    @RequestMapping("/returnToDataSource")
    @ResponseBody
    public APIContent sendWorkTest(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");

                if (ids != null && ids.length > 0) {
                    if (workflowTestService.getCountToComeBackByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    workflowTestService.returnDataSrouce(ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据返回失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 测试工站添加，修改测试结果
     * 
     * @author TangYuping
     * @version 2017年2月22日 上午9:35:25
     * @param wt
     * @return
     */
    @RequestMapping("/saveOrUpdateTestResult")
    @ResponseBody
    public APIContent saveOrUpdateTestResult(@ModelAttribute WorkflowTest wt, HttpServletRequest req) {
        try {
            if (wt.getWfId() != null && wt.getWfId() > 0) {
                if (workflowTestService.getCountUpdateByWfids(wt.getWfId().toString()) != 1) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                }
                String currentUserName = super.getSessionUserName(req);
                workflowTestService.saveOrUpdateTestResult(wt, currentUserName);
                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据更新失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取数据到自己名下
     * 
     * @author TangYuping
     * @version 2017年3月2日 上午9:40:11
     * @param req
     * @return
     */
    @RequestMapping("/getData")
    @ResponseBody
    public APIContent getDataSource(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    if (workflowTestService.getCountUpdateByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    String currentUserName = super.getSessionUserName(req);
                    workflowTestService.updateTestPerson(currentUserName, ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            }
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "获取数据成功");
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }

    }

    /**
     * 推送给其他人
     * 
     * @param req
     * @return
     */
    @RequestMapping("/sendToOther")
    @ResponseBody
    public APIContent sendToOther(@RequestParam("testPerson") String testPerson, HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    if (workflowTestService.getCountUpdateByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    workflowTestService.updateTestPerson(testPerson, ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            }
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "发送成功！");
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: notPrice
     * @Description:更变设备为：不报价，测试中，更变后报价时将忽略此设备
     * @param req
     * @return
     * @author Wang Xirui
     * @date 2019年6月17日 下午3:20:35
     */
    @RequestMapping("/notPrice")
    @ResponseBody
    public APIContent notPrice(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            String ids[] = idsemp.split(",");
            String currentUserName = super.getSessionUserName(req);
            
            workflowTestService.setNotPrice(currentUserName, ids);

            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("数据返回失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
