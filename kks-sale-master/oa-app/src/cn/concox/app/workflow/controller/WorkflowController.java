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
package cn.concox.app.workflow.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.concox.app.scheduler.CountTimeoutDateService;
import cn.concox.comm.util.DateUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.common.utils.CollectionUtils;

import org.springframework.web.multipart.MultipartFile;
import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.mapper.ZbxhmanageMapper;
import cn.concox.app.basicdata.service.LockIdUrlService;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.util.DateTimeUtil;
import cn.concox.app.workflow.service.WorkflowFicheckService;
import cn.concox.app.workflow.service.WorkflowPriceService;
import cn.concox.app.workflow.service.WorkflowRepairService;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.BatchNumUtil;
import cn.concox.comm.util.JavaNetURLRESTFulClient;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.DeviceInfo;
import cn.concox.vo.basicdata.LockIdUrl;
import cn.concox.vo.basicdata.Saledata;
import cn.concox.vo.basicdata.WarrantyInfo;
import cn.concox.vo.basicdata.Zbxhmanage;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowTemp;
import cn.concox.vo.workflow.WorkflowTest;

/**
 * 工作流程 公共控制层
 * 
 * @author Li.Shangzhi
 * @date 2016年7月20日
 */
@Controller
@Scope("prototype")
public class WorkflowController extends BaseController {
    Logger logger = Logger.getLogger("workflowInfo");
    Logger delLogger = Logger.getLogger("deleteWorkflowInfo");

    @Value("${ams_goods_url}")
    private String AMS_GOODS_URL;

    @Value("${ams_sales_url}")
    private String AMS_SALES_URL;

    @Resource(name = "workflowService")
    private WorkflowService workflowService;

    @Resource(name = "zbxhmanageMapper")
    private ZbxhmanageMapper<Zbxhmanage> zbxhmanageMapper;

    @Resource(name = "workflowRepairService")
    private WorkflowRepairService workflowRepairService;

    @Resource(name = "ficheckService")
    private WorkflowFicheckService workflowFicheckService;

    @Resource(name = "lockIdUrlService")
    private LockIdUrlService lockIdUrlService;

    @Resource(name = "workflowPriceService")
    private WorkflowPriceService workflowPriceService;

    @Resource(name = "countTimeoutDateService")
    private CountTimeoutDateService countTimeoutDateService;

    //前端传来日期的转换问题。
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    /**
     * 获取受理数据
     * 
     * @param Workflow
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/acceptList")
    @ResponseBody
    public APIContent getAcceptList(@ModelAttribute Workflow workflow, HttpServletRequest req,
            @ModelAttribute CommonParam comp) {
        try {
            Page<Workflow> wflListPage = workflowService.getAcceptList(workflow, comp.getCurrentpage(),
                    comp.getPageSize());
            req.getSession().setAttribute("totalValue", wflListPage.getTotal());
            List<Workflow> wl = wflListPage.getResult();
            workflowService.setList(wl);
            return super.putAPIData(wl);
        } catch (Exception e) {
            logger.error("获取受理 分页数据失败:" + e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 删除受理
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/deleteInfo")
    @ResponseBody
    public APIContent deleteInfo(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");
                if (null != ids && ids.length > 0) {
                    for (String s : ids) {
                        Workflow w = workflowService.getT(Integer.valueOf(s));
                        if (null != w && null != w.getId() && !StringUtil.isEmpty(w.getImei()))
                            delLogger.error("删除受理数据:(id=" + w.getId() + ", imei=" + w.getImei() + ", repairnNmber="
                                    + w.getRepairnNmber() + ", acceptanceTime=" + w.getAcceptanceTime() + ")" + ",操作人："
                                    + super.getSessionUserName(req));
                    }
                }
                workflowService.deleteInfos(ids);
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("删除受理失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 根据imei删除批量受理
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/deleteByImei")
    @ResponseBody
    public APIContent deleteByImei(HttpServletRequest req) {
        try {
            String imeisemp = req.getParameter("imeis");
            if (null != imeisemp && !StringUtil.isEmpty(imeisemp)) {
                String imeis[] = imeisemp.split(",");
                workflowService.deleteByImei(imeis);
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("删除批量受理失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 发送短信
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/pushMsg")
    @ResponseBody
    public APIContent pushMsg(@ModelAttribute Workflow workflow, HttpServletRequest req) {
        try {
            workflowService.pushMsg(workflow);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("受理发送短信失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取单受理详情
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/getInfo")
    @ResponseBody
    public APIContent getInfo(@ModelAttribute Workflow workflow, HttpServletRequest req) {
        try {
            if (null != workflow && workflow.getId() > 0) {
                workflow = workflowService.getInfo(workflow.getId(), false);
                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, workflow);
            } else {
                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
            }
        } catch (Exception e) {
            logger.error("获取受理数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 存储临时数据
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/copyInfo")
    @ResponseBody
    public APIContent copyInfo(HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);
            String array = (String) req.getParameter("array");
            WorkflowTemp workflow = com.alibaba.fastjson.JSON.parseObject(array, WorkflowTemp.class);
            workflow.setUid(user.getuId());

            workflowService.insertWorkflowInfo(workflow);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("更新受理数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取临时数据
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/getLinshiData")
    @ResponseBody
    public APIContent getLinshiData(HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);

            List<WorkflowTemp> workflowInfo = workflowService.getWorkflowInfo(user.getuId());
            return super.putAPIData(workflowInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取临时数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * @Title: deleteLinshiData
     * @Description:删除临时保存记录
     * @param req
     * @return
     * @author Wang Xirui
     * @date 2019年6月13日 下午3:52:55
     */
    @RequestMapping("workflowcon/deleteLinshiData")
    @ResponseBody
    public APIContent deleteLinshiData(HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);
            workflowService.deleteWorkflowInfoByUserId(user.getuId());

            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("删除临时数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 修改受理信息
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/addOrUpdateInfo")
    @ResponseBody
    public APIContent addOrUpdateInfo(@ModelAttribute Workflow workflow, HttpServletRequest req) {
        try {
            UserInfo user = getSessionUser(req);
            if (null != workflow.getId() && workflow.getId() > 0) {
                workflowService.updateInfo(workflow);
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "最终受理信息修改" + Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                workflow.setAccepter(super.getSessionUserName(req));
                workflow.setState(0); // TODO 未受理状态
                workflow.setSendStatus("0");
                if (!StringUtils.isBlank(workflow.getRepairnNmber()) && !StringUtils.isBlank(workflow.getW_cjgzId())
                        && null != workflow.getXhId() && 0 != workflow.getXhId()) {
                    if (!StringUtils.isBlank(workflow.getCustomerStatus())
                            && "normal".equals(workflow.getCustomerStatus())) {
                        if (workflow.getSxdwId() == null) {
                            logger.error("IMEI为:【" + workflow.getImei() + "】的客户信息录入错误");
                            return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                    "IMEI为:【" + workflow.getImei() + "】的客户信息录入错误，请重新选择客户信息");
                        }
                    }
                    if (workflowService.isExists(workflow) == 0) {
                        workflowService.insertInfo(workflow);
                        workflowService.deleteWorkflowInfoByUserId(user.getuId());
                        return super.operaStatus(Globals.OPERA_SUCCESS_CODE,
                                "最终受理信息增加" + Globals.OPERA_SUCCESS_CODE_DESC);
                    } else {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, "同一批次不可重复受理");
                    }
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "客户名称、初检故障和主板型号不能为空");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新受理数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 获取匹配数据
     * 
     * @param workflow
     * @param req
     * @return {客户名称 ,主板型号 ,市场型号 ,初检故障 ,随机附件}
     */
    @RequestMapping("workflowcon/getMateDatas")
    @ResponseBody
    public APIContent getMateDatas(@ModelAttribute Workflow workflow, HttpServletRequest req) {
        try {
            if (!StringUtil.isRealEmpty(workflow.getImei())) {
                Workflow work = workflowService.getInfoByImei(workflow.getImei());
                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, work);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("获取匹配数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 发送至分拣
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/sendSort")
    @ResponseBody
    public APIContent sendSort(HttpServletRequest req) {
        try {
            String idsemp = req.getParameter("ids");
            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    if (workflowService.getCountToTestByIds(ids) != ids.length) {
                        return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                    }
                    workflowService.sendSort(ids);
                }
                return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送至分拣失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 数据同步 synch
     * 
     * @param workflow
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/synchDatas")
    @ResponseBody
    public APIContent synchDatas(@ModelAttribute Workflow workflow, HttpServletRequest req) {
        try {
            //计算应返还时间
            Date date = workflowService.gainBackTime(new Date());
//            System.out.println(date);
            if (!StringUtil.isRealEmpty(workflow.getImei()) || !StringUtil.isRealEmpty(workflow.getLockId())) {
                Workflow work = new Workflow();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                work.setBackTime(date);
                // TODO 调用本地服务接口
                Workflow w = null;
                if (!StringUtil.isRealEmpty(workflow.getImei())) {
                    w = workflowService.getInfoByImei(workflow.getImei());
                } else if (!StringUtil.isRealEmpty(workflow.getLockId())) {
                    w = workflowService.getInfoByLockId(workflow.getLockId());
                }

                if (null != w) {
                    // TODO 调用本地API-返修次数控制 / 调用AMS API 返修次数默认为零
                    // PA:三个月以内
                    if (w.getState() != -1) { // 不存在售后工作流中
                        return super.returnJson(Globals.OPERA_SYSCNFAIL_CODE, "[IMEI:" + w.getImei() + "]已存在售后工作流中",
                                null);
                    } else {
                        work.setImei(w.getImei());
                        work.setLockId(w.getLockId());
                        work.setBluetoothId(w.getBluetoothId());
                        work.setLockInfo(w.getLockInfo());// 扫码智能锁ID二维码
                        work.setSxdwId(w.getSxdwId());
                        work.setW_cusName(w.getW_cusName());
                        Integer CountNum = workflowService.getReturnTimes(w.getImei());

                        Workflow lastWf = workflowService.getLastReturnEngineer(w.getImei(), null);
                        if (null != lastWf) {
                            if (null != lastWf.getLastEngineer()) {
                                work.setLastEngineer(lastWf.getLastEngineer());
                                work.setLastAccTime(lastWf.getLastAccTime());
                            }
                        }
                        if (CountNum > 0) {
                            Workflow wf = workflowService.getLastReturnEngineer(w.getImei(), "repairAgain");
                            if (null != wf) {
                                if (null != wf.getReturnTimes()) {
                                    if (null != wf.getW_solution() && !"".equals(wf.getW_solution())
                                            && wf.getW_solution().indexOf("放弃维修") > -1) {
                                        work.setReturnTimes((0L));// 放弃维修返修次数重新计算
                                    } else {
                                        work.setReturnTimes(wf.getReturnTimes() + 1);
                                    }
                                } else {
                                    work.setReturnTimes((0L));
                                }
                            } else {
                                work.setReturnTimes((0L));
                            }
                        } else {
                            work.setReturnTimes((0L));
                        }

                        // 获取送修批号
                        Workflow workflowc = workflowService.getBatchNo(work.getSxdwId().toString());
                        if (null != workflowc) {
                            // 该批次已发货,重新生成批次号
                            String rep = workflowc.getRepairnNmber();
                            if (!StringUtil.isRealEmpty(rep)) {
                                if (workflowService.isExistsAndPay(rep) > 0) {
                                    work.setRepairnNmber(BatchNumUtil.getBatchNum());
                                } else {
                                    // 判断该批次是否已报价
                                    if (workflowService.getRepairNumberCountPriced(rep) > 0) {
                                        work.setPricedCount(1);
                                        work.setOldRepairNumber(rep);
                                    }
                                    // 送修批号只保存一天
                                    work.setRepairnNmber(rep);
                                }
                            }
                        } else {
                            work.setRepairnNmber(BatchNumUtil.getBatchNum());
                        }
                    }
                }
                {
                    // TODO 调用AMS服务接口
                    /**
                     * HTTP POST API GET AMS -MES INFERT
                     *
                     * @author Li.Shangzhi
                     * @date 2016-08-03 11:21:42
                     */
                    Saledata data = null;
                    String json = null;
                    if (null != workflow.getImei() && !"".equals(workflow.getImei().trim())) {
                        json = JavaNetURLRESTFulClient.restSale(workflow.getImei().trim(), "", AMS_SALES_URL);
                    } else if (null != workflow.getLockId() && !"".equals(workflow.getLockId().trim())) {
                        json = JavaNetURLRESTFulClient.restSale("", workflow.getLockId().trim(), AMS_SALES_URL);
                    }
                    // 判断扫码的智能锁ID是否一致，用来保存扫码二维码信息
                    if (!StringUtil.isRealEmpty(workflow.getLockInfo())) {
                        LockIdUrl u = lockIdUrlService.matchLongStr(workflow.getLockInfo());
                        if (null != u && null != u.getLockId() && null != u.getLockInfo()) {
                            if (workflow.getLockId().trim().equals(u.getLockId())) {
                                work.setLockInfo(workflow.getLockInfo());
                            }
                        }
                    }

                    if (null != json) {
                        JSONObject jsonObject = JSONObject.fromObject(json);
                        String msg = jsonObject.getString("msg");
                        if ("0".equals(msg)) {
                            data = (Saledata) JSONObject.toBean(jsonObject, Saledata.class);
                        }
                    }

                    if (null != data) {
                        if (!StringUtil.isRealEmpty(data.getSfVersion())) {// 软件版本号
                            work.setSfVersion(data.getSfVersion().trim());
                        }
                        if (!StringUtil.isRealEmpty(data.getBill())) {// 订单号
                            work.setBill(data.getBill().trim());
                        }
                        if (!StringUtil.isRealEmpty(data.getBoxCountNumber())
                                && !StringUtil.isRealEmpty(data.getBoxCountNumber().trim())) {// 该订单出货数量
                            if (data.getBoxCountNumber().indexOf("台") != -1) {
                                work.setOutCount(Integer.valueOf(data.getBoxCountNumber()
                                        .substring(0, data.getBoxCountNumber().indexOf("台")).trim()));
                            }
                        }
                        if (!StringUtil.isRealEmpty(data.getInsideSoftModel())) {
                            work.setInsideSoftModel(data.getInsideSoftModel().trim());
                        }
                        if (!StringUtil.isRealEmpty(data.getTestStatus())) {// 是否是试流料
                            work.setTestMatStatus(data.getTestStatus().trim());
                        }
                        if (!StringUtil.isRealEmpty(data.getLockId())) {// 智能锁ID
                            work.setLockId(data.getLockId().trim());
                        }
                        if (!StringUtil.isRealEmpty(data.getBluetoothId())) {// 蓝牙ID
                            work.setBluetoothId(data.getBluetoothId().trim());
                        }
                        if (!StringUtil.isRealEmpty(data.getImei())) {// Imei
                            work.setImei(data.getImei().trim());
                        }
                        Zbxhmanage zbType = null;
                        if (!StringUtil.isRealEmpty(data.getMcType()) && !StringUtil.isRealEmpty(data.getSfVersion())) {
                            String model = null;
                            if (data.getSfVersion().indexOf("_") != -1) {
                                model = data.getSfVersion().substring(0, data.getSfVersion().indexOf("_")).toUpperCase()
                                        .trim();// 截取版本号里第一个下划线前面的就是主板型号
                            } else if (data.getSfVersion().indexOf("-") != -1) {
                                model = data.getSfVersion().substring(0, data.getSfVersion().indexOf("-")).toUpperCase()
                                        .trim();// 英文横线
                            } else if (data.getSfVersion().indexOf("-") != -1) {
                                model = data.getSfVersion().substring(0, data.getSfVersion().indexOf("-")).toUpperCase()
                                        .trim();// 中文横线
                            }
                            String marketModel = data.getMcType();
                            if (null != marketModel && !"".equals(marketModel)) {
                                marketModel = marketModel.toUpperCase().replaceAll("\\（", "(").replaceAll("\\）", ")")
                                        .replaceAll(" ", "");// AMS系统里保存的市场型号有大小写，实际不区分
                            }

                            // TODO 通过市场型号 匹配主板型号
                            List<Zbxhmanage> zbxh = zbxhmanageMapper.getZbTypeByAms(marketModel, model);
                            if (zbxh != null && zbxh.size() > 0) {
                                if (zbxh.size() == 1) {
                                    zbType = zbxh.get(0);
                                    if (null != zbType) {
                                        work.setXhId(Long.valueOf(zbType.getMId()));
                                        if (StringUtils.isNotBlank(work.getW_cusName())
                                                && work.getW_cusName().indexOf("营销三部") > -1) {
                                            //保修规则1
                                            work.setIsWarranty(getisWarranty(zbType, data.getOutDate().toString(),
                                                    work.getW_cusName()));
                                        } else {
                                            //保修规则2
                                            work.setIsWarranty(getisWarranty(zbType, data.getOutDate().toString()));
                                        }
                                    }
                                }
                            } else {
                                if (null == work || null == work.getReturnTimes()) {
                                    work.setReturnTimes(0L);
                                }
                                work.setSalesTime(DateFormate(data.getOutDate()));
                                work.setW_marketModel(marketModel);
                                work.setW_ams_model(model);
                                return super.returnJson(Globals.ZBXH_FAIL_CODE,
                                        "市场型号是：" + marketModel + "，主板型号是：" + model + "的数据不存在，请添加后操作，否则会影响保修期判断", work);
                            }
                            if (null == work || null == work.getReturnTimes()) {
                                work.setReturnTimes(0L);
                            }
                            work.setSalesTime(DateFormate(data.getOutDate()));
                            work.setW_marketModel(marketModel);
                            work.setW_model(model);

                            // GK309/GK310个人设备以激活时间计算，保修一年，保修规则3
                            if (null != zbType) {
                                List<Integer> gkModel = Arrays.asList(new Integer[]{19, 20, 58, 265, 542, 785});
                                Integer gId = zbType.getGId();
                                if (gkModel.contains(gId)) {
                                    work.setIsWarranty(getisWarranty(data));
                                }
                            }
                            //保修规则4
                            if (null != data) {
                                Integer war = getIsWarranty(data.getImei(), data.getOutDate().toString());
                                if (null != war) {
                                    work.setIsWarranty(war);
                                }
                            }
                        }
                        //如果配置过保修期则直接从数据库中查询保修期
                        //如果没有配置过保修期，保修期直接根据主板类型确定，其他规则（保修规则1、2、3、4）作废
                        WarrantyInfo warrantyInfo = new WarrantyInfo();
                        List<String> imieList = new ArrayList<>();
                        imieList.add(data.getImei());
                        warrantyInfo.setImeiList(imieList);
                        List<WarrantyInfo> list = workflowService.listWarrantyInfo(warrantyInfo);
                        Date startDate = DateUtil.getDate(data.getOutDate());
                        //如果在保修表中设置了保修期，则直接使用
                        if (list.size() > 0 && list.get(0).getImei().equals(data.getImei())) {
                            WarrantyInfo war = list.get(0);
                            Date today = new Date();
                            Date endDate = DateUtils.addMonths(startDate, war.getProlongMonth());
                            //判断是否超保修期
                            if (endDate.getTime() > today.getTime() || DateUtils.isSameDay(endDate, today)) {
                                work.setIsWarranty(0);
                            } else {
                                work.setIsWarranty(1);
                            }
                        } else {
                            //如果保修表中没有设置保修期则根据机型等规则判断
                            if(zbType != null) {
                                int isWarranty = getIsWarrantyFormSpe(data,zbType, startDate);
                                if (isWarranty < 0) {
                                    return super.returnJson(Globals.ZBXH_FAIL_CODE,
                                            "市场型号为：" + zbType.getMarketModel() + ",主板型号为：" + zbType.getModel() + ",主板类型为："
                                                    + zbType.getModelType() + "的保修期数据不存在，请联系管理员", work);
                                } else {
                                    work.setIsWarranty(isWarranty);
                                }
                            }
                        }
                        return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, work);
                    } else {
                        return super.returnJson(Globals.OPERA_SYSCNFAIL_CODE, "", work);
                    }
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.operaStatus(Globals.AMS_EXCEPTION_CODE, Globals.AMS_EXCEPTION_CODE_DESC);
        }
    }

    /**
     * 一般性判定规则
     * 保修期限：有线、无线、电摩保修24个月；穿戴、DVR、ISD、Tracker保修13个月
     * @param zbxh
     * @return
     * @Date 2020-08-17
     */
    public int getIsWarranty(Zbxhmanage zbxh,Date time){
        if(zbxh == null || zbxh.getModelType()==null||zbxh.getModelType().equals("")){
            return -1;
        }
        int month=0;
        switch (zbxh.getModelType()){
            case "有线":;
            case "无线":;
            case "电摩":month = 24;break;
            case "穿戴":;
            case "DVR":;
            case "ISD":;
            case "Tracker":month = 13;break;
            default: return -1;
        }


        Date endDate = DateUtils.addMonths(time,month);
        Date today = new Date();
        if(endDate.getTime()>today.getTime()||DateUtils.isSameDay(endDate,today)){
            return 0;//保内
        }else {
            return 1;//保外
        }
    }

    /**
     * 1.订单号FX、FG、FZ开头的保修期3个月
     *
     * 2.上海万位和青岛迪迪的特殊判定规则
     * 公司名称（模糊匹配）      出货日期             产品类型         市场型号         其他        保修期限
     * 上海万位       &&     2018-03-15后    && （有线、无线   ||    GT370）  &&   去除翻新      3年
     * 青岛迪迪       &&     2018-06-01后    &&   有线、无线                                   3年
     *
     * 3.其他按照产品类型分
     * @return
     */
    public int getIsWarrantyFormSpe(Saledata data,Zbxhmanage zbxh,Date time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        int month = 0;
        String bill = data.getBill();
        //返工、翻新的直接三个月保修期
        if(data.getBill()!= null){
            String up = bill.toUpperCase();
            if(up.startsWith("FX") || up.startsWith("FG") || up.startsWith("FZ")){
                month = 3;
            }
        }
        //非返工
        if(month == 0) {
            try {
                d1 = format.parse("2018-03-15");
                d2 = format.parse("2018-06-01");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String cpName = data.getCpName();
            //没有公司名直接走原来的判断
            if (cpName == null || "".equals(cpName) ) {
                return getIsWarranty(zbxh, time);
            }

            if (cpName.contains("上海万位")) {
                //2018-03-15前出货的不管
                if (time.getTime() < d1.getTime()) {
                    return getIsWarranty(zbxh, time);
                }
                //非有线、无线、GT370不管
                if (!"有线".equals(zbxh.getModelType()) && !"无线".equals(zbxh.getModelType()) && !"GT370".equals(zbxh.getMarketModel())) {
                    return getIsWarranty(zbxh, time);
                }
                //符合条件
                month = 36;
            } else if (cpName.contains("青岛迪迪")) {
                if (time.getTime() < d2.getTime()) {
                    return getIsWarranty(zbxh, time);
                }
                //符合条件
                month = 36;
            } else {
                return getIsWarranty(zbxh, time);
            }
        }

        //判断是否过保修期，保修时间3年
        Date endDate = DateUtils.addMonths(time,month);
        Date today = new Date();
        if(endDate.getTime()>today.getTime()||DateUtils.isSameDay(endDate,today)){
            return 0;//保内
        }else {
            return 1;//保外
        }
    }

    /**
     * 验证是保内还是保外,在于2016-01-01之前保13个月 之后车载：保2年，非车载：保13个月
     * 
     * @param outDate
     *            日期格式：yyyy-MM-dd
     * @return 0 保内；1 保外
     */
    public int getisWarranty(Zbxhmanage zbxh, String time) throws ParseException {
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
        Date todayTime = new Date();
        if (deadlineWarranty.getTime() >= todayTime.getTime()) {
            return 0; // 保内
        } else {
            return 1; // 保外
        }
    }

    public int getisWarranty(Saledata saledata) throws ParseException {
        if (saledata.getActiveTime() == null) {
            return 1;
        }
        long activeTime = saledata.getActiveTime().getTime();
        long nowTime = new Date().getTime();
        long rest = nowTime - activeTime;
        if (rest > 365 * 24 * 60 * 60 * 1000) {
            return 1;// 保外
        }
        return 0;// 保内
    }

    public Integer getIsWarranty(String imei, String outDate) throws ParseException {
        Integer month = workflowService.getProlongMonth(imei);
        if (month == null || outDate == null) {
            return null;
        }
        if (month == 0) {
            return 1;// 保外
        }

        long saleTime = DateFormate(outDate).getTime();
        long nowTime = new Date().getTime();
        long pastTime = nowTime - saleTime;

        long prolongTime = month * 31L * 24L * 60L * 60L * 1000L;

        if (pastTime > prolongTime) {
            return 1;// 保外
        }
        return 0;// 保内
    }

    /*
     * 客户是“营销三部” 出货时间：2016年5月1日及以后 主板型号或市场型号是以["GT06N", "TR06", "GT06E", "GT06F", "ET200", "ET210"]开头时直接判定为保外
     */
    public int getisWarranty(Zbxhmanage zbxh, String time, String YXSB) throws ParseException {
        if (null != YXSB && !"".equals(YXSB)) {
            if (YXSB.indexOf("营销三部") > -1) {
                List<String> zbxhList = Arrays.asList("GT06N", "TR06", "GT06E", "GT06F", "ET200", "ET210");
                Date startDate = DateTimeUtil.getDateTime(time);
                Date point = DateTimeUtil.getDate("2016-05-01");
                if (startDate.getTime() >= point.getTime()) {
                    for (String s : zbxhList) {
                        if (zbxh.getModel().toUpperCase().startsWith(s)
                                || zbxh.getMarketModel().toUpperCase().startsWith(s)) {
                            return 1;
                        }
                    }
                }
            }
        }
        return getisWarranty(zbxh, time);
    }

    public Timestamp DateFormate(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(time);
        return new Timestamp(date.getTime());
    }

    /**
     * 匹配到多个主板型号时，选择后，再判断是否保内
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/zbxhIsWarranty")
    @ResponseBody
    public APIContent zbxhIsWarranty(HttpServletRequest req) {
        int ret = -1;
        String xhId = req.getParameter("xhId");
        String salesTime = req.getParameter("salesTime");
        String sxdw = req.getParameter("sxdw");
        try {
            if (!StringUtil.isRealEmpty(xhId) && !StringUtil.isRealEmpty(salesTime)) {
                Zbxhmanage zbxh = zbxhmanageMapper.getT(Integer.valueOf(xhId));
                if (!StringUtil.isRealEmpty(sxdw)) {
                    ret = getisWarranty(zbxh, salesTime, sxdw);
                } else {
                    ret = getisWarranty(zbxh, salesTime);
                }
            }
            return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, ret);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("判断是否保内失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 装箱工站扫描设备确定设备已送至装箱工站， 而不是状态改变了设备还在别的工站
     * 
     * @author TangYuping
     * @version 2017年3月6日 上午10:46:02
     * @param wf
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/getDataByImeiInPack")
    @ResponseBody
    public APIContent getDataByImeiInPack(@ModelAttribute Workflow wf, HttpServletRequest req) {
        try {
            Workflow w = workflowService.getWorkflowByImeiInPack(wf);
            if (null != w && null != w.getId()) {
                return super.putAPIData(w);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "设备不是待装箱状态，无法获取");
            }
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 发送数据到测试
     * 
     * @author TangYuping
     * @version 2017年2月21日 下午2:21:16
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/sendWorkTest")
    @ResponseBody
    public APIContent sendWorkTest(HttpServletRequest req, @ModelAttribute WorkflowTest wt) {
        try {
            String idsemp = req.getParameter("ids");
            String dataSource = req.getParameter("dataSource");

            if (null != idsemp && !StringUtil.isEmpty(idsemp)) {
                String ids[] = idsemp.split(",");
                if (ids != null && ids.length > 0) {
                    if ("受理站".equals(dataSource)) {
                        if (workflowService.getCountToTestByIds(ids) != ids.length) {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                    Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                        }
                    } else if ("维修站".equals(dataSource)) {
                        if (workflowRepairService.getCountNoPriceByWfids(ids) != ids.length) {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                    Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                        }
                    } else if ("终检站".equals(dataSource)) {
                        if (workflowFicheckService.getCountToTestByWfids(ids) != ids.length) {
                            return super.operaStatus(Globals.OPERA_FAIL_CODE,
                                    Globals.OPERA_FAIL_CODE_DESC + ":有数据已更新，刷新后再操作");
                        }
                    }
                    workflowService.sendWorkTest(dataSource, wt, ids);
                    return super.returnJson(Globals.OPERA_SUCCESS_CODE, null, null);
                } else {
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送至测试失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 查询所有未发货的批次号（正常件）
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/getAllNotPackInfo")
    @ResponseBody
    public APIContent getAllNotPackInfo(HttpServletRequest req) {
        try {
            List<Workflow> wList = workflowService.getAllNotPackInfo();
            return super.putAPIData(wList);
        } catch (Exception e) {
            logger.error("查询所有未发货的批次号（正常件）失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 查询要修改的数据
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/getDataList")
    @ResponseBody
    public APIContent getDataList(@ModelAttribute Workflow workflow, HttpServletRequest req) {
        try {
            List<Workflow> wList = workflowService.getDataList(workflow);
            return super.putAPIData(wList);
        } catch (Exception e) {
            logger.error("查询要修改的数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    /**
     * 修改维修数据
     * 
     * @param req
     * @return
     */
    @RequestMapping("workflowcon/updateRepairData")
    @ResponseBody
    public APIContent updateRepairData(HttpServletRequest req) {
        try {
            String repairnNmber = req.getParameter("repairnNmber");
            String imei = req.getParameter("imei");
            String state = req.getParameter("state");
            if (null != repairnNmber && !StringUtil.isEmpty(repairnNmber) && null != imei && !StringUtil.isEmpty(imei)
                    && null != state && !StringUtil.isEmpty(state)) {
                Workflow w = new Workflow();
                w.setRepairnNmber(repairnNmber);
                w.setImei(imei);
                List<Workflow> wList = workflowService.getDataList(w);
                if (null != wList && wList.size() > 0) {
                    logger.info("修改维修数据开始=======修改时间：" + new Timestamp(System.currentTimeMillis()) + "，修改人:"
                            + super.getSessionUserName(req) + "====:\n" + wList.get(0).toString());
                    int ret = workflowService.updateRepairData(wList, state);
                    wList = workflowService.getDataList(w);
                    logger.info("修改维修数据结束=======修改时间：" + new Timestamp(System.currentTimeMillis()) + "，修改人:"
                            + super.getSessionUserName(req) + "====:\n" + wList.get(0).toString());
                    if (ret == 0) {
                        logger.info("修改维修数据成功");
                        return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
                    } else {
                        logger.error("修改维修数据失败:" + ((ret == -1) ? "要修改的数据不符合条件"
                                : ((ret == -200) ? "修改的状态传值错误"
                                        : ((ret == -500) ? "获取数据错误" : ((ret == -100) ? "已付款数据不允许修改" : "其他不明原因")))));
                        return super.operaStatus(Globals.OPERA_FAIL_CODE, "修改维修数据失败:" + ((ret == -1) ? "要修改的数据不符合条件"
                                : ((ret == -200) ? "修改的状态传值错误"
                                        : ((ret == -500) ? "获取数据错误" : ((ret == -100) ? "已付款数据不允许修改" : "其他不明原因")))));
                    }
                } else {
                    logger.error("修改维修数据失败:未查询到数据");
                    return super.operaStatus(Globals.OPERA_FAIL_CODE, "修改维修数据失败");
                }
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "修改维修数据失败");
            }
        } catch (Exception e) {
            logger.error("修改维修数据失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("workflowcon/getWarrantyInfo")
    @ResponseBody
    public APIContent getWarrantyInfo(@ModelAttribute WarrantyInfo warranty, HttpServletRequest request) {
        try {
            Page<WarrantyInfo> page = workflowService.getWarrantyInfo(warranty);
            request.getSession().setAttribute("totalValue", page.getTotal());
            List<WarrantyInfo> list = page.getResult();

            return super.putAPIData(list);
        } catch (Exception e) {
            logger.error("操作失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("workflowcon/setWarrantyInfo")
    @ResponseBody
    public APIContent setWarrantyInfo(@ModelAttribute WarrantyInfo warrantyInfo, HttpServletRequest request) {
        try {

            List<String> existList = workflowService.isExistWarrantyInfo(warrantyInfo);
            if (CollectionUtils.isNotEmpty(existList)) {
                return super.putAPIData(existList);
            }

            warrantyInfo.setCreateBy(super.getSessionUserName(request));
            workflowService.setWarrantyInfo(warrantyInfo);

            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("操作失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("workflowcon/deleteWarrantyInfo")
    @ResponseBody
    public APIContent deleteWarrantyInfo(@ModelAttribute WarrantyInfo warrantyInfo, HttpServletRequest request) {
        try {
            workflowService.deleteWarrantyInfo(warrantyInfo.getImeis());

            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            logger.error("操作失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("workflowcon/getDeviceInfo")
    @ResponseBody
    public APIContent getDeviceInfo(@ModelAttribute DeviceInfo deviceInfo, HttpServletRequest request) {
        try {
            List<DeviceInfo> list = workflowService.getDeviceInfo(deviceInfo, request);

            return super.putAPIData(list);
        } catch (Exception e) {
            logger.error("操作失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("workflowcon/queryCpNameList")
    @ResponseBody
    public APIContent queryCpNameList(HttpServletRequest request) {
        try {
            List<String> list = workflowService.listCpName();

            return super.putAPIData(list);
        } catch (Exception e) {
            logger.error("操作失败:", e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("workflowcon/exportWarrantyData")
    @ResponseBody
    public void exportWarrantyData(@ModelAttribute WarrantyInfo warranty, HttpServletRequest request, HttpServletResponse response) {
        try {
            workflowService.exportWarrantyData(warranty, request, response);
        } catch (Exception e) {
            logger.error("操作失败:", e);
        }
    }

    @RequestMapping("workflowcon/exportNotSendInfo")
    @ResponseBody
    public void exportNotSendInfo(@ModelAttribute Workflow workflow, HttpServletRequest request, HttpServletResponse response) {
        try {
            workflowService.exportNotSendInfo(workflow, request, response);
        } catch (Exception e) {
            logger.error("操作失败:", e);
        }
    }

    @RequestMapping("workflowcon/notSendInfo")
    @ResponseBody
    public APIContent notSendInfo(@ModelAttribute CommonParam commonParam,@ModelAttribute Workflow workflow,
                                  HttpServletRequest request,HttpServletResponse response){
        try {
            //获取装箱条件
            Integer isPacked = workflow.getIsPacked();
            //默认查全部
            if(isPacked == null)
                workflow.setIsPacked(2);

            //获取工站条件
            Integer workStation = workflow.getWorkStation();
            //默认查全部
            if(workStation == null)
                workflow.setWorkStation(0);

            Page<Workflow> page = new Page<>();
            page.setCurrentPage(commonParam.getCurrentpage());
            page.setSize(commonParam.getPageSize());
            workflowService.getNotSendListPage(workflow, page);
            this.getRequest().getSession().setAttribute("pages", page.getTotalPage());
            this.getRequest().getSession().setAttribute("totalValue", page.getTotal());
            return new APIContent(page.getTotal(),page.getResult(),null);
        }catch (Exception e){
            logger.error(e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_SUCCESS_CODE);
        }
    }
    /*
    @RequestMapping("workflowcon/exportNotSendInfo")
    @ResponseBody
    public APIContent exportNotSendInfo(@ModelAttribute CommonParam commonParam,@ModelAttribute Workflow workflow,
                                  HttpServletRequest request,HttpServletResponse response){
        try {
            //获取装箱条件
            Integer isPacked = workflow.getIsPacked();
            //默认查全部
            if(isPacked == null)
                workflow.setIsPacked(2);

            //获取工站条件
            Integer workStation = workflow.getWorkStation();
            //默认查全部
            if(workStation == null)
                workflow.setWorkStation(0);

            Page<Workflow> page = new Page<>();
            page.setCurrentPage(commonParam.getCurrentpage());
            page.setSize(commonParam.getPageSize());
            workflowService.getNotSendListPage(workflow, page);
            this.getRequest().getSession().setAttribute("pages", page.getTotalPage());
            this.getRequest().getSession().setAttribute("totalValue", page.getTotal());
            return new APIContent(page.getTotal(),page.getResult(),null);
        }catch (Exception e){
            logger.error(e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_SUCCESS_CODE);
        }
    }

     */

    @RequestMapping("workflowcon/getState")
    @ResponseBody
    public APIContent getState(HttpServletRequest request,HttpServletResponse response){
        try {
            List<Workflow> list = workflowService.getRepairStateList(request.getParameter("repairnNmber"));
            return new APIContent(list.size(),list,Globals.OPERA_SUCCESS_CODE);
        }catch (Exception e){
            logger.error(e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE,"操作异常");
        }

    }

    @RequestMapping("workflowcon/ImportWarranty")
    @ResponseBody
    public APIContent ImportDatas(@RequestParam(value = "file", required = false) MultipartFile file,
                                  @RequestParam(value = "prolongMonth") Integer prolongMonth,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Globals.USER_KEY);
            int num = workflowService.importWarranty(file,prolongMonth,userInfo);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE,"成功导入"+num+"条数据");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("导入保修期数据失败");
        }
        return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
    }

    @RequestMapping("workflow/flushTimeoutDays")
    @ResponseBody
    public APIContent flushTimeoutDays(HttpServletRequest request){
        try {
            countTimeoutDateService.countTimoutDate();
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
        }catch (Exception e){
            logger.error("刷新超期天数失败"+e);
            e.printStackTrace();
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }

    @RequestMapping("workflow/flushBackTime")
    @ResponseBody
    public APIContent flushBackTime(@RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            workflowService.flushBackTime(start,end);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE,Globals.OPERA_SUCCESS_CODE_DESC);
        }catch (Exception e){
            logger.error("刷新应返还时间失败"+e);
            e.printStackTrace();
            return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
        }
    }

}
