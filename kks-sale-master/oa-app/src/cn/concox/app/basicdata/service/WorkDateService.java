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
 * 2020-09-22             pengshoulong   Create the class
 * http://www.jimilab.com/
 */


package cn.concox.app.basicdata.service;

import cn.concox.app.basicdata.mapper.WorkDateMapper;
import cn.concox.app.common.page.Page;
import cn.concox.vo.basicdata.WorkDate;
import cn.concox.vo.basicdata.WorkDateQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * TODO
 * 工作日
 * @author pengshoulong
 * @date 2020-09-22
 * @since 1.0.0
 */
@Service
public class WorkDateService {

    private static final Logger logger = LoggerFactory.getLogger(WorkDateService.class);

    @Resource
    private WorkDateMapper workDateMapper;

    public Page<WorkDate> getWorkDate(Page<WorkDate> page, WorkDateQuery workDate){
        workDateMapper.selectWorkDate(page,workDate);
        return page;
    }

    /**
     * 修改工作日信息（只修改工作类型）
     * @param query
     */
    @Transactional
    public boolean setWorkDate(WorkDateQuery query){
        WorkDate date = workDateMapper.selectWorkTypeById(query.getId());
        if(date == null){
            return false;
        }
        date.setWorkType(query.getWorkType());
        workDateMapper.update(date);
        return true;
    }

    /**
     * 通过日期判断是否需要工作
     * @param date
     * @return
     */
    public Boolean needWork(Date date,Integer userType){
        WorkDate workDate = workDateMapper.selectUserWorkDateByDate(date,userType);
        if(workDate == null){
            logger.error("未找到该日期的信息{}",date);
            return null;
        }
        //如果这天整天都上班则直接返回真
        if(workDate.getWorkType() == 1){
            return true;
        }

        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(date);
        //获取时间是第几个小时，00:33是第0个小时。12小时前表示上午，12小时后表示下午
        //上午返回2，下午返回3
        int type = calendar.get(Calendar.HOUR_OF_DAY)<12? 2:3;

        return workDate.getWorkType() == type;
    }

    /**
     * 获取from到to之间的所有假期的日期（上半天班也表示假期）,左闭右开区间
     * 限制limit个
     * @param from
     * @param to
     * @param limit
     * @return
     */
    public List<WorkDate> getHolidayBetween(Date from,Date to,int limit,int type){
        List<WorkDate> list = workDateMapper.selectHolidayBetween(from,to,limit,type);
//        System.out.println(list);
        return list;
    }

    public void batchAdd(){
        Integer count = workDateMapper.selectCount();
        if(count > 200)
            return;
        ycfkWorkDatePrd();
    }

    public void ycfkWorkDatePrd() {
        LocalDate from = LocalDate.of(2017, 1, 1);
        LocalDate to = LocalDate.of(2031, 1, 1);
        WorkDate workDate = new WorkDate();
        workDate.setUserType(1);
        do {
            ZonedDateTime zonedDateTime = from.atStartOfDay(ZoneId.systemDefault());
            workDate.setNormalDate(Date.from(zonedDateTime.toInstant()));
            Integer dayofweek = from.getDayOfWeek().getValue();
            workDate.setWeekDay(dayofweek);
            if (dayofweek == 6) {
                workDate.setWorkType(2);
            } else if (dayofweek == 7) {
                workDate.setWorkType(0);
            } else {
                workDate.setWorkType(1);
            }
            workDateMapper.insert(workDate);
            from = from.plusDays(1);
        } while (from.isBefore(to));
    }
}
