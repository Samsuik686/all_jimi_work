/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2020.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording,
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 *
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2020-10-15             pengshoulong   Create the class
 * http://www.jimilab.com/
 */


package cn.concox.app.scheduler;

import cn.concox.app.basicdata.service.WorkDateService;
import cn.concox.app.report.service.HwmReportService;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.app.workflow.service.WorkflowTotalpayService;
import cn.concox.comm.util.DateUtil;
import cn.concox.vo.basicdata.WorkDate;
import cn.concox.vo.workflow.Workflow;
import cn.concox.vo.workflow.WorkflowTotalpay;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * TODO
 * 定时计算超期时间、自动填写超天原因、自动备注
 * @author pengshoulong
 * @date 2020-10-15
 * @since 1.0.0
 */
@Component
@Service("countTimeoutDateService")
public class CountTimeoutDateService {

    @Resource(name="workflowService")
    private WorkflowService workflowService;

    @Resource(name ="workDateService")
    private WorkDateService workDateService;

    @Resource(name = "workflowTotalpayService")
    private WorkflowTotalpayService workflowTotalpayService;

    @Resource(name="hwmReportService")
    private HwmReportService reportService;


    Logger logger = LoggerFactory.getLogger(CountTimeoutDateService.class);
    /**
     * 售后服务器的时间不准
     *
     * 每天00:30执行
     */
    @Scheduled(cron="0 30 0 * * ?")
    public void countTimoutDate(){
        try {
//            logger.info("定时计算超期时间、自动填写超天原因、自动备注开始");
            //1.获取超期且超期天数未锁定的记录，即超过了应返还时间，并且未发送装箱
            Calendar now = Calendar.getInstance();
            //设置成当日的00:00:00
            Date initNow = DateUtil.initDate(now.getTime());
            List<Workflow> timeoutAndNotLock = workflowService.getTimeoutAndNotLockData(initNow);
            //2.计算超期天数，当前时间与应返还时间隔的天数，且要去除假期。（如果有单个半天的假期则算一天，但是两个半天也算一天）
            logger.info("未锁定超天未寄出记录数："+timeoutAndNotLock.size());
            for (Workflow workflow : timeoutAndNotLock) {
                    Date from = DateUtils.addDays(DateUtil.initDate(workflow.getBackTime()), 1);
                    //该方法返回左闭右开区间中的非工作日。所以是应返还时间的后一天到当前时间的下一天
                    List<WorkDate> holidayBetween = workDateService.getHolidayBetween(from, DateUtils.addDays(initNow, 1), 100, 1);
                    //计算返还时间到当前时间的天数
                    int dayDiff = DateUtil.getDayDiff(workflow.getBackTime(), initNow);
                    //计算中间的假期数
                    int holidayNum = countDay(holidayBetween);
                    //真实超期时间
                    int timeoutDays = dayDiff - holidayNum;

                    //超期时间有变化则写入数据库
                    if (workflow.getTimeoutDays() == null || timeoutDays != workflow.getTimeoutDays())
                        workflowService.modifyTimeoutDayById(workflow.getId(), timeoutDays);


                    //自动填写超期原因
                    if (timeoutDays == 1 && workflow.getTimeoutReason() == null) {
                        //获取超期原因
                        String reason = getTimeoutReason(workflow);
                        if (reason != null) {
                            //更新数据库
                            workflowService.modifyTimeoutReasonById(workflow.getId(), reason);
                        }else {
                            if ( workflow.getBackTime()!=null && workflow.getW_sendFinTime()!=null) {
                                long TimeoutBackTime=workflow.getBackTime().getTime();
                                long sendFicheckTime =workflow.getW_sendFinTime().getTime();
                                if (TimeoutBackTime>sendFicheckTime){
                                    workflowService.modifyTimeoutReasonById(workflow.getId(), "测试超时");
                                }
                                else{
                                    workflowService.modifyTimeoutReasonById(workflow.getId(), "维修超时");
                                    String[] arr = { String.valueOf(workflow.getId()) };
                                    reportService.timeoutUpdateInfo(workflow.getDutyOfficer(), null, null, arr);
                                }
                            }
                        }
                        logger.info("设备" + workflow.getImei() + "超期原因：" + reason);
                    }
            }
        }catch (Exception e){
            logger.error("更新超期天数失败",e);
            e.printStackTrace();
        }
    }

    /**
     * 获取超天原因
     * 1.客户付款日期在预计返还日期下午的，自动备注客户原因
     * 2.已报价，待付款---》客户原因
     * 3.测试工站----》测试验证原因
     * 4.单客户返修超过100台
     * 5.营销三部设备
     * @param workflow
     * @return
     */
    public String getTimeoutReason(Workflow workflow){
        /*
        客户付款日期在预计返还日期下午的，自动备注客户原因.
         */
        List<WorkflowTotalpay> list  = workflowTotalpayService.getTotalpayDate(workflow.getRepairnNmber());
        if(list.size() != 0){
            WorkflowTotalpay workflowTotalpay = list.get(0);
            Date payTime = workflowTotalpay.getPayTime();
            Date backTime = workflow.getBackTime();
            if(DateUtils.isSameDay(payTime,backTime) && DateUtil.hourOfDay(payTime) >= 12){
                return "客户原因";
            }
        }

        /*
        已报价待付款填客户原因
         */
        int state = workflow.getState();
        switch (state){
            //报价工站的都备注测试验证问题
            case 3: return "客户原因";
            case 9: return "客户原因";
            case 14: return "客户原因";
            //测试工站的都备注测试验证问题
            case 15: return "测试验证问题";
            default:  break;
        }

         /*
        同天同一客户收件数量大于等于100台的，自动备注单客户返修超过100台
         */
        Date acceptTime = workflow.getAcceptanceTime();
        //获取受理的当日零点时间
        Date initAcceptTime = DateUtil.initDate(acceptTime);
        //获取受理日的下一日零点时间
        Date nextData = DateUtils.addDays(initAcceptTime,1);
        int count = workflowService.getCountBetweenAcceptTime(initAcceptTime,nextData,workflow.getSxdwId().intValue());
        if(count>=100){
            return "单客户返修超过100台";
        }
        /*
        客户名称模糊匹配为营销三部的 ——自动备注营销三部设备
         */
        if(workflow.getCusName().contains("营销三部")){
            return "营销三部设备";
        }
        return null;

    }

    /**
     * 计算list中假期的天数。两个半天或者单个的半天都算一天
     * @param list
     * @return
     */
    public int countDay(List<WorkDate> list){
        //一天数
        int all=0;
        //半天数
        int half = 0;
        for(WorkDate workDate:list){
            //全天放假
            if(workDate.getWorkType() == 0 ){
                all++;
            }
            //半天放假
            if(workDate.getWorkType() == 2 || workDate.getWorkType() == 3){
                half++;
            }
        }
        return all+(half/2)+(half%2);
    }
}
