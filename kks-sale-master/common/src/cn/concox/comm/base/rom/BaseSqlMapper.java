/*
 * Created: 2013-5-19 下午7:49:12
 * ==================================================================================================
 *
 * Cicove Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2010-2013 Cicove Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Cicove R&D.
 * @author: Li Zhongjie
 * @file: BaseSqlMapper.java
 */
package cn.concox.comm.base.rom;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

public interface BaseSqlMapper<T> extends SqlMapper {
	
	public T getT(@Param("id") Integer id) throws DataAccessException;
	
	public void insert(T entity) throws DataAccessException;

	public void update(T entity) throws DataAccessException;

	public void delete(T entity) throws DataAccessException;

	public T select(T entity) throws DataAccessException;
	
	public List<T> queryList(T entity) throws DataAccessException;
	
	
	
	/**
	 * 导入、新增判断是否重复
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public int isExists(T entity) throws DataAccessException;
	
	
	/**
	 * 有分组需求的数据导入、新增判断是否存在gid
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public Integer isExistsGid(T entity) throws DataAccessException;
	
	/**
	 * 删除或修改分组，查看该组下数据个数
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId);
	
	/**
	 * 修改分组，改其下数据分类名称
	 * @param gId
	 */
	public void updateByGid(Integer gId);
}
