package cn.concox.app.workflow.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.workflow.service.WorkflowPriceService;
import cn.concox.app.workflow.service.WorkflowTotalpayService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowPrice;
import cn.concox.vo.workflow.WorkflowTotalpay;

/**
 * 报价总记录
 */
@Controller
@Scope("prototype")
public class WorkflowTotalpayController extends BaseController {
    private Logger logger = Logger.getLogger("workflowInfo");

    @Resource(name = "workflowTotalpayService")
    private WorkflowTotalpayService workflowTotalpayService;

    @Resource(name = "workflowPriceService")
    private WorkflowPriceService workflowPriceService;

    /**
     * 保存报价记录
     * 
     * @param workflow
     *            ;
     * @param req
     * @return
     */
    @RequestMapping("totalpay/updateOrSaveInfo")
    @ResponseBody
    public APIContent updatePriceInfo(@ModelAttribute WorkflowPrice price, @ModelAttribute WorkflowTotalpay totalpay,
            HttpServletRequest req) {
        try {
            if (null != price && !StringUtil.isRealEmpty(price.getPrice_repairnNmber())) {
                // 查询报价的费用是否和维修表、报价表的最新数据一致
                BigDecimal pricePay = workflowPriceService.bathSumRepairPriceByPrice(price.getPrice_repairnNmber());

                BigDecimal repairPay = workflowPriceService.bathSumRepairPriceByRepair(price.getPrice_repairnNmber());
                if (repairPay.compareTo(pricePay) != 0) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":报价与维修的报价费用不一致");
                }

                if (repairPay.compareTo(totalpay.getRepairMoney()) != 0
                        || pricePay.compareTo(totalpay.getRepairMoney()) != 0) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":数据已更新，刷新后再操作");
                }

                // 保存报价信息，或人工付款更新报价人
                WorkflowPrice wp = new WorkflowPrice();
                wp.setPrice_repairnNmber(totalpay.getRepairNumber());
                wp.setOffer(super.getSessionUserName(req));
                workflowPriceService.updateOffer(wp);

                String isAutoPay = req.getParameter("isAutoPay");// 人工确认付款 1
                if (Integer.valueOf(isAutoPay) == 1) {
                    totalpay.setIsAutoPay(false);
                } else {
                    totalpay.setIsAutoPay(true);
                }
                String s = req.getParameter("payDate");
                if (!StringUtils.isBlank(s)) {
                    if (totalpay.getIsPay() == 0) {// 已付款的保存填写的时间
                        totalpay.setPayTime(Timestamp.valueOf(s));
                    }
                }
                if (totalpay.getRate() == null) {
                    totalpay.setRate(BigDecimal.ZERO);
                }
                if (workflowTotalpayService.doInsert(totalpay) == 0) {
                    return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "保存报价记录" + Globals.OPERA_SUCCESS_CODE_DESC);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("报价记录失败:" + e.toString());
            price.setPriceState(0L);// 状态为待报价
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 不产生维修费用的数据添加物流费
     * 
     * @author TangYuping
     * @version 2016年12月19日 下午3:27:21
     * @param wt
     * @return
     */
    @RequestMapping("totalpay/saveLogcost")
    @ResponseBody
    public APIContent saveLogcost(@ModelAttribute WorkflowTotalpay wt, HttpServletRequest req) {
        try {
            wt.setRepairMoney(BigDecimal.ZERO);
            String isAutoPay = req.getParameter("isAutoPay");// 人工确认付款 1
            if (Integer.valueOf(isAutoPay) == 1) {
                wt.setIsAutoPay(false);
                wt.setIsPay(0);
            } else {
                wt.setIsAutoPay(true);
                wt.setIsPay(1);
            }
            if (workflowTotalpayService.doInsert(wt) == 0) {
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "物流费添加成功" + Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }

        } catch (Exception e) {
            logger.error("物流费添加失败：" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("totalpay/getByRepairNumber")
    @ResponseBody
    public APIContent getByRepairNumber(HttpServletRequest req) {
        try {
            String repairNumber = req.getParameter("repairnNmber");
            return super.putAPIData(workflowTotalpayService.getByRepairNumber(repairNumber));
        } catch (Exception e) {
            logger.error("查询批次报价信息失败" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "查询批次报价信息" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: autoTotalpay
     * @Description:检查可报价批次，可报价批次自动报价
     * @param req
     * @return
     * @author Wang Xirui
     * @date 2019年5月30日 下午3:29:27
     */
    @RequestMapping("totalpay/autoTotalpay")
    @ResponseBody
    public APIContent autoTotalpay() {
        try {
            List<String> repairNumberList = new ArrayList<>();
            List<String> failedList = new ArrayList<>();

            List<Workflow> wfList = workflowPriceService.getPriceRepairNumber(new WorkflowPrice());
            for (Workflow workflow : wfList) {
                // 待报价：3
                if (workflow.getW_priceState() != 3){
                    continue;
                }
                
                WorkflowTotalpay totalpay = new WorkflowTotalpay();
                String repairNumber = workflow.getRepairnNmber();

                // 查询报价的费用是否和维修表、报价表的最新数据一致
                BigDecimal pricePay = workflowPriceService.bathSumRepairPriceByPrice(repairNumber);
                BigDecimal repairPay = workflowPriceService.bathSumRepairPriceByRepair(repairNumber);
                if (repairPay.compareTo(pricePay) != 0) {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC + ":报价与维修的报价费用不一致");
                }
                // 保存报价信息
                WorkflowPrice wp = new WorkflowPrice();
                wp.setPrice_repairnNmber(repairNumber);
                wp.setOffer("自动报价");
                workflowPriceService.updateOffer(wp);

                // 默认设置：未付款；税率0；物流费0；顺付
                BigDecimal logCost = BigDecimal.ZERO;
                totalpay.setIsPay(1);
                totalpay.setLogCost(logCost);
                totalpay.setRepairNumber(repairNumber);
                totalpay.setRepairMoney(repairPay);
                totalpay.setTotalMoney(repairPay.add(logCost));
                totalpay.setIsAutoPay(false);
                totalpay.setRate(BigDecimal.ZERO);
                totalpay.setRatePrice(totalpay.getTotalMoney().multiply(BigDecimal.ZERO));
                totalpay.setBatchPjCosts(BigDecimal.ZERO);
                totalpay.setReceipt("1");
                totalpay.setCustomerReceipt("Y");
                totalpay.setPriceRemark("自动报价");

                int success = workflowTotalpayService.doInsert(totalpay);
                if (success == 0) {
                    repairNumberList.add(repairNumber);
                    continue;
                }
                failedList.add(repairNumber);
            }
            if (failedList.size() > 0) {
                logger.error("自动报价失败，批次号：" + failedList.toString());
            }
            if(repairNumberList.size() < 1){
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "无可报价批次");
            }
            
            return super.putAPIData(repairNumberList);
        } catch (Exception e) {
            logger.error("自动报价失败");
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "自动报价失败" + Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
