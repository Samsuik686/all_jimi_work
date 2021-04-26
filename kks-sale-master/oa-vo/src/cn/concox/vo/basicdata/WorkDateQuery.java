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
 * 2020-09-25             pengshoulong   Create the class
 * http://www.jimilab.com/
 */


package cn.concox.vo.basicdata;



import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 * workdate查询用
 * @author pengshoulong
 * @date 2020-09-25
 * @since 1.0.0
 */
public class WorkDateQuery implements Serializable {
    private static final long serialVersionUID = -7193490765458015823L;
    /** 工作日ID*/
    private Long id;
    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /** 查询：0表示不工作，1表示全天工作，2表示仅上午工作，3表示仅下午工作 4表示全部*/
    private Integer workType;
    /**查询： 1表示维修未寄出，2表示异常反馈。0表示全部*/
    private Integer userType;
    /** 标识日期*/
    private Date normalDate;
    /** 表示是周几，0表示查询全部*/
    private Integer weekDay;

    private Integer pageSize;

    private Integer pageNum;

    public WorkDateQuery() {
    }

    public WorkDateQuery(Long id, Date startTime,Date normalDate, Date endTime, Integer workType, Integer userType, Integer weekDay, Integer pageSize, Integer pageNum) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workType = workType;
        this.userType = userType;
        this.weekDay = weekDay;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.normalDate = normalDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer type) {
        this.userType = type;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Date getNormalDate() {
        return normalDate;
    }

    public void setNormalDate(Date normalDate) {
        this.normalDate = normalDate;
    }
}
