package cn.concox.vo.material;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <pre>
 * 出入库日志管理实体类
 * 数据库表名称：t_sale_material_log
 * </pre>
 * 
 * @author Li.bifeng
 * @version v1.0
 */
public class MaterialLog implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 字段名称：id
	 * 
	 * 数据库字段信息:id INT(11)
	 */
	private Integer id;
	/**
	 * 字段名称：单据编号
	 * 
	 * 数据库字段信息:orderNO VARCHAR(255)
	 */
	private String orderNO;

	/**
	 * 字段名称：物料类型[0:配件料，1:电子料]
	 * 
	 */
	private Integer materialType;

	/**
	 * 字段名称：出库入库
	 * 
	 * 数据库字段信息:totalType VARCHAR(50)
	 */
	private Integer totalType;

	/**
	 * 字段名称：出库入库类型
	 * 
	 * 数据库字段信息:type VARCHAR(50)
	 */
	private String type;

	/**
	 * 字段名称：仓库的名字
	 * 
	 * 数据库字段信息:depotName VARCHAR(255)
	 */
	private String depotName;

	/**
	 * 字段名称：收货方
	 * 
	 * 数据库字段信息:receivingParty VARCHAR(255)
	 */
	private String receivingParty;

	/**
	 * 字段名称：经手人
	 * 
	 * 数据库字段信息:clerk VARCHAR(255)
	 */
	private String clerk;

	/**
	 * 字段名称：出入库日期
	 * 
	 * 数据库字段信息:outTime DATETIME
	 */
	private Timestamp outTime;

	/**
	 * 字段名称：物料编码
	 * 
	 * 数据库字段信息:proNO VARCHAR(255)
	 */
	private String proNO;

	/**
	 * 字段名称：序号
	 * 
	 * 数据库字段信息:placesNO VARCHAR(255)
	 */
	private String placesNO;

	/**
	 * 字段名称：M | T
	 * 
	 * 数据库字段信息:masterOrSlave VARCHAR(255)
	 */
	private String masterOrSlave;

	/**
	 * 字段名称：厂商代码
	 * 
	 * 数据库字段信息:manufacturerCode VARCHAR(255)
	 */
	private String manufacturerCode;

	/**
	 * 字段名称：厂商
	 * 
	 * 数据库字段信息:manufacturer VARCHAR(255)
	 */
	private String manufacturer;

	/**
	 * 字段名称：损耗
	 * 
	 * 数据库字段信息:loss VARCHAR(255)
	 */
	private String loss;

	/**
	 * 字段名称：数量
	 * 
	 * 数据库字段信息:number INT(10)
	 */
	private Integer number;

	/**
	 * 字段名称：批发价
	 * 
	 * 数据库字段信息:tradePrice DECIMAL(11,2)
	 */
	private BigDecimal tradePrice;

	/**
	 * 字段名称：成本价
	 * 
	 * 数据库字段信息:costPrice DECIMAL(8,2)
	 */
	private BigDecimal costPrice;
	
	/**
	 * 字段名称：工时费
	 * 
	 * 数据库字段信息:hourlyRates DECIMAL(8,2)
	 */
	private BigDecimal hourlyRates;
	
	/**
	 * 字段名称：零售价
	 * 
	 * 数据库字段信息:retailPrice DECIMAL(8,2)
	 */
	private BigDecimal retailPrice;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;

	/**
	 * 字段名称：型号(对应型好管理的市场型号)
	 * 
	 * 数据库字段信息:marketModel VARCHAR(255)
	 */
	private String marketModel;

	/**
	 * 字段名称：平台
	 * 
	 * 数据库字段信息:platform VARCHAR(40)
	 */
	private String platform;
	/**
	 * 字段名称：出库入库成功或失败
	 * 
	 * 数据库字段信息:status int(11)
	 */
	private Integer status;

	/**
	 * 批量id
	 */
	private String ids;

	/**
	 * 批量orderNOs
	 */
	private String orderNOs;
	
	private String proName;//物料名称
	
	private String proSpeci;//规格
	
	private Integer consumption;//用量

	private String importFlag;// 导入标记，有失败，删除该标记的所有数据
	
	private String outTimeStart;
	
	private String outTimeEnd;
	
	private Integer errorNum;//修改出入库时的误差数量

	public MaterialLog() {
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public String getOutTimeStart() {
		return outTimeStart;
	}

	public void setOutTimeStart(String outTimeStart) {
		this.outTimeStart = outTimeStart;
	}

	public String getOutTimeEnd() {
		return outTimeEnd;
	}

	public void setOutTimeEnd(String outTimeEnd) {
		this.outTimeEnd = outTimeEnd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNO() {
		return this.orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Integer getTotalType() {
		return totalType;
	}

	public void setTotalType(Integer totalType) {
		this.totalType = totalType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepotName() {
		return this.depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getReceivingParty() {
		return this.receivingParty;
	}

	public void setReceivingParty(String receivingParty) {
		this.receivingParty = receivingParty;
	}

	public String getClerk() {
		return this.clerk;
	}

	public void setClerk(String clerk) {
		this.clerk = clerk;
	}

	public Timestamp getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Timestamp outTime) {
		this.outTime = outTime;
	}

	public String getProNO() {
		return this.proNO;
	}

	public void setProNO(String proNO) {
		this.proNO = proNO;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPlacesNO() {
		return this.placesNO;
	}

	public void setPlacesNO(String placesNO) {
		this.placesNO = placesNO;
	}

	public String getManufacturerCode() {
		return this.manufacturerCode;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getLoss() {
		return this.loss;
	}

	public void setLoss(String loss) {
		this.loss = loss;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getRetailPrice() {
		return this.retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public BigDecimal getHourlyRates() {
		return this.hourlyRates;
	}

	public void setHourlyRates(BigDecimal hourlyRates) {
		this.hourlyRates = hourlyRates;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMarketModel() {
		return this.marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOrderNOs() {
		return orderNOs;
	}

	public void setOrderNOs(String orderNOs) {
		this.orderNOs = orderNOs;
	}

	public String getImportFlag() {
		return importFlag;
	}

	public void setImportFlag(String importFlag) {
		this.importFlag = importFlag;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public String getProName() {
		return proName;
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

	public Integer getConsumption() {
		return consumption;
	}

	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}

	public String getMasterOrSlave() {
		return masterOrSlave;
	}

	public void setMasterOrSlave(String masterOrSlave) {
		this.masterOrSlave = masterOrSlave;
	}

	public Integer getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}
}
