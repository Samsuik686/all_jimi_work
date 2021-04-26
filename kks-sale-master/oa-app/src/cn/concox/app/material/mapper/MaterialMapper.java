/*
 * Created: 2016-8-10
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
package cn.concox.app.material.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.material.Material;
@SuppressWarnings("rawtypes")
public interface MaterialMapper<T> extends BaseSqlMapper<T> {

	public Page<Material> queryListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws  DataAccessException;

	public List<Material> getOrderList(Material material) throws  DataAccessException; 

	public Page<Material> getOrderListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po);
	
	public Material getInfoByProNOAndMasterOrSlave(@Param("proNO")String proNO, @Param("masterOrSlave")String masterOrSlave);
	
}
