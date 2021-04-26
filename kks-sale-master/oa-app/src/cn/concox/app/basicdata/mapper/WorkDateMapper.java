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


package cn.concox.app.basicdata.mapper;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.WorkDate;
import cn.concox.vo.basicdata.WorkDateQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author pengshoulong
 * @date 2020-09-22
 * @since 1.0.0
 */
public interface WorkDateMapper extends BaseSqlMapper<WorkDate> {
    public void updateWorkTypeByDate(@Param("normalDate") Date normalDate,@Param("userType") Integer userType,@Param("workType") Integer workType);

    public void updateWorkTypeById(@Param("id") Long id,@Param("workType") Integer workType);


    public List<WorkDate> selectWorkDate(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") WorkDateQuery workDate);

    public Integer selectWorkTypeByDate(Date date);

    public List<WorkDate> selectWorkDateByDate(Date date);

    public WorkDate selectWorkTypeById(long id);

    public WorkDate selectUserWorkDateByDate(@Param("date") Date date,@Param("userType") Integer userType);

    public List<WorkDate> selectHolidayBetween(@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,@Param("num")int num,@Param("type")int type);

    public Integer selectCount();

    public void insert(@Param("po")WorkDate workDate);
}