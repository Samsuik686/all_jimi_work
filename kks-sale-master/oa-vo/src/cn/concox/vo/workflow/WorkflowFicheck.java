/*
 * Created: 2016-8-6
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
 * @file: SaleWorkflowFicheck.java
 * @functionName : workflow
 * @systemAbreviation : sale
 */
package cn.concox.vo.workflow;
import java.sql.Timestamp;
/**
 * <pre>
 * 终检管理表实体类
 * 数据库表名称：t_sale_workflow_ficheck
 * </pre>
 * @author Li.ShangZhi 
 * @version v1.0
 */
public class WorkflowFicheck {
	
	 /**
     * 字段名称：主键ID
     * 
     * 数据库字段信息:Id INT(10)
     */
    private Integer Id;
	
    /**
     * 字段名称：公共表主键ID
     * 
     * 数据库字段信息:wfId INT(10)
     */
    private Integer wfId;

    /**
     * 字段名称：是否合格{1.合格，0不合格}
     * 
     * 数据库字段信息:ispass INT(10)
     */
    private Integer ispass;

    /**
     * 字段名称：描述备注
     * 
     * 数据库字段信息:finDesc VARCHAR(255)
     */
    private String finDesc;

    /**
     * 字段名称：终于检查人员
     * 
     * 数据库字段信息:finChecker VARCHAR(255)
     */
    private String finChecker;

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
    
    private Long state ;        //状态
    
    private String  imei;       //设备IMEI
    private String  lockId;     //智能锁ID
    private String  bluetoothId; //蓝牙ID
    private String  repairnNmber; //送修批号
    
	/* =================================业务字段 start===============================*/
    private String startTime;//开始时间
    
    private String endTime;//结束时间
    
    private String treeDate;
    
    private String searchMorOrAft; //判断查询上午或是下午
    
    private String fin_engineer;
    private Timestamp fin_payTime;
    
    private String f_acceptRemark; //受理备注
    private String f_remark;       //送修备注
    private String f_repairNumberRemark; //批次备注
    private String f_repairRemark; //维修备注
    private String f_priceRemark; //批次报价备注
    
    private String testResult;  //测试结果
	private String testPerson;  //测试人
	private Timestamp testTime;    //测试时间
    /* =================================业务字段 end===============================*/
    

    public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public WorkflowFicheck() {
    }	
	
    public Integer getWfId() {
		return wfId;
	}

	public void setWfId(Integer wfId) {
		this.wfId = wfId;
	}

	public Integer getIspass() {
		return ispass;
	}

	public void setIspass(Integer ispass) {
		this.ispass = ispass;
	}

	public String getFinDesc() {
        return this.finDesc;
    }

    public void setFinDesc(String finDesc) {
        this.finDesc = finDesc;
    }
	
    public String getFinChecker() {
        return this.finChecker;
    }

    public void setFinChecker(String finChecker) {
        this.finChecker = finChecker;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getRepairnNmber() {
		return repairnNmber;
	}

	public void setRepairnNmber(String repairnNmber) {
		this.repairnNmber = repairnNmber;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getTreeDate() {
		return treeDate;
	}

	public void setTreeDate(String treeDate) {
		this.treeDate = treeDate;
	}

	public String getSearchMorOrAft() {
		return searchMorOrAft;
	}

	public void setSearchMorOrAft(String searchMorOrAft) {
		this.searchMorOrAft = searchMorOrAft;
	}

	public String getFin_engineer() {
		return fin_engineer;
	}

	public void setFin_engineer(String fin_engineer) {
		this.fin_engineer = fin_engineer;
	}

	public Timestamp getFin_payTime() {
		return fin_payTime;
	}

	public void setFin_payTime(Timestamp fin_payTime) {
		this.fin_payTime = fin_payTime;
	}

	public String getF_acceptRemark() {
		return f_acceptRemark;
	}

	public void setF_acceptRemark(String f_acceptRemark) {
		this.f_acceptRemark = f_acceptRemark;
	}

	public String getF_remark() {
		return f_remark;
	}

	public void setF_remark(String f_remark) {
		this.f_remark = f_remark;
	}

	public String getF_repairNumberRemark() {
		return f_repairNumberRemark;
	}

	public void setF_repairNumberRemark(String f_repairNumberRemark) {
		this.f_repairNumberRemark = f_repairNumberRemark;
	}

	public String getF_repairRemark() {
		return f_repairRemark;
	}

	public void setF_repairRemark(String f_repairRemark) {
		this.f_repairRemark = f_repairRemark;
	}

	public String getF_priceRemark() {
		return f_priceRemark;
	}

	public void setF_priceRemark(String f_priceRemark) {
		this.f_priceRemark = f_priceRemark;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
	}

	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getBluetoothId() {
		return bluetoothId;
	}

	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
	}

	
	
	
	
}