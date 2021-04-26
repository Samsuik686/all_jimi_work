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

import cn.concox.comm.base.rom.BaseSqlMapper;

/**
 * <pre>
 *数据访问接口
 * </pre>
 */
public interface PriceGroupPjKeyMapper<T> extends BaseSqlMapper<T>{
	
	public String getPjlIdsByProName(@Param("model")String model, @Param("marketModel")String marketModel, @Param("keyType")String keyType, @Param("proName")String proName);
}