package cn.concox.app.workflow.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.RepairPriceMapper;
import cn.concox.app.basicdata.mapper.SxdwmanageMapper;
import cn.concox.app.basicdata.mapper.ZbxhmanageMapper;
import cn.concox.app.basicdata.mapper.ZgzmanageMapper;
import cn.concox.app.basicdata.service.PricePjService;
import cn.concox.app.basicdata.service.TipService;
import cn.concox.app.basicdata.service.TipService.TipState;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.app.material.service.MaterialLogService;
import cn.concox.app.report.service.XspjcostsReportService;
import cn.concox.app.rolePrivilege.mapper.UserRoleMgtMapper;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.app.workflow.mapper.WorkflowPriceMapper;
import cn.concox.app.workflow.mapper.WorkflowRelatedMapper;
import cn.concox.app.workflow.mapper.WorkflowRepairMapper;
import cn.concox.app.workflow.mapper.WorkflowTotalpayMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.util.JavaNetURLRESTFulClient;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.PricePj;
import cn.concox.vo.basicdata.ProlongCost;
import cn.concox.vo.basicdata.ProlongInfo;
import cn.concox.vo.basicdata.RepairPriceManage;
import cn.concox.vo.basicdata.Saledata;
import cn.concox.vo.basicdata.Sxdwmanage;
import cn.concox.vo.basicdata.Zbxhmanage;
import cn.concox.vo.basicdata.Zgzmanage;
import cn.concox.vo.report.XspjcostsReport;
import cn.concox.vo.rolePrivilege.UserRoleMgt;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowPrice;
import cn.concox.vo.workflow.WorkflowRelated;
import cn.concox.vo.workflow.WorkflowRepair;
import cn.concox.vo.workflow.WorkflowTotalpay;
import freemarker.template.Template;
import net.sf.json.JSONObject;

/**
 * 报价
 * 
 * @author Liao.bifeng
 * @date 2016年7月26日
 */
@Service("workflowPriceService")
@Scope("prototype")
public class WorkflowPriceService {

    Logger logger = Logger.getLogger("privilege");

    @Resource(name = "workflowPriceMapper")
    private WorkflowPriceMapper<WorkflowPrice> workflowPriceMapper;

    @Resource(name = "workflowMapper")
    private WorkflowMapper<Workflow> workflowMapper;

    @Resource(name = "workflowTotalpayMapper")
    private WorkflowTotalpayMapper<WorkflowTotalpay> workflowTotalpayMapper;

    @Resource(name = "sxdwmanageMapper")
    private SxdwmanageMapper<Sxdwmanage> sxdwmanageMapper;

    @Resource(name = "workflowRepairMapper")
    private WorkflowRepairMapper<WorkflowRepair> workflowRepairMapper;

    @Resource(name = "zgzmanageMapper")
    private ZgzmanageMapper<Zgzmanage> zgzmanageMapper;

    @Resource(name = "workflowRelatedMapper")
    private WorkflowRelatedMapper<WorkflowRelated> workflowRelatedMapper;

    @Resource(name = "repairPriceMapper")
    private RepairPriceMapper<RepairPriceManage> repairPriceMapper;

    @Resource(name = "userRoleMgtMapper")
    private UserRoleMgtMapper<UserRoleMgt> userRoleMgtMapper;

    @Resource(name = "workflowService")
    private WorkflowService workflowService;

    @Resource(name = "workflowRepairService")
    private WorkflowRepairService workflowRepairService;

    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource(name = "pricePjService")
    private PricePjService pricePjService;

    @Resource(name = "materialLogService")
    private MaterialLogService materialLogService;

    @Resource(name = "xspjcostsReportService")
    public XspjcostsReportService xspjcostsReportService;

    @Resource(name = "tipService")
    private TipService tipService;

    /**
     * 新增报价数据 供维修使用
     * 
     * @param WorkflowPrice.wfId
     *            联表ID
     * @param WorkflowPrice.Offer
     *            报价员（维修工程师） * @param WorkflowPrice.isPay 默认1：未付款
     * @param WorkflowPrice.priceState
     *            报价状态 [-1：已发送 0：待报价 1:待维修 2：待装箱]
     * @param WorkflowPrice.createTime
     *            创建时间 （当前时间）
     * @return Code -1 插入失败 Code 0 插入成功 Code 1 参数错误
     */
    public Integer doInsert(WorkflowPrice wfpr) {
        try {
            if (null != wfpr && null != wfpr.getWfId()) {
                workflowPriceMapper.insert(wfpr);
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            logger.error("新增报价信息失败！" + e);
            return -1;
        }
    }

    /**
     * API 修改报价状态
     * 
     * @param priceState
     *            报价状态
     * @param ids[]
     *            WorkflowPrice.id 主键ID
     * 
     * @return Code -1 修改失败 Code 0 修改成功 Code 1 参数错误
     * @author Li.Shangzhi
     * @date 2016-08-15 17:42:23
     */
    public Integer updateState(long priceState, String... ids) {
        try {
            if (!StringUtil.isEmpty(ids.toString())) {
                workflowPriceMapper.updateState(priceState, ids);
                return 0; // TODO 修改成功
            } else {
                return 1; // TODO 参数错误
            }
        } catch (Exception e) {
            logger.info("API -- 修改报价状态  " + e.toString());
            return -1; // TODO 修改失败
        }
    }

    public WorkflowPrice getInfo(WorkflowPrice workflowPrice) {
        return workflowPriceMapper.getT(workflowPrice.getId());
    }

    public void update(WorkflowPrice workflowPrice) {
        workflowPriceMapper.update(workflowPrice);
    }

    public void delete(WorkflowPrice workflowPrice) {
        workflowPriceMapper.delete(workflowPrice);
    }

    public Page<WorkflowPrice> getPriceList(WorkflowPrice workflowPrice, Integer currentpage, Integer pageSize) {
        Page<WorkflowPrice> wpList = new Page<WorkflowPrice>();
        wpList.setCurrentPage(currentpage);
        wpList.setSize(pageSize);
        wpList = workflowPriceMapper.getPricePageList(wpList, workflowPrice);
        return wpList;
    }

    /**
     * 获取所有可报价批次号
     * 
     * @author TangYuping
     * @version 2017年1月4日 下午4:32:58
     * @param price
     * @return
     */
    public List<Workflow> getPriceRepairNumber(WorkflowPrice price) {
        List<Workflow> wpList = workflowPriceMapper.getPriceRepairNumber(price);
        return wpList;
    }

    public List<Workflow> getPriceListByrepairNumber(String repairnNmber, String state) {
        return workflowPriceMapper.queryListByrepairNumber(repairnNmber, state);
    }

    /**
     * 查询送修批号
     * 
     * @param wfId
     * @return
     */
    public Workflow getImeiState(int wfId) {
        return workflowMapper.getT(wfId);
    }

    /**
     * 获取批量报价的总费用
     * 
     * @param price_repairnNmber
     *            批次号
     * @param priceIds[]
     *            WorkflowPrice.id 主键ID
     * @return Code -1 获取批量报价的维修总费用失败 Code 0 获取批量报价的维修总费用成功 Code 1 参数错误
     */
    public WorkflowTotalpay bathSumRepairPrice(WorkflowPrice workflowPrice) {
        WorkflowTotalpay total = new WorkflowTotalpay();
        try {
            BigDecimal price = workflowPriceMapper.bathSumRepairPrice(workflowPrice.getPrice_repairnNmber());
            BigDecimal pjCost = pricePjService.getPricePjCosts(workflowPrice.getPrice_repairnNmber());
            BigDecimal prolongCost = workflowPriceMapper.getSumProlongCost(workflowPrice.getPrice_repairnNmber());
            total = workflowPriceMapper.selLOgcostByRepairNumber(workflowPrice);
            if (null == price) {
                price = BigDecimal.ZERO;
            }
            if (total == null) {
                total = new WorkflowTotalpay();
                total.setLogCost(BigDecimal.ZERO);
                total.setBatchPjCosts(BigDecimal.ZERO);
            }
            if (null == total.getLogCost()) {
                total.setLogCost(BigDecimal.ZERO);
            }
            if (null == pjCost) {
                total.setBatchPjCosts(BigDecimal.ZERO);
            }
            if (null == prolongCost) {
                total.setProlongCost(BigDecimal.ZERO);
            }
            total.setBatchPjCosts(pjCost);
            total.setSumPrice(price);
            total.setProlongCost(prolongCost);
        } catch (Exception e) {
            e.printStackTrace();
            total.setSumPrice(new BigDecimal(-1)); // TODO 获取批量报价的维修总费用失败
        }
        return total;
    }

    /**
     * @Title: bathSumRepairPriceByRepair
     * @Description:获取批次报价的报价表维修总费用
     * @param workflowPrice
     * @return
     * @author 20160308
     * @date 2017年11月8日 上午10:38:13
     */
    @SuppressWarnings("finally")
    public BigDecimal bathSumRepairPriceByPrice(String repairnNmber) {
        BigDecimal price = BigDecimal.ZERO;
        try {
            price = workflowPriceMapper.bathSumRepairPrice(repairnNmber);
        } catch (Exception e) {
            e.printStackTrace();
            price = new BigDecimal(-1); // TODO 获取批量报价的维修总费用失败
        } finally {
            return price;
        }
    }

    /**
     * @Title: bathSumRepairPriceByRepair
     * @Description:获取批次报价的维修表维修总费用
     * @param workflowPrice
     * @return
     * @author 20160308
     * @date 2017年11月8日 上午10:38:13
     */
    @SuppressWarnings("finally")
    public BigDecimal bathSumRepairPriceByRepair(String repairnNmber) {
        BigDecimal price = BigDecimal.ZERO;
        try {
            price = workflowPriceMapper.bathSumRepairPriceByRepair(repairnNmber);
        } catch (Exception e) {
            e.printStackTrace();
            price = new BigDecimal(-1); // TODO 获取批量报价的维修总费用失败
        } finally {
            return price;
        }
    }

    /**
     * 根据批次改变状态（已付款）
     * 
     * @param state
     * @param repairnNmber
     */
    @Transactional
    public void updateStateByRepairnNmber(String repairnNmber) {
        if (null != repairnNmber && !repairnNmber.equals(""))
            workflowPriceMapper.updateStateByRepairnNmber(repairnNmber);
        workflowPriceMapper.updateStateByRepairnNmberIsPay(4L, repairnNmber);// 已付款，待维修
        workflowPriceMapper.updateLogcostStatusByNmber(repairnNmber, "0"); // 修改物流费支付状态
    }

    /**
     * 根据批次改变状态（未付款）
     * 
     * @param state
     * @param repairnNmber
     */
    @Transactional
    public void updateStateByRepairnNmberIsNotPay(String repairnNmber) {
        if (null != repairnNmber && !repairnNmber.equals(""))
            workflowPriceMapper.updateStateByRepairnNmberIsNotPay(repairnNmber);// 已报价，未付款
        workflowPriceMapper.updateStateByRepairnNmberIsPay(9L, repairnNmber);// 已报价，未付款
    }

    /**
     * 供支付使用
     * 
     * @param repairnNmber
     * @return
     */
    @Transactional
    public boolean UpdateAlipayBackCode(String repairnNmber, String payType) {
        if (null != repairnNmber && !repairnNmber.equals("")) {
            WorkflowTotalpay w = workflowTotalpayMapper.getByRepairNumber(repairnNmber);
            if (null != w && null != w.getIsPay() && Integer.valueOf(w.getIsPay()) == 1) {
                workflowPriceMapper.updateStateByRepairnNmber(repairnNmber);
                workflowTotalpayMapper.updateByRepairnNmber(repairnNmber);
                workflowPriceMapper.updateStateByRepairnNmberIsPay(4L, repairnNmber);// 已付款，待维修
                workflowPriceMapper.updateLogcostStatusByNmber(repairnNmber, "0"); // 修改物流费支付状态
                WorkflowTotalpay totalpay = new WorkflowTotalpay();
                totalpay.setIsPay(0);
                totalpay.setPayTime(new Timestamp(System.currentTimeMillis()));
                totalpay.setRepairNumber(repairnNmber);
                totalpay.setRemAccount(payType);
                updatePricePj(repairnNmber, totalpay);
                workflowTotalpayMapper.update(totalpay);// 支付成功，记录改为付款
                return true;
            }
        }
        return false;
    }

    /**
     * 批次配件料减库存,插入销售配件记录
     * 
     * @param repairnNmber
     * @param totalpay
     */
    public void updatePricePj(String repairnNmber, WorkflowTotalpay totalpay) {
        try {
            List<PricePj> pjList = pricePjService.queryListByrepairNumber(repairnNmber);
            if (null != pjList && pjList.size() > 0) {
                for (PricePj p : pjList) {
                    materialLogService.outBoundPj(p);

                    XspjcostsReport xs = new XspjcostsReport();
                    xs.setCusName(p.getCusName());
                    xs.setSaleDate(new Timestamp(System.currentTimeMillis()));
                    xs.setMarketModel(p.getMarketModel());
                    xs.setProNO(p.getProNO());
                    xs.setProName(p.getProName());
                    xs.setNumber(p.getPjNumber());
                    xs.setUnitPrice(p.getRetailPrice());
                    xs.setPayReason("销售配件仓");
                    xs.setPayMethod(totalpay.getRemAccount());
                    xs.setPayDate(new Timestamp(System.currentTimeMillis()));
                    xs.setPayState("1");
                    if (!StringUtil.isRealEmpty(p.getRetailPrice().toString())) {
                        xs.setPayPrice(p.getRetailPrice().multiply(new BigDecimal(p.getPjNumber())));
                        xs.setSumPrice(xs.getPayPrice());
                    }
                    xs.setLogCost(BigDecimal.ZERO);
                    xs.setReceipt(1);
                    xs.setRate(BigDecimal.ZERO);
                    xs.setRatePrice(BigDecimal.ZERO);
                    xs.setRemark(totalpay.getPriceRemark());
                    xs.setRepairNumber(totalpay.getRepairNumber());
                    xspjcostsReportService.insertXspjcosts(xs);
                }
            }
        } catch (Exception e) {
            logger.error("支付后修改配件料相关信息错误：", e);
        }
    }

    /**
     * 判断该批次是否全部报价
     * 
     * @param repairnNmber
     * @return
     */
    public boolean isAllPay(String repairnNmber) {
        if (null != repairnNmber && !repairnNmber.equals("")) {
            return workflowPriceMapper.selectPriceCountByRepairnNmber(repairnNmber) == 0;
        }
        return false;
    }

    /**
     * @Title: isPayByRepairNumber
     * @Description:该批次是否已付款，防止测试的数据可以报价
     * @param repairnNmber
     * @return
     * @author 20160308
     * @date 2017年11月13日 上午11:01:24
     */
    public boolean isPayByRepairNumber(String repairnNmber) {
        if (null != repairnNmber && !repairnNmber.equals("")) {
            return workflowTotalpayMapper.isPayByRepairNumber(repairnNmber);
        }
        return false;
    }

    /**
     * 保存报价更新报价人
     * 
     * @author TangYuping
     * @version 2016年11月28日 下午4:10:08
     * @param wp
     */
    public void updateOffer(WorkflowPrice wp) {
        workflowPriceMapper.updateOffer(wp);
    }

    /**
     * 根据 wfid 批量删除报价记录表
     * 
     * @author TangYuping
     * @version 2017年1月12日 下午4:17:14
     * @param wfids
     */
    public void delPriceByWfid(String... wfids) {
        workflowPriceMapper.delPriceByWfid(wfids);
        // 删除成品料和配件料
        workflowMapper.updateWlByWfids(wfids);
    }

    /**
     * 把报价数据放入公共数据里
     * 
     * @param wfpr
     * @param wf
     */
    public void setProperties(WorkflowPrice wfpr, Workflow wf) {
        if (null != wfpr) {
            WorkflowRepair wfr = workflowRepairService.getRepairByWfId(wf.getId());
            wf.setW_priceId(wfpr.getId());
            if (null != wfpr.getIsPay()) {
                wf.setW_isPay(wfpr.getIsPay());
            } else {
                wf.setW_isPay(1);// 默认不付款
            }
            wf.setW_priceState(wfpr.getPriceState());
            wf.setW_remAccount(wfpr.getRemAccount());
            wf.setW_payTime(wfpr.getPayTime());
            wf.setW_offer(wfpr.getOffer());
            wf.setW_priceState(wfpr.getPriceState());
            wf.setW_priceStateFalg(wfpr.getPriceState());
            wf.setW_sumPrice(wfr.getSumPrice());
            wf.setW_price_Remark(wfpr.getPriceRemark());
            wf.setW_customerReceipt(wfpr.getCustomerReceipt());
            wf.setW_engineer(wfpr.getPrice_engineer());
            wf.setAcceptRemark(wfpr.getPrice_acceptRemark());
            wf.setRemark(wfpr.getPrice_sxRemark());
            wf.setW_expressNO(wfpr.getPrice_expressNO());
            wf.setW_isPrice(wfpr.getPrice_isPrice());
            wf.setW_receipt(wfpr.getReceipt());
            wf.setW_rate(wfpr.getRate());
            wf.setW_onePriceRemark(wfpr.getOnePriceRemark());
            wf.setPrice_createTime(wfpr.getCreateTime());
            wf.setW_t_remAccount(wfpr.getT_remAccount());
            wf.setW_repairRemark(wfpr.getPrice_repairRemark());
            wf.setW_priceRemark(wfpr.getPrice_priceRemark());
            wf.setRepairNumberRemark(wfpr.getPrice_repairNumberRemark());

            if (null != wfpr.getTestResult() && !"".equals(wfpr.getTestResult())) {
                StringBuffer result = new StringBuffer();
                if (wfpr.getTestPerson() != null && wfpr.getTestTime() != null) {
                    result.append("———" + wfpr.getTestPerson() + "    " + wfpr.getTestTime());
                    wf.setTestResult(wfpr.getTestResult() + result.substring(0, result.lastIndexOf(".0")));
                }

            }

            if (!StringUtil.isEmpty(wfpr.getPrice_zzgzDesc())) {
                // TODO 最终故障
                String zzgz_Name = zgzmanageMapper.getGRoupConcat(StringUtil.split(wfpr.getPrice_zzgzDesc()));
                String zzgzIds = workflowService.sortIds(wfpr.getPrice_zzgzDesc());
                wf.setW_zzgzId(zzgzIds);
                wf.setW_zzgzDesc(zzgz_Name);
            }
            if (null != wfr) {
                if (null == wfr.getIsRW()) {
                    wfr.setIsRW(0);// 是人为
                }
                wf.setW_isRW(wfr.getIsRW());
                if (null == wfr.getSumPrice()) {
                    wfr.setSumPrice(BigDecimal.ZERO);
                }
                wfpr.setRepairMoney(wfr.getSumPrice());
            }
            if (null != wfpr.getRepairnNmber_totalMoney()) {
                wf.setRepairnNmber_totalMoney(wfpr.getRepairnNmber_totalMoney());
            } else {
                wf.setRepairnNmber_totalMoney(BigDecimal.ZERO);
            }
            workflowService.setBackDate(wf);
        }
    }

    /**
     * 导出报价信息
     * 
     * @author TangYuping
     * @version 2016年12月15日 下午1:55:29
     * @param wp
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    public void ExportDatas(WorkflowPrice wp, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String cusName = wp.getPrice_cusName();
        if (null != cusName && !"".equals(cusName)) {
            wp.setPrice_cusName(new String(cusName.getBytes("iso8859-1"), "utf-8"));
        }
        List<Workflow> wfList = new ArrayList<Workflow>();
        List<WorkflowPrice> wfprList = workflowPriceMapper.getPricePageList(wp);
        if (null != wfprList && wfprList.size() > 0) {
            for (WorkflowPrice wfpr : wfprList) {
                Workflow wf = workflowService.getInfo(new Long(wfpr.getWfId()).intValue(), true);
                if (null != wf) {
                    this.setProperties(wfpr, wf);
                    wfList.add(wf);
                }
            }
        }
        String[] fieldNames = new String[] { "IMEI", "智能锁ID", "蓝牙ID", "进度", "维修员", "维修报价描述", "维修报价（￥）", "送修批号", "客户名称",
                "客户手机号", "保内|保外", "主板型号", "市场型号", "是否人为", "是否付款", "报价人", "客户寄货方式" };
        Map map = new HashMap();
        map.put("size", wfList.size() + 2);
        map.put("peList", wfList);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("报价管理列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.WORKFLOW).append("workPrice.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

    /**
     * 查看需要填写物流费的数据列表
     * 
     * @author TangYuping
     * @version 2016年12月19日 上午11:53:44
     * @param wf
     * @return
     */
    public Page<Workflow> queryListLogCost(Workflow wf, int currentPage, int pageSize) {
        Page<Workflow> listPageLogCost = new Page<Workflow>();
        listPageLogCost.setCurrentPage(currentPage);
        listPageLogCost.setSize(pageSize);
        listPageLogCost = workflowPriceMapper.queryListLogCost(listPageLogCost, wf);
        return listPageLogCost;
    }

    /**
     * 根据wfid 查询报价数据
     * 
     * @author TangYuping
     * @version 2017年2月9日 下午3:00:20
     * @param wfid
     * @return
     */
    public WorkflowPrice getPriceByWfid(Integer wfid) {
        return workflowPriceMapper.getByWfid(wfid);
    };

    /**
     * @Title: sendNoPriceToRepair
     * @Description:发送待付款数据到维修工站
     * @param id
     * @param repairNumber
     * @author 20160308
     * @date 2017年11月3日 上午10:28:00
     */
    @Transactional
    public void sendNoPriceToRepair(String id, String repairNumber) {
        // 批次修改维修表状态，已发送，改成已维修， 待报价，维修报价，成品料先不做清空
        workflowRepairMapper.updateState(1L, id);
        // 主表状态改成已维修，待报价
        workflowMapper.updateState(12L, id);
        // 删除报价表记录
        this.delPriceByWfid(id);
        // 查询该批次其他所有待付款数据的wfid
        String wfids = workflowPriceMapper.getWfIdsByRepairnNmber(repairNumber);// 不包含选中的id
        // 该批次所有已报价，待付款数据改成待报价
        if (!StringUtil.isRealEmpty(wfids)) {
            String ids[] = wfids.split(",");
            // 报价表改成待报价
            this.updateState(0L, ids);
            // 主表状态改成待报价
            workflowMapper.updateState(3L, ids);
        }
        // 删除total表数据
        workflowTotalpayMapper.deleteByRepairNumber(repairNumber);
    }

    /**
     * @Title: getCountHasNotPriceByWfids
     * @Description:查询选中的数据是否有不是待报价或已报价，待付款的？有：不允许放弃报价
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月3日 下午3:01:25
     */
    public int getCountHasNotPriceByWfids(String... ids) {
        return workflowPriceMapper.getCountHasNotPriceByWfids(ids);
    }

    /**
     * @Title: giveupPriceSendRepair
     * @Description:放弃报价发维修
     * @param isPrice
     * @param onepriceMark
     * @param currentUserName
     * @param ids
     * @author 20160308
     * @date 2017年11月3日 下午3:02:25
     */
    @Transactional
    public void giveupPriceSendRepair(String isPrice, String onepriceMark, String currentUserName, String... ids) {
        WorkflowRepair wfr = new WorkflowRepair();
        wfr.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        wfr.setIsPay(1);
        wfr.setRepairState(6L);// 放弃报价，待维修
        if (isPrice != null && StringUtils.isNotBlank(isPrice)) {
            wfr.setIsPrice(Integer.valueOf(isPrice));
            wfr.setGiveupPricePerson(currentUserName);
            wfr.setGiveupPriceTime(new Timestamp(System.currentTimeMillis()));
        }
        // 放弃报价发维修，减去放弃报价的设备产生的费用
        workflowService.updateSumPrice(onepriceMark, ids);
        workflowRepairService.beathUpdate(wfr, ids);
        this.updateState(-1L, ids);// 已发送
        workflowService.updateState(11L, ids); // 放弃报价，待维修
        tipService.addTip(TipState.TIP_WX, ids.length);
    }

    /**
     * @Title: getCountHasNotPayByWfids
     * @Description:报价工站查询选中的数据是否有不是已付款？有：不允许点击已付款，发送维修
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月3日 下午3:46:34
     */
    public int getCountHasNotPayByWfids(String... ids) {
        return workflowPriceMapper.getCountHasNotPayByWfids(ids);
    }

    /**
     * @Title: sendRepair
     * @Description:已付款，发送维修
     * @param ids
     * @author 20160308
     * @date 2017年11月3日 下午3:41:20
     */
    @Transactional
    public void sendRepair(String... ids) {
        WorkflowRepair wfr = new WorkflowRepair();
        wfr.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        wfr.setIsPay(0);
        wfr.setRepairState(4L);// 已付款，待维修
        workflowRepairService.beathUpdate(wfr, ids);

        workflowPriceMapper.updateState(-1L, ids);// 已发送
        workflowService.updateState(4L, ids);// 从 已报价，待维修 到已付款，待维修
        tipService.addTip(TipState.TIP_WX, ids.length);
    }

    /**
     * @Title: getCountHasPriceByWfids
     * @Description:报价工站查询选中的数据是否有不是待报价？有：不允许点击待报价，发送维修
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月3日 下午3:46:34
     */
    public int getCountHasPriceByWfids(String... ids) {
        return workflowPriceMapper.getCountHasPriceByWfids(ids);
    }

    /**
     * @Title: sendDataToRepair
     * @Description:待报价数据返回到维修工站
     * @param ids
     * @author 20160308
     * @date 2017年11月3日 下午4:29:43
     */
    @Transactional
    public void sendDataToRepair(String... ids) {
        // 批次修改维修表状态，已发送，改成已维修， 待报价，维修报价，成品料先不做清空
        workflowRepairService.updateStateByWfids(1L, ids);
        // 主表状态改成已维修，待报价
        workflowService.updateState(12L, ids);;
        // 删除报价表记录
        this.delPriceByWfid(ids);
    }

    /**
     * @Title: getCountPayByRepairnNmber
     * @Description:报价工站查询选中的数据是否有已付款的数据？有：不允许点击报价
     * @param repairnNmber
     * @return
     * @author 20160308
     * @date 2017年11月6日 下午3:15:10
     */
    public int getCountPayByRepairnNmber(String repairnNmber) {
        return workflowPriceMapper.getCountPayByRepairnNmber(repairnNmber);
    }

    /**
     * @Title: queryProlongCost
     * @Description:查询延保价格
     * @return
     * @author Wang Xirui
     * @date 2019年6月24日 下午4:06:52
     */
    public ProlongCost queryProlongCost() {
        return workflowPriceMapper.queryProlongCost();
    }

    /**
     * @Title: updateProlongCost
     * @Description:更改延保价格
     * @param prolongCost
     * @author Wang Xirui
     * @date 2019年6月24日 下午4:06:55
     */
    public void updateProlongCost(ProlongCost prolongCost) {
        workflowPriceMapper.updateProlongCost(prolongCost);
    }

    @Value("${ams_sales_url}")
    private String AMS_SALES_URL;

    @Resource(name = "zbxhmanageMapper")
    private ZbxhmanageMapper<Zbxhmanage> zbxhmanageMapper;

    /**
     * @Title: saveProlongInfo
     * @Description:保存延保记录
     * @param prolongInfo
     * @author Wang Xirui
     * @date 2019年6月26日 下午3:26:12
     */
    @Transactional
    public void saveProlongInfo(ProlongInfo prolongInfo) {
        workflowPriceMapper.insertProlongInfo(prolongInfo);
    }

    /**
     * @Title: saveProlongInfo
     * @Description:保存延保期限
     * @param prolongInfo
     * @author Wang Xirui
     * @date 2019年6月26日 下午3:26:12
     */
    @Transactional
    public void updateProlongInfo(ProlongInfo prolongInfo) {
        String imei = prolongInfo.getImei();
        int year = prolongInfo.getProlongYear();
        Date warrantyDate = null;
        Workflow workflow = workflowService.getInfoByImei(imei);

        int isWarranty = workflow.getIsWarranty();
        // 保修期以内
        if (isWarranty == 0) {
            ProlongInfo query = new ProlongInfo();
            query.setImei(workflow.getImei());
            ProlongInfo oldProlong = queryProlongInfo(query);

            if (null == oldProlong) {
                Saledata data = null;
                String json = JavaNetURLRESTFulClient.restSale(imei.trim(), "", AMS_SALES_URL);
                if (null != json) {
                    JSONObject jsonObject = JSONObject.fromObject(json);
                    String msg = jsonObject.getString("msg");
                    if ("0".equals(msg)) {
                        data = (Saledata) JSONObject.toBean(jsonObject, Saledata.class);
                    }
                }
                Zbxhmanage zbxhmanage = zbxhmanageMapper.getT(Integer.valueOf(workflow.getXhId().toString()));
                warrantyDate = getWarrantyDate(zbxhmanage, data.getOutDate().toString());
            }
            if (null != oldProlong) {
                warrantyDate = oldProlong.getWarrantyDate();
            }

            // 在现有保修期上加上延保期限
            warrantyDate = DateUtils.addMonths(warrantyDate, year * 12);

            prolongInfo.setWarrantyDate(warrantyDate);
        }

        // 保修期以外
        if (isWarranty == 1) {
            warrantyDate = DateUtils.addMonths(new Date(), year * 12);

            prolongInfo.setWarrantyDate(warrantyDate);
        }
        // 保存延保期限
        workflowPriceMapper.updateProlongInfo(prolongInfo);
        // 设置保内
        workflowPriceMapper.updateIsWarranty(0, workflow.getId());

        WorkflowRepair workflowRepair = workflowRepairService.getRepairByWfId(workflow.getRfild());
        // 人为,维修费用减半
        if (workflowRepair.getIsRW() == 0) {
            
        }

        // 非人为,不报价，返回维修工站
        if (workflowRepair.getIsRW() == 1) {
            String wfId = String.valueOf(workflow.getRfild());
            sendDataToRepair(wfId);
            workflowRepair.setRfild(workflow.getRfild());
            workflowRepairService.update(workflowRepair);
        }
    }
    
    /**
     * @Title: getWarrantyDate
     * @Description:在于2016-01-01之前保13个月 之后车载：保2年，非车载：保13个月
     * @param zbxh
     * @param time
     * @return
     * @throws ParseException
     * @author Wang Xirui
     * @date 2019年6月26日 下午4:40:49
     */
    private Date getWarrantyDate(Zbxhmanage zbxh, String time) {
        Date startDate = DateTimeUtil.getDateTime(time);
        Date point = DateTimeUtil.getDate("2016-01-01");
        int month = 0;
        if (startDate.getTime() > point.getTime()) {
            if ("车载".equals(zbxh.getModelType().trim())) {
                month = 24;
            } else {
                month = 13;
            }
        } else {
            month = 13;
        }
        Date deadlineWarranty = DateUtils.addMonths(startDate, month);
        return deadlineWarranty;
    }

    /**
     * @Title: queryProlongInfo
     * @Description:查询延保记录
     * @param prolongInfo
     * @author Wang Xirui
     * @date 2019年6月26日 下午3:28:25
     */
    public ProlongInfo queryProlongInfo(ProlongInfo prolongInfo) {
        return workflowPriceMapper.getProlongInfo(prolongInfo);
    }
}
