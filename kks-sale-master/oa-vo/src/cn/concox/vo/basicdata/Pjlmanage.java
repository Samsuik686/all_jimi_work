package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 配件料管理
 * 
 * 数据库表名称：t_sale_pjlmanage
 * </pre>
 */
public class Pjlmanage implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段名称：ID
	 * 
	 * 数据库字段信息:dzlId AUTO_INCREMENT COMMENT 'ID',
	 */
	private Integer pjlId;

	/**
	 * 字段名称：市场型号
	 * 
	 * 数据库字段信息:marketModel VARCHAR(50)
	 */
	private String marketModel;
	
	/**
	 * 字段名称：主板型号
	 * 
	 * 数据库字段信息:model VARCHAR(50)
	 */
	private String model;

	/**
	 * 字段名称：编码
	 * 
	 * 数据库字段信息:proNO VARCHAR(50)
	 */
	private String proNO;

	/**
	 * 数据库字段信息:proName VARCHAR(50)
	 */
	private String proName;

	/**
	 * 字段名称：规格
	 * 
	 * 数据库字段信息:proSpeci VARCHAR(255)
	 */
	private String proSpeci;

	/**
	 * 字段名称：零售价
	 * 
	 * 数据库字段信息:retailPrice DECIMAL(8,2)
	 */
	private BigDecimal retailPrice;
	/**
	 * 
	 * 字段名称：用量
	 * 
	 * 数据库字段信息:consumption INT(10)
	 */
	private Integer consumption;

	/**
	 * 字段名称：备选型号
	 * 
	 * 数据库字段信息:otherModel VARCHAR(255)
	 */
	private String otherModel;

	/**
	 * 字段名称：备注
	 * 
	 * 数据库字段信息:remark VARCHAR(255)
	 */
	private String remark;
	
	/**
	 * 字段名称：M|T
	 * 
	 * 数据库字段信息:masterOrSlave VARCHAR(255)
	 */
	private String masterOrSlave;
	
	private BigDecimal hourFee;//工时费
	
	private String searchKey;// 查询关键字（维修、报价查询配件料）
	
	private String searchType;//维修、报价根据主板型号查询配件料
	
	private String groupsearchKey;//销售配件料组合查询
	
	private String priceGroupsearchKey;//报价配件料组合查询

	public Pjlmanage() {
	}

	public String getMarketModel() {
		return this.marketModel;
	}

	public void setMarketModel(String marketModel) {
		this.marketModel = marketModel;
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

	public Integer getConsumption() {
		return this.consumption;
	}

	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOtherModel() {
		return otherModel;
	}

	public void setOtherModel(String otherModel) {
		this.otherModel = otherModel;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getMasterOrSlave() {
		return masterOrSlave;
	}

	public void setMasterOrSlave(String masterOrSlave) {
		this.masterOrSlave = masterOrSlave;
	}

	public Integer getPjlId() {
		return pjlId;
	}

	public void setPjlId(Integer pjlId) {
		this.pjlId = pjlId;
	}

	public String getGroupsearchKey() {
		return groupsearchKey;
	}

	public void setGroupsearchKey(String groupsearchKey) {
		this.groupsearchKey = groupsearchKey;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getPriceGroupsearchKey() {
		return priceGroupsearchKey;
	}

	public void setPriceGroupsearchKey(String priceGroupsearchKey) {
		this.priceGroupsearchKey = priceGroupsearchKey;
	}

	public BigDecimal getHourFee() {
		return hourFee;
	}

	public void setHourFee(BigDecimal hourFee) {
		this.hourFee = hourFee;
	}

}