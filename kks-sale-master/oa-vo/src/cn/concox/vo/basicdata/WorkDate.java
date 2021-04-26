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


package cn.concox.vo.basicdata;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

/**
 * TODO
 * 工作日类，表示某天是否需要工作
 * @author pengshoulong
 * @date 2020-09-22
 * @since 1.0.0
 */
public class WorkDate {
    /** 主键id */
    private Long id;
    /** 日期 */
    private Date normalDate;
    /** 0表示不工作，1表示全天工作，2表示仅上午工作，3表示仅下午工作*/
    private Integer workType;
    /** 1表示维修未寄出，2表示异常反馈。*/
    private Integer userType;
    /** 星期几 1代表星期一 7代表星期日*/
    private Integer weekDay;

    public WorkDate(Long id, Date normalDate, Integer workType, Integer userType, Integer weekDay) {
        this.id = id;
        this.normalDate = normalDate;
        this.workType = workType;
        this.userType = userType;
        this.weekDay = weekDay;
    }

    public WorkDate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getNormalDate() {
        return normalDate;
    }

    public void setNormalDate(Date normalDate) {
        this.normalDate = normalDate;
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

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDate workDate = (WorkDate) o;
        return Objects.equals(id, workDate.id) &&
                Objects.equals(normalDate, workDate.normalDate) &&
                Objects.equals(workType, workDate.workType) &&
                Objects.equals(userType, workDate.userType) &&
                Objects.equals(weekDay, workDate.weekDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, normalDate, workType, userType, weekDay);
    }

    @Override
    public String toString() {
        return "WorkDate{" +
                "id=" + id +
                ", normalDate=" + normalDate +
                ", workType=" + workType +
                ", userType=" + userType +
                ", weekday=" + weekDay +
                '}';
    }
}
