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
package cn.concox.app.workflow.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.app.basicdata.service.WorkDateService;
import cn.concox.comm.Globals;
import cn.concox.vo.basicdata.*;

import cn.concox.vo.report.HwmReport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.concox.app.basicdata.mapper.CjgzmanageMapper;
import cn.concox.app.basicdata.mapper.DzlmanageMapper;
import cn.concox.app.basicdata.mapper.PjlmanageMapper;
import cn.concox.app.basicdata.mapper.RepairPriceMapper;
import cn.concox.app.basicdata.mapper.SjfjmanageMapper;
import cn.concox.app.basicdata.mapper.SxdwmanageMapper;
import cn.concox.app.basicdata.mapper.TestMaterialMapper;
import cn.concox.app.basicdata.mapper.ZgzmanageMapper;
import cn.concox.app.basicdata.service.SxdwmanageService;
import cn.concox.app.basicdata.service.TipService;
import cn.concox.app.basicdata.service.TipService.TipState;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.app.report.service.HwmReportService;
import cn.concox.app.workflow.mapper.WorkflowFicheckMapper;
import cn.concox.app.workflow.mapper.WorkflowMapper;
import cn.concox.app.workflow.mapper.WorkflowPriceMapper;
import cn.concox.app.workflow.mapper.WorkflowRelatedMapper;
import cn.concox.app.workflow.mapper.WorkflowRepairMapper;
import cn.concox.app.workflow.mapper.WorkflowTestMapper;
import cn.concox.app.workflow.mapper.WorkflowTotalpayMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.mail.other.CVMailUtil;
import cn.concox.comm.sms.other.SMSConstants;
import cn.concox.comm.sms.other.UCPaasUtils;
import cn.concox.comm.util.Convert;
import cn.concox.comm.util.DateUtil;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.util.http.Result;
import cn.concox.comm.util.http.SendRequest;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowFicheck;
import cn.concox.vo.workflow.WorkflowPrice;
import cn.concox.vo.workflow.WorkflowRelated;
import cn.concox.vo.workflow.WorkflowRepair;
import cn.concox.vo.workflow.WorkflowTemp;
import cn.concox.vo.workflow.WorkflowTest;
import cn.concox.vo.workflow.WorkflowTotalpay;
import freemarker.template.Template;
import sso.comm.model.UserInfo;

/**
 * 工作流程 公共处理
 * 
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
@Service("workflowService")
@Scope("prototype")
public class WorkflowService {

    Logger logger = Logger.getLogger("workflowInfo");

    @Value("${ams_device_url}")
    private String AMS_DEVICE_URL;

    @Resource(name = "workflowMapper")
    private WorkflowMapper<Workflow> workflowMapper;

    @Resource(name = "sxdwmanageMapper")
    private SxdwmanageMapper<Sxdwmanage> sxdwmanageMapper;

    @Resource(name = "sxdwmanageService")
    public SxdwmanageService sxdwmanageService;

    @Resource(name = "sjfjmanageMapper")
    private SjfjmanageMapper<Sjfjmanage> sjfjmanageMapper;

    @Resource(name = "cjgzmanageMapper")
    private CjgzmanageMapper<Cjgzmanage> cjgzmanageMapper;

    @Resource(name = "zgzmanageMapper")
    private ZgzmanageMapper<Zgzmanage> zgzmanageMapper;

    @Resource(name = "pjlmanageMapper")
    private PjlmanageMapper<Pjlmanage> pjlmanageMapper;

    @Resource(name = "dzlmanageMapper")
    private DzlmanageMapper<Dzlmanage> dzlmanageMapper;

    @Resource(name = "testMaterialMapper")
    private TestMaterialMapper<TestMaterial> testMaterialMapper;

    @Resource(name = "workflowRelatedMapper")
    private WorkflowRelatedMapper<WorkflowRelated> workflowRelatedMapper;

    @Resource(name = "repairPriceMapper")
    private RepairPriceMapper<RepairPriceManage> repairPriceMapper;

    @Resource(name = "workflowPriceMapper")
    private WorkflowPriceMapper<WorkflowPrice> workflowPriceMapper;

    @Resource(name = "workflowRepairMapper")
    private WorkflowRepairMapper<WorkflowRepair> workflowRepairMapper;

    @Resource(name = "workflowTotalpayMapper")
    private WorkflowTotalpayMapper<WorkflowTotalpay> workflowTotalpayMapper;

    @Autowired
    private HwmReportService hwmReportService;

    @Autowired
    private WorkflowTestMapper<WorkflowTest> workflowTestMapper;

    @Resource(name = "workflowFicheckMapper")
    private WorkflowFicheckMapper<WorkflowFicheck> workflowFicheckMapper;

    @Resource(name = "tipService")
    private TipService tipService;

    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource(name = "workDateService")
    private WorkDateService workDateService;


    /**
     * 受理时判断是否存在
     * 
     * @param workflow
     * @return
     */
    public int isExists(Workflow workflow) {
        return workflowMapper.isExists(workflow);
    }

    /**
     * 获取分页数据
     * <p>
     * 暂无使用
     * </p>
     * 
     * @param workflow
     * @param getCurrentPage
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<Workflow> getListPage(Workflow workflow, int getCurrentPage) {
        Page<Workflow> workflowList = new Page<Workflow>();
        workflowList.setCurrentPage(getCurrentPage);
        workflowList = workflowMapper.queryListPage(workflowList, workflow);
        List<Workflow> workflows = workflowList.getResult();
        setList(workflows);
        workflowList.setResult(workflows);
        return workflowList;

    }

    /**
     * * 获取受理单据
     * <p>
     * 数据封装
     * </p>
     * 
     * @param workflow
     *            Comm工作流 表
     * @return
     */
    public Page<Workflow> getAcceptList(Workflow workflow, Integer currentpage, Integer pageSize) {
        Page<Workflow> workflows = new Page<Workflow>();
        workflows.setCurrentPage(currentpage);
        workflows.setSize(pageSize);

        String treeDateStr = workflow.getTreeDate();
        if (treeDateStr != null && StringUtils.isNotBlank(treeDateStr)) {
            int morIndex = treeDateStr.indexOf("上午");
            int aftIndex = treeDateStr.indexOf("下午");
            if (morIndex > -1) {
                // 查询下午数据
                workflow.setSearchMorOrAft("mor");
                workflow.setTreeDate(treeDateStr.substring(0, treeDateStr.indexOf("上午")));
            } else if (aftIndex > -1) {
                workflow.setSearchMorOrAft("aft");
                workflow.setTreeDate(treeDateStr.substring(0, treeDateStr.indexOf("下午")));
            }
        }
        workflows = workflowMapper.queryAcceptList(workflows, workflow);
        return workflows;
    }

    /**
     * 设置预计返还日期
     * 
     * @author TangYuping
     * @version 2017年1月3日 上午10:21:37
     * @param workflows
     * @return
     */
    public Workflow setBackDate(Workflow wf) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (wf.getAcceptanceTime() != null && StringUtils.isNotBlank(wf.getAcceptanceTime().toString())) {
            if (wf.getAcceptanceTime().getTime() >= DateTimeUtil.getDateTime(GlobalCons.BASETIME).getTime()) {
                Timestamp backTimeState = new Timestamp(
                        hwmReportService.getInvalidTime(wf.getAcceptanceTime()).getTime());
                wf.setBackDate(df.format(backTimeState));
            } else {
                Timestamp backTimeState = new Timestamp(
                        hwmReportService.getInvalidTime1(wf.getAcceptanceTime()).getTime());
                wf.setBackDate(df.format(backTimeState));
            }
        }
        return wf;
    }

    /**
     * * 获取分拣分页单据
     * <p>
     * 数据封装
     * </p>
     * 
     * @param workflow
     *            Comm工作流 表
     * @return
     */
    public Page<Workflow> getSortPage(Workflow workflow, Integer currentpage, Integer pageSize) {
        Page<Workflow> workflows = new Page<Workflow>();
        workflows.setCurrentPage(currentpage);
        workflows.setSize(pageSize);
        workflows = workflowMapper.querySortPage(workflows, workflow);
        return workflows;
    }

    /**
     * * 获取分拣单据
     * <p>
     * 数据封装
     * </p>
     * 
     * @param workflow
     *            Comm工作流 表
     * @return
     */
    public List<Workflow> getSortList(Workflow workflow) {
        List<Workflow> workflows = workflowMapper.querySortList(workflow);
        for (Workflow wf : workflows) {
            this.setBackDate(wf);
        }
        setList(workflows);
        return workflows;
    }

    /**
     * 数据封装
     * 
     * @param workflows
     * @return
     */
    public List<Workflow> setList(List<Workflow> workflows) {
        if (null != workflows && workflows.size() > 0) {
            for (Workflow wf : workflows) {
                this.setBackDate(wf);
                if (!StringUtil.isEmpty(wf.getW_model()) && !StringUtil.isEmpty(wf.getW_marketModel())) {
                    wf.setW_model(
                            new StringBuilder(wf.getW_model()).append(" | ").append(wf.getW_marketModel()).toString());
                }
                this.getAllGroupConcat(wf);
                if (null != wf.getTestResult() && !"".equals(wf.getTestResult())) {
                    StringBuffer result = new StringBuffer();
                    if (wf.getTestPerson() != null && wf.getTestTime() != null) {
                        result.append("———" + wf.getTestPerson() + "    " + wf.getTestTime());
                        wf.setTestResult(wf.getTestResult() + result);
                    }
                }
            }
        }
        return workflows;
    }

    public Workflow getT(Integer id) {
        return workflowMapper.getT(id);
    }

    /**
     * 根据批次号 查询保内非人为客户顺付来的数据
     * 
     * @author TangYuping
     * @version 2017年1月7日 下午2:36:51
     * @param wf
     */
    public void getDataByRepairNmber(Workflow wf) {
        if (wf != null && wf.getRepairnNmber() != null && StringUtils.isNotBlank(wf.getRepairnNmber())) {
            int count = workflowMapper.getDataByRepairnNmber(wf);
            if (count > 0) {
                // 设置物流支付状态为空
                workflowPriceMapper.updateLogcostStatusByNmber(wf.getRepairnNmber(), "");
                workflowTotalpayMapper.updateReceiptByNumber(wf.getRepairnNmber());
            }
        }
    }

    /**
     * 批量删除
     * 
     * @param ids
     */
    @Transactional
    public void deleteInfos(String... ids) {
        List<Workflow> list = workflowMapper.selectById(ids);
        workflowMapper.deleteInfos(ids);
        // 批量删除受理数据的时候删除 workflow_related 表记录
        StringBuffer relateId = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (Workflow wf : list) {
                if (wf.getRfild() != null && wf.getRfild() > 0) {
                    relateId.append("," + wf.getRfild());
                }
            }
            this.deleteRelated(relateId);

        }
    }

    /**
     * 删除受理信息时连带删除关联表
     * 
     * @author TangYuping
     * @version 2017年1月10日 上午10:34:10
     * @param rIds
     */
    public void deleteRelated(StringBuffer rIds) {
        String[] rids = rIds.toString().substring(1).split(",");
        workflowRelatedMapper.deleteByIds(rids);
    }

    /**
     * 根据imei批量删除
     */
    @Transactional
    public void deleteByImei(String... imeis) {
        // 批量受理未全部成功，查询关系表的id
        String rids = workflowMapper.getRelatedIdsByImei(imeis);
        if (!StringUtil.isRealEmpty(rids)) {
            workflowRelatedMapper.deleteByIds(rids.split(","));
        }
        workflowMapper.deleteByImei(imeis);
    }

    /**
     * 获取受理单据
     * <p>
     * 附联数据封装-供其他工作站使用
     * </p>
     * <Desc> 列表数据封装 True ,数据提取False</Desc>
     * 
     * @param id
     *            主键 ID
     * @param falg
     *            受理工作站 False | 其他工作站 True
     * @return
     */
    public Workflow getInfo(int id, boolean falg) {
        Workflow wf = workflowMapper.getInfo(id);
        if (null != wf) {
            wf.setW_sjfj(wf.getW_sjfjDesc());
            wf.setW_cjgzId(wf.getW_cjgzDesc());
            wf.setW_zzgzId(wf.getW_zzgzDesc());
            wf.setW_wxbjId(wf.getW_wxbjDesc());
            wf.setW_matId(wf.getW_matDesc());
            wf.setW_dzlId(wf.getW_dzlDesc());
            wf.setW_sllMatNO(wf.getW_sllDesc());
            if (!StringUtil.isEmpty(wf.getW_cjgzDesc())) {
                // TODO 初检故障
                String cjgz_Name = cjgzmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_cjgzDesc()));
                String cjgzIds = this.sortIds(wf.getW_cjgzDesc());
                wf.setW_cjgzId(cjgzIds);
                wf.setW_cjgzDesc(cjgz_Name);
            }
            if (!StringUtil.isEmpty(wf.getW_sjfjDesc())) {
                // TODO 随机附件
                String sjfj_Name = sjfjmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_sjfjDesc()));
                String sjfjIds = this.sortIds(wf.getW_sjfjDesc());
                wf.setW_sjfj(sjfjIds);
                wf.setW_sjfjDesc(sjfj_Name);
            }
            if (falg) {
                if (!StringUtil.isEmpty(wf.getW_zzgzDesc())) {
                    // TODO 最终故障
                    String zzgz_Name = zgzmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_zzgzDesc()));
                    String zzgzIds = this.sortIds(wf.getW_zzgzDesc());
                    wf.setW_zzgzId(zzgzIds);
                    wf.setW_zzgzDesc(zzgz_Name);
                }
                if (!StringUtil.isEmpty(wf.getW_wxbjDesc())) {
                    // TODO 维修报价
                    String wxbj_Name = repairPriceMapper.getGRoupConcat(StringUtil.split(wf.getW_wxbjDesc()));
                    String wxbjIds = this.sortIds(wf.getW_wxbjDesc());
                    wf.setW_wxbjId(wxbjIds);
                    wf.setW_wxbjDesc(wxbj_Name);
                }
                if (!StringUtil.isEmpty(wf.getW_matDesc())) {
                    // TODO 维修配件料
                    String wxzj_Name = pjlmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_matDesc()));
                    String pjlIds = this.sortIds(wf.getW_matDesc());
                    wf.setW_matId(pjlIds);
                    wf.setW_matDesc(wxzj_Name);
                }
                if (!StringUtil.isEmpty(wf.getW_dzlDesc())) {
                    // TODO 维修电子料
                    String wxDzl_Name = dzlmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_dzlDesc()));
                    String dzlIds = this.sortIds(wf.getW_dzlDesc());
                    wf.setW_dzlId(dzlIds);
                    wf.setW_dzlDesc(wxDzl_Name);
                }
                if (!StringUtil.isEmpty(wf.getW_sllDesc()) && !StringUtil.isEmpty(wf.getBill())) {
                    // TODO 维修试流料
                    String wxSll_Name = testMaterialMapper.getGRoupConcat(StringUtil.split(wf.getW_sllDesc()));
                    wf.setW_sllMatNO(wf.getW_sllDesc());
                    wf.setW_sllDesc(wxSll_Name);
                }
            }
        }
        return wf;
    }

    /**
     * 根据组合的id，获得名字或描述
     * 
     * @param wf
     */
    private void getAllGroupConcat(Workflow wf) {
        if (null != wf) {
            if (!StringUtil.isEmpty(wf.getW_cjgzDesc())) {
                // TODO 初检故障
                String cjgz_Name = cjgzmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_cjgzDesc()));
                String cjgzIds = this.sortIds(wf.getW_cjgzDesc());
                wf.setW_cjgzId(cjgzIds);
                wf.setW_cjgzDesc(cjgz_Name);
            }
            if (!StringUtil.isEmpty(wf.getW_sjfjDesc())) {
                // TODO 随机附件
                String sjfj_Name = sjfjmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_sjfjDesc()));
                String sjfjIds = this.sortIds(wf.getW_sjfjDesc());
                wf.setW_sjfj(sjfjIds);
                wf.setW_sjfjDesc(sjfj_Name);
            }
            if (!StringUtil.isEmpty(wf.getW_zzgzDesc())) {
                // TODO 最终故障
                String zzgz_Name = zgzmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_zzgzDesc()));
                String zzgzIds = this.sortIds(wf.getW_zzgzDesc());
                wf.setW_zzgzId(zzgzIds);
                wf.setW_zzgzDesc(zzgz_Name);
            }
            if (!StringUtil.isEmpty(wf.getW_wxbjDesc())) {
                // TODO 维修报价
                String wxbj_Name = repairPriceMapper.getGRoupConcat(StringUtil.split(wf.getW_wxbjDesc()));
                String wxbjIds = this.sortIds(wf.getW_wxbjDesc());
                wf.setW_wxbjId(wxbjIds);
                wf.setW_wxbjDesc(wxbj_Name);
            }
            if (!StringUtil.isEmpty(wf.getW_matDesc())) {
                // TODO 维修配件料
                String wxzj_Name = pjlmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_matDesc()));
                String pjlIds = this.sortIds(wf.getW_matDesc());
                wf.setW_matId(pjlIds);
                wf.setW_matDesc(wxzj_Name);
            }
            if (!StringUtil.isEmpty(wf.getW_dzlDesc())) {
                // TODO 维修电子料
                String wxDzl_Name = dzlmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_dzlDesc()));
                String dzlIds = this.sortIds(wf.getW_dzlDesc());
                wf.setW_dzlId(dzlIds);
                wf.setW_dzlDesc(wxDzl_Name);
            }
            if (!StringUtil.isEmpty(wf.getW_sllDesc()) && !StringUtil.isEmpty(wf.getBill())) {
                // TODO 维修试流料
                String wxSll_Name = testMaterialMapper.getGRoupConcat(StringUtil.split(wf.getW_sllDesc()));
                wf.setW_sllMatNO(wf.getW_sllDesc());
                wf.setW_sllDesc(wxSll_Name);
            }
        }
    }

    /**
     * 对当前对象做赋值处理，
     * 
     * @author TangYuping
     * @version 2016年12月15日 下午4:18:49
     * @param wf
     * @return
     */
    public Workflow getSomeType(Workflow wf) {
        wf.setW_zzgzId(wf.getW_zzgzDesc());
        wf.setW_wxbjId(wf.getW_wxbjDesc());
        if (!StringUtil.isEmpty(wf.getW_zzgzDesc())) {
            // TODO 最终故障
            String zzgz_Name = zgzmanageMapper.getGRoupConcat(StringUtil.split(wf.getW_zzgzDesc()));
            String zzgzIds = this.sortIds(wf.getW_zzgzDesc());
            wf.setW_zzgzId(zzgzIds);
            wf.setW_zzgzDesc(zzgz_Name);
        }
        if (!StringUtil.isEmpty(wf.getW_wxbjDesc())) {
            // TODO 维修报价
            String wxbj_Name = repairPriceMapper.getGRoupConcat(StringUtil.split(wf.getW_wxbjDesc()));
            String wxbjIds = this.sortIds(wf.getW_wxbjDesc());
            wf.setW_wxbjId(wxbjIds);
            wf.setW_wxbjDesc(wxbj_Name);
        }
        return wf;
    }

    /**
     * 组件选择给ID排序后返回到前端，防止文字和ID对应不上
     * 
     * @author TangYuping
     * @version 2016年12月13日 下午2:44:54
     * @param id
     * @return
     */
    public String sortIds(String id) {
        String[] ids = id.split(",");
        LinkedList<Integer> listIds = new LinkedList<Integer>();
        int intId = 0;
        for (String stringId : ids) {
            intId = Integer.valueOf(stringId);
            listIds.add(intId);
        }
        Collections.sort(listIds);
        String newId = "";
        for (Integer iid : listIds) {
            newId += "," + iid;
        }
        return newId.substring(1);
    }

    /**
     * 更新修改单据信息
     * 
     * @param workflow
     */
    @Transactional
    public void updateInfo(Workflow workflow) {
        try {
            // TODO 同步联表数据
            if (null != workflow && null != workflow.getRfild()) {
                WorkflowRelated related = new WorkflowRelated();
                related.setId(workflow.getRfild());
                related.setCjgzDesc(workflow.getW_cjgzId());
                related.setSjfjDesc(workflow.getW_sjfj());
                workflowRelatedMapper.update(related);
            }
            if (workflow != null && workflow.getId() != null) {
                Workflow wf = workflowMapper.getT(workflow.getId());
                if (wf.getCustomerStatus().equals("un_normal") && workflow.getCustomerStatus().equals("normal")) {
                    // 之前的数据是无名件才做更新客户信息的操作,改成正常状态
                    this.updateUnnormalCus(workflow);
                } else {
                    // TODO 修改单据数据
                    workflowMapper.update(workflow);
                    workflowMapper.updateRelatedImeiForAccept(workflow);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 无名件数据更新客户信息
     * 
     * @author TangYuping
     * @version 2016年11月24日 下午5:16:00
     * @param workflow
     */
    public void updateUnnormalCus(Workflow workflow) {
        if (workflow != null) {
            Sxdwmanage cus = sxdwmanageMapper.findCustomerByPhone(workflow.getW_phone());
            if (cus == null) {
                cus = new Sxdwmanage();
            }
            cus.setAddress(workflow.getW_address());
            cus.setCusName(workflow.getW_cusName());
            cus.setEmail(workflow.getW_email());
            cus.setFax(workflow.getW_fax());
            cus.setLinkman(workflow.getW_linkman());
            cus.setPhone(workflow.getW_phone());
            cus.setTelephone(workflow.getW_telephone());
            if (cus.getCId() != null && cus.getCId() > 0) {
                // 无名氏更改的送修单位存在于系统中，做更新操作
                sxdwmanageMapper.update(cus);
                workflow.setSxdwId(Long.valueOf(cus.getCId()));
                if (null != workflow.getAccTime() && "accTime".equals(workflow.getAccTime())) {
                    workflow.setAcceptanceTime(new Timestamp(System.currentTimeMillis()));
                }
            } else {
                // 添加新的送修单位
                cus.setServiceTime("0");// 无名件添加时服务周期默认是0
                sxdwmanageService.insertSxdwmanage(cus);
                workflow.setSxdwId(Long.valueOf(cus.getCId()));
            }
            workflowMapper.updateSxdwId(workflow); // 修改一个客户正常时，这一批客户都变成正常状态
        }
    }

    /**
     * 新增受理单据
     * 
     * @param workflow
     * @throws ParseException
     * @throws IOException
     * @throws ClientProtocolException
     */
    @Transactional
    public void insertInfo(Workflow workflow) {
        try {
            {
                // TODO 附联表数据同步
                WorkflowRelated related = new WorkflowRelated();
                related.setFild(new StringBuilder(workflow.getImei()).append(System.currentTimeMillis()).toString());
                related.setImei(workflow.getImei());
                related.setCreateTime(new Timestamp(System.currentTimeMillis()));
                related.setCjgzDesc(workflow.getW_cjgzId());
                related.setSjfjDesc(workflow.getW_sjfj());

                workflowRelatedMapper.doInsert(related);

                // TODO 受理单据
                workflow.setRfild(related.getId());
                workflow.setAcceptanceTime(new Timestamp(System.currentTimeMillis()));
                workflow.setState(0); // 待发送
                if (null == workflow.getReturnTimes()) {
                    workflow.setReturnTimes(1L);
                }
                if (null != workflow.getStrSalesTime()) {
                    workflow.setSalesTime(DateUtil.getTimestampByStr(workflow.getStrSalesTime()));
                }
                if (null != workflow.getStrLastAccTime()) {
                    workflow.setLastAccTime(DateUtil.getTimestampByStr(workflow.getStrLastAccTime()));
                }

                workflow.setPayedLogCost("1");
                workflowMapper.insert(workflow);
            }
        } catch (Exception e) {
            logger.info("受理失败！" + e);
        }
    }

    // 发送短信
    public void pushMsg(Workflow workflow) {
        try {
            if (workflowMapper.acceptexists(workflow.getRepairnNmber()) == 0) {
                // TODO 短信推送
                {
                    Sxdwmanage sxdwmanage = sxdwmanageMapper.getT(Convert.getInteger(workflow.getSxdwId().toString()));
                    logger.info("【受理】发送短信：" + "送修单位id：" + sxdwmanage.getCId() + ",送修单位：" + sxdwmanage.getCusName()
                            + ",手机号：" + sxdwmanage.getPhone() + ",批次号：" + workflow.getRepairnNmber());

                    // 发送短信
                    String ret = UCPaasUtils.sendSms(SMSConstants.SMS_APPID, SMSConstants.ACCEPT_TEMPLATE_ID,
                            new StringBuilder(sxdwmanage.getCusName()).append(",").append(workflow.getRepairnNmber())
                                    .toString(),
                            sxdwmanage.getPhone());
                    logger.info("受理短信发送：" + ret);
                    // 发送邮件
                    if (!StringUtil.isRealEmpty(sxdwmanage.getEmail())
                            && sxdwmanage.getEmail().trim().indexOf("@") > 0) {
                        boolean flag = CVMailUtil.sendMailForeach(sxdwmanage.getEmail().split(","), "几米售后部维修服务推送",
                                "尊敬的客户，" + sxdwmanage.getCusName() + "，您批次号为" + workflow.getRepairnNmber()
                                        + "的送修设备已经被受理。");
                        logger.info(
                                "【受理】邮件发送：" + (flag
                                        ? "成功!送修单位id：" + sxdwmanage.getCId() + ",送修单位：" + sxdwmanage.getCusName()
                                                + ",手机号：" + sxdwmanage.getPhone() + ",批次号：" + workflow.getRepairnNmber()
                                        : "失败"));
                    }

                }
                workflow.setSendFlag(1);
                workflowMapper.updateBySendFlag(workflow);
            }
        } catch (Exception e) {
            logger.info("短信发送失败！" + e);
        }
    }

    public List<Workflow> getRepairStateList(String repairnNmber) {
        List<Workflow> workflows = workflowMapper.queryRepairStateList(repairnNmber);
        return workflows;
    }

    /**
     * 根据IMEI获取最近受理时间
     * <p>
     * 获取当天的批次号 、如果没有则自动生成
     * </p>
     * 
     * @param imei
     * @return
     */
    public Workflow getInfoByImei(String imei) {
        return workflowMapper.getInfoByImei(imei);
    }

    public Workflow getInfoByLockId(String lockId) {
        return workflowMapper.getInfoByLockId(lockId);
    }

    /**
     * 根据送修单位获取批号
     * <p>
     * 获取当天的批次号 、如果没有则自动生成
     * </p>
     * 
     * @param sxdwId
     * @return
     */
    public Workflow getBatchNo(String sxdwId) {
        return workflowMapper.getBatchNo(sxdwId);
    }

    /**
     * 获取返修次数
     * <p>
     * 三个月以内则，返修次数递增
     * </p>
     * 
     * @param imei
     * @return
     */
    public Integer getReturnTimes(String imei) {
        return workflowMapper.getReturnTimes(imei);
    }

    /**
     * 二次返修，根据imei自动查询到上次维修人员
     * 
     * @param imei
     * @return
     */
    public Workflow getLastReturnEngineer(String imei, String type) {
        return workflowMapper.getLastReturnEngineer(imei, type);
    }

    /**
     * 更新单据状态
     * 
     * @param ids
     *            单据ID 集合
     * @param state
     *            状态
     */
    public void updateState(long state, String... ids) {
        if (!StringUtil.isEmpty(ids.toString())) {
            workflowMapper.updateState(state, ids);
        }
    }

    /**
     * @Title: updateRepairToSortByWfids
     * @Description:维修数据返回分拣工站,修改t_sale_workflow_related表数据为初始状态
     * @param ids
     * @author HuangGangQiang
     * @date 2017年7月25日 上午10:21:20
     */
    public void updateRepairToSortByWfids(String... ids) {
        if (!StringUtil.isEmpty(ids.toString())) {
            workflowMapper.updateRepairToSortByWfids(ids);
        }
    }

    public void updateStateAndSendPackTime(long state, String... ids) {
        if (!StringUtil.isEmpty(ids.toString())) {
            workflowMapper.updateStateAndSendPackTime(state, ids);
        }
    }

    public void updateStateByRepairNumber(long state, String[] repairNumbers, String... wfids) {
        if (wfids != null && !StringUtil.isEmpty(wfids.toString()) && repairNumbers != null
                && !StringUtil.isEmpty(repairNumbers.toString())) {
            workflowMapper.updateStateByRepairNumber(state, repairNumbers, wfids);
        }
    }

    public boolean exists(String repairnNmber) {
        return workflowMapper.exists(repairnNmber);
    }

    /**
     * 受理时判断该批次是否已发货 是，则重新生成新的批次
     * 
     * @param repairnNmber
     * @return
     */
    public int isExistsAndPay(String repairnNmber) {
        return workflowMapper.isExistsAndPay(repairnNmber);
    }

    /**
     * 首页设备查询
     * 
     * @param crucial
     * @param fashion
     * @param currentpage
     * @param pageSize
     * @return
     */
    public Page<Workflow> getDeviceListPage(String crucial, String fashion, Workflow w, Integer currentpage,
            Integer pageSize) {
        Page<Workflow> workflows = new Page<Workflow>();
        workflows.setCurrentPage(currentpage);
        workflows.setSize(pageSize);
        Workflow workflow = new Workflow();
        if ("0".equals(fashion)) {
            workflow.setW_cusName(crucial); // 送修单位
        } else if ("1".equals(fashion)) {
            workflow.setRepairnNmber(crucial); // 送修批号
        } else if ("2".equals(fashion)) {
            workflow.setW_phone(crucial); // 手机号
        } else if ("3".equals(fashion)) {
            List<String> imeis = Arrays.asList(crucial.split(","));
            workflow.setImeis(imeis);// IMEI
        } else if ("4".equals(fashion)) {
            workflow.setCustomerStatus("un_normal"); // 无名件
            workflow.setCusName(crucial);
        } else if ("5".equals(fashion)) {
            workflow.setW_zzgzDesc(crucial); // 最终故障
        } else if ("6".equals(fashion)) {
            workflow.setLockId(crucial); // 智能锁ID
        } else if ("7".equals(fashion)) {
            workflow.setW_expressNO(crucial); // 快递单号
        } else if ("8".equals(fashion)) {
            workflow.setW_model(crucial); // 主板型号
        }

        if (w.getStartTime() != null && StringUtils.isNotBlank(w.getStartTime())) {
            workflow.setStartTime(w.getStartTime());
        }

        if (w.getEndTime() != null && StringUtils.isNotBlank(w.getEndTime())) {
            workflow.setEndTime(w.getEndTime());
        }
        workflows = workflowMapper.getDeviceListPage(workflows, workflow);

        if (workflows.getResult().size() > 0) {
            List<Workflow> wlist = workflows.getResult();
            for (Workflow wf : wlist) {
                this.getAllGroupConcat(wf);
//                setBackDate(wf);
            }
        }
        return workflows;
    }

    /**
     * 管理员查询
     * 
     * @param operator
     * @param workstation
     * @param currentpage
     * @param pageSize
     * @return
     */
    public Page<Workflow> directiveListPage(String operator, String workstation, String startTime, String endTime,
            Integer currentpage, Integer pageSize) {
        Page<Workflow> workflows = new Page<Workflow>();
        workflows.setCurrentPage(currentpage);
        workflows.setSize(pageSize);
        Workflow workflow = new Workflow();
        if ("0".equals(workstation)) {
            workflow.setAccepter(operator); // 受理人员
        } else if ("1".equals(workstation)) {
            workflow.setW_offer(operator); // 报价人员
        } else if ("2".equals(workstation)) {
            workflow.setW_engineer(operator); // 维修人员
        } else if ("3".equals(workstation)) {
            workflow.setW_FinChecker(operator); // 终检人员
        } else if ("4".equals(workstation)) {
            workflow.setW_packer(operator); // 装箱人员
        }
        workflow.setStartTime(startTime); // 开始日期
        workflow.setEndTime(endTime); // 结束日期
        workflows = workflowMapper.getDirectiveListPage(workflows, workflow);
        if (workflows.getResult().size() > 0) {
            List<Workflow> wlist = workflows.getResult();
            for (Workflow wf : wlist) {
                this.getAllGroupConcat(wf);
            }
        }
        return workflows;
    }

    /**
     * 放弃报价设置当前设备维修报价为0
     * 
     * @author TangYuping
     * @version 2016年12月9日 上午9:51:09
     * @param ids
     */
    @Transactional
    public void updateSumPrice(String onepriceMark, String... ids) {
        List<Workflow> wList = workflowMapper.getWorkflowByIds(ids);
        if (wList != null && wList.size() > 0) {
            for (Workflow wf : wList) {
                if (wf != null && wf.getId() > 0) {
                    WorkflowPrice wp = workflowPriceMapper.getByWfid(wf.getId());
                    BigDecimal subPrice = BigDecimal.ZERO;
                    if (wp != null && wp.getId() > 0) {
                        subPrice = wp.getRepairMoney();
                        wp.setRepairMoney(BigDecimal.ZERO);
                        wp.setOnePriceRemark(onepriceMark);
                        workflowPriceMapper.update(wp);
                    }
                    WorkflowRepair wr = workflowRepairMapper.getRepairByWfId(wf.getId());
                    if (wr != null && wr.getId() > 0) {
                        wr.setSumPrice(BigDecimal.ZERO);
                        workflowRepairMapper.update(wr);
                    }
                    // total表 总费用减去相应值
                    if (wr.getGiveUpRepairStatus().equals("0")) {
                        WorkflowTotalpay total = workflowTotalpayMapper.getByRepairNumber(wf.getRepairnNmber());
                        if (total != null) {
                            total.setTotalMoney(total.getTotalMoney().subtract(subPrice));
                            total.setRepairMoney(total.getRepairMoney().subtract(subPrice));
                            workflowTotalpayMapper.update(total);
                        }
                    }
                    workflowMapper.update(wf);
                }
            }
        }
    }

    /**
     * 受理发送分拣
     * 
     * @author TangYuping
     * @version 2017年1月23日 上午9:36:47
     * @param ids
     */
    @Transactional
    public void sendSort(String[] ids) {
        for (String id : ids) {
            // 如果为17-已受理，已测试，则发送到分拣的状态为18-已测试，待分拣
            Workflow workflow = workflowMapper.getById(Integer.valueOf(id));
            if (workflow.getState() == 17) {
                this.updateState(18L, id);
            } else {
                this.updateState(1L, id);
            }
            Workflow wf = this.getT(Integer.valueOf(id));
            if (wf != null) {
                wf.setSortTime(new Timestamp(System.currentTimeMillis()));
                wf.setSendStatus("1");
                workflowMapper.update(wf);
            }
        }
        tipService.addTip(TipState.TIP_FJ, ids.length);
    }

    /**
     * 根据IMEI获取待装箱设备
     * 
     * @author TangYuping
     * @version 2017年3月6日 上午10:50:32
     * @param wf
     * @return
     */
    public Workflow getWorkflowByImeiInPack(Workflow wf) {
        if (null != wf && ((null != wf.getImei() && !"".equals(wf.getImei()))
                || (null != wf.getLockId() && !"".equals(wf.getLockId()))
                || (null != wf.getLockInfo() && !"".equals(wf.getLockInfo())))) {
            Workflow workflow = workflowMapper.getInfoInPackByImei(wf);
            if (workflow != null && workflow.getId() > 0) {
                if (workflow.getMachinaInPack() != null && "Y".equals(workflow.getMachinaInPack())) {
                    return workflow;
                } else {
                    workflow.setMachinaInPack("Y");
                    workflowMapper.update(workflow);
                    return workflow;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    public void ExportDatas(Workflow wk, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fashion = req.getParameter("fashion");
        String crucial = req.getParameter("crucial");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        if (null != crucial && !"".equals(crucial)) {
//            crucial = new String(crucial.getBytes("iso8859-1"), "utf-8");
            if ("0".equals(fashion)) {
                wk.setW_cusName(crucial); // 送修单位
            } else if ("1".equals(fashion)) {
                wk.setRepairnNmber(crucial); // 送修批号
            } else if ("2".equals(fashion)) {
                wk.setW_phone(crucial); // 手机号
            } else if ("3".equals(fashion)) {
                List<String> imeis = Arrays.asList(crucial.split(","));
                wk.setImeis(imeis);// IMEI
            } else if ("4".equals(fashion)) {
                wk.setCustomerStatus("un_normal"); // 无名件
                wk.setCusName(crucial);
            } else if ("5".equals(fashion)) {
                wk.setW_zzgzDesc(crucial); // 最终故障
            } else if ("6".equals(fashion)) {
                wk.setLockId(crucial); // 智能锁ID
            } else if ("7".equals(fashion)) {
                wk.setW_expressNO(crucial); // 快递单号
            } else if ("8".equals(fashion)) {
                wk.setW_model(crucial); // 主板型号
            }
            wk.setStartTime(startTime); // 开始日期
            wk.setEndTime(endTime); // 结束日期
        }
        List<Workflow> wfList = workflowMapper.getDeviceList(wk);
        if (null != wfList && wfList.size() > 0) {
            for (Workflow wf : wfList) {
                wf.setW_sjfj(wf.getW_sjfjDesc());
                wf.setW_cjgzId(wf.getW_cjgzDesc());
                wf.setW_zzgzId(wf.getW_zzgzDesc());
                wf.setW_wxbjId(wf.getW_wxbjDesc());
                wf.setW_matId(wf.getW_matDesc());
                wf.setW_dzlId(wf.getW_dzlDesc());
                this.getAllGroupConcat(wf);
            }
        }
        String[] fieldNames = new String[] { "客户名称", "手机号", "送修批号", "IMEI", "智能锁ID", "状态", "市场型号", "主板型号", "内部机型",
                "保内|保外", "是否人为", "受理时间", "出货日期", "软件版本号", "送修备注", "初检故障", "最终故障", "处理措施", "快递单号", "快递公司", "返修次数",
                "受理备注", "维修员", "受理人", "客户寄货方式", "无名件", "联系人", "通信地址", "邮箱", "维修报价", "维修备注", "型号类别", "批次备注", "上次维修人员",
                "上次受理日期", "是否已支付物流费", "寄来无名件的快递公司", "寄来无名件的快递单号", "超三天责任人", "超三天备注", "分拣时间", "随机附件", "组件料", "电子料",
                "内部二次返修次数", "维修报价说明", "放弃报价", "处理措施2", "放弃维修状态", "发送报价时间", "报价人", "单台报价备注", "客户收货方式", "批次报价备注", "维修费",
                "付款原因", "付款方式", "付款时间", "是否付款", "是否合格", "送修单位备注", "终检备注", "终检人", "装箱时间", "发送装箱时间", "装箱单号", "装箱人", "发货方",
                "装箱备注", "测试员", "测试数据来源站", "发送测试时间", "测试结果", "测试时间" };
        Map map = new HashMap();
        map.put("size", wfList.size() + 2);
        map.put("peList", wfList);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("设备维修单").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(req.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.WORKFLOW).append("allInfo.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(req, resp, exportFile, fileName, template, map);
    }

    public List<Workflow> getAllNotPackInfo() {
        List<Workflow> workflows = workflowMapper.getAllNotPackInfo();
        return workflows;
    }

    /**
     * @Title: getDataList
     * @Description:查询售后系统维修的数据
     * @param workflow
     * @return
     * @author HuangGangQiang
     * @date 2017年7月26日 下午2:15:05
     */
    public List<Workflow> getDataList(Workflow workflow) {
        return workflowMapper.getDataList(workflow);
    }

    /**
     * @Title: updateRepairData
     * @Description:修改售后系统维修的数据
     * @param w
     * @param state
     * @return
     * @author HuangGangQiang
     * @date 2017年7月26日 下午2:14:32
     */
    @Transactional
    public int updateRepairData(List<Workflow> wList, String state) {
        Workflow w = wList.get(0);
        if (null != w.getState() && null != w.getId() && null != w.getRepairnNmber()
                && !"".equals(w.getRepairnNmber())) {
            if (Integer.valueOf(state) == 2) {// 改成已分拣，待维修
                if (8 == w.getState() || 11 == w.getState()) {// 放弃报价，待装箱或放弃报价，待维修
                    if (8 == w.getState()) {
                        // 查询是否已装箱 修改成已分拣，待维修或待报价状态，
                        String idsTemp = workflowMapper.getIdInPack(w.getRepairnNmber());
                        if (null != idsTemp && !"".equals(idsTemp)) {
                            String[] ids = idsTemp.split(",");
                            if (null != ids && ids.length > 0) {
                                // 不存在直接删除
                                if (ids.length == 1) {
                                    workflowMapper.deleteInfoForPack(w.getRepairnNmber());
                                }
                            }
                        }
                    }
                    // 删除price数据
                    workflowMapper.deletePriceForRepair(w.getId());
                } else if (10 == w.getState() || 5 == w.getState() || 6 == w.getState()) {// 不报价，待维修|已维修，待终检|已终检，待维修（必须为未报价数据）
                    // 查询price表里是否有wfid的数据，没有才能修改
                    int ret = workflowMapper.getPriceCountForRepair(w.getId());
                    if (ret > 0) {// 查询结果大于0
                        return -100;// 已报过价数据不允许修改
                    } else {// 查询是否终检
                        int ret1 = workflowMapper.getFicheckCountForRepair(w.getId());
                        if (ret1 > 0) {
                            workflowMapper.deleteFicheckForRepair(w.getId());
                        }
                    }
                } else {
                    return -1;// 要修改的数据不符合
                }
                // 公共部分
                // 删除t_sale_related_zzgz表里rfiId为wfid的数据
                workflowMapper.deleteInfoForRelated(w.getId());
                // repair表数据改成刚分拣的状态
                workflowMapper.updateRepairForRepair(w.getId());
                // related表的报价及物料数据清除
                workflowMapper.updateRelatedForRepair(w.getId());
                // workflow表的状态改成2，发送装箱时间置空
                workflowMapper.updateInfoForWorkflow(2, w.getId());

            } else if (Integer.valueOf(state) == 3) {// 改成待报价
                if (8 == w.getState() || 11 == w.getState()) {// 放弃报价，待装箱或放弃报价，待维修
                    if (8 == w.getState()) {
                        // 查询是否已装箱 修改成已分拣，待维修或待报价状态，
                        String idsTemp = workflowMapper.getIdInPack(w.getRepairnNmber());
                        if (null != idsTemp && !"".equals(idsTemp)) {
                            String[] ids = idsTemp.split(",");
                            if (null != ids && ids.length > 0) {
                                // 不存在直接删除
                                if (ids.length == 1) {
                                    workflowMapper.deleteInfoForPack(w.getRepairnNmber());
                                }
                            }
                        }
                    }
                    // repair表状态改为-1，维修总金额 = 维修费+工时费，放弃报价时间等修改为未放弃
                    workflowMapper.updateRepairForPrice(w.getId());
                    // price数据状态0，付款状态，维修金额=维修报价+ 工时费
                    workflowMapper.updatePriceForPrice(w.getId());
                    // 删除t_sale_related_zzgz表里rfiId为wfid的数据
                    workflowMapper.deleteInfoForRelated(w.getId());
                    // related表的最终故障数据清除
                    workflowMapper.updateRelatedForPrice(w.getId());
                    // workflow表的状态改成3
                    workflowMapper.updateInfoForWorkflow(3, w.getId());
                } else {
                    return -1;// 要修改的数据不符合
                }
            } else {
                return -200;// 修改的状态传值错误
            }
            return 0;
        } else {
            return -500;// 获取数据错误
        }
    }

    /**
     * @Title: getCountToTestByIds
     * @Description:受理工站查询选中的数据是否有不是已受理？有：不允许发送测试,分拣
     * @param ids
     * @return
     * @author 20160308
     * @date 2017年11月10日 下午2:15:18
     */
    public int getCountToTestByIds(String... ids) {
        return workflowMapper.getCountToTestByIds(ids);
    }

    /**
     * @Title: sendWorkTest
     * @Description:发送到分拣
     * @param dataSource
     * @param wt
     * @param ids
     * @author 20160308
     * @date 2017年11月10日 下午2:30:38
     */
    @Transactional
    public void sendWorkTest(String dataSource, WorkflowTest wt, String... ids) {
        if (ids != null && ids.length > 0) {// 测试
            for (String id : ids) {
                WorkflowTest workflowTest = workflowTestMapper.getWorkflowTestByWfId(id);
                if (workflowTest != null) {
                    workflowTest.setDataSource(wt.getDataSource());
                    workflowTest.setTestStatus(Integer.valueOf(wt.getTestStatus()));
                    workflowTest.setTestPerson(wt.getTestPerson());
                    workflowTest.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    workflowTestMapper.update(workflowTest);
                } else {
                    workflowTest = new WorkflowTest();
                    workflowTest.setDataSource(wt.getDataSource());
                    workflowTest.setTestStatus(Integer.valueOf(wt.getTestStatus()));
                    workflowTest.setWfId(Integer.valueOf(id));
                    workflowTest.setTestPerson(wt.getTestPerson());
                    workflowTest.setSendTime(new Timestamp(System.currentTimeMillis()));
                    workflowTestMapper.insert(workflowTest);
                }
            }
        }

        this.updateState(15L, ids); // 测试中
        if ("受理站".equals(dataSource)) {

        } else if ("维修站".equals(dataSource)) {
            workflowRepairMapper.updateState(10L, ids);// 10-不报价，测试中（原为8-测试中）
            this.updateState(19L, ids); // 不报价，测试中
        } else if ("终检站".equals(dataSource)) {
            workflowFicheckMapper.updateStateByWfid(3, ids);// TODO 终检表表 处于测试中状态
        }
        tipService.addTip(TipState.TIP_TEST, ids.length);
    }

    /**
     * @Title: getRepairNumberCountPriced
     * @Description:该批次是否已报价或已付款，提示受理是否要更新批次号
     * @param repairnNmber
     * @return
     * @author 20160308
     * @date 2017年11月21日 上午9:32:38
     */
    public int getRepairNumberCountPriced(String repairnNmber) {
        return workflowMapper.getRepairNumberCountPriced(repairnNmber);
    }

    /**
     * @Title: getRepairNumberCountPriced
     * @Description:插入临时数据
     * @param repairnNmber
     * @return
     * @author 马文军
     * @date 2017年11月21日 上午9:32:38
     */
    public void insertWorkflowInfo(WorkflowTemp workflowTemp) {
        workflowMapper.insertWorkflowInfo(workflowTemp);
    }

    /**
     * @Title: getRepairNumberCountPriced
     * @Description:查询临时数据
     * @param repairnNmber
     * @return
     * @author 马文军
     * @date 2017年11月21日 上午9:32:38
     */
    public List<WorkflowTemp> getWorkflowInfo(String getuId) {
        return workflowMapper.getWorkflowInfo(getuId);
    }

    /**
     * @Title: getRepairNumberCountPriced
     * @Description:更新临时数据
     * @param repairnNmber
     * @return
     * @author 马文军
     * @date 2017年11月21日 上午9:32:38
     */
    public void updateWorkflowInfo(WorkflowTemp workflow) {
        workflowMapper.updateWorkflowInfo(workflow);

    }

    /**
     * @Title: getRepairNumberCountPriced
     * @Description:根据IMEI查询临时数据
     * @param repairnNmber
     * @return
     * @author 马文军
     * @date 2017年11月21日 上午9:32:38
     */
    public WorkflowTemp getWorkflowInfoByImei(String imei) {
        return workflowMapper.getWorkflowInfoByImei(imei);
    }

    /**
     * @Title: getRepairNumberCountPriced
     * @Description:根据IMEI查询临时数据
     * @param repairnNmber
     * @return
     * @author 马文军
     * @date 2017年11月21日 上午9:32:38
     */
    public void deleteWorkflowInfoByUserId(String uId) {
        workflowMapper.deleteWorkflowInfoByUserId(uId);
    }

    public Page<WarrantyInfo> getWarrantyInfo(WarrantyInfo warranty) {
        if (StringUtils.isNotBlank(warranty.getImeis())) {
            String[] imeiArr = warranty.getImeis().split(",");
            warranty.setImeiList(Arrays.asList(imeiArr));
        }

        Page<WarrantyInfo> page = new Page<WarrantyInfo>();
        page.setCurrentPage(warranty.getCurrentpage());
        page.setSize(warranty.getPageSize());
        page = workflowMapper.getWarrantyInfo(page, warranty);
        return page;
    }

    @SuppressWarnings("deprecation")
    public void exportWarrantyData(WarrantyInfo warranty, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.isNotBlank(warranty.getImeis())) {
            String[] imeiArr = warranty.getImeis().split(",");
            warranty.setImeiList(Arrays.asList(imeiArr));
        }
        List<WarrantyInfo> list = workflowMapper.listWarrantyInfo(warranty);

        String[] fieldNames = new String[] { "保修期限", "IMEI", "设备型号", "主板软件版本号", "订单号 ", "公司名称", "创建人", "创建时间" };
        Map<String, Object> map = new HashMap<>();
        map.put("size", list.size() + 2);
        map.put("peList", list);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("保修期限列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.WORKFLOW).append("warrantyInfo.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

    public void exportNotSendInfo(Workflow workflow, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取装箱条件
        Integer isPacked = workflow.getIsPacked();
        //默认查全部
        if (isPacked == null)
            workflow.setIsPacked(2);

        //获取工站条件
        Integer workStation = workflow.getWorkStation();
        //默认查全部
        if (workStation == null)
            workflow.setWorkStation(0);

        List<Workflow> list = workflowMapper.listNotSendInfo(workflow);

        for (Workflow wf : list) {
            this.getAllGroupConcat(wf);
//                setBackDate(wf);
        }

        String[] fieldNames = new String[] { "受理时间", "送修批号", "客户名称", "IMEI", "工站","进度 ","主板型号",
                "市场型号", "维修员","已至装箱", "发送终检时间", "客户付款日期", "发送报价日期 ","随机附件",
                "返修次数", "保内|保外 ", "初检故障","最终故障","处理方法 ","智能锁ID","是否人为 ","维修报价(￥)",
                "报价描述","测试人员 ","测试结果","送修备注","受理备注 ","批次备注",};
        Map<String, Object> map = new HashMap<>();
        map.put("size", list.size() + 2);
        map.put("peList", list);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("未寄出设备列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.WORKFLOW).append("notSend.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

    /**
     * 设置指定的设备的保修期
     * @param warrantyInfo 包括设备的imei（多个）和保修期限
     * @throws Exception
     */
    @Transactional
    public void setWarrantyInfo(WarrantyInfo warrantyInfo) throws Exception {
        //从AMS获取imei对应设备的一些信息
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "x-www-form-urlencoded");

        Map<String, String> params = new HashMap<String, String>();
        params.put("imeiStr", warrantyInfo.getImeis());

        Result result = SendRequest.sendGet(AMS_DEVICE_URL, headers, params, "utf-8");
        String jsonStr = result.getHtml(result, "utf-8");

        JSONObject json = JSONObject.parseObject(jsonStr);
        List<DeviceInfo> deviceList = JSONArray.parseArray(json.getString("data"), DeviceInfo.class);

        List<WarrantyInfo> WarrantyList = new ArrayList<>();
        //根据设备创建WarrantyInfo对象，设置保修期
        for (DeviceInfo device : deviceList) {
            WarrantyInfo warranty = new WarrantyInfo(device.getImei());
            warranty.setMcType(device.getMcType());
            warranty.setBill(device.getBill());
            warranty.setCpName(device.getCpName());
            warranty.setSfVersion(device.getSfVersion());
            //设置保修期
            warranty.setProlongMonth(warrantyInfo.getProlongMonth());
            warranty.setCreateBy(warrantyInfo.getCreateBy());

            WarrantyList.add(warranty);

        }
        //插入保修期数据到数据库
        workflowMapper.insertWarrantyInfo(WarrantyList);

        List<String> inImeis = new ArrayList<>();
        List<String> outImeis = new ArrayList<>();
        for (DeviceInfo device : deviceList) {
            //根据imei得到设备是否在保修期内（0表示是，1表示否），如果返回null则说明没有该设备的保修期信息
            Integer isWarranty = getIsWarranty(device.getImei(), device.getOutDate());
            if (isWarranty == 0) {
                inImeis.add(device.getImei());
            }
            if (isWarranty == 1) {
                outImeis.add(device.getImei());
            }
        }
        // 保内
        if (inImeis.size() > 0) {
            workflowMapper.updateInWarranty(inImeis);
        }
        // 保外
        if (outImeis.size() > 0) {
            workflowMapper.updateOutWarranty(outImeis);
        }
    }

    public List<DeviceInfo> getDeviceInfo(DeviceInfo deviceInfo, HttpServletRequest request) throws Exception {

        JSONObject json = querySaleData(deviceInfo);
        List<DeviceInfo> list = JSONArray.parseArray(json.getString("data"), DeviceInfo.class);

        int totalValue = (int) JSONObject.parse(json.getString("count"));
        request.getSession().setAttribute("totalValue", totalValue);

        return list;
    }

    public String sdf8ToSdf14(String source) throws ParseException {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        SimpleDateFormat sdf14 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyy-MM-dd");

        Date date = sdf8.parse(source);
        String newSource = sdf14.format(date);
        newSource = newSource.replaceAll(" ", "%20");

        return newSource;
    }

    public Integer getProlongMonth(String imei) {
        Integer prolongMonth = null;
        if (StringUtils.isNotEmpty(imei)) {
            prolongMonth = workflowMapper.getProlongMonthByImei(imei);
        }
        return prolongMonth;
    }

    public void deleteWarrantyInfo(String imeiArr) throws Exception {
        String[] imeiStr = imeiArr.split(",");
        List<String> imeis = Arrays.asList(imeiStr);
        workflowMapper.deleteWarrantyInfo(imeis);
    }

    public List<String> isExistWarrantyInfo(WarrantyInfo warrantyInfo) {
        String[] imeiStr = warrantyInfo.getImeis().split(",");
        List<String> list = Arrays.asList(imeiStr);

        List<String> existList = workflowMapper.repeatWarrantyImei(list);
        return existList;
    }

    public List<String> listCpName() {
        return workflowMapper.listCpName();
    }

    public Integer getIsWarranty(String imei, Date outDate) throws ParseException {
        Integer month = getProlongMonth(imei);
        if (month == null || outDate == null) {
            return null;
        }
        if (month == 0) {
            return 1;// 保外
        }

        long saleTime = outDate.getTime();
        long nowTime = new Date().getTime();
        long pastTime = nowTime - saleTime;

        long prolongTime = month * 31L * 24L * 60L * 60L * 1000L;

        if (pastTime > prolongTime) {
            return 1;// 保外
        }
        return 0;// 保内
    }

    public JSONObject querySaleData(DeviceInfo deviceInfo) throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "x-www-form-urlencoded");

        Map<String, String> params = new HashMap<String, String>();
        if (StringUtils.isNotBlank(deviceInfo.getImeis())) {
            params.put("imeiStr", deviceInfo.getImeis());
        }
        if (StringUtils.isNotBlank(deviceInfo.getMcType())) {
            params.put("mcType", deviceInfo.getMcType());
        }
        if (StringUtils.isNotBlank(deviceInfo.getBill())) {
            params.put("bill", deviceInfo.getBill());
        }
        if (StringUtils.isNotBlank(deviceInfo.getSfVersion())) {
            params.put("sfVersion", deviceInfo.getSfVersion());
        }
        if (StringUtils.isNotBlank(deviceInfo.getCpName())) {
            params.put("cpName", deviceInfo.getCpName());
        }
        if (StringUtils.isNotBlank(String.valueOf(deviceInfo.getCurrentpage()))) {
            params.put("currentPage", String.valueOf(deviceInfo.getCurrentpage()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getOutDateStart())) {
            params.put("outStartDateStr", sdf8ToSdf14(deviceInfo.getOutDateStart()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getOutDateEnd())) {
            params.put("outEndDateStr", sdf8ToSdf14(deviceInfo.getOutDateEnd()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getProductionDateStart())) {
            params.put("productionStartDateStr", sdf8ToSdf14(deviceInfo.getProductionDateStart()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getProductionDateEnd())) {
            params.put("productionEndDateStr", sdf8ToSdf14(deviceInfo.getProductionDateEnd()));
        }

        Result result = SendRequest.sendGet(AMS_DEVICE_URL, headers, params, "utf-8");
        String jsonStr = result.getHtml(result, "utf-8");

        JSONObject json = JSONObject.parseObject(jsonStr);

        return json;
    }
    
    public void updateOutdateByImei(String imei,Date outdate){
    	workflowMapper.updateOutdateByImei(imei,outdate);
    }

    /**
     * 根据imei和送修批号更改设置的客户信息
     * @param workflow
     * @param sxdwmanage
     */
    public void updateCustomer(Workflow workflow,Sxdwmanage sxdwmanage){
        workflowMapper.updateCustomer(workflow.getImei(),workflow.getRepairnNmber(),sxdwmanage.getCId(),sxdwmanage.getCusName());
    }

    /**
     * 根据imei获取设置的保修期信息
     * @return
     */
    public List<WarrantyInfo> listWarrantyInfo(WarrantyInfo warrantyInfo){
        return  workflowMapper.listWarrantyInfo(warrantyInfo);
    }

    /**
     * 根据条件查询设备信息。条件包括受理事件、工站、维修人员、送修批号、客户名称。
     * @param workflow
     * @param page
     * @return
     */
    @Transactional
    public Page<Workflow>   getNotSendListPage(Workflow workflow,Page<Workflow> page){
        workflowMapper.selectNotSendListPage(page,workflow);
        //处理方法查询
        List<Zgzmanage> list = zgzmanageMapper.queryList(new Zgzmanage());
        Map<String, Object> map = new HashMap<>();
        for(Zgzmanage z:list) {
            map.put(String.valueOf(z.getId()),z);
        }
        for(Workflow w:page.getResult()){
            getAllGroupConcat(w);
        }
        return page;
    }
/*
    public void  exportNotSendList(Workflow workflow,HttpServletRequest request,HttpServletResponse response)
        throws IOException{
        List<Workflow> workflows = workflowMapper.selectNotSendList(workflow);
        //处理方法查询
        List<Zgzmanage> list = zgzmanageMapper.queryList(new Zgzmanage());
        Map<String, Object> data = new HashMap<>();
        for(Zgzmanage z:list) {
            data.put(String.valueOf(z.getId()),z);
        }
        for(Workflow w:workflows){
            getAllGroupConcat(w);
        }

        String[] fieldNames = new String[] { "受理时间", "送修批号", "客户名称", "IMEI", "工站",
                "进度", "主板型号", "市场型号", "维修员", "已至装箱", "发送终检时间", "客户付款日期",
                "发送报价日期", "随机附件", "返修次数", "保内|保外", "初检故障", "最终故障","处理方法",
                "智能锁ID","是否人为","维修报价","报价描述","测试人员","测试结果","送修备注","受理备注",
                "批次备注"};
        Map map = new HashMap();
        map.put("size", workflows.size() + 2);
        map.put("peList", workflows);
        map.put("fieldNames", fieldNames);
        map.put("cosLenth", fieldNames.length);
        String fileName = new StringBuilder("未寄出设备报表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
        String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName)
                .toString();
        String templatePath = new StringBuilder(GlobalCons.WORKFLOW).append("workPrice.ftl").toString();
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        FreemarkerManager.down(request, response, exportFile, fileName, template, map);
    }

 */

    /**
     *
     * @param file 要导入的数据
     * @param month 保修期
     * @param userInfo 导入人
     */
    public int importWarranty (MultipartFile file, Integer month, UserInfo userInfo)throws Exception{
        InputStream in = null;
        try{
            in = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(in);
            int numSheet = workbook.getNumberOfSheets();
            if(numSheet <= 0){
                return 0;
            }
            List<WarrantyInfo> list = new ArrayList<>();
            //遍历每个sheet
            for(int i=0;i<numSheet;i++){
                Sheet sheet = workbook.getSheetAt(i);
                int numRow = sheet.getLastRowNum();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(numRow);
                //遍历每一行
                for(int j=1;j<=numRow;j++){
                    Row row = sheet.getRow(j);
                    WarrantyInfo war = new WarrantyInfo();
                    war.setImei(row.getCell(0).getStringCellValue());
                    war.setSfVersion(row.getCell(2).getStringCellValue());
                    war.setBill(row.getCell(5).getStringCellValue());
                    war.setCpName(row.getCell(10).getStringCellValue());
                    war.setMcType(row.getCell(7).getStringCellValue());
                    war.setProlongMonth(month);
                    war.setCreateTime(sdf.format(new Date()));
                    war.setCreateBy(userInfo.getuName());

//                System.out.println(war.getImei()+","+war.getSfVersion()+","+
//                        war.getBill()+","+war.getCpName()+","+war.getMcType()+
//                        ","+war.getProlongMonth()+","+war.getCreateTime());
                    if(war.getImei()!=null && war.getImei().trim()!="" && !workflowMapper.existsWarrantyByImei(war.getImei())){
                        list.add(war);
                    }
                }
            }
            logger.info("导入"+list.size()+"行保修期数据");
            if(list.size() > 0) {
                doImport(list);
            }
            return list.size();
        }finally {
            if(in!=null){
                in.close();
            }
        }
    }

    /**
     * 插入数据到数据库
     * @param list 返回未插入数据
     * @return
     */
    @Transactional
    public void doImport(List<WarrantyInfo> list){
        for(WarrantyInfo war:list){
                workflowMapper.insertWarrantyInfo(list);
        }
    }

    /**
     * 超三天未寄出应返还时间
     * 应返还时间规则，超过应返回时间则算超期
     *  受理        终检时间
     * 周一上午     周三下午
     * 周一下午     周四下午
     * 周二上午     周四下午
     * 周二下午     周五下午
     * 周三上午	   周六上午
     * 周三下午     周一下午
     * 周四上午     周一下午
     * 周四下午     周一下午
     * 周五上午     周二下午
     * 周五下午     周二下午
     * 周六上午     周三下午
     *
     * 筛选超时未寄出数据
     * 前提：
     * 1.放假形式为连续的下午+N天，或者来连续N天，不会出现其他的放假情况
     * 2.一次放假后距离下一次放假至少三天。
     * 3.非工作时间不受理维修设备
     * 4.超期时间超过15天可能不正确
     * 否则以下规则会出问题。
     * 半天假前的第三个工作日按周三处理，第二个工作日按周四处理，第一个中昨日按周五处理，放假当天按周六处理，
     * 寄出时间按照规则顺推，多放一天假就多往后推一天
     * 例如：
     * 周一、二、三工作、周四上午工作，周四下午和周五周六放假，周日工作、下周的周一工作…
     * 那么这周的周一按正常的周三处理，周二按周四处理、周三按周五处理，周四按周六处理。
     * @param date
     **/
    public Date gainBackTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //Date backTime = shouldBackTime(dayDiff,acceptTimeSlot,holiday,acceptTime.getTime());
        //获取受理时间
        int dateSlot = calendar.get(Calendar.HOUR_OF_DAY) >=12 ? 1:0;
        //获取最近的放假时间（要求该放假时间必须在受理时间后面）。
        Holiday holiday = getLatestHoliday(date,1);
        //如果holiday与受理时间是同一天，则表示在半天假的12点以后也受理了。直接当作上午受理
        //按道理不会出现这种情况，因为售后说了放假期间不受理
        if(DateUtils.isSameDay(holiday.getFrom().getNormalDate(),calendar.getTime())){
            dateSlot = 0;
        }
        //获取date与假期头一天相隔时间
        int dayDiff = getDayDiff(date,holiday.getFrom().getNormalDate());
//        System.out.println("acceptTime"+calendar.toString()+"   dayDiff："+dayDiff);

        //计算应返还时间
        Date backTime = shouldBackTime(dayDiff,dateSlot,holiday,date);
        logger.debug(date+"与假期第一天的间隔时间："+dayDiff+"，应返还时间："+backTime);
        return backTime;
    }
    /**
     * 根据假期和受理时间距假期开始时间天数计算应返还日期
     * @param dayDiff
     * @param holiday
     * @param slot 受理是上午还是下午
     * @return
     */
    public Date shouldBackTime(int dayDiff,int slot,Holiday holiday,Date acceptTime){
        //假期最后一天
        Date to = holiday.getTo().getNormalDate();
        //头一天的假期是
        Integer workType = holiday.getFrom().getWorkType();
        //假期开始当天是整天放假，那就所有的都当作假期前四天、前五天处理，返回时间需要顺延假期时间数
        if(workType == 0){
            //假期天数
            int holidayLong = getDayDiff(holiday.getFrom().getNormalDate(), holiday.getTo().getNormalDate())+1;
            if(slot == 0) {
                //上午受理
                return DateUtils.addDays(acceptTime,2+holidayLong);
            }else{
                //下午受理
                return DateUtils.addDays(acceptTime,3+holidayLong);
            }
        }

        //假期开始当天是放半天假
        switch (dayDiff){
            //假期当天，按周六处理，假期结束时间加三天
            case 0:return DateUtils.addDays(to,3);
            //假期前一天，按周五处理
            case 1:return DateUtils.addDays(to,2);
            //假期前2天，按周四处理
            case 2: return DateUtils.addDays(to,1);
            //假期前3天，按周三处理
            case 3:
                if(slot == 0)
                    return holiday.getFrom().getNormalDate();
                else
                    return DateUtils.addDays(to,1);
                //假期前四天，前五天...等按照周一，周二...处理，上午受理的往后推两天，下午受理的往后推三天
            default:
                if(slot == 0) {
                    //上午受理
                    return DateUtils.addDays(acceptTime,2);
                }else{
                    //下午受理
                    return DateUtils.addDays(acceptTime,3);
                }
        }
    }

    /**
     * 返回两个日期间的天数差，同一天返回0.
     * d1在d2之前返回正值，d1在d2之后返回负值
     * @param d1
     * @param d2
     */
    public int getDayDiff(Date d1,Date d2){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(d1)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(d2)).getTime();
            return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
        } catch (java.text.ParseException e) {
            logger.error("日期转换异常"+e.toString());
        }
        return 0;
    }

    /**
     * 获取离Date最近的放假区间
     *
     */
    public Holiday getLatestHoliday(Date date,int type){
        //获取后二十天的日期
        Date to = DateUtils.addDays(date,20);
        //从date到后二十天内的假期
        List<WorkDate> holidayBetween = workDateService.getHolidayBetween(date, to, 100, type);
        List<Holiday> holidays = megerToHoliday(holidayBetween);
        return holidays.get(0);
    }
    /**
     * 将假日合并为一个个的区间。
     * 例如，2020-10-01下午到2020-10-03放假，那么就转换为一个区间（Holiday），from是2020-10-01，To是2020-10-03
     * @param list
     * @return 多个假期区间
     */
    public List<Holiday> megerToHoliday(List<WorkDate> list){
        Collections.sort(list, new Comparator<WorkDate>() {
            @Override
            public int compare(WorkDate o1, WorkDate o2) {
                return o1.getNormalDate().compareTo(o2.getNormalDate());
            }
        });
        List<Holiday> holidays = new ArrayList<>();
        if(list.size() == 1){
            holidays.add(new Holiday(list.get(0),list.get(0)));
            return holidays;
        }

        for(int i=0;i<list.size()-1;){
            Holiday holiday  = new Holiday();
            holiday.setFrom(list.get(i));
            //获取i的下一天
            Date nextI = DateUtils.addDays(list.get(i).getNormalDate(),1);
            for(int j=i+1;j<list.size();j++){
                WorkDate workDateJ = list.get(j);
                //判断i，j两天是否连续，如果不连续，则表示j是一个新区间的开始。j-1则是上一个区间的结束
                //或者一直连续，但是j到了最后一个。由于假期寻找到了最大受理时间的20天后，这里不判断也没问题
                if(!DateUtils.isSameDay(nextI,workDateJ.getNormalDate()) ||
                        (j==list.size()-1 && DateUtils.isSameDay(nextI,workDateJ.getNormalDate()))){
                    holiday.setTo(list.get(j-1));
                    i = j;
                    break;
                }
            }
            holidays.add(holiday);
        }
        return holidays;
    }

    /**
     * 获取以date时间计算超期了，但是超期天数未锁定的记录
     * 即date的日期大于应返还日期且未发送到装箱工站
     * 数据库中使用的是datetime存储应返还时间，但是这里判断时只以日期为判断。
     *
     * @return
     */
    public List<Workflow> getTimeoutAndNotLockData(Date date){
        Date tmp = DateUtil.initDate(date);
        List<Workflow> workflows = workflowMapper.selectTimeoutAndNotLockData(tmp);
        return  workflows;
    }

    /**
     * 根据imei和送修批号修改超期天数
     * @param imei
     * @param repairNumber
     * @param timeoutDays
     */
    public void modifyTimeoutDay(String imei,String repairNumber,int timeoutDays){
        workflowMapper.updateTimeoutDay(imei,repairNumber,timeoutDays);
    }

    public void modifyTimeoutDayById(Integer id,Integer timeoutDays){
        workflowMapper.updateTimeoutDayById(id,timeoutDays);
    }

    /**
     * 根据IMEI和送修批号修改超期原因
     */
    public void modifyTimeoutReason(String imei,String repairNumber,String timeoutReason){
        workflowMapper.updateTimeoutReason(imei,repairNumber,timeoutReason);
    }
    public void modifyTimeoutReasonById(Integer id,String timeoutReason){
        workflowMapper.updateTimeoutReasonById(id,timeoutReason);
    }

    /**
     * 获取某个客户的受理时间在[form,to)的记录数
     * @param from
     * @param to
     * @return
     */
    public int getCountBetweenAcceptTime(Date from,Date to,Integer sxdwId){
        return workflowMapper.selectCountBetweenAcceptTime(from,to,sxdwId);
    }

    /**
     * 修改超天备注
     * @param id workflow id
     */
    public void modifyTimeoutRemarkById(Integer id,String timeoutRemark){
        workflowMapper.updateTimeoutRemarkById(id,timeoutRemark);
    }

    /**
     * 刷新返还时间。由于数据库添加了应还时间。所以对与遗留数据要更新改字段信息
     */
    @Transactional
    public void flushBackTime(Date start,Date end){
        List<Workflow> workflows = workflowMapper.selectNotHaveBackTimeByTime(start,end);
        for(Workflow workflow:workflows){
            Date acceptTime  = workflow.getAcceptanceTime();
            if(acceptTime != null){
                Date backTime = gainBackTime(acceptTime);
                workflowMapper.updateBackTimeById(workflow.getId(),backTime);
            }
        }
    }
    /*
    public void importWarranty2(MultipartFile file, Integer month, UserInfo userInfo) throws Exception{
        InputStream in = null;
        try {
            in  = file.getInputStream();
            EasyExcel.read(in, new WarrantyListener(workflowMapper,month,userInfo,new Date())).sheet().doRead();
        }finally {
            if(in != null){
                in.close();
            }
        }
    }

     */

    /**
     * EasyExcel的监听类，每读取一行数据都会调用其中的invoke方法。
     */
    /*
    public static class WarrantyListener extends AnalysisEventListener<WarrantyInfo>{
        private static final Logger log = Logger.getLogger(WarrantyListener.class);
        //每多少条存一次数据库
        private static final int BATCH_COUNT = 1000;
        //用来保存接卸到的数据
        List<WarrantyInfo> list = new ArrayList<>();
        //用来存数据库
        private WorkflowMapper workflowMapper;
        private UserInfo userInfo;
        private Integer month;
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private Date date;
        public WarrantyListener(){

        }
        public WarrantyListener(WorkflowMapper workflowMapper,Integer month,UserInfo userInfo,Date date){
            this.workflowMapper = workflowMapper;
            this.month = month;
            this.userInfo = userInfo;
            this.date = date;
        }

        @Override
        public void invoke(WarrantyInfo warrantyInfo, AnalysisContext analysisContext) {
            log.info("解析到数据"+JSON.toJSONString(warrantyInfo));
            warrantyInfo.setCreateBy(userInfo.getuName());
            warrantyInfo.setProlongMonth(month);
            warrantyInfo.setCreateTime(simpleDateFormat.format(date));
            list.add(warrantyInfo);
            //每1000条存入数据库
            if(list.size()>=BATCH_COUNT){
                System.out.println("存入"+list.size()+"条数据");
                workflowMapper.insertWarrantyInfo(list);
                list.clear();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            //最后不足10000的也要存入
            workflowMapper.insertWarrantyInfo(list);
            System.out.println("最后存入数据成功");
        }
    }

     */
}