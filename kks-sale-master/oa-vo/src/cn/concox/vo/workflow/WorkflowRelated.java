/*
 * Created: 2016-8-2
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
 * @file: SaleWorkflowRelated.java
 * @functionName : workflow
 * @systemAbreviation : sale
 */
package cn.concox.vo.workflow;
import java.sql.Timestamp;

/**
 * <pre>
 * 售后服务关联表实体类
 * 数据库表名称：t_sale_workflow_related
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
public class WorkflowRelated{
	
	/**
     * 字段名称：ID
     * 
     * 数据库字段信息:ID INT(11)
     */
    private Integer id;

    /**
     * 字段名称：IMEI+时间戳
     * 
     * 数据库字段信息:fild VARCHAR(255)
     */
    private String fild;

    /**
     * 字段名称：IMEI
     * 
     * 数据库字段信息:imei VARCHAR(255)
     */
    private String imei;

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

    /**
     * 字段名称：随机附件标识{如：1XXX.2.XXX.列举｝
     * 
     * 数据库字段信息:sjfjDesc VARCHAR(255)
     */
    private String sjfjDesc;

    /**
     * 字段名称：初检查故障标识{如：1XXX.2.XXX.列举｝
     * 
     * 数据库字段信息:cjgzDesc VARCHAR(255)
     */
    private String cjgzDesc;

    /**
     * 字段名称：最终故障标识{如：1XXX.2.XXX.列举｝
     * 
     * 数据库字段信息:zzgzDesc VARCHAR(255)
     */
    private String zzgzDesc;
    
    /**
     * 字段名称：维修报价标识{如：1XXX.2.XXX.列举｝
     * 
     * 数据库字段信息:wxbjDesc VARCHAR(255)
     */
    private String wxbjDesc;

    /**
     * 字段名称：维修组件标识{如：1.XXX.2.XXX.列举｝
     * 
     * 数据库字段信息:matDesc VARCHAR(255)
     */
    private String matDesc;
    
    /**
     * 字段名称：电子料标识{如：1.XXX.2.XXX.列举｝
     * 
     * 数据库字段信息:dzlDesc VARCHAR(255)
     */
    private String dzlDesc;
    
    private String sllDesc;//试流料
    
    public WorkflowRelated() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFild() {
		return fild;
	}

	public void setFild(String fild) {
		this.fild = fild;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getSjfjDesc() {
		return sjfjDesc;
	}

	public void setSjfjDesc(String sjfjDesc) {
		this.sjfjDesc = sjfjDesc;
	}

	public String getCjgzDesc() {
		return cjgzDesc;
	}

	public void setCjgzDesc(String cjgzDesc) {
		this.cjgzDesc = cjgzDesc;
	}

	public String getZzgzDesc() {
		return zzgzDesc;
	}

	public void setZzgzDesc(String zzgzDesc) {
		this.zzgzDesc = zzgzDesc;
	}

	public String getMatDesc() {
		return matDesc;
	}

	public void setMatDesc(String matDesc) {
		this.matDesc = matDesc;
	}

	public String getWxbjDesc() {
		return wxbjDesc;
	}

	public void setWxbjDesc(String wxbjDesc) {
		this.wxbjDesc = wxbjDesc;
	}

	public String getDzlDesc() {
		return dzlDesc;
	}

	public void setDzlDesc(String dzlDesc) {
		this.dzlDesc = dzlDesc;
	}

	public String getSllDesc() {
		return sllDesc;
	}

	public void setSllDesc(String sllDesc) {
		this.sllDesc = sllDesc;
	}
	
}