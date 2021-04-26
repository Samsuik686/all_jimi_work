package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 维修报价表
 * 数据库表名称：t_sale_repair_price
 */
public class RepairPriceManage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 字段名称：主键
	 * 
	 * 数据库字段信息:int(11)
	 */
	private Integer rid;
	/**
	 * 字段名称：分组类别
	 * 
	 * 数据库字段信息:int(11)
	 */
	private Integer gId;
	/**
	 * 字段名称：报价信息描述
	 * 
	 * 数据库字段信息:amount varchar(100)
	 */
	private String amount;
	/**
	 * 字段名称：报价金额
	 * 
	 * 数据库字段信息:price DECIMAL(8,2)
	 */
	private BigDecimal price;
	/**
	 * 字段名称：报价编码（唯一）
	 * 
	 * 数据库字段信息:priceNumber varchar(30)
	 */
	private String priceNumber;
	/**
	 * 字段名称：主板型号（机型）
	 * 
	 * 数据库字段信息:model varchar(30)
	 */
	private String model;
	
	/**
	 * 字段名称： 维修报价类别
	 * 
	 * 数据库字段信息 repairType varchar(100)
	 */
	private String repairType;
	
	 /**
	  * 字段名称： 数据使用状态
	  * 数据库字段信息 use_state varchar(10);
	  * 
	  */
	private String useState;
	
	 /**
	  * 字段名称： 关联配件料
	  * 数据库字段信息 pjlDesc varchar(255);
	  * 
	  */
	private String pjlDesc;
	
	private String pjlId;//配件料保存id
	
	private String searchType; //区分查询列表的地方
	
	private BigDecimal sumPrice; //几个维修报价之和
	
	private String idCode;//识别码
	
	private BigDecimal hourFee;//工时费
	
	private String priceGroupDesc;//报价配件料组合
	
	private String priceGroupId;//组合id
	
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Integer getgId() {
		return gId;
	}
	public void setgId(Integer gId) {
		this.gId = gId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPriceNumber() {
		return priceNumber;
	}
	public void setPriceNumber(String priceNumber) {
		this.priceNumber = priceNumber;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRepairType() {
		return repairType;
	}
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}
	public String getUseState() {
		return useState;
	}
	public void setUseState(String useState) {
		this.useState = useState;
	}
	public String getPjlDesc() {
		return pjlDesc;
	}
	public void setPjlDesc(String pjlDesc) {
		this.pjlDesc = pjlDesc;
	}
	public String getPjlId() {
		return pjlId;
	}
	public void setPjlId(String pjlId) {
		this.pjlId = pjlId;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public BigDecimal getHourFee() {
		return hourFee;
	}
	public void setHourFee(BigDecimal hourFee) {
		this.hourFee = hourFee;
	}
	public String getPriceGroupDesc() {
		return priceGroupDesc;
	}
	public void setPriceGroupDesc(String priceGroupDesc) {
		this.priceGroupDesc = priceGroupDesc;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
}
