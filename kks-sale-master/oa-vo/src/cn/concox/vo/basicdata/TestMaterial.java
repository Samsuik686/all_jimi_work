package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <pre>
 * 试流料管理
 * 
 * 数据库表名称：t_ams_test_material
 * </pre>
 */
public class TestMaterial implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer tid;
	
    /**
     * 字段名称：创建人
     * 
     * 数据库字段信息:create_user VARCHAR(30)
     */
    private String createUser;

    /**
     * 字段名称：创建时间
     * 
     * 数据库字段信息:create_time DATETIME(19)
     */
    private Timestamp createTime;

    /**
     * 字段名称：物料名称
     * 
     * 数据库字段信息:material_name VARCHAR(100)
     */
    private String materialName;

    /**
     * 字段名称：物料编码
     * 
     * 数据库字段信息:material_number VARCHAR(30)
     */
    private String materialNumber;

    /**
     * 字段名称：物料规格
     * 
     * 数据库字段信息:material_specification VARCHAR(30)
     */
    private String materialSpecification;

    /**
     * 字段名称：所要替换的物料编码
     * 
     * 数据库字段信息:material_replace VARCHAR(100)
     */
    private String materialReplace;

    /**
     * 字段名称：位号
     * 
     * 数据库字段信息:place_number VARCHAR(30)
     */
    private String placeNumber;

    /**
     * 字段名称：供应商
     * 
     * 数据库字段信息:supplier VARCHAR(50)
     */
    private String supplier;

    /**
     * 字段名称：项目
     * 
     * 数据库字段信息:project VARCHAR(50)
     */
    private String project;

    /**
     * 字段名称：出货总数量
     * 
     * 数据库字段信息:amount INT(10)
     */
    private Integer amount;

    /**
     * 字段名称：SMT试流时间
     * 
     * 数据库字段信息:SMT_test_time DATE(10)
     */
    private Date SMTTestTime;

    /**
     * 字段名称：SMT工单
     * 
     * 数据库字段信息:SMT_work_order VARCHAR(50)
     */
    private String SMTWorkOrder;

    /**
     * 字段名称：组装试流时间
     * 
     * 数据库字段信息:assembly_test_time DATE(10)
     */
    private Date assemblyTestTime;

    /**
     * 字段名称：组装试流结果
     * 
     * 数据库字段信息:assembly_test_result VARCHAR(50)
     */
    private String assemblyTestResult;

    /**
     * 
     * 
     * 数据库字段信息:test_result VARCHAR(50)
     */
    private String testResult;

    /**
     * 字段名称：订单号
     * 
     * 数据库字段信息:bill VARCHAR(100)
     */
    private String bill;

    /**
     * 字段名称：出货时间
     * 
     * 数据库字段信息:out_time DATE(10)
     */
    private Date outTime;

    /**
     * 字段名称：
     * 
     * 数据库字段信息:update_user VARCHAR(30)
     */
    private String updateUser;

    /**
     * 
     * 
     * 数据库字段信息:update_time DATETIME(19)
     */
    private Timestamp updateTime;

    /**
     * 字段名称：SMT试流结果
     * 
     * 数据库字段信息:SMT_test_result VARCHAR(50)
     */
    private String SMTtestResult;

    /**
     * 
     * 
     * 数据库字段信息:_MASK_TO_V2 DATETIME(19)
     */
    private Timestamp MASKTOV2;

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getMaterialSpecification() {
		return materialSpecification;
	}

	public void setMaterialSpecification(String materialSpecification) {
		this.materialSpecification = materialSpecification;
	}

	public String getMaterialReplace() {
		return materialReplace;
	}

	public void setMaterialReplace(String materialReplace) {
		this.materialReplace = materialReplace;
	}

	public String getPlaceNumber() {
		return placeNumber;
	}

	public void setPlaceNumber(String placeNumber) {
		this.placeNumber = placeNumber;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getSMTTestTime() {
		return SMTTestTime;
	}

	public void setSMTTestTime(Date sMTTestTime) {
		SMTTestTime = sMTTestTime;
	}

	public String getSMTWorkOrder() {
		return SMTWorkOrder;
	}

	public void setSMTWorkOrder(String sMTWorkOrder) {
		SMTWorkOrder = sMTWorkOrder;
	}

	public Date getAssemblyTestTime() {
		return assemblyTestTime;
	}

	public void setAssemblyTestTime(Date assemblyTestTime) {
		this.assemblyTestTime = assemblyTestTime;
	}

	public String getAssemblyTestResult() {
		return assemblyTestResult;
	}

	public void setAssemblyTestResult(String assemblyTestResult) {
		this.assemblyTestResult = assemblyTestResult;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getSMTtestResult() {
		return SMTtestResult;
	}

	public void setSMTtestResult(String sMTtestResult) {
		SMTtestResult = sMTtestResult;
	}

	public Timestamp getMASKTOV2() {
		return MASKTOV2;
	}

	public void setMASKTOV2(Timestamp mASKTOV2) {
		MASKTOV2 = mASKTOV2;
	}

}