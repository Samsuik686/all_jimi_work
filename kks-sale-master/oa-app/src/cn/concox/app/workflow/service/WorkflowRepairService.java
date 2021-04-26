package cn.concox.app.workflow.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.basicdata.mapper.RepairPriceMapper;
import cn.concox.app.basicdata.mapper.ZgzmanageMapper;
import cn.concox.app.basicdata.service.TipService;
import cn.concox.app.basicdata.service.TipService.TipState;
import cn.concox.app.common.page.Page;
import cn.concox.app.rolePrivilege.mapper.UserRoleMgtMapper;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.app.workflow.mapper.WorkflowPriceMapper;
import cn.concox.app.workflow.mapper.WorkflowRelatedMapper;
import cn.concox.app.workflow.mapper.WorkflowRepairMapper;
import cn.concox.app.workflow.mapper.WorkflowTotalpayMapper;
import cn.concox.comm.util.Convert;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.aipay.CustomerClient;
import cn.concox.vo.basicdata.RepairPriceManage;
import cn.concox.vo.basicdata.Zgzmanage;
import cn.concox.vo.rolePrivilege.UserRoleMgt;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowFicheck;
import cn.concox.vo.workflow.WorkflowPack;
import cn.concox.vo.workflow.WorkflowPrice;
import cn.concox.vo.workflow.WorkflowRelated;
import cn.concox.vo.workflow.WorkflowRepair;
import cn.concox.vo.workflow.WorkflowTotalpay;

/**
 * 维修
 * 
 * @author Li.Shangzhi
 * @date 2016-08-15 17:42:11
 */
@Service("workflowRepairService")
@Scope("prototype")
public class WorkflowRepairService {

    Logger logger = Logger.getLogger("privilege");

    @Resource(name = "workflowRepairMapper")
    private WorkflowRepairMapper<WorkflowRepair> workflowRepairMapper;

    @Resource(name = "workflowMapper")
    private WorkflowMapper<Workflow> workflowMapper;

    @Resource(name = "zgzmanageMapper")
    private ZgzmanageMapper<Zgzmanage> zgzmanageMapper;

    @Resource(name = "workflowRelatedMapper")
    private WorkflowRelatedMapper<WorkflowRelated> workflowRelatedMapper;

    @Resource(name = "workflowPriceMapper")
    private WorkflowPriceMapper<WorkflowPrice> workflowPriceMapper;

    @Resource(name = "repairPriceMapper")
    private RepairPriceMapper<RepairPriceManage> repairPriceMapper;

    @Resource(name = "userRoleMgtMapper")
    private UserRoleMgtMapper<UserRoleMgt> userRoleMgtMapper;

    @Resource(name = "workflowTotalpayMapper")
    private WorkflowTotalpayMapper<WorkflowTotalpay> workflowTotalpayMapper;

    @Resource(name = "workflowService")
    private WorkflowService workflowService;

    // @Resource(name="workflowPriceService")
    // private WorkflowPriceService workflowPriceService;

    @Resource(name = "ficheckService")
    private WorkflowFicheckService ficheckService;

    @Resource(name = "workflowPackService")
    private WorkflowPackService workflowPackService;

    @Resource(name = "tipService")
    private TipService tipService;

    /**
     * 获取维修分页数据
     * <p>
     * </p>
     * 
     * @param WorkflowRepair
     * @return
     */
    public Page<WorkflowRepair> getRepairPage(WorkflowRepair w, Integer currentpage, Integer pageSize) {
        Page<WorkflowRepair> wfrList = new Page<WorkflowRepair>();
        wfrList.setCurrentPage(currentpage);
        wfrList.setSize(pageSize);
        wfrList = workflowRepairMapper.getRepairPage(wfrList, w);
        return wfrList;
    }

    /**
     * 获取维修数据
     * <p>
     * </p>
     * 
     * @param WorkflowRepair
     * @return
     */
    public List<WorkflowRepair> getRepairList(WorkflowRepair w, String userId) {
        List<String> roleIds = userRoleMgtMapper.getUserRoleIdListByUId(userId);
        if (roleIds != null && roleIds.size() > 0) {
            if (roleIds.indexOf("25") > -1 || roleIds.indexOf("96") > -1) {
                if (w.getSearchEngineer() == null || StringUtils.isBlank(w.getSearchEngineer())) {
                    w.setEngineer("");
                } else {
                    w.setEngineer(w.getSearchEngineer());
                }
            }
        }
        List<WorkflowRepair> workflows = workflowRepairMapper.getRepairList(w);
        return workflows;
    }

    /**
     * API 新增维修信息单
     * 
     * @param WorkflowRepair
     *            .wfId 联表ID
     * @param WorkflowRepair
     *            .engineer 维修工程师
     * @param WorkflowRepair
     *            .repairState 维修状态 [0：未处理 1:已处理]F
     * @param WorkflowRepair
     *            .createTime 创建时间 F ....
     * @return Code -1 插入失败 Code 0 插入成功 Code 1 参数错误
     * @author Li.Shangzhi
     * @date 2016-08-15 17:42:18
     */
    public Integer doInsert(WorkflowRepair wr) {
        try {
            if (null != wr && null != wr.getWfId() && !StringUtil.isEmpty(wr.getEngineer())) {
                wr.setIsRW(1);// 默认非人为
                wr.setIsPay(1);// 默认未付款
                workflowRepairMapper.insert(wr);
                return 0; // TODO 插入成功
            } else {
                return 1; // TODO 参数错误
            }
        } catch (Exception e) {
            logger.info("API -- 新增维修信息单  " + e.toString());
            return -1; // TODO 插入失败
        }
    }

    /**
     * API 修改维修状态
     * 
     * @param repairState
     *            维修状态 [-1：已发送 0：待维修 1:待报价 2：待终检]
     * @param wfids
     *            [] WorkflowRepair.id 主键ID
     * 
     * @return Code -1 插入失败 Code 0 插入成功 Code 1 参数错误
     * @author Li.Shangzhi
     * @date 2016-08-15 17:42:23
     */
    public Integer updateStateByWfids(long repairState, String... wfids) {
        try {
            if (wfids != null && !StringUtil.isEmpty(wfids.toString())) {
                workflowRepairMapper.updateState(repairState, wfids);
                return 0; // TODO 插入成功
            } else {
                return 1; // TODO 参数错误
            }
        } catch (Exception e) {
            logger.info("API -- 修改维修状态  " + e.toString());
            return -1; // TODO 插入失败
        }
    }

    public Integer updateStateByRepairNumber(long repairState, String[] repairNumbers, String... wfids) {
        try {
            if (wfids != null && !StringUtil.isEmpty(wfids.toString()) && repairNumbers != null
                    && !StringUtil.isEmpty(repairNumbers.toString())) {
                workflowRepairMapper.updateStateByRepairNumber(repairState, repairNumbers, wfids);
                return 0; // TODO 插入成功
            } else {
                return 1; // TODO 参数错误
            }
        } catch (Exception e) {
            logger.info("API -- 修改维修状态  " + e.toString());
            return -1; // TODO 插入失败
        }
    }

    /**
     * API 已付款，待维修，维修更新接口
     * 
     * @param wr
     * @return
     */
    public boolean doUpdate(WorkflowRepair wr) {
        if (null != wr.getWfId() && wr.getWfId() > 0) {
            workflowRepairMapper.update(wr);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据主键ID获取数据
     * 
     * @param Id
     * @return
     */
    public WorkflowRepair getT(Integer Id) {
        if (Id > 0) {
            return workflowRepairMapper.getT(Id);
        } else {
            return null;
        }
    }

    /**
     * 更新维修数据,同步更新工作流附联表 Update WorkflowRepair Update WorkflowRelated
     * 
     * @param WorkflowRepair
     * @return true | false
     */
    @Transactional
    public boolean update(WorkflowRepair wr) {
        try {
            if (null != wr && null != wr.getRfild() && null != wr.getWfId() && wr.getRfild() > 0 && wr.getWfId() > 0) {
                WorkflowRepair w = workflowRepairMapper.getRepairByWfId(wr.getWfId());// 获取维修数据
                if (null != w && null != w.getIsPrice()) {// 是否放弃维修 0：是 1：否
                    wr.setIsPrice(w.getIsPrice());
                }
                if (null != w && null != w.getIsPay()) {// 是否付款
                    wr.setIsPay(w.getIsPay());
                }
                WorkflowRelated related = new WorkflowRelated();
                related.setId(wr.getRfild());
                related.setZzgzDesc(wr.getZzgzDesc());
                related.setWxbjDesc(wr.getWxbjDesc());
                related.setMatDesc(wr.getMatDesc());
                related.setDzlDesc(wr.getDzlDesc());
                related.setSllDesc(wr.getSllDesc());
                related.setUpdateTime(new Timestamp(System.currentTimeMillis()));

                // TODO Update WorkflowRelated
                workflowRelatedMapper.update(related);
                {
                    // t_related_zzgz 同步关联表处理
                    // 1.清除原有记录
                    workflowRepairMapper.doRZClear(related.getId());
                    // 2.新增故障记录
                    String[] Ids = StringUtil.split(wr.getZzgzDesc());
                    if (Ids != null && Ids.length > 0) {
                        for (String zzgzId : Ids) {
                            Zgzmanage zgzmanage = zgzmanageMapper.getT(Convert.getInteger(zzgzId));
                            if (null != zgzmanage && zgzmanage.getGId() > 0 && zgzmanage.getId() > 0) {
                                workflowRepairMapper.doREInsert(related.getId(), zgzmanage.getId().toString(),
                                        zgzmanage.getGId());
                            }
                        }
                    }
                }
                boolean IsWarranty = true; // TODO 是否保内
                Workflow workflow = workflowMapper.getT(wr.getWfId());
                if (null != workflow && workflow.getIsWarranty() == 1) {
                    IsWarranty = false;
                }
                /**
                 * @author Li.Shangzhi
                 * @date 2016-08-18 10:31:07 注： 是否认为 ： 是 > 保内保外都收费 否 > 保内 ：不收费 保外：收费
                 */
                if (!IsWarranty || wr.getIsRW() == 0) { // TODO 保外,人为破坏
                    if (wr.getWxbjDesc() != null && !"".equals(wr.getWxbjDesc())) {// 有维修报价信息
                        if (null != wr.getIsPrice() && 1 == wr.getIsPrice()) {// 放弃报价，总价格为0
                            if (wr.getRepairState() != null && (wr.getRepairState() == 6L || wr.getRepairState() == 7L)) {
                                // 放弃报价，待装箱
                                wr.setSumPrice(BigDecimal.ZERO);
                                wr.setRepairState(7L);
                                workflowMapper.updateState(8L, new String[] { wr.getWfId().toString() });
                            }
                        } else {
                            if (null != wr.getIsPay() && 0 == wr.getIsPay()) {// 已付款
                                wr.setRepairState(2L);
                                workflowMapper.updateState(5L, new String[] { wr.getWfId().toString() });
                            } else {
                                String[] wxbjIds = wr.getWxbjDesc().split(",");// 维修报价

                                // 工时费
                                BigDecimal hourFee = BigDecimal.ZERO;
                                RepairPriceManage rp = repairPriceMapper.getHourFee(wxbjIds);
                                if (null != rp && null != rp.getHourFee()) {
                                    hourFee = rp.getHourFee();
                                }

                                BigDecimal wxbjPrices = zgzmanageMapper.getSumPrices(wxbjIds);// 维修报价表里
                                // 是否选择了维修报价
                                if (null == wxbjPrices || wxbjPrices.compareTo(BigDecimal.ZERO) == 0) {
                                    wxbjPrices = BigDecimal.ZERO;
                                }
                                wr.setHourFee(hourFee);
                                wr.setRepairPrice(wxbjPrices);
                                wr.setSumPrice(wxbjPrices.add(hourFee));
                                if (null != wr.getSumPrice() && wr.getSumPrice().compareTo(BigDecimal.ZERO) == 0) {
                                    wr.setRepairState(2L);
                                    workflowMapper.updateState(5L, new String[] { wr.getWfId().toString() });
                                } else {
                                    wr.setRepairState(1L);
                                    workflowMapper.updateState(12L, new String[] { wr.getWfId().toString() }); // 已分拣，待维修
                                                                                                               // 到
                                                                                                               // 已维修，待报价
                                }
                            }
                        }
                    } else {
                        wr.setRepairPrice(BigDecimal.ZERO);
                        wr.setHourFee(BigDecimal.ZERO);
                        wr.setSumPrice(BigDecimal.ZERO);
                        if (wr.getRepairState() != null && (wr.getRepairState() == 6L || wr.getRepairState() == 7L)) {
                            // 放弃报价，待装箱
                            wr.setRepairState(7L);
                            workflowMapper.updateState(8L, new String[] { wr.getWfId().toString() });
                        } else {
                            // 已维修，待终检
                            wr.setRepairState(2L);
                            workflowMapper.updateState(5L, new String[] { wr.getWfId().toString() });
                        }
                    }
                } else {
                    // 保内非人为免费维修
                    wr.setRepairPrice(BigDecimal.ZERO);
                    wr.setHourFee(BigDecimal.ZERO);
                    wr.setSumPrice(BigDecimal.ZERO);
                    if (wr.getRepairState() != null && (wr.getRepairState() == 6L || wr.getRepairState() == 7L)) {
                        // 放弃报价，待装箱
                        wr.setRepairState(7L);
                        workflowMapper.updateState(8L, new String[] { wr.getWfId().toString() });
                    } else {
                        wr.setRepairState(2L);
                        // 已维修，待终检
                        workflowMapper.updateState(5L, new String[] { wr.getWfId().toString() });
                    }
                }
                workflowRepairMapper.update(wr);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 新增维修数据
     * 
     * @param repair
     */
    public void doInset(WorkflowRepair repair) {
        repair.setIsRW(1);// 默认非人为
        repair.setIsPay(1);// 默认未付款
        if (repair.getRepairState() == null) {
            repair.setRepairState(0L);
        }
        repair.setCreateTime(new Timestamp(System.currentTimeMillis()));
        workflowRepairMapper.insert(repair);
    }

    /**
     * 报价工站添加物料信息
     * 
     * @author TangYuping
     * @version 2016年11月28日 上午9:26:10
     * @param wr
     * @return
     */
    @Transactional
    public boolean updateWl(WorkflowRepair wr) {
        try {
            if (wr.getPriceId() != null && StringUtils.isNotBlank(wr.getPriceId().toString())) {
                WorkflowPrice price = workflowPriceMapper.getT(wr.getPriceId());
                if (price != null) {
                    if (wr.getRepair_onePriceRemark() != null && StringUtils.isNotBlank(wr.getRepair_onePriceRemark())) {
                        price.setOnePriceRemark(wr.getRepair_onePriceRemark());
                        workflowPriceMapper.update(price);
                    }
                }
            }
            if (wr.getIsPrice().equals("1")) {
                // 放弃报价，直接发送到装箱
                return true;
            } else if (wr.getRepair_Imei() != null && StringUtils.isNotBlank(wr.getRepair_Imei())) {
                WorkflowRepair repair = workflowRepairMapper.getByImei(wr.getRepair_Imei(), "");
                wr.setWfId(repair.getWfId());
                WorkflowRelated related = new WorkflowRelated();
                related.setId(wr.getRfild());
                related.setMatDesc(wr.getMatDesc());
                related.setDzlDesc(wr.getDzlDesc());
                related.setWxbjDesc(wr.getWxbjDesc());
                related.setSllDesc(wr.getSllDesc());
                related.setUpdateTime(new Timestamp(System.currentTimeMillis()));

                // TODO Update WorkflowRelated
                workflowRelatedMapper.update(related);

                if (repair != null) {
                    // 获取维修那边产生的维修报价费用(从前台获取)
                    String[] rids = wr.getWxbjDesc().split(",");

                    // 工时费
                    BigDecimal hourFee = BigDecimal.ZERO;
                    RepairPriceManage r = repairPriceMapper.getHourFee(rids);
                    if (null != r && null != r.getHourFee()) {
                        hourFee = r.getHourFee();
                    }
                    // 维修报价费
                    RepairPriceManage rp = repairPriceMapper.getSumPrice(rids);
                    BigDecimal repairPrice = new BigDecimal(0);
                    if (rp != null) {
                        repairPrice = rp.getSumPrice();
                    }

                    if (repairPrice != null) {
                        wr.setRepairPrice(repairPrice);
                        wr.setHourFee(hourFee);
                        wr.setSumPrice(repairPrice.add(hourFee));
                    } else {
                        wr.setRepairPrice(BigDecimal.ZERO);
                        wr.setHourFee(BigDecimal.ZERO);
                        wr.setSumPrice(BigDecimal.ZERO);
                    }

                    WorkflowPrice price = new WorkflowPrice();
                    price.setWfId(repair.getWfId());
                    price.setRepairMoney(wr.getSumPrice());
                    workflowPriceMapper.updateRepairMoneyByWfid(price);
                    workflowRepairMapper.update(wr);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据 WFID 获取维修数据
     * 
     * @param wfId
     * @return
     */
    public WorkflowRepair getRepairByWfId(Integer wfId) {
        return workflowRepairMapper.getRepairByWfId(wfId);
    }

    /**
     * 批量修改维修信息，供报价
     * 
     * @param workflowPrice
     *            workflowPrice.wfId workflowPrice.updateTime workflowPrice.isPay
     * @param ids
     */
    public void beathUpdate(WorkflowRepair w, String... ids) {
        workflowRepairMapper.beathUpdate(w, ids);
    }

    public void updateRepairAgainCount(String... ids) {
        workflowRepairMapper.updateRepairAgainCount(ids);
    }

    /**
     * 维修不报价
     * 
     * @param workflowRepair
     * @param ids
     */
    public Integer notPrice(WorkflowRepair wr, String... ids) {
        try {
            if (ids != null && !StringUtil.isEmpty(ids.toString())) {
                wr.setIsRW(1);// 不是人为

                wr.setRepairPrice(BigDecimal.ZERO);
                wr.setSumPrice(BigDecimal.ZERO);
                wr.setRepairState(5L); // 不报价，待维修

                workflowRepairMapper.notPrice(wr, ids);
                workflowMapper.updateState(10L, new String[] { wr.getWfId().toString() });
                return 0; // 不报价成功
            } else {
                return 1; // 参数错误
            }
        } catch (Exception e) {
            logger.info("API -- 不报价  " + e.toString());
            return -1; // 插入失败
        }
    }

    /**
     * 维修发送数据到分拣，删除维修表记录,情空维修报价
     * 
     * @author TangYuping
     * @version 2017年1月12日 下午2:38:30
     * @param ids
     */
    public void deleteRepairByWfids(String states, String... wfids) {
        // 待报价 到 已受理，待分拣 清空 关联表 relate 数据
        if (states.indexOf("1") > -1 || states.indexOf("6") > -1) {
            workflowMapper.updateWxbjIfNotPrice(wfids);
        }
        workflowRepairMapper.deleteRepairByWfids(wfids);
    }

    /**
     * 现只做修改是否放弃报价用
     * 
     * @author TangYuping
     * @version 2016年12月15日 下午6:21:32
     * @param wr
     */
    public void updateRepairIsprice(WorkflowRepair wr) {
        workflowRepairMapper.update(wr);
    }

    /**
     * 客户端修改设备维修状态，放弃/恢复维修
     * 
     * @author TangYuping
     * @version 2017年2月7日 下午2:18:14
     * @param client
     */
    @Transactional
    public int updateRepairStatus(CustomerClient client) {
        if (client.getImei() != null && StringUtils.isNotBlank(client.getImei())) {
            WorkflowRepair repair = workflowRepairMapper.getByImei(client.getImei(), client.getAcceptTime().toString());
            if (!client.getGiveUpRepairStatus().equals(repair.getGiveUpRepairStatus())) {// 查询的状态和传入的状态不一致才能修改
                repair.setGiveUpRepairStatus(client.getGiveUpRepairStatus());
                Workflow wf = workflowMapper.getInfoByImei(client.getImei());
                WorkflowPrice price = workflowPriceMapper.getByWfid(wf.getId());
                if (price != null) {// 报价表有数据
                    if (client.getGiveUpRepairStatus().equals("0")) {
                        price.setPriceState(2L); // 报价表状态改成 已报价，待付款
                        // price.setRepairMoney(repair.getRepairPrice());// 2019-5-31 17:27:08 放弃报价的设备在报价工站的维修费显示不变
                        // repair.setSumPrice(repair.getRepairPrice());
                        repair.setIsPrice(0);
                        workflowMapper.updateState(9L, wf.getId().toString()); // 主表状态改成 已报价，待付款
                    } else if (client.getGiveUpRepairStatus().equals("1")) {
                        price.setPriceState(4L); // 报价表状态改成 放弃维修
                        // price.setRepairMoney(BigDecimal.ZERO);// 2019-5-31 17:27:08 放弃报价的设备在报价工站的维修费显示不变
                        // repair.setSumPrice(BigDecimal.ZERO);
                        repair.setIsPrice(1);
                        workflowMapper.updateState(14L, wf.getId().toString()); // 主表状态改成 放弃维修
                    }
                    price.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    workflowPriceMapper.update(price);
                    workflowRepairMapper.update(repair);
                    WorkflowTotalpay total = workflowTotalpayMapper.getByRepairNumber(wf.getRepairnNmber());
                    if (total != null) {
                        total.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        if (client.getGiveUpRepairStatus().equals("0")) {// 正常
                            total.setRepairMoney(total.getRepairMoney().add(repair.getRepairPrice()));
                        } else if (client.getGiveUpRepairStatus().equals("1")) {// 放弃维修
                            total.setRepairMoney(total.getRepairMoney().subtract(repair.getRepairPrice()));
                        }
                        // 维修费 + 配件费 + 运费
                        BigDecimal sumMoney = total.getRepairMoney().add(total.getLogCost())
                                .add(total.getBatchPjCosts());
                        if (null != total.getRate() && total.getRate().compareTo(new BigDecimal(0)) > 0) {// 有税费
                            // 减掉税费，重新计算新的税费
                            total.setTotalMoney(total.getTotalMoney().subtract(total.getRatePrice()));

                            // 税费 =（维修费 + 配件费 + 运费）*税率
                            total.setRatePrice(sumMoney.multiply(total.getRate()).divide(new BigDecimal(100)));

                            // 总费用=（维修费 + 配件费 + 运费 + 税费）
                            total.setTotalMoney(sumMoney.add(total.getRatePrice()));
                        } else {
                            total.setTotalMoney(sumMoney);
                        }
                        workflowTotalpayMapper.update(total);
                    }
                } else {
                    return 100;// 报价表无数据，客户端需刷新后操作
                }
                return 0;// 操作成功
            } else {
                return 200;// 数据不正常
            }
        } else {
            return 300;// imei错误
        }
    }

    /**
     * @Title: getCountNoPriceByWfids
     * @Description:维修工站查询选中的数据是否有不是已分拣，待维修？有：不允许点击不报价、发送测试、返回分拣
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月6日 上午10:09:54
     */
    public int getCountNoPriceByWfids(String... ids) {
        return workflowRepairMapper.getCountNoPriceByWfids(ids);
    }

    /**
     * @Title: sendSort
     * @Description:返回分拣
     * @param repairStates
     * @param ids
     * @author 20160308
     * @date 2017年11月6日 上午11:43:06
     */
    @Transactional
    public void sendSort(String repairStates, String... ids) {
        // TODO 修改受理表状态
        workflowService.updateState(1L, ids); // 从已分拣，待维修 改成 已受理，待分拣

        workflowService.updateRepairToSortByWfids(ids);// 维修数据返回分拣工站,修改t_sale_workflow_related表数据为初始状态
        // TODO 删除维修表数据
        this.deleteRepairByWfids(repairStates, ids);

        // 删除报价表数据
        workflowPriceMapper.delPriceByWfid(ids);

        // 删除成品料和配件料
        workflowMapper.updateWlByWfids(ids);

    }

    /**
     * @Title: getCountToSortByWfids
     * @Description:维修工站查询选中的数据是否有不是已分拣，待维修或已维修，待报价或不报价，待维修或放弃报价，待维修？有：不允许点击返回分拣
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月6日 上午10:44:06
     */
    public int getCountToSortByWfids(String... ids) {
        return workflowRepairMapper.getCountToSortByWfids(ids);
    }

    /**
     * @Title: getCountToPriceByWfids
     * @Description:维修工站查询选中的数据是否有不是已维修，待报价？有：不允许点击发送报价
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月6日 上午10:10:01
     */
    public int getCountToPriceByWfids(String... ids) {
        return workflowRepairMapper.getCountToPriceByWfids(ids);
    }

    /**
     * @Title: sendPrice
     * @Description:发送报价
     * @param ids
     * @author 20160308
     * @date 2017年11月6日 上午10:32:19
     */
    @Transactional
    public void sendPrice(String... ids) {
        for (String id : ids) {
            WorkflowRepair wfr = this.getRepairByWfId(Integer.valueOf(id));
            WorkflowPrice wp = workflowPriceMapper.getByWfid(Integer.valueOf(id));
            if (wp != null) {
                // 针对 放弃报价，待维修 数据返回到分拣工站重新走流程
                wp.setWfId(Convert.getInteger(id));
                wp.setIsPay(1);// 未报价
                wp.setCreateTime(new Timestamp(System.currentTimeMillis()));
                wp.setPriceState(0L);
                wp.setRepairMoney(wfr.getSumPrice());
                workflowPriceMapper.update(wp);

            } else {
                WorkflowPrice wfpr = new WorkflowPrice();
                wfpr.setWfId(Integer.valueOf(id));
                wfpr.setIsPay(1);// 未报价
                wfpr.setCreateTime(new Timestamp(System.currentTimeMillis()));
                wfpr.setPriceState(0L);
                wfpr.setRepairMoney(wfr.getSumPrice());
                workflowPriceMapper.insert(wfpr);
            }
        }
        this.updateStateByWfids(-1L, ids); // TODO 维修表 处于已发送状态
        tipService.addTip(TipState.TIP_BJ, ids.length);
        workflowService.updateState(3L, ids);// 待报价
    }

    /**
     * @Title: sendPriceUpdateState
     * @Description:点击发送报价后，改变状态，该维修员已分拣的数据里选中的批次的所有已分拣，待维修的变成不报价，待维修
     * @param repairNumbers
     * @param ids
     * @author 20160308
     * @date 2017年11月6日 上午10:59:35
     */
    @Transactional
    public void sendPriceUpdateState(String[] repairNumbers, String... ids) {
        this.updateStateByRepairNumber(5L, repairNumbers, ids); // TODO 已分拣、待维修状态要变成不报价、待维修
        workflowService.updateStateByRepairNumber(10L, repairNumbers, ids);// 不报价、待维修
    }

    /**
     * @Title: getCountRepairedByWfids
     * @Description:维修工站查询选中的数据是否有不是已维修，待终检？有：不允许点击发送终检
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月6日 上午10:10:06
     */
    public int getCountRepairedByWfids(String... ids) {
        return workflowRepairMapper.getCountRepairedByWfids(ids);
    }

    /**
     * @Title: sendFicheck
     * @Description:发送终检
     * @param ids
     * @author 20160308
     * @date 2017年11月6日 上午11:11:10
     */
    @Transactional
    public void sendFicheck(String... ids) {
        this.updateStateByWfids(-1L, ids); // TODO 处于已发送状态
        for (String id : ids) {
            if (!StringUtil.isEmpty(id)) {
                WorkflowFicheck wf = new WorkflowFicheck();
                wf.setWfId(Integer.valueOf(id).intValue());
                wf.setIspass(1);// 默认合格
                wf.setState(0L);
                WorkflowFicheck fic = ficheckService.getByWfid(Integer.valueOf(id));
                if (fic != null) {
                    fic.setState(0L); // 保内二次返修，从终检返回来的数据再发到终检
                    fic.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    ficheckService.updateRepairAgain(fic);
                } else {
                    wf.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    ficheckService.doInsert(wf);
                }
            }
            // 查询此批设备是否有 保内非人为客户顺付过来的数据，有 则设置物流支付状态为 空，可以直接发货
            Workflow workFlow = workflowService.getT(Integer.valueOf(id));
            workflowService.getDataByRepairNmber(workFlow);

        }
        tipService.addTip(TipState.TIP_ZJ, ids.length);
        workflowService.updateState(13L, ids);// 待终检
    }

    /**
     * @Title: getCountToPackByWfids
     * @Description:维修工站查询选中的数据是否有不是放弃维修，发送装箱？有：不允许点击发送装箱
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月6日 上午10:10:10
     */
    public int getCountToPackByWfids(String... ids) {
        return workflowRepairMapper.getCountToPackByWfids(ids);
    }

    /**
     * @Title: sendPack
     * @Description:发送装箱
     * @param wf
     * @param ids
     * @author 20160308
     * @date 2017年11月6日 上午11:29:29
     */
    @Transactional
    public void sendPack(Workflow wf, String... ids) {
        // 装箱按批次，当批次已存在时，不允许插入装箱
        if (!workflowService.exists(wf.getRepairnNmber())) {// 判断装箱批号是否存在
            WorkflowPack wfp = new WorkflowPack();
            wfp.setCreateTime(new Timestamp(System.currentTimeMillis()));
            wfp.setPackState(0L);// 未处理
            wfp.setRepairnNmber(wf.getRepairnNmber());
            workflowPackService.doInsert(wfp);// 每批次只有一个装箱记录
        }
        this.updateStateByWfids(-1L, ids);// 已发送
        workflowService.updateStateAndSendPackTime(8L, ids);
        tipService.addTip(TipState.TIP_ZX, ids.length);
    }
}
