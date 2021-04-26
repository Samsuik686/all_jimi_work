package cn.concox.vo.material;

import java.io.Serializable;

/**
 * <pre>
 * 数据库表名称：t_sale_material_log_temp
 * </pre>
 */
public class MaterialLogTemp implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	/**
	 * 字段名称：rowNO
	 * 
	 * 数据库字段信息:rowNO INT(11)
	 */
	private Object rowNO;

	/**
	 * 字段名称：物料类型[0:配件料，1:电子料]
	 * 
	 */
	private Object materialType;

	/**
	 * 字段名称：出库入库
	 * 
	 * 数据库字段信息:totalType VARCHAR(50)
	 */
	private Object totalType;

	/**
	 * 字段名称：出库入库类型
	 * 
	 * 数据库字段信息:type VARCHAR(50)
	 */
	private Object type;

	/**
	 * 字段名称：仓库的名字
	 * 
	 * 数据库字段信息:depotName VARCHAR(255)
	 */
	private Object depotName;

	/**
	 * 字段名称：收货方
	 * 
	 * 数据库字段信息:receivingParty VARCHAR(255)
	 */
	private Object receivingParty;

	/**
	 * 字段名称：经手人
	 * 
	 * 数据库字段信息:clerk VARCHAR(255)
	 */
	private Object clerk;

	/**
	 * 字段名称：出入库日期
	 * 
	 * 数据库字段信息:outTime DATETIME
	 */
	private Object outTime;

	/**
	 * 字段名称：物料编码
	 * 
	 * 数据库字段信息:proNO VARCHAR(255)
	 */
	private Object proNO;

	/**
	 * 字段名称：序号
	 * 
	 * 数据库字段信息:placesNO VARCHAR(255)
	 */
	private Object placesNO;

	/**
	 * 字段名称：M | T
	 * 
	 * 数据库字段信息:masterOrSlave VARCHAR(255)
	 */
	private Object masterOrSlave;

	/**
	 * 字段名称：厂商代码
	 * 
	 * 数据库字段信息:manufacturerCode VARCHAR(255)
	 */
	private Object manufacturerCode;

	/**
	 * 字段名称：厂商
	 * 
	 * 数据库字段信息:manufacturer VARCHAR(255)
	 */
	private Object manufacturer;

	/**
	 * 字段名称：损耗
	 * 
	 * 数据库字段信息:loss VARCHAR(255)
	 */
	private Object loss;

	/**
	 * 字段名称：数量
	 * 
	 * 数据库字段信息:number INT(10)
	 */
	private Object number;

	/**
	 * 字段名称：批发价
	 * 
	 * 数据库字段信息:tradePrice DECIMAL(11,2)
	 */
	private Object tradePrice;

	/**
	 * 字段名称：成本价
	 * 
	 * 数据库字段信息:costPrice DECIMAL(8,2)
	 */
	private Object costPrice;
	
	/**
	 * 字段名称：工时费
	 * 
	 * 数据库字段信息:hourlyRates DECIMAL(8,2)
	 */
	private Object hourlyRates;
	
	/**
	 * 字段名称：零售价
	 * 
	 * 数据库字段信息:retailPrice DECIMAL(8,2)
	 */
	private Object retailPrice;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private Object remark;

	/**
	 * 字段名称：型号(对应型好管理的市场型号)
	 * 
	 * 数据库字段信息:marketModel VARCHAR(255)
	 */
	private Object marketModel;

	/**
	 * 字段名称：平台
	 * 
	 * 数据库字段信息:platform VARCHAR(40)
	 */
	private Object platform;
	/**
	 * 字段名称：出库入库成功或失败
	 * 
	 * 数据库字段信息:status int(11)
	 */
	private Object status;

	/**
	 * 批量orderNOs
	 */
	private Object proName;//物料名称
	
	private Object proSpeci;//规格
	
	private Object consumption;//用量

	private Object importDate;// 导入标记，有失败，删除该标记的所有数据
	
	private Object outTimeStart;
	
	private Object outTimeEnd;
	
	private String ids;
	
	private String errorInfo;
	
	private String importPerson;//导入人

	public MaterialLogTemp() {
	}

	public Object getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Object materialType) {
		this.materialType = materialType;
	}

	public Object getOutTimeStart() {
		return outTimeStart;
	}

	public void setOutTimeStart(Object outTimeStart) {
		this.outTimeStart = outTimeStart;
	}

	public Object getOutTimeEnd() {
		return outTimeEnd;
	}

	public void setOutTimeEnd(Object outTimeEnd) {
		this.outTimeEnd = outTimeEnd;
	}

	public Object getTotalType() {
		return totalType;
	}

	public void setTotalType(Object totalType) {
		this.totalType = totalType;
	}

	public Object getType() {
		return this.type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public Object getDepotName() {
		return this.depotName;
	}

	public void setDepotName(Object depotName) {
		this.depotName = depotName;
	}

	public Object getReceivingParty() {
		return this.receivingParty;
	}

	public void setReceivingParty(Object receivingParty) {
		this.receivingParty = receivingParty;
	}

	public Object getClerk() {
		return this.clerk;
	}

	public void setClerk(Object clerk) {
		this.clerk = clerk;
	}

	public Object getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Object outTime) {
		this.outTime = outTime;
	}

	public Object getProNO() {
		return this.proNO;
	}

	public void setProNO(Object proNO) {
		this.proNO = proNO;
	}

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}

	public Object getPlacesNO() {
		return this.placesNO;
	}

	public void setPlacesNO(Object placesNO) {
		this.placesNO = placesNO;
	}

	public Object getManufacturerCode() {
		return this.manufacturerCode;
	}

	public void setManufacturerCode(Object manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public Object getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(Object manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Object getLoss() {
		return this.loss;
	}

	public void setLoss(Object loss) {
		this.loss = loss;
	}

	public Object getNumber() {
		return this.number;
	}

	public void setNumber(Object number) {
		this.number = number;
	}

	public Object getRetailPrice() {
		return this.retailPrice;
	}

	public void setRetailPrice(Object retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Object getHourlyRates() {
		return this.hourlyRates;
	}

	public void setHourlyRates(Object hourlyRates) {
		this.hourlyRates = hourlyRates;
	}

	public Object getRemark() {
		return this.remark;
	}

	public void setRemark(Object remark) {
		this.remark = remark;
	}

	public Object getMarketModel() {
		return this.marketModel;
	}

	public void setMarketModel(Object marketModel) {
		this.marketModel = marketModel;
	}

	public Object getPlatform() {
		return this.platform;
	}

	public void setPlatform(Object platform) {
		this.platform = platform;
	}

	public Object getImportDate() {
		return importDate;
	}

	public void setImportDate(Object importDate) {
		this.importDate = importDate;
	}

	public Object getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(Object tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Object getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Object costPrice) {
		this.costPrice = costPrice;
	}

	public Object getProName() {
		return proName;
	}

	public void setProName(Object proName) {
		this.proName = proName;
	}

	public Object getProSpeci() {
		return proSpeci;
	}

	public void setProSpeci(Object proSpeci) {
		this.proSpeci = proSpeci;
	}

	public Object getConsumption() {
		return consumption;
	}

	public void setConsumption(Object consumption) {
		this.consumption = consumption;
	}

	public Object getMasterOrSlave() {
		return masterOrSlave;
	}

	public void setMasterOrSlave(Object masterOrSlave) {
		this.masterOrSlave = masterOrSlave;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Object getRowNO() {
		return rowNO;
	}

	public void setRowNO(Object rowNO) {
		this.rowNO = rowNO;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getImportPerson() {
		return importPerson;
	}

	public void setImportPerson(String importPerson) {
		this.importPerson = importPerson;
	}
	
}
