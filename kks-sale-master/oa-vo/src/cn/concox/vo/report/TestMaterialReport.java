package cn.concox.vo.report;

import java.sql.Timestamp;

public class TestMaterialReport {

	private String bill; // 订单号

	private String imei; // imei

	private String materialNumber; // 试流料编码

	private String materialName; // 试流料名称

	private Integer matCount; // 维修选择数量

	private Integer amount; // 订单数量

	private String percent; // 百分比

	private Timestamp acceptanceTime;// 受理时间

	private Timestamp salesTime;// 出货日期

	private String engineer;// 维修员

	private String zzgzDesc;// 最终故障

	private String solution;// 处理措施

	private String startTime; // 受理开始日期

	private String endTime; // 受理结束日期

	private String saleStartTime; // 销售开始日期

	private String saleEndTime; // 销售结束日期

	private Integer testMatfalg;// 使用试流料标记

	private String project;// 项目

	private String createUser;//创建人
	private String createTime;// 创建时间
	private String materialSpecification;// 物料规格
	private String materialReplace;// 所要替换的物料编码
	private String placeNumber;// 位号
	private String supplier;// 供应商
	private String sMTTestTime;// SMT试流时间
	private String sMTWorkOrder;// SMT工单
	private String assemblyTestTime;// 组装试流时间
	private String assemblyTestTesult;// 组装试流结果
	private String testResult;// 试流判定
	private String updateUser;// 修改人
	private String updateTime;// 修改时间
	private String sMTTestResult;// SMT试流结果
	
	private String outTime;//出货日期

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getMatCount() {
		return matCount;
	}

	public void setMatCount(Integer matCount) {
		this.matCount = matCount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
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

	public Timestamp getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(Timestamp acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getZzgzDesc() {
		return zzgzDesc;
	}

	public void setZzgzDesc(String zzgzDesc) {
		this.zzgzDesc = zzgzDesc;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Integer getTestMatfalg() {
		return testMatfalg;
	}

	public void setTestMatfalg(Integer testMatfalg) {
		this.testMatfalg = testMatfalg;
	}

	public Timestamp getSalesTime() {
		return salesTime;
	}

	public void setSalesTime(Timestamp salesTime) {
		this.salesTime = salesTime;
	}

	public String getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public String getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(String saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getAssemblyTestTime() {
		return assemblyTestTime;
	}

	public void setAssemblyTestTime(String assemblyTestTime) {
		this.assemblyTestTime = assemblyTestTime;
	}

	public String getAssemblyTestTesult() {
		return assemblyTestTesult;
	}

	public void setAssemblyTestTesult(String assemblyTestTesult) {
		this.assemblyTestTesult = assemblyTestTesult;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getsMTTestTime() {
		return sMTTestTime;
	}

	public void setsMTTestTime(String sMTTestTime) {
		this.sMTTestTime = sMTTestTime;
	}

	public String getsMTWorkOrder() {
		return sMTWorkOrder;
	}

	public void setsMTWorkOrder(String sMTWorkOrder) {
		this.sMTWorkOrder = sMTWorkOrder;
	}

	public String getsMTTestResult() {
		return sMTTestResult;
	}

	public void setsMTTestResult(String sMTTestResult) {
		this.sMTTestResult = sMTTestResult;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

}
