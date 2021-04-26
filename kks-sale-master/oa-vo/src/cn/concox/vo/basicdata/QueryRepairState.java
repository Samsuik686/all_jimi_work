package cn.concox.vo.basicdata;

import java.sql.Timestamp;

/**
 * 查询维修进度
 * 
 * @author 20160308
 *
 */
public class QueryRepairState {
	private Timestamp acceptanceTime;//受理时间
	
	private String imei;// IMEI号

	private String marketModel;// 市场型号

	private String cusName;// 送修单位
	
	private String phone;//手机号
	
	private String address;//联系地址

	private Integer state;// 进度
	
	private String sjfjDesc;//随机附件
	
	private String wxbjDesc;//维修报价描述
	
	private String solution;//处理措施
	
	private String zzgzDesc;//最终故障
	
	private String lockId;//智能锁id
	
	private String bluetoothId;//蓝牙id

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMarketModel() {
		return marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getSjfjDesc() {
		return sjfjDesc;
	}

	public void setSjfjDesc(String sjfjDesc) {
		this.sjfjDesc = sjfjDesc;
	}

	public String getWxbjDesc() {
		return wxbjDesc;
	}

	public void setWxbjDesc(String wxbjDesc) {
		this.wxbjDesc = wxbjDesc;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getZzgzDesc() {
		return zzgzDesc;
	}

	public void setZzgzDesc(String zzgzDesc) {
		this.zzgzDesc = zzgzDesc;
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

	public Timestamp getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(Timestamp acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}