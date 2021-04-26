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
 * @functionName : system
 * @systemAbreviation : sale
 */
package cn.concox.app.basicdata.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.PricePjMapper;
import cn.concox.app.common.page.Page;
import cn.concox.vo.basicdata.PricePj;
/**
 * <pre>
 * 批次报价添加配件料
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
@Service("pricePjService")
@Scope("prototype")
public class PricePjService{
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="pricePjMapper")
	private PricePjMapper<PricePj> pricePjMapper;
	
	@SuppressWarnings("unchecked")
	public Page<PricePj> getManageListPage(PricePj pricePj,int currentPage, int pageSize){
		Page<PricePj> pricePjs = new Page<PricePj>();
		pricePjs.setCurrentPage(currentPage); 
		pricePjs.setSize(pageSize);
		return pricePjMapper.queryListPage(pricePjs, pricePj);
	}

	public PricePj getInfo(PricePj pricePj) {  
		return pricePjMapper.getT(pricePj.getId()); 
	} 
	
	public void deleteInfo(String... ids){
		pricePjMapper.deleteInfo(ids);
	} 
	
	public void insertInfo(PricePj pricePj){
		pricePjMapper.insert(pricePj);
	}
	
	public void updateInfo(PricePj pricePj){
		pricePjMapper.update(pricePj);
	}
 
	public List<PricePj> queryList(PricePj pricePj) {
		return pricePjMapper.queryList(pricePj);
	}
	
	public List<PricePj> queryListByrepairNumber(String repairNumber) {
		return pricePjMapper.queryListByrepairNumber(repairNumber);
	}
	
	public int isExists(PricePj pricePj){
		return pricePjMapper.isExists(pricePj);
	}
	
	public Integer isExistsGid(PricePj pricePj){
		return pricePjMapper.isExistsGid(pricePj);
	}
	
	public int findCountForPricePj(String repairNumber,String id){
		return pricePjMapper.findCountForPricePj(repairNumber,id);
	}
	
	public BigDecimal getPricePjCosts(String repairNumber){
		BigDecimal pjCosts= pricePjMapper.getPricePjCosts(repairNumber);
		if(null==pjCosts){
			pjCosts =BigDecimal.ZERO;
		}
		return pjCosts;
	}
}
