package cn.concox.app.workflow.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ResponseBody;


import cn.concox.app.common.page.Page;
import cn.concox.app.workflow.service.WorkflowPriceService;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.ProlongCost;
import cn.concox.vo.basicdata.ProlongInfo;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowPrice;
import cn.concox.vo.workflow.WorkflowTotalpay;

/**
 * 报价
 */
@Controller
@Scope("prototype")
public class WorkflowPriceController extends BaseController {
    private Logger logger = Logger.getLogger("workflowInfo");

    @Resource(name = "workflowPriceService")
    private WorkflowPriceService workflowPriceService;

    @Resource(name = "workflowService")
    private WorkflowService workflowService;

    /**
     * 获取所有报价数据
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("price/priceList")
    @ResponseBody
    public APIContent getPriceList(@ModelAttribute WorkflowPrice w, HttpServletRequest req, CommonParam comm) {
        if (!StringUtil.isEmpty(w.getPrice_imei())) {
            String[] imeiArray = w.getPrice_imei().split(",");
            w.setImeis(imeiArray);
        }
        try {
            w.setOffer(super.getSessionUserName(req));
            String payDate = req.getParameter("payDate");
            if (!StringUtils.isBlank(payDate)) {
                w.setPayTime(Timestamp.valueOf(payDate));
            }
            List<Workflow> wfList = new ArrayList<Workflow>();
            // 是否为查询可报价批次号
            if (w.getAllPrice() != null && w.getAllPrice().equals("allPrice")) {
                wfList = workflowPriceService.getPriceRepairNumber(w);
                return super.putAPIData(wfList);
            } else {
                Page<WorkflowPrice> wfprList = workflowPriceService.getPriceList(w, comm.getCurrentpage(),
                        comm.getPageSize());
                req.getSession().setAttribute("totalValue", wfprList.getTotal());
                List<WorkflowPrice> wpList = wfprList.getResult();
                if (null != wpList && wpList.size() > 0) {
                    for (WorkflowPrice wfpr : wpList) {
                        Workflow wf = workflowService.getInfo(new Long(wfpr.getWfId()).intValue(), true);
                        if (null != wf) {
                            workflowPriceService.setProperties(wfpr, wf);
                            wfList.add(wf);
                        }
                    }
                }
                return super.putAPIData(wfList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取所有报价数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 查询当前批次下的所有维修状态
     * 
     * @param req
     * @return
     */
    @RequestMapping("price/repairStateList")
    @ResponseBody
    public APIContent getRepairStateList(HttpServletRequest req) {
        try {
            String repairnNmber = req.getParameter("price_repairnNmber");
            List<Workflow> wfList = workflowService.getRepairStateList(repairnNmber);
            return super.putAPIData(wfList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取所有维修状态数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 根据批次获取所有报价数据
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("price/priceListByrepairNumber")
    @ResponseBody
    public APIContent getPriceListByrepairNumber(@ModelAttribute WorkflowPrice w, HttpServletRequest req) {
        try {
            String payDate = req.getParameter("payDate");
            if (!StringUtils.isBlank(payDate)) {
                w.setPayTime(Timestamp.valueOf(payDate));
            }
            String repairnNmber = req.getParameter("repairnNmber");
            String state = req.getParameter("state");
            Workflow countWf = new Workflow();// 批次详情合计
            BigDecimal w_repairPrice = BigDecimal.ZERO;
            BigDecimal w_sumPrice = BigDecimal.ZERO;
            BigDecimal w_priceRepairMoney = BigDecimal.ZERO;
            countWf.setW_cusName("合计");
            List<Workflow> wfList = workflowPriceService.getPriceListByrepairNumber(repairnNmber, state);
            for (Workflow wf : wfList) {
                wf = workflowService.getSomeType(wf);
                if (wf.getW_repairPrice() != null) {
                    w_repairPrice = w_repairPrice.add(wf.getW_repairPrice());
                }
                if (wf.getW_sumPrice() != null) {
                    w_sumPrice = w_sumPrice.add(wf.getW_sumPrice());
                }
                if (wf.getW_priceRepairMoney() != null) {
                    w_priceRepairMoney = w_priceRepairMoney.add(wf.getW_priceRepairMoney());
                }
            }
            countWf.setW_repairPrice(w_repairPrice);
            countWf.setW_sumPrice(w_sumPrice);
            countWf.setW_priceRepairMoney(w_priceRepairMoney);
            wfList.add(wfList.size(), countWf);
            return super.putAPIData(wfList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据批次获取所有报价数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 已付款发送维修
     * 
     * @param Workflow
     * @param req
     * @return
     */
    @RequestMapping("price/sendRepair")
    @ResponseBody
    public APIContent sendRepair(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtils.isBlank(idsemp)) {
                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    // 报价工站查询选中的数据是否有不是已付款？有：不允许点击已付款，发送维修
                    if (workflowPriceService.getCountHasNotPayByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC
                                + ":有数据已更新，刷新后再操作");
                    }
                    workflowPriceService.sendRepair(ids);
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

    /**
     * 放弃报价发维修
     * 
     * @author TangYuping
     * @version 2017年1月7日 下午4:13:40
     * @param req
     * @return
     */
    @RequestMapping("price/giveupPriceSendRepair")
    @ResponseBody
    public APIContent giveupPriceSendRepair(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            String isPrice = req.getParameter("isPrice"); // 放弃报价
            String onepriceMark = req.getParameter("onepriceMark");
            String currentUserName = super.getSessionUserName(req);
            if (null != idsemp && !StringUtils.isBlank(idsemp)) {
                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    if (workflowPriceService.getCountHasNotPriceByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC
                                + ":有数据已更新，刷新后再操作");
                    }
                    workflowPriceService.giveupPriceSendRepair(isPrice, onepriceMark, currentUserName, ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("发送维修失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 待报价数据可 将数据返回到维修工站
     * 
     * @author TangYuping
     * @version 2017年1月12日 下午3:57:29
     * @param req
     * @return
     */
    @RequestMapping("price/sendDataToRepair")
    @ResponseBody
    public APIContent sendDataToRepair(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtils.isBlank(idsemp)) {
                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    if (workflowPriceService.getCountHasPriceByWfids(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC
                                + ":有数据已更新，刷新后再操作");
                    }
                    workflowPriceService.sendDataToRepair(ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("发送维修失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: sendNoPriceToRepair
     * @Description: 发送待付款数据到维修工站
     * @param req
     * @return
     * @author 20160308
     * @date 2017年11月3日 上午9:54:16
     */
    @RequestMapping("price/sendNoPriceToRepair")
    @ResponseBody
    public APIContent sendNoPriceToRepair(HttpServletRequest req) {
        try {
            String id = req.getParameter("id");
            String repairNumber = req.getParameter("repairNumber");
            if (!StringUtil.isRealEmpty(id) && !StringUtil.isRealEmpty(repairNumber)) {
                WorkflowPrice price = workflowPriceService.getPriceByWfid(Integer.valueOf(id));
                if (null != price && null != price.getPriceState()) {
                    if (price.getPriceState() != 2) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC
                                + ":数据已更新，刷新后再操作");
                    } else {
                        workflowPriceService.sendNoPriceToRepair(id, repairNumber);
                        return super.returnJson(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC, null);
                    }
                }
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":数据已更新，刷新后再操作");
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("发送维修失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 批量报价维修总费用
     * 
     * @param Workflow
     * @param req
     * @return
     */
    @RequestMapping("price/bathSumRepairPrice")
    @ResponseBody
    public APIContent bathSumRepairPrice(@ModelAttribute WorkflowPrice workflowPrice, HttpServletRequest req) {
        try {
            if (null != workflowPrice && workflowPrice.getPrice_repairnNmber() != null) {
                // 针对测试数据发送报价
                if (workflowPriceService.isPayByRepairNumber(workflowPrice.getPrice_repairnNmber())) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":该批次已报价付款");
                }

                if (workflowPriceService.getCountPayByRepairnNmber(workflowPrice.getPrice_repairnNmber()) > 0) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":数据已更新，刷新后再操作");
                }

                if (workflowPriceService.isAllPay(workflowPrice.getPrice_repairnNmber())) {
                    WorkflowTotalpay totalpay = workflowPriceService.bathSumRepairPrice(workflowPrice);
                    if (totalpay != null) {
                        return super.putAPIData(totalpay);
                    } else {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "该批次设备未全部发送报价，不能报价");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("批量报价维修总费用失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 导出报价数据
     * 
     * @author TangYuping
     * @version 2016年12月15日 下午1:53:50
     * @param wp
     * @param request
     * @param response
     */
    @RequestMapping("price/ExportDatas")
    @ResponseBody
    public void ExportDatas(@ModelAttribute WorkflowPrice wp, HttpServletRequest request, HttpServletResponse response) {
        try {
            workflowPriceService.ExportDatas(wp, request, response);
        } catch (Exception e) {
            logger.error("导出维修报价数据失败:", e);
        }
    }

    /**
     * 查询所有需要填写物流费的数据
     * 
     * @author TangYuping
     * @version 2016年12月19日 下午1:39:19
     * @param wf
     * @return
     */
    @RequestMapping("price/queryListLogCost")
    @ResponseBody
    public APIContent queryListLogCost(Workflow wf, @ModelAttribute CommonParam comp, HttpServletRequest request) {
        try {
            Page<Workflow> wfList = workflowPriceService
                    .queryListLogCost(wf, comp.getCurrentpage(), comp.getPageSize());
            request.getSession().setAttribute("totalValueTwo", wfList.getTotal());
            return super.putAPIData(wfList.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    /**
     * @Title: queryProlongCost 
     * @Description:查询延保价格
     * @param request
     * @return 
     * @author Wang Xirui
     * @date 2019年6月24日 下午6:16:47
     */
    @RequestMapping("price/queryProlongCost")
    @ResponseBody
    public APIContent queryProlongCost(HttpServletRequest request) {
        try {
            ProlongCost prolongCost = workflowPriceService.queryProlongCost();
            return super.putAPIData(prolongCost);
        } catch (Exception e) {
            logger.error("获取数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    /**
     * @Title: updateProlongCost 
     * @Description:更新延保价格
     * @param prolongCost
     * @param request
     * @return 
     * @author Wang Xirui
     * @date 2019年6月24日 下午6:17:05
     */
    @RequestMapping("price/updateProlongCost")
    @ResponseBody
    public APIContent updateProlongCost(ProlongCost prolongCost, HttpServletRequest request) {
        try {
            workflowPriceService.updateProlongCost(prolongCost);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("获取数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
    
    /**
     * @Title: saveProlongInfo 
     * @Description:保存延保信息
     * @param prolongInfo
     * @param request
     * @return 
     * @author Wang Xirui
     * @date 2019年7月24日 下午4:48:27
     */
    @RequestMapping("price/saveProlongInfo")
    @ResponseBody
    public APIContent saveProlongInfo(ProlongInfo prolongInfo, HttpServletRequest request) {
        try {
            String[] imeis = prolongInfo.getImeiStr().split(",");
            for(String imei : imeis) {
                prolongInfo.setImei(imei);
                workflowPriceService.saveProlongInfo(prolongInfo);
            }
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("获取数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
