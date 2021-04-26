/*
 * Created: 2016-7-15
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
 * @file: SaleFinalfailureMapper.java
 * @functionName : system
 * @systemAbreviation : sale
 */
package cn.concox.app.basicdata.mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;

/**
 * <pre>
 * SaleFinalfailureMapper 数据访问接口
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
public interface BasegroupMapper<T> extends BaseSqlMapper<T>{
	
	@SuppressWarnings("rawtypes")
	public Page queryListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException; 

	public String selectByGidInShareMode(Integer gId);
}