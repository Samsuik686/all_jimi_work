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
 * @file: SaleFinalfailure.java
 * @functionName : system
 * @systemAbreviation : sale
 */
package cn.concox.vo.basicdata;
import java.io.Serializable;
/**
 * <pre>
 * 最终故障管理表实体类
 * 数据库表名称：t_sale_zgzmanage
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
public class Zgzmanage implements Serializable{
	private static final long serialVersionUID = 1L;

    /**
     * 字段名称：主键ID
     * 
     * 
     * 数据库字段信息:id int(11)
     */
    private Integer id;
    /**
     * 字段名称：分组id（引用t_sale_basegroup表里的gId）
     * 
     * 数据库字段信息:gId INT(10)
     */
    private Integer gId;

    /**
     * 字段名称：故障类别
     * 
     * 数据库字段信息:faultType VARCHAR(255)
     */
    private String faultType;

    /**
     * 字段名称：处理方法
     * 
     * 数据库字段信息:proceMethod VARCHAR(255)
     */
    private String proceMethod;

    /**
     * 字段名称：方法编码
     * 
     * 数据库字段信息:methodNO VARCHAR(32)
     */
    private String methodNO;

    /**
     * 字段名称：型号类别（车载 个人 手机）
     * 
     * 数据库字段信息:modelType VARCHAR(100)
     */
    private String modelType;

    /**
     * 字段名称：方法描述
     * 
     * 数据库字段信息:remark VARCHAR(255)
     */
    private String remark;
    /**
     * 字段名称：是否启用
     * 
     * 数据库字段信息:enabledFlag INT(11)
     */
    private int enabledFlag;
    /**
     * 字段名称：是否同步
     * 
     * 数据库字段信息:isSyncSolution INT(2)
     */
    private Integer isSyncSolution;
    
    private String showType;//显示位置

    public Zgzmanage() {
    }	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGId() {
        return this.gId;
    }

    public void setGId(Integer gId) {
        this.gId = gId;
    }

    public String getFaultType() {
        return this.faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

	
    public String getProceMethod() {
        return this.proceMethod;
    }

    public void setProceMethod(String proceMethod) {
        this.proceMethod = proceMethod;
    }

    public String getMethodNO() {
        return this.methodNO;
    }

    public void setMethodNO(String methodNO) {
        this.methodNO = methodNO;
    }

    public String getModelType() {
        return this.modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

	
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public int getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(int enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Integer getIsSyncSolution() {
		return isSyncSolution;
	}

	public void setIsSyncSolution(Integer isSyncSolution) {
		this.isSyncSolution = isSyncSolution;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

}