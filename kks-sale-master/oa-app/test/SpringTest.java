/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2021.
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
 * 2021-01-25             Peng.Shoulong   Create the class
 * http://www.jimilab.com/
 */

import cn.concox.app.basicdata.service.TimeoutReasonService;
import cn.concox.app.report.controller.HwmReportController;
import cn.concox.app.scheduler.CountTimeoutDateService;
import cn.concox.app.workflow.service.WorkflowService;
import cn.concox.vo.basicdata.Holiday;
import cn.concox.vo.basicdata.WorkDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试
 * @author Peng.Shoulong
 * @date 2021-01-25
 * @since 1.0.0
 */
@ContextConfiguration(locations = "classpath:applicationcontext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTest {

    @Autowired
    WorkflowService workflowService;
    
    
    MockMvc mockMvc;
    //应返回时间计算测试

    @Test
    public void backTimeTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parse = dateFormat.parse("2021-03-06 10:00:00");
        Date date = workflowService.gainBackTime(parse);
        System.out.println(date);
    }

    @Test
    public void shouldBackTimeTest() throws ParseException {
        Holiday holiday = new Holiday();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //放假开始时间，这里开始时间和结束时间是同一天
        WorkDate from = new WorkDate();
        from.setWorkType(0);//全天放假
        Date parse = format.parse("2021-01-01 00:00:00");
        from.setNormalDate(parse);//日期
        from.setUserType(1);
        from.setWeekDay(6);
        //放假结束时间
        WorkDate to = new WorkDate();
        to.setWorkType(0);//全天放假
        Date toParse = format.parse("2021-01-04 00:00:00");
        to.setNormalDate(toParse);//日期
        to.setUserType(1);
        to.setWeekDay(1);

        holiday.setFrom(from);
        holiday.setTo(to);
        //受理时间
        Date accept = format.parse("2020-12-31 15:00:00");

        Date date = workflowService.shouldBackTime(1, 1, holiday, accept);
        System.out.println(date);
    }

    @Resource
    CountTimeoutDateService countTimeoutDateService;

    //超期原因自动备注测试
    @Test
    public void autoTest(){

    }

    @Resource
    TimeoutReasonService timeoutReasonService;

    Logger logger = LoggerFactory.getLogger(SpringTest.class);

    @Test
    public void timeoutReasonTest(){
        logger.info("日志日志日日志{}","13213");
        boolean hasReason = timeoutReasonService.hasReason("客户原因123");
        Assert.assertTrue(hasReason);

        boolean hasReason1 = timeoutReasonService.hasReason("adbsfa");
        Assert.assertFalse(hasReason1);
    }

    @Autowired
    private HwmReportController hwmReportController;

    @Test
    public void updateTimeoutTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(hwmReportController).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/timeout/updateInfo")
                .param("ids", "181020")
                .param("timeoutReason", "test1"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println("=============================================="+contentAsString);
    }

    //超期原因自动备注
    @Transactional
    @Test
    @Rollback(false)
    public void autoReasonTest(){
        countTimeoutDateService.countTimoutDate();
    }
}
