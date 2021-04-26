/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
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
 * 2019年12月9日    Wang Xirui         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.app.report.task;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.concox.app.report.mapper.BadrateReportMapper;
import cn.concox.comm.Globals;
import cn.concox.comm.util.JavaNetURLRESTFulClient;
import cn.concox.vo.report.BadRateReport;

/**
 * @FileName SynchronizeGoods.java
 * @Description:同步设备出货总数
 *
 * @Date 2019年12月9日 下午4:05:32
 * @author Wang Xirui
 * @version 1.0
 */
@Component
@Service
public class SynchronizeGoods {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizeGoods.class);

    @Resource
    private BadrateReportMapper<BadRateReport> badrateReportMapper;

    /**
     * @Title: SynGoodsTask
     * @Description: 每周日04:00执行
     * @author Wang Xirui
     * @date 2019年12月9日 下午4:10:31
     */
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void SynGoodsTask() {
        logger.info("同步出货总数定时任务开始...");

        List<String> mcTypeList = badrateReportMapper.listAllMcType();
        for (String mcType : mcTypeList) {
            int number = JavaNetURLRESTFulClient.restGoods(mcType, Globals.AMS_Goodsurl);

            int updateNum = badrateReportMapper.updateGoods(mcType, number);
            if (updateNum < 1 && number > 0) {
                badrateReportMapper.insertGoods(mcType, number);
            }
        }
        logger.info("同步出货总数定时任务结束。");
    }
}
