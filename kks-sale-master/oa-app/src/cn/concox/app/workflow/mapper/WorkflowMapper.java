/*
 * Created: 2016-7-20
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
package cn.concox.app.workflow.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.WarrantyInfo;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowTemp;

/**
 * 工作流程公共处理
 * 
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
public interface WorkflowMapper<T> extends BaseSqlMapper<T> {

    @SuppressWarnings("rawtypes")
    public Page queryListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") Workflow workflow)
            throws DataAccessException;

    public Workflow getInfo(@Param("id") Integer id);

    public Workflow getInfoByImei(@Param("imei") String imei);

    public Workflow getBatchNo(@Param("sxdwId") String sxdwId);

    public Integer getReturnTimes(@Param("imei") String imei);

    public Workflow getLastReturnEngineer(@Param("imei") String imei, @Param("type") String type);

    public void updateState(@Param("state") long state, @Param("ids") String... ids);

    /**
     * @Title: updateBackToAccept
     * @Description:返回受理
     * @param state
     * @param ids
     * @author HuangGangQiang
     * @date 2017年7月26日 下午6:56:07
     */
    public void updateBackToAccept(@Param("state") Integer state, @Param("ids") String... ids);

    public void updateStateAndSendPackTime(@Param("state") long state, @Param("ids") String... ids);

    public void updateWxbjIfNotPrice(@Param("ids") String... ids);

    public void updateStateByRepairNumber(@Param("state") long state, @Param("repairNumbers") String[] repairNumbers,
            @Param("ids") String... ids);

    public void updateStateByBatchNo(@Param("state") long state, @Param("repairnNmber") String repairnNmber);

    public List<Workflow> getPackList(Workflow w);

    public List<Workflow> getImeiState(@Param("repairnNmber") String repairnNmber, @Param("imei") String imei,
            @Param("state") String state);

    /**
     * 获取装箱工站扫描过的设备个数同一批次中
     * 
     * @author TangYuping
     * @version 2017年3月17日 上午10:21:43
     * @param repairnNmber
     * @param imei
     * @param state
     * @return
     */
    public Integer getImeiInPackCount(@Param("repairnNmber") String repairnNmber, @Param("imei") String imei,
            @Param("state") String state);

    /**
     * 该批次设备总数量
     * 
     * @param repairnNmber
     * @return
     */
    public Integer getCountOfRepairnNmber(@Param("repairnNmber") String repairnNmber);

    /**
     * @Title: getOneByRepairnNmber
     * @Description:装箱获取批次号
     * @param repairnNmber
     * @return
     * @author 20160308
     * @date 2017年11月9日 下午5:46:18
     */
    public Workflow getOneByRepairnNmber(@Param("repairnNmber") String repairnNmber);

    public boolean exists(@Param("repairnNmber") String repairnNmber);

    public int acceptexists(@Param("repairnNmber") String repairnNmber);

    @SuppressWarnings("rawtypes")
    public Page<Workflow> getPackListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") Workflow workflow);

    public List<Workflow> getPackListPage(@Param("po") Workflow workflow);

    @SuppressWarnings("rawtypes")
    public Page<Workflow> getDeviceListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") Workflow workflow);

    @SuppressWarnings("rawtypes")
    public Page<Workflow> getDirectiveListPage(@Param(PageInterceptor.PAGE_KEY) Page page,
            @Param("po") Workflow workflow);

    public void deleteInfos(@Param("ids") String... ids);

    public void deleteByImei(@Param("imeis") String... imeis);

    public int isExistsAndPay(@Param("repairnNmber") String repairnNmber);

    /**
     * @Title: getRepairNumberCountPriced
     * @Description:该批次是否已报价或已付款，提示受理是否要更新批次号
     * @param repairnNmber
     * @return
     * @author 20160308
     * @date 2017年11月21日 上午9:32:38
     */
    public int getRepairNumberCountPriced(@Param("repairnNmber") String repairnNmber);

    public List<Workflow> queryRepairStateList(String repairnNmber);

    public Workflow getById(@Param("id") Integer id);

    public void updateSxdwId(Workflow wf);

    public void updateBySendFlag(Workflow wf);

    public List<Workflow> getWorkflowByIds(@Param("ids") String... ids);

    @SuppressWarnings("rawtypes")
    public Page<Workflow> querySortPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") Workflow workflow);

    @SuppressWarnings("rawtypes")
    public Page<Workflow> queryAcceptList(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") Workflow workflow);

    public List<Workflow> querySortList(Workflow workflow);

    public void updateImei(Workflow wf);

    public void updateRelatedImei(Workflow wf);

    public void updateRelatedImeiForAccept(Workflow wf);

    public void updateYcfkImei(Workflow wf);

    public void updateYcfkTwoImei(Workflow wf);

    /**
     * 根据批次号 查询保内非人为客户顺付来的数据
     * 
     * @author TangYuping
     * @version 2017年1月7日 下午2:36:51
     * @param wf
     */
    public Integer getDataByRepairnNmber(Workflow wf);

    /**
     * 放弃报价查询是否所有数据都已过了 待报价状态
     * 
     * @author TangYuping
     * @version 2017年1月7日 下午4:02:10
     * @param repairnNmber
     * @return
     */
    public Integer selCountPayLogcost(String repairnNmber);

    /**
     * 根据ID查询受理信息
     * 
     * @author TangYuping
     * @version 2017年1月10日 上午10:29:49
     * @param ids
     * @return
     */

    public List<Workflow> selectById(@Param("ids") String... ids);

    /**
     * 报价数据返回维修工站清楚物料信息
     * 
     * @author TangYuping
     * @version 2017年1月13日 上午9:59:58
     * @param ids
     */
    public void updateWlByWfids(@Param("ids") String... ids);

    /**
     * 超三天修改责任人和备注
     * 
     * @param ids
     */
    public void updateForTimeout(@Param("dutyOfficer") String dutyOfficer, @Param("timeoutRemark") String timeoutRemark,
            @Param("timeoutReason") String timeoutReason, @Param("ids") String... ids);

    /**
     * 根据IMEI获取装箱工站设备
     * 
     * @author TangYuping
     * @version 2017年3月6日 上午11:00:10
     * @param workflow
     * @return
     */
    public Workflow getInfoInPackByImei(Workflow workflow);

    /**
     * 首页查询所有字段
     * 
     * @param wf
     * @return
     */
    public List<Workflow> getDeviceList(Workflow wf);

    /**
     * 查询所有未发货的批次号（正常件）
     * 
     * @return
     */
    public List<Workflow> getAllNotPackInfo();

    public Workflow getInfoByLockId(@Param("lockId") String lockId);

    public List<Workflow> matchImei(@Param("imei") String imei);

    /**
     * @Title: updateRepairToSortByWfids
     * @Description:维修数据返回分拣工站,修改t_sale_workflow_related表数据为初始状态
     * @param ids
     * @author HuangGangQiang
     * @date 2017年7月25日 上午10:15:44
     */
    public void updateRepairToSortByWfids(@Param("ids") String... ids);

    /**
     * @Title: getRelatedIdsByImei
     * @Description:批量受理未全部成功，查询关系表的数据
     * @param imeis
     * @return
     * @author HuangGangQiang
     * @date 2017年7月25日 上午11:10:49
     */
    public String getRelatedIdsByImei(@Param("imeis") String... imeis);

    /**
     * @Title: getCountSortByIds
     * @Description:分拣工站查询选中的数据是否有不是已受理，待分拣？有：不允许分拣、返回受理
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月6日 下午4:01:22
     */
    public int getCountSortByIds(@Param("ids") String... ids);

    /**
     * @Title: getCountToTestByIds
     * @Description:受理工站查询选中的数据是否有不是已受理？有：不允许发送测试,分拣
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月10日 下午2:15:18
     */
    public int getCountToTestByIds(@Param("ids") String... ids);

    //// ========================================================================================================
    ////// ===============================售后系统修改线上数据 start=================================================
    //// ========================================================================================================

    /**
     * @Title: getDataList
     * @Description:根据imei和批次号查询数据
     * @param workflow
     * @return
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:07:44
     */
    public List<Workflow> getDataList(Workflow workflow);

    /**
     * @Title: getIdInPack
     * @Description:查询是否已装箱 修改成已分拣，待维修或待报价状态
     * @param repairnNmber
     * @return
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:09:34
     */
    public String getIdInPack(@Param("repairnNmber") String repairnNmber);

    /**
     * @Title: deleteInfoForPack
     * @Description:删除装箱数据 修改成已分拣，待维修或待报价状态
     * @param repairnNmber
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:22:38
     */
    public void deleteInfoForPack(@Param("repairnNmber") String repairnNmber);

    /**
     * @Title: deletePriceForRepair
     * @Description:删除price数据 修改成已分拣，待维修状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:22:43
     */
    public void deletePriceForRepair(@Param("wfId") Integer wfId);

    /**
     * @Title: getPriceCountForRepair
     * @Description:查询price表里是否有wfid的数据 修改成已分拣，待维修状态
     * @param wfId
     * @return
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:22:47
     */
    public int getPriceCountForRepair(@Param("wfId") Integer wfId);

    /**
     * @Title: getFicheckCountForRepair
     * @Description:查询ficheck表里是否有wfid的数据 修改成已分拣，待维修状态
     * @param wfId
     * @return
     * @author HuangGangQiang
     * @date 2017年7月27日 上午11:14:00
     */
    public int getFicheckCountForRepair(@Param("wfId") Integer wfId);

    /**
     * @Title: deleteFicheckForRepair
     * @Description:删除ficheck数据 修改成已分拣，待维修状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月27日 上午11:15:39
     */
    public void deleteFicheckForRepair(@Param("wfId") Integer wfId);

    /**
     * @Title: deleteInfoForRelated
     * @Description:删除t_sale_related_zzgz表里rfiId为wfid的数据 修改成已分拣，待维修或待报价状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:22:52
     */
    public void deleteInfoForRelated(@Param("wfId") Integer wfId);

    /**
     * @Title: updateRepairForRepair
     * @Description:修改repair表数据改成刚分拣的状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:22:57
     */
    public void updateRepairForRepair(@Param("wfId") Integer wfId);

    /**
     * @Title: updateRepairForPrice
     * @Description:repair表状态改为-1，维修总金额 = 维修费+工时费，放弃报价时间等修改为未放弃 修改成待报价状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:23:02
     */
    public void updateRepairForPrice(@Param("wfId") Integer wfId);

    /**
     * @Title: updatePriceForPrice
     * @Description:price数据状态0，付款状态，维修金额=维修报价+ 工时费 修改成待报价状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:23:07
     */
    public void updatePriceForPrice(@Param("wfId") Integer wfId);

    /**
     * @Title: updateRelatedForRepair
     * @Description:related表的报价及物料数据清除 修改成已分拣，待维修状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:23:11
     */
    public void updateRelatedForRepair(@Param("wfId") Integer wfId);

    /**
     * @Title: updateRelatedForPrice
     * @Description:related表的最终故障数据清除 修改成待报价状态
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:23:18
     */
    public void updateRelatedForPrice(@Param("wfId") Integer wfId);

    /**
     * @Title: updateInfoForWorkflow
     * @Description:workflow表的状态改成2或3，发送装箱时间置空 修改成已分拣，待维修或待报价状态
     * @param state
     * @param wfId
     * @author HuangGangQiang
     * @date 2017年7月26日 下午5:23:15
     */
    public void updateInfoForWorkflow(@Param("state") Integer state, @Param("wfId") Integer wfId);

    /**
     * @Title: updateInfoForWorkflow
     * @Description:插入临时数据
     * @param state
     * @param wfId
     * @author 马文军
     * @date 2017年7月26日 下午5:23:15
     */
    public void insertWorkflowInfo(WorkflowTemp wf);

    /**
     * @Title: updateInfoForWorkflow
     * @Description:获取临时数据
     * @param state
     * @param wfId
     * @author 马文军
     * @date 2017年7月26日 下午5:23:15
     */
    public List<WorkflowTemp> getWorkflowInfo(@Param("uId") String uId);

    /**
     * @Title: updateInfoForWorkflow
     * @Description:修改临时数据
     * @param state
     * @param wfId
     * @author 马文军
     * @date 2017年7月26日 下午5:23:15
     */
    public void updateWorkflowInfo(WorkflowTemp workflow);

    /**
     * @Title: updateInfoForWorkflow
     * @Description:根据IMEI获取临时数据
     * @param state
     * @param wfId
     * @author 马文军
     * @date 2017年7月26日 下午5:23:15
     */
    public WorkflowTemp getWorkflowInfoByImei(@Param("imei") String imei);

    /**
     * @Title: deleteWorkflowInfoByUserId
     * @Description:根据用户Id删除数据
     * @param state
     * @param wfId
     * @author 马文军
     * @date 2017年7月26日 下午5:23:15
     */
    public void deleteWorkflowInfoByUserId(@Param("uId") String uId);
    
    //// ========================================================================================================
    //////// ===============================售后系统修改线上数据 end================================================
    //// ========================================================================================================

    
    @SuppressWarnings("rawtypes")
    Page<WarrantyInfo> getWarrantyInfo(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("warranty") WarrantyInfo warranty);
    
    List<WarrantyInfo> listWarrantyInfo(WarrantyInfo warranty);

    void insertWarrantyInfo(List<WarrantyInfo> list);

    void updateInWarranty(List<String> list);

    void updateOutWarranty(List<String> list);

    Integer getProlongMonthByImei(String imei);

    void deleteWarrantyInfo(List<String> list);

    List<String> repeatWarrantyImei(List<String> list);

    List<String> listCpName();
    
    @Update("update t_sale_workflow set salesTime = #{outdate } where imei = #{imei }")
	void updateOutdateByImei(@Param("imei") String imei,@Param("outdate") Date outdate);

    /**
     * 根据imei和送修批号更改送修单位和客户名
     * @param imei
     * @param repairnNmber
     * @param sxdwId
     * @param cusName
     */
    void updateCustomer(@Param("imei")String imei,@Param("repairnNmber")String repairnNmber,@Param("sxdwId")Integer sxdwId,@Param("cusName")String cusName);

    public List<Workflow> selectNotSendListPage(@Param(PageInterceptor.PAGE_KEY)Page page,@Param("po") Workflow workflow);

    List<Workflow> listNotSendInfo(@Param("po") Workflow workflow);

    /**
     * 通过IMEI判断是否已经存在保修期数据
     * @param imei
     * @return
     */
    public boolean existsWarrantyByImei(String imei);

    /**
     *获取超期并且没有锁定超期天数的记录
     * 发送装箱即锁定超期天数
     */
    public List<Workflow> selectTimeoutAndNotLockData(Date date);

    /**
     * 更新超天数
     * @param imei
     * @param repairNumber
     * @param timeoutDays
     */
    public void updateTimeoutDay(@Param("imei")String imei,@Param("repairNumber")String repairNumber,@Param("timeoutDays")Integer timeoutDays);
    public void updateTimeoutDayById(@Param("id") Integer id,@Param("timeoutDays") Integer timeoutDays);

    /**
     * 更新超天原因
     * @param imei
     * @param repairNumber
     * @param timeoutReason
     */
    public void updateTimeoutReason(@Param("imei")String imei,@Param("repairNumber")String repairNumber,@Param("timeoutReason")String timeoutReason);
    public void updateTimeoutReasonById(@Param("id")Integer id,@Param("timeoutReason")String timeoutReason);

    public Integer selectCountBetweenAcceptTime(@Param("from") Date from,@Param("to") Date to,@Param("sxdwId") Integer sxdwId);

    /**
     * 更改超天备注
     * @param id
     */
    public void updateTimeoutRemarkById(@Param("id") Integer id,@Param("timeoutRemark") String timeoutRemark);

    /**
     * 查询backTime为空的数据
     * @return
     */
    public List<Workflow> selectNotHaveBackTime();

    public void updateBackTimeById(@Param("id")Integer id,@Param("backTime")Date backTime);

    public List<Workflow> selectNotHaveBackTimeByTime(@Param("start") Date start,@Param("end")Date end);
}
