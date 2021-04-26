/*
 * Created: 2016-7-27
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
package cn.concox.vo.basicdata;

import java.io.Serializable;

/**
 * <pre>
 * 报价配件料组合关键字
 * 数据库表名称：t_sale_pricegrouppj_key
 */
public class PriceGroupPjKey implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 字段名称：报价组合匹配关键字id
	 * 
	 * 数据库字段信息:id Integer(11)
	 */
	private Integer id;

	/**
	 * 字段名称：关键字组合名称
	 * 
	 * 数据库字段信息:keyName VARCHAR(255)
	 */
	private String keyName;
	
	/**
	 * 字段名称：关键字类别 参照维修报价类别
	 * 
	 * 数据库字段信息:keyType VARCHAR(255)
	 */
	private String keyType;

	/**
	 * 字段名称：关键字描述
	 * 
	 * 数据库字段信息:keyDesc VARCHAR(255)
	 */
	private String keyDesc;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:keyRemark VARCHAR(255)
	 */
	private String keyRemark;

	public PriceGroupPjKey() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyDesc() {
		return keyDesc;
	}

	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}

	public String getKeyRemark() {
		return keyRemark;
	}

	public void setKeyRemark(String keyRemark) {
		this.keyRemark = keyRemark;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

}