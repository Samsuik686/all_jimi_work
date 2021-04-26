package cn.concox.vo.basicdata;

import java.util.Date;

/**
 * ams系统设备销售数据
 * @author Administrator
 *
 */
public class Saledata {

	private Integer eqpDepartment; //部门id
	private String boxNumber; //箱号
	private String imei;
	private String xpId;//芯片ID (查询条件)
	private String sfVersion;//软件版本号 (查询条件)
	private String hdVersion;//硬件版本号 (查询条件)
	private String bill;//订单号 (查询条件)
	private String mcType;//机型 (查询条件)
	private String cpName;//公司名称（或客户名） (查询条件)
	private String productionDate;//生产日期 (查询条件)
	private String outDate;//出货日期 (查询条件)
	private Integer status;//设备状态（正常/已删除/禁止接入系统）(查询条件)
	private String phone;//手机号/SIM
	private String sn; //SN码
	private String eqpDepartmentName;//部门名称
	private String deputySfVersion; //副版软件版本号
	private String deputyHdVersion; //副版硬件版本号
	
	private String chaddress;       //送修单位地址
	
	private String testStatus;//是否使用试流料
	
	private String boxCountNumber;//订单数量
	
	private String insideSoftModel; //内部机型
	
	private String lockId;//智能锁Id/密码
	private String bluetoothId;//蓝牙ID
	private String ICCID;
	private Date activeTime;// 激活时间
	
	public Date getActiveTime() {
        return activeTime;
    }
    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }
    public Integer getEqpDepartment() {
		return eqpDepartment;
	}
	public void setEqpDepartment(Integer eqpDepartment) {
		this.eqpDepartment = eqpDepartment;
	}
	public String getBoxNumber() {
		return boxNumber;
	}
	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getXpId() {
		return xpId;
	}
	public void setXpId(String xpId) {
		this.xpId = xpId;
	}
	public String getSfVersion() {
		return sfVersion;
	}
	public void setSfVersion(String sfVersion) {
		this.sfVersion = sfVersion;
	}
	public String getHdVersion() {
		return hdVersion;
	}
	public void setHdVersion(String hdVersion) {
		this.hdVersion = hdVersion;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getMcType() {
		return mcType;
	}
	public void setMcType(String mcType) {
		this.mcType = mcType;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getEqpDepartmentName() {
		return eqpDepartmentName;
	}
	public void setEqpDepartmentName(String eqpDepartmentName) {
		this.eqpDepartmentName = eqpDepartmentName;
	}
	public String getDeputySfVersion() {
		return deputySfVersion;
	}
	public void setDeputySfVersion(String deputySfVersion) {
		this.deputySfVersion = deputySfVersion;
	}
	public String getDeputyHdVersion() {
		return deputyHdVersion;
	}
	public void setDeputyHdVersion(String deputyHdVersion) {
		this.deputyHdVersion = deputyHdVersion;
	}
	public String getChaddress() {
		return chaddress;
	}
	public void setChaddress(String chaddress) {
		this.chaddress = chaddress;
	}
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	public String getBoxCountNumber() {
		return boxCountNumber;
	}
	public void setBoxCountNumber(String boxCountNumber) {
		this.boxCountNumber = boxCountNumber;
	}
	public String getInsideSoftModel() {
		return insideSoftModel;
	}
	public void setInsideSoftModel(String insideSoftModel) {
		this.insideSoftModel = insideSoftModel;
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
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
}
