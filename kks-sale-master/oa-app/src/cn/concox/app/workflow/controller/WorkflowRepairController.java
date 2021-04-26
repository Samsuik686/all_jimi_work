package cn.concox.app.workflow.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.RepairPriceService;
import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowRepairService;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.Convert;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.RepairPriceManage;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowRepair;

/**
 * 维修工作站
 * 
 * @author Li.Shangzhi
 * @date 2016-08-16 11:39:29
 */
@Controller
@Scope("prototype")
public class WorkflowRepairController extends BaseController {
    private static Logger logger = Logger.getLogger("workflowInfo");

    @Resource(name = "workflowRepairService")
    private WorkflowRepairService workflowRepairService;

    @Resource(name = "workflowService")
    private WorkflowService workflowService;

    @Resource(name = "repairPriceService")
    public RepairPriceService repairPriceService;

    /**
     * 获取所有维修数据
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("repair/repairPage")
    @ResponseBody
    public APIContent getRepairPage(@ModelAttribute WorkflowRepair repair, HttpServletRequest req,
            @ModelAttribute CommonParam comp) {
        try {
            repair.setEngineer(super.getSessionUserName(req));
            Page<WorkflowRepair> wfrList = workflowRepairService.getRepairPage(repair, comp.getCurrentpage(),
                    comp.getPageSize());
            req.getSession().setAttribute("totalValue", wfrList.getTotal());
            List<WorkflowRepair> wrList = wfrList.getResult();

            // TODO 数据封装
            List<Workflow> wfList = new ArrayList<Workflow>();
            if (null != wrList && wrList.size() > 0) {
                for (WorkflowRepair wfr : wrList) {
                    Workflow wf = workflowService.getInfo(new Long(wfr.getWfId()).intValue(), true);
                    if (null != wf) {
                        if (!StringUtil.isEmpty(wf.getW_model()) && !StringUtil.isEmpty(wf.getW_marketModel())) {
                            wf.setW_model(new StringBuilder(wf.getW_model()).append(" | ").append(wf.getW_marketModel())
                                    .toString());
                        }
                        setProperties(wfr, wf);
                        wfList.add(wf);
                    }
                }
            }
            return super.putAPIData(wfList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取所有维修数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取所有维修数据
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("repair/repairList")
    @ResponseBody
    public APIContent getRepairList(@ModelAttribute WorkflowRepair repair, HttpServletRequest req) {
        try {
            repair.setEngineer(super.getSessionUserName(req));
            String userId = super.getSessionUser(req).getuId();
            List<WorkflowRepair> wfrList = workflowRepairService.getRepairList(repair, userId);
            // TODO 数据封装
            List<Workflow> wfList = new ArrayList<Workflow>();
            if (null != wfrList && wfrList.size() > 0) {
                for (WorkflowRepair wfr : wfrList) {
                    if (wfr.getWfId() != null && StringUtils.isNotBlank(wfr.getWfId().toString())) {
                        Workflow wf = workflowService.getInfo(new Long(wfr.getWfId()).intValue(), true);
                        if (null != wf) {
                            if (!StringUtil.isEmpty(wf.getW_model()) && !StringUtil.isEmpty(wf.getW_marketModel())) {
                                wf.setW_model(new StringBuilder(wf.getW_model()).append(" | ")
                                        .append(wf.getW_marketModel()).toString());
                            }
                            setProperties(wfr, wf);
                            wfList.add(wf);
                        }
                    }
                }
            }
            return super.putAPIData(wfList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取所有维修数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取维修详情
     * 
     * @param repair
     * @param req
     * @return
     */
    @RequestMapping("repair/getInfo")
    @ResponseBody
    public APIContent getInfo(@ModelAttribute WorkflowRepair wfr, HttpServletRequest req) {
        try {
            if (null != wfr && wfr.getId() > 0) {
                WorkflowRepair repair = workflowRepairService.getT(wfr.getId());
                if (null != repair) {
                    Workflow wf = workflowService.getInfo(repair.getWfId(), true);
                    setProperties(repair, wf);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, wf);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("获取维修详情失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 修改维修
     * 
     * @param workflow
     *            ;
     * @param req
     * @return
     */
    @RequestMapping("repair/updateInfo")
    @ResponseBody
    public APIContent updaterepairInfo(@ModelAttribute WorkflowRepair w, HttpServletRequest req) {
        try {
            if (w.getWfId() != null && StringUtils.isNotBlank(w.getWfId().toString())) {
                WorkflowRepair wr = workflowRepairService.getRepairByWfId(w.getWfId());
                // 判断是否需要维修
                if (wr.getRepairState() == -1) { 
                    // 在维修工站的数据都可以操作，修改最终故障，组件
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "设备不允许再维修");
                }
                if (wr.getRepairState() != w.getRepairState()) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                }
                boolean success = workflowRepairService.update(w);
                if (success) {
//                    RepairPriceManage manage = new RepairPriceManage();
//                    
//                    
//                    repairPriceService.insertRepairPrice(manage);

                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
                }
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);

            }
            boolean success = workflowRepairService.updateWl(w);
            if (success) {
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "组件信息修改成功");
            }
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "组件信息修改失败");
            
        } catch (Exception e) {
            logger.error("修改维修失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 发送报价
     * 
     * @param Workflow
     * @param req
     * @return
     */
    @RequestMapping("repair/sendPrice")
    @ResponseBody
    public APIContent sendPrice(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {

                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    if (workflowRepairService.getCountToPriceByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    workflowRepairService.sendPrice(ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送报价失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 点击报价后，维修员已分拣的该批次下的已分拣、待维修状态要变成不报价、待维修
     * 
     * @param Workflow
     * @param req
     * @return
     */
    @RequestMapping("repair/sendPriceUpdateState")
    @ResponseBody
    public APIContent sendPriceUpdateState(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("notPriceIds");
            String notPriceRepairNumber = req.getParameter("notPriceRepairNumber");
            if (null != idsemp && !StringUtil.isRealEmpty(idsemp) && null != notPriceRepairNumber
                    && !StringUtil.isRealEmpty(notPriceRepairNumber)) {
                String ids[] = idsemp.split(",");
                String rep[] = notPriceRepairNumber.split(",");

                Set<String> repSet = new HashSet<String>();
                for (int i = 0; i < rep.length; i++) {
                    repSet.add(rep[i]);
                }
                String repairNumbers[] = (String[]) repSet.toArray(new String[repSet.size()]);

                workflowRepairService.sendPriceUpdateState(repairNumbers, ids);

                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("点击报价后，已分拣、待维修状态要变成不报价、待维修失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 发送终检
     * 
     * 
     * @param req
     * @return
     */
    @RequestMapping("repair/sendFicheck")
    @ResponseBody
    public APIContent sendFicheck(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");

                if (ids != null && ids.length > 0) {
                    if (workflowRepairService.getCountRepairedByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    workflowRepairService.sendFicheck(ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("发送终检失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 发送装箱
     * 
     * @param Workflow
     * @param req
     * @return
     */
    @RequestMapping("repair/sendPack")
    @ResponseBody
    public APIContent sendPack(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtils.isBlank(idsemp)) {
                String ids[] = idsemp.split(",");

                if (ids != null && ids.length > 0) {
                    if (workflowRepairService.getCountToPackByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    for (int i = 0; i < ids.length; i++) {
                        Workflow wf = workflowService.getT(Integer.valueOf(ids[i]));
                        if (null != wf && null != wf.getRepairnNmber()) {
                            workflowRepairService.sendPack(wf, ids);
                        } else {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                        }
                    }
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }

            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("发送装箱失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 把维修数据放入公共数据里
     * 
     * @param wfr
     * @param wf
     */
    public void setProperties(WorkflowRepair wfr, Workflow wf) {
        if (null != wfr) {
            wf.setW_modelType(wfr.getRepair_modelType());
            wf.setW_repairId(wfr.getId());
            wf.setW_isRW(wfr.getIsRW());
            wf.setW_isPay(wfr.getIsPay());
            wf.setW_engineer(wfr.getEngineer());
            wf.setW_repairState(wfr.getRepairState());
            wf.setW_repairStateFalg(wfr.getRepairState());
            wf.setW_repairRemark(wfr.getRepairRemark());
            wf.setW_solution(wfr.getSolution());
            wf.setW_priceRemark(wfr.getPriceRemark());
            wf.setW_solutionTwo(wfr.getSolutionTwo());
            wf.setW_payTime(wfr.getRepair_payTime());
            wf.setPrice_createTime(wfr.getRepair_sendPriceTime());
            wf.setAcceptRemark(wfr.getRepair_acceptRemark());
            wf.setRemark(wfr.getRepair_remark());
            wf.setRepairNumberRemark(wfr.getRepair_repairNumberRemark());
            wf.setW_priceRemark(wfr.getRepair_priceRemark());
            wf.setW_onePriceRemark(wfr.getRepair_onePriceRemark());
            wf.setTestPerson(wfr.getTestPerson());
            if (null != wfr.getTestResult() && !"".equals(wfr.getTestResult())) {
                String result = "";
                if (wfr.getTestPerson() != null && wfr.getTestTime() != null) {
                    result = "———" + wfr.getTestPerson() + "    " + wfr.getTestTime();
                }
                wf.setTestResult(wfr.getTestResult() + result.substring(0, result.lastIndexOf(".")));
            }

            if (null != wfr.getRepairAgainCount()) {
                wf.setW_repairAgainCount(wfr.getRepairAgainCount());
            } else {
                wf.setW_repairAgainCount(0);
            }
            if (null != wfr.getRepairPrice()) {
                wf.setW_repairPrice(wfr.getRepairPrice());
            } else {
                wf.setW_repairPrice(BigDecimal.ZERO);
            }
            if (null != wfr.getHourFee()) {
                wf.setW_hourFee(wfr.getHourFee());
            } else {
                wf.setW_hourFee(BigDecimal.ZERO);
            }
            wfr.setSumPrice(wf.getW_repairPrice().add(wf.getW_hourFee()));
            wf.setW_sumPrice(wfr.getSumPrice());
            wf.setBill(wfr.getRepair_bill());
            wf.setSfVersion(wfr.getRepair_sfVersion());
            wf.setInsideSoftModel(wfr.getRepair_insideSoftModel());
            wf.setOutCount(wfr.getRepair_outCount());
            wf.setTestMatStatus(wfr.getRepair_testMatStatus());
            workflowService.setBackDate(wf);

        }
    }

    /**
     * 不报价
     * 
     * @param repair
     * @param req
     * @return
     */
    @RequestMapping("repair/notPrice")
    @ResponseBody
    public APIContent notPrice(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtils.isBlank(idsemp)) {
                String ids[] = idsemp.split(",");

                if (ids != null && ids.length > 0) {
                    if (workflowRepairService.getCountNoPriceByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    for (String id : ids) {
                        WorkflowRepair wfr = workflowRepairService.getRepairByWfId(Convert.getInteger(id));
                        wfr.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        if (workflowRepairService.notPrice(wfr, ids) != 0) {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                        }
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("不报价操作失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 维修工站查询选中的数据返回分拣
     * 
     * @author TangYuping
     * @version 2017年1月12日 下午2:33:43
     * @param req
     * @return
     */
    @RequestMapping("repair/sendDataToSort")
    @ResponseBody
    public APIContent sendDataToSort(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            String repairStates = req.getParameter("repairState");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");

                if (ids != null && ids.length > 0) {
                    if (workflowRepairService.getCountToSortByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    workflowRepairService.sendSort(repairStates, ids);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("发送维修失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
