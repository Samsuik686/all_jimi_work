/*
 * Created: 2016-7-12
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
 **/
package cn.concox.comm.base.service;

import java.util.List;

 import cn.concox.comm.base.rom.BaseSqlMapper;

public class BaseService<T> {
	
	private BaseSqlMapper<T> baseSqlMapper;
	
	public void setMapper(BaseSqlMapper<T> sqlMapper){
		this.baseSqlMapper=sqlMapper;
	}
	
	public void insert(T entity) {
		baseSqlMapper.insert(entity);
	}

	public void update(T entity){
		baseSqlMapper.update(entity);
	}

	public void delete(T entity) {
		baseSqlMapper.delete(entity);
	}

	public T select(T entity){
		return baseSqlMapper.select(entity);
	}
 
	public List<T> queryList(T entity){
		return baseSqlMapper.queryList(entity);
	}
}
