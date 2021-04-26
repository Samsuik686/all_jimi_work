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
package cn.concox.vo.material;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <pre>
 * 物料信息单据表实体类
 * 数据库表名称：t_sale_material
 * </pre>
 * 
 * @author Li.ShangZhi
 * @version v1.0
 */
public class Material {
	private Integer id;

	/**
     * 字段名称：仓库名称
     * 
     * 数据库字段信息:depot VARCHAR(255)
     */
    private String depot;

    /**
     * 字段名称：物料类型[0:配件料，1:电子料]
     * 
     * 数据库字段信息:materialType INT(10)
     */
    private Integer materialType;

    /**
     * 字段名称：物料编码
     * 
     * 数据库字段信息:proNO VARCHAR(255)
     */
    private String proNO;

    /**
     * 字段名称：物料名称
     * 
     * 数据库字段信息:proName VARCHAR(255)
     */
    private String proName;

    /**
     * 字段名称：规格
     * 
     * 数据库字段信息:proSpeci VARCHAR(2000)
     */
    private String proSpeci;

    /**
     * 字段名称：M | T
     * 
     * 数据库字段信息:masterOrSlave VARCHAR(255)
     */
    private String masterOrSlave;

    /**
     * 字段名称：损耗
     * 
     * 数据库字段信息:lossType VARCHAR(255)
     */
    private String lossType;

    /**
     * 字段名称：用量
     * 
     * 数据库字段信息:consumption INT(10)
     */
    private Integer consumption;

    /**
     * 字段名称：总数
     * 
     * 数据库字段信息:totalNum INT(10)
     */
    private Integer totalNum;

    /**
     * 字段名称：预警数值
     * 
     * 数据库字段信息:analyzeNum INT(10)
     */
    private Integer analyzeNum;

    /**
     * 字段名称：零售价
     * 
     * 数据库字段信息:retailPrice DECIMAL(8,2)
     */
    private BigDecimal retailPrice;

    /**
     * 字段名称：状态
     * 
     * 数据库字段信息:state VARCHAR(255)
     */
    private String state;

    /**
     * 字段名称：备注
     * 
     * 数据库字段信息:remark VARCHAR(255)
     */
    private String remark;

    /**
     * 字段名称：创建时间
     * 
     * 数据库字段信息:createTime DATETIME(19)
     */
    private Timestamp createTime;

    /**
     * 字段名称：修改时间
     * 
     * 数据库字段信息:updateTime DATETIME(19)
     */
    private Timestamp updateTime;

	/*----------------------业务字段------------------------------*/
	private Integer pjNumber;// 批次报价添加配件料
	
	private Integer conNum;//需求数量

	public Material() {
	}

	public String getDepot() {
		return this.depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public Integer getMaterialType() {
		return this.materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public String getProNO() {
		return this.proNO;
	}

	public void setProNO(String proNO) {
		this.proNO = proNO;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProSpeci() {
		return proSpeci;
	}

	public void setProSpeci(String proSpeci) {
		this.proSpeci = proSpeci;
	}

	public String getLossType() {
		return this.lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}

	public Integer getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getAnalyzeNum() {
		return this.analyzeNum;
	}

	public void setAnalyzeNum(Integer analyzeNum) {
		this.analyzeNum = analyzeNum;
	}

	public BigDecimal getRetailPrice() {
		return this.retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getPjNumber() {
		return pjNumber;
	}

	public void setPjNumber(Integer pjNumber) {
		this.pjNumber = pjNumber;
	}

	public Integer getConsumption() {
		return consumption;
	}

	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMasterOrSlave() {
		return masterOrSlave;
	}

	public void setMasterOrSlave(String masterOrSlave) {
		this.masterOrSlave = masterOrSlave;
	}

	public Integer getConNum() {
		return conNum;
	}

	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}

}